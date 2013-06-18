package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KLib.*;

import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.FunctionNodeAcceptor;

class DefaultFunctionNodeAcceptor implements FunctionNodeAcceptor {
	@Override
	public boolean Invoke(FunctionNode Node, NodeVisitor Visitor) {
		Visitor.EnterFunction(Node);
		return Visitor.ExitFunction(Node);
	}
}

public class FunctionNode extends TypedNode implements CallableNode {
	/* [Method, DefaultObject, [Env1, Env2, ...., EnvN] */
	/*
	 * void f() { int Env1, Env2; return function (int Param1, int Param2) int {
	 * return Env1 + Env2 + Param1 + Param2; } (10, 20); } }
	 */
	public KonohaArray	EnvList;
	public KonohaMethod			Method;

	public FunctionNode(KonohaType TypeInfo, KonohaMethod Method) {
		super(TypeInfo, null/* FIXME */);
		this.Method = Method;
		this.EnvList = new KonohaArray();
	}

	@Override
	public void Append(TypedNode Expr) {
		this.EnvList.add(Expr);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.FunctionNodeAcceptor.Invoke(this, Visitor);
	}

}