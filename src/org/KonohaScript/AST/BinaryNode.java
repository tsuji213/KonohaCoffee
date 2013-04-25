package org.KonohaScript.AST;

import org.KonohaScript.KClass;

public abstract class BinaryNode extends TypedNode {
	TypedNode Left;
	TypedNode Right;

	BinaryNode(KClass ClassInfo, TypedNode Left, TypedNode Right) {
		super(ClassInfo);
		this.Left = Left;
		this.Right = Right;
	}
}