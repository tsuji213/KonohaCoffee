package org.KonohaScript.AST;

import org.KonohaScript.CodeGen.CodeGenerator;

public class NewNode extends TypedNode {

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterNew(this);
		Gen.ExitNew(this);
		return true;
	}
}