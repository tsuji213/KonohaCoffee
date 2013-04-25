package org.KonohaScript.AST;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class MethodCallNode extends TypedNode implements CallableNode {
	public ArrayList<TypedNode> Params; /* [this, arg1, arg2, ...] */
	Method Mtd;

	/* call self.Method(arg1, arg2, ...) */
	MethodCallNode(KClass ClassInfo, Method Mtd) {
		super(ClassInfo);
		this.Mtd = Mtd;
		this.Params = new ArrayList<TypedNode>();
	}

	@Override
	public void Append(TypedNode Expr) {
		this.Params.add(Expr);
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterMethodCall(this);
		for (TypedNode Node : this.Params) {
			Gen.Visit(Node);
		}
		Gen.ExitMethodCall(this);
		return true;
	}
}
