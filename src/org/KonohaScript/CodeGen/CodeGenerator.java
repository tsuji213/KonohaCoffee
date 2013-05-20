package org.KonohaScript.CodeGen;

import java.util.ArrayList;

import org.KonohaScript.KClass;
import org.KonohaScript.KMethod;
import org.KonohaScript.SyntaxTree.TypedNode;

class CompiledMethod {
	public Object CompiledCode;

	Object Invoke(Object[] Args) {
		return null;
	}
}

public abstract class CodeGenerator {
	ArrayList<Local> LocalVals;

	CodeGenerator() {
		this.LocalVals = new ArrayList<Local>();
	}

	CompiledMethod Compile(TypedNode Block) {
		return null;
	}

	Local FindLocalVariable(String Name) {
		for (Local l : this.LocalVals) {
			if (l.Name.compareTo(Name) == 0) {
				return l;
			}
		}
		return null;
	}

	Local GetLocalVariableByIndex(int Index) {
		if (this.LocalVals.size() > Index) {
			return this.LocalVals.get(Index);
		}
		return null;
	}

	Local AddLocal(KClass Type, String Name) {
		Local local = new Local(this.LocalVals.size(), Type, Name);
		this.LocalVals.add(local);
		return local;
	}

	Local AddLocalVarIfNotDefined(KClass Type, String Name) {
		Local local = this.FindLocalVariable(Name);
		if (local != null) {
			return local;
		}
		return AddLocal(Type, Name);
	}

	public void Prepare(KClass ReturnType, KMethod Method, ArrayList<Local> ParamTypes) {
	}

	public void Prepare(KClass ReturnType, KMethod Method) {
	}

}