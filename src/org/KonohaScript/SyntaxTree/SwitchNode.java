package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KLib.*;

import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.SwitchNodeAcceptor;

class DefaultSwitchNodeAcceptor implements SwitchNodeAcceptor {
	@Override
	public boolean Invoke(SwitchNode Node, NodeVisitor Visitor) {
		Visitor.EnterSwitch(Node);
		Visitor.Visit(Node.CondExpr);
		for(int i = 0; i < Node.Blocks.size(); i++) {
			TypedNode Block = (TypedNode) Node.Blocks.get(i);
			Visitor.VisitList(Block);
		}
		return Visitor.ExitSwitch(Node);
	}
}

public class SwitchNode extends TypedNode {
	public SwitchNode(KonohaType TypeInfo) {
		super(TypeInfo, null/* fixme */);
	}

	/*
	 * switch CondExpr { Label[0]: Blocks[0]; Label[1]: Blocks[2]; ... }
	 */
	public TypedNode			CondExpr;
	public KonohaArray	Labels;
	public KonohaArray	Blocks;

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.SwitchNodeAcceptor.Invoke(this, Visitor);
	}

}
