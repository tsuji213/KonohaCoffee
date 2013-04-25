package org.KonohaScript.AST;

import org.KonohaScript.CodeGen.CodeGenerator;

public class OrNode extends BinaryNode {

	OrNode(TypedNode Left, TypedNode Right) {
		super(Left, Right);
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterOr(this);
		Gen.Visit(this.Left);
		Gen.Visit(this.Right);
		Gen.ExitOr(this);
		return true;
	}
}
