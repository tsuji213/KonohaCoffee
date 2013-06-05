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

import java.lang.reflect.Method;

import org.KonohaScript.KLib.KonohaArray;

public class KonohaType {
	Konoha				KonohaContext;
	KPackage			Package;
	int					ClassFlag;
	public String		ShortClassName;
	KonohaType			BaseClass;
	KonohaType			SuperClass;
	KonohaParam			ClassParam;
	KonohaType			SearchSimilarClass;
	KonohaArray			ClassMethodList;
	public KonohaType	SearchSuperMethodClass;
	// FIXME(ide) where is superclass info?
	public Object		DefaultNullValue;
	Object				LocalSpec;

	// Java Implementation Only
	Class<?>			HostedClassInfo	= null;

	public KonohaType(Konoha KonohaContext, KPackage Package, int ClassFlag, String ClassName, Object Spec) {
		this.KonohaContext = KonohaContext;
		this.ClassFlag = ClassFlag;
		this.Package = Package;
		this.ShortClassName = ClassName;
		this.SuperClass = null;
		this.BaseClass = this;
		this.ClassMethodList = KonohaContext.EmptyList;
		this.LocalSpec = Spec;
	}

	public KonohaType(Konoha KonohaContext, Class<?> ClassInfo) {
		this(KonohaContext, null, 0, ClassInfo.getSimpleName(), null);
		this.HostedClassInfo = ClassInfo;
		// this.ClassFlag = ClassFlag;
		if(ClassInfo != Object.class) {
			this.SuperClass = KonohaContext.LookupTypeInfo(ClassInfo.getSuperclass());
		}
	}

	// public KClass(Konoha KonohaContext, String ClassName) throws
	// ClassNotFoundException {
	// this(KonohaContext, null, 0, ClassName, null);
	// Class<?> ClassInfo = Class.forName(ClassName);
	// this.HostedClassInfo = ClassInfo;
	// this.ShortClassName = ClassInfo.getSimpleName();
	// // this.ClassFlag = ClassFlag;
	// if(ClassInfo != Object.class) {
	// this.SuperClass =
	// KonohaContext.LookupTypeInfo(ClassInfo.getSuperclass().getName());
	// }
	// }

	static KonohaMethod ConvertMethod(Konoha KonohaContext, Method Method) {
		KonohaType ThisType = KonohaContext.LookupTypeInfo(Method.getClass());
		Class<?>[] ParamTypes = Method.getParameterTypes();
		KonohaType[] ParamData = new KonohaType[ParamTypes.length + 1];
		String[] ArgNames = new String[ParamTypes.length + 1];
		ParamData[0] = KonohaContext.LookupTypeInfo(Method.getReturnType());
		for(int i = 0; i < ParamTypes.length; i++) {
			ParamData[i + 1] = KonohaContext.LookupTypeInfo(ParamTypes[i]);
			ArgNames[i] = "arg" + i;
		}
		KonohaParam Param = new KonohaParam(ParamData.length, ParamData, ArgNames);
		KonohaMethod Mtd = new KonohaMethod(0, ThisType, Method.getName(), Param, Method);
		ThisType.AddMethod(Mtd);
		return Mtd;
	}

	int CreateMethods(String MethodName) {
		int Count = 0;
		Method[] Methods = this.HostedClassInfo.getMethods();
		for(int i = 0; i < Methods.length; i++) {
			if(MethodName.equals(Methods[i].getName())) {
				KonohaType.ConvertMethod(this.KonohaContext, Methods[i]);
				Count = Count + 1;
			}
		}
		return Count;
	}

	public boolean Accept(KonohaType TypeInfo) {
		if(this == TypeInfo) {
			return true;
		}
		return false;
	}

	public void AddMethod(KonohaMethod Method) {
		if(this.ClassMethodList == this.KonohaContext.EmptyList) {
			this.ClassMethodList = new KonohaArray();
		}
		this.ClassMethodList.add(Method);
	}

	public void DefineMethod(int MethodFlag, String MethodName, KonohaParam Param, Object Callee, String LocalName) {
		KonohaMethod Method = new KonohaMethod(MethodFlag, this, MethodName, Param, KonohaFunc.LookupMethod(Callee, LocalName));
		this.AddMethod(Method);
	}

	public KonohaMethod LookupMethod(String MethodName, int ParamSize) {
		for(int i = 0; i < this.ClassMethodList.size(); i++) {
			KonohaMethod Method = (KonohaMethod) this.ClassMethodList.get(i);
			if(Method.Match(MethodName, ParamSize)) {
				return Method;
			}
		}
		if(this.SearchSuperMethodClass != null) {
			KonohaMethod Method = this.SearchSuperMethodClass.LookupMethod(MethodName, ParamSize);
			if(Method != null) {
				return Method;
			}
		}
		if(this.HostedClassInfo != null) {
			if(this.CreateMethods(MethodName) > 0) {
				return this.LookupMethod(MethodName, ParamSize);
			}
		}
		return null;
	}

	public boolean DefineNewMethod(KonohaMethod NewMethod) {
		for(int i = 0; i < this.ClassMethodList.size(); i++) {
			KonohaMethod DefinedMethod = (KonohaMethod) this.ClassMethodList.get(i);
			if(NewMethod.Match(DefinedMethod)) {
				return false;
			}
		}
		this.AddMethod(NewMethod);
		return true;
	}

}
