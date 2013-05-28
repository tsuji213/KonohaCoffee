package org.KonohaScript.SyntaxTree;

import java.util.ArrayList;

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
	public ArrayList<TypedNode>	EnvList;
	public KonohaMethod			Mtd;

	public FunctionNode(KonohaType TypeInfo, KonohaMethod Mtd) {
		super(TypeInfo, null/* FIXME */);
		this.Mtd = Mtd;
		this.EnvList = new ArrayList<TypedNode>();
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
