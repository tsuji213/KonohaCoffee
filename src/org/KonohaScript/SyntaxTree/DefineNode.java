package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.KonohaDef;
import org.KonohaScript.CodeGen.ASTVisitor;

public class DefineNode extends TypedNode {
	
	public KonohaDef DefInfo;
	public DefineNode(KClass TypeInfo, KonohaDef DefInfo) {
		super(TypeInfo);
		this.DefInfo = DefInfo;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterDef(this);
		return Visitor.ExitDef(this);
	}
}