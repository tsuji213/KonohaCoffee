package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.Parser.KonohaToken;

public class LetNode extends TypedNode {
	public KonohaToken	VarToken;
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
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.VisitLet(this);
	}

}
