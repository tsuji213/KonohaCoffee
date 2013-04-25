package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class JumpNode extends TypedNode {
	public String Label;

	/* goto Label */
	public JumpNode(KClass ClassInfo, String Label) {
		super(ClassInfo);
		this.Label = Label;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterJump(this);
		Visitor.ExitJump(this);
		return true;
	}
}
