package org.KonohaScript.Peg.MiniKonoha;

import org.KonohaScript.Tester.KParserTester;

public class MiniKonohaGrammerTest extends KParserTester {

	void testLiteral() {
		AssertEqual(CompileAndCheck(NameSpace, "20;"), new Integer(20));
		AssertEqual(CompileAndCheck(NameSpace, "(10);"), new Integer(10));
		AssertEqual(CompileAndCheck(NameSpace, "true;"), Boolean.TRUE);
		AssertEqual(CompileAndCheck(NameSpace, "false;"), Boolean.FALSE);
		AssertEqual(CompileAndCheck(NameSpace, "\"abcd\";"), "abcd");
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

	void testLogicalOperator() {
		AssertEqual(CompileAndCheck(NameSpace, "true  && true ;"), Boolean.TRUE);
		AssertEqual(CompileAndCheck(NameSpace, "true  && false;"), Boolean.FALSE);
		AssertEqual(CompileAndCheck(NameSpace, "false && false;"), Boolean.FALSE);
		AssertEqual(CompileAndCheck(NameSpace, "false && true ;"), Boolean.FALSE);

		AssertEqual(CompileAndCheck(NameSpace, "true  || true ;"), Boolean.TRUE);
		AssertEqual(CompileAndCheck(NameSpace, "true  || false;"), Boolean.TRUE);
		AssertEqual(CompileAndCheck(NameSpace, "false || false;"), Boolean.FALSE);
		AssertEqual(CompileAndCheck(NameSpace, "false || true ;"), Boolean.TRUE);
	}

	@Override
	public void Test() {
		testLiteral();
		testInteger();
		testMethodDefinition();
		testMethodCall();
		//testLogicalOperator();

	}

	@Override
	public void Init() {
		Init(new MiniKonohaPegGrammar());
	}

	public static void main(String[] args) {
		new MiniKonohaGrammerTest().Execute();
	}

}
