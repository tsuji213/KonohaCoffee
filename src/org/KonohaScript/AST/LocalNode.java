package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.CodeGenerator;

public class LocalNode extends TypedNode {
	/* frame[$Index] (or TermToken->text) */
	public KToken TermToken;
	int Index;

	LocalNode(KClass ClassInfo, KToken TermToken, int Index) {
		super(ClassInfo);
		this.TermToken = TermToken;
		this.Index = Index;
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterLocal(this);
		Gen.ExitLocal(this);
		return true;
	}
}
