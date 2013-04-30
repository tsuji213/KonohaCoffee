package org.KonohaScript;

import java.util.ArrayList;

public final class LexicalConverter implements KonohaParserConst {

	KNameSpace ns;
	public LexicalConverter(KNameSpace ns) {
		this.ns = ns;
	}

	public KSyntax GetSyntax(String symbol) {
		return ns.GetSyntax(symbol);
	}
	
	private void ResolveTokenSyntax(KToken token) {
		token.ResolvedObject = ns.GetSymbol(token.ParsedText);
		if(token.ResolvedObject == null) {
			token.ResolvedSyntax = KSyntax.SymbolSyntax;
		}
		else if(token.ResolvedObject instanceof KClass) {
			token.ResolvedSyntax = KSyntax.TypeSyntax;
		}
		else {
			token.ResolvedSyntax = KSyntax.ConstSyntax;
		}
	}

	public int Do(ArrayList<KToken> SourceList, int beginIdx, int endIdx, ArrayList<KToken> BufferList) {
		int c = beginIdx;
		while (c < endIdx) {
			KToken Token = SourceList.get(c);
			if (Token.ResolvedSyntax == null) {
				KFunc macro = ns.GetMacroFunc(Token.ParsedText);
				KonohaDebug.P("symbol='"+Token.ParsedText+"', macro="+macro);
				if (macro != null) {
					int nextIdx = macro.InvokeMacroFunc(this, SourceList, c, endIdx, BufferList);
					if (nextIdx == BreakPreProcess) {
						return c + 1;
					}
					c = nextIdx;
					continue;
				}
				ResolveTokenSyntax(Token);
			}
			assert (Token.ResolvedSyntax != null);
			BufferList.add(Token);
			c = c + 1;
		}
		return c;
	}

}
