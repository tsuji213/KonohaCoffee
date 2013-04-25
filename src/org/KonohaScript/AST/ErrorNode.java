package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class ErrorNode extends TypedNode {
	String ErrorMessage;

	public ErrorNode(KClass ClassInfo, String ErrorMessage) {
		super(ClassInfo);
		this.ErrorMessage = ErrorMessage;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterError(this);
		Visitor.ExitError(this);
		return true;
	}
}
