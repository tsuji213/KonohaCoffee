package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.Parser.KonohaToken;

public class AssignNode extends BinaryNode {

	public AssignNode(KonohaType TypeInfo, KonohaToken TermToken, TypedNode Left, TypedNode Right) {
		super(TypeInfo, TermToken, Left, Right);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.VisitAssign(this);
	}
}