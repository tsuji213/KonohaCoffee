package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class LoopNode extends TypedNode {
	public LoopNode(KClass ClassInfo) {
		super(ClassInfo);
	}

	/* while CondExpr then { LoopBlock; IterationExpr } */
	TypedNode CondExpr;
	TypedNode LoopBody;
	TypedNode IterationExpr;

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterLoop(this);
		Gen.Visit(this.CondExpr);
		Gen.Visit(this.LoopBody);
		Gen.Visit(this.IterationExpr);
		Gen.ExitLoop(this);
		return true;
	}
}
