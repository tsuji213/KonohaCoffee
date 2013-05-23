package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.KonohaDef;
import org.KonohaScript.CodeGen.ASTVisitor;

public class DefNode extends TypedNode {
	
	public KonohaDef DefInfo;
	public DefNode(KClass TypeInfo, KonohaDef DefInfo) {
		super(TypeInfo);
		this.DefInfo = DefInfo;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterDone(this);
		return Visitor.ExitDone(this);
	}
}