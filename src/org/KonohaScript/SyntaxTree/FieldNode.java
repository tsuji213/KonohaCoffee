package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;

abstract class FieldNode extends TypedNode {
	public String	FieldName;

	public FieldNode(KonohaType TypeInfo, KonohaToken SourceToken, String FieldName) {
		super(TypeInfo, SourceToken);
		this.FieldName = FieldName;
	}

	public String GetFieldName() {
		return this.FieldName;
	}
}