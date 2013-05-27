package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.LoopNodeAcceptor;

class DefaultLoopNodeAcceptor implements LoopNodeAcceptor {
	@Override
	public boolean Eval(LoopNode Node, NodeVisitor Visitor) {
		Visitor.EnterLoop(Node);
		Visitor.Visit(Node.CondExpr);
		Visitor.Visit(Node.LoopBody);
		Visitor.Visit(Node.IterationExpr);
		return Visitor.ExitLoop(Node);
	}
}

public class LoopNode extends TypedNode {

	/* while CondExpr then { LoopBlock; IterationExpr } */
	public TypedNode	CondExpr;
	public TypedNode	LoopBody;
	public TypedNode	IterationExpr;

	public LoopNode(KonohaType TypeInfo) {
		super(TypeInfo, null/* fixme */);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.LoopNodeAcceptor.Eval(this, Visitor);
	}

}
