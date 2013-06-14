package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.LocalNodeAcceptor;

class DefaultLocalNodeAcceptor implements LocalNodeAcceptor {
	@Override
	public boolean Invoke(LocalNode Node, NodeVisitor Visitor) {
		Visitor.EnterLocal(Node);
		return Visitor.ExitLocal(Node);
	}
}

public class LocalNode extends FieldNode {
	public LocalNode(KonohaType TypeInfo, KonohaToken SourceToken, String FieldName) {
		super(TypeInfo, SourceToken, FieldName);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.LocalNodeAcceptor.Invoke(this, Visitor);
	}

}
