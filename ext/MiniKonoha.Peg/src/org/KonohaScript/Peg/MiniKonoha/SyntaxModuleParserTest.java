package org.KonohaScript.Peg.MiniKonoha;

import org.KonohaScript.Konoha;
import org.KonohaScript.KonohaBuilder;
import org.KonohaScript.KonohaConst;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.Parser.TypeEnv;
import org.KonohaScript.Parser.UntypedNode;
import org.KonohaScript.SyntaxTree.TypedNode;
import org.KonohaScript.Tester.KTestCase;

public class SyntaxModuleParserTest extends KTestCase {

	Object CompileAndCheck(KonohaNameSpace NameSpace, String Source) {
		TokenList BufferList = NameSpace.Tokenize(Source, 0);
		TokenList TokenList = new TokenList();
		NameSpace.PreProcess(BufferList, 0, BufferList.size(), TokenList);
		KonohaToken.DumpTokenList(0, "Dump::", TokenList, 0, TokenList.size());
		UntypedNode UNode = NameSpace.Parser.ParseNewNode(NameSpace, null, TokenList, 0, TokenList.size(), 0);

		System.out.println("untyped tree: " + UNode);
		TypeEnv Gamma = new TypeEnv(NameSpace, null);
		TypedNode TNode = TypeEnv.TypeCheckEachNode(Gamma, UNode, Gamma.VoidType, KonohaConst.DefaultTypeCheckPolicy);
		KonohaBuilder Builder = NameSpace.GetBuilder();
		Object ResultValue = Builder.EvalAtTopLevel(TNode, NameSpace.GetGlobalObject());

		return ResultValue;
	}

	Konoha			konoha;
	KonohaNameSpace	NameSpace;

	@Override
	public void Init() {
		konoha = new Konoha(new MiniKonohaGrammer(), "org.KonohaScript.CodeGen.ASTInterpreter");
		NameSpace = konoha.DefaultNameSpace;

	}

	@Override
	public void Exit() {
	}

	@Override
	public void Test() {
		//CompileAndCheck(Mod, NameSpace, "void fibo(int a) {\n  if(a < 3) {    return 1;\n  } \n  return fibo(a-1)+fibo(a-2);\n}");
		//CompileAndCheck(Mod, NameSpace, "f(b * c);");
		//CompileAndCheck(Mod, NameSpace, "int sub(int a) {  return (a - 100);}");
		//CompileAndCheck(Mod, NameSpace, "int f(int a) {  return g(a);}");
		AssertEqual(CompileAndCheck(NameSpace, "int add(int x) { return x + 1; }"), null);
		AssertEqual(CompileAndCheck(NameSpace, "add(10);"), new Integer(11));
		//CompileAndCheck(Mod, NameSpace, "(10);");
		//CompileAndCheck(Mod, NameSpace, "20;");
		//CompileAndCheck(Mod, NameSpace, "10 + 20;");
		//source = "10 + 20;";
		//source = "if(a < b) { f(); } else { return 1; };";
		//source = "if(a < b) { return 1; };";

	}

	public static void main(String[] args) {
		SyntaxModuleParserTest Test = new SyntaxModuleParserTest();
		Test.Init();
		Test.Test();
		Test.Exit();
	}
}
