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

package org.KonohaScript;
import java.util.ArrayList;

public final class KonohaSyntax implements KonohaParserConst {
	// Token 
	public int TokenFunc(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		System.out.println("TokenFunc");
		return -1;
	}

	public int SingleSymbolTokenFunc(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		KToken token = new KToken(SourceText.substring(pos, pos + 1));
		ParsedTokenList.add(token);
		return pos+1;
	}

	public int TextLiteralTokenFunc(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		int start = pos + 1;
		char prev = '"';
		pos = start;
		while(pos < SourceText.length()) {
			char ch = SourceText.charAt(pos); 
			if(ch == '"' && prev == '\\') {
				KToken token = new KToken(SourceText.substring(start, pos - start));
				token.ResolvedSyntax = ns.GetSyntax("$Text");
				ParsedTokenList.add(token);
				return pos+1;
			}
			if(ch == '\n') {
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
	
	// Parse
	

	int ParseIfNode(UntypedNode node, ArrayList<KToken> tokens, int beginIdx, int opIdx, int endIdx) {
		int nextIdx = node.MatchCondition("if", tokens, beginIdx + 1, endIdx);
		nextIdx = node.MatchSingleBlock(")", tokens, nextIdx, endIdx);
		nextIdx = node.MatchSymbol(null, "else", tokens, nextIdx, endIdx);
		nextIdx = node.MatchSingleBlock("else", tokens, nextIdx, endIdx);
		return nextIdx;
	}

//	TypedNode TypeIfNode(KGamma gma, UntypedNode node) {
//		return null;
//	}
	
	void LoadDefaultSyntax(KNameSpace ns) {
		ns.AddTokenFunc("abc", this, "SingleSymbolTokenFunc");
		ns.AddTokenFunc("\"", this, "TextLiteralTokenFunc");
	}
}
