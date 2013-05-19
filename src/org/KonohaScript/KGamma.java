package org.KonohaScript;

import java.lang.reflect.InvocationTargetException;
import org.KonohaScript.SyntaxTree.*;

//struct KGammaStack {
//KGammaStackDecl *varItems;
//size_t varsize;
//size_t capacity;
//size_t allocsize;  // set size if not allocated  (by default on stack)
//} ;
//
//struct KGammaLocalData {
//khalfflag_t  flag;   khalfflag_t cflag;
//KClass   *thisClass;
//kMethod  *currentWorkingMethod;
//struct KGammaStack    localScope;
//} ;

public class KGamma {
	
	KNameSpace GammaNameSpace; 
	
	KGamma(KNameSpace ns) {
		GammaNameSpace = ns;
	}
	
	KClass GetLocalType(String Symbol) {
		return null;
	}
	
	int GetLocalIndex(String Symbol) {
		return -1;
	}
	
	public static TypedNode TypeEachNode(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		TypedNode Node = null;
		try {
			Node = (TypedNode)UNode.Syntax.TypeMethod.invoke(UNode.Syntax.TypeObject, Gamma, UNode, TypeInfo);
		} 
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Node = new ErrorNode(TypeInfo, UNode.KeyToken, "internal error: " + e);
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Node = new ErrorNode(TypeInfo, UNode.KeyToken, "internal error: " + e);
		} 
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Node = new ErrorNode(TypeInfo, UNode.KeyToken, "internal error: " + e);
		}
		if(Node == null) {
			Node = new ErrorNode(TypeInfo, UNode.KeyToken, "undefined type checker: " + UNode.Syntax);
		}
		return Node;
	}

	public static TypedNode TypeCheckEachNode(KGamma Gamma, UntypedNode UNode, KClass TypeInfo, int TypeCheckPolicy) {
		TypedNode Node = TypeEachNode(Gamma, UNode, TypeInfo);
//		if(Node.TypeInfo == null) {
//			
//		}
		return Node;
	}
	
	public static TypedNode TypeCheck(KGamma Gamma, UntypedNode UNode, KClass TypeInfo, int TypeCheckPolicy) {
		TypedNode TPrevNode = null;
		while(UNode != null) {
			KClass CurrentTypeInfo = (UNode.NextNode != null) ? KClass.VoidType : TypeInfo;
			TypedNode CurrentTypedNode = TypeCheckEachNode(Gamma, UNode, CurrentTypeInfo, TypeCheckPolicy);
			if(TPrevNode == null) {
				TPrevNode = CurrentTypedNode;
			}
			else {
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
