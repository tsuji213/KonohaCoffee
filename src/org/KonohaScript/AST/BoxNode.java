package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class BoxNode extends UnaryNode {
	BoxNode(KClass ClassInfo, TypedNode Expr) {
		super(ClassInfo, Expr);
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterBox(this);
		Gen.Visit(this.Expr);
		Gen.ExitBox(this);
		return true;
	}
}
