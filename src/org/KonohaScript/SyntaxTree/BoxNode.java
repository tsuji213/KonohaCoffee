package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;

@Deprecated
public class BoxNode extends UnaryNode {
	public BoxNode(KonohaType TypeInfo, TypedNode Expr) {
		super(TypeInfo, Expr);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		Visitor.EnterBox(this);
		Visitor.Visit(this.Expr);
		return Visitor.ExitBox(this);
	}
}
