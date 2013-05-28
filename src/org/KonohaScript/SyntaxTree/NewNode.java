package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.NewNodeAcceptor;

class DefaultNewNodeAcceptor implements NewNodeAcceptor {
	@Override
	public boolean Invoke(NewNode Node, NodeVisitor Visitor) {
		Visitor.EnterNew(Node);
		return Visitor.ExitNew(Node);
	}
}

public class NewNode extends TypedNode {

	public NewNode(KonohaType TypeInfo) {
		super(TypeInfo, null/* fixme */);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.NewNodeAcceptor.Invoke(this, Visitor);
	}

}