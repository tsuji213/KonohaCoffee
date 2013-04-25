package org.KonohaScript;

public class KonohaContext {
	// KonohaStack *esp;
	// PlatformApi *platApi;
	// KonohaLib *klib;
	// KRuntime *share;
	// KRuntimeContextVar *stack;
	// KRuntimeModel **runtimeModels;
	// KModelContext **localContexts;

	KNameSpace DefaultNameSpace;
	KSymbolTable SymbolTable;

	public KonohaContext(KonohaSyntax defaultSyntax) {
		this.SymbolTable = new KSymbolTable();
		this.SymbolTable.Init(this);
		this.DefaultNameSpace = new KNameSpace(this, null);
		defaultSyntax.LoadDefaultSyntax(DefaultNameSpace);
	}

	public int GetSymbol(String key, boolean isnew) {
		return this.SymbolTable.GetSymbol(key, isnew);
	}

	public KClass AddClass(String name, KPackage p) {// FIXME adhoc impl.
		return this.SymbolTable.NewClass(this, p, name);
	}

	void Eval(String text) {
		System.out.println("Eval: " + text);
		DefaultNameSpace.Tokenize(text, 1);
	}

}