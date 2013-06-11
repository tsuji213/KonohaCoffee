package org.KonohaScript.Parser;

import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.KLib.TokenList;

abstract class Syntax {
	String		Name;
	KonohaArray	Childrens;

	public Syntax(String Name) {
		this.Name = Name;
		this.Childrens = null;
	}

	void Init(SyntaxModule syntaxModule) {
	}

	int Fail(String SyntaxName, SyntaxModule Parser) {
		this.Report("Fail " + SyntaxName);
		return -1;
	}

	int BackTrack(SyntaxModule Parser, int pos0, int thunkpos0, int NodeSize0, String message) {
		Parser.Cursor = pos0;
		Parser.ThunkPos = thunkpos0;
		this.Report("BackTrack " + message);
		return NodeSize0;
	}

	int Match(SyntaxModule Parser, TokenList TokenList) {
		return -1;
	}

	void Report(String Message) {
		System.out.println(Message);
	}
}