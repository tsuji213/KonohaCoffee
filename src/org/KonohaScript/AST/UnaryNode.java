package org.KonohaScript.AST;

import org.KonohaScript.KClass;

public abstract class UnaryNode extends TypedNode {
	TypedNode Expr;

	UnaryNode(KClass ClassInfo, TypedNode Expr) {
		super(ClassInfo);
		this.Expr = Expr;
	}
}