package org.KonohaScript.AST;

import org.KonohaScript.CodeGen.CodeGenerator;

public class JumpNode extends TypedNode {
	int Label;
	/* goto Label */
	JumpNode(int Label) {
		this.Label = Label;
	}
	@Override
	public boolean Evaluate(CodeGenerator Gen) {
	    Gen.EnterJump(this);
	    Gen.ExitJump(this);
	    return true;
	}
}
