package org.KonohaScript.CodeGen;

class CompiledMethod {
	Object Invoke(Object[] Args) { return null; }
}

public interface CodeGenerator {
	CompiledMethod Compile(TypedNode Block);
}
