package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaDef;
import org.KonohaScript.KonohaType;
import org.KonohaScript.Parser.KonohaToken;

public class DefineNode extends TypedNode {

	public KonohaDef	DefInfo;

	public DefineNode(KonohaType TypeInfo, KonohaToken KeywordToken, KonohaDef DefInfo) {
		super(TypeInfo, KeywordToken);
		this.DefInfo = DefInfo;
	}

	@Override
	public boolean Evaluate(NodeVisitor Visitor) {
		return Visitor.VisitDefine(this);
	}

}