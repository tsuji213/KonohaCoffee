package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;

public class ThrowNode extends UnaryNode {
	/* THROW ExceptionExpr */
	public ThrowNode(KonohaType TypeInfo, TypedNode Expr) {
		super(TypeInfo, Expr);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		Visitor.EnterThrow(this);
		Visitor.Visit(this.Expr);
		return Visitor.ExitThrow(this);
	}
}
