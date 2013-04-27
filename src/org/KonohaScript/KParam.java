package org.KonohaScript;


public class KParam {
	public final static int MAX = 16;
	public final static int VariableParamSize = -1;
	KClass ReturnTypeInfo;
	int ParamSize;
	KClass[] ParamTypes;
	public KParam(KClass ReturnTypeInfo, int ParamSize, KClass[] Types, String[] Names) {
		this.ReturnTypeInfo = ReturnTypeInfo;
		this.ParamSize = ParamSize;
		if(ParamSize > 0) {

		}
	}

	public boolean Accept(int ParamSize, KClass[] Types) {
		if(this.ParamSize != VariableParamSize) {
			if(this.ParamSize == ParamSize) {
				for(int i = 0; i < ParamSize; i++) {
					if(!ParamTypes[i].Accept(Types[i])) return false;
				}
				return true;
			}
			return false;
		}
		return true;
	}

}

class KParamType {
	KClass KTypeInfo;
	String Symbol;
}
