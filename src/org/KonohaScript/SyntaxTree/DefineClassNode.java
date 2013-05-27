package org.KonohaScript.SyntaxTree;

import java.util.ArrayList;

import org.KonohaScript.KClass;

@Deprecated
public class DefineClassNode extends TypedNode implements CallableNode {
	public ArrayList<TypedNode>	Fields;

	public DefineClassNode(KClass TypeInfo) {
		super(TypeInfo);
		this.Fields = new ArrayList<TypedNode>();
	}

	@Override
	public void Append(TypedNode Expr) {
		assert (Expr instanceof FieldNode);
		this.Fields.add(Expr);
	}

}