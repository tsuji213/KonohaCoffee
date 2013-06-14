package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.SyntaxTree.NodeVisitor.GetterNodeAcceptor;

class DefaultGetterNodeAcceptor implements GetterNodeAcceptor {
	@Override
	public boolean Invoke(GetterNode Node, NodeVisitor Visitor) {
		Visitor.EnterGetter(Node);
		Visitor.Visit(Node.BaseNode);
		return Visitor.ExitGetter(Node);
	}
}

public class GetterNode extends FieldNode {
	public TypedNode	BaseNode;

	public GetterNode(KonohaType TypeInfo, KonohaToken FieldToken, TypedNode BaseNode) {
		super(TypeInfo, FieldToken, FieldToken.ParsedText);
		this.BaseNode = BaseNode;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.GetterNodeAcceptor.Invoke(this, Visitor);
	}
}
