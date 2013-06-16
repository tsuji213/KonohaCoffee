package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaDef;
import org.KonohaScript.KonohaType;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.SyntaxTree.NodeVisitor.DefineNodeAcceptor;

class DefaultDefineNodeAcceptor implements DefineNodeAcceptor {
	@Override
	public boolean Invoke(DefineNode Node, NodeVisitor Visitor) {
		Visitor.EnterDefine(Node);
		return Visitor.ExitDefine(Node);
	}
}

public class DefineNode extends TypedNode {

	public KonohaDef	DefInfo;

	public DefineNode(KonohaType TypeInfo, KonohaToken KeywordToken,
			KonohaDef DefInfo) {
		super(TypeInfo, KeywordToken);
		this.DefInfo = DefInfo;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.DefineNodeAcceptor.Invoke(this, Visitor);
	}

}