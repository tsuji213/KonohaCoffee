package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class GetterNode extends TypedNode {
	public TypedNode     BaseNode;
	
	public GetterNode(KonohaType TypeInfo, KonohaToken FieldToken, TypedNode BaseNode) {
		super(TypeInfo, FieldToken);
		this.BaseNode = BaseNode;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterField(this);
		return Visitor.ExitField(this);
	}
}
