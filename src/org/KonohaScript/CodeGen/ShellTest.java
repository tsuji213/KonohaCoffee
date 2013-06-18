package org.KonohaScript.CodeGen;

import org.KonohaScript.Konoha;
import org.KonohaScript.Grammar.ShellGrammar;

public class ShellTest extends ASTInterpreter {
	public static void main(String[] args) {
		Konoha konoha = new Konoha(new ShellGrammar(), "org.KonohaScript.CodeGen.ASTInterpreter");
		konoha.Eval("$(ls -la)", 0);
		//konoha.Eval("int add(int x) { return x + 1; }", 0);
		//konoha.Eval("add(10);", 0);
	}
}
