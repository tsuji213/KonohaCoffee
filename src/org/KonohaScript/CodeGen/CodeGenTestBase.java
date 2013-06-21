package org.KonohaScript.CodeGen;

import org.KonohaScript.Konoha;
import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaMethodInvoker;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaParam;
import org.KonohaScript.KonohaType;
import org.KonohaScript.Grammar.MiniKonohaGrammar;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.SyntaxTree.AndNode;
import org.KonohaScript.SyntaxTree.ApplyNode;
import org.KonohaScript.SyntaxTree.AssignNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.ErrorNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.NewNode;
import org.KonohaScript.SyntaxTree.NullNode;
import org.KonohaScript.SyntaxTree.OrNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.ThrowNode;
import org.KonohaScript.SyntaxTree.TypedNode;
import org.KonohaScript.Tester.KTestCase;

abstract class CodeGeneratorTester extends KTestCase {
	CodeGenerator CreateCodeGen() {
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

	//test case: const value
	Object testConstInteger() {
		return null;
	}

	Object testNegativeConstInteger() {
		return null;
	}

	Object testConstIntegers() {
		return null;
	}

	Object testConstBooleanTrue() {
		return null;
	}

	Object testConstBooleanFalse() {
		return null;
	}

	Object testConstBooleans() {
		return null;
	}

	Object testConstString() {
		return null;
	}

	Object testConstString2() {
		return null;
	}

	Object testConstString3() {
		return null;
	}

	Object testConstStrings() {
		return null;
	}

	//test case: variable
	Object testAssign() {
		return null;
	}

	Object testBinaryOps() {
		return null;
	}

	Object testCondLogicalOps() {
		return null;
	}

	Object testReturnConstBoolean() {
		return null;
	}

	Object testReturnConstString() {
		return null;
	}

	Object testThrow() {
		return null;
	}

	Object testError() {
		return null;
	}

	CodeGenTestBase		TestRules;
	CodeGeneratorTester	test;

	public CodeGeneratorTester GetTester() {
		return this;
	}

	@Override
	public void Init() {
		this.TestRules = new CodeGenTestBase();
		this.test = this;
	}

	@Override
	public void Exit() {
	}

	@Override
	public void Test() {

		this.TestRules.testReturnConst(this.test);
		this.TestRules.testAddOne(this.test);
		this.TestRules.testIf(this.test);
		this.TestRules.testMethodCall(this.test);
		this.TestRules.testFibo(this.test);
		this.TestRules.testTopLevelExpr(this.test);

		this.TestRules.testConstInteger(this.test);
		this.TestRules.testNegativeConstInteger(this.test);
		this.TestRules.testConstIntegers(this.test);
		this.TestRules.testConstBooleanTrue(this.test);
		this.TestRules.testConstBooleanFalse(this.test);
		this.TestRules.testConstBooleans(this.test);
		this.TestRules.testConstString(this.test);
		this.TestRules.testConstString2(this.test);
		this.TestRules.testConstString3(this.test);
		this.TestRules.testConstStrings(this.test);

		this.TestRules.testBinaryOps(this.test);
		this.TestRules.testCondLogicalOps(this.test);
		this.TestRules.testReturnConstBoolean(this.test);
		this.TestRules.testReturnConstString(this.test);

		this.TestRules.testThrow(this.test);
		this.TestRules.testError(this.test);

		this.TestRules.testAssign(this.test);
	}
}

public class CodeGenTestBase extends KTestCase {

	public static final Konoha		KonohaContext	= new Konoha(new MiniKonohaGrammar(), null);
	public final KonohaNameSpace	NameSpace		= KonohaContext.DefaultNameSpace;
	public final KonohaType			VoidTy			= KonohaContext.VoidType;
	public final KonohaType			ObjectTy		= KonohaContext.ObjectType;
	public final KonohaType			BooleanTy		= KonohaContext.BooleanType;
	public final KonohaType			IntTy			= KonohaContext.IntType;
	public final KonohaType			StringTy		= KonohaContext.StringType;

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
		CodeGenerator Builder = Tester.CreateCodeGen();
		KonohaType[] ParamData1 = new KonohaType[1];
		String[] ArgData1 = new String[0];
		ParamData1[0] = this.IntTy;
		KonohaParam Param1 = new KonohaParam(1, ParamData1, ArgData1);
		KonohaMethod func1 = new KonohaMethod(0, this.VoidTy, "func1", Param1, null);
		Builder.Prepare(func1);
		TypedNode Block = new ReturnNode(this.IntTy, new ConstNode(this.IntTy, null, 1));
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.AssertEqual(Mtd.CompiledCode, Tester.testReturnConst());
	}

	public void testAddOne(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();
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
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.AssertEqual(Mtd.CompiledCode, Tester.testAddOne());
	}

	public void testIf(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();
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

		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.AssertEqual(Mtd.CompiledCode, Tester.testIf());
	}

	public void testTopLevelExpr(CodeGeneratorTester Tester) {
		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		CodeGenerator Builder = Tester.CreateCodeGen();
		Builder.Prepare(GlobalFunction);

		TypedNode Block = new IfNode(this.VoidTy,
		/* cond */new ConstNode(this.BooleanTy, null, true),
		/* then */new NewNode(this.ObjectTy),
		/* else */new ReturnNode(this.IntTy, new ConstNode(this.BooleanTy, null, false)));
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(Program, Tester.testTopLevelExpr());
	}

	void testMethodCall(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();
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
				new ApplyNode(
						this.IntTy,
						null,
						Fibo,
						new NullNode(this.VoidTy/* FIXME */),
						new ConstNode(this.IntTy, null, 36))));
		Builder.Prepare(GlobalFunction);
		KonohaMethodInvoker Mtd = Builder.Compile(Block1);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(Program, Tester.testMethodCall());
	}

	void testFibo(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();
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
		KonohaMethod intLt = new KonohaMethod(0, this.IntTy, "<", Param3, null);

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
			KonohaMethodInvoker Mtd = Builder.Compile(Block2);
			this.Assert(Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			this.AssertEqual(Program, Tester.testFibo());
		}
	}

	//additional test case
	public void testConstInteger(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ConstNode(this.IntTy, null, 123);
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(Program, Tester.testConstInteger());
	}

	public void testNegativeConstInteger(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ConstNode(this.IntTy, null, -123);
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(Program, Tester.testNegativeConstInteger());
	}

	public void testConstIntegers(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ConstNode(this.IntTy, null, 9999999).Next(new ConstNode(this.IntTy, null, -86757));
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(Program, Tester.testConstIntegers());
	}

	public void testConstBooleanTrue(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ConstNode(this.BooleanTy, null, true);
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(Program, Tester.testConstBooleanTrue());
	}

	public void testConstBooleanFalse(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ConstNode(this.BooleanTy, null, false);
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(Program, Tester.testConstBooleanFalse());
	}

	public void testConstBooleans(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ConstNode(this.BooleanTy, null, false).Next(new ConstNode(this.BooleanTy, null, true));
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(Program, Tester.testConstBooleans());
	}

	public void testConstString(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ConstNode(this.StringTy, null, "Hello World!!");
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(Program, Tester.testConstString());
	}

	public void testConstString2(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ConstNode(this.StringTy, null, "\"Hello World!!\"");
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(Program, Tester.testConstString2());
	}

	public void testConstString3(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ConstNode(this.StringTy, null, "こんにちは世界");
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(/*ConstString3", */Tester.testConstString3(), Program);
	}

	public void testConstStrings(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ConstNode(this.StringTy, null, "Hello World!!").Next(new ConstNode(
				this.BooleanTy,
				null,
				"こんにちは世界"));
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(/*ConstStrings", */Tester.testConstStrings(), Program);
	}

	public void testBinaryOps(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = { this.VoidTy };
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		// Binary operations: ReceiverType = int, ReturnType= int, ParamType = int
		KonohaType[] int_int_type = { this.IntTy, this.IntTy }; //retType, paramType
		String[] binaryArgData = { "x" };
		KonohaParam int_int_param = new KonohaParam(2, int_int_type, binaryArgData);

		KonohaMethod intAdd = new KonohaMethod(0, this.IntTy, "+", int_int_param, null); //Receiver Type: Return Type, Params Type
		KonohaMethod intSub = new KonohaMethod(0, this.IntTy, "-", int_int_param, null);
		KonohaMethod intMul = new KonohaMethod(0, this.IntTy, "*", int_int_param, null);
		KonohaMethod intDiv = new KonohaMethod(0, this.IntTy, "/", int_int_param, null);

		// Binary operations: ReceiverType = int, ReturnType= Boolean, ParamType = int
		KonohaType[] bool_int_type = { this.BooleanTy, this.IntTy };
		KonohaParam bool_int_param = new KonohaParam(2, bool_int_type, binaryArgData);

		KonohaMethod intLT = new KonohaMethod(0, this.IntTy, "<", bool_int_param, null);
		KonohaMethod intLTEQ = new KonohaMethod(0, this.IntTy, "<=", bool_int_param, null);
		KonohaMethod intGT = new KonohaMethod(0, this.IntTy, ">", bool_int_param, null);
		KonohaMethod intGTEQ = new KonohaMethod(0, this.IntTy, ">=", bool_int_param, null);
		KonohaMethod intEQ = new KonohaMethod(0, this.IntTy, "==", bool_int_param, null); //FIXME
		KonohaMethod intNEQ = new KonohaMethod(0, this.IntTy, "!=", bool_int_param, null); //FIXME

		TypedNode Block = new ApplyNode(this.IntTy, null, intAdd, new ConstNode(this.IntTy, null, 1024), new ConstNode(
				this.IntTy,
				null,
				-256))
				.Next(new ApplyNode(this.IntTy, null, intSub, new ConstNode(this.IntTy, null, 1024), new ConstNode(
						this.IntTy,
						null,
						-256)))
				.Next(new ApplyNode(this.IntTy, null, intMul, new ConstNode(this.IntTy, null, 1024), new ConstNode(
						this.IntTy,
						null,
						-256)))
				.Next(new ApplyNode(this.IntTy, null, intDiv, new ConstNode(this.IntTy, null, 1024), new ConstNode(
						this.IntTy,
						null,
						-256)))
				.

				Next(new ApplyNode(this.BooleanTy, null, intLT, new ConstNode(this.IntTy, null, 1024), new ConstNode(
						this.IntTy,
						null,
						-256)))
				.Next(new ApplyNode(this.BooleanTy, null, intLTEQ, new ConstNode(this.IntTy, null, 1024), new ConstNode(
						this.IntTy,
						null,
						-256)))
				.Next(new ApplyNode(this.BooleanTy, null, intGT, new ConstNode(this.IntTy, null, 1024), new ConstNode(
						this.IntTy,
						null,
						-256)))
				.Next(new ApplyNode(this.BooleanTy, null, intGTEQ, new ConstNode(this.IntTy, null, 1024), new ConstNode(
						this.IntTy,
						null,
						-256)))
				.Next(new ApplyNode(this.BooleanTy, null, intEQ, new ConstNode(this.IntTy, null, 1024), new ConstNode(
						this.IntTy,
						null,
						-256)))
				.Next(new ApplyNode(this.BooleanTy, null, intNEQ, new ConstNode(this.IntTy, null, 1024), new ConstNode(
						this.IntTy,
						null,
						-256)));

		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(/*BinaryOps", */Tester.testBinaryOps(), Program);
	}

	public void testCondLogicalOps(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = { this.VoidTy };
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		// Binary operations: ReceiverType = int, ReturnType= Boolean, ParamType = int
		KonohaType[] bool_int_type = { this.BooleanTy, this.IntTy };
		String[] binaryArgData = { "x" };
		KonohaParam bool_int_param = new KonohaParam(2, bool_int_type, binaryArgData);

		KonohaMethod intLT = new KonohaMethod(0, this.IntTy, "<", bool_int_param, null);
		KonohaMethod intGT = new KonohaMethod(0, this.IntTy, ">", bool_int_param, null);
		KonohaMethod intGTEQ = new KonohaMethod(0, this.IntTy, ">=", bool_int_param, null);
		KonohaMethod intNEQ = new KonohaMethod(0, this.IntTy, "!=", bool_int_param, null); //FIXME

		TypedNode Block = new AndNode(this.BooleanTy, null, new ApplyNode(this.BooleanTy, null, intLT, new ConstNode(
				this.IntTy,
				null,
				1024), new ConstNode(this.IntTy, null, -256)), new ApplyNode(this.BooleanTy, null, intNEQ, new ConstNode(
				this.IntTy,
				null,
				1024), new ConstNode(this.IntTy, null, -256)))
				.Next(new AndNode(this.BooleanTy, null, new ApplyNode(this.BooleanTy, null, intGTEQ, new ConstNode(
						this.IntTy,
						null,
						1024), new ConstNode(this.IntTy, null, -256)), new ApplyNode(
						this.BooleanTy,
						null,
						intNEQ,
						new ConstNode(this.IntTy, null, 1024),
						new ConstNode(this.IntTy, null, -256))))
				.Next(new AndNode(this.BooleanTy, null, new ConstNode(this.BooleanTy, null, true), new ApplyNode(
						this.BooleanTy,
						null,
						intGT,
						new ConstNode(this.IntTy, null, 1024),
						new ConstNode(this.IntTy, null, -256))))
				.Next(new AndNode(this.BooleanTy, null, new ConstNode(this.BooleanTy, null, false), new ApplyNode(
						this.BooleanTy,
						null,
						intGT,
						new ConstNode(this.IntTy, null, 1024),
						new ConstNode(this.IntTy, null, -256))))
				.

				Next(new OrNode(this.BooleanTy, null, new ApplyNode(this.BooleanTy, null, intLT, new ConstNode(
						this.IntTy,
						null,
						1024), new ConstNode(this.IntTy, null, -256)), new ApplyNode(
						this.BooleanTy,
						null,
						intNEQ,
						new ConstNode(this.IntTy, null, 1024),
						new ConstNode(this.IntTy, null, -256))))
				.Next(new OrNode(this.BooleanTy, null, new ApplyNode(this.BooleanTy, null, intGTEQ, new ConstNode(
						this.IntTy,
						null,
						1024), new ConstNode(this.IntTy, null, -256)), new ApplyNode(
						this.BooleanTy,
						null,
						intNEQ,
						new ConstNode(this.IntTy, null, 1024),
						new ConstNode(this.IntTy, null, -256))))
				.Next(new OrNode(this.BooleanTy, null, new ConstNode(this.BooleanTy, null, true), new ApplyNode(
						this.BooleanTy,
						null,
						intGT,
						new ConstNode(this.IntTy, null, 1024),
						new ConstNode(this.IntTy, null, -256))))
				.Next(new OrNode(this.BooleanTy, null, new ConstNode(this.BooleanTy, null, false), new ApplyNode(
						this.BooleanTy,
						null,
						intGT,
						new ConstNode(this.IntTy, null, 1024),
						new ConstNode(this.IntTy, null, -256))));
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(/*CondLogicalOps", */Tester.testCondLogicalOps(), Program);
	}

	public void testReturnConstBoolean(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ReturnNode(this.BooleanTy, new ConstNode(this.BooleanTy, null, true));
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(/*ReturnConstBoolean", */Tester.testReturnConstBoolean(), Program);
	}

	public void testReturnConstString(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ReturnNode(this.StringTy, new ConstNode(this.StringTy, null, "Hello World"));
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(/*ReturnConstString", */Tester.testReturnConstString(), Program);
	}

	public void testThrow(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ThrowNode(this.VoidTy, new ConstNode(this.StringTy, null, "IOException"));
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(/*Throw", */Tester.testThrow(), Program);
	}

	public void testError(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new ErrorNode(this.VoidTy, new KonohaToken("printlg", 123), "invalid symbol");
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(/*Error", */Tester.testError(), Program);
	}

	public void testAssign(CodeGeneratorTester Tester) {
		CodeGenerator Builder = Tester.CreateCodeGen();

		KonohaType[] ParamData = new KonohaType[1];
		ParamData[0] = this.VoidTy;
		String[] ArgData1 = new String[0];
		KonohaParam Param = new KonohaParam(1, ParamData, ArgData1);

		KonohaMethod GlobalFunction = new KonohaMethod(0, this.VoidTy, "", Param, null);

		Builder.Prepare(GlobalFunction);

		TypedNode Block = new AssignNode(this.IntTy, null, new LocalNode(this.IntTy, null, "localVar"), new ConstNode(
				this.IntTy,
				null,
				123)).Next(new AssignNode(this.IntTy, null, new LocalNode(this.IntTy, null, "localVar"), new ConstNode(
				this.IntTy,
				null,
				1024)));
		KonohaMethodInvoker Mtd = Builder.Compile(Block);
		this.Assert(Mtd.CompiledCode instanceof String);
		String Program = (String) Mtd.CompiledCode;
		this.AssertEqual(/*Assign", */Tester.testAssign(), Program);
	}

	@Override
	public void Init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Test() {
		// TODO Auto-generated method stub

	}
}
