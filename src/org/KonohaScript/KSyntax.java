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

import java.lang.reflect.Method;
import java.util.ArrayList;
import org.KonohaScript.CodeGen.TypedNode;

public class KSyntax {
	final static int Precedence_CPPStyleScope  =  50;
	final static int Precedence_CStyleSuffixCall     = 100;  /*x(); x[]; x.x x->x x++ */
	final static int Precedence_CStylePrefixOperator = 200;  /*++x; --x; sizeof x &x +x -x !x (T)x  */
//	Precedence_CppMember      = 300;  /* .x ->x */
	final static int Precedence_CStyleMUL      = 400;  /* x * x; x / x; x % x*/
	final static int Precedence_CStyleADD      = 500;  /* x + x; x - x */
	final static int Precedence_CStyleSHIFT    = 600;  /* x << x; x >> x */
	final static int Precedence_CStyleCOMPARE  = 700;
	final static int Precedence_CStyleEquals   = 800;
	final static int Precedence_CStyleBITAND   = 900;
	final static int Precedence_CStyleBITXOR   = 1000;
	final static int Precedence_CStyleBITOR    = 1100;
	final static int Precedence_CStyleAND      = 1200;
	final static int Precedence_CStyleOR       = 1300;
	final static int Precedence_CStyleTRINARY  = 1400;  /* ? : */
	final static int Precedence_CStyleAssign   = 1500;
	final static int Precedence_CStyleCOMMA    = 1600;
	final static int Precedence_Statement      = 1900;
	final static int Precedence_CStyleStatementEnd    = 2000;

	KNameSpace     packageNameSpace;
	String         syntaxName;
	int            flag;
	boolean IsTypeSuffix() { return false; }
	boolean IsOperatorSuffix() { return false; }
	boolean IsLeftJoinOperator() {return false; }
	int precedence_op2;
	int precedence_op1;
	Object ParseObject;
	Method ParseMethod;
	Object TypeObject;
	Method TypeMethod;
	KSyntax        prev;
	KSyntax Pop() { return prev; }
	
	KSyntax(Object po, String methodName, int precedence_op1, int precedence_op2) {
		this.ParseObject = po;
		this.ParseMethod = KFunc.LookupMethod(po, methodName);
	}
	
	int InvokeParseFunc(UntypedNode node, ArrayList<KToken> tokens, int beginIdx, int optIdx, int endIdx) {
		return -1; // Todo
	}

	int InvokeTypeFunc(UntypedNode node) {
		return -1; // Todo
	}

	TypedNode TypeMethodCall(KGamma gma, UntypedNode node) {
		return null;
	}
	
}



//static int FindFirstStatementToken(KonohaContext *kctx, kArray *tokenList, int currentIdx, int endIdx)
//{
//	for(; currentIdx < endIdx; currentIdx++) {
//		kToken *tk = tokenList->TokenItems[currentIdx];
//		if(kToken_IsStatementSeparator(tk)) continue;
//		break;
//	}
//	return currentIdx;
//}
//
//static int FindEndOfStatement(KonohaContext *kctx, kNameSpace *ns, kArray *tokenList, int beginIdx, int endIdx)
//{
//	int c;
//	for(c = beginIdx; c < endIdx; c++) {
//		kToken *tk = tokenList->TokenItems[c];
//		if(kToken_IsStatementSeparator(tk)) return c;
//	}
//	return endIdx;
//}
//
//static int SkipAnnotation(KonohaContext *kctx, kArray *tokenList, int currentIdx, int endIdx)
//{
//	for(; currentIdx < endIdx; currentIdx++) {
//		kToken *tk = tokenList->TokenItems[currentIdx];
//		if(kToken_IsIndent(tk)) continue;
//		if(KSymbol_IsAnnotation(tk->symbol)) {
//			if(currentIdx + 1 < endIdx) {
//				kToken *nextToken = tokenList->TokenItems[currentIdx+1];
//				if(nextToken->resolvedSyntaxInfo != NULL && nextToken->resolvedSyntaxInfo->keyword == KSymbol_ParenthesisGroup) {
//					currentIdx++;
//				}
//			}
//			continue;
//		}
//		break;
//	}
//	return currentIdx;
//}
//
//static void kNode_AddAnnotation(KonohaContext *kctx, kNode *stmt, kArray *tokenList, int beginIdx, int endIdx)
//{
//	int currentIdx;
//	for(currentIdx = beginIdx; currentIdx < endIdx; currentIdx++) {
//		kToken *tk = tokenList->TokenItems[currentIdx];
//		if(kToken_IsIndent(tk)) {
//			continue;
//		}
//		if(KSymbol_IsAnnotation(tk->symbol)) {
//			kObject *value = UPCAST(K_TRUE);
//			if(currentIdx + 1 < endIdx) {
//				kToken *nextToken = tokenList->TokenItems[currentIdx+1];
//				if(nextToken->resolvedSyntaxInfo != NULL && nextToken->resolvedSyntaxInfo->keyword == KSymbol_ParenthesisGroup) {
//					int start = 0;
//					value = (kObject *)KLIB ParseNewNode(kctx, kNode_ns(stmt), nextToken->GroupTokenList, &start, kArray_size(nextToken->GroupTokenList), ParseExpressionOption, "(");
//					currentIdx++;
//				}
//			}
//			KLIB kObjectProto_SetObject(kctx, stmt, tk->symbol, kObject_typeId(value), value);
//			continue;
//		}
//		break;
//	}
//}
//
//static int ParseMetaPattern(KonohaContext *kctx, kNameSpace *ns, kNode *node, kArray *tokenList, int beginIdx, int endIdx)
//{
//	int i;
//	//KLIB dumpTokenArray(kctx, 0, tokenList, beginIdx, endIdx);
//	for(i = beginIdx; i < endIdx; i++) {
//		kToken *tk = tokenList->TokenItems[i];
//		if(kToken_IsStatementSeparator(tk)) {
//			return ParseSyntaxNode(kctx, tk->resolvedSyntaxInfo, node, 0, tokenList, beginIdx, i, endIdx);
//		}
//	}
//	int currentIdx = SkipAnnotation(kctx, tokenList, beginIdx, endIdx);
//	if(!(currentIdx < endIdx)) {
//		return endIdx;  // empty
//	}
//	kToken *tk = tokenList->TokenItems[currentIdx];
//	kSyntax *syn = tk->resolvedSyntaxInfo;
//	if(syn->syntaxPatternListNULL == NULL) {
//		kNameSpace *currentNameSpace = ns;
//		KFieldSet(node, node->KeyOperatorToken, tk);
//		while(currentNameSpace != NULL) {
//			kArray *metaPatternList = currentNameSpace->metaPatternList;
//			intptr_t i;
//			for(i = kArray_size(metaPatternList) - 1; i >=0; i--) {
//				kSyntax *patternSyntax = metaPatternList->SyntaxItems[i];
//				DBG_ASSERT(IS_Syntax(patternSyntax));
//				node->syn = patternSyntax;
//				int nextIdx = ParseSyntaxNode(kctx, patternSyntax, node, 0, tokenList, currentIdx, PatternNoMatch, endIdx);
//				//DBG_P(">>>>>>>>>> searching meta pattern = %s%s index=%d,%d,%d", KSymbol_Fmt2(patternToken->symbol), beginIdx, nextIdx, endIdx);
//				if(nextIdx != PatternNoMatch) {
//					if(beginIdx < currentIdx) {
//						kNode_AddAnnotation(kctx, node, tokenList, beginIdx, currentIdx);
//					}
//					return nextIdx;
//				}
//				if(kNode_IsError(node)) return endIdx;
//				node->syn = NULL;
//				kNode_Reset(kctx, node);
//			}
//			currentNameSpace = currentNameSpace->parentNULL;
//		}
//	}
//	KFieldSet(node, node->KeyOperatorToken, K_NULLTOKEN);
//	return PatternNoMatch;
//}
