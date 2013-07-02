package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;

public class LabelNode extends TypedNode {
	public String	Label;

	/* Label: */
	public LabelNode(KonohaType TypeInfo, String Label) {
		super(TypeInfo, null/* fixme */);
		this.Label = Label;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.VisitLabel(this);
	}

}