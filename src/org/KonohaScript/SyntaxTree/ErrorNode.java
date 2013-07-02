package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.Parser.KonohaToken;

public class ErrorNode extends TypedNode {
	public String	ErrorMessage;

	public ErrorNode(KonohaType TypeInfo, KonohaToken KeyToken, String ErrorMessage) {
		super(TypeInfo, KeyToken);
		this.ErrorMessage = KeyToken.SetErrorMessage(ErrorMessage);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.VisitError(this);
	}

}
