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
import org.KonohaScript.SyntaxTree.NullNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public class LeafJSCodeGenTest {

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

		KClass[] ParamData = new KClass[1];
		ParamData[0] = VoidTy;
		KParam Param = new KParam(1, ParamData);

		KMethod GlobalFunction = new KMethod(0, VoidTy, "", Param, null);

		{
			SourceCodeGen G = new LeafJSCodeGen();
			G.Prepare(GlobalFunction);
			TypedNode Block = new BlockNode(VoidTy,
					new ReturnNode(IntTy,
							new ConstNode(IntTy, null, 1)));
			CompiledMethod Mtd = G.Compile(Block);
			assert (Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			Check("{return 1;}", Program);
		}

		{
			SourceCodeGen G = new LeafJSCodeGen();
			G.Prepare(GlobalFunction);

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

		KClass VoidTy = KonohaContext.VoidType;
		KClass booleanTy = KonohaContext.BooleanType;
		KClass IntTy = KonohaContext.IntType;
		KClass StringTy = KonohaContext.StringType;

		SourceCodeGen G = new LeafJSCodeGen();

		KClass[] ParamData1 = new KClass[2];
		ParamData1[0] = IntTy;
		ParamData1[1] = IntTy;
		KParam Param1 = new KParam(2, ParamData1);

		KClass[] ParamData2 = new KClass[2];
		ParamData2[0] = VoidTy;
		ParamData2[1] = StringTy;
		KParam Param2 = new KParam(2, ParamData2);

		KClass[] ParamData3 = new KClass[2];
		ParamData3[0] = booleanTy;
		ParamData3[1] = IntTy;
		KParam Param3 = new KParam(2, ParamData3);

		KClass[] ParamData4 = new KClass[1];
		ParamData4[0] = StringTy;
		KParam Param4 = new KParam(1, ParamData4);

		KMethod Fibo = new KMethod(0, VoidTy, "fibo", Param1, null);
		KMethod p = new KMethod(0, VoidTy, "p", Param2, null);

		KMethod intAdd = new KMethod(0, IntTy, "+", Param1, null);
		KMethod intSub = new KMethod(0, IntTy, "-", Param1, null);
		KMethod intLt = new KMethod(0, booleanTy, "<", Param3, null);

		KMethod toString = new KMethod(0, IntTy, "toString", Param4, null);

		KClass[] ParamData = new KClass[1];
		ParamData[0] = VoidTy;
		KParam Param = new KParam(1, ParamData);

		KMethod GlobalFunction = new KMethod(0, VoidTy, "", Param, null);

		TypedNode Block1 = new BlockNode(VoidTy, new MethodCallNode(
				VoidTy,
				null,
				p,
				new NullNode(VoidTy/* FIXME */),
				new MethodCallNode(StringTy, null, toString,
						new NullNode(IntTy),
						new MethodCallNode(
								IntTy,
								null,
								Fibo,
								new NullNode(VoidTy/* FIXME */),
								new ConstNode(IntTy, null, 36)))));
		{
			G.Prepare(GlobalFunction);
			CompiledMethod Mtd = G.Compile(Block1);
			assert (Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			Check("void.p(int.toString(void.fibo(36)));", Program);
		}

		TypedNode Block2 = new BlockNode(
				VoidTy,
				new IfNode(VoidTy,
						/* cond */new MethodCallNode(booleanTy,
								null,
								intLt,
								new LocalNode(IntTy, null, "n"),
								new ConstNode(IntTy, null, 3)),
						/* then */new BlockNode(VoidTy,
								new ReturnNode(VoidTy,
										new ConstNode(IntTy, null, 1))),
						/* else */new BlockNode(VoidTy)),
				new ReturnNode(
						IntTy,
						new MethodCallNode(
								IntTy,
								null,
								intAdd,
								new MethodCallNode(
										IntTy,
										null,
										Fibo,
										new LocalNode(VoidTy, null, "this"),
										new MethodCallNode(
												IntTy,
												null,
												intSub,
												new LocalNode(
														IntTy,
														null,
														"n"),
												new ConstNode(IntTy, null, 1))),
								new MethodCallNode(
										IntTy,
										null,
										Fibo,
										new LocalNode(VoidTy, null, "this"),
										new MethodCallNode(
												IntTy,
												null,
												intSub,
												new LocalNode(IntTy, null, "n"),
												new ConstNode(IntTy, null, 2))))));
		{
			ArrayList<Local> Params = new ArrayList<Local>();
			Params.add(new Param(0, IntTy, "n"));
			G.Prepare(Fibo, Params);
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
		LeafJSCodeGenTest test = new LeafJSCodeGenTest();
		test.testCompile();
		test.Fibo();
	}
}