package org.KonohaScript.CodeGen;

import org.KonohaScript.AST.AndNode;
import org.KonohaScript.AST.AssignNode;
import org.KonohaScript.AST.BlockNode;
import org.KonohaScript.AST.BoxNode;
import org.KonohaScript.AST.ConstNode;
import org.KonohaScript.AST.DoneNode;
import org.KonohaScript.AST.ErrorNode;
import org.KonohaScript.AST.FieldNode;
import org.KonohaScript.AST.FunctionNode;
import org.KonohaScript.AST.IfNode;
import org.KonohaScript.AST.JumpNode;
import org.KonohaScript.AST.LabelNode;
import org.KonohaScript.AST.LetNode;
import org.KonohaScript.AST.LocalNode;
import org.KonohaScript.AST.LoopNode;
import org.KonohaScript.AST.MethodCallNode;
import org.KonohaScript.AST.NewNode;
import org.KonohaScript.AST.NullNode;
import org.KonohaScript.AST.OrNode;
import org.KonohaScript.AST.ReturnNode;
import org.KonohaScript.AST.SwitchNode;
import org.KonohaScript.AST.ThrowNode;
import org.KonohaScript.AST.TryNode;
import org.KonohaScript.AST.TypedNode;

public interface ASTVisitor {
	boolean Visit(TypedNode Node);

	void EnterDone(DoneNode Node);

	boolean ExitDone(DoneNode Node);

	void EnterConst(ConstNode Node);

	boolean ExitConst(ConstNode Node);

	void EnterNew(NewNode Node);

	boolean ExitNew(NewNode Node);

	void EnterNull(NullNode Node);

	boolean ExitNull(NullNode Node);

	void EnterLocal(LocalNode Node);

	boolean ExitLocal(LocalNode Node);

	void EnterField(FieldNode Node);

	boolean ExitField(FieldNode Node);

	void EnterBox(BoxNode Node);

	boolean ExitBox(BoxNode Node);

	void EnterMethodCall(MethodCallNode Node);

	boolean ExitMethodCall(MethodCallNode Node);

	void EnterAnd(AndNode Node);

	boolean ExitAnd(AndNode Node);

	void EnterOr(OrNode Node);

	boolean ExitOr(OrNode Node);

	void EnterAssign(AssignNode Node);

	boolean ExitAssign(AssignNode Node);

	void EnterLet(LetNode Node);

	boolean ExitLet(LetNode Node);

	void EnterBlock(BlockNode Node);

	boolean ExitBlock(BlockNode Node);

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