/****************************************************************************
 * Copyright (c) 2013, the Konoha project authors. All rights reserved.
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

import org.KonohaScript.JUtils.HostLang;
import org.KonohaScript.JUtils.KonohaConst;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.Parser.TypeEnv;
import org.KonohaScript.Parser.UntypedNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public class KonohaMethod extends KonohaDef implements KonohaConst {

	public KonohaType			ClassInfo;
	public String				MethodName;
	int							MethodSymbolId;
	int							CanonicalSymbolId;
	public KonohaParam			Param;
	public KonohaMethodInvoker	MethodInvoker;
	public int					MethodFlag;

	// DoLazyComilation();
	KonohaNameSpace				LazyNameSpace;
	TokenList					SourceList;
	//FIXME merge ParsedTree field in SouceList.
	public UntypedNode			ParsedTree;

	@HostLang
	public KonohaMethod(int MethodFlag, KonohaType ClassInfo, String MethodName, KonohaParam Param, Method MethodRef) {
		this.MethodFlag = MethodFlag;
		this.ClassInfo = ClassInfo;
		this.MethodName = MethodName;
		this.MethodSymbolId = KonohaSymbol.GetSymbolId(MethodName);
		this.CanonicalSymbolId = KonohaSymbol.GetCanonicalSymbolId(MethodName);
		this.Param = Param;
		this.MethodInvoker = null;
		if (MethodRef != null) {
			this.MethodInvoker = new NativeMethodInvoker(Param, MethodRef);
		}
		this.ParsedTree = null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.Param.Types[0]);
		builder.append(" ");
		builder.append(this.MethodName);
		builder.append("(");
		for (int i = 0; i < this.Param.ArgNames.length; i++) {
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(this.Param.Types[i + 1]);
			builder.append(" ");
			builder.append(this.Param.ArgNames[i]);
		}
		builder.append(")");
		return builder.toString();
	};


	public boolean Is(int Flag) {
		return ((this.MethodFlag & Flag) == Flag);
	}

	public final KonohaType GetReturnType(KonohaType BaseType) {
		KonohaType ReturnType = this.Param.Types[0];
		return ReturnType;
	}

	public final KonohaType GetParamType(KonohaType BaseType, int ParamIdx) {
		KonohaType ParamType = this.Param.Types[ParamIdx + this.Param.ReturnSize];
		return ParamType;
	}

	public final boolean Match(KonohaMethod Other) {
		return (this.MethodName.equals(Other.MethodName) && this.Param.Match(Other.Param));
	}

	public boolean Match(String MethodName, int ParamSize) {
		// FIXME(ide) implement match function with strict rule
		if (MethodName.equals(this.MethodName)) {
			if (ParamSize == -1) {
				return true;
			}
			if (this.Param.GetParamSize() == ParamSize) {
				return true;
			}
		}
		return false;
	}

	@HostLang
	public Object Eval(Object[] ParamData) {
		//int ParamSize = this.Param.GetParamSize();
		//KonohaDebug.P("ParamSize: " + ParamSize);
		return this.MethodInvoker.Invoke(ParamData);
	}

	public KonohaMethod(int MethodFlag, KonohaType ClassInfo, String MethodName, KonohaParam Param, KonohaNameSpace LazyNameSpace, TokenList SourceList) {
		this(MethodFlag, ClassInfo, MethodName, Param, null);
		this.LazyNameSpace = LazyNameSpace;
		this.SourceList = SourceList;
	}

	public void DoCompilation() {
		if (this.MethodInvoker != null) {
			return;
		}
		UntypedNode UNode = this.ParsedTree;
		KonohaNameSpace NS = this.LazyNameSpace;
		if (UNode == null) {
			TokenList BufferList = new TokenList();
			NS.PreProcess(this.SourceList, 0, this.SourceList.size(), BufferList);
			UNode = NS.Parser.ParseNewNode(NS, null, BufferList, 0, BufferList.size(), AllowEmpty);
			System.out.println("untyped tree: " + UNode);
		}
		TypeEnv Gamma = new TypeEnv(this.LazyNameSpace, this);
		TypedNode TNode = TypeEnv.TypeCheck(Gamma, UNode, Gamma.VoidType, DefaultTypeCheckPolicy);
		KonohaBuilder Builder = this.LazyNameSpace.GetBuilder();
		this.MethodInvoker = Builder.Build(TNode, this);
	}

}
