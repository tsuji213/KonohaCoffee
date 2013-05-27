package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.CodeGen.ASTVisitor;

public class OrNode extends BinaryNode {

	public OrNode(KonohaType TypeInfo, TypedNode Left, TypedNode Right) {
		super(TypeInfo, Left, Right);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterOr(this);
		Visitor.Visit(this.Left);
		Visitor.Visit(this.Right);
		return Visitor.ExitOr(this);
	}
}
