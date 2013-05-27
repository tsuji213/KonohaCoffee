package org.KonohaScript.SyntaxTree;

import java.util.ArrayList;

import org.KonohaScript.KonohaType;
import org.KonohaScript.CodeGen.ASTVisitor;

public class SwitchNode extends TypedNode {
	public SwitchNode(KonohaType TypeInfo) {
		super(TypeInfo, null/*fixme*/);
	}

	/*
	 * switch CondExpr { Label[0]: Blocks[0]; Label[1]: Blocks[2]; ... }
	 */
	public TypedNode			CondExpr;
	public ArrayList<String>	Labels;
	public ArrayList<TypedNode>	Blocks;

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterSwitch(this);
		Visitor.Visit(this.CondExpr);
		for(TypedNode Node : this.Blocks) {
			Visitor.Visit(Node);
		}
		return Visitor.ExitSwitch(this);
	}
}
