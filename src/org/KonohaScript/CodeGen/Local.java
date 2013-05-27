package org.KonohaScript.CodeGen;

import org.KonohaScript.KonohaType;

public class Local {
	String Name;
	KonohaType TypeInfo;
	int Index;

	public Local(int Index, KonohaType TypeInfo, String Name) {
		this.Index = Index;
		this.TypeInfo = TypeInfo;
		this.Name = Name;
	}
}

class Param extends Local {
	public Param(int Index, KonohaType TypeInfo, String Name) {
		super(Index, TypeInfo, Name);
	}
}
