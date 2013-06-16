package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.SyntaxTree.NodeVisitor.LetNodeAcceptor;

class DefaultLetNodeAcceptor implements LetNodeAcceptor {
	@Override
	public boolean Invoke(LetNode Node, NodeVisitor Visitor) {
		Visitor.EnterLet(Node);
		Visitor.Visit(Node.ValueNode);
		Visitor.VisitList(Node.BlockNode);
		return Visitor.ExitLet(Node);
	}
}

public class LetNode extends TypedNode {
	public KonohaToken	VarToken;
	public TypedNode	ValueNode;
	public TypedNode	BlockNode;

	/* let frame[Index] = Right in Block end */
	public LetNode(KonohaType TypeInfo, KonohaToken VarToken, TypedNode Right, TypedNode Block) {
		super(TypeInfo, VarToken);
		this.VarToken = VarToken;
		this.ValueNode = Right;
		this.BlockNode = Block;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.LetNodeAcceptor.Invoke(this, Visitor);
	}

}
