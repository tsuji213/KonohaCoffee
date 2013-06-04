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
import org.KonohaScript.KLib.*;

import org.KonohaScript.SyntaxTree.TypedNode;

public final class KonohaNameSpace implements KonohaConst {

	public Konoha   KonohaContext;
	KonohaNameSpace ParentNameSpace;
	KonohaArray     ImportedNameSpaceList;

	KonohaNameSpace(Konoha konoha, KonohaNameSpace ParentNameSpace) {
		this.KonohaContext = konoha;
		this.ParentNameSpace = ParentNameSpace;
		if(ParentNameSpace != null) {
			ImportedTokenMatrix = new KonohaFunc[KonohaChar.MAX];
			for(int i = 0; i < KonohaChar.MAX; i++) {
				if(ParentNameSpace.ImportedTokenMatrix[i] != null) {
					ImportedTokenMatrix[i] = ParentNameSpace.GetTokenFunc(i).Duplicate();
				}
			}
			if(ParentNameSpace.ImportedSymbolTable != null) {
				ImportedSymbolTable = (KonohaMap)ParentNameSpace.ImportedSymbolTable.Duplicate();
			}
		}
	}
	
	// class
	public final KonohaType LookupTypeInfo(String ClassName) {
		try {
			return KonohaContext.LookupTypeInfo(Class.forName(ClassName));

		}
		catch(ClassNotFoundException e) {
		}
		return null;
	}

	public final KonohaType LookupTypeInfo(Class<?> ClassInfo) {
		return KonohaContext.LookupTypeInfo(ClassInfo);
	}

	KonohaFunc MergeFunc(KonohaFunc f, KonohaFunc f2) {
		if(f == null)
			return f2;
		if(f2 == null)
			return f;
		return f.Merge(f2);
	}

	KonohaFunc[] DefinedTokenMatrix;
	KonohaFunc[] ImportedTokenMatrix;

	KonohaFunc GetDefinedTokenFunc(int kchar) {
		return (DefinedTokenMatrix != null) ? DefinedTokenMatrix[kchar] : null;
	}

	KonohaFunc GetTokenFunc(int kchar) {
		if(ImportedTokenMatrix == null) {
			return null;
		}
		if(ImportedTokenMatrix[kchar] == null) {
			KonohaFunc func = null;
			if(ParentNameSpace != null) {
				func = ParentNameSpace.GetTokenFunc(kchar);
			}
			func = MergeFunc(func, GetDefinedTokenFunc(kchar));
			assert (func != null);
			ImportedTokenMatrix[kchar] = func;
		}
		return ImportedTokenMatrix[kchar];
	}

	public void AddTokenFunc(String keys, Object callee, String name) {
		if(DefinedTokenMatrix == null) {
			DefinedTokenMatrix = new KonohaFunc[KonohaChar.MAX];
		}
		if(ImportedTokenMatrix == null) {
			ImportedTokenMatrix = new KonohaFunc[KonohaChar.MAX];
		}
		for(int i = 0; i < keys.length(); i++) {
			int kchar = KonohaChar.JavaCharToKonohaChar(keys.charAt(i));
			DefinedTokenMatrix[kchar] = KonohaFunc.NewFunc(callee, name, DefinedTokenMatrix[kchar]);
			ImportedTokenMatrix[kchar] = KonohaFunc.NewFunc(callee, name, GetTokenFunc(kchar));
			//KonohaDebug.P("key="+kchar+", " + name + ", " + GetTokenFunc(kchar));
		}
	}

	public TokenList Tokenize(String text, long uline) {
		return new KonohaTokenizer(this, text, uline).Tokenize();
	}

	static final String MacroPrefix     = "@$";  // FIXME: use different symbol tables
	static final String TopLevelPrefix  = "#";

//	KFunc GetDefinedMacroFunc(String Symbol) {
//		if(DefinedSymbolTable != null) {
//			Object object = DefinedSymbolTable.get(MacroPrefix + Symbol);
//			return (object instanceof KFunc) ? (KFunc)object : null;
//		}
//		return null;
//	}

	KonohaFunc GetMacro(String Symbol, boolean TopLevel) {
		if(TopLevel) {
			Object o = GetSymbol(MacroPrefix + TopLevelPrefix + Symbol);
			if(o != null && o instanceof KonohaFunc) {
				return (KonohaFunc)o;
			}
		}
		Object o = GetSymbol(MacroPrefix + Symbol);
		return (o instanceof KonohaFunc) ? (KonohaFunc) o : null;
	}

	public void DefineMacro(String Symbol, Object Callee, String MethodName) {
		DefineSymbol(MacroPrefix + Symbol, new KonohaFunc(Callee, MethodName, null));
	}

	public void DefineTopLevelMacro(String Symbol, Object Callee, String MethodName) {
		DefineSymbol(MacroPrefix + TopLevelPrefix + Symbol, new KonohaFunc(Callee, MethodName, null));
	}

	KonohaMap DefinedSymbolTable;
	KonohaMap ImportedSymbolTable;

	Object GetDefinedSymbol(String symbol) {
		return (DefinedSymbolTable != null) ? DefinedSymbolTable.get(symbol) : null;
	}

	public Object GetSymbol(String symbol) {
		return ImportedSymbolTable.get(symbol);
	}

	public void DefineSymbol(String Symbol, Object Value) {
		if(DefinedSymbolTable == null) {
			DefinedSymbolTable = new KonohaMap();
		}
		DefinedSymbolTable.put(Symbol, Value);
		if(ImportedSymbolTable == null) {
			ImportedSymbolTable = new KonohaMap();
		}
		ImportedSymbolTable.put(Symbol, Value);
	}

	public KonohaSyntax GetSyntax(String symbol) {
		Object o = GetSymbol(symbol);
		return (o instanceof KonohaSyntax) ? (KonohaSyntax) o : null;
	}

	public KonohaSyntax GetSyntax(String symbol, boolean TopLevel) {
		if(TopLevel) {
			Object o = GetSymbol(TopLevelPrefix + symbol);
			if(o != null && o instanceof KonohaSyntax) return (KonohaSyntax)o;
		}
		Object o = GetSymbol(symbol);
		return (o instanceof KonohaSyntax) ? (KonohaSyntax) o : null;
	}

	public void AddSyntax(KonohaSyntax Syntax, boolean TopLevel) {
		Syntax.PackageNameSpace = this;
		Syntax.ParentSyntax = GetSyntax(Syntax.SyntaxName, TopLevel);
		String Key = (TopLevel) ? TopLevelPrefix + Syntax.SyntaxName : Syntax.SyntaxName;
		DefineSymbol(Key, Syntax);
	}

	public void DefineSyntax(String SyntaxName, int flag, Object Callee, String MethodName) {
		AddSyntax(new KonohaSyntax(SyntaxName, flag, Callee, "Parse" + MethodName, "Type" + MethodName), false);
	}

	public void DefineSyntax(String SyntaxName, int flag, Object Callee, String ParseMethod, String TypeMethod) {
		AddSyntax(new KonohaSyntax(SyntaxName, flag, Callee, "Parse" + ParseMethod, "Type" + TypeMethod), false);
	}

	// Global Object
	public KonohaObject CreateGlobalObject(int ClassFlag, String ShortName) {
		KonohaType NewClass = new KonohaType(KonohaContext, null, ClassFlag, ShortName, null);
		KonohaObject GlobalObject = new KonohaObject(NewClass);
		NewClass.DefaultNullValue = GlobalObject;
		return GlobalObject;
	}

	public KonohaObject GetGlobalObject() {
		Object GlobalObject = GetDefinedSymbol(GlobalConstName);
		if(GlobalObject == null || !(GlobalObject instanceof KonohaObject)) {
			GlobalObject = CreateGlobalObject(SingletonClass, "*GlobalType*");
			DefineSymbol(GlobalConstName, GlobalObject);
		}
		return (KonohaObject)GlobalObject;
	}

	public void ImportNameSpace(KonohaNameSpace ns) {
		if(ImportedNameSpaceList == null) {
			ImportedNameSpaceList = new KonohaArray();
			ImportedNameSpaceList.add(ns);
		}
		if(ImportedTokenMatrix == null) {
			ImportedTokenMatrix = new KonohaFunc[KonohaChar.MAX];
		}

		if(ns.DefinedTokenMatrix != null) {
			for(int i = 0; i < KonohaChar.MAX; i++) {
				if(ns.DefinedTokenMatrix[i] != null) {
					ImportedTokenMatrix[i] = MergeFunc(GetTokenFunc(i), ns.DefinedTokenMatrix[i]);
				}
			}
		}
		// if(ns.DefinedSymbolTable != null) {
		// Set<Entry<String,Object>> data = DefinedSymbolTable.entrySet();
		// }
	}

	public int PreProcess(TokenList tokenList, int BeginIdx, int EndIdx, TokenList BufferList) {
		return new LexicalConverter(this, /*TopLevel*/true, /*SkipIndent*/false).Do(tokenList, BeginIdx, EndIdx, BufferList);
	}

	String GetSourcePosition(long uline) {
		return "(eval:" + (int) uline + ")";
	}

	public String Message(int Level, KonohaToken Token, String Message) {
		if(!Token.IsErrorToken()) {
			if(Level == Error) {
				Message = "(error) " + GetSourcePosition(Token.uline) + " " + Message;
				Token.SetErrorMessage(Message);
			} else if(Level == Warning) {
				Message = "(warning) " + GetSourcePosition(Token.uline) + " " + Message;
			} else if(Level == Info) {
				Message = "(info) " + GetSourcePosition(Token.uline) + " " + Message;
			}
			System.out.println(Message);
			return Message;
		}
		return Token.GetErrorMessage();
	}
		
	public Object Eval(String text, long uline) {
		Object ResultValue = null;
		System.out.println("Eval: " + text);
		TokenList BufferList = Tokenize(text, uline);
		int next = BufferList.size();
		PreProcess(BufferList, 0, next, BufferList);
		UntypedNode UNode = UntypedNode.ParseNewNode(this, null, BufferList, next, BufferList.size(), AllowEmpty);
		System.out.println("untyped tree: " + UNode);
		while(UNode != null) {
			TypeEnv Gamma = new TypeEnv(this, null);
			TypedNode TNode = TypeEnv.TypeCheckEachNode(Gamma, UNode, Gamma.VoidType, DefaultTypeCheckPolicy);
			KonohaBuilder Builder = GetBuilder();
			ResultValue = Builder.EvalAtTopLevel(TNode);
			UNode = UNode.NextNode;
		}
		return ResultValue;
	}

	// Builder 

	public KonohaBuilder Builder;

	public KonohaBuilder GetBuilder() {
		if(Builder == null)  {
			if(ParentNameSpace != null) {
				return ParentNameSpace.GetBuilder();
			}
			Builder = (KonohaBuilder) new DefaultKonohaBuilder(); // create default builder
		}
		return Builder;
	}

	public boolean LoadBuilder(String Name) {
		Class<?> BuilderClass;
		try {
			BuilderClass = Class.forName(Name);
			this.Builder = (KonohaBuilder)BuilderClass.newInstance();
			return true;
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}

class KonohaTokenizer implements KonohaConst {
	KonohaNameSpace ns;
	String SourceText;
	long CurrentLine;
	TokenList SourceList;

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
		for(int i = StartIdx; i < SourceList.size(); i++) {
			KonohaToken token = SourceList.get(i);
			if(token.ResolvedSyntax == KonohaSyntax.IndentSyntax) {
				CurrentLine = CurrentLine + 1;
			}
			token.uline = CurrentLine;
		}
		return SourceList.size();
	}

	int DispatchFunc(int KonohaChar, int pos) {
		KonohaFunc FuncStack = ns.GetTokenFunc(KonohaChar);
		int UnusedIdx = SourceList.size();
		while(FuncStack != null) {
			int NextIdx = FuncStack.InvokeTokenFunc(ns, SourceText, pos, SourceList);
			if(NextIdx != -1) {
				UnusedIdx = StampLine(UnusedIdx);
				return NextIdx;
			}
			FuncStack = FuncStack.Pop();
		}
		KonohaToken Token = new KonohaToken(SourceText.substring(pos));
		Token.uline = CurrentLine;
		SourceList.add(Token);
		ns.Message(Error, Token, "undefined token: " + Token.ParsedText);
		return SourceText.length();
	}

	TokenList Tokenize() {
		int pos = 0, len = SourceText.length();
		pos = TokenizeFirstToken(SourceList);
		while(pos < len) {
			int kchar = KonohaChar.JavaCharToKonohaChar(SourceText.charAt(pos));
			int pos2 = DispatchFunc(kchar, pos);
			if(!(pos < pos2))
				break;
			pos = pos2;
		}
		//KToken.DumpTokenList(SourceList);
		return SourceList;
	}

}
