/**
 * 
 */
package org.KonohaScript;

/**
 * @author kiki
 *
 */


import java.util.ArrayList;
//import java.util.HashMap;

import org.KonohaScript.GrammarSet.MiniKonoha;

/* konoha util */

class KParamType {
	int typeId;
	int symbol;
}

class KParam {
	int returnTypeId;
	int paramSize;
	KParamType[] ParamTypeItems;
}

class KMethod {
//	KMethodFunc       invokeKMethodFunc;
//	union {
//		struct KVirtualCode     *vcode_start;
//		struct KVirtualCodeAPI **virtualCodeApi_plus1;
//	};
	int    flag;
	int    packageId;
	int    typeId;
	int    mn;
	int    symbol;
	KParam param;
	KParam signature;
	String Source;
	long   uline;
	KNameSpace LazyCompileNameSpace;
}

class KPackage {
	int              packageId;
	String           PackageName;
	KNameSpace       PackageNameSpace;
	int              kickoutFileId;
	
	KPackage(Konoha kctx, int packageId, String name) {
		this.packageId = packageId;
		this.PackageName = name;
		this.PackageNameSpace = new KNameSpace(kctx, kctx.DefaultNameSpace);
	}
	
}

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

class KSymbolTable {
	ArrayList<KClass>      ClassList;
	KKeyIdMap              LongClassNameMap;
	ArrayList<String>      FileIdList;
	KKeyIdMap              FileIdMap;
	ArrayList<KPackage>    PackageList;
	KKeyIdMap              PackageMap;
	ArrayList<String>      SymbolList;
	KKeyIdMap              SymbolMap;
	ArrayList<KParam>      ParamList;
	KParamMap              ParamMap;
	ArrayList<KParam>      SignatureList;
	KParamMap              SignatureMap;
	
	KSymbolTable() {
		this.ClassList    = new ArrayList<KClass>(64);
		this.FileIdList    = new ArrayList<String>(16);
		this.PackageList   = new ArrayList<KPackage>(16);
		this.SymbolList    = new ArrayList<String>(64);
		this.ParamList     = new ArrayList<KParam>(64);
		this.SignatureList = new ArrayList<KParam>(64);
		this.LongClassNameMap = new KKeyIdMap();
		this.FileIdMap    = new KKeyIdMap();
		this.PackageMap   = new KKeyIdMap();
		this.SymbolMap    = new KKeyIdMap();
		this.ParamMap     = new KParamMap();
		this.SignatureMap = new KParamMap();
	}
	
	void Init(Konoha kctx) {
		// 
		KPackage defaultPackage = NewPackage(kctx, "Konoha");
		NewClass(kctx, defaultPackage, "void");
	}
	
	int GetFileId(String file) {
		int id = this.FileIdMap.GetId(file);
		if(id == -1) {
			id = this.FileIdList.size();
			this.FileIdList.add(file);
		}
		return id;
	}

	int GetSymbol(String key, boolean isnew) {
		int id = this.SymbolMap.GetId(key);
		if(id == -1 && isnew) {
			id = this.SymbolList.size();
			this.SymbolList.add(key);
		}
		return id;
	}
	
	KPackage NewPackage(Konoha kctx, String name) {
		int packageId = this.PackageList.size();
		KPackage p = new KPackage(kctx, packageId, name);
		this.PackageList.add(p);
		return p;
	}
	
	KClass NewClass(Konoha kctx, KPackage p, String name) {
		int classId = this.ClassList.size();
		KClass c = new KClass(kctx, p, classId, name);
		this.ClassList.add(c);
		this.LongClassNameMap.SetId(p.PackageName + "." + name, classId);
		return c;
	}
	
}

public class Konoha {

	KNameSpace DefaultNameSpace;
	KSymbolTable   SymbolTable;
	
	Konoha (MiniKonoha defaultSyntax) {
		this.SymbolTable = new KSymbolTable();
		this.SymbolTable.Init(this);
		this.DefaultNameSpace = new KNameSpace(this, null);
		defaultSyntax.LoadDefaultSyntax(DefaultNameSpace);
	}
	
	public int GetSymbol(String key, boolean isnew) {
		return this.SymbolTable.GetSymbol(key, isnew);
	}
	
	public void Eval(String text, long uline) {
		System.out.println("Eval: " + text);
		DefaultNameSpace.Tokenize(text, uline);
	}

	public void Load(String fileName) {
//		System.out.println("Eval: " + text);
//		DefaultNameSpace.Tokenize(text, uline);
	}

	public static void main(String[] argc) {
		MiniKonoha defaultSyntax = new MiniKonoha();
		Konoha konoha = new Konoha(defaultSyntax);
		konoha.Eval("hello,world", 1);
	}
}

