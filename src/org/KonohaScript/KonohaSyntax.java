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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.KonohaScript.KLib.*;

import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public final class KonohaSyntax implements KonohaConst {

	public KonohaNameSpace PackageNameSpace;
	public String SyntaxName;

	@Override
	public String toString() {
		return SyntaxName;
	}

	int SyntaxFlag;

	public boolean IsBeginTerm() {
		return ((SyntaxFlag & Term) == Term);
	}

	public boolean IsBinaryOperator() {
		return ((SyntaxFlag & BinaryOperator) == BinaryOperator);
	}

	public boolean IsSuffixOperator() {
		return ((SyntaxFlag & SuffixOperator) == SuffixOperator);
	}

	public boolean IsDelim() {
		return ((SyntaxFlag & Precedence_CStyleDelim) == Precedence_CStyleDelim);
	}

	public final static boolean IsFlag(int flag, int flag2) {
		return ((flag & flag2) == flag2);
	}

	public boolean IsLeftJoin(KonohaSyntax Right) {
		int left = this.SyntaxFlag >> PrecedenceShift, right = Right.SyntaxFlag >> PrecedenceShift;
		// System.err.printf("left=%d,%s, right=%d,%s\n", left, this.SyntaxName,
		// right, Right.SyntaxName);
		return (left < right || (left == right && IsFlag(this.SyntaxFlag, LeftJoin) && IsFlag(Right.SyntaxFlag, LeftJoin)));
	}

	public Object ParseObject;
	public Method ParseMethod;
	public Object TypeObject;
	public Method TypeMethod;
	public KonohaSyntax ParentSyntax = null;

	// KSyntax Pop() { return ParentSyntax; }

	public KonohaSyntax(String SyntaxName, int flag, Object Callee, String ParseMethod, String TypeMethod) {
		this.SyntaxName = SyntaxName;
		this.SyntaxFlag = flag;
		this.ParseObject = Callee == null ? this : Callee;
		this.TypeObject = this.ParseObject;
		this.ParseMethod = KonohaFunc.LookupMethod(this.ParseObject, ParseMethod);
		this.TypeMethod = KonohaFunc.LookupMethod(this.TypeObject, TypeMethod);
	}

	private final static CommonSyntax baseSyntax = new CommonSyntax();
	public final static KonohaSyntax ErrorSyntax = new KonohaSyntax("$Error", Precedence_Error, baseSyntax, "ParseErrorNode", null);
	public final static KonohaSyntax IndentSyntax = new KonohaSyntax("$Indent", Precedence_CStyleDelim, baseSyntax, "ParseIndent", null);
	public final static KonohaSyntax EmptySyntax = new KonohaSyntax("$Empty", Precedence_Error, baseSyntax, "ParseValue", null);
	public final static KonohaSyntax TypeSyntax = new KonohaSyntax("$Type", Precedence_CStyleValue, baseSyntax, "ParseIndent", null);
	public final static KonohaSyntax ConstSyntax = new KonohaSyntax("$Const", Precedence_CStyleValue, baseSyntax, "ParseValue", null);
	public final static KonohaSyntax MemberSyntax = new KonohaSyntax("$Member", Precedence_CStyleValue, baseSyntax, "ParseValue", null);
	public final static KonohaSyntax ApplyMethodSyntax = new KonohaSyntax("$ApplyMethod", Precedence_CStyleValue, baseSyntax, "ParseValue", null);

	public boolean IsError() {
		return this == ErrorSyntax;
	}

	int InvokeParseFunc(UntypedNode UNode, KonohaArray TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		try {
			System.err.println("invoking.." + ParseMethod);
			Integer NextId = (Integer) ParseMethod.invoke(ParseObject, UNode, TokenList, BeginIdx, EndIdx, ParseOption);
			return NextId.intValue();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("undefined ParseMethod: " + SyntaxName);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

}

class CommonSyntax {

	public int ParseErrorNode(UntypedNode node, KonohaArray tokens, int BeginIdx, int OpIdx, int EndIdx) {
		// KToken token = tokens.get(OpIdx);
		node.Syntax = KonohaSyntax.ErrorSyntax;
		return EndIdx;
	}

	public TypedNode TypeErrorNode(TypeEnv gma, UntypedNode node) {
		return null;
	}

	public int ParseIndent(UntypedNode node, KonohaArray tokens, int BeginIdx, int OpIdx, int EndIdx) {
		// // KToken token = tokens.get(OpIdx);
		// node.Syntax = KSyntax.ErrorSyntax;
		return EndIdx;
	}

	public int ParseTypeStatement(UntypedNode node, KonohaArray tokens, int BeginIdx, int OpIdx, int EndIdx) {
		// // KToken token = tokens.get(OpIdx);
		// node.Syntax = KSyntax.ErrorSyntax;
		return EndIdx;
	}

	public int ParseValue(UntypedNode node, KonohaArray tokens, int BeginIdx, int OpIdx, int EndIdx) {
		KonohaToken Token = tokens.get(OpIdx);
		return EndIdx;
	}

	public TypedNode TypeValue(TypeEnv Gamma, UntypedNode Node, KonohaType ReqType) {
		KonohaToken KeyToken = Node.KeyToken;
		KonohaType TypeInfo = Node.NodeNameSpace.LookupTypeInfo(KeyToken.ResolvedObject.getClass());
		return new ConstNode(TypeInfo, KeyToken, KeyToken.ResolvedObject);
	}

	public TypedNode TypeSymbol(TypeEnv Gamma, UntypedNode Node, KonohaType ReqType) {
		KonohaType TypeInfo = Gamma.GetLocalType(Node.KeyToken.ParsedText);
		if(TypeInfo != null) {
			return new LocalNode(TypeInfo, Node.KeyToken, Node.KeyToken.ParsedText);
		}
		return Gamma.NewErrorNode(Node.KeyToken, "undefined name: " + Node.KeyToken.ParsedText);
	}
}