package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class LocalNode extends TypedNode {
	/* TermToken->text */
	public String FieldName;

	public LocalNode(KClass TypeInfo, KToken SourceToken, String FieldName) {
		super(TypeInfo, SourceToken);
		this.FieldName = FieldName;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterLocal(this);
		return Visitor.ExitLocal(this);
	}
}
