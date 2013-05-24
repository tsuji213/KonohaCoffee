package org.KonohaScript.CodeGen;

public class LeafJSCodeGenTest extends CodeGeneratorTester {
	@Override
	CodeGenerator CreteCodeGen() {
		return new LeafJSCodeGen();
	}

	@Override
	Object testReturnConst() {
		return "var f1 = function(n) {\n" + "  return 1;\n" + "}";
	}

	@Override
	Object testAddOne() {
		return "var AddOne = function(n) {\n" + "  return n + 1;\n" + "}";
	}

	@Override
	Object testIf() {
		return null;
	}

	@Override
	Object testMethodCall() {
		return null;
	}

	@Override
	Object testFibo() {
		return "var fibo = function(n) {" + "if(n < 3) {" + "  return 1;" + "} else {" + "}" + "return fibo(n-1) + fibo(n-2);\n" + "}";
	}

	@Override
	Object testTopLevelExpr() {
		return null;
	}

	public static void main(String[] args) {
		LeafJSCodeGenTest tester = new LeafJSCodeGenTest();
		tester.Test(tester);
	}
}
