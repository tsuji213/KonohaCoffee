package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class ReturnNode extends UnaryNode {

	public ReturnNode(KClass ClassInfo, TypedNode Expr) {
		super(ClassInfo, Expr);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterReturn(this);
		Visitor.Visit(this.Expr);
		Visitor.ExitReturn(this);
		return true;
	}
}