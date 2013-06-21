package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.KonohaArray;

public class TryNode extends TypedNode {
	/*
	 * let HasException = TRY(TryBlock); in if HasException ==
	 * CatchedExceptions[0] then CatchBlock[0] if HasException ==
	 * CatchedExceptions[1] then CatchBlock[1] ... FinallyBlock end
	 */
	public TypedNode	TryBlock;
	public KonohaArray	TargetException;
	public KonohaArray	CatchBlock;
	public TypedNode	FinallyBlock;

	public TryNode(KonohaType TypeInfo, TypedNode TryBlock, TypedNode FinallyBlock) {
		super(TypeInfo, null/* fixme */);
		this.TryBlock = TryBlock;
		this.FinallyBlock = FinallyBlock;
		this.CatchBlock = new KonohaArray();
		this.TargetException = new KonohaArray();
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.VisitTry(this);
	}
}
