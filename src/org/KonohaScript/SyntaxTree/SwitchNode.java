package org.KonohaScript.SyntaxTree;

import java.util.ArrayList;

import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.SwitchNodeAcceptor;

class DefaultSwitchNodeAcceptor implements SwitchNodeAcceptor {
	@Override
	public boolean Eval(SwitchNode Node, NodeVisitor Visitor) {
		Visitor.EnterSwitch(Node);
		Visitor.Visit(Node.CondExpr);
		for (TypedNode Block : Node.Blocks) {
			Visitor.Visit(Block);
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
	public ArrayList<String>	Labels;
	public ArrayList<TypedNode>	Blocks;

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.SwitchNodeAcceptor.Eval(this, Visitor);
	}

}
