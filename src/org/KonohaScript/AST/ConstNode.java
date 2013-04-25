package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class ConstNode extends TypedNode {
	public long ConstValue;
	public Object ConstObject;

	public ConstNode(KClass ClassInfo, Object ConstObject) {
		super(ClassInfo);
		init(ConstObject);
	}

	public ConstNode(KClass ClassInfo, long ConstValue) {
		super(ClassInfo);
		this.ConstValue = ConstValue;
	}

	public ConstNode(KClass ClassInfo, int ConstValue) {
		super(ClassInfo);
		this.ConstValue = ConstValue;
	}

	public ConstNode(KClass ClassInfo, float ConstValue) {
		super(ClassInfo);
		this.ConstValue = Double.doubleToLongBits(ConstValue);
	}

	public ConstNode(KClass ClassInfo, boolean ConstValue) {
		super(ClassInfo);
		this.ConstValue = ConstValue ? 1 : 0;
	}

	void init(Object Value) {
		if (ConstObject instanceof Integer) {
			this.ConstValue = ((Integer) Value).intValue();

		} else if (ConstObject instanceof Double) {
			double val = ((Double) Value).doubleValue();
			this.ConstValue = Double.doubleToLongBits(val);
		} else if (ConstObject instanceof Boolean) {
			boolean val = ((Boolean) Value).booleanValue();
			this.ConstValue = val ? 1 : 0;
		} else {
			this.ConstObject = Value;

		}
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterConst(this);
		Gen.ExitConst(this);
		return true;
	}
}