package org.KonohaScript.Parser;

import org.KonohaScript.KonohaConst;
import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.TypeEnv;
import org.KonohaScript.UntypedNode;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.SyntaxTree.ApplyNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.GetterNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public class KonohaCallExpressionTypeChecker {
	public static final int	CallExpressionOffset	= SyntaxAcceptor.ListOffset;
	public static final int	CallMethodNameOffset	= CallExpressionOffset + 1;
	public static final int	CallParameterOffset		= CallExpressionOffset + 2;

	public static TypedNode TypeCheckMethodCall(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode MemberExpr = UNode.TypeNodeAt(CallExpressionOffset, Gamma, Gamma.VarType, 0);
		if(MemberExpr.IsError()) {
			return MemberExpr;
		}
		TypedNode Reciver = null;
		String MethodName = UNode.GetTokenString(CallMethodNameOffset, null);
		if(MethodName != null) {
			// case: 1 + 10 => MemberExpr=(TypedNode)
			Reciver = MemberExpr;
		} else if(MemberExpr instanceof ConstNode) {
			ConstNode Const = (ConstNode) MemberExpr;
			if(Const.ConstValue instanceof KonohaMethod) {
				// case: MethodName(3) => MemberExpr=(ConstNode:Method)
				// tranform to this.MethodName(3)
				KonohaMethod Method = (KonohaMethod) Const.ConstValue;
				KonohaType ReciverType = Gamma.GetLocalType("this");
				Reciver = new LocalNode(ReciverType, UNode.KeyToken, "this");
				MethodName = Method.MethodName;
			} else {
				// case: SomeClass.CONST(1) => MemberExpr=(ConstNode:Func)
				// transform to CONST.Invoke(1)
				return Gamma.NewErrorNode(UNode.KeyToken, "Calling Function Object is not supported yet.");
			}
		} else if(MemberExpr instanceof LocalNode) {
			// case: FieldName(3) => MemberExpr= (LocalNode:Func FieldName)
			LocalNode LNode = (LocalNode) MemberExpr;
			KonohaType ReciverType = Gamma.GetLocalType("this");
			Reciver = new LocalNode(ReciverType, UNode.KeyToken, "this");
			MethodName = LNode.GetFieldName();
		} else if(MemberExpr instanceof GetterNode) {
			// SomeInstance.MethodName(2) => MemberExpr=(GetterNode:Method SomeInstance FieldName)
			// SomeInstance.FieldName(2) => MemberExpr=(GetterNode:Func SomeInstance FieldName)
			GetterNode GNode = (GetterNode) MemberExpr;
			Reciver = GNode.BaseNode;
			MethodName = GNode.GetFieldName();
		}
		return TypeFindingMethod(Gamma, UNode, TypeInfo, Reciver, MethodName);
	}

	static TypedNode TypeFindingMethod(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo, TypedNode Reciver,
			String MethodName) {
		KonohaArray NodeList = UNode.NodeList;
		int ParamSize = NodeList.size() - CallParameterOffset;
		KonohaType ReciverType = Reciver.TypeInfo;
		KonohaMethod Method = ReciverType.LookupMethod(MethodName, ParamSize);

		KonohaToken KeyToken = UNode.KeyToken;
		if(Method != null) {
			ApplyNode CallNode = new ApplyNode(Method.GetReturnType(ReciverType), KeyToken, Method);
			CallNode.Append(Reciver);
			return TypeMethodEachParam(Gamma, ReciverType, CallNode, NodeList, ParamSize);
		}
		return Gamma.NewErrorNode(KeyToken, "undefined method: " + MethodName + " in " + ReciverType.ShortClassName);
	}

	static TypedNode TypeMethodEachParam(TypeEnv Gamma, KonohaType ReciverType, ApplyNode CallNode, KonohaArray NodeList,
			int ParamSize) {
		KonohaMethod Method = CallNode.Method;
		for(int ParamIdx = 0; ParamIdx < ParamSize; ParamIdx++) {
			KonohaType ParamType = Method.GetParamType(ReciverType, ParamIdx);
			UntypedNode UntypedParamNode = (UntypedNode) NodeList.get(CallParameterOffset + ParamIdx);
			TypedNode ParamNode;
			if(UntypedParamNode != null) {
				ParamNode = TypeEnv.TypeCheck(Gamma, UntypedParamNode, ParamType, KonohaConst.DefaultTypeCheckPolicy);
			} else {
				ParamNode = Gamma.GetDefaultTypedNode(ParamType);
			}
			if(ParamNode.IsError())
				return ParamNode;
			CallNode.Append(ParamNode);
		}
		return CallNode;
	}

}
