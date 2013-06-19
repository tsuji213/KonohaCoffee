package org.KonohaScript.CodeGen;

import org.KonohaScript.Konoha;
import org.KonohaScript.Grammar.ShellGrammar;
import org.KonohaScript.Tester.KTestCase;

public class ShellTest extends KTestCase {

	Konoha konoha;

	@Override
	public void Init() {
		konoha = new Konoha(new ShellGrammar(), "org.KonohaScript.CodeGen.ASTInterpreter");
	}

	@Override
	public void Exit() {
	}

	@Override
	public void Test() {	
		konoha.Eval("$(ls -la | grep .txt > listoftext.txt) ", 0);
		/*
		SubProc p0 = new SubProc("ls");
		p0.AddArgument("-la");
		p0.Fg();
		SubProc p1 = new SubProc("grep");
		p1.AddArgument(".txt");
		p0.Pipe(p1);
		p1.SetOutputFileName("listoftext.txt");
		p1.Fg();
		*/
	}

	public static void main(String[] args) {
		ShellTest test = new ShellTest();
		test.Init();
		test.Test();
		test.Exit();
	}
}
