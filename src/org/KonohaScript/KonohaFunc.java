/****************************************************************************
 * Copyright (c) 2013, the Konoha project authors. All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ***************************************************************************/

package org.KonohaScript;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public final class KonohaFunc {
	Object callee;
	Method method;
	KonohaFunc  prev;

	static Method LookupMethod(Object Callee, String MethodName) {
		if(MethodName != null) {
//			KonohaDebug.P("looking up method : " + Callee.getClass().getSimpleName() + "." + MethodName);
			Method[] methods = Callee.getClass().getMethods();
			for(int i = 0; i < methods.length; i++) {
				if(MethodName.equals(methods[i].getName())) {
					return methods[i];
				}
			}
			KonohaDebug.P("method not found: " + Callee.getClass().getSimpleName() + "." + MethodName);
		}
		return null; /*throw new KonohaParserException("method not found: " + callee.getClass().getName() + "." + methodName);*/
	}

	KonohaFunc(Object callee, Method method, KonohaFunc prev) {
		this.callee = callee;
		this.method = method;
		this.prev = prev;
	}

	KonohaFunc(Object callee, String methodName, KonohaFunc prev) {
		this(callee, LookupMethod(callee, methodName), prev);
	}

	static boolean EqualsMethod(Method m1, Method m2) {
		if(m1 == null) {
			return (m2 == null) ? true : false;
		}
		else {
			return (m2 == null) ? false: m1.equals(m2);
		}
	}

	static KonohaFunc NewFunc(Object callee, String methodName, KonohaFunc prev) {
		Method method = LookupMethod(callee, methodName);
		if(prev != null && EqualsMethod(prev.method, method)) {
			return prev;
		}
		return new KonohaFunc(callee, method, prev);
	}

	KonohaFunc Pop() {
		return this.prev;
	}

	KonohaFunc Duplicate() {
		if(prev == null) {
			return new KonohaFunc(callee, method, null);
		}
		else {
			return new KonohaFunc(callee, method, prev.Duplicate());
		}
	}

	KonohaFunc Merge(KonohaFunc other) {
		return other.Duplicate().prev = this.Duplicate();
	}

	int InvokeTokenFunc(KonohaNameSpace ns, String source, int pos, ArrayList<KonohaToken> bufferToken) {
		try {
			//KonohaDebug.P("invoking: " + method + ", pos: " + pos + " < " + source.length());
			Integer next = (Integer)method.invoke(callee, ns, source, pos, bufferToken);
			return next.intValue();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	int InvokeMacroFunc(LexicalConverter lex,  ArrayList<KonohaToken> tokenList, int BeginIdx, int EndIdx, ArrayList<KonohaToken> bufferToken) {
		try {
			Integer next = (Integer)method.invoke(callee, lex, tokenList, BeginIdx, EndIdx, bufferToken);
			return next.intValue();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EndIdx;
	}

	@Override public String toString() {
		return method.toString();
	}


}

