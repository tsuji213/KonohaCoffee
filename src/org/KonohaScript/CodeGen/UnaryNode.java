package org.KonohaScript.CodeGen;

public abstract class UnaryNode extends TypedNode {
	TypedNode Expr;
	UnaryNode(TypedNode Expr) {
		this.Expr = Expr;
	}
}