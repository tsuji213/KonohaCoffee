package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class ErrorNode extends TypedNode {
	public String	ErrorMessage;

	public ErrorNode(KonohaType TypeInfo, KonohaToken KeyToken, String ErrorMessage) {
		super(TypeInfo, KeyToken);
		this.ErrorMessage = KeyToken.SetErrorMessage(ErrorMessage);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterError(this);
		return Visitor.ExitError(this);
	}
}
