package org.KonohaScript.CodeGen;

import junit.framework.Assert;

import org.KonohaScript.KClass;
import org.KonohaScript.Konoha;
import org.KonohaScript.GrammarSet.MiniKonoha;
import org.KonohaScript.SyntaxTree.BlockNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.NewNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.TypedNode;
import org.junit.Test;

public class LeafJSCodeGenTest {

	@Test
	public void testCompile() {
		MiniKonoha defaultSyntax = new MiniKonoha();
		Konoha kctx = new Konoha(defaultSyntax);

		KClass VoidTy = KClass.VoidType = new KClass(kctx, Void.class);
		KClass ObjectTy = KClass.ObjectType = new KClass(kctx, Object.class);
		KClass BooleanTy = KClass.BooleanType = new KClass(kctx, Boolean.class);
		KClass IntTy = KClass.IntType = new KClass(kctx, Integer.class);
		KClass StringTy = KClass.StringType = new KClass(kctx, String.class);
		{
			LeafJSCodeGen G = new LeafJSCodeGen();
			TypedNode Block = new BlockNode(VoidTy, new ReturnNode(IntTy,
					new ConstNode(IntTy, 1)));
			CompiledMethod Mtd = G.Compile(Block);
			Assert.assertTrue(Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			Assert.assertEquals("{return 1;};", Program);
		}

		{
			LeafJSCodeGen G = new LeafJSCodeGen();
			TypedNode Block = new IfNode(VoidTy,
					new ConstNode(BooleanTy, true), new BlockNode(VoidTy,
							new NewNode(ObjectTy)), new BlockNode(VoidTy,
							new ReturnNode(IntTy, new ConstNode(BooleanTy,
									false))));
			CompiledMethod Mtd = G.Compile(Block);
			Assert.assertTrue(Mtd.CompiledCode instanceof String);
			String Program = (String) Mtd.CompiledCode;
			Assert.assertEquals(
					"if (true) {new Object();}; else {return false;};",
					Program);
		}
	}
}
