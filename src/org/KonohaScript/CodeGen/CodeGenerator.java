package org.KonohaScript.CodeGen;

import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.SyntaxTree.NodeVisitor;
import org.KonohaScript.SyntaxTree.TypedNode;

class CompiledMethod extends KonohaMethod {
	public Object	CompiledCode;

	public CompiledMethod(KonohaMethod MethodInfo) {
		super(MethodInfo.MethodFlag, MethodInfo.ClassInfo,
				MethodInfo.MethodName, MethodInfo.Param, null);
	}
	
	@Override
	protected boolean IsStaticInvocation() {
		return false; //FIXME
	}

	@Override
	public Object Eval(Object[] ParamData) {
		return null;
	}
}

public abstract class CodeGenerator extends NodeVisitor {
	KonohaArray	        LocalVals;
	KonohaMethod		MethodInfo;

	CodeGenerator(KonohaMethod MethodInfo) {
		this.LocalVals = new KonohaArray();
		this.MethodInfo = MethodInfo;
	}

	CompiledMethod Compile(TypedNode Block) {
		return null;
	}

	Local FindLocalVariable(String Name) {
		for(int i = 0; i < LocalVals.size(); i++ ) {
			Local l = (Local)LocalVals.get(i);
			if(l.Name.compareTo(Name) == 0) {
				return l;
			}
		}
		return null;
	}

	Local GetLocalVariableByIndex(int Index) {
		if(this.LocalVals.size() > Index) {
			return (Local)LocalVals.get(Index);
		}
		return null;
	}

	Local AddLocal(KonohaType Type, String Name) {
		Local local = new Local(this.LocalVals.size(), Type, Name);
		this.LocalVals.add(local);
		return local;
	}

	Local AddLocalVarIfNotDefined(KonohaType Type, String Name) {
		Local local = this.FindLocalVariable(Name);
		if(local != null) {
			return local;
		}
		return this.AddLocal(Type, Name);
	}

	public void Prepare(KonohaMethod Method) {
		this.LocalVals.clear();
		this.MethodInfo = Method;
		this.AddLocal(Method.ClassInfo, "this");
	}

	public void Prepare(KonohaMethod Method, KonohaArray params) {
		this.Prepare(Method);
		for(int i = 0; i < params.size(); i++) {
			Local local = (Local)params.get(i);
			this.AddLocal(local.TypeInfo, local.Name);
		}
	}

}