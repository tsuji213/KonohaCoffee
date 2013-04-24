package org.KonohaScript.CodeGen;

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
}