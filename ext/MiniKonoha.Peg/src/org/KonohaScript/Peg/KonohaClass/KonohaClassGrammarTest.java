package org.KonohaScript.Peg.KonohaClass;

import org.KonohaScript.Tester.KParserTester;

public class KonohaClassGrammarTest extends KParserTester {

	@Override
	public void Init() {
		Init(new KonohaClassGrammar());
	}

	void testClassDef() {
		AssertEqual(CompileAndCheck(NameSpace, "class A {}"), null);
		AssertEqual(CompileAndCheck(NameSpace, "class B { int x; }"), null);
		AssertEqual(CompileAndCheck(NameSpace, "class C { int x; int y; }"), null);
	}

	void testClassConstructor() {
		AssertEqual(CompileAndCheck(NameSpace, "class A { int x; A() { this.x = 10; }}"), null);
		AssertEqual(CompileAndCheck(NameSpace, "class B { int x; B(int x) { this.x = x; }}"), null);
	}

	void testClassExtendsDef() {
		AssertEqual(CompileAndCheck(NameSpace, "class A extends Object {}"), null);
		AssertEqual(CompileAndCheck(NameSpace, "class B extends A { int x; }"), null);
		AssertEqual(CompileAndCheck(NameSpace, "class C extends B { int y; }"), null);
	}

	void testClassMethodDef() {
		AssertEqual(CompileAndCheck(NameSpace, "class A { int f() { return 1; } }"), null);
		AssertEqual(CompileAndCheck(NameSpace, "class B { int x; int f() { return x + 1; } }"), null);
		AssertEqual(CompileAndCheck(NameSpace, "class C { int x; int y; }"), null);
	}

	@Override
	public void Test() {
		testClassDef();
		testClassConstructor();
		testClassExtendsDef();
		testClassMethodDef();

	}

	public static void main(String[] args) {
		new KonohaClassGrammarTest().Execute();
	}
}
