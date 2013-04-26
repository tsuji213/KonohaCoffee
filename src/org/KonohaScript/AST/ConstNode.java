package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class ConstNode extends TypedNode {
	public long ConstValue;
	public Object ConstObject;

	public ConstNode(KClass TypeInfo, Object ConstObject) {
		super(TypeInfo);
		init(ConstObject);
	}

	public ConstNode(KClass TypeInfo, long ConstValue) {
		super(TypeInfo);
		this.ConstValue = ConstValue;
	}

	public ConstNode(KClass TypeInfo, int ConstValue) {
		super(TypeInfo);
		this.ConstValue = ConstValue;
	}

	public ConstNode(KClass TypeInfo, float ConstValue) {
		super(TypeInfo);
		this.ConstValue = Double.doubleToLongBits(ConstValue);
	}

	public ConstNode(KClass TypeInfo, boolean ConstValue) {
		super(TypeInfo);
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
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterConst(this);
		return Visitor.ExitConst(this);
	}
}