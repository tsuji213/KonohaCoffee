package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class LetNode extends TypedNode {
	public KonohaToken  VarToken;
	public TypedNode	ValueNode;
	public TypedNode	BlockNode;

	/* let frame[Index] = Right in Block end */
	public LetNode(KonohaType TypeInfo, KonohaToken VarToken, TypedNode Right, TypedNode Block) {
		super(TypeInfo, VarToken);
		this.VarToken = VarToken;
		this.ValueNode = Right;
		this.BlockNode = Block;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterLet(this);
		Visitor.Visit(this.ValueNode);
		Visitor.Visit(this.BlockNode);
		return Visitor.ExitLet(this);
	}
}
