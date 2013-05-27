/****************************************************************************
 * Copyright (c) 2012-2013, Masahiro Ide <ide@konohascript.org> All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ***************************************************************************/
package org.KonohaScript.CodeGen;

import java.util.ArrayList;

import org.KonohaScript.KClass;
import org.KonohaScript.KMethod;
import org.KonohaScript.KToken;
import org.KonohaScript.SyntaxTree.AndNode;
import org.KonohaScript.SyntaxTree.AssignNode;
import org.KonohaScript.SyntaxTree.BlockNode;
import org.KonohaScript.SyntaxTree.BoxNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.DefNode;
import org.KonohaScript.SyntaxTree.DefineClassNode;
import org.KonohaScript.SyntaxTree.ErrorNode;
import org.KonohaScript.SyntaxTree.FieldNode;
import org.KonohaScript.SyntaxTree.FunctionNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.JumpNode;
import org.KonohaScript.SyntaxTree.LabelNode;
import org.KonohaScript.SyntaxTree.LetNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.LoopNode;
import org.KonohaScript.SyntaxTree.MethodCallNode;
import org.KonohaScript.SyntaxTree.NewNode;
import org.KonohaScript.SyntaxTree.NullNode;
import org.KonohaScript.SyntaxTree.OrNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.SwitchNode;
import org.KonohaScript.SyntaxTree.ThrowNode;
import org.KonohaScript.SyntaxTree.TryNode;
import org.KonohaScript.SyntaxTree.TypedNode;

class Value {
}

class BasicBlock extends Value {
	public void JumpTo(BasicBlock jumpTo) {
		// TODO Auto-generated method stub

	}

	public void Branch(Value cond, BasicBlock loopBB, BasicBlock mergeBB) {
		// TODO Auto-generated method stub

	}

	public void Append(Value cond) {
		// TODO Auto-generated method stub
	}
}

class ValueList extends ArrayList<Value> {
	private static final long	serialVersionUID	= 1L;

	public static ValueList unshift(ValueList oldList, Value Val) {
		ArrayList<Value> newList = new ArrayList<Value>();
		newList.add(Val);
		for (int i = 0; i < oldList.size(); i++) {
			newList.add(oldList.get(i));
		}
		return (ValueList) newList;
	}
}

class TypedNodeList extends ArrayList<TypedNode> {
	private static final long	serialVersionUID	= 1L;
}

class Eval {
	public Value Get(int i) {
		return null;
	}

	public ValueList Get() {
		return null;
	}

	public Value LogicalAnd(Value l, Value r) {
		// TODO Auto-generated method stub
		return null;

	}

	public Value LogicalOr(Value l, Value r) {
		// TODO Auto-generated method stub
		return null;

	}

	public Value Box(Value e) {
		// TODO Auto-generated method stub
		return null;

	}

	public Value LoadConst(Object constValue) {
		// TODO Auto-generated method stub
		return null;
	}

	public Value Local(String parsedText) {
		// TODO Auto-generated method stub
		return null;
	}

	public Value LoadField(Value obj, int offset) {
		// TODO Auto-generated method stub
		return null;

	}

	public Value Throw(Value e) {
		// TODO Auto-generated method stub
		return null;

	}

	public void Assign(String parsedText, Value r) {
		// TODO Auto-generated method stub

	}

	public void DefClass(KClass typeInfo, ValueList field) {
		// TODO Auto-generated method stub

	}

	public void Call(Value method, ValueList param) {
		// TODO Auto-generated method stub

	}

	public void NewBlock(ValueList evaled) {
		// TODO Auto-generated method stub

	}

	public BasicBlock NewBlock(String label) {
		// TODO Auto-generated method stub
		return null;

	}

	public BasicBlock NewBlock() {
		// TODO Auto-generated method stub
		return null;
	}

	public void Branch(Value cond, BasicBlock thenBB, BasicBlock elseBB) {
		// TODO Auto-generated method stub

	}

	public Object JumpTo(BasicBlock mergeBB) {
		// TODO Auto-generated method stub
		return null;
	}

	public void JumpTo(String label) {
		// TODO Auto-generated method stub

	}

	public void LoadLocal(Value local) {
		// TODO Auto-generated method stub

	}

	public BasicBlock PopLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void PushLabel(String string, BasicBlock newBlock) {
		// TODO Auto-generated method stub

	}

	public void Alloc(KClass typeInfo) {
		// TODO Auto-generated method stub

	}

	public void Return(Value e) {
		// TODO Auto-generated method stub

	}
}

public class KonohaVMCodeGen extends CodeGenerator implements ASTVisitor {

	Eval	Eval;

	public KonohaVMCodeGen() {
		super(null);
		this.Eval = new Eval();
	}

	@Override
	public boolean Visit(TypedNode Node) {
		return Node.Evaluate(this);
	}

	@Override
	public void Prepare(KMethod Method) {
		this.LocalVals.clear();
		this.MethodInfo = Method;
		this.AddLocal(Method.ClassInfo, "this");
	}

	@Override
	public void Prepare(KMethod Method, ArrayList<Local> params) {
		this.Prepare(Method);
		for (int i = 0; i < params.size(); i++) {
			Local local = params.get(i);
			this.AddLocal(local.TypeInfo, local.Name);
		}
	}

	@Override
	public void EnterAnd(AndNode Node) {
	}

	@Override
	public boolean ExitAnd(AndNode Node) {
		Value L = Eval.Get(0);
		Value R = Eval.Get(1);
		Eval.LogicalAnd(L, R);
		return true;
	}

	@Override
	public void EnterOr(OrNode Node) {
	}

	@Override
	public boolean ExitOr(OrNode Node) {
		Value L = Eval.Get(0);
		Value R = Eval.Get(1);
		Eval.LogicalOr(L, R);
		return true;
	}

	@Override
	public void EnterAssign(AssignNode Node) {
		Local local = this.FindLocalVariable(Node.TermToken.ParsedText);
		assert (local != null);
	}

	@Override
	public boolean ExitAssign(AssignNode Node) {
		KToken TermToken = Node.TermToken;
		Value R = Eval.Get(0);
		Eval.Assign(TermToken.ParsedText, R);
		return true;
	}

	@Override
	public void EnterBlock(BlockNode Node) {
	}

	@Override
	public boolean ExitBlock(BlockNode Node) {
		ValueList Evaled = Eval.Get();
		Eval.NewBlock(Evaled);
		return true;
	}

	@Override
	public void EnterBox(BoxNode Node) {
	}

	@Override
	public boolean ExitBox(BoxNode Node) {
		Value E = Eval.Get(0);
		Eval.Box(E);
		return true;
	}

	@Override
	public void EnterConst(ConstNode Node) {
	}

	@Override
	public boolean ExitConst(ConstNode Node) {
		Object ConstValue = Node.ConstValue;
		Eval.LoadConst(ConstValue);
		return true;
	}

	@Override
	public void EnterDefineClass(DefineClassNode Node) {
	}

	@Override
	public boolean ExitDefineClass(DefineClassNode Node) {
		ValueList Field = Eval.Get();
		Eval.DefClass(Node.TypeInfo, Field);
		return true;
	}

	@Override
	public void EnterDef(DefNode Node) {
	}

	@Override
	public boolean ExitDef(DefNode Node) {
		return true;
	}

	@Override
	public void EnterError(ErrorNode Node) {
	}

	@Override
	public boolean ExitError(ErrorNode Node) {
		String ErrorMessage = Node.ErrorMessage;
		Value Err = Eval.LoadConst(ErrorMessage);
		Eval.Throw(Err);
		return true;
	}

	@Override
	public void EnterField(FieldNode Node) {
		Local local = this.FindLocalVariable(Node.TermToken.ParsedText);
		assert (local != null);
	}

	@Override
	public boolean ExitField(FieldNode Node) {
		KToken TermToken = Node.TermToken;
		int Offset = Node.Offset;
		Value Obj = Eval.Local(TermToken.ParsedText);
		Eval.LoadField(Obj, Offset);
		return true;
	}

	@Override
	public void EnterFunction(FunctionNode Node) {
	}

	@Override
	public boolean ExitFunction(FunctionNode Node) {
		KMethod Mtd = Node.Mtd;
		ValueList Param = Eval.Get();
		Value Method = Eval.LoadConst(Mtd);
		KMethod FuncNew = null;
		Value FuncMtd = Eval.LoadConst(FuncNew);
		Param = ValueList.unshift(Param, Method);
		Eval.Call(FuncMtd, Param);
		return true;
	}

	@Override
	public void EnterIf(IfNode Node) {
	}

	@Override
	public boolean ExitIf(IfNode Node) {
		Value Cond = Eval.Get(0);
		BasicBlock ThenBB = (BasicBlock) Eval.Get(1);
		BasicBlock ElseBB = (BasicBlock) Eval.Get(2);
		BasicBlock MergeBB = Eval.NewBlock();
		Eval.Branch(Cond, ThenBB, ElseBB);
		ThenBB.JumpTo(MergeBB);
		ElseBB.JumpTo(MergeBB);
		return true;
	}

	@Override
	public void EnterJump(JumpNode Node) {
	}

	@Override
	public boolean ExitJump(JumpNode Node) {
		String Label = Node.Label;
		Eval.JumpTo(Label);
		return true;
	}

	@Override
	public void EnterLabel(LabelNode Node) {
	}

	@Override
	public boolean ExitLabel(LabelNode Node) {
		String Label = Node.Label;
		Eval.NewBlock(Label);
		return true;
	}

	@Override
	public void EnterLet(LetNode Node) {
		this.AddLocalVarIfNotDefined(Node.TypeInfo, Node.TermToken.ParsedText);
	}

	@Override
	public boolean ExitLet(LetNode Node) {
		KToken TermToken = Node.TermToken;
		Value R = Eval.Get(0);
		BasicBlock B = (BasicBlock) Eval.Get(1);
		Eval.Assign(TermToken.ParsedText, R);
		Eval.JumpTo(B);
		return true;
	}

	@Override
	public void EnterLocal(LocalNode Node) {
		Local local = this.FindLocalVariable(Node.FieldName);
		assert (local != null);
	}

	@Override
	public boolean ExitLocal(LocalNode Node) {
		String FieldName = Node.FieldName;
		Eval.LoadLocal(Eval.Local(FieldName));
		return true;
	}

	@Override
	public void EnterLoop(LoopNode Node) {
		Eval.PushLabel("break", Eval.NewBlock("break"));
		Eval.PushLabel("continue", Eval.NewBlock("continue"));
	}

	@Override
	public boolean ExitLoop(LoopNode Node) {
		Value Cond = Eval.Get(0);
		BasicBlock LoopBB = (BasicBlock) Eval.Get(1);
		BasicBlock Iter = (BasicBlock) Eval.Get(2);
		BasicBlock HeadBB = Eval.PopLabel();
		BasicBlock MergeBB = Eval.PopLabel();
		BasicBlock IterBB = Eval.NewBlock();
		Eval.JumpTo(HeadBB);
		HeadBB.Append(Cond);
		HeadBB.Branch(Cond, LoopBB, MergeBB);
		LoopBB.JumpTo(IterBB);
		IterBB.Append(Iter);
		IterBB.JumpTo(HeadBB);
		return true;
	}

	@Override
	public void EnterMethodCall(MethodCallNode Node) {
	}

	@Override
	public boolean ExitMethodCall(MethodCallNode Node) {
		KMethod Mtd = Node.Method;
		ValueList P = Eval.Get();
		Value Method = Eval.LoadConst(Mtd);
		Eval.Call(Method, P);
		return true;
	}

	@Override
	public void EnterNew(NewNode Node) {
	}

	@Override
	public boolean ExitNew(NewNode Node) {
		Eval.Alloc(Node.TypeInfo);
		return true;
	}

	@Override
	public void EnterNull(NullNode Node) {
	}

	@Override
	public boolean ExitNull(NullNode Node) {
		Eval.LoadConst(null/* FIXME */);
		return true;
	}

	@Override
	public void EnterReturn(ReturnNode Node) {
	}

	@Override
	public boolean ExitReturn(ReturnNode Node) {
		Value E = Eval.Get(0);
		Eval.Return(E);
		return true;
	}

	@Override
	public void EnterSwitch(SwitchNode Node) {
	}

	@Override
	public boolean ExitSwitch(SwitchNode Node) {
		// TypedNode CondExpr = Node.CondExpr;
		// LabelNodeList Labels = Node.Labels;
		// TypedNodeList Blocks = (TypedNodeList) Node.Blocks;
		return true;
	}

	@Override
	public void EnterThrow(ThrowNode Node) {
	}

	@Override
	public boolean ExitThrow(ThrowNode Node) {
		Value E = Eval.Get(0);
		Eval.Throw(E);
		return true;
	}

	@Override
	public void EnterTry(TryNode Node) {
	}

	@Override
	public boolean ExitTry(TryNode Node) {
		// TypedNode TryBlock = Node.TryBlock;
		// TypedNodeList TargetException = (TypedNodeList) Node.TargetException;
		// TypedNodeList CatchBlock = (TypedNodeList) Node.CatchBlock;
		// TypedNode FinallyBlock = Node.FinallyBlock;
		/* BasicBlock TryBB = Eval.Get(0); */
		return true;
	}

}
