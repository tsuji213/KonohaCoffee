package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class AssignNode extends BinaryNode {

	public AssignNode(KonohaType TypeInfo, KonohaToken TermToken, TypedNode Left, TypedNode Right) {
		super(TypeInfo, TermToken, Left, Right);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterAssign(this);
		return Visitor.ExitAssign(this);
	}

}