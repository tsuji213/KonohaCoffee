package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.CodeGen.ASTVisitor;

public class LabelNode extends TypedNode {
	public String Label;

	/* Label: */
	public LabelNode(KonohaType TypeInfo, String Label) {
		super(TypeInfo, null/*fixme*/);
		this.Label = Label;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterLabel(this);
		return Visitor.ExitLabel(this);
	}
}