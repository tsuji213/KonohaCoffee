package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;

public class AndNode extends BinaryNode {
	public AndNode(KonohaType TypeInfo, KonohaToken KeyToken, TypedNode Left, TypedNode Right) {
		super(TypeInfo, KeyToken, Left, Right);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		Visitor.EnterAnd(this);
		Visitor.Visit(this.LeftNode);
		Visitor.Visit(this.RightNode);
		return Visitor.ExitAnd(this);
	}

}
