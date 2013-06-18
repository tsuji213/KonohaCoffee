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
	Object testBinaryOps() {
		return "{\n" + "    1024 + -256;\n" + "    1024 - -256;\n" + "    1024 * -256;\n" + "    1024 / -256;\n"
				+ "    1024 < -256;\n" + "    1024 <= -256;\n" + "    1024 > -256;\n" + "    1024 >= -256;\n"
				+ "    1024 == -256;\n" + "    1024 != -256;\n" + "}";
	}

	@Override
	Object testCondLogicalOps() {
		return "{\n" + "    1024 < -256 && 1024 != -256;\n" + "    1024 >= -256 && 1024 != -256;\n"
				+ "    true && 1024 > -256;\n" + "    false && 1024 > -256;\n" + "    1024 < -256 || 1024 != -256;\n"
				+ "    1024 >= -256 || 1024 != -256;\n" + "    true || 1024 > -256;\n" + "    false || 1024 > -256;\n" + "}";
	}

	@Override
	Object testReturnConstBoolean() {
		return "{\n" + "    return true;\n" + "}";
	}

	@Override
	Object testReturnConstString() {
		return "{\n" + "    return Hello World;\n" + "}";
	}

	@Override
	Object testThrow() {
		return "{\n" + "    throw IOException;\n" + "}";
	}

	@Override
	Object testError() { //FIXME
		return "{\n" + "    throw IOException;\n" + "}";
	}

	@Override
	Object testAssign() {
		return "{\n" + "    var localVar = 123;\n" + "    localVar = 1024;\n" + "}";
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
