package org.KonohaScript.CodeGen;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.KonohaScript.Konoha;
import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaParam;
import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.MiniKonoha.MiniKonohaGrammar;
import org.KonohaScript.SyntaxTree.ApplyNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.TypedNode;

class TestClassLoader extends ClassLoader {

}

public class JVMCodeGenTest {

	public static final Konoha	KonohaContext	= new Konoha(
			new MiniKonohaGrammar(),
			null);
	public static final KonohaType		VoidTy			= KonohaContext.VoidType;
	public static final KonohaType		ObjectTy		= KonohaContext.ObjectType;
	public static final KonohaType		BooleanTy		= KonohaContext.BooleanType;
	public static final KonohaType		IntTy			= KonohaContext.IntType;
	public static final KonohaType		StringTy		= KonohaContext.StringType;

	public static void testReturnConst(JVMCodeGenerator Builder) {
		KonohaType[] ParamData1 = new KonohaType[1];
		String[] ArgData1 = new String[0];
		ParamData1[0] = IntTy;
		KonohaParam Param1 = new KonohaParam(1, ParamData1, ArgData1);
		KonohaMethod func1 = new KonohaMethod(0, VoidTy, "testReturnConst", Param1, null);
		Builder.Prepare(func1);
		TypedNode Block = new ReturnNode(IntTy, new ConstNode(IntTy, null, 1));
		Builder.Compile(Block);
	}

	public static void testAddOne(JVMCodeGenerator Builder) {
		KonohaType[] ParamData1 = new KonohaType[2];
		String[] ArgData1 = new String[1];
		ParamData1[0] = IntTy;
		ParamData1[1] = IntTy;
		ArgData1[0] = "n";
		KonohaParam Param1 = new KonohaParam(2, ParamData1, ArgData1);
		KonohaMethod func1 = new KonohaMethod(0, VoidTy, "testAddOne", Param1, null);

		KonohaType[] ParamData2 = new KonohaType[2];
		ParamData2[0] = IntTy;
		ParamData2[1] = IntTy;
		String[] ArgData2 = new String[1];
		ArgData2[0] = "x";
		KonohaParam Param2 = new KonohaParam(2, ParamData2, ArgData2);

		KonohaMethod intAdd = new KonohaMethod(0, IntTy, "+", Param2, null);

		KonohaArray Params = new KonohaArray();
		Params.add(new Param(0, IntTy, "n"));
		Builder.Prepare(func1, Params);

		TypedNode Block = new ReturnNode(IntTy, new ApplyNode(VoidTy, null, intAdd, new LocalNode(
				IntTy,
				null,
				"n"), new ConstNode(IntTy, null, 1)));
		Builder.Compile(Block);
	}

	public static void testIf(JVMCodeGenerator Builder) {
		KonohaType[] ParamData1 = new KonohaType[2];
		ParamData1[0] = IntTy;
		ParamData1[1] = IntTy;
		String[] ArgData1 = new String[1];
		ArgData1[0] = "n";
		KonohaParam Param1 = new KonohaParam(2, ParamData1, ArgData1);
		KonohaMethod func1 = new KonohaMethod(0, VoidTy, "testIf", Param1, null);

		KonohaType[] ParamData2 = new KonohaType[2];
		ParamData2[0] = BooleanTy;
		ParamData2[1] = IntTy;
		String[] ArgData2 = new String[1];
		ArgData2[0] = "x";
		KonohaParam Param2 = new KonohaParam(2, ParamData2, ArgData2);

		KonohaMethod intLt = new KonohaMethod(0, BooleanTy, "<", Param2, null);

		KonohaArray Params = new KonohaArray();
		Params.add(new Param(0, IntTy, "n"));
		Builder.Prepare(func1, Params);

		TypedNode Block = new IfNode(VoidTy,
		/* cond */new ApplyNode(BooleanTy, null, intLt, new LocalNode(IntTy, null, "n"), new ConstNode(
				IntTy,
				null,
				3)),
		/* then */new ReturnNode(IntTy, new ConstNode(IntTy, null, 1)),
		/* else */new ReturnNode(IntTy, new ConstNode(IntTy, null, 2))).Next(
		/* */new ReturnNode(IntTy, new ConstNode(IntTy, null, 3)));
		Builder.Compile(Block);
	}

	public static void testFibo(JVMCodeGenerator Builder) {
		String[] ArgData2 = new String[1];
		ArgData2[0] = "x";
		KonohaType[] ParamData1 = new KonohaType[2];
		ParamData1[0] = IntTy;
		ParamData1[1] = IntTy;
		KonohaParam Param1 = new KonohaParam(2, ParamData1, ArgData2);

		KonohaType[] ParamData3 = new KonohaType[2];
		ParamData3[0] = BooleanTy;
		ParamData3[1] = IntTy;
		KonohaParam Param3 = new KonohaParam(2, ParamData3, ArgData2);

		KonohaMethod Fibo = new KonohaMethod(0, VoidTy, "testFibo", Param1, null);

		KonohaMethod intAdd = new KonohaMethod(0, IntTy, "+", Param1, null);
		KonohaMethod intSub = new KonohaMethod(0, IntTy, "-", Param1, null);
		KonohaMethod intLt = new KonohaMethod(0, BooleanTy, "<", Param3, null);

		TypedNode Block2 = new IfNode(VoidTy,
		/* cond */new ApplyNode(BooleanTy, null, intLt, new LocalNode(IntTy, null, "n"), new ConstNode(
				IntTy,
				null,
				3)),
		/* then */new ReturnNode(IntTy, new ConstNode(IntTy, null, 1)),
		/* else */null).Next(new ReturnNode(IntTy, new ApplyNode(IntTy, null, intAdd, new ApplyNode(
				IntTy,
				null,
				Fibo,
				/*new LocalNode(VoidTy, null, "this"),*/
				new ApplyNode(
						IntTy,
						null,
						intSub,
						new LocalNode(IntTy, null, "n"),
						new ConstNode(IntTy, null, 1))), new ApplyNode(IntTy, null, Fibo, /*new LocalNode(
				VoidTy,
				null,
				"this"),*/ new ApplyNode(IntTy, null, intSub, new LocalNode(IntTy, null, "n"), new ConstNode(
				IntTy,
				null,
				2))))));
		KonohaArray Params = new KonohaArray();
		Params.add(new Param(0, IntTy, "n"));
		Builder.Prepare(Fibo, Params);
		Builder.Compile(Block2);
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		JVMCodeGenerator Builder = new JVMCodeGenerator();
		
		testReturnConst(Builder);
		testAddOne(Builder);
		testIf(Builder);
		testFibo(Builder);
		
		Builder.OutputClassFile("Script", "./bin/org/KonohaScript/CodeGen/");
		ClassLoader cl = new TestClassLoader();
		Class<?> c = cl.loadClass("org.KonohaScript.CodeGen.Script");
		
		Object ret1 = c.getMethod("testReturnConst").invoke(null);
		System.out.println(ret1);
		assert((Integer)ret1 == 1);
		Object ret2 = c.getMethod("testAddOne", int.class).invoke(null, 1);
		System.out.println(ret2);
		assert((Integer)ret2 == 2);
		Object ret3 = c.getMethod("testIf", int.class).invoke(null, 1);
		System.out.println(ret3);
		assert((Integer)ret3 == 1);
		Object ret4 = c.getMethod("testFibo", int.class).invoke(null, 10);
		System.out.println(ret4);
		assert((Integer)ret3 == 55);
	}

}