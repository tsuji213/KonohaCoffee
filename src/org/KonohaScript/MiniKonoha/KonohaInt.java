package org.KonohaScript.MiniKonoha;

import org.KonohaScript.KonohaConst;
import org.KonohaScript.KonohaDebug;
import org.KonohaScript.KonohaGrammar;
import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaParam;
import org.KonohaScript.KonohaType;

public class KonohaInt extends KonohaGrammar implements KonohaConst {

	public KonohaInt() {
	}

	void DefineMethod(KonohaNameSpace ns) {
		KonohaType BaseClass = ns.LookupTypeInfo(Integer.class);
		KonohaParam BinaryParam = KonohaParam.ParseOf(ns, "int int x");
		KonohaParam UniaryParam = KonohaParam.ParseOf(ns, "int");

		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "+", UniaryParam, this, "PlusInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "+", BinaryParam, this, "IntAddInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "-", UniaryParam, this, "MinusInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "-", BinaryParam, this, "IntSubInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "*", BinaryParam, this, "IntMulInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "/", BinaryParam, this, "IntDivInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "%", BinaryParam, this, "IntModInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "<", BinaryParam, this, "IntLtInt");

		if(KonohaDebug.UseBuiltInTest) {
			assert (BaseClass.LookupMethod("+", 0) != null);
			assert (BaseClass.LookupMethod("+", 1) != null);
			assert (BaseClass.LookupMethod("+", 2) == null);
			KonohaMethod m = BaseClass.LookupMethod("+", 1);
			Object[] p = new Object[2];
			p[0] = new Integer(1);
			p[1] = new Integer(2);
			System.out.println("******* 1+2=" + m.Eval(p));
		}
	}

	public static int PlusInt(int x) {
		return +x;
	}

	public static int IntAddInt(int x, int y) {
		return x + y;
	}

	public static int MinusInt(int x) {
		return -x;
	}

	public static int IntSubInt(int x, int y) {
		return x - y;
	}

	public static int IntMulInt(int x, int y) {
		return x * y;
	}

	public static int IntDivInt(int x, int y) {
		return x / y;
	}

	public static int IntModInt(int x, int y) {
		return x % y;
	}
	public static int IntLtInt(int x, int y) {
		return x < y;
	}
}
