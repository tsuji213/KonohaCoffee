package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KonohaToken;

public class ConstNode extends TypedNode {
	public Object ConstValue;

	public ConstNode(KonohaType TypeInfo, KonohaToken SourceToken, Object ConstValue) {
		super(TypeInfo, SourceToken);
		this.ConstValue = ConstValue;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		Visitor.EnterConst(this);
		return Visitor.ExitConst(this);
	}
}
