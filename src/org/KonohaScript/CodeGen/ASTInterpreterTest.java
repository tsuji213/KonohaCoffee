package org.KonohaScript.CodeGen;

import org.KonohaScript.Konoha;
import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaMethodInvoker;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaParam;
import org.KonohaScript.KonohaType;
import org.KonohaScript.Grammar.MiniKonohaGrammar;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.TypedNode;
import org.KonohaScript.Tester.KTestCase;

public class ASTInterpreterTest extends KTestCase {
	@Override
	public void Init() {
	}

	@Override
	public void Exit() {
	}

	public static final Konoha		KonohaContext	= new Konoha(new MiniKonohaGrammar(), null);
	public final KonohaNameSpace	NameSpace		= KonohaContext.DefaultNameSpace;
	public final KonohaType			VoidTy			= KonohaContext.VoidType;
	public final KonohaType			ObjectTy		= KonohaContext.ObjectType;
	public final KonohaType			BooleanTy		= KonohaContext.BooleanType;
	public final KonohaType			IntTy			= KonohaContext.IntType;
	public final KonohaType			StringTy		= KonohaContext.StringType;

	@Override
	public void Test() {
		CodeGenerator Builder = new ASTInterpreter();
		String[] ArgData1 = new String[1];
		ArgData1[0] = "x";
		KonohaType[] ParamData1 = new KonohaType[2];
		ParamData1[0] = this.IntTy;
		ParamData1[1] = this.IntTy;
		KonohaParam Param1 = new KonohaParam(2, ParamData1, ArgData1);
		KonohaMethod Add = new KonohaMethod(0, this.IntTy, "p", Param1, null);

		// int int.f(int x) { return x; }
		TypedNode Block1 = new ReturnNode(this.IntTy, new LocalNode(this.IntTy, null, "x"));
		Builder.Prepare(Add);
		KonohaMethodInvoker Mtd1 = Builder.Compile(Block1);
		Object[] Args = { new Integer(0), new Integer(1) };
		Object Ret = Mtd1.Invoke(Args);
		this.Assert(Ret instanceof Integer);
		this.AssertEqual(((Integer) Ret).intValue(), 1);
	}

	public static void main(String[] args) {
		ASTInterpreterTest tester = new ASTInterpreterTest();
		tester.Init();
		tester.Test();
		tester.Exit();
	}
}
