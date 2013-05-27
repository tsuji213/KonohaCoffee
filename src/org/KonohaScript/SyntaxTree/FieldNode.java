package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class FieldNode extends TypedNode {
	/* ($TermToken->text)[Offset] */
	public KToken	TermToken;
	public int		Offset;

	public FieldNode(KClass TypeInfo, KToken TermToken, int Offset) {
		super(TypeInfo);
		this.TermToken = TermToken;
		this.Offset = Offset;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterField(this);
		return Visitor.ExitField(this);
	}
}
