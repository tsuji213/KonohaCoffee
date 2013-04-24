package org.KonohaScript.CodeGen;

public class ErrorNode extends TypedNode {
	String ErrorMessage;
	ErrorNode(String ErrorMessage) {
		this.ErrorMessage = ErrorMessage;
	}
}
