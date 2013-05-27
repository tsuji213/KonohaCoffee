package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.CodeGen.ASTVisitor;

public class AndNode extends BinaryNode {
	public AndNode(KonohaType TypeInfo, KonohaToken KeyToken, TypedNode Left, TypedNode Right) {
		super(TypeInfo, KeyToken, Left, Right);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterAnd(this);
		Visitor.Visit(this.LeftNode);
		Visitor.Visit(this.RightNode);
		return Visitor.ExitAnd(this);
	}

}
