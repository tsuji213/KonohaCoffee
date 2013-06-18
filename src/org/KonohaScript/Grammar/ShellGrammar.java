package org.KonohaScript.Grammar;

import org.KonohaScript.KonohaConst;
import org.KonohaScript.KonohaDebug;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.Parser.KonohaGrammar;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.Parser.TypeEnv;
import org.KonohaScript.Parser.UntypedNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public final class ShellGrammar extends KonohaGrammar implements KonohaConst {

	MiniKonohaGrammar MiniKonoha = new MiniKonohaGrammar();

	// $(ls -la | grep .txt)
	public int ShellToken(KonohaNameSpace ns, String SourceText, int pos, TokenList ParsedTokenList) {
		int start = pos;
		int level = 1;
		pos++;
		if(SourceText.charAt(pos) != '('){
			return -1;
		}
		for(; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(ch == '\\') {
				pos++;
				continue;
			}
			if(ch == '(') {
				level++;
			}else if(ch == ')') {
				level--;
				if(level < 0){
					break;
				}
			}
		}
		KonohaToken Token = new KonohaToken(SourceText.substring(start, pos));
		KonohaDebug.P("Shell: " + Token.ParsedText.substring(2, Token.ParsedText.length() - 2));
		Token.ResolvedSyntax = ns.GetSyntax("$Shell");
		ParsedTokenList.add(Token);
		return pos;
	}

	public int ParseShell(UntypedNode UNode, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return NoMatch;
	}

	public TypedNode TypeShell(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return null;
	}

	@Override
	public void LoadDefaultSyntax(KonohaNameSpace NameSpace) {
		MiniKonoha.LoadDefaultSyntax(NameSpace);

		//FIXME Move to ShellGrammar
		NameSpace.AddTokenFunc("$", this, "ShellToken");
		NameSpace.DefineSyntax("$Shell", Term, this, "Shell");
	}
}
