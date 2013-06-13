package org.KonohaScript;

import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.SyntaxTree.ErrorNode;
import org.KonohaScript.SyntaxTree.TypedNode;

class VarSet {
	KonohaType	TypeInfo;
	String		Name;

	VarSet(KonohaType TypeInfo, String Name) {
		this.TypeInfo = TypeInfo;
		this.Name = Name;
	}
}

public class TypeEnv implements KonohaConst {

	public KonohaNameSpace	GammaNameSpace;

	/* for convinient short cut */
	public final KonohaType	VoidType;
	public final KonohaType	BooleanType;
	public final KonohaType	IntType;
	public final KonohaType	StringType;
	public final KonohaType	VarType;

	public TypeEnv(KonohaNameSpace GammaNameSpace, KonohaMethod Method) {
		this.GammaNameSpace = GammaNameSpace;
		this.VoidType = GammaNameSpace.KonohaContext.VoidType;
		this.BooleanType = GammaNameSpace.KonohaContext.BooleanType;
		this.IntType = GammaNameSpace.KonohaContext.IntType;
		this.StringType = GammaNameSpace.KonohaContext.StringType;
		this.VarType = GammaNameSpace.KonohaContext.VarType;
		this.Method = Method;
		if(Method != null) {
			this.InitMethod(Method);
		} else {
			// global
			this.AppendLocalType(GammaNameSpace.GetGlobalObject().TypeInfo, "this");
		}
	}

	public KonohaMethod	Method;
	public KonohaType	ReturnType;

	void InitMethod(KonohaMethod Method) {
		this.ReturnType = Method.GetReturnType(Method.ClassInfo);
		if(!Method.Is(StaticMethod)) {
			this.AppendLocalType(Method.ClassInfo, "this");
			KonohaParam Param = Method.Param;
			for(int i = 0; i < Param.ArgNames.length; i++) {
				this.AppendLocalType(Param.Types[i + Param.ReturnSize], Param.ArgNames[i]);
			}
		}
	}

	KonohaArray	LocalStackList	= null;

	public void AppendLocalType(KonohaType TypeInfo, String Name) {
		if(this.LocalStackList == null) {
			this.LocalStackList = new KonohaArray();
		}
		this.LocalStackList.add(new VarSet(TypeInfo, Name));
	}

	public KonohaType GetLocalType(String Symbol) {
		if(this.LocalStackList != null) {
			for(int i = this.LocalStackList.size() - 1; i >= 0; i--) {
				VarSet t = (VarSet) this.LocalStackList.get(i);
				if(t.Name.equals(Symbol))
					return t.TypeInfo;
			}
		}
		return null;
	}

	int GetLocalIndex(String Symbol) {
		return -1;
	}

	public TypedNode GetDefaultTypedNode(KonohaType TypeInfo) {
		return null; // TODO
	}

	public TypedNode NewErrorNode(KonohaToken KeyToken, String Message) {
		return new ErrorNode(this.VoidType, KeyToken, this.GammaNameSpace.Message(KonohaConst.Error, KeyToken, Message));
	}

	public static TypedNode TypeEachNode(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode Node = UNode.Syntax.InvokeTypeFunc(Gamma, UNode, TypeInfo);

		if(Node == null) {
			Node = Gamma.NewErrorNode(UNode.KeyToken, "undefined type checker: " + UNode.Syntax);
		}
		return Node;
	}

	public static TypedNode TypeCheckEachNode(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo, int TypeCheckPolicy) {
		TypedNode Node = TypeEachNode(Gamma, UNode, TypeInfo);
		// if(Node.TypeInfo == null) {
		//
		// }
		return Node;
	}

	public static TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo, int TypeCheckPolicy) {
		TypedNode TPrevNode = null;
		while(UNode != null) {
			KonohaType CurrentTypeInfo = (UNode.NextNode != null) ? Gamma.VoidType : TypeInfo;
			TypedNode CurrentTypedNode = TypeCheckEachNode(Gamma, UNode, CurrentTypeInfo, TypeCheckPolicy);
			if(TPrevNode == null) {
				TPrevNode = CurrentTypedNode;
			} else {
				TPrevNode.LinkNode(CurrentTypedNode);
			}
			if(CurrentTypedNode.IsError()) {
				break;
			}
			UNode = UNode.NextNode;
		}
		return TPrevNode == null ? null : TPrevNode.GetHeadNode();
	}

}
