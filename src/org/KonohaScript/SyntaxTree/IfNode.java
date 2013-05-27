package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.IfNodeAcceptor;

class DefaultIfNodeAcceptor implements IfNodeAcceptor {
	@Override
	public boolean Eval(IfNode Node, NodeVisitor Visitor) {
		Visitor.EnterIf(Node);
		Visitor.Visit(Node.CondExpr);
		Visitor.Visit(Node.ThenNode);
		Visitor.Visit(Node.ElseNode);
		return Visitor.ExitIf(Node);
	}
}

public class IfNode extends TypedNode {
	public TypedNode	CondExpr;
	public TypedNode	ThenNode;
	public TypedNode	ElseNode;

	/* If CondExpr then ThenBlock else ElseBlock */
	public IfNode(KonohaType TypeInfo, TypedNode CondExpr, TypedNode ThenBlock,
			TypedNode ElseNode) {
		super(TypeInfo, null/* fixme */);
		this.CondExpr = CondExpr;
		this.ThenNode = ThenBlock;
		this.ElseNode = ElseNode;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.IfNodeAcceptor.Eval(this, Visitor);
	}

}