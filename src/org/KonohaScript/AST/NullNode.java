package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class NullNode extends TypedNode {

	public NullNode(KClass ClassInfo) {
		super(ClassInfo);
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterNull(this);
		Gen.ExitNull(this);
		return true;
	}
}
