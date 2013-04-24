package org.KonohaScript.CodeGen;
import org.KonohaScript.KToken;

public class LocalNode extends TypedNode {
	/* frame[$Index] (or TermToken->text) */
	KToken TermToken;
	int Index;
	LocalNode(KToken TermToken, int Index) {
		this.TermToken = TermToken;
		this.Index     = Index;
	}
}
