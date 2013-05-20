package org.KonohaScript.CodeGen;

import java.util.ArrayList;

import org.KonohaScript.KClass;
import org.KonohaScript.KMethod;
import org.KonohaScript.KParam;
import org.KonohaScript.Konoha;
import org.KonohaScript.GrammarSet.MiniKonoha;
import org.KonohaScript.SyntaxTree.BlockNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.MethodCallNode;
import org.KonohaScript.SyntaxTree.NewNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public class SimpleVMCodeGenTest {

	public static final Konoha KonohaContext = new Konoha(new MiniKonoha());

	void Check(Object CollectData, Object Data) {
		System.out.println(Data);
		assert (CollectData == Data);
	}

	public void testCompile() {

		KClass VoidTy = KonohaContext.VoidType;
		KClass ObjectTy = KonohaContext.ObjectType;
		KClass BooleanTy = KonohaContext.BooleanType;
		KClass IntTy = KonohaContext.IntType;
		{
			SimpleVMCodeGen G = new SimpleVMCodeGen();
			TypedNode Block = new BlockNode(VoidTy,
					new ReturnNode(IntTy,
							new ConstNode(IntTy, null, 1)));
			CompiledMethod Mtd = G.Compile(Block);
			assert (Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			Check("{return 1;}", Program);
		}

		{
			SimpleVMCodeGen G = new SimpleVMCodeGen();
			TypedNode Block = new IfNode(VoidTy,
					new ConstNode(BooleanTy, null, true),
					new BlockNode(VoidTy, new NewNode(ObjectTy)),
					new BlockNode(VoidTy,
							new ReturnNode(IntTy,
									new ConstNode(BooleanTy, null, false))));
			CompiledMethod Mtd = G.Compile(Block);
			assert (Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			Check("if (true) { new Object(); } else { return false; }", Program);
		}
	}

	void Fibo() {

		KClass voidTy = KonohaContext.VoidType;
		KClass booleanTy = KonohaContext.BooleanType;
		KClass intTy = KonohaContext.IntType;
		KClass StringTy = KonohaContext.StringType;

		SimpleVMCodeGen G = new SimpleVMCodeGen();
		// TypedNode Block1 = new BlockNode(voidTy, new MethodCallNode(
		// voidTy,
		// "p",
		// new NullNode(voidTy/* FIXME */),
		// new MethodCallNode(StringTy, "toString",
		// new NullNode(intTy),
		// new MethodCallNode(
		// intTy,
		// "fibo",
		// new NullNode(voidTy/* FIXME */),
		// new ConstNode(intTy, null, 36)))));
		// {
		// CompiledMethod Mtd = G.Compile(Block1);
		// assert (Mtd.CompiledCode instanceof String);
		// String Program = (String) Mtd.CompiledCode;
		// Check("void.p(int.toString(void.fibo(36)));", Program);
		// }

		KClass[] ParamData1 = new KClass[1];
		ParamData1[0] = intTy;
		KParam Param1 = new KParam(1, ParamData1);

		KMethod Fibo = new KMethod(0, voidTy, "fibo", Param1, null);
		KMethod intAdd = new KMethod(0, intTy, "+", Param1, null);
		KMethod intSub = new KMethod(0, intTy, "-", Param1, null);
		KMethod intLt = new KMethod(0, booleanTy, "<", Param1, null);

		TypedNode Block2 = new BlockNode(
				voidTy,
				new IfNode(voidTy,
						/* cond */new MethodCallNode(booleanTy,
								null,
								intLt,
								new LocalNode(intTy, null, "n"),
								new ConstNode(intTy, null, 3)),
						/* then */new BlockNode(voidTy,
								new ReturnNode(voidTy,
										new ConstNode(intTy, null, 1))),
						/* else */new BlockNode(voidTy)),
				new ReturnNode(
						intTy,
						new MethodCallNode(
								intTy,
								null,
								intAdd,
								new MethodCallNode(
										intTy,
										null,
										Fibo,
										new LocalNode(voidTy, null, "this"),
										new MethodCallNode(
												intTy,
												null,
												intSub,
												new LocalNode(
														intTy,
														null,
														"n"),
												new ConstNode(intTy, null, 1))),
								new MethodCallNode(
										intTy,
										null,
										Fibo,
										new LocalNode(voidTy, null, "this"),
										new MethodCallNode(
												intTy,
												null,
												intSub,
												new LocalNode(intTy, null, "n"),
												new ConstNode(intTy, null, 2))))));
		{
			ArrayList<Local> Params = new ArrayList<Local>();
			Params.add(new Param(0, intTy, "n"));
			G.Prepare(intTy, Fibo, Params);
			CompiledMethod Mtd = G.Compile(Block2);
			assert (Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			Check("int void.fibo(int n) {" +
					"if(n < 3) {" +
					"return 1;" +
					"} else {" +
					"}" +
					"return void.fibo(n-1) + void.fibo(n-2);\n" +
					"}", Program);
		}
	}

	public static void main(String[] args) {
		SimpleVMCodeGenTest test = new SimpleVMCodeGenTest();
		// test.testCompile();
		test.Fibo();
	}
}