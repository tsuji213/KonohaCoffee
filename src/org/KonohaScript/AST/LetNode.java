package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.CodeGenerator;

public class LetNode extends TypedNode {
	public KToken TermToken;
	int Index;
	TypedNode Right;
	TypedNode Block;

	/* let frame[Index] = Right in Block end */
	LetNode(KClass ClassInfo, KToken TermToken, int Index, TypedNode Right,
			BlockNode Block) {
		super(ClassInfo);
		this.TermToken = TermToken;
		this.Index = Index;
		this.Right = Right;
		this.Block = Block;
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterLet(this);
		Gen.Visit(this.Right);
		Gen.Visit(this.Block);
		Gen.ExitLet(this);
		return true;
	}
}
