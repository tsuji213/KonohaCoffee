package org.KonohaScript.AST;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class IfNode extends TypedNode {
	TypedNode CondExpr;
	BlockNode ThenBlock;
	BlockNode ElseBlock;

	/* If CondExpr then ThenBlock else ElseBlock */
	public IfNode(KClass ClassInfo, TypedNode CondExpr, BlockNode ThenBlock,
			BlockNode ElseNode) {
		super(ClassInfo);
		this.CondExpr = CondExpr;
		this.ThenBlock = ThenBlock;
		this.ElseBlock = ElseNode;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterIf(this);
		Visitor.Visit(this.CondExpr);
		Visitor.Visit(this.ThenBlock);
		Visitor.Visit(this.ElseBlock);
		Visitor.ExitIf(this);
		return true;
	}
}