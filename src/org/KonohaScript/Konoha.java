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

class KMacro {
	int Prep(KNameSpace ns, ArrayList<KToken> source, int beginIdx, int endIdx,
			ArrayList<KToken> buffer) {
		return beginIdx;
	}
}

class KNode {
	KNode Parent;
	KNameSpace RootNodeNameSpace;
	KSyntax Syntax;
	KToken KeyOperatorToken;
	ArrayList<Object> NodeList;

	HashMap<String, Object> Annotation;
}

// typedef struct {
// ktypeattr_t typeAttr; ksymbol_t name;
// } KGammaStackDecl;
//
// #define kNameSpace_TopLevel (khalfflag_t)(1)
// #define kNameSpace_IsTopLevel(GMA) KFlag_Is(khalfflag_t, GMA->genv->flag,
// kNameSpace_TopLevel)
//
// struct KGammaStack {
// KGammaStackDecl *varItems;
// size_t varsize;
// size_t capacity;
// size_t allocsize; // set size if not allocated (by default on stack)
// } ;
//
// struct KGammaLocalData {
// khalfflag_t flag; khalfflag_t cflag;
// KClass *thisClass;
// kMethod *currentWorkingMethod;
// struct KGammaStack localScope;
// } ;

// class KTypedNode {
// int nodeType;
// int typeId;
// }

// #define kNode_uline(O) (O)->KeyOperatorToken->uline
//
// #define KNewNode(ns) new_(Node, ns, OnGcStack)
//
// #define kNode_IsRootNode(O) IS_NameSpace(O->RootNodeNameSpace)
// #define kNode_ns(O) kNode_GetNameSpace(kctx, O)
// static inline kNameSpace *kNode_GetNameSpace(KonohaContext *kctx, kNode
// *node)
// {
// kNameSpace *ns = node->StmtNameSpace;
// while(!IS_NameSpace(ns)) {
// if(kNode_IsRootNode(node)) {
// ns = node->RootNodeNameSpace;
// break;
// }
// node = node->Parent;
// ns = node->StmtNameSpace;
// }
// return ns;
// }
//
// #define kNode_GetParent(kctx, node) ((IS_Node(node->Parent)) ? node->Parent :
// K_NULLNODE)
// #define kNode_GetParentNULL(stmt) ((IS_Node(stmt->Parent)) ? stmt->Parent :
// NULL)
// #define kNode_SetParent(kctx, node, parent) KFieldSet(node, node->Parent,
// parent)
//
//
// static inline kNode *kNode_Type(kNode *node, knode_t nodeType, ktypeattr_t
// typeAttr)
// {
// if(kNode_node(node) != KNode_Error) {
// kNode_setnode(node, nodeType);
// node->typeAttr = typeAttr;
// }
// return node;
// }
//
// static inline size_t kNode_GetNodeListSize(KonohaContext *kctx, kNode *node)
// {
// return (IS_Array(node->NodeList)) ? kArray_size(node->NodeList) : 0;
// }
//
// #define kNode_IsTerm(N) IS_Token((N)->TermToken)
//
// #define kNodeFlag_ObjectConst kObjectFlag_Local1
//
// #define kNodeFlag_OpenBlock kObjectFlag_Local2 /* KNode_Block */
// #define kNodeFlag_CatchContinue kObjectFlag_Local3 /* KNode_Block */
// #define kNodeFlag_CatchBreak kObjectFlag_Local4 /* KNode_Block */
//
// #define kNode_Is(P, O) (KFlag_Is(uintptr_t,(O)->h.magicflag, kNodeFlag_##P))
// #define kNode_Set(P, O, B) KFlag_Set(uintptr_t,(O)->h.magicflag,
// kNodeFlag_##P, B)
//
// #define kNode_At(E, N) ((E)->NodeList->NodeItems[(N)])
// #define kNode_IsError(STMT) (kNode_node(STMT) == KNode_Error)
//
// #define kNode_GetObjectNULL(CTX, O, K) (KLIB kObject_getObject(CTX,
// UPCAST(O), K, NULL))
// #define kNode_GetObject(CTX, O, K, DEF) (KLIB kObject_getObject(CTX,
// UPCAST(O), K, DEF))
// #define kNode_SetObject(CTX, O, K, V) KLIB kObjectProto_SetObject(CTX,
// UPCAST(O), K, kObject_typeId(V), UPCAST(V))
// #define kNode_SetUnboxValue(CTX, O, K, T, V) KLIB
// kObjectProto_SetUnboxValue(CTX, UPCAST(O), K, T, V)
// #define kNode_RemoveKey(CTX, O, K) KLIB kObjectProto_RemoveKey(CTX,
// UPCAST(O), K)
// #define kNode_DoEach(CTX, O, THUNK, F) kObjectProto_DoEach(CTX, UPCAST(O),
// THUNK, F)
//
// #define kNode_Message(kctx, STMT, PE, FMT, ...) KLIB MessageNode(kctx, STMT,
// NULL, NULL, PE, FMT, ## __VA_ARGS__)
// #define kNodeToken_Message(kctx, STMT, TK, PE, FMT, ...) KLIB
// MessageNode(kctx, STMT, TK, NULL, PE, FMT, ## __VA_ARGS__)

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

//
// #define CLASSAPI \
// void (*init)(KonohaContext*, kObject*, void *conf);\
// void (*reftrace)(KonohaContext*, kObject*, struct KObjectVisitor *visitor);\
// void (*free)(KonohaContext*, kObject *);\
// kObject* (*fnull)(KonohaContext*, KClass *);\
// uintptr_t (*unbox)(KonohaContext*, kObject *);\
// void (*format)(KonohaContext*, KonohaValue *, int, KBuffer *);\
// int (*compareTo)(KonohaContext *, kObject *, kObject *);\
// int (*compareUnboxValue)(uintptr_t, uintptr_t);\
// void (*initdef)(KonohaContext*, KClassVar*, KTraceInfo *);\
// kbool_t (*isSubType)(KonohaContext*, KClass*, KClass *);\
// KClass* (*realtype)(KonohaContext*, KClass*, KClass *)

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

	KNameSpace DefaultNameSpace;
	KSymbolTable SymbolTable;

	public Konoha(MiniKonoha defaultSyntax) {
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

	public KClass AddClass(String name, KPackage p) {// FIXME adhoc impl.
		return this.SymbolTable.NewClass(this, p, name);
	}

	public void Load(String fileName) {
		// System.out.println("Eval: " + text);
		// DefaultNameSpace.Tokenize(text, uline);
	}

	public static void main(String[] argc) {
		MiniKonoha defaultSyntax = new MiniKonoha();
		Konoha konoha = new Konoha(defaultSyntax);
		konoha.Eval("hello,world", 1);
	}
}
