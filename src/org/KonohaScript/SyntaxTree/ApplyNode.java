package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KLib.*;

import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaType;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.SyntaxTree.NodeVisitor.ApplyNodeAcceptor;

class DefaultApplyNodeAcceptor implements ApplyNodeAcceptor {
	@Override
	public boolean Invoke(ApplyNode Node, NodeVisitor Visitor) {
		Visitor.EnterApply(Node);
		for(int i = 0; i < Node.Params.size(); i++) {
			Visitor.Visit((TypedNode)Node.Params.get(i));
		}
		return Visitor.ExitApply(Node);
	}
}

public class ApplyNode extends TypedNode implements CallableNode {
	public KonohaMethod			Method;
	public KonohaArray	Params; /* [this, arg1, arg2, ...] */

	/* call self.Method(arg1, arg2, ...) */
	public ApplyNode(KonohaType TypeInfo, KonohaToken KeyToken, KonohaMethod Method) {
		super(TypeInfo, KeyToken);
		this.Method = Method;
		this.Params = new KonohaArray();
	}

	public ApplyNode(KonohaType TypeInfo, KonohaToken KeyToken, KonohaMethod Method, TypedNode arg1) {
		super(TypeInfo, KeyToken);
		this.Method = Method;
		this.Params = new KonohaArray();
		this.Params.add(arg1);
	}

	public ApplyNode(KonohaType TypeInfo, KonohaToken KeyToken, KonohaMethod Method, TypedNode arg1, TypedNode arg2) {
		super(TypeInfo, KeyToken);
		this.Method = Method;
		this.Params = new KonohaArray();
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
