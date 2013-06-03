package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.ReturnNodeAcceptor;

class DefaultReturnNodeAcceptor implements ReturnNodeAcceptor {
	@Override
	public boolean Invoke(ReturnNode Node, NodeVisitor Visitor) {
		Visitor.EnterReturn(Node);
		Visitor.Visit(Node.Expr);
		return Visitor.ExitReturn(Node);
	}
}

public class ReturnNode extends UnaryNode {

	public ReturnNode(KonohaType TypeInfo, TypedNode Expr) {
		super(TypeInfo, Expr);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.ReturnNodeAcceptor.Invoke(this, Visitor);
	}

}