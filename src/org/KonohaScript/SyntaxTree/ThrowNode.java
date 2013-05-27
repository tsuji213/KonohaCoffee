package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.ThrowNodeAcceptor;

class DefaultThrowNodeAcceptor implements ThrowNodeAcceptor {
	@Override
	public boolean Eval(ThrowNode Node, NodeVisitor Visitor) {
		Visitor.EnterThrow(Node);
		Visitor.Visit(Node.Expr);
		return Visitor.ExitThrow(Node);
	}
}

public class ThrowNode extends UnaryNode {
	/* THROW ExceptionExpr */
	public ThrowNode(KonohaType TypeInfo, TypedNode Expr) {
		super(TypeInfo, Expr);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.ThrowNodeAcceptor.Eval(this, Visitor);
	}
}
