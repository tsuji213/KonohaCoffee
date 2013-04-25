package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class NewNode extends TypedNode {

	public NewNode(KClass ClassInfo) {
		super(ClassInfo);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterNew(this);
		Visitor.ExitNew(this);
		return true;
	}
}