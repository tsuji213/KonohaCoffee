package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;

public class NewNode extends TypedNode {

	public NewNode(KonohaType TypeInfo) {
		super(TypeInfo, null/* fixme */);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.VisitNew(this);
	}

}