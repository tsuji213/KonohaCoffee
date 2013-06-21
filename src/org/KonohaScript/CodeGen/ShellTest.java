package org.KonohaScript.CodeGen;

import java.util.ArrayList;

import org.KonohaScript.Konoha;
import org.KonohaScript.Grammar.ShellGrammar;
import org.KonohaScript.Tester.KTestCase;

public class ShellTest extends KTestCase {

	Konoha	konoha;

	@Override
	public void Init() {
		this.konoha = new Konoha(new ShellGrammar(), ASTInterpreter.class.getName());
	}

	@Override
	public void Exit() {
	}

	public static String Join(ArrayList<String> list, String delim) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < list.size(); i++) {
			if(i > 0) {
				builder.append(delim);
			}
			builder.append(list.get(i));
		}
		return builder.toString();
	}

	public static String Join(String[] list, String delim) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < list.length; i++) {
			if(i > 0) {
				builder.append(delim);
			}
			builder.append(list[i]);
		}
		return builder.toString();
	}

	@Override
	public void Test() {
		this.AssertEqual(Join(ShellGrammar.SplitIntoCommands("ls -la | grep .txt > listoftext.txt"), ","), "ls -la,grep .txt > listoftext.txt");
		this.AssertEqual(Join(ShellGrammar.SplitIntoCommands("echo ' | echo hoge'"), ","), "echo ' | echo hoge'");
		this.AssertEqual(Join(ShellGrammar.SplitIntoCommandTokens("ls -la"), ","), "ls,-la");
		this.AssertEqual(Join(ShellGrammar.SplitIntoCommandTokens("echo '1 2|3 4'"), ","), "echo,1 2|3 4");
		this.AssertEqual(ShellGrammar.FindOutputFileName(ShellGrammar.SplitIntoCommandTokens("grep .txt > listoftext.txt")), "listoftext.txt");
		this.AssertEqual(ShellGrammar.FindInputFileName(ShellGrammar.SplitIntoCommandTokens("find '<test>' < list.txt")), "list.txt");

		//this.konoha.Eval("int num = 100;", 0);
		//this.konoha.Eval("Process p0 = new Process(\"ls\");", 0);
		this.konoha.Eval("Process p0 = new Process(\"ls\");p0.SetArgument(\"-la\");", 0);
		/*
		this.konoha.Eval("Process p0 = new Process();" +
				"p0.SetArgument(\"ls\");" +
				"p0.SetArgument(\"-la\");" +
				"p0.Fg();" +
				"Process p1 = new Process();" +
				"p1.SetArgument(\"grep\");" +
				"p1.SetArgument(\".txt\");" +
				"p0.Pipe(p1);" +
				"p1.SetOutputFileName(\"listoftext.txt\");" +
				"p1.Fg();", 0);
		*/
		/*
		Process p0 = new Process("ls");
		p0.SetArgument("-la");
		p0.Fg();
		Process p1 = new Process("grep");
		p1.SetArgument(".txt");
		p0.Pipe(p1);
		p1.SetOutputFileName("listoftext.txt");
		p1.Fg();
		*/
	}

	public static void main(String[] args) {
		new ShellTest().Execute();
	}
}
