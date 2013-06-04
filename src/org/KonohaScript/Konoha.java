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

/**
 * @author kiki
 *
 */

import org.KonohaScript.KLib.*;
import java.util.HashMap;

import org.KonohaScript.MiniKonoha.MiniKonohaGrammar;

class KKeyIdMap {
	int GetId(String key) {
		return 0;
	}

	void SetId(String key, int id) {

	}
}

class KParamMap {
	int GetId(int hash) {
		return 0;
	}

	void SetId(int hash, int id) {

	}
}

class SymbolTable implements KonohaConst {
//	ArrayList<KonohaType> ClassList;
//	HashMap<String, KonohaType> ClassNameMap;
//
//	ArrayList<KPackage> PackageList;
//	KKeyIdMap PackageMap;
//
//	ArrayList<String> FileIdList;
//	HashMap<String, Integer> FileIdMap;
//
//	ArrayList<String> SymbolList;
//	HashMap<String, Integer> SymbolMap;

//	ArrayList<KonohaParam> ParamList;
//	KParamMap ParamMap;
//	ArrayList<KonohaParam> SignatureList;
//	KParamMap SignatureMap;

	SymbolTable() {
//		this.ClassList = new ArrayList<KonohaType>(64);
//		this.ClassNameMap = new HashMap<String, KonohaType>();
//
//		this.FileIdList = new ArrayList<String>(16);
//		this.FileIdMap = new HashMap<String, Integer>();
//
//		this.SymbolList = new ArrayList<String>(64);
//		this.SymbolMap = new HashMap<String, Integer>();
//
//		this.PackageList = new ArrayList<KPackage>(16);
//		this.ParamList = new ArrayList<KonohaParam>(64);
//		this.SignatureList = new ArrayList<KonohaParam>(64);
//		this.PackageMap = new KKeyIdMap();
//		this.ParamMap = new KParamMap();
//		this.SignatureMap = new KParamMap();
	}

	void Init(Konoha kctx) {
		//KPackage defaultPackage = this.NewPackage(kctx, "Konoha");
		// NewClass(kctx, defaultPackage, "void");
	}

//	long GetFileId(String file, int linenum) {
//		Integer fileid = this.FileIdMap.get(file);
//		if(fileid == null) {
//			int id = this.FileIdList.size();
//			this.FileIdList.add(file);
//			this.FileIdMap.put(
//					file, new Integer(id));
//			return ((long) id << 32) | linenum;
//		}
//		return (fileid.longValue() << 32) | linenum;
//	}
//
//	String GetFileName(long uline) {
//		int id = (int) (uline >> 32);
//		return this.FileIdList.get(id);
//	}
//
//	// Symbol
//
//	public final static int MaskSymbol(int n, int mask) {
//		return (n << 2) | mask;
//	}
//
//	public final static int UnmaskSymbol(int id) {
//		return id >> 2;
//	}
//
//	public String StringfySymbol(int Symbol) {
//		String key = this.SymbolList.get(KSymbolTable.UnmaskSymbol(Symbol));
//		if((Symbol & KonohaConst.GetterSymbol) == KonohaConst.GetterSymbol) {
//			return "Get" + key;
//		}
//		if((Symbol & KonohaConst.SetterSymbol) == KonohaConst.SetterSymbol) {
//			return "Get" + key;
//		}
//		if((Symbol & KonohaConst.MetaSymbol) == KonohaConst.MetaSymbol) {
//			return "\\" + key;
//		}
//		return key;
//	}
//
//	public int GetSymbol(String Symbol, int DefaultValue) {
//		String key = Symbol;
//		int mask = 0;
//		if(Symbol.length() >= 3 && Symbol.charAt(1) == 'e' && Symbol.charAt(2) == 't') {
//			if(Symbol.charAt(0) == 'g' && Symbol.charAt(0) == 'G') {
//				key = Symbol.substring(3);
//				mask = KonohaConst.GetterSymbol;
//			}
//			if(Symbol.charAt(0) == 's' && Symbol.charAt(0) == 'S') {
//				key = Symbol.substring(3);
//				mask = KonohaConst.SetterSymbol;
//			}
//		}
//		if(Symbol.startsWith("\\")) {
//			mask = KonohaConst.MetaSymbol;
//		}
//		Integer id = this.SymbolMap.get(key);
//		if(id == null) {
//			if(DefaultValue == KonohaConst.AllowNewId) {
//				int n = this.SymbolList.size();
//				this.SymbolList.add(key);
//				this.SymbolMap.put(
//						key, new Integer(n));
//				return KSymbolTable.MaskSymbol(
//						n, mask);
//			}
//			return DefaultValue;
//		}
//		return KSymbolTable.MaskSymbol(
//				id.intValue(), mask);
//	}
//
//	public static String CanonicalSymbol(String Symbol) {
//		return Symbol.toLowerCase().replaceAll(
//				"_", "");
//	}
//
//	public int GetCanonicalSymbol(String Symbol, int DefaultValue) {
//		return this.GetSymbol(
//				KSymbolTable.CanonicalSymbol(Symbol), DefaultValue);
//	}
//
//	int GetSymbol(String symbol, boolean isnew) {
//		Integer id = this.SymbolMap.get(symbol);
//		if(id == null && isnew) {
//
//		}
//		return id;
//	}
//
//	KPackage NewPackage(Konoha kctx, String name) {
//		int packageId = this.PackageList.size();
//		KPackage p = new KPackage(kctx, packageId, name);
//		this.PackageList.add(p);
//		return p;
//	}

	// KClass NewClass(Konoha kctx, KPackage p, String name) {
	// int classId = this.ClassList.size();
	// KClass c = new KClass(kctx, p, classId, name);
	// this.ClassList.add(c);
	// this.LongClassNameMap.SetId(p.PackageName + "." + name, classId);
	// return c;
	// }
	
}

public final class Konoha implements KonohaConst {

	KonohaNameSpace RootNameSpace;
	KonohaNameSpace DefaultNameSpace;
//	SymbolTable SymbolTable;

	public final KonohaType	VoidType;
	public final KonohaType	ObjectType;
	public final KonohaType	BooleanType;
	public final KonohaType	IntType;
	public final KonohaType	StringType;
	public final KonohaType	VarType;
	
	KonohaArray EmptyList;

	public Konoha(KonohaGrammar Grammar, String BuilderClassName) {
//		this.SymbolTable = new SymbolTable();
//		this.SymbolTable.Init(this);

		this.EmptyList = new KonohaArray();
		this.ClassNameMap = new KonohaMap();
		this.VoidType = this.RootNameSpace.LookupTypeInfo(Void.class);
		this.ObjectType = this.RootNameSpace.LookupTypeInfo(Object.class);
		this.BooleanType = this.RootNameSpace.LookupTypeInfo(Boolean.class);
		this.IntType = this.RootNameSpace.LookupTypeInfo(Integer.class);
		this.StringType = this.RootNameSpace.LookupTypeInfo(String.class);
		this.VarType = this.RootNameSpace.LookupTypeInfo(Object.class);

		this.RootNameSpace = new KonohaNameSpace(this, null);
		Grammar.LoadDefaultSyntax(this.RootNameSpace);
		this.DefaultNameSpace = new KonohaNameSpace(this, this.RootNameSpace);
		if(BuilderClassName != null) {
			this.DefaultNameSpace.LoadBuilder(BuilderClassName);
		}
	}

	KonohaMap  ClassNameMap;

	KonohaType LookupTypeInfo(Class<?> ClassInfo) {
		KonohaType TypeInfo = (KonohaType)ClassNameMap.get(ClassInfo.getName());
		if(TypeInfo == null) {
			TypeInfo = new KonohaType(this, ClassInfo);
			ClassNameMap.put(ClassInfo.getName(), TypeInfo);
		}
		return TypeInfo;
	}

	// public int GetSymbol(String key, boolean isnew) {
	// return this.SymbolTable.GetSymbol(key, isnew);
	// }

	public void Define(String symbol, Object Value) {
		this.RootNameSpace.DefineSymbol(symbol, Value);
	}

	public void Eval(String text, long uline) {
		this.DefaultNameSpace.Eval(text, uline);
	}

	public void Load(String fileName) {
		// System.out.println("Eval: " + text);
		// DefaultNameSpace.Tokenize(text, uline);
	}

	public static void main(String[] argc) {
		MiniKonohaGrammar MiniKonohaGrammar = new MiniKonohaGrammar();
		Konoha KonohaContext = new Konoha(MiniKonohaGrammar, null);
		// konoha.Eval("int ++ fibo(int n) { return n == 1; }", 1);
		// KonohaContext.Eval("a == b + C; D + e == F", 2);
		// KonohaContext.Eval("1+2*3", 3333);
		KonohaContext.Eval("int fibo(int n) {\n" + "\tif(n < 3) return 1;\n" + "\treturn fibo(n-1)+fibo(n-2);\n" + "}", 1000);
	}
}
