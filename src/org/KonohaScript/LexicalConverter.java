package org.KonohaScript;

import java.util.ArrayList;

public final class LexicalConverter implements KonohaConst {
	
	public boolean TopLevel;
	public boolean SkipIndent;
	int LastIndent;
	
	public LexicalConverter(KonohaNameSpace ns, boolean TopLevel, boolean SkipIndent) {
		this.ns = ns;
		this.TopLevel = TopLevel;
		this.SkipIndent = SkipIndent;
		LastIndent = 0;
	}

	KonohaNameSpace ns;
	
	public KonohaSyntax GetSyntax(String Symbol) {
		return ns.GetSyntax(Symbol, this.TopLevel);
	}
	
	public void ResolveTokenSyntax(KonohaToken Token) {
		Token.ResolvedObject = ns.GetSymbol(Token.ParsedText);
		if(Token.ResolvedObject == null) {
			Token.ResolvedSyntax = ns.GetSyntax("$Symbol", this.TopLevel);
		}
		else if(Token.ResolvedObject instanceof KonohaType) {
			Token.ResolvedSyntax = ns.GetSyntax("$Type", this.TopLevel);
		}
		else if(Token.ResolvedObject instanceof KonohaSyntax) {
			Token.ResolvedSyntax = (KonohaSyntax)Token.ResolvedObject;
		}
		else {
			Token.ResolvedSyntax = ns.GetSyntax("$Const", this.TopLevel);
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
	
	public int Do(ArrayList<KonohaToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KonohaToken> BufferList) {
		int c = BeginIdx;
		while (c < EndIdx) {
			KonohaToken Token = SourceList.get(c);
			if(Token.ResolvedSyntax == null) {
				KonohaFunc Macro = ns.GetMacro(Token.ParsedText, this.TopLevel);
				//KonohaDebug.P("symbol='"+Token.ParsedText+"', macro="+Macro);
				if(Macro != null) {
					int nextIdx = Macro.InvokeMacroFunc(this, SourceList, c, EndIdx, BufferList);
					if(nextIdx == BreakPreProcess) {
						return c + 1;
					}
					c = nextIdx;
					continue;
				}
				ResolveTokenSyntax(Token);
			}
			assert (Token.ResolvedSyntax != null);
			c = c + 1;
			if(Token.ResolvedSyntax == KonohaSyntax.IndentSyntax) {
				if(this.SkipIndent) continue;
				LastIndent = Indent(Token.ParsedText);
			}
			BufferList.add(Token);
		}
		return c;
	}

}
