package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class FieldNode extends TypedNode {
	/* ($TermToken->text)[Offset] */
	public KonohaToken	TermToken;
	public int		Offset;

	public FieldNode(KonohaType TypeInfo, KonohaToken TermToken, int Offset) {
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
