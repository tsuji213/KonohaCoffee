package org.KonohaScript.AST;

import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.CodeGenerator;

public class LocalNode extends TypedNode {
	/* frame[$Index] (or TermToken->text) */
	KToken TermToken;
	int Index;

	LocalNode(KToken TermToken, int Index) {
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
