package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class BoxNode extends UnaryNode {
	public BoxNode(KClass ClassInfo, TypedNode Expr) {
		super(ClassInfo, Expr);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterBox(this);
		Visitor.Visit(this.Expr);
		Visitor.ExitBox(this);
		return true;
	}
}
