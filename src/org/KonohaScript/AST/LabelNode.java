package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class LabelNode extends TypedNode {
	public String Label;

	/* Label: */
	public LabelNode(KClass ClassInfo, String Label) {
		super(ClassInfo);
		this.Label = Label;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterLabel(this);
		Visitor.ExitLabel(this);
		return true;
	}
}