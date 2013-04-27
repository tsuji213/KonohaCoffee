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

	public int Do(ArrayList<KToken> tokenList, int beginIdx, int endIdx, ArrayList<KToken> BufferList) {
		int c = beginIdx;
		while (c < endIdx) {
			KToken tk = tokenList.get(c);
			if (tk.ResolvedSyntax == null) {
				KFunc macro = ns.GetMacroFunc(tk.ParsedText);
				if (macro != null) {
					int nextIdx = macro.InvokeMacroFunc(this, tokenList, c, endIdx, BufferList);
					if (nextIdx == BreakPreProcess) {
						return c + 1;
					}
					c = nextIdx;
					break;
				}
				ResolveTokenSyntax(tk);
			}
			assert (tk.ResolvedSyntax != null);
			BufferList.add(tk);
			c = c + 1;
		}
		return c;
	}

}
