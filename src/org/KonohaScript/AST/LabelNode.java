package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class LabelNode extends TypedNode {
	public String Label;

	/* Label: */
	LabelNode(KClass ClassInfo, String Label) {
		super(ClassInfo);
		this.Label = Label;
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterLabel(this);
		Gen.ExitLabel(this);
		return true;
	}
}