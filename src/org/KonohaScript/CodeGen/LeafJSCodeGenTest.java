package org.KonohaScript.CodeGen;

public class LeafJSCodeGenTest extends CodeGeneratorTester {
	@Override
	CodeGenerator CreteCodeGen() {
		return new LeafJSCodeGen();
	}

	@Override
	Object testReturnConst() {
		return "var func1 = function() {\n" +
		       "    return 1;\n" +
		       "};";
	}

	@Override
	Object testAddOne() {
		return "var AddOne = function(n) {\n" +
		       "    return n + 1;\n" +
		       "};";
	}

	@Override
	Object testIf() {
		return "var AddOne = function(n) {\n" +
		       "    if (n < 3) {\n" +
		       "        return 1;\n" +
		       "    } else {\n" +
		       "        return 2;\n" +
		       "    };\n" +
		       "    return 3;\n" +
		       "};";
	}

	@Override
	Object testMethodCall() {
		return null;
	}

	@Override
	Object testFibo() {
		return "var fibo = function(n) {\n" +
		       "    if (n < 3) {\n" +
		       "        return 1;\n" +
		       "    };\n" +
		       "    return this.fibo(n - 1) + this.fibo(n - 2);\n" +
		       "};";
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
