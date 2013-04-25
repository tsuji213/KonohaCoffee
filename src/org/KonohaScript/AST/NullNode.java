package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class NullNode extends TypedNode {

	public NullNode(KClass ClassInfo) {
		super(ClassInfo);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterNull(this);
		Visitor.ExitNull(this);
		return true;
	}
}
