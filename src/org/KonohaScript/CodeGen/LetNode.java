package org.KonohaScript.CodeGen;

import org.KonohaScript.KToken;

public class LetNode extends TypedNode {
	KToken TermToken;
	int Index;
	TypedNode Right;
	TypedNode Block;

	/* let frame[Index] = Right in Block end*/
	LetNode(KToken TermToken, int Index, TypedNode Right, BlockNode Block) {
		this.TermToken = TermToken;
		this.Index     = Index;
		this.Right     = Right;
		this.Block     = Block;
	}
}
