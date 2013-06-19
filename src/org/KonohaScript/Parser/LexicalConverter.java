package org.KonohaScript.Parser;

import java.lang.reflect.InvocationTargetException;

import org.KonohaScript.KonohaFunc;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaType;
import org.KonohaScript.JUtils.KonohaConst;
import org.KonohaScript.KLib.TokenList;

public final class LexicalConverter implements KonohaConst {

	public boolean	TopLevel;
	public boolean	SkipIndent;
	int				LastIndent;

	public LexicalConverter(KonohaNameSpace ns, boolean TopLevel, boolean SkipIndent) {
		this.ns = ns;
		this.TopLevel = TopLevel;
		this.SkipIndent = SkipIndent;
		this.LastIndent = 0;
	}

	KonohaNameSpace	ns;

	public KonohaSyntax GetSyntax(String Symbol) {
		return this.ns.GetSyntax(Symbol, this.TopLevel);
	}

	public void ResolveTokenSyntax(KonohaToken Token) {
		Token.ResolvedObject = this.ns.GetSymbol(Token.ParsedText);
		if(Token.ResolvedObject == null) {
			Token.ResolvedSyntax = this.ns.GetSyntax("$Symbol", this.TopLevel);
		} else if(Token.ResolvedObject instanceof KonohaType) {
			Token.ResolvedSyntax = this.ns.GetSyntax("$Type", this.TopLevel);
		} else if(Token.ResolvedObject instanceof KonohaSyntax) {
			Token.ResolvedSyntax = (KonohaSyntax) Token.ResolvedObject;
		} else {
			Token.ResolvedSyntax = this.ns.GetSyntax("$Const", this.TopLevel);
		}
	}

	static int Indent(String Text) {
		int indent = 0;
		for(int i = 0; i < Text.length(); i++) {
			char ch = Text.charAt(i);
			if(ch == '\t') {
				indent = indent + 4;
				continue;
			}
			if(ch == ' ') {
				indent = indent + 1;
				continue;
			}
			break;
		}
		return indent;
	}

	public int InvokeMacroFunc(KonohaFunc Macro, TokenList tokenList, int BeginIdx, int EndIdx, TokenList bufferToken) {
		try {
			Integer next = (Integer) Macro.method.invoke(Macro.callee, this, tokenList, BeginIdx, EndIdx, bufferToken);
			return next.intValue();
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EndIdx;
	}

	public int Do(TokenList SourceList, int BeginIdx, int EndIdx, TokenList BufferList) {
		int c = BeginIdx;
		while(c < EndIdx) {
			KonohaToken Token = SourceList.get(c);
			if(Token.ResolvedSyntax == null) {
				KonohaFunc Macro = this.ns.GetMacro(Token.ParsedText, this.TopLevel);
				//KonohaDebug.P("symbol='"+Token.ParsedText+"', macro="+Macro);
				if(Macro != null) {
					int nextIdx = this.InvokeMacroFunc(Macro, SourceList, c, EndIdx, BufferList);
					if(nextIdx == BreakPreProcess) {
						return c + 1;
					}
					c = nextIdx;
					continue;
				}
				this.ResolveTokenSyntax(Token);
			}
			assert (Token.ResolvedSyntax != null);
			c = c + 1;
			if(Token.ResolvedSyntax == KonohaSyntax.IndentSyntax) {
				if(this.SkipIndent)
					continue;
				this.LastIndent = Indent(Token.ParsedText);
			}
			BufferList.add(Token);
		}
		return c;
	}

}
