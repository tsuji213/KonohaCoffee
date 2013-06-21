package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.Parser.KonohaToken;

public class GetterNode extends FieldNode {
	public TypedNode	BaseNode;

	public GetterNode(KonohaType TypeInfo, KonohaToken FieldToken, TypedNode BaseNode) {
		super(TypeInfo, FieldToken, FieldToken.ParsedText);
		this.BaseNode = BaseNode;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.VisitGetter(this);
	}
}
