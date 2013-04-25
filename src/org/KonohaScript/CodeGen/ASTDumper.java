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

public class ASTDumper implements CodeGenerator {

	@Override
	public CompiledMethod Compile(TypedNode Block) {
		Visit(Block);
		return null;
	}

	@Override
	public void Visit(TypedNode Node) {
		Node.Evaluate(this);
	}

	@Override
	public void EnterDone(DoneNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitDone(DoneNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterConst(ConstNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitConst(ConstNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterNew(NewNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitNew(NewNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterNull(NullNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitNull(NullNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterLocal(LocalNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitLocal(LocalNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterField(FieldNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitField(FieldNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterBox(BoxNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitBox(BoxNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterMethodCall(MethodCallNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitMethodCall(MethodCallNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterAnd(AndNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitAnd(AndNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterOr(OrNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitOr(OrNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterAssign(AssignNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitAssign(AssignNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterLet(LetNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitLet(LetNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterBlock(BlockNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitBlock(BlockNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterIf(IfNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitIf(IfNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterSwitch(SwitchNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitSwitch(SwitchNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterLoop(LoopNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitLoop(LoopNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterReturn(ReturnNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitReturn(ReturnNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterLabel(LabelNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitLabel(LabelNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterJump(JumpNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitJump(JumpNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterTry(TryNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitTry(TryNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterThrow(ThrowNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitThrow(ThrowNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterFunction(FunctionNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitFunction(FunctionNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterError(ErrorNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExitError(ErrorNode Node) {
		// TODO Auto-generated method stub

	}

}
