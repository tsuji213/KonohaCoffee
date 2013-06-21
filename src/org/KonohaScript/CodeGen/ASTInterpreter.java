package org.KonohaScript.CodeGen;

import org.KonohaScript.KonohaBuilder;
import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaMethodInvoker;
import org.KonohaScript.KonohaObject;
import org.KonohaScript.KonohaParam;
import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.KLib.KonohaMap;
import org.KonohaScript.SyntaxTree.AndNode;
import org.KonohaScript.SyntaxTree.ApplyNode;
import org.KonohaScript.SyntaxTree.AssignNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.DefineNode;
import org.KonohaScript.SyntaxTree.ErrorNode;
import org.KonohaScript.SyntaxTree.FunctionNode;
import org.KonohaScript.SyntaxTree.GetterNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.JumpNode;
import org.KonohaScript.SyntaxTree.LabelNode;
import org.KonohaScript.SyntaxTree.LetNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.LoopNode;
import org.KonohaScript.SyntaxTree.NewNode;
import org.KonohaScript.SyntaxTree.NullNode;
import org.KonohaScript.SyntaxTree.OrNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.SwitchNode;
import org.KonohaScript.SyntaxTree.ThrowNode;
import org.KonohaScript.SyntaxTree.TryNode;
import org.KonohaScript.SyntaxTree.TypedNode;

class ASTInterpreterMethodInvoker extends KonohaMethodInvoker {
	ASTInterpreter	Interpreter;
	KonohaMethod	Method;

	public ASTInterpreterMethodInvoker(KonohaMethod Method, Object CompiledCode, ASTInterpreter Interpreter) {
		super(Method.Param, CompiledCode);
		this.Interpreter = Interpreter;
		this.Method = Method;
	}

	@Override
	public Object Invoke(Object[] Args) {
		KonohaParam Param = this.Method.Param;
		assert (Param.GetParamSize() == Args.length - 1);
		this.Interpreter.BindLocalVariable("this", Args[0]);
		for(int i = 1; i < Args.length; i++) {
			this.Interpreter.BindLocalVariable(Param.ArgNames[i - 1], Args[i]);
		}
		return this.Interpreter.Eval((TypedNode) this.CompiledCode, this.Method, null);
	}
}

class LoopBreakException extends RuntimeException {
	private static final long	serialVersionUID	= 1L;
	JumpNode					Jump;

	public LoopBreakException(JumpNode Jump) {
		this.Jump = Jump;
	}
}

class LoopContinueException extends RuntimeException {
	private static final long	serialVersionUID	= 1L;
	JumpNode					Jump;

	public LoopContinueException(JumpNode Jump) {
		this.Jump = Jump;

	}
}

class ReturnException extends RuntimeException {
	private static final long	serialVersionUID	= 1L;

	public ReturnException() {
	}
}

class NotSupportedCodeError extends RuntimeException {
	private static final long	serialVersionUID	= 1L;

	NotSupportedCodeError() {
		super();
	}
}

public class ASTInterpreter extends CodeGenerator implements KonohaBuilder {
	KonohaArray	Evaled;
	KonohaArray	Labels;
	KonohaMap	LocalVariable;

	public ASTInterpreter() {
		super(null);
		this.Init();
	}

	public void BindLocalVariable(String FieldName, Object Value) {
		this.LocalVariable.put(FieldName, Value);
	}

	public ASTInterpreter(KonohaMethod MethodInfo) {
		super(MethodInfo);
		this.Init();
	}

	void Init() {
		this.Evaled = new KonohaArray();
		this.Labels = new KonohaArray();
		this.LocalVariable = new KonohaMap();
	}

	public Object Pop() {
		return this.Evaled.remove(this.Evaled.size() - 1);
	}

	public void push(Object Obj) {
		this.Evaled.add(Obj);
	}

	@Override
	public void Prepare(KonohaMethod Method) {
		this.LocalVals.clear();
		this.MethodInfo = Method;
		if(Method != null) {
			this.AddLocal(Method.ClassInfo, "this");
		}
	}

	@Override
	public void Prepare(KonohaMethod Method, KonohaArray params) {
		this.Prepare(Method);
		for(int i = 0; i < params.size(); i++) {
			Local local = (Local) params.get(i);
			this.AddLocal(local.TypeInfo, local.Name);
		}
	}

	@Override
	public KonohaMethodInvoker Compile(TypedNode Block) {
		KonohaMethodInvoker Mtd = new ASTInterpreterMethodInvoker(this.MethodInfo, Block, this);
		return Mtd;
	}

	@Override
	public boolean VisitDefine(DefineNode Node) {
		if(Node.DefInfo instanceof KonohaMethod) {
			KonohaMethod Mtd = (KonohaMethod) Node.DefInfo;
			Mtd.DoCompilation();
		} else {
			throw new NotSupportedCodeError();
		}
		this.push(null);
		return true;
	}

	@Override
	public boolean VisitConst(ConstNode Node) {
		this.push(Node.ConstValue);
		return true;
	}

	@Override
	public boolean VisitNew(NewNode Node) {
		for(int i = 0; i < Node.Params.size(); i++) {
			TypedNode Param = (TypedNode) Node.Params.get(i);
			Param.Evaluate(this);
		}
		KonohaType TypeInfo = Node.TypeInfo;
		// FIXME It seems that I cannot get type infomation from DefaultNullValue.
		Class<?> KClass = TypeInfo.HostedClassInfo;//TypeInfo.DefaultNullValue.getClass();
		try {
			// FIXME Enable using constractor with parameters
			Object Obj = KClass.newInstance();
			this.push(Obj);
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean VisitNull(NullNode Node) {
		KonohaType TypeInfo = Node.TypeInfo;
		this.push(TypeInfo.DefaultNullValue);
		return true;
	}

	@Override
	public boolean VisitLocal(LocalNode Node) {
		Object Obj = this.LocalVariable.get(Node.FieldName);
		this.push(Obj);
		return true;
	}

	@Override
	public boolean VisitGetter(GetterNode Node) {
		Node.BaseNode.Evaluate(this);
		Object Base = this.Pop();
		assert (Base instanceof KonohaObject);
		KonohaObject Obj = (KonohaObject) Base;
		this.push(Obj.get(Node.FieldName));
		return true;
	}

	@Override
	public boolean VisitApply(ApplyNode Node) {
		int n = Node.Params.size();
		Object[] args = new Object[n];
		for(int i = 0; i < n; i++) {
			TypedNode Param = (TypedNode) Node.Params.get(i);
			Param.Evaluate(this);
			args[i] = this.Pop();
		}
		KonohaMethod Mtd = Node.Method;
		if(Mtd.MethodInvoker == null) {
			Mtd.DoCompilation();
		}
		Object Ret = Mtd.Eval(args);
		this.push(Ret);
		return true;
	}

	@Override
	public boolean VisitAnd(AndNode Node) {
		Node.LeftNode.Evaluate(this);
		if(this.Pop() == Boolean.TRUE) {
			Node.RightNode.Evaluate(this);
			if(this.Pop() == Boolean.TRUE) {
				this.push(Boolean.TRUE);
			} else {
				this.push(Boolean.FALSE);
			}
		} else {
			this.push(Boolean.FALSE);
		}
		return true;
	}

	@Override
	public boolean VisitOr(OrNode Node) {
		Node.LeftNode.Evaluate(this);
		if(this.Pop() == Boolean.TRUE) {
			this.push(Boolean.TRUE);
		} else {
			Node.RightNode.Evaluate(this);
			if(this.Pop() == Boolean.TRUE) {
				this.push(Boolean.TRUE);
			} else {
				this.push(Boolean.FALSE);
			}
		}
		return true;
	}

	@Override
	public boolean VisitAssign(AssignNode Node) {
		Node.RightNode.Evaluate(this);
		Object Val = this.Pop();
		if(Node.LeftNode instanceof GetterNode) {
			GetterNode Left = (GetterNode) Node.LeftNode;
			Left.BaseNode.Evaluate(this);
			Object Base = this.Pop();
			assert (Base instanceof KonohaObject);
			KonohaObject Obj = (KonohaObject) Base;
			Obj.set(Left.FieldName, Val);
			this.push(Val);
		} else {
			assert (Node.LeftNode instanceof LocalNode);
			LocalNode Left = (LocalNode) Node.LeftNode;
			this.LocalVariable.put(Left.FieldName, Val);
		}
		this.push(Val);
		return true;
	}

	@Override
	public boolean VisitLet(LetNode Node) {
		this.LocalVariable.put(Node.VarToken.ParsedText, null);
		Node.ValueNode.Evaluate(this);
		Object Value = this.Pop();
		this.LocalVariable.put(Node.VarToken.ParsedText, Value);
		this.VisitList(Node.BlockNode);
		return true;
	}

	@Override
	public boolean VisitIf(IfNode Node) {
		Node.CondExpr.Evaluate(this);
		if(this.Pop() == Boolean.TRUE) {
			this.VisitList(Node.ThenNode);
		} else {
			this.VisitList(Node.ElseNode);
		}
		return true;
	}

	@Override
	public boolean VisitSwitch(SwitchNode Node) {
		Node.CondExpr.Evaluate(this);
		for(int i = 0; i < Node.Blocks.size(); i++) {
			TypedNode Block = (TypedNode) Node.Blocks.get(i);
			this.VisitList(Block);
		}

		throw new NotSupportedCodeError();
		// return true;
	}

	@Override
	public boolean VisitLoop(LoopNode Node) {
		while(true) {
			Node.CondExpr.Evaluate(this);
			if(this.Pop() == Boolean.FALSE) {
				break;
			}
			try {
				this.VisitList(Node.LoopBody);
			}
			catch (LoopBreakException e) {
				if(e.Jump.TargetNode == null) {
					e.Jump.TargetNode = Node;
				} else if(e.Jump.TargetNode != Node) {
					throw e;
				}
				break;
			}
			catch (LoopContinueException e) {
				if(e.Jump.TargetNode == null) {
					e.Jump.TargetNode = Node;
				} else if(e.Jump.TargetNode != Node) {
					throw e;
				}
			}
			Node.IterationExpr.Evaluate(this);
		}
		return true;
	}

	@Override
	public boolean VisitReturn(ReturnNode Node) {
		Node.Expr.Evaluate(this);
		this.push(this.Pop());
		throw new ReturnException();
	}

	@Override
	public boolean VisitLabel(LabelNode Node) {
		throw new NotSupportedCodeError();
		// return true;
	}

	@Override
	public boolean VisitJump(JumpNode Node) {
		if(Node.Label == "break") {
			throw new LoopBreakException(Node);
		} else if(Node.Label == "continue") {
			throw new LoopContinueException(Node);
		}
		throw new NotSupportedCodeError();
		// return false;
	}

	@Override
	public boolean VisitTry(TryNode Node) {
		this.VisitList(Node.TryBlock);
		for(int i = 0; i < Node.CatchBlock.size(); i++) {
			TypedNode Block = (TypedNode) Node.CatchBlock.get(i);
			TypedNode Exception = (TypedNode) Node.TargetException.get(i);
			this.VisitList(Block);
		}
		this.VisitList(Node.FinallyBlock);

		throw new NotSupportedCodeError();
		// return true;
	}

	@Override
	public boolean VisitThrow(ThrowNode Node) {
		Node.Expr.Evaluate(this);
		throw new NotSupportedCodeError();
		// return false;
	}

	@Override
	public boolean VisitFunction(FunctionNode Node) {
		throw new NotSupportedCodeError();
		// return true;
	}

	@Override
	public boolean VisitError(ErrorNode Node) {
		throw new RuntimeException(Node.ErrorMessage);
	}

	public Object Eval(TypedNode Node, KonohaMethod Method, KonohaObject GlobalObject) {
		KonohaArray Params = new KonohaArray();
		if(Method != null) {
			for(int i = 0; i < Method.Param.GetParamSize(); i++) {
				Params.add(new Param(i, Method.GetParamType(Method.ClassInfo, i), Method.Param.ArgNames[i]));
			}
		} else {
			this.BindLocalVariable("this", GlobalObject);
			//FIXME
			//Params.add(new Param(0, null, "this"));
		}
		this.Prepare(Method, Params);
		try {
			this.VisitList(Node);
		}
		catch (ReturnException e) {
			/* do nothing */
		}
		if(this.Evaled.size() > 0) {
			Object Ret = this.Pop();
			return Ret;
		}
		return null;
	}

	@Override
	public Object EvalAtTopLevel(TypedNode Node, KonohaObject GlobalObject) {
		Object Ret = this.Eval(Node, null, GlobalObject);
		if(Ret == null) {
			Ret = "";
		}
		//System.out.println("EvalAtTopLevel::::::" + Ret.toString());
		return Ret;
	}

	@Override
	public KonohaMethodInvoker Build(TypedNode Node, KonohaMethod Method) {
		this.Prepare(Method);
		return this.Compile(Node);
	}

	//	public static void main(String[] args) {
	//		Konoha konoha = new Konoha(new MiniKonohaGrammar(), "org.KonohaScript.CodeGen.ASTInterpreter");
	//		konoha.Eval("3 + 1", 0);
	//		//konoha.Eval("int add(int x) { return x + 1; }", 0);
	//		konoha.Eval("add(10);", 0);
	//	}
}
