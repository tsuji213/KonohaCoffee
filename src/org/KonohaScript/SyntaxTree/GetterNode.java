package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KonohaToken;

public class GetterNode extends TypedNode {
	public TypedNode     BaseNode;
	
	public GetterNode(KonohaType TypeInfo, KonohaToken FieldToken, TypedNode BaseNode) {
		super(TypeInfo, FieldToken);
		this.BaseNode = BaseNode;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		Visitor.EnterField(this);
		return Visitor.ExitField(this);
	}
}
