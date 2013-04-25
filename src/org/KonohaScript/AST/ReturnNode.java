package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class ReturnNode extends UnaryNode {

	public ReturnNode(KClass ClassInfo, TypedNode Expr) {
		super(ClassInfo, Expr);
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterReturn(this);
		Gen.Visit(this.Expr);
		Gen.ExitReturn(this);
		return true;
	}
}