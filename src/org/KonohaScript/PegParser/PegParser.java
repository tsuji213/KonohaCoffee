package org.KonohaScript.PegParser;

import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.Parser.KonohaParser;
import org.KonohaScript.Parser.UntypedNode;

public class PegParser extends KonohaParser {
	public SyntaxModule	Module;

	public PegParser(KonohaNameSpace NameSpace) {
		this.Module = new SyntaxModule(NameSpace);
	}

	@Override
	public UntypedNode ParseNewNode(KonohaNameSpace ns, UntypedNode PrevNode, TokenList TokenList, int BeginIdx, int EndIdx,
			int ParseOption) {
		UntypedNode UNode = this.Module.Parse(TokenList, BeginIdx, EndIdx);
		return UNode;
	}

	//FIXME
	public void AddSyntax(KonohaNameSpace NameSpace, SyntaxTemplate ParentGrammer, SyntaxTemplate Grammer, boolean TopLevel) {
		this.Module.AddSyntax(ParentGrammer, Grammer, TopLevel);
	}

}
