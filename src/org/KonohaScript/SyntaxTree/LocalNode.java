package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class LocalNode extends TypedNode {
	/* frame[$Index] (or TermToken->text) */
	int ClassicStackIndex;
	String FieldName;

	public LocalNode(KClass TypeInfo, KToken SourceToken, int Index) {
		super(TypeInfo, SourceToken);
		this.ClassicStackIndex = Index;
	}

	public LocalNode(KClass TypeInfo, int Index, String FieldName) {
		super(TypeInfo);
		this.ClassicStackIndex = Index;
		this.FieldName = FieldName;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterLocal(this);
		return Visitor.ExitLocal(this);
	}
}
