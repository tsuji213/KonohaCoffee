package org.KonohaScript.CodeGen;

import org.KonohaScript.AST.*;

class CompiledMethod {
	Object Invoke(Object[] Args) { return null; }
}

public interface CodeGenerator {
	CompiledMethod Compile(TypedNode Block);
	void Visit(TypedNode Node);

	void EnterDone(DoneNode Node);
	void ExitDone(DoneNode Node);

	void EnterConst(ConstNode Node);
	void ExitConst(ConstNode Node);

	void EnterNew(NewNode Node);
	void ExitNew(NewNode Node);

	void EnterNull(NullNode Node);
	void ExitNull(NullNode Node);

	void EnterLocal(LocalNode Node);
	void ExitLocal(LocalNode Node);

	void EnterField(FieldNode Node);
	void ExitField(FieldNode Node);

	void EnterBox(BoxNode Node);
	void ExitBox(BoxNode Node);

	void EnterMethodCall(MethodCallNode Node);
	void ExitMethodCall(MethodCallNode Node);

	void EnterAnd(AndNode Node);
	void ExitAnd(AndNode Node);

	void EnterOr(OrNode Node);
	void ExitOr(OrNode Node);

	void EnterAssign(AssignNode Node);
	void ExitAssign(AssignNode Node);

	void EnterLet(LetNode Node);
	void ExitLet(LetNode Node);

	void EnterBlock(BlockNode Node);
	void ExitBlock(BlockNode Node);

	void EnterIf(IfNode Node);
	void ExitIf(IfNode Node);

	void EnterSwitch(SwitchNode Node);
	void ExitSwitch(SwitchNode Node);

	void EnterLoop(LoopNode Node);
	void ExitLoop(LoopNode Node);

	void EnterReturn(ReturnNode Node);
	void ExitReturn(ReturnNode Node);

	void EnterLabel(LabelNode Node);
	void ExitLabel(LabelNode Node);

	void EnterJump(JumpNode Node);
	void ExitJump(JumpNode Node);

	void EnterTry(TryNode Node);
	void ExitTry(TryNode Node);

	void EnterThrow(ThrowNode Node);
	void ExitThrow(ThrowNode Node);

	void EnterFunction(FunctionNode Node);
	void ExitFunction(FunctionNode Node);

	void EnterError(ErrorNode Node);
	void ExitError(ErrorNode Node);
}
