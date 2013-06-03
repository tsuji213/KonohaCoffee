package org.KonohaScript.CodeGen;

public class LLVMCodeGenTest extends CodeGeneratorTester {
	@Override
	CodeGenerator CreteCodeGen() {
		return new LLVMCodeGen();
	}

	@Override
	Object testReturnConst() {
		return "func1(void)\n" +"{\n" +"  return 1;\n" + "}";
	}

	@Override
	Object testAddOne() {
		return "int AddOne(n)\n" + "{\n" + "  return n + 1;\n" + "}";
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
		return "int fibo(n)\n" + "{\n" + "  if(n < 3) {\n" + "    return 1;" + "\n  } else {\n" + "\n  }\n" + "  return fibo(n-1) + fibo(n-2);\n" + "}";
	}

	@Override
	Object testTopLevelExpr() {
		return null;
	}

	public static void main(String[] args) {
		LLVMCodeGenTest tester = new LLVMCodeGenTest();
		tester.Test(tester);
	}
}
