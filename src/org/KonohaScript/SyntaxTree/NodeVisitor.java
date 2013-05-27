package org.KonohaScript.SyntaxTree;

public interface NodeVisitor {
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

	void EnterField(GetterNode Node);

	boolean ExitField(GetterNode Node);

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