package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;

public abstract class BinaryNode extends TypedNode {
	public TypedNode    LeftNode;
	public TypedNode	RightNode;

	public BinaryNode(KonohaType TypeInfo, KonohaToken OperatorToken, TypedNode Left, TypedNode Right) {
		super(TypeInfo, OperatorToken);
		this.LeftNode  = Left;
		this.RightNode = Right;
	}

}