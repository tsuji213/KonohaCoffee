package org.KonohaScript.CodeGen;

public class LoopNode extends TypedNode {
	/* while CondExpr then { LoopBlock; IterationExpr } */
	TypedNode CondExpr;
	TypedNode LoopBody;
	TypedNode IterationExpr;
}
