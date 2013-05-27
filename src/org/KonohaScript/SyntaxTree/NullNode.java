package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;

public class NullNode extends TypedNode {

	public NullNode(KonohaType TypeInfo) {
		super(TypeInfo, null/*fixme*/);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		Visitor.EnterNull(this);
		return Visitor.ExitNull(this);
	}
}
