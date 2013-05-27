package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.JumpNodeAcceptor;

class DefaultJumpNodeAcceptor implements JumpNodeAcceptor {
	@Override
	public boolean Eval(JumpNode Node, NodeVisitor Visitor) {
		Visitor.EnterJump(Node);
		return Visitor.ExitJump(Node);
	}
}

public class JumpNode extends TypedNode {
	public String		Label;
	public TypedNode	TargetNode;

	/* goto Label */
	public JumpNode(KonohaType TypeInfo, String Label) {
		super(TypeInfo, null/* fixme */);
		this.Label = Label;
		this.TargetNode = null; /* TargetNode is resolved by NodeVisitor */
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.JumpNodeAcceptor.Eval(this, Visitor);
	}
}
