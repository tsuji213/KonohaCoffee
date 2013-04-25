package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class AndNode extends BinaryNode {
	AndNode(KClass ClassInfo, TypedNode Left, TypedNode Right) {
		super(ClassInfo, Left, Right);
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterAnd(this);
		Gen.Visit(this.Left);
		Gen.Visit(this.Right);
		Gen.ExitAnd(this);
		return true;
	}

}
