package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KLib.*;

import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.TryNodeAcceptor;

class DefaultTryNodeAcceptor implements TryNodeAcceptor {
	@Override
	public boolean Invoke(TryNode Node, NodeVisitor Visitor) {
		Visitor.EnterTry(Node);
		Visitor.VisitList(Node.TryBlock);
		Visitor.VisitList(Node.FinallyBlock);
		return Visitor.ExitTry(Node);
	}
}

public class TryNode extends TypedNode {
	/*
	 * let HasException = TRY(TryBlock); in if HasException ==
	 * CatchedExceptions[0] then CatchBlock[0] if HasException ==
	 * CatchedExceptions[1] then CatchBlock[1] ... FinallyBlock end
	 */
	public TypedNode			TryBlock;
	public ArrayList<TypedNode>	TargetException;
	public ArrayList<TypedNode>	CatchBlock;
	public TypedNode			FinallyBlock;

	public TryNode(KonohaType TypeInfo, TypedNode TryBlock, TypedNode FinallyBlock) {
		super(TypeInfo, null/* fixme */);
		this.TryBlock = TryBlock;
		this.FinallyBlock = FinallyBlock;
		this.CatchBlock = new ArrayList<TypedNode>();
		this.TargetException = new ArrayList<TypedNode>();
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.TryNodeAcceptor.Invoke(this, Visitor);
	}
}
