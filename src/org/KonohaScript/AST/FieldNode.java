package org.KonohaScript.AST;

import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.CodeGenerator;

public class FieldNode extends TypedNode {
	/* frame[Index][Xindex] (or ($TermToken->text)[Xindex] */
	KToken TermToken;
	int Index;
	int Xindex;
	FieldNode(KToken TermToken, int Index, int Xindex) {
		this.TermToken = TermToken;
		this.Index  = Index;
		this.Xindex = Xindex;
	}
	@Override
	public boolean Evaluate(CodeGenerator Gen) {
	    Gen.EnterField(this);
	    Gen.ExitField(this);
	    return true;
	}
}
