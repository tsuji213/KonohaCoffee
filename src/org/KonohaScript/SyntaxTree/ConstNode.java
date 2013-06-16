package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.SyntaxTree.NodeVisitor.ConstNodeAcceptor;

class DefaultConstNodeAcceptor implements ConstNodeAcceptor {
	@Override
	public boolean Invoke(ConstNode Node, NodeVisitor Visitor) {
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
		return Visitor.ConstNodeAcceptor.Invoke(this, Visitor);
	}

}
