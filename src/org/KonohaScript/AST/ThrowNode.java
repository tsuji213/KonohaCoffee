package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class ThrowNode extends UnaryNode {
	/* THROW ExceptionExpr */
	ThrowNode(KClass ClassInfo, TypedNode Expr) {
		super(ClassInfo, Expr);
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterThrow(this);
		Gen.Visit(this.Expr);
		Gen.ExitThrow(this);
		return true;
	}
}
