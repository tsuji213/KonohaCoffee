package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;

public abstract class UnaryNode extends TypedNode {
	public TypedNode	Expr;

	UnaryNode(KonohaType TypeInfo, TypedNode Expr) {
		super(TypeInfo, null/*fixme*/);
		this.Expr = Expr;
	}
}