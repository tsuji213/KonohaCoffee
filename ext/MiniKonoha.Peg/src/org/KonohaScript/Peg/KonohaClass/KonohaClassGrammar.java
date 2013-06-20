package org.KonohaScript.Peg.KonohaClass;

import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.Grammar.MiniKonohaGrammar;
import org.KonohaScript.Parser.KonohaGrammar;
import org.KonohaScript.PegParser.PegParser;

public class KonohaClassGrammar extends KonohaGrammar {
	@Override
	public void LoadDefaultSyntax(KonohaNameSpace NameSpace) {
		new MiniKonohaGrammar().LoadDefaultSyntax(NameSpace);
		//FIXME
		PegParser PegParser = (PegParser) NameSpace.Parser;
		PegParser.AddSyntax(NameSpace, new TopLevelDefinitionSyntax(), new ClassDefinitionSyntax(), false);
	}
}
