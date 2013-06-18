package org.KonohaScript.CodeGen;

import org.KonohaScript.Konoha;
import org.KonohaScript.Grammar.ShellGrammar;
import org.KonohaScript.Tester.KTestCase;

public class ShellTest extends KTestCase {

	@Override
	public void Init() {
	}

	@Override
	public void Exit() {
	}

	@Override
	public void Test() {
		Konoha konoha = new Konoha(new ShellGrammar(), "org.KonohaScript.CodeGen.ASTInterpreter");
		konoha.Eval("$(ls -la | grep .txt > listoftext.txt)", 0);
	}

	public static void main(String[] args) {
		new ShellTest().Test();
	}
}
