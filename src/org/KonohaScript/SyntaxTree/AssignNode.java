package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class AssignNode extends TypedNode {
	public KonohaToken		TermToken;
	public TypedNode	Right;

	/* frame[Index] = Right */
	public AssignNode(KonohaType TypeInfo, KonohaToken TermToken, TypedNode Right) {
		super(TypeInfo);
		this.TermToken = TermToken;
		this.Right = Right;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterAssign(this);
		return Visitor.ExitAssign(this);
	}

}