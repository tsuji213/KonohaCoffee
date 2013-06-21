package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.Parser.KonohaToken;

public class OrNode extends BinaryNode {

	public OrNode(KonohaType TypeInfo, KonohaToken KeyToken, TypedNode Left, TypedNode Right) {
		super(TypeInfo, KeyToken, Left, Right);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.VisitOr(this);
	}

}
