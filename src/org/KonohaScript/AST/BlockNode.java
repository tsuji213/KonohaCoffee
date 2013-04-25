package org.KonohaScript.AST;

import java.util.ArrayList;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.CodeGenerator;

public class BlockNode extends TypedNode {
	public ArrayList<TypedNode> ExprList;

	/* [Expr1, Expr2, ... ] */
	public BlockNode(KClass ClassInfo) {
		super(ClassInfo);
		this.ExprList = new ArrayList<TypedNode>();
	}

	public BlockNode(KClass ClassInfo, TypedNode Node1) {
		super(ClassInfo);
		init();
		this.ExprList.add(Node1);
	}

	public BlockNode(KClass ClassInfo, TypedNode Node1, TypedNode Node2) {
		super(ClassInfo);
		init();
		this.ExprList.add(Node1);
		this.ExprList.add(Node2);
	}

	public BlockNode(KClass ClassInfo, TypedNode Node1, TypedNode Node2,
			TypedNode Node3) {
		super(ClassInfo);
		init();
		this.ExprList.add(Node1);
		this.ExprList.add(Node2);
		this.ExprList.add(Node3);
	}

	void init() {
		this.ExprList = new ArrayList<TypedNode>();
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
		Gen.EnterBlock(this);
		for (TypedNode Node : this.ExprList) {
			Gen.Visit(Node);
		}
		Gen.ExitBlock(this);
		return true;
	}
}
