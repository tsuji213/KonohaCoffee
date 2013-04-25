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

import org.KonohaScript.AST.TypedNode;

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
	final static int Precedence_Error          = 1700;
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
	
	KSyntax(String syntaxName, int flag, Object po, String methodName, int precedence_op1, int precedence_op2) {
		this.syntaxName = syntaxName;
		this.flag = flag;
		this.precedence_op1 = precedence_op1;
		this.precedence_op2 = precedence_op2;
		
		this.ParseObject = po == null ? this : po;
		this.ParseMethod = KFunc.LookupMethod(po, methodName);
	}
	
//	static KSyntax DefineSyntax(String syntaxName, int flag, Object base, String name, int precedence_op1, int precedence_op2) {
//		return new KSyntax(syntaxName, flag, precedence_op1, precedence_op2);
//	}
	
	int InvokeParseFunc(UntypedNode node, ArrayList<KToken> tokens, int beginIdx, int optIdx, int endIdx) {
		return -1; // Todo
	}

	int InvokeTypeFunc(UntypedNode node) {
		return -1; // Todo
	}

	TypedNode TypeMethodCall(KGamma gma, UntypedNode node) {
		return null;
	}

	private final static CommonSyntax baseSyntax = new CommonSyntax();
	final static KSyntax ErrorSyntax = new KSyntax("$Error", 0, baseSyntax, "ParseErrorNode", Precedence_Error, Precedence_Error);
	final static KSyntax IndentSyntax = new KSyntax("$Indent", 0, baseSyntax, "ParseIndent", Precedence_Error, Precedence_Error);
		
}

class CommonSyntax {
	
	public int ParseErrorNode(UntypedNode node, ArrayList<KToken> tokens, int beginIdx, int optIdx, int endIdx) {
//		KToken token = tokens.get(optIdx);
		node.Syntax = KSyntax.ErrorSyntax;
		return endIdx;
	}
	
	public TypedNode TypeErrorNode(KGamma gma, UntypedNode node) {
		return null;
	}

	public int ParseIndent(UntypedNode node, ArrayList<KToken> tokens, int beginIdx, int optIdx, int endIdx) {
////		KToken token = tokens.get(optIdx);
//		node.Syntax = KSyntax.ErrorSyntax;
		return endIdx;
	}

}