package org.KonohaScript.AST;

import org.KonohaScript.CodeGen.CodeGenerator;

public class IfNode extends TypedNode {
	TypedNode CondExpr;
	BlockNode ThenBlock;
	BlockNode ElseBlock;
	/* If CondExpr then ThenBlock else ElseBlock */
	IfNode(TypedNode CondExpr, BlockNode ThenBlock, BlockNode ElseBlock) {
		this.CondExpr = CondExpr;
		this.ThenBlock = ThenBlock;
		this.ElseBlock = ElseBlock;
	}
	
	@Override
	public boolean Evaluate(CodeGenerator Gen) {
	    Gen.EnterIf(this);
	    Gen.Visit(this.CondExpr);
	    Gen.Visit(this.ThenBlock);
	    Gen.Visit(this.ElseBlock);
	    Gen.ExitIf(this);
	    return true;
	}
}