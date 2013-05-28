package org.KonohaScript.SyntaxTree;

import java.util.ArrayList;

import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.ApplyNodeAcceptor;

class DefaultApplyNodeAcceptor implements ApplyNodeAcceptor {
	@Override
	public boolean Invoke(ApplyNode Node, NodeVisitor Visitor) {
		Visitor.EnterApply(Node);
		for (TypedNode Element : Node.Params) {
			Visitor.Visit(Element);
		}
		return Visitor.ExitApply(Node);
	}
}

public class ApplyNode extends TypedNode implements CallableNode {
	public KonohaMethod			Method;
	public ArrayList<TypedNode>	Params; /* [this, arg1, arg2, ...] */

	/* call self.Method(arg1, arg2, ...) */
	public ApplyNode(KonohaType TypeInfo, KonohaToken KeyToken,
			KonohaMethod Method) {
		super(TypeInfo, KeyToken);
		this.Method = Method;
		this.Params = new ArrayList<TypedNode>();
	}

	public ApplyNode(KonohaType TypeInfo, KonohaToken KeyToken,
			KonohaMethod Method, TypedNode arg1) {
		super(TypeInfo, KeyToken);
		this.Method = Method;
		this.Params = new ArrayList<TypedNode>();
		this.Params.add(arg1);
	}

	public ApplyNode(KonohaType TypeInfo, KonohaToken KeyToken,
			KonohaMethod Method, TypedNode arg1, TypedNode arg2) {
		super(TypeInfo, KeyToken);
		this.Method = Method;
		this.Params = new ArrayList<TypedNode>();
		this.Params.add(arg1);
		this.Params.add(arg2);
	}

	@Override
	public void Append(TypedNode Expr) {
		this.Params.add(Expr);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.ApplyNodeAcceptor.Invoke(this, Visitor);
	}

}
