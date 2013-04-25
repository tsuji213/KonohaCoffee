package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class DoneNode extends TypedNode {
	public DoneNode(KClass ClassInfo) {
		super(ClassInfo);
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterDone(this);
		Gen.ExitDone(this);
		return true;
	}
}