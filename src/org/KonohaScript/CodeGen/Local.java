package org.KonohaScript.CodeGen;

import org.KonohaScript.KClass;

public class Local {
	String Name;
	KClass TypeInfo;
	int Index;

	public Local(int Index, KClass TypeInfo, String Name) {
		this.Index = Index;
		this.TypeInfo = TypeInfo;
		this.Name = Name;
	}
}

class Param extends Local {
	public Param(int Index, KClass TypeInfo, String Name) {
		super(Index, TypeInfo, Name);
	}
}
