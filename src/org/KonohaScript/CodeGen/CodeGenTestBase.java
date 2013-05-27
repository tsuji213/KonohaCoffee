package org.KonohaScript.CodeGen;

import java.util.ArrayList;

import org.KonohaScript.KClass;
import org.KonohaScript.KMethod;
import org.KonohaScript.KParam;
import org.KonohaScript.Konoha;
import org.KonohaScript.MiniKonoha.MiniKonohaGrammar;
import org.KonohaScript.SyntaxTree.BlockNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.ApplyNode;
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

	public final KClass			VoidTy			= KonohaContext.VoidType;
	public final KClass			ObjectTy		= KonohaContext.ObjectType;
	public final KClass			BooleanTy		= KonohaContext.BooleanType;
	public final KClass			IntTy			= KonohaContext.IntType;
	public final KClass			StringTy		= KonohaContext.StringType;

	void Check(String TestName, Object Expected, Object Actual) {
		if (Expected != null && Actual != null && !Expected.equals(Actual)) {
			System.out.println("Test Failed!!" + TestName);
			System.out.println("---  Actual:---");
			System.out.println(Actual);
			System.out.println("---Expected:---");
			System.out.println(Expected);
		}
	}

	public void testReturnConst(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreteCodeGen();
		KClass[] ParamData1 = new KClass[1];
		ParamData1[0] = this.IntTy;
		KParam Param1 = new KParam(1, ParamData1);
		KMethod func1 = new KMethod(0, this.VoidTy, "func1", Param1, null);
		Builder.Prepare(func1);
		TypedNode Block = new BlockNode(this.VoidTy, new ReturnNode(this.IntTy, new ConstNode(this.IntTy, null, 1)));
		CompiledMethod Mtd = Builder.Compile(Block);
		this.Check("ReturnConstInt", Tester.testReturnConst(), Mtd.CompiledCode);
	}

	public void testAddOne(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreteCodeGen();
		KClass[] ParamData1 = new KClass[1];
		ParamData1[0] = this.IntTy;
		KParam Param1 = new KParam(1, ParamData1);
		KMethod func1 = new KMethod(0, this.VoidTy, "AddOne", Param1, null);

		KClass[] ParamData2 = new KClass[2];
		ParamData2[0] = this.IntTy;
		ParamData2[1] = this.IntTy;
		KParam Param2 = new KParam(2, ParamData2);

		KMethod intAdd = new KMethod(0, this.IntTy, "+", Param2, null);

		ArrayList<Local> Params = new ArrayList<Local>();
		Params.add(new Param(0, this.IntTy, "n"));
		Builder.Prepare(func1, Params);

		TypedNode Block = new BlockNode(this.VoidTy, new ReturnNode(this.IntTy, new ApplyNode(this.VoidTy, null, intAdd, new LocalNode(this.IntTy, null, "n"), new ConstNode(this.IntTy, null, 1))));
		CompiledMethod Mtd = Builder.Compile(Block);
		this.Check("AddOne", Tester.testAddOne(), Mtd.CompiledCode);
	}

	public void testIf(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreteCodeGen();
		KClass[] ParamData1 = new KClass[1];
		ParamData1[0] = this.IntTy;
		KParam Param1 = new KParam(1, ParamData1);
		KMethod func1 = new KMethod(0, this.VoidTy, "AddOne", Param1, null);

		KClass[] ParamData2 = new KClass[2];
		ParamData2[0] = this.BooleanTy;
		ParamData2[1] = this.IntTy;
		KParam Param2 = new KParam(2, ParamData2);

		KMethod intLt = new KMethod(0, this.BooleanTy, "<", Param2, null);

		ArrayList<Local> Params = new ArrayList<Local>();
		Params.add(new Param(0, this.IntTy, "n"));
		Builder.Prepare(func1, Params);

		TypedNode Block = new BlockNode(this.VoidTy, new IfNode(this.VoidTy,
		/* cond */new ApplyNode(this.BooleanTy, null, intLt, new LocalNode(this.IntTy, null, "n"), new ConstNode(this.IntTy, null, 3)),
		/* then */new BlockNode(this.VoidTy, new ReturnNode(this.VoidTy, new ConstNode(this.IntTy, null, 1))),
		/* else */new BlockNode(this.VoidTy, new ReturnNode(this.IntTy, new ConstNode(this.IntTy, null, 2)))),
		/* */new ReturnNode(this.IntTy, new ConstNode(this.IntTy, null, 3)));

		CompiledMethod Mtd = Builder.Compile(Block);
		this.Check("If", Tester.testIf(), Mtd.CompiledCode);
	}

	public void testTopLevelExpr(CodeGeneratorTester Tester) {
		KClass[] ParamData = new KClass[1];
		ParamData[0] = this.VoidTy;
		KParam Param = new KParam(1, ParamData);

		KMethod GlobalFunction = new KMethod(0, this.VoidTy, "", Param, null);

		CodeGenerator Builder = Tester.CreteCodeGen();
		Builder.Prepare(GlobalFunction);

		TypedNode Block = new IfNode(this.VoidTy, new ConstNode(this.BooleanTy, null, true), new BlockNode(this.VoidTy, new NewNode(this.ObjectTy)), new BlockNode(this.VoidTy, new ReturnNode(this.IntTy, new ConstNode(this.BooleanTy, null, false))));
		CompiledMethod Mtd = Builder.Compile(Block);
		assert (Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.Check("TopLevelExpr", Tester.testTopLevelExpr(), Program);
	}

	void testMethodCall(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreteCodeGen();

		KClass[] ParamData1 = new KClass[2];
		ParamData1[0] = this.IntTy;
		ParamData1[1] = this.IntTy;
		KParam Param1 = new KParam(2, ParamData1);

		KClass[] ParamData2 = new KClass[2];
		ParamData2[0] = this.VoidTy;
		ParamData2[1] = this.StringTy;
		KParam Param2 = new KParam(2, ParamData2);

		KClass[] ParamData4 = new KClass[1];
		ParamData4[0] = this.StringTy;
		KParam Param4 = new KParam(1, ParamData4);

		KMethod Fibo = new KMethod(0, this.VoidTy, "fibo", Param1, null);
		KMethod p = new KMethod(0, this.VoidTy, "p", Param2, null);

		KMethod toString = new KMethod(0, this.IntTy, "toString", Param4, null);

		KClass[] ParamData = new KClass[1];
		ParamData[0] = this.VoidTy;
		KParam Param = new KParam(1, ParamData);

		KMethod GlobalFunction = new KMethod(0, this.VoidTy, "", Param, null);

		TypedNode Block1 = new BlockNode(this.VoidTy, new ApplyNode(this.VoidTy, null, p, new NullNode(this.VoidTy/* FIXME */), new ApplyNode(this.StringTy, null, toString, new NullNode(this.IntTy), new ApplyNode(
				this.IntTy,
				null,
				Fibo,
				new NullNode(this.VoidTy/* FIXME */),
				new ConstNode(this.IntTy, null, 36)))));
		Builder.Prepare(GlobalFunction);
		CompiledMethod Mtd = Builder.Compile(Block1);
		assert (Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.Check("MethodCall", Tester.testMethodCall(), Program);
	}

	void testFibo(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreteCodeGen();

		KClass[] ParamData1 = new KClass[2];
		ParamData1[0] = this.IntTy;
		ParamData1[1] = this.IntTy;
		KParam Param1 = new KParam(2, ParamData1);

		KClass[] ParamData3 = new KClass[2];
		ParamData3[0] = this.BooleanTy;
		ParamData3[1] = this.IntTy;
		KParam Param3 = new KParam(2, ParamData3);

		KMethod Fibo = new KMethod(0, this.VoidTy, "fibo", Param1, null);

		KMethod intAdd = new KMethod(0, this.IntTy, "+", Param1, null);
		KMethod intSub = new KMethod(0, this.IntTy, "-", Param1, null);
		KMethod intLt = new KMethod(0, this.BooleanTy, "<", Param3, null);

		TypedNode Block2 = new BlockNode(this.VoidTy, new IfNode(this.VoidTy,
		/* cond */new ApplyNode(this.BooleanTy, null, intLt, new LocalNode(this.IntTy, null, "n"), new ConstNode(this.IntTy, null, 3)),
		/* then */new BlockNode(this.VoidTy, new ReturnNode(this.VoidTy, new ConstNode(this.IntTy, null, 1))),
		/* else */new BlockNode(this.VoidTy)), new ReturnNode(this.IntTy, new ApplyNode(this.IntTy, null, intAdd, new ApplyNode(this.IntTy, null, Fibo, new LocalNode(this.VoidTy, null, "this"), new ApplyNode(
				this.IntTy,
				null,
				intSub,
				new LocalNode(this.IntTy, null, "n"),
				new ConstNode(this.IntTy, null, 1))), new ApplyNode(this.IntTy, null, Fibo, new LocalNode(this.VoidTy, null, "this"), new ApplyNode(
				this.IntTy,
				null,
				intSub,
				new LocalNode(this.IntTy, null, "n"),
				new ConstNode(this.IntTy, null, 2))))));
		{
			ArrayList<Local> Params = new ArrayList<Local>();
			Params.add(new Param(0, this.IntTy, "n"));
			Builder.Prepare(Fibo, Params);
			CompiledMethod Mtd = Builder.Compile(Block2);
			assert (Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			this.Check("Fibo", Tester.testFibo(), Program);
		}
	}

}