package org.KonohaScript.AST;

import org.KonohaScript.CodeGen.CodeGenerator;

public class ConstNode extends TypedNode {
	long   ConstValue;
	Object ConstObject;
	ConstNode(Object ConstObject) {
		this.ConstObject = ConstObject;
	}
	ConstNode(long ConstValue) {
		this.ConstValue = ConstValue;
	}
	ConstNode(float ConstValue) {
		this.ConstValue = Double.doubleToLongBits(ConstValue);
	}
	ConstNode(boolean ConstValue) {
		this.ConstValue = ConstValue ? 1 : 0;
	}
	@Override
	public boolean Evaluate(CodeGenerator Gen) {
	    Gen.EnterConst(this);
	    Gen.ExitConst(this);
	    return true;
	}
}