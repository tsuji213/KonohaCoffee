package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.AssignNodeAcceptor;

class DefaultAssignNodeAcceptor implements AssignNodeAcceptor {
	@Override
	public boolean Invoke(AssignNode Node, NodeVisitor Visitor) {
		Visitor.EnterAssign(Node);
		Visitor.Visit(Node.LeftNode);
		Visitor.Visit(Node.RightNode);
		return Visitor.ExitAssign(Node);
	}
}

public class AssignNode extends BinaryNode {

	public AssignNode(KonohaType TypeInfo, KonohaToken TermToken,
			TypedNode Left, TypedNode Right) {
		super(TypeInfo, TermToken, Left, Right);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.AssignNodeAcceptor.Invoke(this, Visitor);
	}
}