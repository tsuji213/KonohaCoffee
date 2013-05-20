package org.KonohaScript.CodeGen;

import org.KonohaScript.KClass;
import org.KonohaScript.Konoha;
import org.KonohaScript.GrammarSet.MiniKonoha;
import org.KonohaScript.SyntaxTree.BlockNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.NewNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public class SimpleVMCodeGenTest {

	public void testCompile() {
		MiniKonoha defaultSyntax = new MiniKonoha();
		Konoha kctx = new Konoha(defaultSyntax);

		KClass VoidTy = kctx.VoidType;
		KClass ObjectTy = kctx.ObjectType;
		KClass BooleanTy = kctx.BooleanType;
		KClass IntTy = kctx.IntType;
		// KClass StringTy = kctx.StringType;
		{
			SimpleVMCodeGen G = new SimpleVMCodeGen();
			TypedNode Block = new BlockNode(VoidTy,
					new ReturnNode(IntTy, new ConstNode(IntTy, null,
							new Integer(1))));
			CompiledMethod Mtd = G.Compile(Block);
			assert (Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			assert ("{return 1;}" == Program);
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
			assert ("if (true) { new Object(); } else { return false; }" == Program);
		}
	}

	// void f() {
	// MiniKonoha defaultSyntax = new MiniKonoha();
	// Konoha kctx = new Konoha(defaultSyntax);
	//
	// KClass voidTy = KClass.VoidType;
	// KClass booleanTy = KClass.BooleanType;
	// KClass intTy = KClass.IntType;
	// KClass StringTy = KClass.StringType;
	// KClass NameSpaceTy = StringTy;
	// KClass SystemTy = StringTy;
	// new DoneNode(voidTy);
	// new BlockNode(voidTy, new MethodCallNode(voidTy, "p", new NullNode(
	// SystemTy), new MethodCallNode(StringTy, "toString",
	// new MethodCallNode(intTy, "fibo", new ConstNode(NameSpaceTy,
	// null), new ConstNode(intTy, 36)))));
	// new BlockNode(voidTy, new IfNode(voidTy, new MethodCallNode(booleanTy,
	// "<", new LocalNode(intTy, 1, "n"), new ConstNode(intTy, 3)),
	// new BlockNode(voidTy, new ReturnNode(voidTy, new ConstNode(
	// intTy, 1))), new DoneNode(voidTy)), new ReturnNode(
	// voidTy, new MethodCallNode(intTy, "+", new MethodCallNode(
	// intTy, "fibo", new LocalNode(NameSpaceTy, 0, ""),
	// new MethodCallNode(intTy, "-", new LocalNode(intTy, 1,
	// "n"), new ConstNode(intTy, 1))),
	// new MethodCallNode(intTy, "fibo", new LocalNode(
	// NameSpaceTy, 0, ""), new MethodCallNode(intTy,
	// "-", new LocalNode(intTy, 1, "n"),
	// new ConstNode(intTy, 2))))));
	//
	// }
	public static void main(String[] args) {
		SimpleVMCodeGenTest test = new SimpleVMCodeGenTest();
		test.testCompile();
	}
}