package org.KonohaScript.AST;

import java.util.ArrayList;

import org.KonohaScript.CodeGen.CodeGenerator;

public class BlockNode extends TypedNode {
	ArrayList<TypedNode> ExprList;
	/* [Expr1, Expr2, ... ]*/
	BlockNode() {
		this.ExprList = new ArrayList<TypedNode>();
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
	    Gen.EnterBlock(this);
	    for(TypedNode Node : this.ExprList) {
	    	Gen.Visit(Node);
	    }
	    Gen.ExitBlock(this);
	    return true;
	}
}
