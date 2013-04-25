package org.KonohaScript.AST;

import org.KonohaScript.CodeGen.CodeGenerator;

public class DoneNode extends TypedNode {
	@Override
	public boolean Evaluate(CodeGenerator Gen) {
	    Gen.EnterDone(this);
	    Gen.ExitDone(this);
	    return true;
	}
}