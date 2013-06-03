package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.NullNodeAcceptor;

class DefaultNullNodeAcceptor implements NullNodeAcceptor {
	@Override
	public boolean Invoke(NullNode Node, NodeVisitor Visitor) {
		Visitor.EnterNull(Node);
		return Visitor.ExitNull(Node);
	}
}

public class NullNode extends TypedNode {

	public NullNode(KonohaType TypeInfo) {
		super(TypeInfo, null/* fixme */);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.NullNodeAcceptor.Invoke(this, Visitor);
	}

}
