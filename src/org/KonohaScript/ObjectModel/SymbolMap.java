package org.KonohaScript.ObjectModel;

public class SymbolMap {
	public Object Get(int KeySymbol) {
		// TODO (tsuji213)
		return null;
	}

	public void Set(int KeySymbol, Object Value) {
		// TODO (tsuji213)
	}
}

class SymbolMapTest {
	public static void main(String[] args) {
		// Create Map
		// set Value with Some Key
		// get Value from Map
		// check Value
	}
}
////-------------------------------------------------------------------------
////KHashMap
//
//typedef struct KHashMapEntry {
//	struct KHashMapEntry *next;
//  int Key;
//  Object Value;
//} KHashMapEntry;
//
//typedef struct KHashMap KHashMap;
//struct KHashMap {
//	KHashMapEntry  *arena;
//	KHashMapEntry  *unused;
//	KHashMapEntry **hentry;
//	size_t          arenasize;
//	size_t          size;
//	size_t          hmax;
//};
//#define HMAP_INIT 83
//
//static void KHashMap_MakeFreeList(KHashMap *kmap, size_t s, size_t e)
//{
//	bzero(kmap->arena + s, (e - s) * sizeof(KHashMapEntry));
//	kmap->unused = kmap->arena + s;
//	size_t i;
//	for(i = s; i < e - 1; i++) {
//		kmap->arena[i].hcode = ((uintptr_t)-1);
//		kmap->arena[i].unboxValue = 0;
//		kmap->arena[i].next = kmap->arena + i + 1;
//	}
//	kmap->arena[e-1].hcode = ((uintptr_t)-1);
//	kmap->arena[e-1].unboxValue = 0;
//	DBG_ASSERT(kmap->arena[e-1].next == NULL);
//}
//
//static void KHashMap_Rehash(KonohaContext *kctx, KHashMap *kmap)
//{
//	size_t i, newhmax = kmap->hmax * 2 + 1;
//	KHashMapEntry **newhentry = (KHashMapEntry**)KCalloc_UNTRACE(newhmax, sizeof(KHashMapEntry *));
//	for(i = 0; i < kmap->arenasize / 2; i++) {
//		KHashMapEntry *e = kmap->arena + i;
//		kuint_t ni = e->hcode % newhmax;
//		e->next = newhentry[ni];
//		newhentry[ni] = e;
//	}
//	KFree(kmap->hentry, kmap->hmax * sizeof(KHashMapEntry *));
//	kmap->hentry = newhentry;
//	kmap->hmax = newhmax;
//}
//
//static void KHashMap_ShiftPointer(KHashMap *kmap, intptr_t shift)
//{
//	size_t i, size = kmap->arenasize / 2;
//	for(i = 0; i < size; i++) {
//		KHashMapEntry *e = kmap->arena + i;
//		if(e->next != NULL) {
//			e->next = (KHashMapEntry *)(((char *)e->next) + shift);
//			DBG_ASSERT(kmap->arena <= e->next && e->next < kmap->arena + size);
//		}
//	}
//}
//
//static KHashMapEntry *KHashMap_newEntry(KonohaContext *kctx, KHashMap *kmap, kuint_t hcode)
//{
//	KHashMapEntry *e;
//	if(kmap->unused == NULL) {
//		size_t oarenasize = kmap->arenasize;
//		char *oarena = (char *)kmap->arena;
//		kmap->arenasize *= 2;
//		kmap->arena = (KHashMapEntry *)KMalloc_UNTRACE(kmap->arenasize * sizeof(KHashMapEntry));
//		memcpy(kmap->arena, oarena, oarenasize * sizeof(KHashMapEntry));
//		KHashMap_ShiftPointer(kmap, (char *)kmap->arena - oarena);
//		KHashMap_MakeFreeList(kmap, oarenasize, kmap->arenasize);
//		KFree(oarena, oarenasize * sizeof(KHashMapEntry));
//		KHashMap_Rehash(kctx, kmap);
//	}
//	e = kmap->unused;
//	kmap->unused = e->next;
//	e->hcode = hcode;
//	e->next = NULL;
//	kmap->size++;
//	{
//		KHashMapEntry **hlist = kmap->hentry;
//		size_t idx = e->hcode % kmap->hmax;
//		e->next = hlist[idx];
//		hlist[idx] = e;
//	}
//	return e;
//}
//
//KLIBDECL KHashMap *KHashMap_Init(KonohaContext *kctx, size_t init)
//{
//	KHashMap *kmap = (KHashMap *)KCalloc_UNTRACE(sizeof(KHashMap), 1);
//	if(init < HMAP_INIT) init = HMAP_INIT;
//	kmap->arenasize = (init * 3) / 4;
//	kmap->arena = (KHashMapEntry *)KMalloc_UNTRACE(kmap->arenasize * sizeof(KHashMapEntry));
//	KHashMap_MakeFreeList(kmap, 0, kmap->arenasize);
//	kmap->hentry = (KHashMapEntry**)KCalloc_UNTRACE(init, sizeof(KHashMapEntry *));
//	kmap->hmax = init;
//	kmap->size = 0;
//	return (KHashMap *)kmap;
//}
//
//KLIBDECL void KHashMap_DoEach(KonohaContext *kctx, KHashMap *kmap, void *thunk, void (*f)(KonohaContext *kctx, KHashMapEntry *, void *thunk))
//{
//	size_t i;
//	for(i = 0; i < kmap->hmax; i++) {
//		KHashMapEntry *e = kmap->hentry[i];
//		while(e != NULL) {
//			f(kctx, e, thunk);
//			e = e->next;
//		}
//	}
//}
//
//KLIBDECL void KHashMap_Free(KonohaContext *kctx, KHashMap *kmap, void (*f)(KonohaContext *kctx, void *))
//{
//	if(f != NULL) {
//		size_t i;
//		for(i = 0; i < kmap->hmax; i++) {
//			KHashMapEntry *e = kmap->hentry[i];
//			while(e != NULL) {
//				f(kctx, e->ptrValue);
//				e = e->next;
//			}
//		}
//	}
//	KFree(kmap->arena, sizeof(KHashMapEntry)*(kmap->arenasize));
//	KFree(kmap->hentry, sizeof(KHashMapEntry *)*(kmap->hmax));
//	KFree(kmap, sizeof(KHashMap));
//}
//
//static KHashMapEntry *KHashMap_getentry(KonohaContext *kctx, KHashMap* kmap, kuint_t hcode)
//{
//	KHashMapEntry **hlist = kmap->hentry;
//	size_t idx = hcode % kmap->hmax;
//	KHashMapEntry *e = hlist[idx];
//	while(e != NULL) {
//		if(e->hcode == hcode) return e;
//		e = e->next;
//	}
//	return NULL;
//}
//
//static void KHashMap_Unuse(KHashMap *kmap, KHashMapEntry *e)
//{
//	e->next = kmap->unused;
//	kmap->unused = e;
//	e->hcode = ((uintptr_t)-1);
//	e->unboxValue  = 0;
//	kmap->size--;
//}
//
//static void KHashMap_Remove(KHashMap* kmap, KHashMapEntry *oe)
//{
//	KHashMapEntry **hlist = kmap->hentry;
//	size_t idx = oe->hcode % kmap->hmax;
//	KHashMapEntry *e = hlist[idx];
//	while(e != NULL) {
//		if(e->next == oe) {
//			e->next = oe->next;
//			KHashMap_Unuse(kmap, oe);
//			return;
//		}
//		e = e->next;
//	}
//	hlist[idx] = oe->next;
//	KHashMap_Unuse(kmap, oe);
//}
//
////key management
//
//static void KHashMap_AddStringUnboxValue(KonohaContext *kctx, KHashMap *kmp, uintptr_t hcode, kString *StringKey, uintptr_t unboxValue)
//{
//	KHashMapEntry *e = KLIB KHashMap_newEntry(kctx, kmp, hcode);
//	KUnsafeFieldInit(e->StringKey, StringKey);
//	e->unboxValue = unboxValue;
//}
//
//static ksymbol_t KHashMap_getcode(KonohaContext *kctx, KHashMap *kmp, kArray *list, const char *name, size_t len, uintptr_t hcode, int spol, ksymbol_t def)
//{
//	KHashMapEntry *e = KLIB KHashMap_get(kctx, kmp, hcode);
//	while(e != NULL) {
//		if(e->hcode == hcode && len == kString_size(e->StringKey) && strncmp(kString_text(e->StringKey), name, len) == 0) {
//			return (ksymbol_t)e->unboxValue;
//		}
//		e = e->next;
//	}
//	if(def == KSymbol_NewId) {
//		uintptr_t sym = kArray_size(list);
//		kString *stringKey = KLIB new_kString(kctx, list, name, len, spol);
//		KHashMap_AddStringUnboxValue(kctx, kmp, hcode, stringKey, sym);
//		return (ksymbol_t)sym;
//	}
//	return def;
//}
