package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.GetterNodeAcceptor;

class DefaultGetterNodeAcceptor implements GetterNodeAcceptor {
	@Override
	public boolean Eval(GetterNode Node, NodeVisitor Visitor) {
		Visitor.EnterGetter(Node);
		Visitor.Visit(Node.BaseNode);
		return Visitor.ExitGetter(Node);
	}
}

public class GetterNode extends TypedNode {
	public TypedNode	BaseNode;
	public String		FieldName;

	public GetterNode(KonohaType TypeInfo, KonohaToken FieldToken,
			TypedNode BaseNode) {
		super(TypeInfo, FieldToken);
		this.BaseNode = BaseNode;
		this.FieldName = FieldToken.ParsedText;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.GetterNodeAcceptor.Eval(this, Visitor);
	}
}
