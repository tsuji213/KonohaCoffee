package org.KonohaScript.CodeGen;

public class JumpNode extends TypedNode {
	int Label;
	/* goto Label */
	JumpNode(int Label) {
		this.Label = Label;
	}
}
