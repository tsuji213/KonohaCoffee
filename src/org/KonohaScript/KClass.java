package org.KonohaScript;

import java.util.ArrayList;

public class KClass {
	int cflag;
	int packageId;
	int packageDomain;
	public int classId;
	KClass BaseClass;
	KClass SuperClass;
	int p0;
	int classSignature;
	// size_t cstruct_size;
	// KClassField *fieldItems;
	// kuhalfword_t fieldsize; kuhalfword_t fieldAllocSize;
	// const char *DBG_NAME;
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

	public boolean IsUnboxedNode() {
		return this.classId == 3/* KType_Int */||
				this.classId == 4/* KType_Boolean */;
	}

	public KClass(Konoha kctx, KPackage p, int classId, String cname) {
		this.packageId = p.packageId;
		this.classId = classId;
		this.BaseClass = this;
		this.SuperClass = this;
		this.ShortClassName = cname;
		this.classNameSymbol = kctx.GetSymbol(cname, true);
		this.FieldNames = null;
		this.FieldTypes = null;
		this.ClassMethodList = EmptyMethodList;

	}
}
