package org.KonohaScript;

public abstract class KonohaMethodInvoker {
	KonohaParam		Param;
	public Object	CompiledCode;

	public KonohaMethodInvoker(KonohaParam Param, Object CompiledCode) {
		this.Param = Param;
		this.CompiledCode = CompiledCode;

	}

	public Object Invoke(Object[] Args) {
		return null;
	}
}