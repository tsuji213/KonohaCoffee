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

import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.KLib.KonohaMap;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.SyntaxTree.TypedNode;

public final class KonohaNameSpace implements KonohaConst {

	public Konoha	KonohaContext;
	KonohaNameSpace	ParentNameSpace;
	KonohaArray		ImportedNameSpaceList;

	public KonohaNameSpace(Konoha konoha, KonohaNameSpace ParentNameSpace) {
		this.KonohaContext = konoha;
		this.ParentNameSpace = ParentNameSpace;
		if(ParentNameSpace != null) {
			this.ImportedTokenMatrix = new KonohaFunc[KonohaChar.MAX];
			for(int i = 0; i < KonohaChar.MAX; i++) {
				if(ParentNameSpace.ImportedTokenMatrix[i] != null) {
					this.ImportedTokenMatrix[i] = ParentNameSpace.GetTokenFunc(i).Duplicate();
				}
			}
			if(ParentNameSpace.ImportedSymbolTable != null) {
				this.ImportedSymbolTable = ParentNameSpace.ImportedSymbolTable.Duplicate();
			}
		}
	}

	// class
	public final KonohaType LookupTypeInfo(String ClassName) {
		try {
			return this.KonohaContext.LookupTypeInfo(Class.forName(ClassName));

		}
		catch (ClassNotFoundException e) {
		}
		return null;
	}

	public final KonohaType LookupTypeInfo(Class<?> ClassInfo) {
		return this.KonohaContext.LookupTypeInfo(ClassInfo);
	}

	KonohaFunc MergeFunc(KonohaFunc f, KonohaFunc f2) {
		if(f == null)
			return f2;
		if(f2 == null)
			return f;
		return f.Merge(f2);
	}

	KonohaFunc[]	DefinedTokenMatrix;
	KonohaFunc[]	ImportedTokenMatrix;

	KonohaFunc GetDefinedTokenFunc(int kchar) {
		return (this.DefinedTokenMatrix != null) ? this.DefinedTokenMatrix[kchar] : null;
	}

	KonohaFunc GetTokenFunc(int kchar) {
		if(this.ImportedTokenMatrix == null) {
			return null;
		}
		if(this.ImportedTokenMatrix[kchar] == null) {
			KonohaFunc func = null;
			if(this.ParentNameSpace != null) {
				func = this.ParentNameSpace.GetTokenFunc(kchar);
			}
			func = this.MergeFunc(func, this.GetDefinedTokenFunc(kchar));
			assert (func != null);
			this.ImportedTokenMatrix[kchar] = func;
		}
		return this.ImportedTokenMatrix[kchar];
	}

	public void AddTokenFunc(String keys, Object callee, String name) {
		if(this.DefinedTokenMatrix == null) {
			this.DefinedTokenMatrix = new KonohaFunc[KonohaChar.MAX];
		}
		if(this.ImportedTokenMatrix == null) {
			this.ImportedTokenMatrix = new KonohaFunc[KonohaChar.MAX];
		}
		for(int i = 0; i < keys.length(); i++) {
			int kchar = KonohaChar.FromJavaChar(keys.charAt(i));
			this.DefinedTokenMatrix[kchar] = KonohaFunc.NewFunc(callee, name, this.DefinedTokenMatrix[kchar]);
			this.ImportedTokenMatrix[kchar] = KonohaFunc.NewFunc(callee, name, this.GetTokenFunc(kchar));
		}
	}

	public TokenList Tokenize(String text, long uline) {
		return new KonohaTokenizer(this, text, uline).Tokenize();
	}

	static final String	MacroPrefix		= "@$"; // FIXME: use different symbol tables
	static final String	TopLevelPrefix	= "#";

	// KFunc GetDefinedMacroFunc(String Symbol) {
	// if(DefinedSymbolTable != null) {
	// Object object = DefinedSymbolTable.get(MacroPrefix + Symbol);
	// return (object instanceof KFunc) ? (KFunc)object : null;
	// }
	// return null;
	// }

	KonohaFunc GetMacro(String Symbol, boolean TopLevel) {
		if(TopLevel) {
			Object o = this.GetSymbol(MacroPrefix + TopLevelPrefix + Symbol);
			if(o != null && o instanceof KonohaFunc) {
				return (KonohaFunc) o;
			}
		}
		Object o = this.GetSymbol(MacroPrefix + Symbol);
		return (o instanceof KonohaFunc) ? (KonohaFunc) o : null;
	}

	public void DefineMacro(String Symbol, Object Callee, String MethodName) {
		this.DefineSymbol(MacroPrefix + Symbol, new KonohaFunc(Callee, MethodName, null));
	}

	public void DefineTopLevelMacro(String Symbol, Object Callee, String MethodName) {
		this.DefineSymbol(MacroPrefix + TopLevelPrefix + Symbol, new KonohaFunc(Callee, MethodName, null));
	}

	KonohaMap	DefinedSymbolTable;
	KonohaMap	ImportedSymbolTable;

	Object GetDefinedSymbol(String symbol) {
		return (this.DefinedSymbolTable != null) ? this.DefinedSymbolTable.get(symbol) : null;
	}

	public Object GetSymbol(String symbol) {
		return this.ImportedSymbolTable.get(symbol);
	}

	public void DefineSymbol(String Symbol, Object Value) {
		if(this.DefinedSymbolTable == null) {
			this.DefinedSymbolTable = new KonohaMap();
		}
		this.DefinedSymbolTable.put(Symbol, Value);
		if(this.ImportedSymbolTable == null) {
			this.ImportedSymbolTable = new KonohaMap();
		}
		this.ImportedSymbolTable.put(Symbol, Value);
	}

	public KonohaSyntax GetSyntax(String symbol) {
		Object o = this.GetSymbol(symbol);
		return (o instanceof KonohaSyntax) ? (KonohaSyntax) o : null;
	}

	public KonohaSyntax GetSyntax(String symbol, boolean TopLevel) {
		if(TopLevel) {
			Object o = this.GetSymbol(TopLevelPrefix + symbol);
			if(o != null && o instanceof KonohaSyntax)
				return (KonohaSyntax) o;
		}
		Object o = this.GetSymbol(symbol);
		return (o instanceof KonohaSyntax) ? (KonohaSyntax) o : null;
	}

	public void AddSyntax(KonohaSyntax Syntax, boolean TopLevel) {
		Syntax.PackageNameSpace = this;
		Syntax.ParentSyntax = this.GetSyntax(Syntax.SyntaxName, TopLevel);
		String Key = (TopLevel) ? TopLevelPrefix + Syntax.SyntaxName : Syntax.SyntaxName;
		this.DefineSymbol(Key, Syntax);
	}

	public void DefineSyntax(String SyntaxName, int flag, Object Callee, String MethodName) {
		this.DefineSyntax(SyntaxName, flag, Callee, MethodName, MethodName);
	}

	public void DefineSyntax(String SyntaxName, int flag, Object Callee, String ParseMethod, String TypeMethod) {
		this.AddSyntax(new KonohaSyntax(SyntaxName, flag, Callee, "Parse" + ParseMethod, "Type" + TypeMethod), false);
	}

	// Global Object
	public KonohaObject CreateGlobalObject(int ClassFlag, String ShortName) {
		KonohaType NewClass = new KonohaType(this.KonohaContext, ClassFlag, ShortName, null);
		KonohaObject GlobalObject = new KonohaObject(NewClass);
		NewClass.DefaultNullValue = GlobalObject;
		return GlobalObject;
	}

	public KonohaObject GetGlobalObject() {
		Object GlobalObject = this.GetDefinedSymbol(GlobalConstName);
		if(GlobalObject == null || !(GlobalObject instanceof KonohaObject)) {
			GlobalObject = this.CreateGlobalObject(SingletonClass, "*GlobalType*");
			this.DefineSymbol(GlobalConstName, GlobalObject);
		}
		return (KonohaObject) GlobalObject;
	}

	public void ImportNameSpace(KonohaNameSpace ns) {
		if(this.ImportedNameSpaceList == null) {
			this.ImportedNameSpaceList = new KonohaArray();
			this.ImportedNameSpaceList.add(ns);
		}
		if(this.ImportedTokenMatrix == null) {
			this.ImportedTokenMatrix = new KonohaFunc[KonohaChar.MAX];
		}

		if(ns.DefinedTokenMatrix != null) {
			for(int i = 0; i < KonohaChar.MAX; i++) {
				if(ns.DefinedTokenMatrix[i] != null) {
					this.ImportedTokenMatrix[i] = this.MergeFunc(this.GetTokenFunc(i), ns.DefinedTokenMatrix[i]);
				}
			}
		}
		// if(ns.DefinedSymbolTable != null) {
		// Set<Entry<String,Object>> data = DefinedSymbolTable.entrySet();
		// }
	}

	public int PreProcess(TokenList tokenList, int BeginIdx, int EndIdx, TokenList BufferList) {
		return new LexicalConverter(this, /* TopLevel */true, /* SkipIndent */false)
				.Do(tokenList, BeginIdx, EndIdx, BufferList);
	}

	String GetSourcePosition(long uline) {
		return "(eval:" + (int) uline + ")";
	}

	public String Message(int Level, KonohaToken Token, String Message) {
		if(!Token.IsErrorToken()) {
			if(Level == Error) {
				Message = "(error) " + this.GetSourcePosition(Token.uline) + " " + Message;
				Token.SetErrorMessage(Message);
			} else if(Level == Warning) {
				Message = "(warning) " + this.GetSourcePosition(Token.uline) + " " + Message;
			} else if(Level == Info) {
				Message = "(info) " + this.GetSourcePosition(Token.uline) + " " + Message;
			}
			System.out.println(Message);
			return Message;
		}
		return Token.GetErrorMessage();
	}

	public Object Eval(String text, long uline) {
		Object ResultValue = null;
		System.out.println("Eval: " + text);
		TokenList BufferList = this.Tokenize(text, uline);
		int next = BufferList.size();
		this.PreProcess(BufferList, 0, next, BufferList);
		UntypedNode UNode = UntypedNode.ParseNewNode(this, null, BufferList, next, BufferList.size(), AllowEmpty);
		System.out.println("untyped tree: " + UNode);
		while(UNode != null) {
			TypeEnv Gamma = new TypeEnv(this, null);
			TypedNode TNode = TypeEnv.TypeCheckEachNode(Gamma, UNode, Gamma.VoidType, DefaultTypeCheckPolicy);
			KonohaBuilder Builder = this.GetBuilder();
			ResultValue = Builder.EvalAtTopLevel(TNode);
			UNode = UNode.NextNode;
		}
		return ResultValue;
	}

	// Builder

	public KonohaBuilder	Builder;

	public KonohaBuilder GetBuilder() {
		if(this.Builder == null) {
			if(this.ParentNameSpace != null) {
				return this.ParentNameSpace.GetBuilder();
			}
			this.Builder = new DefaultKonohaBuilder(); // create default builder
		}
		return this.Builder;
	}

	public boolean LoadBuilder(String Name) {
		Class<?> BuilderClass;
		try {
			BuilderClass = Class.forName(Name);
			this.Builder = (KonohaBuilder) BuilderClass.newInstance();
			return true;
		}
		catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public KonohaMethod LookupMethod(String MethodName, int ParamSize) {
		return null;
	}
}

class KonohaTokenizer implements KonohaConst {
	KonohaNameSpace	ns;
	String			SourceText;
	long			CurrentLine;
	TokenList		SourceList;

	KonohaTokenizer(KonohaNameSpace ns, String text, long CurrentLine) {
		this.ns = ns;
		this.SourceText = text;
		this.CurrentLine = CurrentLine;
		this.SourceList = new TokenList();
	}

	int TokenizeFirstToken(TokenList tokenList) {
		return 0;
	}

	int StampLine(int StartIdx) {
		for(int i = StartIdx; i < this.SourceList.size(); i++) {
			KonohaToken token = this.SourceList.get(i);
			if(token.ResolvedSyntax == KonohaSyntax.IndentSyntax) {
				this.CurrentLine = this.CurrentLine + 1;
			}
			token.uline = this.CurrentLine;
		}
		return this.SourceList.size();
	}

	int DispatchFunc(int KonohaChar, int pos) {
		KonohaFunc FuncStack = this.ns.GetTokenFunc(KonohaChar);
		int UnusedIdx = this.SourceList.size();
		while(FuncStack != null) {
			int NextIdx = FuncStack.InvokeTokenFunc(this.ns, this.SourceText, pos, this.SourceList);
			if(NextIdx != -1) {
				UnusedIdx = this.StampLine(UnusedIdx);
				return NextIdx;
			}
			FuncStack = FuncStack.Pop();
		}
		KonohaToken Token = new KonohaToken(this.SourceText.substring(pos));
		Token.uline = this.CurrentLine;
		this.SourceList.add(Token);
		this.ns.Message(Error, Token, "undefined token: " + Token.ParsedText);
		return this.SourceText.length();
	}

	TokenList Tokenize() {
		int pos = 0, len = this.SourceText.length();
		pos = this.TokenizeFirstToken(this.SourceList);
		while(pos < len) {
			int kchar = KonohaChar.FromJavaChar(this.SourceText.charAt(pos));
			int pos2 = this.DispatchFunc(kchar, pos);
			if(!(pos < pos2))
				break;
			pos = pos2;
		}
		// KToken.DumpTokenList(SourceList);
		return this.SourceList;
	}

}
