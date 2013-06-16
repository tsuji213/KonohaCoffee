package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.SyntaxTree.NodeVisitor.ErrorNodeAcceptor;

class DefaultErrorNodeAcceptor implements ErrorNodeAcceptor {
	@Override
	public boolean Invoke(ErrorNode Node, NodeVisitor Visitor) {
		Visitor.EnterError(Node);
		return Visitor.ExitError(Node);
	}
}

public class ErrorNode extends TypedNode {
	public String	ErrorMessage;

	public ErrorNode(KonohaType TypeInfo, KonohaToken KeyToken,
			String ErrorMessage) {
		super(TypeInfo, KeyToken);
		this.ErrorMessage = KeyToken.SetErrorMessage(ErrorMessage);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.ErrorNodeAcceptor.Invoke(this, Visitor);
	}

}
