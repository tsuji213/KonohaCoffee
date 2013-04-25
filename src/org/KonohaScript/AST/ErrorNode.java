package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class ErrorNode extends TypedNode {
	String ErrorMessage;

	ErrorNode(KClass ClassInfo, String ErrorMessage) {
		super(ClassInfo);
		this.ErrorMessage = ErrorMessage;
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterError(this);
		Gen.ExitError(this);
		return true;
	}
}
