package org.KonohaScript.Parser;

import org.KonohaScript.KonohaNameSpace;

public class KonohaGrammar {

	public void LoadDefaultSyntax(KonohaNameSpace NameSpace) {
		NameSpace.LoadParser(new KonohaParser());
	}

}
