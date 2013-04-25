package org.KonohaScript.AST;

import org.KonohaScript.CodeGen.CodeGenerator;

public class NullNode extends TypedNode {

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterNull(this);
		Gen.ExitNull(this);
		return true;
	}
}
