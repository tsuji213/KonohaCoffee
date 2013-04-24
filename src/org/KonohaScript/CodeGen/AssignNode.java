package org.KonohaScript.CodeGen;

import org.KonohaScript.KToken;

public class AssignNode extends TypedNode {
	KToken TermToken;
	int Index;
	TypedNode Right;

	/* frame[Index] = Right */
	AssignNode(KToken TermToken, int Index, TypedNode Right) {
		this.TermToken = TermToken;
		this.Index     = Index;
		this.Right     = Right;
	}
}