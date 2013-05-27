package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KonohaToken;

public class AssignNode extends BinaryNode {

	public AssignNode(KonohaType TypeInfo, KonohaToken TermToken, TypedNode Left, TypedNode Right) {
		super(TypeInfo, TermToken, Left, Right);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		Visitor.EnterAssign(this);
		return Visitor.ExitAssign(this);
	}

}