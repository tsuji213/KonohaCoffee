package org.KonohaScript.CodeGen;

public abstract class BinaryNode extends TypedNode {
	TypedNode Left;
	TypedNode Right;
	BinaryNode(TypedNode Left, TypedNode Right) {
		this.Left = Left;
		this.Right = Right;
	}
}