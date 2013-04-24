package org.KonohaScript.CodeGen;

public class AndNode extends BinaryNode {
	AndNode(TypedNode Left, TypedNode Right) {
		super(Left, Right);
	}
	
	@Override
	boolean Compile(CodeGenerator Gen) {
		return false; // FIXME
	}

}
