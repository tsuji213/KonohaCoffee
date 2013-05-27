package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;

public abstract class BinaryNode extends TypedNode {
	public TypedNode	Left;
	public TypedNode	Right;

	BinaryNode(KonohaType TypeInfo, TypedNode Left, TypedNode Right) {
		super(TypeInfo);
		this.Left = Left;
		this.Right = Right;
	}
}