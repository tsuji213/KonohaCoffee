package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class ConstNode extends TypedNode {
	public Object ConstValue;

	public ConstNode(KClass TypeInfo, KToken SourceToken, Object ConstValue) {
		super(TypeInfo, SourceToken);
		init(ConstObject);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterConst(this);
		return Visitor.ExitConst(this);
	}
}
