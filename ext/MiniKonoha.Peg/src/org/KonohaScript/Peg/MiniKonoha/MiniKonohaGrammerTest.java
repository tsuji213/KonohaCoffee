package org.KonohaScript.Peg.MiniKonoha;

import org.KonohaScript.Konoha;
import org.KonohaScript.KonohaBuilder;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.JUtils.KonohaConst;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.Parser.TypeEnv;
import org.KonohaScript.Parser.UntypedNode;
import org.KonohaScript.SyntaxTree.TypedNode;
import org.KonohaScript.Tester.KTestCase;

public class MiniKonohaGrammerTest extends KTestCase {

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

	void testLiteral() {
		AssertEqual(CompileAndCheck(NameSpace, "20;"), new Integer(20));
		AssertEqual(CompileAndCheck(NameSpace, "(10);"), new Integer(10));
		AssertEqual(CompileAndCheck(NameSpace, "true;"), Boolean.TRUE);
		AssertEqual(CompileAndCheck(NameSpace, "false;"), Boolean.FALSE);
		//AssertEqual(CompileAndCheck(NameSpace, "\"abcd\";"), "abcd");
	}

	void testInteger() {
		AssertEqual(CompileAndCheck(NameSpace, "21 + 13;"), new Integer(21 + 13));
		AssertEqual(CompileAndCheck(NameSpace, "21 - 13;"), new Integer(21 - 13));
		AssertEqual(CompileAndCheck(NameSpace, "21 * 13;"), new Integer(21 * 13));
		AssertEqual(CompileAndCheck(NameSpace, "21 / 13;"), new Integer(21 / 13));
		AssertEqual(CompileAndCheck(NameSpace, "21 % 13;"), new Integer(21 % 13));

		AssertEqual(CompileAndCheck(NameSpace, "11 == 13;"), Boolean.FALSE);
		AssertEqual(CompileAndCheck(NameSpace, "13 == 13;"), Boolean.TRUE);
		AssertEqual(CompileAndCheck(NameSpace, "21 == 13;"), Boolean.FALSE);

		AssertEqual(CompileAndCheck(NameSpace, "11 != 13;"), Boolean.TRUE);
		AssertEqual(CompileAndCheck(NameSpace, "13 != 13;"), Boolean.TRUE);
		AssertEqual(CompileAndCheck(NameSpace, "21 != 13;"), Boolean.TRUE);

		AssertEqual(CompileAndCheck(NameSpace, "11 <  13;"), Boolean.TRUE);
		AssertEqual(CompileAndCheck(NameSpace, "13 <  13;"), Boolean.FALSE);
		AssertEqual(CompileAndCheck(NameSpace, "21 <  13;"), Boolean.FALSE);

		AssertEqual(CompileAndCheck(NameSpace, "11 <= 13;"), Boolean.TRUE);
		AssertEqual(CompileAndCheck(NameSpace, "13 <= 13;"), Boolean.TRUE);
		AssertEqual(CompileAndCheck(NameSpace, "21 <= 13;"), Boolean.FALSE);

		AssertEqual(CompileAndCheck(NameSpace, "11 >  13;"), Boolean.FALSE);
		AssertEqual(CompileAndCheck(NameSpace, "13 >  13;"), Boolean.FALSE);
		AssertEqual(CompileAndCheck(NameSpace, "21 >  13;"), Boolean.TRUE);

		AssertEqual(CompileAndCheck(NameSpace, "11 >= 13;"), Boolean.FALSE);
		AssertEqual(CompileAndCheck(NameSpace, "13 >= 13;"), Boolean.TRUE);
		AssertEqual(CompileAndCheck(NameSpace, "21 >= 13;"), Boolean.TRUE);
	}

	void testMethodDefinition() {
		AssertEqual(CompileAndCheck(NameSpace, "int add(int x) { return x + 1; }"), null);
		AssertEqual(CompileAndCheck(NameSpace, "int add2(int a) {  return add(a + 2);}"), null);
		AssertEqual(CompileAndCheck(NameSpace, "int fibo(int a) { if(a < 3) {return 1;  }  return fibo(a-1)+fibo(a-2);}"), null);
	}

	void testMethodCall() {
		AssertEqual(CompileAndCheck(NameSpace, "add(10);"), new Integer(11));
		AssertEqual(CompileAndCheck(NameSpace, "add2(10);"), new Integer(13));
		//AssertEqual(CompileAndCheck(NameSpace, "fibo(1);"), new Integer(1));
		//AssertEqual(CompileAndCheck(NameSpace, "fibo(6);"), new Integer(8));
	}

	@Override
	public void Test() {
		testLiteral();
		testInteger();
		testMethodDefinition();
		testMethodCall();
		//source = "if(a < b) { f(); } else { return 1; };";
		//source = "if(a < b) { return 1; };";

	}

	public static void main(String[] args) {
		new MiniKonohaGrammerTest().Execute();
	}
}
