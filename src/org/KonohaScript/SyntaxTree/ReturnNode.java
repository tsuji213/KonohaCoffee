package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.CodeGen.ASTVisitor;

public class ReturnNode extends UnaryNode {

	public ReturnNode(KonohaType TypeInfo, TypedNode Expr) {
		super(TypeInfo, Expr);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterReturn(this);
		Visitor.Visit(this.Expr);
		return Visitor.ExitReturn(this);
	}
}