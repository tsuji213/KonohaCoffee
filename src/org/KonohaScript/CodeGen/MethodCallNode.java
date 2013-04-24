package org.KonohaScript.CodeGen;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MethodCallNode extends TypedNode implements CallableNode {
	ArrayList<TypedNode> Params; /* [this, arg1, arg2, ...] */
	Method  Mtd;
	/* call self.Method(arg1, arg2, ...) */
	MethodCallNode(Method Mtd) {
		this.Mtd = Mtd;
		this.Params = new ArrayList<TypedNode>();
	}
	
	@Override
	public void Append(TypedNode Expr) {
		this.Params.add(Expr);
	}
}
