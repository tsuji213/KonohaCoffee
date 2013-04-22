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
import java.util.ArrayList;

public class KNameSpace {
	KonohaContext konoha;
	KNameSpace                         ParentNULL;
	int packageId;
	int syntaxOption;
	ArrayList<KNameSpace>              ImportedNameSpaceList;
//	KDict                              constTable;
//	kArray                            *metaPatternList;
//	kObject                           *globalObjectNULL;
//	kArray                            *methodList_OnList;   // default K_EMPTYARRAY
//	size_t                             sortedMethodList;
	// the below references are defined in sugar
//	void                              *tokenMatrix;
////	KHashMap                          *syntaxMapNN;
//	const struct KBuilderAPI          *builderApi;
//	KKeyValue                         *typeVariableItems;
//	size_t                             typesize;
//	struct KGammaLocalData            *genv;
	
	KNameSpace(KonohaContext konoha, KNameSpace parent) {
		this.konoha = konoha;
		this.ParentNULL = parent;
	}
	
	KFuncStack[] TokenFuncMatrix;
	KFuncStack[] TokenFuncMatrixCache;

	void AddTokenFunc(int kchar, Object callee, String name) {
		Method method = null; //callee.getClass().getMethod(name, Integer.class);
		if(TokenFuncMatrix == null) {
			TokenFuncMatrix = new KFuncStack[KonohaChar.MAX];
		}
		TokenFuncMatrix[kchar] = new KFuncStack(method, callee, TokenFuncMatrix[kchar]);
	}
	
//	KFuncStack[] MergeTokenFuncMatrix(int kchar, KFuncStack[] cache) {
//		if(TokenFuncMatrix != null) {
//			if(cache[kchar] == null) {
//				cache[kchar] = TokenFuncMatrix[kchar];
//			}
//			else {
//				cache[kchar] = cache[kchar].Merge(TokenFuncMatrix[kchar]);
//			}
//		}
//		return cache;
//	}
	
	KFuncStack GetTokenFunc(int kchar) {
		if(TokenFuncMatrixCache == null) {
			TokenFuncMatrixCache = new KFuncStack[KonohaChar.MAX];
		}
		if(TokenFuncMatrixCache[kchar] == null) {
			TokenFuncMatrixCache[kchar] = TokenFuncMatrix[kchar];
//			for(int i = 0; i < ImportedNameSpaceList.size(); i++) {
//				TokenFuncMatrixCache = ImportedNameSpaceList.get(i).TokenFuncMatrix.MergeTokenFuncMatrix(kchar, TokenFuncMatrixCache);
//			}
		}
		return TokenFuncMatrixCache[kchar];
	}

	ArrayList<KToken> Tokenize(String text, long uline) {
		return new KTokenizer(this, text, uline).Tokenize();
	}

	KMacro GetMacro(int symbol) {
//		KMacro macro = MacroTable.get(symbol);
//		return macro;
		return null;
	}

	void Prep(ArrayList<KToken> tokenList, int beginIdx, int endIdx, ArrayList<KToken> bufferList) {
		int c = beginIdx;
		while(c < endIdx) {
			KToken tk = tokenList.get(c);
			KMacro m = GetMacro(tk.symbol);
			if(m != null) {
				c = m.Prep(this, tokenList, c, endIdx, bufferList);
			}
			else {
				tk.resolvedSyntaxInfo = GetSyntax(tk.symbol);
				bufferList.add(tk);
				c = c + 1;
			}
		}
	}

	KSyntax GetSyntax(int symbol) {
//		KSyntax syntax = SyntaxTable.get(symbol);
//		return syntax;
		return null;
	}

	KNode ParseNode(ArrayList<KToken> tokenList, int beginIdx, int endIdx) {
		return null;
	}
	
	KTypedNode Type(KNode node) {
		return null;
	}
	
	void Eval(String text, long uline) {
		ArrayList<KToken> tokens = Tokenize(text, uline);
		KToken.DumpTokenList(tokens);
	}
	
}

class KFuncStack {
	KFuncStack prev;
	Object callee;
	Method method;

	KFuncStack(Method method, Object callee, KFuncStack prev) {
		this.method = method;
		this.callee = callee;
		this.prev = prev;
	}

	KFuncStack Pop() {
		return this.prev;
	}

	KFuncStack Duplicate() {
		if(prev == null) {
			return new KFuncStack(method, callee, null);
		}
		else {
			return new KFuncStack(method, callee, prev.Duplicate());
		}
	}

	KFuncStack Merge(KFuncStack other) {
		return other.Duplicate().prev = this.Duplicate();
	}

	int InvokeTokenFunc(KNameSpace ns, String source, int pos, ArrayList<KToken> bufferToken) {
		try {
			Integer next = (Integer)method.invoke(callee, ns, source, pos, bufferToken);
			return next.intValue();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}

class KTokenizer {
	KNameSpace ns;
	String     Source;
	long       currentLine;
	ArrayList<KToken> SourceTokenList;

	KTokenizer(KNameSpace ns, String text, long uline) {
		this.ns = ns;
		this.Source = text;
		this.currentLine = uline;
		this.SourceTokenList = new ArrayList<KToken>();
	}

	int TokenizeFirstToken(ArrayList<KToken> tokenList) {
		return 0;
	}

	int DispatchFunc(int kchar, int pos) {
		KFuncStack fstack = ns.GetTokenFunc(kchar);
		while(fstack != null) {
			int next = fstack.InvokeTokenFunc(this.ns, this.Source, pos, this.SourceTokenList);
			if(next != -1) return next;
			fstack = fstack.Pop();
		}
		return 0; //TODO
	}

	ArrayList<KToken> Tokenize() {
		int kchar, pos = 0;
		pos = TokenizeFirstToken(this.SourceTokenList);
		while((kchar = KonohaChar.JavaCharToKonohaChar(Source.charAt(pos))) != 0) {
			int pos2 = DispatchFunc(kchar, pos);
			if(!(pos < pos2)) break;
			pos = pos2; 
		}
		return this.SourceTokenList;
	}

}

