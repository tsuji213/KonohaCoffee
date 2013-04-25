package org.KonohaScript.AST;

import java.util.ArrayList;

import org.KonohaScript.CodeGen.CodeGenerator;

public class TryNode extends TypedNode {
	/*
	 * let HasException = TRY(TryBlock); in if HasException ==
	 * CatchedExceptions[0] then CatchBlock[0] if HasException ==
	 * CatchedExceptions[1] then CatchBlock[1] ... FinallyBlock end
	 */
	TypedNode TryBlock;
	ArrayList<TypedNode> CatcheExceptionPairs; /* (ExceptionType, CatchBlock) */
	TypedNode FinallyBlock;

	TryNode(TypedNode TryBlock, TypedNode FinallyBlock) {
		this.TryBlock = TryBlock;
		this.FinallyBlock = FinallyBlock;
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterTry(this);
		Gen.Visit(this.TryBlock);
		Gen.Visit(FinallyBlock);
		Gen.ExitTry(this);
		return false; // FIXME
	}
}
