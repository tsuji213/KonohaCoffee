package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.CodeGen.ASTVisitor;

public class NewNode extends TypedNode {

	public NewNode(KonohaType TypeInfo) {
		super(TypeInfo, null/*fixme*/);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterNew(this);
		return Visitor.ExitNew(this);
	}
}