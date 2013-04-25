package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class ThrowNode extends UnaryNode {
	/* THROW ExceptionExpr */
	public ThrowNode(KClass ClassInfo, TypedNode Expr) {
		super(ClassInfo, Expr);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterThrow(this);
		Visitor.Visit(this.Expr);
		Visitor.ExitThrow(this);
		return true;
	}
}
