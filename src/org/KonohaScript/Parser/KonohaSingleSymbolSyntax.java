package org.KonohaScript.Parser;

import org.KonohaScript.KonohaConst;
import org.KonohaScript.KonohaDebug;
import org.KonohaScript.KonohaGrammar;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.TypeEnv;
import org.KonohaScript.UntypedNode;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.SyntaxTree.TypedNode;

public final class KonohaSingleSymbolSyntax extends KonohaGrammar implements KonohaConst {
	public int ParseSingleSymbol(UntypedNode node, TokenList tokens, int BeginIdx, int OpIdx, int EndIdx) {
		return EndIdx;
	}

	public TypedNode TypeSingleSymbol(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return null;
	}

	public int SingleSymbolToken(KonohaNameSpace ns, String SourceText, int pos, TokenList ParsedTokenList) {
		// .(){}[]<>,;+-*/%=&|!
		String TokenName = "UnsupportedSymbol";
		int ch = SourceText.charAt(pos);
		switch (ch) {
		case '!':
			TokenName = "Exclamation";
			break;
		case '%':
			TokenName = "Percent";
			break;
		case '&':
			TokenName = "Ampersand";
			break;
		case '(':
			TokenName = "LBrace";
			break;
		case ')':
			TokenName = "RBrace";
			break;
		case '{':
			TokenName = "LCBrace";
			break;
		case '}':
			TokenName = "RCBrace";
			break;
		case '*': /* * *= */
			TokenName = "Star";
			break;
		case '+': /* + += ++ */
			TokenName = "Plus";
			break;
		case ',':
			TokenName = "Camma";
			break;
		case '-': /* - -= -- */
			TokenName = "Minus";
			break;
		case '.':
			TokenName = "Dot";
			break;
		case '/':
			TokenName = "Slash";
			break;
		case ':':
			TokenName = "Colon";
			break;
		case ';':
			TokenName = "SemiColon";
			break;
		case '<':
			TokenName = "LessThan";
			break;
		case '=':
			TokenName = "Equal";
			break;
		case '>':
			TokenName = "GraterThan";
			break;
		case '?':
			TokenName = "Question";
			break;
		case '[':
			TokenName = "LParenthesis";
			break;
		case ']':
			TokenName = "RParenthesis";
			break;
		case '^':
			TokenName = "Xor";
			break;
		case '|':
			TokenName = "Or";
			break;
		case '~':
			TokenName = "Invert";
			break;
		}
		KonohaToken Token = new KonohaToken("$" + TokenName);
		ParsedTokenList.add(Token);
		return pos + 1;
	}

	@Override
	public void LoadDefaultSyntax(KonohaNameSpace NameSpace) {
		NameSpace.AddTokenFunc(".(){}[]<>,;+-*/%=&|!", this, "SingleSymbolToken");
		NameSpace.DefineSyntax("$Invert", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Or", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$RCBrace", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Colon", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$SemiColon", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$GraterThan", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Question", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$LessThan", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Equal", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$LParenthesis", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Xor", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$RParenthesis", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Star", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Plus", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$LBrace", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$RBrace", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Dot", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Slash", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Camma", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Minus", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Exclamation", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Ampersand", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$Percent", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$SemiColon", 0, this, "SingleSymbol");
		NameSpace.DefineSyntax("$LCBrace", 0, this, "SingleSymbol");

	}
}