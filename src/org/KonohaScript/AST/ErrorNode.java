package org.KonohaScript.AST;

import org.KonohaScript.CodeGen.CodeGenerator;

public class ErrorNode extends TypedNode {
	String ErrorMessage;
	ErrorNode(String ErrorMessage) {
		this.ErrorMessage = ErrorMessage;
	}
	@Override
	public boolean Evaluate(CodeGenerator Gen) {
	    Gen.EnterError(this);
	    Gen.ExitError(this);
	    return true;
	}
}
