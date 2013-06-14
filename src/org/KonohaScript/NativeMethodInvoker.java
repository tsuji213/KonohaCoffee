package org.KonohaScript;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class NativeMethodInvoker extends KonohaMethodInvoker {

	public NativeMethodInvoker(KonohaParam Param, Method MethodRef) {
		super(Param, MethodRef);
	}

	public Method GetMethodRef() {
		return (Method) this.CompiledCode;
	}

	boolean IsStaticInvocation() {
		return Modifier.isStatic(this.GetMethodRef().getModifiers());
	}

	@Override
	public Object Invoke(Object[] Args) {
		int ParamSize = this.Param.GetParamSize();
		try {
			Method MethodRef = this.GetMethodRef();
			if(this.IsStaticInvocation()) {
				switch (ParamSize) {
				case 0:
					return MethodRef.invoke(null, Args[0]);
				case 1:
					return MethodRef.invoke(null, Args[0], Args[1]);
				default:
					return MethodRef.invoke(null, Args); // FIXME
				}
			} else {
				switch (ParamSize) {
				case 0:
					return MethodRef.invoke(Args[0]);
				case 1:
					return MethodRef.invoke(Args[0], Args[1]);
				default:
					return MethodRef.invoke(Args[0], Args); // FIXME
				}
			}
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}