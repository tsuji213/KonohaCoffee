package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaDef;
import org.KonohaScript.KonohaType;
import org.KonohaScript.CodeGen.ASTVisitor;

public class DefineNode extends TypedNode {

	public KonohaDef	DefInfo;

	public DefineNode(KonohaType TypeInfo, KonohaDef DefInfo) {
		super(TypeInfo);
		this.DefInfo = DefInfo;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterDefine(this);
		return Visitor.ExitDefine(this);
	}
}