package org.KonohaScript.AST;

import org.KonohaScript.KClass;

public abstract class BinaryNode extends TypedNode {
	TypedNode Left;
	TypedNode Right;

	BinaryNode(KClass TypeInfo, TypedNode Left, TypedNode Right) {
		super(TypeInfo);
		this.Left = Left;
		this.Right = Right;
	}
}