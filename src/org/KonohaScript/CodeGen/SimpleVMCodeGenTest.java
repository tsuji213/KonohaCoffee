package org.KonohaScript.CodeGen;

import org.KonohaScript.KClass;
import org.KonohaScript.KPackage;
import org.KonohaScript.Konoha;
import org.KonohaScript.AST.BlockNode;
import org.KonohaScript.AST.ConstNode;
import org.KonohaScript.AST.IfNode;
import org.KonohaScript.AST.NewNode;
import org.KonohaScript.AST.ReturnNode;
import org.KonohaScript.AST.TypedNode;
import org.KonohaScript.GrammarSet.MiniKonoha;
import org.junit.Assert;
import org.junit.Test;

public class SimpleVMCodeGenTest {

	@Test
	public void testCompile() {
		MiniKonoha defaultSyntax = new MiniKonoha();
		Konoha kctx = new Konoha(defaultSyntax);
		KPackage pkg = new KPackage(kctx, 0, "SimpleVMTest");

		KClass VoidTy = new KClass(kctx, pkg, 0, "void");
		KClass ObjectTy = new KClass(kctx, pkg, 1, "Object");
		KClass BooleanTy = new KClass(kctx, pkg, 3, "booelan");
		KClass IntTy = new KClass(kctx, pkg, 4, "int");
		KClass StringTy = new KClass(kctx, pkg, 5, "String");
		{
			SimpleVMCodeGen G = new SimpleVMCodeGen();
			TypedNode Block = new BlockNode(VoidTy, new ReturnNode(IntTy,
					new ConstNode(IntTy, 1)));
			CompiledMethod Mtd = G.Compile(Block);
			Assert.assertTrue(Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			Assert.assertEquals("{return 1;}", Program);
		}

		{
			SimpleVMCodeGen G = new SimpleVMCodeGen();
			TypedNode Block = new IfNode(VoidTy,
					new ConstNode(BooleanTy, true), new BlockNode(VoidTy,
							new NewNode(ObjectTy)), new BlockNode(VoidTy,
							new ReturnNode(IntTy, new ConstNode(BooleanTy,
									false))));
			CompiledMethod Mtd = G.Compile(Block);
			Assert.assertTrue(Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			Assert.assertEquals(
					"if (true) { new Object(); } else { return false; }",
					Program);
		}
	}
}
