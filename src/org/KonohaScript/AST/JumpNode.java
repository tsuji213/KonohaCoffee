package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class JumpNode extends TypedNode {
	public String Label;

	/* goto Label */
	JumpNode(KClass ClassInfo, String Label) {
		super(ClassInfo);
		this.Label = Label;
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterJump(this);
		Gen.ExitJump(this);
		return true;
	}
}
