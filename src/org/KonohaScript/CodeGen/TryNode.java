package org.KonohaScript.CodeGen;

import java.util.ArrayList;

public class TryNode extends TypedNode {
	/* let
	 *   HasException = TRY(TryBlock);
	 * in
	 *   if HasException == CatchedExceptions[0] then CatchBlock[0]
	 *   if HasException == CatchedExceptions[1] then CatchBlock[1]
	 *   ...
	 *   FinallyBlock
	 * end
	 * */
	TypedNode TryBlock;
	ArrayList<TypedNode> CatcheExceptionPairs; /* (ExceptionType, CatchBlock) */
	TypedNode FinallyBlock;
	TryNode(TypedNode TryBlock, TypedNode FinallyBlock) {
		this.TryBlock = TryBlock;
		this.FinallyBlock = FinallyBlock;
	}
}
