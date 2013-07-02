package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.Parser.KonohaToken;

public class ApplyNode extends TypedNode implements CallableNode {
	public KonohaMethod	Method;
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
		return Visitor.VisitApply(this);
	}

}
