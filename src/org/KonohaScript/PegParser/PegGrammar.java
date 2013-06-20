package org.KonohaScript.PegParser;

import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.Parser.KonohaGrammar;

public class PegGrammar extends KonohaGrammar {
	@Override
	public void InitGrammarProfile(KonohaNameSpace NameSpace) {
		if(NameSpace.Parser == null) {
			NameSpace.LoadParser(new PegParser(NameSpace));
		}
	}
}
