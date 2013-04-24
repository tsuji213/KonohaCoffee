/****************************************************************************
 * Copyright (c) 2012, the Konoha project authors. All rights reserved.
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

public final class KFunc {
	Object callee;
	Method method;
	KFunc  prev;
	
	static Method LookupMethod(Object callee, String name) {
		Method[] methods = callee.getClass().getMethods();
		for(int i = 0; i < methods.length; i++) {
			if(name.equals(methods[i].getName())) {
				return methods[i];
			}
		}
		return null;
	}

	KFunc(Object callee, Method method, KFunc prev) {
		this.callee = callee;
		this.method = method;
		this.prev = prev;
	}

	KFunc(Object callee, String name, KFunc prev) {
		this(callee, LookupMethod(callee, name), prev);
	}

	KFunc Pop() {
		return this.prev;
	}

	KFunc Duplicate() {
		if(prev == null) {
			return new KFunc(callee, method, null);
		}
		else {
			return new KFunc(callee, method, prev.Duplicate());
		}
	}

	KFunc Merge(KFunc other) {
		return other.Duplicate().prev = this.Duplicate();
	}

	int InvokeTokenFunc(KNameSpace ns, String source, int pos, ArrayList<KToken> bufferToken) {
		try {
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
		}
		return 0;
	}

	int InvokeMacroFunc(KNameSpace ns,  ArrayList<KToken> tokenToken, int beginIdx, int endIdx, ArrayList<KToken> bufferToken) {
//		try {
//			Integer next = (Integer)method.invoke(callee, ns, source, pos, bufferToken);
//			return next.intValue();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return 0;
	}


}

