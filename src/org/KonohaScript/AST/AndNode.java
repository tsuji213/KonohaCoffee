package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class AndNode extends BinaryNode {
	public AndNode(KClass ClassInfo, TypedNode Left, TypedNode Right) {
		super(ClassInfo, Left, Right);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterAnd(this);
		Visitor.Visit(this.Left);
		Visitor.Visit(this.Right);
		Visitor.ExitAnd(this);
		return true;
	}

}
