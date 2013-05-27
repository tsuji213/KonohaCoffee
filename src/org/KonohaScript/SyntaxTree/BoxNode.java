package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.CodeGen.ASTVisitor;

@Deprecated
public class BoxNode extends UnaryNode {
	public BoxNode(KonohaType TypeInfo, TypedNode Expr) {
		super(TypeInfo, Expr);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterBox(this);
		Visitor.Visit(this.Expr);
		return Visitor.ExitBox(this);
	}
}
