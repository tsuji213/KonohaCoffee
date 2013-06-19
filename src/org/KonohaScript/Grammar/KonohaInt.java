package org.KonohaScript.Grammar;

import org.KonohaScript.KonohaDef;
import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaParam;
import org.KonohaScript.KonohaType;
import org.KonohaScript.JUtils.KonohaConst;
import org.KonohaScript.JUtils.KonohaDebug;

public class KonohaInt extends KonohaDef implements KonohaConst {

	public KonohaInt() {
	}

	@Override
	public void MakeDefinition(KonohaNameSpace ns) {
		KonohaType BaseClass = ns.LookupHostLangType(Integer.class);
		KonohaParam BinaryParam = KonohaParam.ParseOf(ns, "int int x");
		KonohaParam UniaryParam = KonohaParam.ParseOf(ns, "int");

		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "+", UniaryParam, this, "PlusInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "+", BinaryParam, this, "IntAddInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "-", UniaryParam, this, "MinusInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "-", BinaryParam, this, "IntSubInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "*", BinaryParam, this, "IntMulInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "/", BinaryParam, this, "IntDivInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "%", BinaryParam, this, "IntModInt");

		KonohaParam RelationParam = KonohaParam.ParseOf(ns, "boolean int x");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "<", RelationParam, this, "IntLtInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "<=", RelationParam, this, "IntLeInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, ">", RelationParam, this, "IntGtInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, ">=", RelationParam, this, "IntGeInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "==", RelationParam, this, "IntEqInt");
		BaseClass.DefineMethod(ImmutableMethod | ConstMethod, "!=", RelationParam, this, "IntNeInt");

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

	public static boolean IntLtInt(int x, int y) {
		return x < y;
	}

	public static boolean IntLeInt(int x, int y) {
		return x <= y;
	}

	public static boolean IntGtInt(int x, int y) {
		return x > y;
	}

	public static boolean IntGeInt(int x, int y) {
		return x >= y;
	}
}
