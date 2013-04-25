package org.KonohaScript.AST;

import org.KonohaScript.CodeGen.CodeGenerator;

public class ReturnNode extends UnaryNode {

	ReturnNode(TypedNode Expr) {
		super(Expr);
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterReturn(this);
		Gen.Visit(this.Expr);
		Gen.ExitReturn(this);
		return true;
	}
}