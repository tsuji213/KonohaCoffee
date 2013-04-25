package org.KonohaScript.AST;

import java.util.ArrayList;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class TryNode extends TypedNode {
	/*
	 * let HasException = TRY(TryBlock); in if HasException ==
	 * CatchedExceptions[0] then CatchBlock[0] if HasException ==
	 * CatchedExceptions[1] then CatchBlock[1] ... FinallyBlock end
	 */
	TypedNode TryBlock;
	ArrayList<TypedNode> TargetException;
	public ArrayList<TypedNode> CatchBlock;
	TypedNode FinallyBlock;

	TryNode(KClass ClassInfo, TypedNode TryBlock, TypedNode FinallyBlock) {
		super(ClassInfo);
		this.TryBlock = TryBlock;
		this.FinallyBlock = FinallyBlock;
		this.CatchBlock = new ArrayList<TypedNode>();
		this.TargetException = new ArrayList<TypedNode>();
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
