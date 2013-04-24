package org.KonohaScript.CodeGen;

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
}