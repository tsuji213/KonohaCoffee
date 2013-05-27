package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.CodeGen.ASTVisitor;

public class LoopNode extends TypedNode {
	public LoopNode(KonohaType TypeInfo) {
		super(TypeInfo);
	}

	/* while CondExpr then { LoopBlock; IterationExpr } */
	public TypedNode	CondExpr;
	public TypedNode	LoopBody;
	public TypedNode	IterationExpr;

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterLoop(this);
		Visitor.Visit(this.CondExpr);
		Visitor.Visit(this.LoopBody);
		Visitor.Visit(this.IterationExpr);
		return Visitor.ExitLoop(this);
	}
}
