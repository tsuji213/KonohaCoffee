package org.KonohaScript.AST;

import org.KonohaScript.CodeGen.CodeGenerator;

public class BoxNode extends UnaryNode {
	BoxNode(TypedNode Expr) {
		super(Expr);
	}

	@Override
	public boolean Evaluate(CodeGenerator Gen) {
	    Gen.EnterBox(this);
	    Gen.Visit(this.Expr);
	    Gen.ExitBox(this);
	    return true;
	}
}
