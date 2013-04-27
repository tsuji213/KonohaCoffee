/**
 * 
 */
package org.KonohaScript;

/**
 * @author kiki
 *
 */

import java.util.ArrayList;

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
	// KMethodFunc invokeKMethodFunc;
	// union {
	// struct KVirtualCode *vcode_start;
	// struct KVirtualCodeAPI **virtualCodeApi_plus1;
	// };
	int flag;
	int packageId;
	int typeId;
	int mn;
	int symbol;
	KParam param;
	KParam signature;
	String Source;
	long uline;
	KNameSpace LazyCompileNameSpace;
}

// Runtime

// #define kContext_Debug ((khalfflag_t)(1<<0))
// #define kContext_Interactive ((khalfflag_t)(1<<1))
// #define kContext_CompileOnly ((khalfflag_t)(1<<2))
// #define kContext_Test ((khalfflag_t)(1<<3))
// #define kContext_Trace ((khalfflag_t)(1<<4))
//
// #define KonohaContext_Is(P, X) (KFlag_Is(khalfflag_t,(X)->stack->flag,
// kContext_##P))
// #define KonohaContext_Set(P, X) KFlag_Set1(khalfflag_t, (X)->stack->flag,
// kContext_##P)

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
	ArrayList<KClass> ClassList;
	KKeyIdMap LongClassNameMap;
	ArrayList<String> FileIdList;
	KKeyIdMap FileIdMap;
	ArrayList<KPackage> PackageList;
	KKeyIdMap PackageMap;
	ArrayList<String> SymbolList;
	KKeyIdMap SymbolMap;
	ArrayList<KParam> ParamList;
	KParamMap ParamMap;
	ArrayList<KParam> SignatureList;
	KParamMap SignatureMap;

	KSymbolTable() {
		this.ClassList = new ArrayList<KClass>(64);
		this.FileIdList = new ArrayList<String>(16);
		this.PackageList = new ArrayList<KPackage>(16);
		this.SymbolList = new ArrayList<String>(64);
		this.ParamList = new ArrayList<KParam>(64);
		this.SignatureList = new ArrayList<KParam>(64);
		this.LongClassNameMap = new KKeyIdMap();
		this.FileIdMap = new KKeyIdMap();
		this.PackageMap = new KKeyIdMap();
		this.SymbolMap = new KKeyIdMap();
		this.ParamMap = new KParamMap();
		this.SignatureMap = new KParamMap();
	}

	void Init(Konoha kctx) {
		KPackage defaultPackage = NewPackage(kctx, "Konoha");
		NewClass(kctx, defaultPackage, "void");
	}

	int GetFileId(String file) {
		int id = this.FileIdMap.GetId(file);
		if (id == -1) {
			id = this.FileIdList.size();
			this.FileIdList.add(file);
		}
		return id;
	}

	int GetSymbol(String key, boolean isnew) {
		int id = this.SymbolMap.GetId(key);
		if (id == -1 && isnew) {
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

	KNameSpace RootNameSpace;
	KNameSpace DefaultNameSpace;
	KSymbolTable SymbolTable;

	public Konoha(MiniKonoha defaultSyntax) {
		this.SymbolTable = new KSymbolTable();
		this.SymbolTable.Init(this);
		RootNameSpace = new KNameSpace(this, null);
		defaultSyntax.LoadDefaultSyntax(RootNameSpace);
		this.DefaultNameSpace = new KNameSpace(this, RootNameSpace);
	}

	public int GetSymbol(String key, boolean isnew) {
		return this.SymbolTable.GetSymbol(key, isnew);
	}

	public void Eval(String text, long uline) {
		System.out.println("Eval: " + text);
		DefaultNameSpace.Tokenize(text, uline);
		// Preprocess();
		// Parse();
		// CodeGenerator();
	}

	public void Load(String fileName) {
		// System.out.println("Eval: " + text);
		// DefaultNameSpace.Tokenize(text, uline);
	}

	public static void main(String[] argc) {
		MiniKonoha defaultSyntax = new MiniKonoha();
		Konoha konoha = new Konoha(defaultSyntax);
		konoha.Eval("int fibo(int n) {}", 1);

	}
}
