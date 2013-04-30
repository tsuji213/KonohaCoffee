/****************************************************************************
 * Copyright (c) 2012, the Konoha project authors. All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ***************************************************************************/

package org.KonohaScript.GrammarSet;

import java.util.ArrayList;

import org.KonohaScript.KNameSpace;
import org.KonohaScript.KToken;
import org.KonohaScript.KonohaParserConst;
import org.KonohaScript.LexicalConverter;
import org.KonohaScript.UntypedNode;
import org.KonohaScript.KonohaDebug;

public final class MiniKonoha implements KonohaParserConst {
	// Token
	public int TokenFunc(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		System.out.println("TokenFunc");
		return -1;
	}

	public int WhiteSpaceToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		for (; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isWhitespace(ch)) {
				break;
			}
		}
		return pos;
	}
	
	public int SingleSymbolToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		KToken token = new KToken(SourceText.substring(pos, pos + 1));
		ParsedTokenList.add(token);
		return pos + 1;
	}

	public int SymbolToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		int start = pos;
		for (; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '_') {
				break;
			}
		}
		KToken token = new KToken(SourceText.substring(start, pos));
		ParsedTokenList.add(token);
		return pos;
	}

	public int NumberLiteralToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		int start = pos;
		for (; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isDigit(ch)) {
				break;
			}
		}
		KToken token = new KToken(SourceText.substring(start, pos));
		token.ResolvedSyntax = ns.GetSyntax("$IntLiteral");
		ParsedTokenList.add(token);
		return pos;
	}

	public int StringLiteralToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		int start = pos + 1;
		char prev = '"';
		pos = start;
		while (pos < SourceText.length()) {
			char ch = SourceText.charAt(pos);
			if (ch == '"' && prev == '\\') {
				KToken token = new KToken(SourceText.substring(start, pos - start));
				token.ResolvedSyntax = ns.GetSyntax("$StringLiteral");
				ParsedTokenList.add(token);
				return pos + 1;
			}
			if (ch == '\n') {
				KToken token = new KToken(SourceText.substring(start, pos - start));
				ns.Message(Error, token, "expected \" to close the string literal");
				ParsedTokenList.add(token);
				return pos;
			}
			pos = pos + 1;
			prev = ch;
		}
		return pos;
	}

	// Macro

	public int OpenParenthesisMacro(LexicalConverter lex, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		ArrayList<KToken> GroupList = new ArrayList<KToken>();
		KToken BeginToken = SourceList.get(BeginIdx);
		GroupList.add(BeginToken);
		int nextIdx = lex.Do(SourceList, BeginIdx + 1, EndIdx, GroupList);
		KToken lastToken = GroupList.get(GroupList.size()-1);
		if (!lastToken.EqualsText(")")) { // ERROR
			lastToken.SetErrorMessage("must close )");
		}
		else {
			KToken GroupToken = new KToken("( ... )", BeginToken.uline);
			GroupToken.SetGroup(lex.GetSyntax("$()"), GroupList);
			BufferList.add(GroupToken);
		}
		KonohaDebug.P("BeginIdx=" + BeginIdx + ",nextIdx="+nextIdx + ",EndIdx="+EndIdx);
		return nextIdx;
	}

	public int CloseParenthesisMacro(LexicalConverter lex, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		BufferList.add(SourceList.get(BeginIdx));
		return BreakPreProcess;
	}

	// Parse

	int ParseIfNode(UntypedNode node, ArrayList<KToken> tokens, int BeginIdx, int opIdx, int EndIdx) {
		int nextIdx = node.MatchCondition("if", tokens, BeginIdx + 1, EndIdx);
		nextIdx = node.MatchSingleBlock(")", tokens, nextIdx, EndIdx);
		nextIdx = node.MatchSymbol(null, "else", tokens, nextIdx, EndIdx);
		nextIdx = node.MatchSingleBlock("else", tokens, nextIdx, EndIdx);
		return nextIdx;
	}

	// TypedNode TypeIfNode(KGamma gma, UntypedNode node) {
	// return null;
	// }

	public void LoadDefaultSyntax(KNameSpace ns) {
		ns.AddTokenFunc(" \t", this, "WhiteSpaceToken");		
		ns.AddTokenFunc("(){}[],;+-*/%=", this, "SingleSymbolToken");
		ns.AddTokenFunc("1", this, "NumberLiteralToken");
		ns.AddTokenFunc("a", this, "SymbolToken");
		ns.AddTokenFunc("\"", this, "StringLiteralToken");
		// Macro
		ns.AddMacroFunc("(", this, "OpenParenthesisMacro");
		ns.AddMacroFunc(")", this, "CloseParenthesisMacro");
		//ns.AddSymbol(symbol, constValue);
	}
}
