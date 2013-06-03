package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.LabelNodeAcceptor;

class DefaultLabelNodeAcceptor implements LabelNodeAcceptor {
	@Override
	public boolean Invoke(LabelNode Node, NodeVisitor Visitor) {
		Visitor.EnterLabel(Node);
		return Visitor.ExitLabel(Node);
	}
}

public class LabelNode extends TypedNode {
	public String	Label;

	/* Label: */
	public LabelNode(KonohaType TypeInfo, String Label) {
		super(TypeInfo, null/* fixme */);
		this.Label = Label;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.LabelNodeAcceptor.Invoke(this, Visitor);
	}

}