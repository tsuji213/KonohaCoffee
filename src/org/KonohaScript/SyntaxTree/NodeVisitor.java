package org.KonohaScript.SyntaxTree;

interface INodeVisitor {
	boolean Visit(TypedNode Node);

	void EnterDefine(DefineNode Node);

	boolean ExitDefine(DefineNode Node);

	void EnterConst(ConstNode Node);

	boolean ExitConst(ConstNode Node);

	void EnterNew(NewNode Node);

	boolean ExitNew(NewNode Node);

	void EnterNull(NullNode Node);

	boolean ExitNull(NullNode Node);

	void EnterLocal(LocalNode Node);

	boolean ExitLocal(LocalNode Node);

	void EnterGetter(GetterNode Node);

	boolean ExitGetter(GetterNode Node);

	void EnterApply(ApplyNode Node);

	boolean ExitApply(ApplyNode Node);

	void EnterAnd(AndNode Node);

	boolean ExitAnd(AndNode Node);

	void EnterOr(OrNode Node);

	boolean ExitOr(OrNode Node);

	void EnterAssign(AssignNode Node);

	boolean ExitAssign(AssignNode Node);

	void EnterLet(LetNode Node);

	boolean ExitLet(LetNode Node);

	void EnterIf(IfNode Node);

	boolean ExitIf(IfNode Node);

	void EnterSwitch(SwitchNode Node);

	boolean ExitSwitch(SwitchNode Node);

	void EnterLoop(LoopNode Node);

	boolean ExitLoop(LoopNode Node);

	void EnterReturn(ReturnNode Node);

	boolean ExitReturn(ReturnNode Node);

	void EnterLabel(LabelNode Node);

	boolean ExitLabel(LabelNode Node);

	void EnterJump(JumpNode Node);

	boolean ExitJump(JumpNode Node);

	void EnterTry(TryNode Node);

	boolean ExitTry(TryNode Node);

	void EnterThrow(ThrowNode Node);

	boolean ExitThrow(ThrowNode Node);

	void EnterFunction(FunctionNode Node);

	boolean ExitFunction(FunctionNode Node);

	void EnterError(ErrorNode Node);

	boolean ExitError(ErrorNode Node);
}

public abstract class NodeVisitor implements INodeVisitor {
	public interface AndNodeAcceptor {
		boolean Eval(AndNode Node, NodeVisitor Visitor);
	}

	public interface ApplyNodeAcceptor {
		boolean Eval(ApplyNode Node, NodeVisitor Visitor);
	}

	public interface AssignNodeAcceptor {
		boolean Eval(AssignNode Node, NodeVisitor Visitor);
	}

	public interface ConstNodeAcceptor {
		boolean Eval(ConstNode Node, NodeVisitor Visitor);
	}

	public interface DefineNodeAcceptor {
		boolean Eval(DefineNode Node, NodeVisitor Visitor);
	}

	public interface ErrorNodeAcceptor {
		boolean Eval(ErrorNode Node, NodeVisitor Visitor);
	}

	public interface FunctionNodeAcceptor {
		boolean Eval(FunctionNode Node, NodeVisitor Visitor);
	}

	public interface GetterNodeAcceptor {
		boolean Eval(GetterNode Node, NodeVisitor Visitor);
	}

	public interface IfNodeAcceptor {
		boolean Eval(IfNode Node, NodeVisitor Visitor);
	}

	public interface JumpNodeAcceptor {
		boolean Eval(JumpNode Node, NodeVisitor Visitor);
	}

	public interface LabelNodeAcceptor {
		boolean Eval(LabelNode Node, NodeVisitor Visitor);
	}

	public interface LetNodeAcceptor {
		boolean Eval(LetNode Node, NodeVisitor Visitor);
	}

	public interface LocalNodeAcceptor {
		boolean Eval(LocalNode Node, NodeVisitor Visitor);
	}

	public interface LoopNodeAcceptor {
		boolean Eval(LoopNode Node, NodeVisitor Visitor);
	}

	public interface NewNodeAcceptor {
		boolean Eval(NewNode Node, NodeVisitor Visitor);
	}

	public interface NullNodeAcceptor {
		boolean Eval(NullNode Node, NodeVisitor Visitor);
	}

	public interface OrNodeAcceptor {
		boolean Eval(OrNode Node, NodeVisitor Visitor);
	}

	public interface ReturnNodeAcceptor {
		boolean Eval(ReturnNode Node, NodeVisitor Visitor);
	}

	public interface SwitchNodeAcceptor {
		boolean Eval(SwitchNode Node, NodeVisitor Visitor);
	}

	public interface ThrowNodeAcceptor {
		boolean Eval(ThrowNode Node, NodeVisitor Visitor);
	}

	public interface TryNodeAcceptor {
		boolean Eval(TryNode Node, NodeVisitor Visitor);
	}

	public AndNodeAcceptor		AndNodeAcceptor			= new DefaultAndNodeAcceptor();
	public ApplyNodeAcceptor	ApplyNodeAcceptor		= new DefaultApplyNodeAcceptor();
	public AssignNodeAcceptor	AssignNodeAcceptor		= new DefaultAssignNodeAcceptor();
	public ConstNodeAcceptor	ConstNodeAcceptor		= new DefaultConstNodeAcceptor();
	public DefineNodeAcceptor	DefineNodeAcceptor		= new DefaultDefineNodeAcceptor();
	public ErrorNodeAcceptor	ErrorNodeAcceptor		= new DefaultErrorNodeAcceptor();
	public FunctionNodeAcceptor	FunctionNodeAcceptor	= new DefaultFunctionNodeAcceptor();
	public GetterNodeAcceptor	GetterNodeAcceptor		= new DefaultGetterNodeAcceptor();
	public IfNodeAcceptor		IfNodeAcceptor			= new DefaultIfNodeAcceptor();
	public JumpNodeAcceptor		JumpNodeAcceptor		= new DefaultJumpNodeAcceptor();
	public LabelNodeAcceptor	LabelNodeAcceptor		= new DefaultLabelNodeAcceptor();
	public LetNodeAcceptor		LetNodeAcceptor			= new DefaultLetNodeAcceptor();
	public LocalNodeAcceptor	LocalNodeAcceptor		= new DefaultLocalNodeAcceptor();
	public LoopNodeAcceptor		LoopNodeAcceptor		= new DefaultLoopNodeAcceptor();
	public NewNodeAcceptor		NewNodeAcceptor			= new DefaultNewNodeAcceptor();
	public NullNodeAcceptor		NullNodeAcceptor		= new DefaultNullNodeAcceptor();
	public OrNodeAcceptor		OrNodeAcceptor			= new DefaultOrNodeAcceptor();
	public ReturnNodeAcceptor	ReturnNodeAcceptor		= new DefaultReturnNodeAcceptor();
	public SwitchNodeAcceptor	SwitchNodeAcceptor		= new DefaultSwitchNodeAcceptor();
	public ThrowNodeAcceptor	ThrowNodeAcceptor		= new DefaultThrowNodeAcceptor();
	public TryNodeAcceptor		TryNodeAcceptor			= new DefaultTryNodeAcceptor();

}