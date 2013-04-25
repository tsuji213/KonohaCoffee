package org.KonohaScript;

public class KPackage {
	int packageId;
	String PackageName;
	KNameSpace PackageNameSpace;
	int kickoutFileId;

	public KPackage(Konoha kctx, int packageId, String name) {
		this.packageId = packageId;
		this.PackageName = name;
		this.PackageNameSpace = new KNameSpace(kctx, kctx.DefaultNameSpace);
	}
}