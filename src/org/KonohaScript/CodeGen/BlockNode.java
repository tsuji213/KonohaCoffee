package org.KonohaScript.CodeGen;

import java.util.ArrayList;

public class BlockNode extends TypedNode {
	ArrayList<TypedNode> ExprList;
	/* [Expr1, Expr2, ... ]*/
	BlockNode() {
		this.ExprList = new ArrayList<TypedNode>();
	}
}
