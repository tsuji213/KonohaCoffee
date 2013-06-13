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

package org.KonohaScript.MiniKonoha;

import org.KonohaScript.KonohaConst;
import org.KonohaScript.KonohaDebug;
import org.KonohaScript.KonohaGrammar;
import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaParam;
import org.KonohaScript.KonohaSyntax;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.LexicalConverter;
import org.KonohaScript.TypeEnv;
import org.KonohaScript.UntypedNode;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.SyntaxTree.AndNode;
import org.KonohaScript.SyntaxTree.ApplyNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.DefineNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.OrNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public final class MiniKonohaGrammar extends KonohaGrammar implements KonohaConst {

	// Token
	public int WhiteSpaceToken(KonohaNameSpace ns, String SourceText, int pos, TokenList ParsedTokenList) {
		for(; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isWhitespace(ch)) {
				break;
			}
		}
		return pos;
	}

	public int IndentToken(KonohaNameSpace ns, String SourceText, int pos, TokenList ParsedTokenList) {
		int LineStart = pos + 1;
		pos = pos + 1;
		for(; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isWhitespace(ch)) {
				break;
			}
		}
		String Text = "";
		if(LineStart < pos) {
			Text = SourceText.substring(LineStart, pos);
		}
		KonohaToken Token = new KonohaToken(Text);
		Token.ResolvedSyntax = KonohaSyntax.IndentSyntax;
		ParsedTokenList.add(Token);
		return pos;
	}

	public int SingleSymbolToken(KonohaNameSpace ns, String SourceText, int pos, TokenList ParsedTokenList) {
		KonohaToken Token = new KonohaToken(SourceText.substring(pos, pos + 1));
		ParsedTokenList.add(Token);
		return pos + 1;
	}

	public int SymbolToken(KonohaNameSpace ns, String SourceText, int pos, TokenList ParsedTokenList) {
		int start = pos;
		for(; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '_') {
				break;
			}
		}
		KonohaToken Token = new KonohaToken(SourceText.substring(start, pos));
		ParsedTokenList.add(Token);
		return pos;
	}

	public int MemberToken(KonohaNameSpace ns, String SourceText, int pos, TokenList ParsedTokenList) {
		int start = pos;
		for(; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '_') {
				break;
			}
		}
		KonohaToken Token = new KonohaToken(SourceText.substring(start, pos));
		Token.ResolvedSyntax = KonohaSyntax.MemberSyntax;
		ParsedTokenList.add(Token);
		return pos;
	}

	public int NumberLiteralToken(KonohaNameSpace ns, String SourceText, int pos, TokenList ParsedTokenList) {
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

	public int StringLiteralToken(KonohaNameSpace ns, String SourceText, int pos, TokenList ParsedTokenList) {
		int start = pos + 1;
		char prev = '"';
		pos = start;
		while(pos < SourceText.length()) {
			char ch = SourceText.charAt(pos);
			if(ch == '"' && prev != '\\') {
				KonohaToken token = new KonohaToken(SourceText.substring(start, pos - start));
				token.ResolvedSyntax = ns.GetSyntax("$StringLiteral");
				ParsedTokenList.add(token);
				return pos + 1;
			}
			if(ch == '\n') {
				KonohaToken token = new KonohaToken(SourceText.substring(start, pos - start));
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

	public int OpenParenthesisMacro(LexicalConverter Lexer, TokenList SourceList, int BeginIdx, int EndIdx, TokenList BufferList) {
		TokenList GroupList = new TokenList();
		KonohaToken BeginToken = SourceList.get(BeginIdx);
		GroupList.add(BeginToken);
		int nextIdx = Lexer.Do(SourceList, BeginIdx + 1, EndIdx, GroupList);
		KonohaToken LastToken = GroupList.get(GroupList.size() - 1);
		if(!LastToken.EqualsText(")")) { // ERROR
			LastToken.SetErrorMessage("must close )");
		} else {
			KonohaToken GroupToken = new KonohaToken("( ... )", BeginToken.uline);
			GroupToken.SetGroup(Lexer.GetSyntax("()"), GroupList);
			BufferList.add(GroupToken);
		}
		return nextIdx;
	}

	public int CloseParenthesisMacro(LexicalConverter Lexer, TokenList SourceList, int BeginIdx, int EndIdx,
			TokenList BufferList) {
		KonohaToken Token = SourceList.get(BeginIdx);
		if(BufferList.size() == 0 || !BufferList.get(0).EqualsText("(")) {
			Token.SetErrorMessage("mismatched )");
		}
		BufferList.add(Token);
		return BreakPreProcess;
	}

	public int OpenBraceMacro(LexicalConverter Lexer, TokenList SourceList, int BeginIdx, int EndIdx, TokenList BufferList) {
		TokenList GroupList = new TokenList();
		KonohaToken BeginToken = SourceList.get(BeginIdx);
		GroupList.add(BeginToken);
		int nextIdx = Lexer.Do(SourceList, BeginIdx + 1, EndIdx, GroupList);
		KonohaToken LastToken = GroupList.get(GroupList.size() - 1);
		if(!LastToken.EqualsText("}")) { // ERROR
			LastToken.SetErrorMessage("must close }");
		} else {
			KonohaToken GroupToken = new KonohaToken("{ ... }", BeginToken.uline);
			GroupToken.SetGroup(Lexer.GetSyntax("{}"), GroupList);
			BufferList.add(GroupToken);
		}
		return nextIdx;
	}

	public int CloseBraceMacro(LexicalConverter Lexer, TokenList SourceList, int BeginIdx, int EndIdx, TokenList BufferList) {
		KonohaToken Token = SourceList.get(BeginIdx);
		if(BufferList.size() == 0 || !BufferList.get(0).EqualsText("{")) {
			Token.SetErrorMessage("mismatched }");
		}
		BufferList.add(Token);
		return BreakPreProcess;
	}

	public int OpenCloseBraceMacro(LexicalConverter Lexer, TokenList SourceList, int BeginIdx, int EndIdx, TokenList BufferList) {
		int BraceLevel = 0;
		TokenList GroupList = new TokenList();
		for(int i = BeginIdx; i < EndIdx; i++) {
			KonohaToken Token = SourceList.get(i);
			GroupList.add(Token);
			if(Token.EqualsText("{")) {
				BraceLevel++;
			}
			if(Token.EqualsText("}")) {
				BraceLevel--;
				if(BraceLevel == 0) {
					KonohaToken GroupToken = new KonohaToken("{ ... }", SourceList.get(BeginIdx).uline);
					GroupToken.SetGroup(Lexer.GetSyntax("${}"), GroupList);
					BufferList.add(GroupToken);
					return i + 1;
				}
			}
		}
		SourceList.get(BeginIdx).SetErrorMessage("must close }");
		BufferList.add(SourceList.get(BeginIdx));
		return EndIdx;
	}

	public int OpenBracketMacro(LexicalConverter Lexer, TokenList SourceList, int BeginIdx, int EndIdx, TokenList BufferList) {
		TokenList GroupList = new TokenList();
		KonohaToken BeginToken = SourceList.get(BeginIdx);
		GroupList.add(BeginToken);
		int nextIdx = Lexer.Do(SourceList, BeginIdx + 1, EndIdx, GroupList);
		KonohaToken LastToken = GroupList.get(GroupList.size() - 1);
		if(!LastToken.EqualsText("]")) { // ERROR
			LastToken.SetErrorMessage("must close ]");
		} else {
			KonohaToken GroupToken = new KonohaToken("[ ... ]", BeginToken.uline);
			GroupToken.SetGroup(Lexer.GetSyntax("[]"), GroupList);
			BufferList.add(GroupToken);
		}
		return nextIdx;
	}

	public int CloseBracketMacro(LexicalConverter Lexer, TokenList SourceList, int BeginIdx, int EndIdx, TokenList BufferList) {
		KonohaToken Token = SourceList.get(BeginIdx);
		if(BufferList.size() == 0 || !BufferList.get(0).EqualsText("[")) {
			Token.SetErrorMessage("mismatched ]");
		}
		BufferList.add(Token);
		return BreakPreProcess;
	}

	public int MergeOperatorMacro(LexicalConverter Lexer, TokenList SourceList, int BeginIdx, int EndIdx, TokenList BufferList) {
		// ["=", "="] => "=="
		KonohaToken Token = SourceList.get(BeginIdx);
		if(BufferList.size() > 0) {
			KonohaToken PrevToken = BufferList.get(BufferList.size() - 1);
			if(PrevToken.ResolvedSyntax != null && PrevToken.uline == Token.uline) {
				// if(!Character.isLetter(PrevToken.ParsedText.charAt(0))) {
				String MergedOperator = PrevToken.ParsedText + Token.ParsedText;
				KonohaSyntax Syntax = Lexer.GetSyntax(MergedOperator);
				if(Syntax != null) {
					PrevToken.ResolvedSyntax = Syntax;
					PrevToken.ParsedText = MergedOperator;
					return BeginIdx + 1;
				}
				// }
			}
		}
		Lexer.ResolveTokenSyntax(Token);
		BufferList.add(Token);
		return BeginIdx + 1;
	}

	// Parse And Type

	public int ParseIntegerLiteral(UntypedNode Node, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		return BeginIdx + 1;
	}

	public TypedNode TypeIntegerLiteral(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaToken Token = UNode.KeyToken;
		return new ConstNode(Gamma.IntType, Token, Integer.valueOf(Token.ParsedText));
	}

	public int ParseStringLiteral(UntypedNode Node, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		return BeginIdx + 1;
	}

	public TypedNode TypeStringLiteral(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaToken Token = UNode.KeyToken;
		/* FIXME: handling of escape sequence */
		return new ConstNode(Gamma.StringType, Token, Token.ParsedText);
	}

	public int ParseSymbol(UntypedNode Node, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		return BeginIdx + 1;
	}

	public TypedNode TypeSymbol(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		// case: Symbol is LocalVariable
		TypeInfo = Gamma.GetLocalType(UNode.KeyToken.ParsedText);
		if(TypeInfo != null) {
			return new LocalNode(TypeInfo, UNode.KeyToken, UNode.KeyToken.ParsedText);
		}

		// case: Symbol is GlobalVariable
		if(UNode.KeyToken.ParsedText.equals("global")) {
			return new ConstNode(
					UNode.NodeNameSpace.GetGlobalObject().TypeInfo,
					UNode.KeyToken,
					UNode.NodeNameSpace.GetGlobalObject());
		}
		// case: Symbol is undefined name
		return Gamma.NewErrorNode(UNode.KeyToken, "undefined name: " + UNode.KeyToken.ParsedText);
	}

	public int ParseConst(UntypedNode Node, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		return BeginIdx + 1;
	}

	public TypedNode TypeConst(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaToken Token = UNode.KeyToken;
		/* FIXME: handling of resolved object */
		return new ConstNode(Gamma.StringType, Token, Token.ParsedText);
	}

	public int ParseUniaryOperator(UntypedNode Node, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		int NextIdx = EndIdx;
		for(int i = BeginIdx + 1; i < EndIdx; i++) {
			KonohaToken Token = TokenList.get(i);
			if(Token.ResolvedSyntax.IsBinaryOperator() || Token.ResolvedSyntax.IsSuffixOperator()
					|| Token.ResolvedSyntax.IsDelim()) {
				NextIdx = i;
				break;
			}
		}
		Node.SetAtNode(0, UntypedNode.ParseNewNode(Node.NodeNameSpace, null, TokenList, BeginIdx + 1, NextIdx, 0));
		return NextIdx;
	}

	public final static int	MethodCallBaseClass	= 0;
	public final static int	MethodCallName		= 1;
	public final static int	MethodCallParam		= 2;

	// $Symbol [ "." $Symbol ] ()
	public int ParseMethodCall(UntypedNode UNode, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		int ClassIdx = -1;
		System.out.println(UNode.KeyToken.ParsedText);
		//ClassIdx = UNode.MatchSyntax(MethodCallBaseClass, "$Type", TokenList, BeginIdx, BeginIdx + 1, AllowEmpty);
		int SymbolIdx = BeginIdx;//ClassIdx + 1;

		int ParamIdx = UNode.MatchSyntax(MethodCallName, "$Symbol", TokenList, SymbolIdx, EndIdx, ParseOption);
		int NextIdx = UNode.MatchSyntax(-1, "()", TokenList, ParamIdx, EndIdx, ParseOption);
		if(NextIdx == -1) {
			return -1;
		}

		KonohaToken GroupToken = TokenList.get(ParamIdx);
		TokenList GroupList = GroupToken.GetGroupList();
		UNode.AppendTokenList(",", GroupList, 1, GroupList.size() - 1, 0/* ParseOption */);

		if(ClassIdx == -1) {
			KonohaToken token = new KonohaToken(KonohaNameSpace.GlobalConstName);
			token.ResolvedSyntax = UNode.NodeNameSpace.GetSyntax("$Symbol");
			TokenList globalTokenList = new TokenList();
			globalTokenList.add(token);
			UntypedNode baseNode = UntypedNode.ParseNewNode(UNode.NodeNameSpace, null, globalTokenList, 0, 1, 0);
			SymbolIdx = BeginIdx;
			UNode.SetAtNode(MethodCallBaseClass, baseNode);
		}

		UNode.Syntax = UNode.NodeNameSpace.GetSyntax("$MethodCall");
		// System.out.printf("SymbolIdx=%d,  ParamIdx=%d, BlockIdx=%d, NextIdx=%d, EndIdx=%d\n",
		// SymbolIdx, ParamIdx, BlockIdx, NextIdx, EndIdx);
		return NextIdx;
	}

	public TypedNode TypeMethodCall(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaDebug.P("(>_<) typing method calls: " + UNode);
		KonohaArray NodeList = UNode.NodeList;
		assert (NodeList.size() > 1);
		assert (NodeList.get(0) instanceof UntypedNode);
		UntypedNode UntypedBaseNode = (UntypedNode) NodeList.get(0);
		if(UntypedBaseNode == null) {
		} else {
			TypedNode BaseNode = TypeEnv.TypeCheckEachNode(Gamma, UntypedBaseNode, Gamma.VarType, 0);
			if(BaseNode.IsError())
				return BaseNode;
			return this.TypeFindingMethod(Gamma, UNode, BaseNode, TypeInfo);
		}
		return null;
	}

	private TypedNode TypeFindingMethod(TypeEnv Gamma, UntypedNode UNode, TypedNode BaseNode, KonohaType TypeInfo) {
		KonohaArray NodeList = UNode.NodeList;
		int ParamSize = NodeList.size() - 2;
		KonohaToken KeyToken = UNode.KeyToken;
		KonohaMethod Method = null;
		Method = Gamma.GammaNameSpace.LookupMethod(KeyToken.ParsedText, ParamSize);
		if(Method == null) {
			Method = BaseNode.TypeInfo.LookupMethod(KeyToken.ParsedText, ParamSize);
		}
		if(Method != null) {
			ApplyNode WorkingNode = new ApplyNode(Method.GetReturnType(BaseNode.TypeInfo), KeyToken, Method);
			WorkingNode.Append(BaseNode);
			return this.TypeMethodEachParam(Gamma, BaseNode.TypeInfo, WorkingNode, NodeList);
		}
		return Gamma.NewErrorNode(KeyToken, "undefined method: " + KeyToken.ParsedText + " in "
				+ BaseNode.TypeInfo.ShortClassName);
	}

	private TypedNode TypeMethodEachParam(TypeEnv Gamma, KonohaType BaseType, ApplyNode WorkingNode, KonohaArray NodeList) {
		KonohaMethod Method = WorkingNode.Method;
		for(int ParamIdx = 0; ParamIdx < NodeList.size() - 1; ParamIdx++) {
			KonohaType ParamType = Method.GetParamType(BaseType, ParamIdx);
			UntypedNode UntypedParamNode = (UntypedNode) NodeList.get(ParamIdx + 1);
			TypedNode ParamNode;
			if(UntypedParamNode != null) {
				ParamNode = TypeEnv.TypeCheck(Gamma, UntypedParamNode, ParamType, DefaultTypeCheckPolicy);
			} else {
				ParamNode = Gamma.GetDefaultTypedNode(ParamType);
			}
			if(ParamNode.IsError())
				return ParamNode;
			WorkingNode.Append(ParamNode);
		}
		return WorkingNode;
	}

	public int ParseParenthesis(UntypedNode Node, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KonohaToken GroupToken = TokenList.get(BeginIdx);
		UntypedNode BodyNode = Node.GetSuffixBodyNode();
		if(BodyNode != null) {
			TokenList GroupList = GroupToken.GetGroupList();
			BodyNode.AppendTokenList(",", GroupList, 1, GroupList.size() - 1, AllowEmpty | CreateNullNode);
			BodyNode.Syntax = KonohaSyntax.ApplyMethodSyntax;
		} else {
			Node.SetAtNode(0, UntypedNode.ParseGroup(Node.NodeNameSpace, GroupToken, 0));
		}
		return BeginIdx + 1;
	}

	// Block {} Statement
	public int ParseBlock(UntypedNode Node, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KonohaToken GroupToken = TokenList.get(BeginIdx);
		UntypedNode BodyNode = Node.GetSuffixBodyNode();
		assert (BodyNode == null);
		Node.SetAtNode(0, UntypedNode.ParseGroup(Node.NodeNameSpace, GroupToken, 0));
		return BeginIdx + 1;
	}

	public TypedNode TypeBlock(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return UNode.TypeNodeAt(0, Gamma, Gamma.VarType, 0);
	}

	public static final int	BinaryOpLeftExpr	= 0;
	public static final int	BinaryOpRightExpr	= 0;

	public TypedNode TypeAndOperator(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode LeftNode = UNode.TypeNodeAt(BinaryOpLeftExpr, Gamma, Gamma.BooleanType, 0);
		if(LeftNode.IsError())
			return LeftNode;
		TypedNode RightNode = UNode.TypeNodeAt(BinaryOpLeftExpr, Gamma, Gamma.BooleanType, 0);
		if(RightNode.IsError())
			return RightNode;

		return new AndNode(RightNode.TypeInfo, UNode.KeyToken, LeftNode, RightNode);
	}

	public TypedNode TypeOrOperator(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode LeftNode = UNode.TypeNodeAt(BinaryOpLeftExpr, Gamma, Gamma.BooleanType, 0);
		if(LeftNode.IsError())
			return LeftNode;
		TypedNode RightNode = UNode.TypeNodeAt(BinaryOpLeftExpr, Gamma, Gamma.BooleanType, 0);
		if(RightNode.IsError())
			return RightNode;

		return new OrNode(RightNode.TypeInfo, UNode.KeyToken, LeftNode, RightNode);
	}

	// public int TypeCheck_Getter()
	// {
	// VAR_TypeCheck2(stmt, expr, ns, reqc);
	// kNode *self = KLIB TypeCheckNodeAt(kctx, expr, 1, ns, KClass_INFER, 0);
	// if(kNode_IsError(self)) {
	// KReturn(self);
	// }
	// kToken *fieldToken = expr->NodeList->TokenItems[0];
	// ksymbol_t fn = fieldToken->symbol;
	// kMethod *mtd = KLIB kNameSpace_GetGetterMethodNULL(kctx, ns,
	// KClass_(self->typeAttr), fn);
	// if(mtd != NULL) {
	// KReturn(KLIB TypeCheckMethodParam(kctx, mtd, expr, ns, reqc));
	// }
	// else { // dynamic field o.name => o.get(name)
	// kparamtype_t p[1] = {{KType_Symbol}};
	// kparamId_t paramdom = KLIB Kparamdom(kctx, 1, p);
	// mtd = KLIB kNameSpace_GetMethodBySignatureNULL(kctx, ns,
	// KClass_(self->typeAttr), KMethodNameAttr_Getter, paramdom, 1, p);
	// if(mtd != NULL) {
	// KLIB kArray_Add(kctx, expr->NodeList, new_UnboxConstNode(kctx, ns,
	// KType_Symbol, KSymbol_Unmask(fn)));
	// KReturn(KLIB TypeCheckMethodParam(kctx, mtd, expr, ns, reqc));
	// }
	// }
	// KLIB MessageNode(kctx, stmt, fieldToken, ns, ErrTag,
	// "undefined field: %s", kString_text(fieldToken->text));
	// }

	// If Statement
	public final static int	IfCond	= 0;
	public final static int	IfThen	= 1;
	public final static int	IfElse	= 2;

	public int ParseIf(UntypedNode UNode, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		int NextIdx = UNode.MatchCond(IfCond, TokenList, BeginIdx + 1, EndIdx, ParseOption);
		NextIdx = UNode.MatchSingleBlock(IfThen, TokenList, NextIdx, EndIdx, ParseOption);
		int NextIdx2 = UNode.MatchKeyword(-1, "else", TokenList, NextIdx, EndIdx, AllowEmpty);
		if(NextIdx == NextIdx2 && NextIdx != -1) { // skiped
			UNode.SetAtNode(IfElse, UntypedNode.NewNullNode(UNode.NodeNameSpace, TokenList, NextIdx2));
		} else {
			NextIdx2 = UNode.MatchSingleBlock(IfElse, TokenList, NextIdx2, EndIdx, ParseOption);
		}
		return NextIdx2;
	}

	public TypedNode TypeIf(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode CondNode = UNode.TypeNodeAt(IfCond, Gamma, Gamma.BooleanType, 0);
		if(CondNode.IsError())
			return CondNode;
		TypedNode ThenNode = UNode.TypeNodeAt(IfThen, Gamma, TypeInfo, 0);
		if(ThenNode.IsError())
			return ThenNode;
		TypedNode ElseNode = UNode.TypeNodeAt(IfElse, Gamma, ThenNode.TypeInfo, 0);
		if(ElseNode.IsError())
			return ElseNode;
		return new IfNode(ThenNode.TypeInfo, CondNode, ThenNode, ElseNode);
	}

	// Return Statement

	public int ParseReturn(UntypedNode UNode, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		int NextIdx = UntypedNode.FindDelim(TokenList, BeginIdx, EndIdx);
		UNode.AddParsedNode(UntypedNode.ParseNewNode(UNode.NodeNameSpace, null, TokenList, BeginIdx + 1, NextIdx, AllowEmpty
				| CreateNullNode));
		return NextIdx;
	}

	public final static int	ReturnExpr	= 0;

	public TypedNode TypeReturn(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode Expr = UNode.TypeNodeAt(ReturnExpr, Gamma, Gamma.ReturnType, 0);
		if(Expr.IsError()) {
			return Expr;
		}
		return new ReturnNode(Expr.TypeInfo, Expr);
	}

	public final static int	VarDeclType		= 0;
	public final static int	VarDeclName		= 1;
	public final static int	VarDeclValue	= 2;
	public final static int	VarDeclScope	= 3;

	public int ParseVarDeclIteration(UntypedNode UNode, KonohaToken TypeToken, TokenList TokenList, int SymbolIdx, int EndIdx,
			int ParseOption) {
		UNode.SetAtToken(VarDeclType, TypeToken);
		int NextIdx = UNode.MatchSyntax(VarDeclName, "$Symbol", TokenList, SymbolIdx, EndIdx, TermRequired);
		// System.out.printf("SymbolIdx=%d,  NextIdx=%d, EndIdx=%d\n",
		// SymbolIdx, NextIdx, EndIdx);
		if(NextIdx == NoMatch)
			return EndIdx;
		if(NextIdx == EndIdx) {
			UNode.SetAtToken(VarDeclValue, null);
			UNode.SetAtToken(VarDeclScope, null);
			return EndIdx;
		}
		int ValueIdx = UNode.MatchKeyword(-1, "=", TokenList, NextIdx, EndIdx, HasNextPattern);
		if(ValueIdx != NoMatch) {
			NextIdx = UNode.MatchExpression(VarDeclValue, TokenList, ValueIdx, EndIdx, ",", TermRequired);
			if(NextIdx == -1)
				return EndIdx;
			NextIdx = NextIdx - 1;
		} else {
			UNode.SetAtToken(VarDeclValue, null);
		}
		int NextVarDeclIdx = UNode.MatchKeyword(-1, ",", TokenList, NextIdx, EndIdx, HasNextPattern);
		if(NextVarDeclIdx != NoMatch) {
			UntypedNode ScopeNode = new UntypedNode(UNode.NodeNameSpace, TypeToken);
			UNode.SetAtNode(VarDeclScope, ScopeNode);
			return this.ParseVarDeclIteration(ScopeNode, TypeToken, TokenList, NextVarDeclIdx, EndIdx, TermRequired);
		}
		if(NextIdx < EndIdx) {
			UNode.SetAtNode(VarDeclScope, UntypedNode
					.ParseNewNode(UNode.NodeNameSpace, null, TokenList, NextIdx, EndIdx, TermRequired));
		} else {
			UNode.SetAtToken(VarDeclScope, null);
		}
		return EndIdx;
	}

	public int ParseVarDecl(UntypedNode UNode, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		//KonohaToken.DumpTokenList(0, "ParseVarDecl", TokenList, BeginIdx, EndIdx);
		int SymbolIdx = BeginIdx + 1;
		int AfterSymbolIdx = UNode.MatchSyntax(-1, "$Symbol", TokenList, SymbolIdx, EndIdx, ParseOption);
		if(AfterSymbolIdx == -1)
			return -1;
		// System.out.printf("SymbolIdx=%d,  AfterSymbolIdx=%d, EndIdx=%d\n",
		// SymbolIdx, AfterSymbolIdx, EndIdx);
		if(AfterSymbolIdx < EndIdx) {
			int NextIdx = UNode.MatchSyntax(-1, "()", TokenList, AfterSymbolIdx, EndIdx, AllowEmpty | ParseOption);
			// System.out.printf("SymbolIdx=%d,  AfterSymbolIdx=%d, NextIdx=%d, EndIdx=%d\n",
			// SymbolIdx, AfterSymbolIdx, NextIdx, EndIdx);
			if(AfterSymbolIdx + 1 == NextIdx)
				return NoMatch;
		}
		return this.ParseVarDeclIteration(UNode, TokenList.get(BeginIdx), TokenList, SymbolIdx, EndIdx, TermRequired);
	}

	public TypedNode TypeVarDecl(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		assert (UNode.KeyToken.ResolvedSyntax == KonohaSyntax.TypeSyntax);
		return null; // TODO
	}

	public final static int	MethodDeclReturn	= 0;
	public final static int	MethodDeclClass		= 1;
	public final static int	MethodDeclName		= 2;
	public final static int	MethodDeclBlock		= 3;
	public final static int	MethodDeclParam		= 4;

	public int ParseMethodDecl(UntypedNode UNode, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		UNode.SetAtToken(MethodDeclReturn, TokenList.get(BeginIdx));
		UNode.SetAtToken(MethodDeclClass, null);
		int SymbolIdx = BeginIdx + 1;
		int ParamIdx = UNode.MatchSyntax(MethodDeclName, "$Symbol", TokenList, SymbolIdx, EndIdx, ParseOption);
		int BlockIdx = UNode.MatchSyntax(-1, "()", TokenList, ParamIdx, EndIdx, ParseOption);
		int NextIdx = UNode.MatchSyntax(MethodDeclBlock, "{}", TokenList, BlockIdx, EndIdx, ParseOption);
		if(NextIdx != -1) {
			KonohaToken GroupToken = TokenList.get(ParamIdx);
			TokenList GroupList = GroupToken.GetGroupList();
			UNode.AppendTokenList(",", GroupList, 1, GroupList.size() - 1, 0/* ParseOption */);
		}
		// System.out.printf("SymbolIdx=%d,  ParamIdx=%d, BlockIdx=%d, NextIdx=%d, EndIdx=%d\n",
		// SymbolIdx, ParamIdx, BlockIdx, NextIdx, EndIdx);
		return NextIdx;
	}

	public TypedNode TypeMethodDecl(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		System.err.println("@@@@@ " + UNode);
		KonohaType BaseType = UNode.GetTokenType(MethodDeclClass, null);
		if(BaseType == null) {
			BaseType = UNode.NodeNameSpace.GetGlobalObject().TypeInfo;
		}
		String MethodName = UNode.GetTokenString(MethodDeclName, "new");
		int ParamSize = UNode.NodeList.size() - MethodDeclParam;
		KonohaType[] ParamData = new KonohaType[ParamSize + 1];
		String[] ArgNames = new String[ParamSize + 1];
		ParamData[0] = UNode.GetTokenType(MethodDeclClass, Gamma.VarType);
		for(int i = 0; i < ParamSize; i++) {
			UntypedNode ParamNode = (UntypedNode) UNode.NodeList.get(MethodDeclParam + i);
			KonohaType ParamType = ParamNode.GetTokenType(VarDeclType, Gamma.VarType);
			ParamData[i + 1] = ParamType;
			ArgNames[i] = ParamNode.GetTokenString(VarDeclName, "");
		}
		KonohaParam Param = new KonohaParam(ParamSize + 1, ParamData, ArgNames);
		KonohaMethod NewMethod = new KonohaMethod(
				0,
				BaseType,
				MethodName,
				Param,
				UNode.NodeNameSpace,
				UNode.GetTokenList(MethodDeclBlock));
		BaseType.DefineNewMethod(NewMethod);
		return new DefineNode(TypeInfo, UNode.KeyToken, NewMethod);
	}

	public int ParseEmpty(UntypedNode UNode, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return NoMatch;
	}

	public TypedNode TypeEmpty(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return null;
	}

	public int ParseUNUSED(UntypedNode UNode, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return NoMatch;
	}

	public TypedNode TypeUNUSED(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return null;
	}

	@Override
	public void LoadDefaultSyntax(KonohaNameSpace NameSpace) {
		NameSpace.DefineSymbol("void", NameSpace.KonohaContext.VoidType); // FIXME
		NameSpace.DefineSymbol("boolean", NameSpace.KonohaContext.BooleanType);
		NameSpace.DefineSymbol("int", NameSpace.KonohaContext.IntType);
		NameSpace.DefineSymbol("String", NameSpace.KonohaContext.StringType);
		NameSpace.DefineSymbol("true", new Boolean(true));
		NameSpace.DefineSymbol("false", new Boolean(false));

		NameSpace.AddTokenFunc(" \t", this, "WhiteSpaceToken");
		NameSpace.AddTokenFunc("\n", this, "IndentToken");
		NameSpace.AddTokenFunc("(){}[]<>,;+-*/%=&|!", this, "SingleSymbolToken");
		NameSpace.AddTokenFunc("Aa", this, "SymbolToken");
		NameSpace.AddTokenFunc(".", this, "MemberToken");
		NameSpace.AddTokenFunc("\"", this, "StringLiteralToken");
		NameSpace.AddTokenFunc("1", this, "NumberLiteralToken");

		// Macro
		//NameSpace.DefineTopLevelMacro("{", this, "OpenCloseBraceMacro");
		NameSpace.DefineMacro("(", this, "OpenParenthesisMacro");
		NameSpace.DefineMacro(")", this, "CloseParenthesisMacro");
		NameSpace.DefineMacro("{", this, "OpenBraceMacro");
		NameSpace.DefineMacro("}", this, "CloseBraceMacro");
		NameSpace.DefineMacro("[", this, "OpenBracketMacro");
		NameSpace.DefineMacro("]", this, "CloseBracketMacro");
		NameSpace.DefineMacro("=", this, "MergeOperatorMacro");
		NameSpace.DefineMacro("&", this, "MergeOperatorMacro");
		NameSpace.DefineMacro("|", this, "MergeOperatorMacro");
		// ns.AddSymbol(symbol, constValue);

		NameSpace.DefineSyntax("*", BinaryOperator | Precedence_CStyleMUL, this, "UNUSED", "MethodCall");
		NameSpace.DefineSyntax("/", BinaryOperator | Precedence_CStyleMUL, this, "UNUSED", "MethodCall");
		NameSpace.DefineSyntax("%", BinaryOperator | Precedence_CStyleMUL, this, "UNUSED", "MethodCall");

		NameSpace
				.DefineSyntax("+", UniaryOperator | BinaryOperator | Precedence_CStyleADD, this, "UniaryOperator", "MethodCall");
		NameSpace
				.DefineSyntax("-", UniaryOperator | BinaryOperator | Precedence_CStyleADD, this, "UniaryOperator", "MethodCall");

		NameSpace.DefineSyntax("<", BinaryOperator | Precedence_CStyleCOMPARE, this, "UNUSED", "MethodCall");
		NameSpace.DefineSyntax("<=", BinaryOperator | Precedence_CStyleCOMPARE, this, "UNUSED", "MethodCall");
		NameSpace.DefineSyntax(">", BinaryOperator | Precedence_CStyleCOMPARE, this, "UNUSED", "MethodCall");
		NameSpace.DefineSyntax(">=", BinaryOperator | Precedence_CStyleCOMPARE, this, "UNUSED", "MethodCall");

		NameSpace.DefineSyntax("==", BinaryOperator | Precedence_CStyleEquals, this, "UNUSED", "MethodCall");
		NameSpace.DefineSyntax("!=", BinaryOperator | Precedence_CStyleEquals, this, "UNUSED", "MethodCall");

		NameSpace.DefineSyntax("=", BinaryOperator | Precedence_CStyleAssign | LeftJoin, this, "UNUSED", "Assign");

		NameSpace.DefineSyntax("&&", BinaryOperator | Precedence_CStyleAND, this, "UNUSED", "AndOperator");
		NameSpace.DefineSyntax("||", BinaryOperator | Precedence_CStyleOR, this, "UNUSED", "OrOperator");
		NameSpace.DefineSyntax("!", UniaryOperator, this, "UniaryOperator", "MethodCall");
		//NameSpace.DefineSyntax(";", Precedence_CStyleDelim, this, null, null);

		NameSpace.DefineSyntax("$Const", Term, this, "Const");
		NameSpace.DefineSyntax("$Symbol", Term, this, "Symbol");
		NameSpace.DefineSyntax("$Symbol", Term, this, "MethodCall");

		NameSpace.DefineSyntax("$MethodCall", Precedence_CStyleSuffixCall, this, "MethodCall");
		NameSpace.DefineSyntax("$Member", Precedence_CStyleSuffixCall, this, "Member");

		NameSpace.DefineSyntax("()", Term | Precedence_CStyleSuffixCall, this, "UNUSED");
		NameSpace.DefineSyntax("{}", 0, this, "UNUSED");
		NameSpace.DefineSyntax("$StringLiteral", Term, this, "StrngLiteral");
		NameSpace.DefineSyntax("$IntegerLiteral", Term, this, "IntegerLiteral");

		NameSpace.DefineSyntax("{}", Statement, this, "Block");

		// ns.DefineSyntax("$Type", Term, this, "ParseTypeSymbol",
		// "TypeTypeSymbol");
		NameSpace.DefineSyntax("$Type", Statement, this, "MethodDecl");
		NameSpace.DefineSyntax("$Type", Statement, this, "VarDecl");

		NameSpace.DefineSyntax("if", Statement, this, "If");
		NameSpace.DefineSyntax("return", Statement, this, "Return");

		new KonohaInt().DefineMethod(NameSpace);
	}
}
