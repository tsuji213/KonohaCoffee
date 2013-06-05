package org.KonohaScript.CodeGen;

import org.KonohaScript.KLib.*;

import org.KonohaScript.Konoha;
import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaParam;
import org.KonohaScript.KonohaType;
import org.KonohaScript.MiniKonoha.MiniKonohaGrammar;
import org.KonohaScript.SyntaxTree.ApplyNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.NewNode;
import org.KonohaScript.SyntaxTree.NullNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.TypedNode;

abstract class CodeGeneratorTester {
	CodeGenerator CreteCodeGen() {
		return null;
	}

	Object testReturnConst() {
		return null;
	}

	Object testAddOne() {
		return null;
	}

	Object testIf() {
		return null;
	}

	Object testMethodCall() {
		return null;
	}

	Object testFibo() {
		return null;
	}

	Object testTopLevelExpr() {
		return null;
	}

	public void Test(CodeGeneratorTester test) {
		CodeGenTestBase TestRules = new CodeGenTestBase();
		TestRules.testReturnConst(test);
		TestRules.testAddOne(test);
		TestRules.testIf(test);
		TestRules.testMethodCall(test);
		TestRules.testFibo(test);
		TestRules.testTopLevelExpr(test);
	}
}

public class CodeGenTestBase {

	public static final Konoha	KonohaContext	= new Konoha(new MiniKonohaGrammar(), null);
	public final KonohaType		VoidTy			= KonohaContext.VoidType;
	public final KonohaType		ObjectTy		= KonohaContext.ObjectType;
	public final KonohaType		BooleanTy		= KonohaContext.BooleanType;
	public final KonohaType		IntTy			= KonohaContext.IntType;
	public final KonohaType		StringTy		= KonohaContext.StringType;

	void Check(String TestName, Object Expected, Object Actual) {
		if(Expected != null && Actual != null && !Expected.equals(Actual)) {
			System.out.println("Test Failed!!" + TestName);
			System.out.println("---  Actual:---");
			System.out.println(Actual);
			System.out.println("---Expected:---");
			System.out.println(Expected);
		}
	}

	public void testReturnConst(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreteCodeGen();
		KonohaType[] ParamData1 = new KonohaType[1];
		String[] ArgData1 = new String[0];
		ParamData1[0] = this.IntTy;
		KonohaParam Param1 = new KonohaParam(1, ParamData1, ArgData1);
		KonohaMethod func1 = new KonohaMethod(0, this.VoidTy, "func1", Param1, null);
		Builder.Prepare(func1);
		TypedNode Block = new ReturnNode(this.IntTy, new ConstNode(this.IntTy, null, 1));
		CompiledMethod Mtd = Builder.Compile(Block);
		this.Check("ReturnConstInt", Tester.testReturnConst(), Mtd.CompiledCode);
	}

	public void testAddOne(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreteCodeGen();
		KonohaType[] ParamData1 = new KonohaType[1];
		String[] ArgData1 = new String[0];
		ParamData1[0] = this.IntTy;
		KonohaParam Param1 = new KonohaParam(1, ParamData1, ArgData1);
		KonohaMethod func1 = new KonohaMethod(0, this.VoidTy, "AddOne", Param1, null);

		KonohaType[] ParamData2 = new KonohaType[2];
		ParamData2[0] = this.IntTy;
		ParamData2[1] = this.IntTy;
		String[] ArgData2 = new String[1];
		ArgData2[0] = "x";
		KonohaParam Param2 = new KonohaParam(2, ParamData2, ArgData2);

		KonohaMethod intAdd = new KonohaMethod(0, this.IntTy, "+", Param2, null);

		KonohaArray Params = new KonohaArray();
		Params.add(new Param(0, this.IntTy, "n"));
		Builder.Prepare(func1, Params);

		TypedNode Block = new ReturnNode(this.IntTy, new ApplyNode(this.VoidTy, null, intAdd, new LocalNode(
				this.IntTy,
				null,
				"n"), new ConstNode(this.IntTy, null, 1)));
		CompiledMethod Mtd = Builder.Compile(Block);
		this.Check("AddOne", Tester.testAddOne(), Mtd.CompiledCode);
	}

	public void testIf(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreteCodeGen();
		KonohaType[] ParamData1 = new KonohaType[1];
		ParamData1[0] = this.IntTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param1 = new KonohaParam(1, ParamData1, ArgData1);
		KonohaMethod func1 = new KonohaMethod(0, this.VoidTy, "AddOne", Param1, null);

		KonohaType[] ParamData2 = new KonohaType[2];
		ParamData2[0] = this.BooleanTy;
		ParamData2[1] = this.IntTy;
		String[] ArgData2 = new String[1];
		ArgData2[0] = "x";
		KonohaParam Param2 = new KonohaParam(2, ParamData2, ArgData2);

		KonohaMethod intLt = new KonohaMethod(0, this.BooleanTy, "<", Param2, null);

		KonohaArray Params = new KonohaArray();
		Params.add(new Param(0, this.IntTy, "n"));
		Builder.Prepare(func1, Params);

		TypedNode Block = new IfNode(this.VoidTy,
		/* cond */new ApplyNode(this.BooleanTy, null, intLt, new LocalNode(this.IntTy, null, "n"), new ConstNode(
				this.IntTy,
				null,
				3)),
		/* then */new ReturnNode(this.VoidTy, new ConstNode(this.IntTy, null, 1)),
		/* else */new ReturnNode(this.IntTy, new ConstNode(this.IntTy, null, 2))).Next(
		/* */new ReturnNode(this.IntTy, new ConstNode(this.IntTy, null, 3)));

		CompiledMethod Mtd = Builder.Compile(Block);
		this.Check("If", Tester.testIf(), Mtd.CompiledCode);
	}

	public void testTopLevelExpr(CodeGeneratorTester Tester) {
		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		CodeGenerator Builder = Tester.CreteCodeGen();
		Builder.Prepare(GlobalFunction);

		TypedNode Block = new IfNode(this.VoidTy,
		/* cond */new ConstNode(this.BooleanTy, null, true),
		/* then */new NewNode(this.ObjectTy),
		/* else */new ReturnNode(this.IntTy, new ConstNode(this.BooleanTy, null, false)));
		CompiledMethod Mtd = Builder.Compile(Block);
		assert (Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.Check("TopLevelExpr", Tester.testTopLevelExpr(), Program);
	}

	void testMethodCall(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreteCodeGen();
		String[] ArgData1 = new String[0];
		String[] ArgData2 = new String[1];
		ArgData2[0] = "x";
		KonohaType[] ParamData1 = new KonohaType[2];
		ParamData1[0] = this.IntTy;
		ParamData1[1] = this.IntTy;
		KonohaParam Param1 = new KonohaParam(2, ParamData1, ArgData2);

		KonohaType[] ParamData2 = new KonohaType[2];
		ParamData2[0] = this.VoidTy;
		ParamData2[1] = this.StringTy;
		KonohaParam Param2 = new KonohaParam(2, ParamData2, ArgData2);

		KonohaType[] ParamData4 = new KonohaType[1];
		ParamData4[0] = this.StringTy;
		KonohaParam Param4 = new KonohaParam(1, ParamData4, ArgData1);

		KonohaMethod Fibo = new KonohaMethod(0, this.VoidTy, "fibo", Param1, null);
		KonohaMethod p = new KonohaMethod(0, this.VoidTy, "p", Param2, null);

		KonohaMethod toString = new KonohaMethod(0, this.IntTy, "toString", Param4, null);

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		TypedNode Block1 = new ApplyNode(this.VoidTy, null, p, new NullNode(this.VoidTy/* FIXME */), new ApplyNode(
				this.StringTy,
				null,
				toString,
				new NullNode(this.IntTy),
				new ApplyNode(this.IntTy, null, Fibo, new NullNode(this.VoidTy/* FIXME */), new ConstNode(this.IntTy, null, 36))));
		Builder.Prepare(GlobalFunction);
		CompiledMethod Mtd = Builder.Compile(Block1);
		assert (Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.Check("MethodCall", Tester.testMethodCall(), Program);
	}

	void testFibo(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreteCodeGen();
		String[] ArgData2 = new String[1];
		ArgData2[0] = "x";
		KonohaType[] ParamData1 = new KonohaType[2];
		ParamData1[0] = this.IntTy;
		ParamData1[1] = this.IntTy;
		KonohaParam Param1 = new KonohaParam(2, ParamData1, ArgData2);

		KonohaType[] ParamData3 = new KonohaType[2];
		ParamData3[0] = this.BooleanTy;
		ParamData3[1] = this.IntTy;
		KonohaParam Param3 = new KonohaParam(2, ParamData3, ArgData2);

		KonohaMethod Fibo = new KonohaMethod(0, this.VoidTy, "fibo", Param1, null);

		KonohaMethod intAdd = new KonohaMethod(0, this.IntTy, "+", Param1, null);
		KonohaMethod intSub = new KonohaMethod(0, this.IntTy, "-", Param1, null);
		KonohaMethod intLt = new KonohaMethod(0, this.BooleanTy, "<", Param3, null);

		TypedNode Block2 = new IfNode(this.VoidTy,
		/* cond */new ApplyNode(this.BooleanTy, null, intLt, new LocalNode(this.IntTy, null, "n"), new ConstNode(
				this.IntTy,
				null,
				3)),
		/* then */new ReturnNode(this.VoidTy, new ConstNode(this.IntTy, null, 1)),
		/* else */null).Next(new ReturnNode(this.IntTy, new ApplyNode(this.IntTy, null, intAdd, new ApplyNode(
				this.IntTy,
				null,
				Fibo,
				new LocalNode(this.VoidTy, null, "this"),
				new ApplyNode(
						this.IntTy,
						null,
						intSub,
						new LocalNode(this.IntTy, null, "n"),
						new ConstNode(this.IntTy, null, 1))), new ApplyNode(this.IntTy, null, Fibo, new LocalNode(
				this.VoidTy,
				null,
				"this"), new ApplyNode(this.IntTy, null, intSub, new LocalNode(this.IntTy, null, "n"), new ConstNode(
				this.IntTy,
				null,
				2))))));
		{
			KonohaArray Params = new KonohaArray();
			Params.add(new Param(0, this.IntTy, "n"));
			Builder.Prepare(Fibo, Params);
			CompiledMethod Mtd = Builder.Compile(Block2);
			assert (Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			this.Check("Fibo", Tester.testFibo(), Program);
		}
	}

}