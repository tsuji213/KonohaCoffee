package org.KonohaScript;

public class KPackage {
	String PackageName;
	KonohaNameSpace PackageNameSpace;
	int kickoutFileId;

	public KPackage(Konoha kctx, int packageId, String name) {
		this.PackageName = name;
		this.PackageNameSpace = new KonohaNameSpace(kctx, kctx.DefaultNameSpace);
	}
}