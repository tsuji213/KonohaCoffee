package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.CodeGen.ASTVisitor;

public class IfNode extends TypedNode {
	public TypedNode CondExpr;
	public TypedNode ThenNode;
	public TypedNode ElseNode;

	/* If CondExpr then ThenBlock else ElseBlock */
	public IfNode(KonohaType TypeInfo, TypedNode CondExpr, TypedNode ThenBlock, TypedNode ElseNode) {
		super(TypeInfo, null/*fixme*/);
		this.CondExpr = CondExpr;
		this.ThenNode = ThenBlock;
		this.ElseNode = ElseNode;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterIf(this);
		Visitor.Visit(this.CondExpr);
		Visitor.Visit(this.ThenNode);
		Visitor.Visit(this.ElseNode);
		return Visitor.ExitIf(this);
	}
}