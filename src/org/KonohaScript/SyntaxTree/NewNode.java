package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.Parser.KonohaToken;

public class NewNode extends TypedNode implements CallableNode {
	public KonohaArray	Params; /* [this, arg1, arg2, ...] */

	public NewNode(KonohaType TypeInfo, KonohaToken KeyToken) {
		super(TypeInfo, KeyToken);
		this.Params = new KonohaArray();
	}

	@Override
	public void Append(TypedNode Expr) {
		this.Params.add(Expr);
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.VisitNew(this);
	}

}