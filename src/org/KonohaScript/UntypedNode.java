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

public class UntypedNode implements KonohaParserConst {	
	UntypedNode        Parent;
	KNameSpace         RootNodeNameSpace;
	KSyntax            Syntax;
	KToken             KeyToken;
	ArrayList<Object>  NodeList;
	
	UntypedNode(KNameSpace ns) {
		this.RootNodeNameSpace = ns;
	}
	
	UntypedNode AddNode(UntypedNode node) {
		return null;
	}

	UntypedNode AddParsedNode(UntypedNode node) {
		return null;
	}
		
	int ExpectedAfter(String beforeToken, ArrayList<KToken> tokens, int BeginIdx, String nextToken) {
		if(beforeToken != null) {
			KeyToken = new KToken(nextToken + " is expected after " + beforeToken);
			if(0 < BeginIdx - 1) {
				KeyToken.uline = tokens.get(BeginIdx-1).uline;
			}
		}
		return -1;
	}

	int ParseNode(String beforeToken, ArrayList<KToken> tokens, int BeginIdx, int EndIdx, int level) {
		if(BeginIdx < EndIdx) {
			int nextIdx = ParseBlockLevel(tokens, BeginIdx, EndIdx, level);
			if(nextIdx == NoMatch) {
				nextIdx = ParseStatementLevel(tokens, BeginIdx, EndIdx, level);
			}
			if(nextIdx == NoMatch) {
				nextIdx = ParseExpressionLevel(tokens, BeginIdx, EndIdx, level);
			}
			return nextIdx;
		}
		return ExpectedAfter(beforeToken, tokens, BeginIdx, "expression");
	}

	int ParseBlockLevel(ArrayList<KToken> tokenList, int BeginIdx, int EndIdx, int level) {
		int nextIdx = NoMatch;
		if(level == BlockLevel) {
			int i = BeginIdx, start = i;
			while(i < EndIdx) {
				KToken tk = tokenList.get(i);
				if(tk.ResolvedSyntax.precedence_op2 == KSyntax.Precedence_CStyleStatementEnd) {
					if(start < i) {
						AddNode(RootNodeNameSpace.ParseNewNode(null, tokenList, start, i, StatementLevel));
						nextIdx = EndIdx;
					}
					start = i + 1;
				}
				i++;
			}
			if(start < EndIdx) {
				AddNode(RootNodeNameSpace.ParseNewNode(null, tokenList, start, EndIdx, StatementLevel));			
				nextIdx = EndIdx;
			}
		}
		return nextIdx;
	}

	int ParseStatementLevel(ArrayList<KToken> tokenList, int BeginIdx, int EndIdx, int level) {
		int nextIdx = NoMatch;
		if(level <= StatementLevel) {
			KSyntax syntax = RootNodeNameSpace.GetSyntax("");
			while(syntax != null) {
				nextIdx = syntax.InvokeParseFunc(this, tokenList, BeginIdx, BeginIdx, EndIdx);
				if(nextIdx != NoMatch) {
					this.Syntax = syntax;
					return nextIdx;
				}
				syntax = syntax.Pop();
			}
			
		}
		return nextIdx;
	}

	int ParseExpressionLevel(ArrayList<KToken> tokenList, int BeginIdx, int EndIdx, int level) {
		int opIdx = FindOperator(tokenList, BeginIdx, EndIdx);
		KToken keyOperator = tokenList.get(opIdx);
		//DBG_P("KeyOperator >>>>>>>> %d<%d<%d, %s", BeginIdx, opIdx, EndIdx, KToken_t(keyOperator));
		KSyntax syntax = RootNodeNameSpace.GetSyntax(keyOperator.ParsedText);
		int nextIdx = NoMatch;
		while(syntax != null) {
			nextIdx = syntax.InvokeParseFunc(this, tokenList, BeginIdx, BeginIdx, EndIdx);
			if(nextIdx != NoMatch) {
				this.Syntax = syntax;
				return nextIdx;
			}
			syntax = syntax.Pop();
		}
		
		return nextIdx;
	}

	int FindOperator(ArrayList<KToken> tokenList, int BeginIdx, int EndIdx) {
		boolean isPrePosition = true;
		int opIdx = BeginIdx, i, precedence = 0;
		KToken typeToken = null;
		for(i = BeginIdx; i < EndIdx; i++) {
			KToken tk = tokenList.get(i);
			KSyntax syntax = tk.ResolvedSyntax;
			if(isPrePosition) {
				if(syntax.precedence_op1 > 0) {
					if(precedence < syntax.precedence_op1) {
						precedence = syntax.precedence_op1;
						opIdx = i;
					}
					continue;
				}
				isPrePosition = false;
			}
			else {
				if(syntax.precedence_op2 > 0) {
					if(syntax.IsTypeSuffix() && typeToken != null) {
						continue;
					}
					if(precedence < syntax.precedence_op2 || (precedence == syntax.precedence_op2 && syntax.IsLeftJoinOperator())) {
						precedence = syntax.precedence_op2;
						opIdx = i;
					}
					if(syntax.IsOperatorSuffix()) {
						isPrePosition = true;
					}
				}
			}
			typeToken = (tk.ResolvedObject instanceof KClass) ? tk : null;
		}
		return opIdx;
	}
	
	// Matcher
	
	public int MatchCondition(String prev, ArrayList<KToken> tokens, int BeginIdx, int EndIdx) {
		if(BeginIdx == -1) return -1;
		if(BeginIdx < EndIdx) {
			KToken token = tokens.get(BeginIdx);
			if(token.ResolvedSyntax.equals("$()")) {
				AddParsedNode(RootNodeNameSpace.ParseNewGroupNode("(", token, ExpressionLevel));
				return BeginIdx + 1;
			}
		}
		return ExpectedAfter(prev, tokens, BeginIdx, "(");
	}

	public int MatchExpression(String prev, ArrayList<KToken> tokens, int BeginIdx, int EndIdx) {
		if(BeginIdx == -1) return -1;
		AddParsedNode(RootNodeNameSpace.ParseNewNode(prev, tokens, BeginIdx, EndIdx, StatementLevel));
		return EndIdx;
	}
	
	public int MatchSingleBlock(String prev, ArrayList<KToken> tokens, int BeginIdx, int EndIdx) {
		if(BeginIdx == -1) return -1;
		if(BeginIdx < EndIdx) {
			KToken token = tokens.get(BeginIdx);
			if(token.ResolvedSyntax.equals("${}")) {
				AddParsedNode(RootNodeNameSpace.ParseNewGroupNode(null, token, BlockLevel));
				return BeginIdx + 1;
			}
			return MatchExpression(prev, tokens, BeginIdx, EndIdx);
		}
		return ExpectedAfter(prev, tokens, BeginIdx, "{");
	}

	public int MatchSymbol(String prev, String symbol, ArrayList<KToken> tokens, int BeginIdx, int EndIdx) {
		if(BeginIdx == -1) return -1;
		if(BeginIdx < EndIdx) {
			KToken token = tokens.get(BeginIdx);
			if(token.equals(symbol)) {
				return BeginIdx + 1;
			}
		}
		return ExpectedAfter(prev, tokens, BeginIdx, symbol);
	}
	
}
