package org.KonohaScript.CodeGen;

public class ThrowNode extends UnaryNode {
	/* THROW ExceptionExpr */
	ThrowNode(TypedNode Expr) {
		super(Expr);
	}
}
