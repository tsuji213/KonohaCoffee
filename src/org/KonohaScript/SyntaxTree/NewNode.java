package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;

public class NewNode extends TypedNode {

	public NewNode(KonohaType TypeInfo) {
		super(TypeInfo, null/*fixme*/);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		Visitor.EnterNew(this);
		return Visitor.ExitNew(this);
	}
}