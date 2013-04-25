package org.KonohaScript.AST;

import org.KonohaScript.CodeGen.CodeGenerator;

public class LabelNode extends TypedNode {
	int Label;
	/* Label: */
	LabelNode(int Label) {
		this.Label = Label;
	}
	@Override
	public boolean Evaluate(CodeGenerator Gen) {
	    Gen.EnterLabel(this);
	    Gen.ExitLabel(this);
	    return true;
	}
}