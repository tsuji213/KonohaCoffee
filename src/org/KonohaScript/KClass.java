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

import java.util.ArrayList;

public class KClass {
	int cflag;
	KPackage Package;
	public int classId;
	KClass BaseClass;
	KClass SuperClass;
	int p0;
	int classSignature;
	public String ShortClassName;
	int classNameSymbol;
	int optvalue;
	ArrayList<KMethod> ClassMethodList;
	Object DefaultNullValue;
	KClass SearchSimilarClassNULL;
	KClass SearchSuperMethodClassNULL;

	// Class javaClassIfAvailableClass;
	public ArrayList<String> FieldNames;
	public ArrayList<KClass> FieldTypes;

	public static final ArrayList<KMethod> EmptyMethodList = new ArrayList<KMethod>();

	public KClass(Konoha konoha, KPackage p, int classId, String cname) {
		this.Package = p;
		this.classId = classId;
		this.BaseClass  = this;
		this.SuperClass = null;
		this.ShortClassName = cname;
//		this.classNameSymbol = konoha.GetSymbol(cname, true);
		this.FieldNames = null;
		this.FieldTypes = null;
		this.ClassMethodList = EmptyMethodList;
	}
	
	Class<?> HostedClassInfo = null;

	public KClass(Konoha konoha, String ClassName) throws ClassNotFoundException {
		Class<?> ClassInfo = Class.forName(ClassName);
		this.HostedClassInfo = ClassInfo;
		this.ShortClassName = ClassInfo.getSimpleName();
		this.BaseClass  = this;
		if(ClassInfo != Object.class) {
			this.SuperClass = konoha.LookupTypeInfo(ClassInfo.getSuperclass().getName());
		}
	}
//
//	private KClass() {
//		this(Object.class, "Object", null);
//	}
//	
//	KClass(Class<?> ClassInfo, String cname, KClass SuperClass) {
//		this.HostedClassInfo = ClassInfo;
//		this.ShortClassName = (cname == null) ? ClassInfo.getSimpleName() : cname;		
//		this.BaseClass  = this;
//		this.SuperClass = SuperClass;
//		this.ClassMethodList = EmptyMethodList;
//	}

	public static KClass ObjectType  = null;	
	public static KClass VoidType    = null;
	public static KClass BooleanType = null;
	public static KClass IntType     = null;
	public static KClass StringType  = null;
	
	public boolean Accept(KClass TypeInfo) {
		if(this == TypeInfo) return true;
		return false;
	}
	
	public void AddMethod(KMethod Method) {
		if(ClassMethodList == EmptyMethodList) {
			ClassMethodList = new ArrayList<KMethod>();
		}
	}
	
	public KMethod LookupMethod(String symbol, int ParamSize) {
		return null;
	}	

//	public KMethod LookupMethod(String symbol, int ParamSize) {
//		return null;
//	}	

}
