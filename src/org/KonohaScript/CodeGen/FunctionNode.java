package org.KonohaScript.CodeGen;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class FunctionNode extends TypedNode implements CallableNode {
	/* [Method, DefaultObject, [Env1, Env2, ...., EnvN] */
	/*
	 * void f() {
	 *   int Env1, Env2;
	 *   return function (int Param1, int Param2) int {
	 *     return Env1 + Env2 + Param1 + Param2; } (10, 20);
	 *   }
	 * }
	 */
	ArrayList<TypedNode> EnvList;
	Method  Mtd;
	FunctionNode(Method Mtd) {
		this.Mtd = Mtd;
		this.EnvList = new ArrayList<TypedNode>();
	}
	
	@Override
	public void Append(TypedNode Expr) {
		this.EnvList.add(Expr);
	}
}
