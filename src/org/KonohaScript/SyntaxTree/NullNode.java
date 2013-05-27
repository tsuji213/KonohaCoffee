package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.CodeGen.ASTVisitor;

public class NullNode extends TypedNode {

	public NullNode(KonohaType TypeInfo) {
		super(TypeInfo);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterNull(this);
		return Visitor.ExitNull(this);
	}
}
