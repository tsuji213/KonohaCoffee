package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.ConstNodeAcceptor;

class DefaultConstNodeAcceptor implements ConstNodeAcceptor {
	@Override
	public boolean Eval(ConstNode Node, NodeVisitor Visitor) {
		Visitor.EnterConst(Node);
		return Visitor.ExitConst(Node);
	}
}

public class ConstNode extends TypedNode {
	public Object	ConstValue;

	public ConstNode(KonohaType TypeInfo, KonohaToken SourceToken,
			Object ConstValue) {
		super(TypeInfo, SourceToken);
		this.ConstValue = ConstValue;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.ConstNodeAcceptor.Eval(this, Visitor);
	}

}
