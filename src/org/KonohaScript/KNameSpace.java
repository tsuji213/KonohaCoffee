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
import java.util.HashMap;

public final class KNameSpace {
	KonohaContext konoha;
	int packageId;
	int syntaxOption;
	KNameSpace                         ParentNULL;
	ArrayList<KNameSpace>              ImportedNameSpaceList;

//	kObject                           *globalObjectNULL;
//	kArray                            *methodList_OnList;   // default K_EMPTYARRAY
//	size_t                             sortedMethodList;
	// the below references are defined in sugar
//	const struct KBuilderAPI          *builderApi;
	
	KNameSpace(KonohaContext konoha, KNameSpace parent) {
		this.konoha = konoha;
		this.ParentNULL = parent;
	}
	
	KFunc MergeFunc(KFunc f, KFunc f2) {
		if(f == null) return f2;
		if(f2 == null) return f;
		return f.Merge(f2);
	}
	
	KFunc[] DefinedTokenMatrix;
	KFunc[] ImportedTokenMatrix;

	KFunc GetDefinedTokenFunc(int kchar) {
		return (DefinedTokenMatrix != null) ? DefinedTokenMatrix[kchar] : null;
	}
	
	KFunc GetTokenFunc(int kchar) {
		if(ImportedTokenMatrix == null) {
			ImportedTokenMatrix = new KFunc[KonohaChar.MAX];
		}
		if(ImportedTokenMatrix[kchar] == null) {
			KFunc func = null;
			if(ParentNULL != null) {
				func = ParentNULL.GetTokenFunc(kchar);
			}
			func = MergeFunc(func, GetDefinedTokenFunc(kchar));
			assert(func != null);
			ImportedTokenMatrix[kchar] = func;
		}
		return ImportedTokenMatrix[kchar];
	}

	void AddTokenFunc(int kchar, Object callee, String name) {
		if(DefinedTokenMatrix == null) {
			DefinedTokenMatrix = new KFunc[KonohaChar.MAX];
		}
		DefinedTokenMatrix[kchar] = new KFunc(callee, name, DefinedTokenMatrix[kchar]);
		ImportedTokenMatrix[kchar] = new KFunc(callee, name, GetTokenFunc(kchar));
	}

	ArrayList<KToken> Tokenize(String text, long uline) {
		return new KTokenizer(this, text, uline).Tokenize();
	}

	HashMap<String, Object> DefinedSymbolTable;
	HashMap<String, Object> ImportedSymbolTable;

	Object GetDefinedSymbol(String symbol) {
		return (DefinedSymbolTable != null) ? DefinedSymbolTable.get(symbol) : null;
	}

	Object GetSymbol(String symbol) {
		if(ImportedSymbolTable == null) {
			Object o = GetDefinedSymbol(symbol);
			if(o != null) {
				ImportedSymbolTable = new HashMap<String, Object>();
				ImportedSymbolTable.put(symbol, o);
			}
			return o;
		}
		return ImportedSymbolTable.get(symbol);
	}

	void AddSymbol(String symbol, Object constValue) {
		if(DefinedSymbolTable == null) {
			DefinedSymbolTable = new HashMap<String, Object>();
		}
		DefinedSymbolTable.put(symbol, constValue);
		if(ImportedSymbolTable != null) {
			ImportedSymbolTable.put(symbol, constValue);
		}
	}

	HashMap<String, KFunc> DefinedMacroTable;
	HashMap<String, KFunc> ImportedMacroTable;

	KFunc GetDefinedMacroFunc(String symbol) {
		return (DefinedMacroTable != null) ? DefinedMacroTable.get(symbol) : null;
	}

	KFunc GetMacroFunc(String symbol) {
		if(ImportedMacroTable == null) {
			KFunc f = GetDefinedMacroFunc(symbol);
			if(f != null) {
				ImportedMacroTable = new HashMap<String, KFunc>();
				ImportedMacroTable.put(symbol, f);
			}
			return f;
		}
		return ImportedMacroTable.get(symbol);
	}

	void AddMacroFunc(String symbol, Object callee, String name) {
		if(DefinedMacroTable == null) {
			DefinedMacroTable = new HashMap<String, KFunc>();
		}
		KFunc f = new KFunc(callee, name, null);
		DefinedMacroTable.put(symbol, f);
		if(ImportedMacroTable != null) {
			ImportedMacroTable.put(symbol, f);
		}
	}

	KSyntax GetSyntax(String symbol) {
		Object o = GetSymbol(symbol);
		return (o instanceof KSyntax) ? (KSyntax)o : null;
	}
	
	void AddSyntax(String symbol, KSyntax syntax) {
		syntax.packageNameSpace = this;
		syntax.prev = GetSyntax(symbol);
		AddSymbol(symbol, syntax);
	}
	
	void ImportNameSpace(KNameSpace ns) {
		if(ImportedNameSpaceList == null) {
			ImportedNameSpaceList = new ArrayList<KNameSpace>();
			ImportedNameSpaceList.add(ns);
		}
		if(ns.DefinedTokenMatrix != null) {
			for(int i = 0; i < KonohaChar.MAX; i++) {
				if(ns.DefinedTokenMatrix[i] != null) {
					ImportedTokenMatrix[i] = MergeFunc(GetTokenFunc(i), ns.DefinedTokenMatrix[i]);
				}
			}
		}
//		if(ns.DefinedSymbolTable != null) {
//			Set<Entry<String,Object>> data = DefinedSymbolTable.entrySet();
//		}		
	}
	
	KSyntax ResolveSyntax(KToken tk) {
		return null; // todo
	}
	
	int Prep(ArrayList<KToken> tokenList, int beginIdx, int endIdx, ArrayList<KToken> bufferList) {
		int c = beginIdx;
		while(c < endIdx) {
			KToken tk = tokenList.get(c);
			if(tk.resolvedSyntaxInfo == null) {
				KFunc macro = GetMacroFunc(tk.text);
				if(macro != null) {
					int nextIdx = macro.InvokeMacroFunc(this, tokenList, c, endIdx, bufferList);
					if(nextIdx == -1) {
						return c + 1;
					}
					c = nextIdx;
					break;
				}
				tk.resolvedSyntaxInfo = ResolveSyntax(tk);
			}
			assert(tk.resolvedSyntaxInfo != null);
			bufferList.add(tk);
			c = c + 1;
		}
		return c;
	}
	
	int MacroOpenParenthesis(KNameSpace ns, ArrayList<KToken> tokenList, int beginIdx, int endIdx, ArrayList<KToken> bufferList) {
		ArrayList<KToken> group = new ArrayList<KToken>();
		int nextIdx = ns.Prep(tokenList, beginIdx+1, endIdx, group);
		KToken lastToken = tokenList.get(nextIdx-1);
		if(!lastToken.equals(")")) {
			// ERROR
		}
		KToken groupToken = null;// TODO
		bufferList.add(groupToken);
		groupToken.resolvedSyntaxInfo = ns.GetSyntax("$()");
		return nextIdx;
	}

	int MacroCloseParenthesis(KNameSpace ns, ArrayList<KToken> tokenList, int beginIdx, int endIdx, ArrayList<KToken> bufferList) {
		return -1;
	}

	
	UntypedNode ParseNode(ArrayList<KToken> tokenList, int beginIdx, int endIdx) {
		return null;
	}
	
//	TypedNode Type(KGamma gma, UntypedNode node) {
//		return node.Syntax.InvokeTypeFunc(gma, node);
//	}
	
	void Eval(String text, long uline) {
		ArrayList<KToken> tokens = Tokenize(text, uline);
		KToken.DumpTokenList(tokens);
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
		KFunc fstack = ns.GetTokenFunc(kchar);
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

