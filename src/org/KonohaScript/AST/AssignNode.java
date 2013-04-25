package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class AssignNode extends TypedNode {
	public KToken TermToken;
	int Index;
	TypedNode Right;

	/* frame[Index] = Right */
	public AssignNode(KClass ClassInfo, KToken TermToken, int Index,
			TypedNode Right) {
		super(ClassInfo);
		this.TermToken = TermToken;
		this.Index = Index;
		this.Right = Right;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterAssign(this);
		Visitor.ExitAssign(this);
		return true;
	}

}