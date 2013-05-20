package org.KonohaScript.SyntaxTree;

import java.util.ArrayList;

import org.KonohaScript.KClass;

public class DefineClassNode extends TypedNode implements CallableNode {
	ArrayList<FieldNode> Fields;

	public DefineClassNode(KClass TypeInfo) {
		super(TypeInfo);
		this.Fields = new ArrayList<FieldNode>();
	}

	@Override
	public void Append(TypedNode Expr) {
		FieldNode node = (FieldNode) Expr;
		this.Fields.add(node);
	}

}