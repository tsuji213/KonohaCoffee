package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.CodeGen.ASTVisitor;

public class ThrowNode extends UnaryNode {
	/* THROW ExceptionExpr */
	public ThrowNode(KonohaType TypeInfo, TypedNode Expr) {
		super(TypeInfo, Expr);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterThrow(this);
		Visitor.Visit(this.Expr);
		return Visitor.ExitThrow(this);
	}
}
