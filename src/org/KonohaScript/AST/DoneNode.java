package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class DoneNode extends TypedNode {
	public DoneNode(KClass ClassInfo) {
		super(ClassInfo);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterDone(this);
		Visitor.ExitDone(this);
		return true;
	}
}