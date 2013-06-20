package org.KonohaScript.Peg.KonohaClass;

import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.Parser.KonohaGrammar;
import org.KonohaScript.Peg.MiniKonoha.MiniKonohaPegGrammar;
import org.KonohaScript.PegParser.PegParser;

public class KonohaClassGrammar extends KonohaGrammar {
	@Override
	public void LoadDefaultSyntax(KonohaNameSpace NameSpace) {
		new MiniKonohaPegGrammar().LoadDefaultSyntax(NameSpace);
		//FIXME
		PegParser PegParser = (PegParser) NameSpace.Parser;
		PegParser.AddSyntax(NameSpace, new TopLevelDefinitionSyntax(), new ClassDefinitionSyntax(), false);
	}
}
