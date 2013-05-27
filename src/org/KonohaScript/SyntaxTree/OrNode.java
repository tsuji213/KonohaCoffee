package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;

public class OrNode extends BinaryNode {

	public OrNode(KonohaType TypeInfo, KonohaToken KeyToken, TypedNode Left, TypedNode Right) {
		super(TypeInfo, KeyToken, Left, Right);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		Visitor.EnterOr(this);
		Visitor.Visit(this.LeftNode);
		Visitor.Visit(this.RightNode);
		return Visitor.ExitOr(this);
	}
}
