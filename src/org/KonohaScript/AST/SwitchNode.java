package org.KonohaScript.AST;

import java.util.ArrayList;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class SwitchNode extends TypedNode {
	public SwitchNode(KClass ClassInfo) {
		super(ClassInfo);
	}

	/*
	 * switch CondExpr { Label[0]: Blocks[0]; Label[1]: Blocks[2]; ... }
	 */
	TypedNode CondExpr;
	public ArrayList<String> Labels;
	public ArrayList<TypedNode> Blocks;

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterSwitch(this);
		Gen.Visit(this.CondExpr);
		for (TypedNode Node : this.Blocks) {
			Gen.Visit(Node);
		}
		Gen.ExitSwitch(this);
		return true;
	}
}
