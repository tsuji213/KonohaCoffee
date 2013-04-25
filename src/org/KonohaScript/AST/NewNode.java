package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class NewNode extends TypedNode {

	public NewNode(KClass ClassInfo) {
		super(ClassInfo);
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterNew(this);
		Gen.ExitNew(this);
		return true;
	}
}