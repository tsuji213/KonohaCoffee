package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class LocalNode extends TypedNode {
	/* frame[$Index] (or TermToken->text) */
	public KToken TermToken;
	int Index;

	public LocalNode(KClass ClassInfo, KToken TermToken, int Index) {
		super(ClassInfo);
		this.TermToken = TermToken;
		this.Index = Index;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterLocal(this);
		Visitor.ExitLocal(this);
		return true;
	}
}
