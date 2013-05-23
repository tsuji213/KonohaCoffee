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

import org.KonohaScript.*;
import org.KonohaScript.SyntaxTree.*;

public final class MiniKonoha implements KonohaConst {

	// Token
	public int WhiteSpaceToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		for (; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isWhitespace(ch)) {
				break;
			}
		}
		return pos;
	}

	public int IndentToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		int LineStart = pos + 1;
		pos = pos + 1;
		for (; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isWhitespace(ch)) {
				break;
			}
		}
		KToken Token = new KToken((LineStart < pos) ? SourceText.substring(LineStart, pos) : "");
		Token.ResolvedSyntax = KSyntax.IndentSyntax;
		ParsedTokenList.add(Token);			
		return pos;
	}
	
	public int SingleSymbolToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		KToken Token = new KToken(SourceText.substring(pos, pos + 1));
		ParsedTokenList.add(Token);
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
		KToken Token = new KToken(SourceText.substring(start, pos));
		ParsedTokenList.add(Token);
		return pos;
	}

	public int MemberToken(KNameSpace ns, String SourceText, int pos, ArrayList<KToken> ParsedTokenList) {
		int start = pos + 1;
		for (; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '_') {
				break;
			}
		}
		KToken Token = new KToken(SourceText.substring(start, pos));
		Token.ResolvedSyntax = KSyntax.MemberSyntax;
		ParsedTokenList.add(Token);
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
		token.ResolvedSyntax = ns.GetSyntax("$IntegerLiteral");
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

	public int OpenParenthesisMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		ArrayList<KToken> GroupList = new ArrayList<KToken>();
		KToken BeginToken = SourceList.get(BeginIdx);
		GroupList.add(BeginToken);
		int nextIdx = Lexer.Do(SourceList, BeginIdx + 1, EndIdx, GroupList);
		KToken LastToken = GroupList.get(GroupList.size()-1);
		if (!LastToken.EqualsText(")")) { // ERROR
			LastToken.SetErrorMessage("must close )");
		}
		else {
			KToken GroupToken = new KToken("( ... )", BeginToken.uline);
			GroupToken.SetGroup(Lexer.GetSyntax("()"), GroupList);
			BufferList.add(GroupToken);
		}
		return nextIdx;
	}

	public int CloseParenthesisMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		KToken Token = SourceList.get(BeginIdx);
		if(BufferList.size() == 0 || !BufferList.get(0).EqualsText("(")) {
			Token.SetErrorMessage("mismatched )");
		}
		BufferList.add(Token);
		return BreakPreProcess;
	}

	public int OpenBraceMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		ArrayList<KToken> GroupList = new ArrayList<KToken>();
		KToken BeginToken = SourceList.get(BeginIdx);
		GroupList.add(BeginToken);
		int nextIdx = Lexer.Do(SourceList, BeginIdx + 1, EndIdx, GroupList);
		KToken LastToken = GroupList.get(GroupList.size()-1);
		if (!LastToken.EqualsText("}")) { // ERROR
			LastToken.SetErrorMessage("must close }");
		}
		else {
			KToken GroupToken = new KToken("{ ... }", BeginToken.uline);
			GroupToken.SetGroup(Lexer.GetSyntax("{}"), GroupList);
			BufferList.add(GroupToken);
		}
		return nextIdx;
	}

	public int CloseBraceMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		KToken Token = SourceList.get(BeginIdx);
		if(BufferList.size() == 0 || !BufferList.get(0).EqualsText("{")) {
			Token.SetErrorMessage("mismatched }");
		}
		BufferList.add(Token);
		return BreakPreProcess;
	}

	public int OpenCloseBraceMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		int BraceLevel = 0;
		ArrayList<KToken> GroupList = new ArrayList<KToken>();
		for(int i = BeginIdx; i < EndIdx; i++) {
			KToken Token = SourceList.get(i);
			GroupList.add(Token);
			if(Token.EqualsText("{")) {
				BraceLevel++;
			}
			if(Token.EqualsText("}")) {
				BraceLevel--;
				if(BraceLevel == 0) {
					KToken GroupToken = new KToken("{ ... }", SourceList.get(BeginIdx).uline);
					GroupToken.SetGroup(Lexer.GetSyntax("${}"), GroupList);
					BufferList.add(GroupToken);
					return i+1;
				}
			}
		}
		SourceList.get(BeginIdx).SetErrorMessage("must close }");
		BufferList.add(SourceList.get(BeginIdx));
		return EndIdx;
	}

	public int OpenBracketMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		ArrayList<KToken> GroupList = new ArrayList<KToken>();
		KToken BeginToken = SourceList.get(BeginIdx);
		GroupList.add(BeginToken);
		int nextIdx = Lexer.Do(SourceList, BeginIdx + 1, EndIdx, GroupList);
		KToken LastToken = GroupList.get(GroupList.size()-1);
		if (!LastToken.EqualsText("]")) { // ERROR
			LastToken.SetErrorMessage("must close ]");
		}
		else {
			KToken GroupToken = new KToken("[ ... ]", BeginToken.uline);
			GroupToken.SetGroup(Lexer.GetSyntax("[]"), GroupList);
			BufferList.add(GroupToken);
		}
		return nextIdx;
	}

	public int CloseBracketMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		KToken Token = SourceList.get(BeginIdx);
		if(BufferList.size() == 0 || !BufferList.get(0).EqualsText("[")) {
			Token.SetErrorMessage("mismatched ]");
		}
		BufferList.add(Token);
		return BreakPreProcess;
	}

	public int MergeOperatorMacro(LexicalConverter Lexer, ArrayList<KToken> SourceList, int BeginIdx, int EndIdx, ArrayList<KToken> BufferList) {
		KToken Token = SourceList.get(BeginIdx);
		if(BufferList.size() > 0) {
			KToken PrevToken = BufferList.get(BufferList.size()-1);			
			if(PrevToken.ResolvedSyntax != null && PrevToken.uline == Token.uline) {
//				if(!Character.isLetter(PrevToken.ParsedText.charAt(0))) {
					String MergedOperator = PrevToken.ParsedText + Token.ParsedText;
					KSyntax Syntax = Lexer.GetSyntax(MergedOperator);
					if(Syntax != null) {
						PrevToken.ResolvedSyntax = Syntax;
						PrevToken.ParsedText = MergedOperator;
						return BeginIdx + 1;
					}
//				}
			}
		}
		Lexer.ResolveTokenSyntax(Token);
		BufferList.add(Token);
		return BeginIdx + 1;
	}
	
	// Parse And Type

	public int ParseIntegerLiteral(UntypedNode Node, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		return BeginIdx+1;
	}

	public TypedNode TypeIntegerLiteral(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		KToken Token = UNode.KeyToken;
		return new ConstNode(Gamma.IntType, Token, Integer.valueOf(Token.ParsedText));
	}

	public int ParseStringLiteral(UntypedNode Node, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		return BeginIdx+1;
	}

	public TypedNode TypeStringLiteral(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		KToken Token = UNode.KeyToken;
		return new ConstNode(Gamma.StringType, Token, Token.ParsedText /* FIXME: handling of escape sequence */);
	}

	public int ParseSymbol(UntypedNode Node, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		return BeginIdx+1;
	}

	public TypedNode TypeSymbol(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		TypeInfo = Gamma.GetLocalType(UNode.KeyToken.ParsedText);
		if(TypeInfo != null) {
			return new LocalNode(TypeInfo, UNode.KeyToken, UNode.KeyToken.ParsedText);
		}
		return Gamma.NewErrorNode(UNode.KeyToken, "undefined name: " + UNode.KeyToken.ParsedText);
	}

	public int ParseConst(UntypedNode Node, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		return BeginIdx+1;
	}

	public TypedNode TypeConst(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		KToken Token = UNode.KeyToken;
		return new ConstNode(Gamma.StringType, Token, Token.ParsedText /* FIXME: handling of resolved object */);
	}

	public int ParseUniaryOperator(UntypedNode Node, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		int NextIdx = EndIdx;
		for(int i = BeginIdx+1; i < EndIdx; i++) {
			KToken Token = TokenList.get(i);
			if(Token.ResolvedSyntax.IsBinaryOperator() || Token.ResolvedSyntax.IsSuffixOperator() || Token.ResolvedSyntax.IsDelim()) {
				NextIdx = i;
				break;
			}
		}
		Node.SetAtNode(0, UntypedNode.ParseNewNode(Node.NodeNameSpace, null, TokenList, BeginIdx+1, NextIdx, 0));
		return NextIdx;
	}

	public TypedNode TypeMethodCall(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		KonohaDebug.P("(>_<) typing method calls: " + UNode);
		ArrayList<Object> NodeList = UNode.NodeList;
		assert(NodeList.size() > 1);
		UntypedNode UntypedBaseNode = (UntypedNode)NodeList.get(0);
		if(UntypedBaseNode == null) {
			
		}
		if(UntypedBaseNode != null) {
			TypedNode BaseNode = KGamma.TypeCheckEachNode(Gamma, UntypedBaseNode, Gamma.VarType, 0);
			if(BaseNode.IsError()) return BaseNode;
			return TypeFindingMethod(Gamma, UNode, BaseNode, TypeInfo);
		}
		return null;
	}
	
	private TypedNode TypeFindingMethod(KGamma Gamma, UntypedNode UNode, TypedNode BaseNode, KClass TypeInfo) {
		ArrayList<Object> NodeList = UNode.NodeList;
		int ParamSize = NodeList.size() - 1;
		KToken KeyToken = UNode.KeyToken;
		KMethod Method = BaseNode.TypeInfo.LookupMethod(KeyToken.ParsedText, ParamSize);
		if(Method != null) {
			MethodCallNode WorkingNode = new MethodCallNode(Method.GetReturnType(BaseNode.TypeInfo), KeyToken, Method);
			WorkingNode.Append(BaseNode);
			return TypeMethodEachParam(Gamma, BaseNode.TypeInfo, WorkingNode, NodeList);
		}
		return Gamma.NewErrorNode(KeyToken, "undefined method: " + KeyToken.ParsedText + " in " + BaseNode.TypeInfo.ShortClassName);
	}

	private TypedNode TypeMethodEachParam(KGamma Gamma, KClass BaseType, MethodCallNode WorkingNode, ArrayList<Object> NodeList) {
		KMethod Method = WorkingNode.Method;
		for(int ParamIdx = 0; ParamIdx < NodeList.size() - 1; ParamIdx++) {
			KClass ParamType = Method.GetParamType(BaseType, ParamIdx);
			UntypedNode UntypedParamNode = (UntypedNode)NodeList.get(ParamIdx+1);
			TypedNode ParamNode;
			if(UntypedParamNode != null) {
				ParamNode = KGamma.TypeCheck(Gamma, UntypedParamNode, ParamType, DefaultTypeCheckPolicy);
			}
			else {
				ParamNode = Gamma.GetDefaultTypedNode(ParamType);
			}
			if(ParamNode.IsError()) return ParamNode;
			WorkingNode.Append(ParamNode);
		}
		return WorkingNode;
	}

	
	public int ParseParenthesis(UntypedNode Node, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KToken GroupToken = TokenList.get(BeginIdx);
		UntypedNode BodyNode = Node.GetSuffixBodyNode();
		if(BodyNode != null) {
			ArrayList<KToken> GroupList = GroupToken.GetGroupList();
			BodyNode.AppendTokenList(",", GroupList, 1, GroupList.size()-1, AllowEmpty|CreateNullNode);
			BodyNode.Syntax = KSyntax.ApplyMethodSyntax;
		}
		else {
			Node.SetAtNode(0, UntypedNode.ParseGroup(Node.NodeNameSpace, GroupToken, 0));
		}
		return BeginIdx+1;
	}

//	public int TypeCheck_Getter()
//	{
//		VAR_TypeCheck2(stmt, expr, ns, reqc);
//		kNode *self = KLIB TypeCheckNodeAt(kctx, expr, 1, ns, KClass_INFER, 0);
//		if(kNode_IsError(self)) {
//			KReturn(self);
//		}
//		kToken *fieldToken = expr->NodeList->TokenItems[0];
//		ksymbol_t fn = fieldToken->symbol;
//		kMethod *mtd = KLIB kNameSpace_GetGetterMethodNULL(kctx, ns, KClass_(self->typeAttr), fn);
//		if(mtd != NULL) {
//			KReturn(KLIB TypeCheckMethodParam(kctx, mtd, expr, ns, reqc));
//		}
//		else {  // dynamic field    o.name => o.get(name)
//			kparamtype_t p[1] = {{KType_Symbol}};
//			kparamId_t paramdom = KLIB Kparamdom(kctx, 1, p);
//			mtd = KLIB kNameSpace_GetMethodBySignatureNULL(kctx, ns, KClass_(self->typeAttr), KMethodNameAttr_Getter, paramdom, 1, p);
//			if(mtd != NULL) {
//				KLIB kArray_Add(kctx, expr->NodeList, new_UnboxConstNode(kctx, ns, KType_Symbol, KSymbol_Unmask(fn)));
//				KReturn(KLIB TypeCheckMethodParam(kctx, mtd, expr, ns, reqc));
//			}
//		}
//		KLIB MessageNode(kctx, stmt, fieldToken, ns, ErrTag, "undefined field: %s", kString_text(fieldToken->text));
//	}

	// If Statement
	public final static int IfCond = 0;
	public final static int IfThen = 1;
	public final static int IfElse = 2;
	
	public int ParseIf(UntypedNode UNode, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		int NextIdx = UNode.MatchCond(IfCond, TokenList, BeginIdx + 1, EndIdx, ParseOption);
		NextIdx = UNode.MatchSingleBlock(IfThen, TokenList, NextIdx, EndIdx, ParseOption);
		int NextIdx2 = UNode.MatchKeyword(-1, "else", TokenList, NextIdx, EndIdx, AllowEmpty);
		if(NextIdx == NextIdx2 && NextIdx != -1) {  // skiped
			UNode.SetAtNode(IfElse, UntypedNode.NewNullNode(UNode.NodeNameSpace, TokenList, NextIdx2));
		}
		else {
			NextIdx2 = UNode.MatchSingleBlock(IfElse, TokenList, NextIdx2, EndIdx, ParseOption);
		}
		return NextIdx2;
	}

	public TypedNode TypeIf(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		TypedNode CondNode = UNode.TypeNodeAt(IfCond, Gamma, UNode.NodeNameSpace.KonohaContext.BooleanType, 0);
		if(CondNode.IsError()) return CondNode;
		TypedNode ThenNode = UNode.TypeNodeAt(IfThen, Gamma, TypeInfo, 0);
		if(ThenNode.IsError()) return ThenNode;
		TypedNode ElseNode = UNode.TypeNodeAt(IfElse, Gamma, ThenNode.TypeInfo, 0);
		if(ElseNode.IsError()) return ElseNode;
		return new IfNode(ThenNode.TypeInfo, CondNode, ThenNode, ElseNode);
	}
		
	// Return Statement

	public int ParseReturn(UntypedNode UNode, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		int NextIdx = UntypedNode.FindDelim(TokenList, BeginIdx, EndIdx);
		UNode.AddParsedNode(UntypedNode.ParseNewNode(UNode.NodeNameSpace, null, TokenList, BeginIdx + 1, NextIdx, AllowEmpty|CreateNullNode));
		return NextIdx;
	}

	public TypedNode TypeReturn(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
//		TypedNode CondNode = UNode.TypeNodeAt(IfCond, Gamma, Gamma.BooleanType, 0);
//		if(CondNode.IsError()) return CondNode;
//		TypedNode ThenNode = UNode.TypeNodeAt(IfThen, Gamma, TypeInfo, 0);
//		if(CondNode.IsError()) return ThenNode;
//		TypedNode ElseNode = UNode.TypeNodeAt(IfElse, Gamma, ThenNode.TypeInfo, TypeCheckPolicy_AllowEmpty);
//		if(CondNode.IsError()) return ThenNode;
//		return new IfNode(ThenNode.TypeInfo, CondNode, ThenNode, CondNode);
		return null;
	}

	public final static int VarDeclType  = 0;
	public final static int VarDeclName  = 1;
	public final static int VarDeclValue = 2;
	public final static int VarDeclScope = 3;

	public int ParseVarDeclIteration(UntypedNode UNode, KToken TypeToken, ArrayList<KToken> TokenList, int SymbolIdx, int EndIdx, int ParseOption) {
		UNode.SetAtToken(VarDeclType, TypeToken);
		int NextIdx = UNode.MatchSyntax(VarDeclName, "$Symbol", TokenList, SymbolIdx, EndIdx, TermRequired);
		//System.out.printf("SymbolIdx=%d,  NextIdx=%d, EndIdx=%d\n", SymbolIdx, NextIdx, EndIdx);
		if (NextIdx == NoMatch) return EndIdx;
		if(NextIdx == EndIdx) {
			UNode.SetAtToken(VarDeclValue, null);
			UNode.SetAtToken(VarDeclScope, null);
			return EndIdx;
		}
		int ValueIdx = UNode.MatchKeyword(-1, "=", TokenList, NextIdx, EndIdx, HasNextPattern);
		if(ValueIdx != NoMatch) {
			NextIdx = UNode.MatchExpression(VarDeclValue, TokenList, ValueIdx, EndIdx, ",", TermRequired);
			if(NextIdx == -1) return EndIdx;
			NextIdx = NextIdx - 1;
		}
		else {
			UNode.SetAtToken(VarDeclValue, null);			
		}
		int NextVarDeclIdx = UNode.MatchKeyword(-1, ",", TokenList, NextIdx, EndIdx, HasNextPattern);
		if(NextVarDeclIdx != NoMatch) {
			UntypedNode ScopeNode = new UntypedNode(UNode.NodeNameSpace, TypeToken);
			UNode.SetAtNode(VarDeclScope, ScopeNode);
			return ParseVarDeclIteration(ScopeNode, TypeToken, TokenList, NextVarDeclIdx, EndIdx, TermRequired);
		}
		if(NextIdx < EndIdx) {
			UNode.SetAtNode(VarDeclScope, UntypedNode.ParseNewNode(UNode.NodeNameSpace, null, TokenList, NextIdx, EndIdx, TermRequired));
		}
		else {
			UNode.SetAtToken(VarDeclScope, null);
		}
		return EndIdx;
	}
	
	public int ParseVarDecl(UntypedNode UNode, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KToken.DumpTokenList(0, "ParseVarDecl", TokenList, BeginIdx, EndIdx);
		int SymbolIdx = BeginIdx + 1;
		int AfterSymbolIdx  = UNode.MatchSyntax(-1, "$Symbol", TokenList, SymbolIdx, EndIdx, ParseOption);
		if(AfterSymbolIdx == -1) return -1;
		//System.out.printf("SymbolIdx=%d,  AfterSymbolIdx=%d, EndIdx=%d\n", SymbolIdx, AfterSymbolIdx, EndIdx);
		if(AfterSymbolIdx < EndIdx) {
			int NextIdx = UNode.MatchSyntax(-1, "()", TokenList, AfterSymbolIdx, EndIdx, AllowEmpty|ParseOption);
			//System.out.printf("SymbolIdx=%d,  AfterSymbolIdx=%d, NextIdx=%d, EndIdx=%d\n", SymbolIdx, AfterSymbolIdx, NextIdx, EndIdx);
			if(AfterSymbolIdx + 1 == NextIdx) return NoMatch;
		}
		return ParseVarDeclIteration(UNode, TokenList.get(BeginIdx), TokenList, SymbolIdx, EndIdx, TermRequired);
	}

	public TypedNode TypeVarDecl(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		assert(UNode.KeyToken.ResolvedSyntax == KSyntax.TypeSyntax);
		return null; // TODO
	}
	
	
	public final static int MethodDeclReturn = 0;
	public final static int MethodDeclClass  = 1;
	public final static int MethodDeclName = 2;
	public final static int MethodDeclBlock  = 3;
	public final static int MethodDeclParam  = 4;
	
	public int ParseMethodDecl(UntypedNode UNode, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		UNode.SetAtToken(MethodDeclReturn, TokenList.get(BeginIdx));
		UNode.SetAtToken(MethodDeclClass, null);
		int SymbolIdx = BeginIdx + 1;
		int ParamIdx = UNode.MatchSyntax(MethodDeclName, "$Symbol", TokenList, SymbolIdx, EndIdx, ParseOption);
		int BlockIdx = UNode.MatchSyntax(-1, "()", TokenList, ParamIdx, EndIdx, ParseOption);
		int NextIdx = UNode.MatchSyntax(MethodDeclBlock, "{}", TokenList, BlockIdx, EndIdx, ParseOption);
		if(NextIdx != -1) {
			KToken GroupToken = TokenList.get(ParamIdx);
			ArrayList<KToken> GroupList = GroupToken.GetGroupList();
			UNode.AppendTokenList(",", GroupList, 1, GroupList.size()-1, 0/*ParseOption*/);
		}
		//System.out.printf("SymbolIdx=%d,  ParamIdx=%d, BlockIdx=%d, NextIdx=%d, EndIdx=%d\n", SymbolIdx, ParamIdx, BlockIdx, NextIdx, EndIdx);
		return NextIdx;
	}

	public TypedNode TypeMethodDecl(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		System.err.println("@@@@@ " + UNode);
		KClass BaseType = UNode.GetTokenType(MethodDeclClass, null);
		if(BaseType == null) {
			BaseType = UNode.NodeNameSpace.GetGlobalObject().TypeInfo;
		}
		String MethodName = UNode.GetTokenString(MethodDeclName, "new");
		int ParamSize = UNode.NodeList.size() - MethodDeclParam;
		KClass[] ParamData = new KClass[ParamSize+1];
		ParamData[0] = UNode.GetTokenType(MethodDeclClass, Gamma.VarType);
		for(int i = 0; i < ParamSize; i++) {
			UntypedNode ParamNode = (UntypedNode)UNode.NodeList.get(MethodDeclParam + i);
			KClass ParamType = ParamNode.GetTokenType(VarDeclType, Gamma.VarType);
			ParamData[i+1] = ParamType;
		}
		KParam Param = new KParam(ParamSize+1, ParamData);
		KMethod Method = new KMethod(0, BaseType, MethodName, Param, UNode.NodeNameSpace, UNode.GetTokenList(MethodDeclBlock));
		return new DefNode(TypeInfo, Method);
	}
	
	public int ParseEmpty(UntypedNode UNode, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return NoMatch;
	}

	public TypedNode TypeEmpty(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return null;
	}

	public int ParseUNUSED(UntypedNode UNode, ArrayList<KToken> TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return NoMatch;
	}

	public TypedNode TypeUNUSED(KGamma Gamma, UntypedNode UNode, KClass TypeInfo) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return null;
	}

	public void LoadDefaultSyntax(KNameSpace NameSpace) {
		NameSpace.DefineSymbol("void",    NameSpace.KonohaContext.VoidType); // FIXME
		NameSpace.DefineSymbol("boolean", NameSpace.KonohaContext.BooleanType);
		NameSpace.DefineSymbol("int",     NameSpace.KonohaContext.IntType);
		NameSpace.DefineSymbol("String",  NameSpace.KonohaContext.StringType);
		NameSpace.DefineSymbol("true",    new Boolean(true));
		NameSpace.DefineSymbol("false",   new Boolean(false));
				
		NameSpace.AddTokenFunc(" \t", this, "WhiteSpaceToken");		
		NameSpace.AddTokenFunc("\n", this, "IndentToken");		
		NameSpace.AddTokenFunc("(){}[]<>,;+-*/%=&|!", this, "SingleSymbolToken");
		NameSpace.AddTokenFunc("Aa", this, "SymbolToken");
		NameSpace.AddTokenFunc(".", this, "MemberToken");
		NameSpace.AddTokenFunc("\"", this, "StringLiteralToken");
		NameSpace.AddTokenFunc("1", this, "NumberLiteralToken");
		
		// Macro
		NameSpace.DefineMacro("(", this, "OpenParenthesisMacro");
		NameSpace.DefineMacro(")", this, "CloseParenthesisMacro");
		NameSpace.DefineMacro("{", this, "OpenBraceMacro");
		NameSpace.DefineMacro("}", this, "CloseBraceMacro");
		NameSpace.DefineMacro("[", this, "OpenBracketMacro");
		NameSpace.DefineMacro("]", this, "CloseBracketMacro");
		NameSpace.DefineMacro("=", this, "MergeOperatorMacro");
		NameSpace.DefineMacro("&", this, "MergeOperatorMacro");
		NameSpace.DefineMacro("|", this, "MergeOperatorMacro");
		//ns.AddSymbol(symbol, constValue);

		NameSpace.DefineSyntax("*", BinaryOperator|Precedence_CStyleMUL, this, "UNUSED", "MethodCall");
		NameSpace.DefineSyntax("/", BinaryOperator|Precedence_CStyleMUL, this, "UNUSED", "MethodCall");
		NameSpace.DefineSyntax("%", BinaryOperator|Precedence_CStyleMUL, this, "UNUSED", "MethodCall");
		
		NameSpace.DefineSyntax("+", UniaryOperator|BinaryOperator|Precedence_CStyleADD, this, "UniaryOperator", "MethodCall");
		NameSpace.DefineSyntax("-", UniaryOperator|BinaryOperator|Precedence_CStyleADD, this, "UniaryOperator", "MethodCall");

		NameSpace.DefineSyntax("<", BinaryOperator|Precedence_CStyleCOMPARE, this, "UNUSED", "MethodCall");
		NameSpace.DefineSyntax("<=", BinaryOperator|Precedence_CStyleCOMPARE, this, "UNUSED", "MethodCall");
		NameSpace.DefineSyntax(">", BinaryOperator|Precedence_CStyleCOMPARE, this, "UNUSED", "MethodCall");
		NameSpace.DefineSyntax(">=", BinaryOperator|Precedence_CStyleCOMPARE, this, "UNUSED", "MethodCall");

		NameSpace.DefineSyntax("==", BinaryOperator|Precedence_CStyleEquals, this, "UNUSED", "MethodCall");
		NameSpace.DefineSyntax("!=", BinaryOperator|Precedence_CStyleEquals, this, "UNUSED", "MethodCall");

		NameSpace.DefineSyntax("=", BinaryOperator|Precedence_CStyleAssign|LeftJoin, this, "UNUSED", "Assign");

		NameSpace.DefineSyntax("&&", BinaryOperator|Precedence_CStyleAND, this, "UNUSED", "AndOperator");
		NameSpace.DefineSyntax("||", BinaryOperator|Precedence_CStyleOR, this,  "UNUSED", "OrOperator");
		NameSpace.DefineSyntax("!",  UniaryOperator, this, "UniaryOperator", "MethodCall");
//		NameSpace.DefineSyntax(";",  Precedence_CStyleDelim, this, null, null);

		NameSpace.DefineSyntax("$Const",  Term, this, "Const");
		NameSpace.DefineSyntax("$Symbol", Term, this, "Symbol");
		NameSpace.DefineSyntax("$Member", Precedence_CStyleSuffixCall, this, "Member");
		NameSpace.DefineSyntax("()",      Term|Precedence_CStyleSuffixCall, this, "UNUSED");
		NameSpace.DefineSyntax("{}",      0, this, "UNUSED");
		NameSpace.DefineSyntax("$StringLiteral",  Term, this, "StrngLiteral");
		NameSpace.DefineSyntax("$IntegerLiteral", Term, this, "IntegerLiteral");

		//ns.DefineSyntax("$Type", Term, this, "ParseTypeSymbol", "TypeTypeSymbol");
		NameSpace.DefineSyntax("$Type", Statement, this, "MethodDecl");
		NameSpace.DefineSyntax("$Type", Statement, this, "VarDecl");

		NameSpace.DefineSyntax("if", Statement, this, "If");
		NameSpace.DefineSyntax("return", Statement, this, "Return");
		
		DefineIntMethod(NameSpace);
	}
	
	void DefineIntMethod (KNameSpace ns) {
		KClass BaseClass = ns.LookupTypeInfo(Integer.class);
		KParam BinaryParam = KParam.ParseOf(ns, "int int x");
		KParam UniaryParam = KParam.ParseOf(ns, "int");
		
		BaseClass.DefineMethod(ImmutableMethod|ConstMethod, "+", UniaryParam, this, "PlusInt");
		BaseClass.DefineMethod(ImmutableMethod|ConstMethod, "+", BinaryParam, this, "IntAddInt");
		BaseClass.DefineMethod(ImmutableMethod|ConstMethod, "-", UniaryParam, this, "MinusInt");
		BaseClass.DefineMethod(ImmutableMethod|ConstMethod, "-", BinaryParam, this, "IntSubInt");
		BaseClass.DefineMethod(ImmutableMethod|ConstMethod, "*", BinaryParam, this, "IntMulInt");
		
		if(KonohaDebug.UseBuiltInTest) {
			assert(BaseClass.LookupMethod("+", 0) != null);
			assert(BaseClass.LookupMethod("+", 1) != null);
			assert(BaseClass.LookupMethod("+", 2) == null);
			KMethod m = BaseClass.LookupMethod("+", 1);
			Object[] p = new Object[2];
			p[0] = new Integer(1);
			p[1] = new Integer(2);
			System.out.println("******* 1+2=" + m.Eval(p));
		}
	}
	
	public static int PlusInt(int x) {
		return +x;
	}

	public static int IntAddInt(int x, int y) {
		return x + y;
	}

	public static int MinusInt(int x) {
		return -x;
	}

	public static int IntSubInt(int x, int y) {
		return x - y;
	}

	public static int IntMulInt(int x, int y) {
		return x * y;
	}

}

