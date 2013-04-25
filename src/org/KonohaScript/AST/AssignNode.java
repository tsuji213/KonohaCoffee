package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.CodeGenerator;

public class AssignNode extends TypedNode {
	public KToken TermToken;
	int Index;
	TypedNode Right;

	/* frame[Index] = Right */
	AssignNode(KClass ClassInfo, KToken TermToken, int Index, TypedNode Right) {
		super(ClassInfo);
		this.TermToken = TermToken;
		this.Index = Index;
		this.Right = Right;
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterAssign(this);
		Gen.ExitAssign(this);
		return true;
	}

}