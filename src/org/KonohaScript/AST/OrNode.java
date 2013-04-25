package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class OrNode extends BinaryNode {

	public OrNode(KClass ClassInfo, TypedNode Left, TypedNode Right) {
		super(ClassInfo, Left, Right);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterOr(this);
		Visitor.Visit(this.Left);
		Visitor.Visit(this.Right);
		Visitor.ExitOr(this);
		return true;
	}
}
