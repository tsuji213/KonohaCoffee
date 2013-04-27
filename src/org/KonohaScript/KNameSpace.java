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

public final class KNameSpace implements KonohaParserConst {
	Konoha konoha;
	int packageId;
	// int syntaxOption;
	KNameSpace ParentNameSpace;
	ArrayList<KNameSpace> ImportedNameSpaceList;

	// kObject *globalObjectNULL;
	// kArray *methodList_OnList; // default K_EMPTYARRAY
	// size_t sortedMethodList;
	// the below references are defined in sugar
	// const struct KBuilderAPI *builderApi;

	KNameSpace(Konoha konoha, KNameSpace parent) {
		this.konoha = konoha;
		this.ParentNameSpace = parent;

		if(parent != null) {
			ImportedTokenMatrix = new KFunc[KonohaChar.MAX];
			for (int i = 0; i < KonohaChar.MAX; i++) {
				if (parent.ImportedTokenMatrix[i] != null) {
					ImportedTokenMatrix[i] = parent.GetTokenFunc(i).Duplicate();
				}
			}
		}
	}

	KFunc MergeFunc(KFunc f, KFunc f2) {
		if (f == null)
			return f2;
		if (f2 == null)
			return f;
		return f.Merge(f2);
	}

	KFunc[] DefinedTokenMatrix;
	KFunc[] ImportedTokenMatrix;

	KFunc GetDefinedTokenFunc(int kchar) {
		return (DefinedTokenMatrix != null) ? DefinedTokenMatrix[kchar] : null;
	}

	KFunc GetTokenFunc(int kchar) {
		if (ImportedTokenMatrix == null) {
			return null;
		}
		if (ImportedTokenMatrix[kchar] == null) {
			KFunc func = null;
			if (ParentNameSpace != null) {
				func = ParentNameSpace.GetTokenFunc(kchar);
			}
			func = MergeFunc(func, GetDefinedTokenFunc(kchar));
			assert (func != null);
			ImportedTokenMatrix[kchar] = func;
		}
		return ImportedTokenMatrix[kchar];
	}

	public void AddTokenFunc(String keys, Object callee, String name) {
		if (DefinedTokenMatrix == null) {
			DefinedTokenMatrix = new KFunc[KonohaChar.MAX];
		}
		if (ImportedTokenMatrix == null) {
			ImportedTokenMatrix = new KFunc[KonohaChar.MAX];
		}
		for (int i = 0; i < keys.length(); i++) {
			int kchar = KonohaChar.JavaCharToKonohaChar(keys.charAt(i));
			DefinedTokenMatrix[kchar] = KFunc.NewFunc(callee, name, DefinedTokenMatrix[kchar]);
			ImportedTokenMatrix[kchar] = KFunc.NewFunc(callee, name, GetTokenFunc(kchar));
			KonohaDebug.P("key="+kchar+", " + name + ", " + GetTokenFunc(kchar));
		}
	}

	public ArrayList<KToken> Tokenize(String text, long uline) {
		return new KTokenizer(this, text, uline).Tokenize();
	}

	
	HashMap<String, Object> DefinedSymbolTable;
	HashMap<String, Object> ImportedSymbolTable;

	Object GetDefinedSymbol(String symbol) {
		return (DefinedSymbolTable != null) ? DefinedSymbolTable.get(symbol) : null;
	}

	public Object GetSymbol(String symbol) {
		if (ImportedSymbolTable == null) {
			Object o = GetDefinedSymbol(symbol);
			if (o != null) {
				ImportedSymbolTable = new HashMap<String, Object>();
				ImportedSymbolTable.put(symbol, o);
			}
			return o;
		}
		return ImportedSymbolTable.get(symbol);
	}

	public void AddSymbol(String symbol, Object constValue) {
		if (DefinedSymbolTable == null) {
			DefinedSymbolTable = new HashMap<String, Object>();
		}
		DefinedSymbolTable.put(symbol, constValue);
		if (ImportedSymbolTable != null) {
			ImportedSymbolTable.put(symbol, constValue);
		}
	}

//	HashMap<String, KFunc> DefinedSymbolTable;
//	HashMap<String, KFunc> ImportedSymbolTable;

	KFunc GetDefinedMacroFunc(String symbol) {
		if(DefinedSymbolTable != null) {
			Object object = DefinedSymbolTable.get(symbol);
			return (object instanceof KFunc) ? (KFunc)object : null;
		}
		return null;
	}

	KFunc GetMacroFunc(String symbol) {		
		Object o = GetSymbol(symbol);
		return (o instanceof KFunc) ? (KFunc) o : null;
	}

	public void AddMacroFunc(String symbol, Object callee, String name) {
		AddSymbol(symbol, new KFunc(callee, name, null));
	}

	public KSyntax GetSyntax(String symbol) {
		Object o = GetSymbol(symbol);
		return (o instanceof KSyntax) ? (KSyntax) o : null;
	}

	public void AddSyntax(String symbol, KSyntax syntax) {
		syntax.packageNameSpace = this;
		syntax.prev = GetSyntax(symbol);
		AddSymbol(symbol, syntax);
	}

	public void ImportNameSpace(KNameSpace ns) {
		if(ImportedNameSpaceList == null) {
			ImportedNameSpaceList = new ArrayList<KNameSpace>();
			ImportedNameSpaceList.add(ns);
		}
		if (ImportedTokenMatrix == null) {
			ImportedTokenMatrix = new KFunc[KonohaChar.MAX];
		}

		if(ns.DefinedTokenMatrix != null) {
			for (int i = 0; i < KonohaChar.MAX; i++) {
				if (ns.DefinedTokenMatrix[i] != null) {
					ImportedTokenMatrix[i] = MergeFunc(GetTokenFunc(i), ns.DefinedTokenMatrix[i]);
				}
			}
		}
		// if(ns.DefinedSymbolTable != null) {
		// Set<Entry<String,Object>> data = DefinedSymbolTable.entrySet();
		// }
	}

	public int PreProcess(ArrayList<KToken> tokenList, int beginIdx, int endIdx, ArrayList<KToken> BufferList) {
		return new LexicalConverter(this).Do(tokenList, beginIdx, endIdx, BufferList);
	}

	UntypedNode ParseNode(ArrayList<KToken> tokenList, int beginIdx, int endIdx) {
		return null;
	}

	UntypedNode ParseNewNode(String beforeToken, ArrayList<KToken> tokens, int beginIdx, int endIdx, int level) {
		UntypedNode node = new UntypedNode(this);
		node.ParseNode(beforeToken, tokens, beginIdx, endIdx, level);
		return node;
	}

	UntypedNode ParseNewGroupNode(String beforeToken, KToken groupToken, int level) {
		ArrayList<KToken> groups = groupToken.GetGroupTokenList();
		return ParseNewNode(beforeToken, groups, 0, groups.size(), level);
	}

	// TypedNode Type(KGamma gma, UntypedNode node) {
	// return node.Syntax.InvokeTypeFunc(gma, node);
	// }

	void Eval(String text, long uline) {
		ArrayList<KToken> tokens = Tokenize(text, uline);
		KToken.DumpTokenList(tokens);
	}

	String GetSourcePosition(long uline) {
		return "(eval:" + (int) uline + ")";
	}

	public void Message(int level, KToken token, String msg) {
		if (!token.IsErrorToken()) {
			if (level == Error) {
				msg = "(error) " + GetSourcePosition(token.uline) + " " + msg;
				token.SetErrorMessage(msg);
			} else if (level == Warning) {
				msg = "(warning) " + GetSourcePosition(token.uline) + " " + msg;
			} else if (level == Info) {
				msg = "(info) " + GetSourcePosition(token.uline) + " " + msg;
			}
			System.out.println(msg);
		}
	}
}

class KTokenizer implements KonohaParserConst {
	KNameSpace ns;
	String Source;
	long currentLine;
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
		while (fstack != null) {
			int next = fstack.InvokeTokenFunc(ns, Source, pos, SourceTokenList);
			if (next != -1) {
				return next;
			}
			fstack = fstack.Pop();
		}
		KonohaDebug.P("key="+kchar+", " + ns.GetTokenFunc(kchar));
		KToken token = new KToken(Source.substring(pos));
		ns.Message(Error, token, "undefined token: " + token.ParsedText);
		SourceTokenList.add(token);
		return Source.length();
	}

	ArrayList<KToken> Tokenize() {
		int pos = 0, len = Source.length();
		pos = TokenizeFirstToken(SourceTokenList);
		while (pos < len) {
			int kchar = KonohaChar.JavaCharToKonohaChar(Source.charAt(pos));
			int pos2 = DispatchFunc(kchar, pos);
			if (!(pos < pos2))
				break;
			pos = pos2;
		}
		for (int i = 0; i < SourceTokenList.size(); i++) {
			KToken token = SourceTokenList.get(i);
			if (token.ResolvedSyntax == KSyntax.IndentSyntax) {
				currentLine = currentLine + 1;
			}
			token.uline = currentLine;
		}
		KToken.DumpTokenList(SourceTokenList);
		return SourceTokenList;
	}

}
