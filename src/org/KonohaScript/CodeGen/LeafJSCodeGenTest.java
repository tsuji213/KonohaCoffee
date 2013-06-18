package org.KonohaScript.CodeGen;

public class LeafJSCodeGenTest extends CodeGeneratorTester {

	@Override
	CodeGenerator CreateCodeGen() {
		return new LeafJSCodeGen();
	}

	@Override
	Object testReturnConst() {
		return "var func1 = function() {\n" + "    return 1;\n" + "};";
	}

	@Override
	Object testAddOne() {
		return "var AddOne = function(n) {\n" + "    return n + 1;\n" + "};";
	}

	@Override
	Object testIf() {
		return "var AddOne = function(n) {\n" + "    if(n < 3) {\n" + "        return 1;\n" + "    } else {\n"
				+ "        return 2;\n" + "    };\n" + "    return 3;\n" + "};";
	}

	@Override
	Object testMethodCall() {
		return null;
	}

	@Override
	Object testFibo() {
		return "var fibo = function(n) {\n" + "    if(n < 3) {\n" + "        return 1;\n" + "    };\n"
				+ "    return this.fibo(n - 1) + this.fibo(n - 2);\n" + "};";
	}

	@Override
	Object testTopLevelExpr() {
		return null;
	}

	//additional test case
	@Override
	Object testConstInteger() {
		return "{\n" + "    123;\n" + "}";
	}

	@Override
	Object testNegativeConstInteger() {
		return "{\n" + "    -123;\n" + "}";
	}

	@Override
	Object testConstIntegers() {
		return "{\n" + "    9999999;\n" + "    -86757;\n" + "}";
	}

	@Override
	Object testConstBooleanTrue() {
		return "{\n" + "    true;\n" + "}";
	}

	@Override
	Object testConstBooleanFalse() {
		return "{\n" + "    false;\n" + "}";
	}

	@Override
	Object testConstBooleans() {
		return "{\n" + "    false;\n" + "    true;\n" + "}";
	}

	@Override
	Object testConstString() {
		return "{\n" + "    Hello World!!;\n" + "}";
	}

	@Override
	Object testConstString2() {
		return "{\n" + "    \"Hello World!!\";\n" + "}";
	}

	@Override
	Object testConstString3() {
		return "{\n" + "    こんにちは世界;\n" + "}";
	}

	@Override
	Object testConstStrings() {
		return "{\n" + "    Hello World!!;\n" + "    こんにちは世界;\n" + "}";
	}

	@Override
	Object testIntegerValiable() {
		return "{\n" + "    var lovalVar;\n" + "}";
	}

	@Override
	public CodeGeneratorTester GetTester() {
		return this;
	}

	public static void main(String[] args) {
		LeafJSCodeGenTest tester = new LeafJSCodeGenTest();
		tester.Init();
		tester.Test();
		tester.Exit();
	}
}
