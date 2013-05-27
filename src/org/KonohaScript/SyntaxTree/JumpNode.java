package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;

public class JumpNode extends TypedNode {
	public String Label;

	/* goto Label */
	public JumpNode(KonohaType TypeInfo, String Label) {
		super(TypeInfo, null/*fixme*/);
		this.Label = Label;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		Visitor.EnterJump(this);
		return Visitor.ExitJump(this);
	}
}
