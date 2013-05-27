package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class LetNode extends TypedNode {
	public KonohaToken		TermToken;
	public TypedNode	Right;
	public TypedNode	Block;

	/* let frame[Index] = Right in Block end */
	public LetNode(KonohaType TypeInfo, KonohaToken TermToken, TypedNode Right, BlockNode Block) {
		super(TypeInfo);
		this.TermToken = TermToken;
		this.Right = Right;
		this.Block = Block;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterLet(this);
		Visitor.Visit(this.Right);
		Visitor.Visit(this.Block);
		return Visitor.ExitLet(this);
	}
}
