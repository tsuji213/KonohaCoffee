package org.KonohaScript;

import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import org.KonohaScript.SyntaxTree.*;

class VarSet {
	KClass TypeInfo;
	String Name;
	VarSet(KClass TypeInfo, String Name) {
		this.TypeInfo = TypeInfo;
		this.Name = Name;
	}
}

public class KGamma {
	
	public KNameSpace GammaNameSpace; 
	
	/* for convinient short cut */
	public final KClass VoidType;
	public final KClass BooleanType;
	public final KClass IntType;
	public final KClass StringType;
	public final KClass VarType;
	
	KGamma(KNameSpace ns) {
		GammaNameSpace = ns;
		VoidType = ns.KonohaContext.VoidType;
		BooleanType = ns.KonohaContext.BooleanType;
		IntType = ns.KonohaContext.IntType;
		StringType = ns.KonohaContext.StringType;
		VarType = ns.KonohaContext.VarType;
	}
	
	ArrayList<VarSet> LocalStackList = null;
	
	public void AppendLocalType(KClass TypeInfo, String Name) {
		if(LocalStackList == null) {
			LocalStackList = new ArrayList<VarSet>();
		}
		LocalStackList.add(new VarSet(TypeInfo, Name));
	}
	
	public KClass GetLocalType(String Symbol) {
		if(LocalStackList != null) {
			for(int i = LocalStackList.size()-1; i >=0; i--) {
				VarSet t = LocalStackList.get(i);
				if(t.Name.equals(Symbol)) return t.TypeInfo;
			}
		}
		return null;
	}
	
	
	int GetLocalIndex(String Symbol) {
		return -1;
	}
	
	public TypedNode GetDefaultTypedNode(KClass TypeInfo) {
		return null;  // TODO
	}
	
	public TypedNode NewErrorNode(KToken KeyToken, String Message) {
		return new ErrorNode(VoidType, KeyToken, GammaNameSpace.Message(KonohaParserConst.Error, KeyToken, Message));
	}
	
	public static TypedNode TypeEachNode(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		TypedNode Node = null;
		try {
//			System.err.println("Syntax" + UNode.Syntax);
//			System.err.println("Syntax.TypeMethod" + UNode.Syntax.TypeMethod);
//			System.err.println("Syntax.TypeObject" + UNode.Syntax.TypeObject);
			Node = (TypedNode)UNode.Syntax.TypeMethod.invoke(UNode.Syntax.TypeObject, Gamma, UNode, TypeInfo);
		} 
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Node = Gamma.NewErrorNode(UNode.KeyToken, "internal error: " + e);
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Node = Gamma.NewErrorNode(UNode.KeyToken, "internal error: " + e);
		} 
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Node = Gamma.NewErrorNode(UNode.KeyToken, "internal error: " + e);
		}
		if(Node == null) {
			Node = Gamma.NewErrorNode(UNode.KeyToken, "undefined type checker: " + UNode.Syntax);
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
			KClass CurrentTypeInfo = (UNode.NextNode != null) ? Gamma.VoidType : TypeInfo;
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
