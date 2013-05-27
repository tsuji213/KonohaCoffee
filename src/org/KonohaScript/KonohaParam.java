package org.KonohaScript;

import java.util.ArrayList;

public class KonohaParam {
	public final static int	MAX					= 16;
	public final static int	VariableParamSize	= -1;
	public int				ReturnSize;
	public KonohaType[]			Types;

	public KonohaParam(int DataSize, KonohaType ParamData[]) {
		this.ReturnSize = 1;
		this.Types = new KonohaType[DataSize];
		System.arraycopy(ParamData, 0, this.Types, 0, DataSize);
	}

	public static KonohaParam ParseOf(KNameSpace ns, String TypeList) {
		ArrayList<KonohaToken> BufferList = ns.Tokenize(TypeList, 0);
		int next = BufferList.size();
		ns.PreProcess(BufferList, 0, next, BufferList);
		KonohaType[] ParamData = new KonohaType[KonohaParam.MAX];
		int i, DataSize = 0;
		for(i = next; i < BufferList.size(); i++) {
			KonohaToken Token = BufferList.get(i);
			if(Token.ResolvedObject instanceof KonohaType) {
				ParamData[DataSize] = (KonohaType) Token.ResolvedObject;
				DataSize++;
				if(DataSize == KonohaParam.MAX)
					break;
			}
		}
		return new KonohaParam(DataSize, ParamData);
	}

	public final int GetParamSize() {
		return this.Types.length - this.ReturnSize;
	};

	public final boolean Match(KonohaParam Other) {
		int ParamSize = Other.GetParamSize();
		if(ParamSize == GetParamSize()) {
			for(int i = ReturnSize; i < Types.length; i++) {
				if(Types[i] != Other.Types[i]) return false;
			}
			return true;
		}
		return false;
	}

	// public boolean Accept(int ParamSize, KClass[] Types) {
	// if(ParamTypes. == ParamSize) {
	// for(int i = 1; i < ParamSize; i++) {
	// if(!ParamTypes[i].Accept(Types[i])) return false;
	// }
	// return true;
	// }
	// return false;
	// }
	// return true;
	// }

}
