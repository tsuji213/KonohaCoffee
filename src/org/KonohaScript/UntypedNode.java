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
import org.KonohaScript.SyntaxTree.*;

public class UntypedNode implements KonohaParserConst {	
	UntypedNode               Parent;
	UntypedNode               PreviousNode;
	UntypedNode               NextNode;	
	public KNameSpace         NodeNameSpace;
	public KSyntax            Syntax;
	public KToken             KeyToken;
	ArrayList<Object>         NodeList;
	
//	UntypedNode(KNameSpace ns) {
//		this.NodeNameSpace = ns;
//	}
	
	public UntypedNode(KNameSpace ns, KToken KeyToken) {
		this.NodeNameSpace = ns;
		this.KeyToken = KeyToken;
		this.Syntax = KeyToken.ResolvedSyntax;
	}
	
	public void LinkNode(UntypedNode Node) {
		Node.PreviousNode = this;
		this.NextNode = Node;
	}
	
//	public UntypedNode AddNode(UntypedNode node) {
//		return null;
//	}
//

	public UntypedNode AddParsedNode(UntypedNode Node) {
		if(NodeList == null) {
			NodeList = new ArrayList<Object>();
		}
		NodeList.add(Node);
		return Node;
	}

	public UntypedNode AddParsedNode(int Index, UntypedNode Node) {
		if(NodeList == null) {
			NodeList = new ArrayList<Object>();
		}
		NodeList.add(Node);
		return Node;
	}
	
	public void SetErrorMessage(KToken Token, String Message) {
		if(Token != null) {
			KeyToken = Token;
		}
		KeyToken.SetErrorMessage(Message);
		this.Syntax = KSyntax.ErrorSyntax;
	}
	
	public static UntypedNode NewNullNode(KNameSpace ns, ArrayList<KToken> TokenList, int BeginIdx) {
		KToken EmptyToken = new KToken("");
		EmptyToken.uline = TokenList.get(BeginIdx-1).uline;
		EmptyToken.ResolvedSyntax = KSyntax.EmptySyntax;
		return new UntypedNode(ns, EmptyToken);
	}
	
	public int ParseToken(KToken KeyToken, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx) {
		this.KeyToken = KeyToken;
		KSyntax Syntax = KeyToken.ResolvedSyntax;
		int NextIdx = NoMatch;
		while(Syntax != null) {
			this.Syntax = Syntax;
			NextIdx = Syntax.InvokeParseFunc(this, TokenList, BeginIdx, EndIdx, (Syntax.ParentSyntax == null) ? 0 : HasNextPattern);
			if(NextIdx != NoMatch) {
				return NextIdx;
			}
			this.Syntax = null;
			this.NodeList = null;
			Syntax = Syntax.ParentSyntax;
		}
		SetErrorMessage(KeyToken, "undefined syntax: " + KeyToken.ParsedText);
		return EndIdx;
	}

	public final static int LeftTerm  = 0;
	public final static int RightTerm = 1;

	/* 1 + 2 * 3 */
	/* 1 * 2 + 3 */
	
	public static UntypedNode BinaryNode(KNameSpace ns, UntypedNode LeftNode, KToken KeyToken, UntypedNode RightNode)
	{
		if(RightNode.Syntax.IsBinaryOperator()) {
			if(LeftNode.Syntax.IsLeftJoin(RightNode.Syntax)) {
				UntypedNode NewNode = new UntypedNode(ns, KeyToken);
				NewNode.AddParsedNode(LeftTerm, LeftNode);
				NewNode.AddParsedNode(RightTerm, (UntypedNode)(RightNode.NodeList.get(LeftTerm)));
				RightNode.NodeList.set(LeftTerm, NewNode);
				return RightNode;
			}
		}
		UntypedNode NewNode = new UntypedNode(ns, KeyToken);
		NewNode.AddParsedNode(LeftTerm, LeftNode);
		NewNode.AddParsedNode(RightTerm, RightNode);
		return NewNode;
	}
	
	int ExpectedAfter(ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption, String NextSyntax) {
		if((ParseOption & AllowEmpty) != AllowEmpty) {
			this.SetErrorMessage(TokenList.get(BeginIdx-1), NextSyntax + " is expected after " + KeyToken.ParsedText);
			return EndIdx;
		}
		return ((ParseOption & AllowSkip) == AllowSkip) ? BeginIdx : NoMatch;
	}
	
	public static UntypedNode ParseNewNode2(KNameSpace ns, UntypedNode PrevNode, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		UntypedNode LeftNode = null;
		int NextIdx = BeginIdx;
		while(NextIdx < BeginIdx) {
			KToken KeyToken = TokenList.get(NextIdx);
			KSyntax Syntax = KeyToken.ResolvedSyntax;
			if(LeftNode == null) {
				if(Syntax.IsDelim()) {  // A ; B
					NextIdx++;
				}
				else {
					LeftNode = new UntypedNode(ns, null);
					NextIdx = LeftNode.ParseToken(KeyToken, TokenList, NextIdx, EndIdx);
				}
			}
			else {
				if(Syntax.IsDelim()) {  // A ; B
					LeftNode = new UntypedNode(ns, null);
					NextIdx = LeftNode.ParseToken(KeyToken, TokenList, NextIdx, EndIdx);
					if(PrevNode != null) {
						PrevNode.PreviousNode = LeftNode;
					}
					ParseNewNode2(ns, LeftNode, TokenList, NextIdx, EndIdx, AllowEmpty);
					return LeftNode;
				}
				if(Syntax.IsBinaryOperator()) {  // A + B
					UntypedNode RightNode = ParseNewNode2(ns, PrevNode, TokenList, NextIdx + 1, EndIdx, 0);
					LeftNode = BinaryNode(ns, LeftNode, KeyToken, RightNode);
				}
				if(Syntax.IsSuffixOperator()) { // A []
					NextIdx = LeftNode.ParseToken(KeyToken, TokenList, NextIdx, EndIdx);
				}
			}
			KonohaDebug.P("NextIdx="+ NextIdx);
		}
		return LeftNode;
	}

	public static UntypedNode ParseGroup(KNameSpace ns, KToken GroupToken, int ParseOption) {
		ArrayList<KToken> GroupList = GroupToken.GetGroupList();
		return ParseNewNode2(ns, null, GroupList, 1, GroupList.size() -1 , ParseOption);
	}
	
	public UntypedNode GetSuffixBodyNode() {
		if(NodeList == null) return null;
		if(NodeList.size() == 1) return this;
		UntypedNode ChildNode = new UntypedNode(NodeNameSpace, KeyToken);
		ChildNode.NodeList = NodeList;
		NodeList = null; // initalized
		AddParsedNode(0, ChildNode);
		return this;
	}
	
	public void AppendTokenList(String delim, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		assert(this.NodeList.size() == 1);
		int start = BeginIdx;
		for(int i = BeginIdx; i < EndIdx; i++) {
			KToken Token = TokenList.get(i);
			if(Token.EqualsText(delim)) {
				AddParsedNode(ParseNewNode2(NodeNameSpace, null, TokenList, start, i, ParseOption));
				start = i + 1;
			}
		}
		if(start < EndIdx) {
			AddParsedNode(ParseNewNode2(NodeNameSpace, null, TokenList, start, EndIdx, ParseOption));
		}
	}

	// Utils
	
	public final static boolean IsAllowEmpty(int ParseOption) {
		return ((ParseOption & AllowEmpty) == AllowEmpty);
	}

	public final static boolean IsAllowNoMatch(int ParseOption) {
		return ((ParseOption & HasNextPattern) == HasNextPattern);
	}
	
	public static int FindDelim(ArrayList<KToken> TokenList, int BeginIdx, int EndIdx) {
		int NextIdx = EndIdx;
		for(int i = BeginIdx+1; i < EndIdx; i++) {
			KToken Token = TokenList.get(i);
			if(Token.ResolvedSyntax.IsDelim()) {
				NextIdx = i;
				break;
			}
		}
		return NextIdx;
	}

	
	// Matcher	
	public int MatchCond(int Index, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		if(BeginIdx == -1) return -1;
		if(BeginIdx < EndIdx) {
			KToken GroupToken = TokenList.get(BeginIdx);
			if(GroupToken.ResolvedSyntax.equals("()")) {
				AddParsedNode(Index, ParseGroup(NodeNameSpace, GroupToken, 0));
				return BeginIdx + 1;
			}
		}
		return ExpectedAfter(TokenList, BeginIdx, EndIdx, ParseOption, "(");
	}

	public int MatchExpression(int Index, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		if(BeginIdx == -1) return -1;
		int NextIdx = EndIdx;
		for(int i = BeginIdx; i < EndIdx; i++) {
			if(TokenList.get(i).ResolvedSyntax.IsDelim()) {
				NextIdx = i;
			}
		}
		AddParsedNode(Index, ParseNewNode2(NodeNameSpace, null, TokenList, BeginIdx, NextIdx, ParseOption));
		return NextIdx+1;
	}
	
	public int MatchSingleBlock(int Index, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		if(BeginIdx == -1) return -1;
		if(BeginIdx < EndIdx) {
			KToken Token = TokenList.get(BeginIdx);
			if(Token.ResolvedSyntax.equals("{}")) {
				AddParsedNode(Index, ParseGroup(NodeNameSpace, Token, AllowEmpty|CreateNullNode));
				return BeginIdx + 1;
			}
			return MatchExpression(Index, TokenList, BeginIdx, EndIdx, ParseOption);
		}
		return ExpectedAfter(TokenList, BeginIdx, EndIdx, ParseOption, "{");
	}

	public int MatchKeyword(int Index, String Symbol, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		if(BeginIdx == -1) return -1;
		if(BeginIdx < EndIdx) {
			KToken Token = TokenList.get(BeginIdx);
			if(Token.EqualsText(Symbol)) {
				return BeginIdx + 1;
			}
		}
		return ExpectedAfter(TokenList, BeginIdx, EndIdx, ParseOption, Symbol);
	}

	public int MatchSyntax(int Index, KSyntax Syntax, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		if(BeginIdx == -1) return -1;
		if(BeginIdx < EndIdx) {
			KToken Token = TokenList.get(BeginIdx);
			if(Token.ResolvedSyntax == Syntax) {
				return BeginIdx + 1;
			}
		}
		return ExpectedAfter(TokenList, BeginIdx, EndIdx, ParseOption, Syntax.SyntaxName);
	}

	
	public final TypedNode TypeNodeAt(int Index, KGamma Gamma, KClass TypeInfo, int TypeCheckPolicy) {
		if(NodeList != null) {
			if(Index < NodeList.size()) {
				Object NodeObject = NodeList.get(Index);
//				if(NodeObject instanceof TypedNode) {
//					TypedNode NodeObject2 = KGamma.TypeCheckNode(Gamma, (TypedNode)NodeObject, TypeInfo, TypeCheckPolicy);
//					if(NodeObject != NodeObject2) {
//						NodeList.set(Index, NodeObject2);
//					}
//					return NodeObject2;
//				}
				if(NodeObject instanceof UntypedNode) {
					TypedNode NodeObject2 = KGamma.TypeCheckNode(Gamma, (UntypedNode)NodeObject, TypeInfo, TypeCheckPolicy);
					NodeList.set(Index, NodeObject2);
					return NodeObject2;
				}
			}
		}
		return new ErrorNode(TypeInfo, KeyToken, "syntax tree error: " + Index);
	}
	
}
