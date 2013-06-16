package org.KonohaScript.PegParser;

import org.KonohaScript.KonohaConst;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.Parser.KonohaGrammar;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.Parser.TypeEnv;
import org.KonohaScript.Parser.UntypedNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public final class KonohaIntegerSyntax extends KonohaGrammar implements KonohaConst {
	public int KonohaIntegerLiteralToken(KonohaNameSpace ns, String SourceText, int pos, TokenList ParsedTokenList) {
		int start = pos;
		for(; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isDigit(ch)) {
				break;
			}
		}
		KonohaToken token = new KonohaToken(SourceText.substring(start, pos));
		token.ResolvedSyntax = ns.GetSyntax("$IntegerLiteral");
		ParsedTokenList.add(token);
		return pos;
	}

	public int ParseIntegerLiteral(UntypedNode Node, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		return BeginIdx + 1;
	}

	public TypedNode TypeIntegerLiteral(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaToken Token = UNode.KeyToken;
		return new ConstNode(Gamma.IntType, Token, Integer.valueOf(Token.ParsedText));
	}

	@Override
	public void LoadDefaultSyntax(KonohaNameSpace NameSpace) {
		NameSpace.AddTokenFunc("1", this, "KonohaIntegerLiteralToken");
		NameSpace.DefineSyntax("$IntegerLiteral", KonohaConst.Term, this, "IntegerLiteral");
	}
}
