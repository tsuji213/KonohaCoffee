package org.KonohaScript;

import java.util.ArrayList;

public final class LexicalConverter implements KonohaParserConst {
	
	public boolean TopLevel;
	public boolean SkipIndent;
	int LastIndent;
	
	public LexicalConverter(KNameSpace ns, boolean TopLevel, boolean SkipIndent) {
		this.ns = ns;
		this.TopLevel = TopLevel;
		this.SkipIndent = SkipIndent;
		LastIndent = 0;
	}

	KNameSpace ns;
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

	static int Indent(String Text) {
		int indent = 0;
		for(int i = 0; i < Text.length(); i++) {
			char ch = Text.charAt(i);
			if(ch == '\t') {
				indent = indent + 4; continue;
			}
			if(ch == ' ') {
				indent = indent + 1; continue;
			}
			break;
		}
		return indent;
	}
	
	public int Do(ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		int c = BeginIdx;
		while (c < EndIdx) {
			KToken Token = SourceList.get(c);
			if (Token.ResolvedSyntax == null) {
				KFunc macro = ns.GetMacroFunc(Token.ParsedText);
				//KonohaDebug.P("symbol='"+Token.ParsedText+"', macro="+macro);
				if (macro != null) {
					int nextIdx = macro.InvokeMacroFunc(this, SourceList, c, EndIdx, BufferList);
					if (nextIdx == BreakPreProcess) {
						return c + 1;
					}
					c = nextIdx;
					continue;
				}
				ResolveTokenSyntax(Token);
			}
			assert (Token.ResolvedSyntax != null);
			c = c + 1;
			if(Token.ResolvedSyntax == KSyntax.IndentSyntax) {
				if(this.SkipIndent) continue;
				LastIndent = Indent(Token.ParsedText);
			}
			BufferList.add(Token);
		}
		return c;
	}

}
