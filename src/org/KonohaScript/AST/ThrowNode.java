package org.KonohaScript.AST;

import org.KonohaScript.CodeGen.CodeGenerator;

public class ThrowNode extends UnaryNode {
	/* THROW ExceptionExpr */
	ThrowNode(TypedNode Expr) {
		super(Expr);
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterThrow(this);
		Gen.Visit(this.Expr);
		Gen.ExitThrow(this);
		return true;
	}
}
