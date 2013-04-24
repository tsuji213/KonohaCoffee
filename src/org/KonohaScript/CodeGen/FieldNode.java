package org.KonohaScript.CodeGen;

import org.KonohaScript.KToken;

public class FieldNode extends TypedNode {
	/* frame[Index][Xindex] (or ($TermToken->text)[Xindex] */
	KToken TermToken;
	int Index;
	int Xindex;
	FieldNode(KToken TermToken, int Index, int Xindex) {
		this.TermToken = TermToken;
		this.Index  = Index;
		this.Xindex = Xindex;
	}
}
