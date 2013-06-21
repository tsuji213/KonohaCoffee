package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;

public class ReturnNode extends UnaryNode {

	public ReturnNode(KonohaType TypeInfo, TypedNode Expr) {
		super(TypeInfo, Expr);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.VisitReturn(this);
	}

}