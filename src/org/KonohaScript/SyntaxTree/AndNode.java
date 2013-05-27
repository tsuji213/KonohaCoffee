package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.AndNodeAcceptor;

class DefaultAndNodeAcceptor implements AndNodeAcceptor {
	@Override
	public boolean Eval(AndNode Node, NodeVisitor Visitor) {
		Visitor.EnterAnd(Node);
		Visitor.Visit(Node.LeftNode);
		Visitor.Visit(Node.RightNode);
		return Visitor.ExitAnd(Node);
	}
}

public class AndNode extends BinaryNode {
	public AndNode(KonohaType TypeInfo, KonohaToken KeyToken, TypedNode Left,
			TypedNode Right) {
		super(TypeInfo, KeyToken, Left, Right);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.AndNodeAcceptor.Eval(this, Visitor);
	}
}
