package org.KonohaScript.CodeGen;
import org.KonohaScript.KClass;

class NotSupportedNodeError extends RuntimeException {
	NotSupportedNodeError() {
		super();
	}
}

public abstract class TypedNode {
	KClass classInfo;
	boolean Compile(CodeGenerator Gen) {
		throw new NotSupportedNodeError();
	}
}