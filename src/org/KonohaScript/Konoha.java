/**
 * 
 */
package org.KonohaScript;

/**
 * @author kiki
 *
 */

import java.util.ArrayList;
import java.util.HashMap;

import org.KonohaScript.MiniKonoha.MiniKonohaGrammar;

/* konoha util */

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

class KSymbolTable implements KonohaConst {
	ArrayList<KonohaType> ClassList;
	HashMap<String, KonohaType> ClassNameMap;

	ArrayList<KPackage> PackageList;
	KKeyIdMap PackageMap;

	ArrayList<String> FileIdList;
	HashMap<String, Integer> FileIdMap;

	ArrayList<String> SymbolList;
	HashMap<String, Integer> SymbolMap;

	ArrayList<KonohaParam> ParamList;
	KParamMap ParamMap;
	ArrayList<KonohaParam> SignatureList;
	KParamMap SignatureMap;

	KSymbolTable() {
		this.ClassList = new ArrayList<KonohaType>(64);
		this.ClassNameMap = new HashMap<String, KonohaType>();

		this.FileIdList = new ArrayList<String>(16);
		this.FileIdMap = new HashMap<String, Integer>();

		this.SymbolList = new ArrayList<String>(64);
		this.SymbolMap = new HashMap<String, Integer>();

		this.PackageList = new ArrayList<KPackage>(16);
		this.ParamList = new ArrayList<KonohaParam>(64);
		this.SignatureList = new ArrayList<KonohaParam>(64);
		this.PackageMap = new KKeyIdMap();
		this.ParamMap = new KParamMap();
		this.SignatureMap = new KParamMap();
	}

	void Init(Konoha kctx) {
		KPackage defaultPackage = this.NewPackage(
				kctx, "Konoha");
		// NewClass(kctx, defaultPackage, "void");
	}

	long GetFileId(String file, int linenum) {
		Integer fileid = this.FileIdMap.get(file);
		if(fileid == null) {
			int id = this.FileIdList.size();
			this.FileIdList.add(file);
			this.FileIdMap.put(
					file, new Integer(id));
			return ((long) id << 32) | linenum;
		}
		return (fileid.longValue() << 32) | linenum;
	}

	String GetFileName(long uline) {
		int id = (int) (uline >> 32);
		return this.FileIdList.get(id);
	}

	// Symbol

	public final static int MaskSymbol(int n, int mask) {
		return (n << 2) | mask;
	}

	public final static int UnmaskSymbol(int id) {
		return id >> 2;
	}

	public String StringfySymbol(int Symbol) {
		String key = this.SymbolList.get(KSymbolTable.UnmaskSymbol(Symbol));
		if((Symbol & KonohaConst.GetterSymbol) == KonohaConst.GetterSymbol) {
			return "Get" + key;
		}
		if((Symbol & KonohaConst.SetterSymbol) == KonohaConst.SetterSymbol) {
			return "Get" + key;
		}
		if((Symbol & KonohaConst.MetaSymbol) == KonohaConst.MetaSymbol) {
			return "\\" + key;
		}
		return key;
	}

	public int GetSymbol(String Symbol, int DefaultValue) {
		String key = Symbol;
		int mask = 0;
		if(Symbol.length() >= 3 && Symbol.charAt(1) == 'e' && Symbol.charAt(2) == 't') {
			if(Symbol.charAt(0) == 'g' && Symbol.charAt(0) == 'G') {
				key = Symbol.substring(3);
				mask = KonohaConst.GetterSymbol;
			}
			if(Symbol.charAt(0) == 's' && Symbol.charAt(0) == 'S') {
				key = Symbol.substring(3);
				mask = KonohaConst.SetterSymbol;
			}
		}
		if(Symbol.startsWith("\\")) {
			mask = KonohaConst.MetaSymbol;
		}
		Integer id = this.SymbolMap.get(key);
		if(id == null) {
			if(DefaultValue == KonohaConst.AllowNewId) {
				int n = this.SymbolList.size();
				this.SymbolList.add(key);
				this.SymbolMap.put(
						key, new Integer(n));
				return KSymbolTable.MaskSymbol(
						n, mask);
			}
			return DefaultValue;
		}
		return KSymbolTable.MaskSymbol(
				id.intValue(), mask);
	}

	public static String CanonicalSymbol(String Symbol) {
		return Symbol.toLowerCase().replaceAll(
				"_", "");
	}

	public int GetCanonicalSymbol(String Symbol, int DefaultValue) {
		return this.GetSymbol(
				KSymbolTable.CanonicalSymbol(Symbol), DefaultValue);
	}

	int GetSymbol(String symbol, boolean isnew) {
		Integer id = this.SymbolMap.get(symbol);
		if(id == null && isnew) {

		}
		return id;
	}

	KPackage NewPackage(Konoha kctx, String name) {
		int packageId = this.PackageList.size();
		KPackage p = new KPackage(kctx, packageId, name);
		this.PackageList.add(p);
		return p;
	}

	// KClass NewClass(Konoha kctx, KPackage p, String name) {
	// int classId = this.ClassList.size();
	// KClass c = new KClass(kctx, p, classId, name);
	// this.ClassList.add(c);
	// this.LongClassNameMap.SetId(p.PackageName + "." + name, classId);
	// return c;
	// }
}

public class Konoha implements KonohaConst {

	KonohaNameSpace RootNameSpace;
	KonohaNameSpace DefaultNameSpace;
	KSymbolTable SymbolTable;

	public final KonohaType VoidType;
	public final KonohaType ObjectType;
	public final KonohaType BooleanType;
	public final KonohaType IntType;
	public final KonohaType StringType;
	public final KonohaType VarType;

	public Konoha(KonohaGrammar Grammar, String BuilderClassName) {
		this.SymbolTable = new KSymbolTable();
		this.SymbolTable.Init(this);
		this.RootNameSpace = new KonohaNameSpace(this, null);

		this.VoidType = this.RootNameSpace.LookupTypeInfo(Void.class);
		this.ObjectType = this.RootNameSpace.LookupTypeInfo(Object.class);
		this.BooleanType = this.RootNameSpace.LookupTypeInfo(Boolean.class);
		this.IntType = this.RootNameSpace.LookupTypeInfo(Integer.class);
		this.StringType = this.RootNameSpace.LookupTypeInfo(String.class);
		this.VarType = this.RootNameSpace.LookupTypeInfo(Object.class);

		Grammar.LoadDefaultSyntax(this.RootNameSpace);
		this.DefaultNameSpace = new KonohaNameSpace(this, this.RootNameSpace);
		if(BuilderClassName != null) {
			this.DefaultNameSpace.LoadBuilder(BuilderClassName);
		}
	}

	final KonohaType LookupTypeInfo(Class<?> ClassInfo) {
		KonohaType TypeInfo = this.SymbolTable.ClassNameMap.get(ClassInfo.getName());
		if(TypeInfo == null) {
			TypeInfo = new KonohaType(this, ClassInfo);
			this.SymbolTable.ClassNameMap.put(
					ClassInfo.getName(), TypeInfo);
		}
		return TypeInfo;
	}

	// public int GetSymbol(String key, boolean isnew) {
	// return this.SymbolTable.GetSymbol(key, isnew);
	// }

	public void Define(String symbol, Object Value) {
		this.RootNameSpace.DefineSymbol(
				symbol, Value);
	}

	public void Eval(String text, long uline) {
		this.DefaultNameSpace.Eval(
				text, uline);
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
		KonohaContext.Eval(
				"int fibo(int n) {\n" +
						"\tif(n < 3) return 1;\n" +
						"\treturn fibo(n-1)+fibo(n-2);\n" +
						"}", 1000);
	}
}
