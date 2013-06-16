package org.KonohaScript;

import org.KonohaScript.KLib.*;
import org.KonohaScript.Parser.KonohaToken;

public class KonohaParam {
	public final static int	MAX					= 16;
	public final static int	VariableParamSize	= -1;
	public int				ReturnSize;
	public KonohaType[]		Types;
	public String[]			ArgNames;

	public KonohaParam(int DataSize, KonohaType ParamData[], String[] ArgNames) {
		this.ReturnSize = 1;
		this.Types = new KonohaType[DataSize];
		this.ArgNames = new String[DataSize - this.ReturnSize];
		System.arraycopy(ParamData, 0, this.Types, 0, DataSize);
		System.arraycopy(ArgNames, 0, this.ArgNames, 0, DataSize - this.ReturnSize);
	}

	public static KonohaParam ParseOf(KonohaNameSpace ns, String TypeList) {
		TokenList BufferList = ns.Tokenize(TypeList, 0);
		int next = BufferList.size();
		ns.PreProcess(BufferList, 0, next, BufferList);
		KonohaType[] ParamData = new KonohaType[KonohaParam.MAX];
		String[] ArgNames = new String[KonohaParam.MAX];
		int i, DataSize = 0, ParamSize = 0;
		for(i = next; i < BufferList.size(); i++) {
			KonohaToken Token = BufferList.get(i);
			if(Token.ResolvedObject instanceof KonohaType) {
				ParamData[DataSize] = (KonohaType) Token.ResolvedObject;
				DataSize++;
				if(DataSize == KonohaParam.MAX)
					break;
			} else {
				ArgNames[ParamSize] = Token.ParsedText;
				ParamSize++;
			}
		}
		return new KonohaParam(DataSize, ParamData, ArgNames);
	}

	public final int GetParamSize() {
		return this.Types.length - this.ReturnSize;
	};

	public final boolean Match(KonohaParam Other) {
		int ParamSize = Other.GetParamSize();
		if(ParamSize == this.GetParamSize()) {
			for(int i = this.ReturnSize; i < this.Types.length; i++) {
				if(this.Types[i] != Other.Types[i])
					return false;
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
