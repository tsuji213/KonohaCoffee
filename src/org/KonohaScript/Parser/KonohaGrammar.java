package org.KonohaScript.Parser;

import org.KonohaScript.KonohaNameSpace;

public class KonohaGrammar {

	public void LoadDefaultSyntax(KonohaNameSpace NameSpace) {
	}

	public void InitGrammarProfile(KonohaNameSpace NameSpace) {
		NameSpace.LoadParser(new KonohaParser());
	}

}
