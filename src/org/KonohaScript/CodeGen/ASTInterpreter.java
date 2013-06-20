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
import org.KonohaScript.SyntaxTree.NodeVisitor;
import org.KonohaScript.SyntaxTree.NodeVisitor.AssignNodeAcceptor;
import org.KonohaScript.SyntaxTree.NodeVisitor.IfNodeAcceptor;
import org.KonohaScript.SyntaxTree.NodeVisitor.LetNodeAcceptor;
import org.KonohaScript.SyntaxTree.NodeVisitor.LoopNodeAcceptor;
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

class InterpreterAndNodeAcceptor implements NodeVisitor.AndNodeAcceptor {
	@Override
	public boolean Invoke(AndNode Node, NodeVisitor Visitor) {
		ASTInterpreter thisVisitor = (ASTInterpreter) Visitor;
		Visitor.EnterAnd(Node);
		Visitor.Visit(Node.LeftNode);
		if(thisVisitor.Pop() == Boolean.TRUE) {
			Visitor.Visit(Node.RightNode);
			if(thisVisitor.Pop() == Boolean.TRUE) {
				thisVisitor.push(Boolean.TRUE);
			} else {
				thisVisitor.push(Boolean.FALSE);
			}
		} else {
			thisVisitor.push(Boolean.FALSE);
		}
		return Visitor.ExitAnd(Node);
	}
}

class InterpreterOrNodeAcceptor implements NodeVisitor.OrNodeAcceptor {
	@Override
	public boolean Invoke(OrNode Node, NodeVisitor Visitor) {
		ASTInterpreter thisVisitor = (ASTInterpreter) Visitor;
		Visitor.EnterOr(Node);
		Visitor.Visit(Node.LeftNode);
		if(thisVisitor.Pop() == Boolean.TRUE) {
			thisVisitor.push(Boolean.TRUE);
		} else {
			Visitor.Visit(Node.RightNode);
			if(thisVisitor.Pop() == Boolean.TRUE) {
				thisVisitor.push(Boolean.TRUE);
			} else {
				thisVisitor.push(Boolean.FALSE);
			}
		}
		return Visitor.ExitOr(Node);
	}
}

class InterpreterAssignNodeAcceptor implements AssignNodeAcceptor {
	@Override
	public boolean Invoke(AssignNode Node, NodeVisitor Visitor) {
		Visitor.EnterAssign(Node);
		if(Node.LeftNode instanceof GetterNode) {
			GetterNode Left = (GetterNode) Node.LeftNode;
			Visitor.Visit(Left.BaseNode);
		}
		Visitor.Visit(Node.RightNode);
		return Visitor.ExitAssign(Node);
	}
}

class InterpreterLetNodeAcceptor implements LetNodeAcceptor {
	@Override
	public boolean Invoke(LetNode Node, NodeVisitor Visitor) {
		ASTInterpreter thisVisitor = (ASTInterpreter) Visitor;
		Visitor.EnterLet(Node);
		Visitor.Visit(Node.ValueNode);
		thisVisitor.LocalVariable.put(Node.VarToken.ParsedText, thisVisitor.Pop());
		Visitor.VisitList(Node.BlockNode);
		return Visitor.ExitLet(Node);
	}
}

class InterpreterIfNodeAcceptor implements IfNodeAcceptor {
	@Override
	public boolean Invoke(IfNode Node, NodeVisitor Visitor) {
		ASTInterpreter thisVisitor = (ASTInterpreter) Visitor;
		Visitor.EnterIf(Node);
		Visitor.Visit(Node.CondExpr);
		if(thisVisitor.Pop() == Boolean.TRUE) {
			Visitor.VisitList(Node.ThenNode);
		} else {
			Visitor.VisitList(Node.ElseNode);
		}
		return Visitor.ExitIf(Node);
	}
}

class InterpreterLoopNodeAcceptor implements LoopNodeAcceptor {

	@Override
	public boolean Invoke(LoopNode Node, NodeVisitor Visitor) {
		ASTInterpreter thisVisitor = (ASTInterpreter) Visitor;
		Visitor.EnterLoop(Node);
		while(true) {
			Visitor.Visit(Node.CondExpr);
			if(thisVisitor.Pop() == Boolean.FALSE) {
				break;
			}
			try {
				Visitor.VisitList(Node.LoopBody);
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
			Visitor.Visit(Node.IterationExpr);
		}
		return Visitor.ExitLoop(Node);
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

		this.AndNodeAcceptor = new InterpreterAndNodeAcceptor();
		this.OrNodeAcceptor = new InterpreterOrNodeAcceptor();
		this.AssignNodeAcceptor = new InterpreterAssignNodeAcceptor();
		this.LetNodeAcceptor = new InterpreterLetNodeAcceptor();
		this.IfNodeAcceptor = new InterpreterIfNodeAcceptor();
		this.LoopNodeAcceptor = new InterpreterLoopNodeAcceptor();
	}

	@Override
	public boolean Visit(TypedNode Node) {
		return Node.Evaluate(this);
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
	public void EnterDefine(DefineNode Node) {
	}

	@Override
	public boolean ExitDefine(DefineNode Node) {
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
	public void EnterConst(ConstNode Node) {
	}

	@Override
	public boolean ExitConst(ConstNode Node) {
		this.push(Node.ConstValue);
		return true;
	}

	@Override
	public void EnterNew(NewNode Node) {
	}

	@Override
	public boolean ExitNew(NewNode Node) {
		KonohaType TypeInfo = Node.TypeInfo;
		Class<?> KClass = TypeInfo.DefaultNullValue.getClass();
		try {
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
	public void EnterNull(NullNode Node) {
	}

	@Override
	public boolean ExitNull(NullNode Node) {
		KonohaType TypeInfo = Node.TypeInfo;
		this.push(TypeInfo.DefaultNullValue);
		return true;
	}

	@Override
	public void EnterLocal(LocalNode Node) {
	}

	@Override
	public boolean ExitLocal(LocalNode Node) {
		Object Obj = this.LocalVariable.get(Node.FieldName);
		this.push(Obj);
		return true;
	}

	@Override
	public void EnterGetter(GetterNode Node) {
	}

	@Override
	public boolean ExitGetter(GetterNode Node) {
		Object Base = this.Pop();
		assert (Base instanceof KonohaObject);
		KonohaObject Obj = (KonohaObject) Base;
		this.push(Obj.get(Node.FieldName));
		return true;
	}

	@Override
	public void EnterApply(ApplyNode Node) {
	}

	@Override
	public boolean ExitApply(ApplyNode Node) {
		int n = Node.Params.size();
		Object[] args = new Object[n];
		for(int i = 0; i < n; ++i) {
			args[n - i - 1] = this.Pop();
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
	public void EnterAnd(AndNode Node) {
	}

	@Override
	public boolean ExitAnd(AndNode Node) {
		/* do nothing */
		return true;
	}

	@Override
	public void EnterOr(OrNode Node) {
	}

	@Override
	public boolean ExitOr(OrNode Node) {
		/* do nothing */
		return true;
	}

	@Override
	public void EnterAssign(AssignNode Node) {
	}

	@Override
	public boolean ExitAssign(AssignNode Node) {
		Object Val = this.Pop();
		if(Node.LeftNode instanceof GetterNode) {
			GetterNode Left = (GetterNode) Node.LeftNode;
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
	public void EnterLet(LetNode Node) {
		this.LocalVariable.put(Node.VarToken.ParsedText, null);
	}

	@Override
	public boolean ExitLet(LetNode Node) {
		this.LocalVariable.put(Node.VarToken.ParsedText, null);
		return true;
	}

	@Override
	public void EnterIf(IfNode Node) {
	}

	@Override
	public boolean ExitIf(IfNode Node) {
		/* do nothing */
		return true;
	}

	@Override
	public void EnterSwitch(SwitchNode Node) {
	}

	@Override
	public boolean ExitSwitch(SwitchNode Node) {
		throw new NotSupportedCodeError();
		// return true;
	}

	@Override
	public void EnterLoop(LoopNode Node) {
	}

	@Override
	public boolean ExitLoop(LoopNode Node) {
		/* do nothing */
		return true;
	}

	@Override
	public void EnterReturn(ReturnNode Node) {
	}

	@Override
	public boolean ExitReturn(ReturnNode Node) {
		this.push(this.Pop());
		throw new ReturnException();
	}

	@Override
	public void EnterLabel(LabelNode Node) {
	}

	@Override
	public boolean ExitLabel(LabelNode Node) {
		throw new NotSupportedCodeError();
		// return true;
	}

	@Override
	public void EnterJump(JumpNode Node) {
	}

	@Override
	public boolean ExitJump(JumpNode Node) {
		if(Node.Label == "break") {
			throw new LoopBreakException(Node);
		} else if(Node.Label == "continue") {
			throw new LoopContinueException(Node);
		}
		throw new NotSupportedCodeError();
		// return false;
	}

	@Override
	public void EnterTry(TryNode Node) {
	}

	@Override
	public boolean ExitTry(TryNode Node) {
		throw new NotSupportedCodeError();
		// return true;
	}

	@Override
	public void EnterThrow(ThrowNode Node) {
	}

	@Override
	public boolean ExitThrow(ThrowNode Node) {
		throw new NotSupportedCodeError();
		// return false;
	}

	@Override
	public void EnterFunction(FunctionNode Node) {
	}

	@Override
	public boolean ExitFunction(FunctionNode Node) {
		throw new NotSupportedCodeError();
		// return true;
	}

	@Override
	public void EnterError(ErrorNode Node) {
	}

	@Override
	public boolean ExitError(ErrorNode Node) {
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
		Object Ret = this.Pop();
		return Ret;
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
