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

import org.KonohaScript.Grammar.MiniKonohaGrammar;
import org.KonohaScript.JUtils.KonohaConst;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.KLib.KonohaMap;
import org.KonohaScript.Parser.KonohaGrammar;

//class KKeyIdMap {
//	int GetId(String key) {
//		return 0;
//	}
//
//	void SetId(String key, int id) {
//
//	}
//}
//
//class KParamMap {
//	int GetId(int hash) {
//		return 0;
//	}
//
//	void SetId(int hash, int id) {
//
//	}
//}
//
//class SymbolTable implements KonohaConst {
//	KonohaArray					ClassList;
//	HashMap<String, KonohaType>	ClassNameMap;
//
//	KonohaArray					PackageList;
//	KKeyIdMap					PackageMap;
//
//	KonohaArray					FileIdList;
//	HashMap<String, Integer>	FileIdMap;
//
//	KonohaArray					SymbolList;
//	HashMap<String, Integer>	SymbolMap;
//
//	KonohaArray					ParamList;
//	KParamMap					ParamMap;
//	KonohaArray					SignatureList;
//	KParamMap					SignatureMap;
//
//	SymbolTable() {
//		this.ClassList = new KonohaArray(64);
//		this.ClassNameMap = new HashMap<String, KonohaType>();
//
//		this.FileIdList = new KonohaArray(16);
//		this.FileIdMap = new HashMap<String, Integer>();
//
//		this.SymbolList = new KonohaArray(64);
//		this.SymbolMap = new HashMap<String, Integer>();
//
//		this.PackageList = new KonohaArray(16);
//		this.ParamList = new KonohaArray(64);
//		this.SignatureList = new KonohaArray(64);
//		this.PackageMap = new KKeyIdMap();
//		this.ParamMap = new KParamMap();
//		this.SignatureMap = new KParamMap();
//	}
//
//	void Init(Konoha kctx) {
//		//this.NewPackage(kctx, "Konoha");
//		// NewClass(kctx, defaultPackage, "void");
//	}
//
//	long GetFileId(String file, int linenum) {
//		Integer fileid = this.FileIdMap.get(file);
//		if(fileid == null) {
//			int id = this.FileIdList.size();
//			this.FileIdList.add(file);
//			this.FileIdMap.put(file, new Integer(id));
//			return ((long) id << 32) | linenum;
//		}
//		return (fileid.longValue() << 32) | linenum;
//	}
//
//	String GetFileName(long uline) {
//		int id = (int) (uline >> 32);
//		return (String) this.FileIdList.get(id);
//	}
//
//	//	KPackage NewPackage(Konoha kctx, String name) {
//	//		int packageId = this.PackageList.size();
//	//		KPackage p = new KPackage(kctx, packageId, name);
//	//		this.PackageList.add(p);
//	//		return p;
//	//	}
//
//	// KClass NewClass(Konoha kctx, KPackage p, String name) {
//	// int classId = this.ClassList.size();
//	// KClass c = new KClass(kctx, p, classId, name);
//	// this.ClassList.add(c);
//	// this.LongClassNameMap.SetId(p.PackageName + "." + name, classId);
//	// return c;
//	// }
//
//}

public final class Konoha implements KonohaConst {

	public KonohaNameSpace	RootNameSpace;
	public KonohaNameSpace	DefaultNameSpace;
	//	SymbolTable				SymbolTable;

	public final KonohaType	VoidType;
	public final KonohaType	ObjectType;
	public final KonohaType	BooleanType;
	public final KonohaType	IntType;
	public final KonohaType	StringType;
	public final KonohaType	VarType;

	final KonohaArray		EmptyList;
	final KonohaMap			ClassNameMap;

	public Konoha(KonohaGrammar Grammar, String BuilderClassName) {
		//		this.SymbolTable = new SymbolTable();
		//		this.SymbolTable.Init(this);

		this.EmptyList = new KonohaArray();
		this.ClassNameMap = new KonohaMap();
		this.RootNameSpace = new KonohaNameSpace(this, null);

		this.VoidType = this.RootNameSpace.LookupHostLangType(Void.class);
		this.ObjectType = this.RootNameSpace.LookupHostLangType(Object.class);
		this.BooleanType = this.RootNameSpace.LookupHostLangType(Boolean.class);
		this.IntType = this.RootNameSpace.LookupHostLangType(Integer.class);
		this.StringType = this.RootNameSpace.LookupHostLangType(String.class);
		this.VarType = this.RootNameSpace.LookupHostLangType(Object.class);

		Grammar.LoadDefaultSyntax(this.RootNameSpace);
		this.DefaultNameSpace = new KonohaNameSpace(this, this.RootNameSpace);
		if(BuilderClassName != null) {
			this.DefaultNameSpace.LoadBuilder(BuilderClassName);
		}
	}

	KonohaType LookupHostLangType(Class<?> ClassInfo) {
		KonohaType TypeInfo = (KonohaType) this.ClassNameMap.get(ClassInfo.getName());
		if(TypeInfo == null) {
			TypeInfo = new KonohaType(this, ClassInfo);
			this.ClassNameMap.put(ClassInfo.getName(), TypeInfo);
		}
		return TypeInfo;
	}

	public void Define(String Symbol, Object Value) {
		this.RootNameSpace.DefineSymbol(Symbol, Value);
	}

	public void Eval(String text, long uline) {
		this.DefaultNameSpace.Eval(text, uline);
	}

	public void Load(String fileName) {
		// TODO (kkuramitsu)
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
