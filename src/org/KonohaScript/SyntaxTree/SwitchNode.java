package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.KonohaArray;

public class SwitchNode extends TypedNode {
	public SwitchNode(KonohaType TypeInfo, KonohaType KeyToken) {
		super(TypeInfo, null/* FIXME */);
	}

	/*
	 * switch CondExpr { Label[0]: Blocks[0]; Label[1]: Blocks[2]; ... }
	 */
	public TypedNode	CondExpr;
	public KonohaArray	Labels;
	public KonohaArray	Blocks;

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.VisitSwitch(this);
	}

}
