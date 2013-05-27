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

import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
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

class BasicBlock extends KonohaIR {
	public static final boolean	DEBUG_MODE	= true;
	String						label;				/* for debug */

	public BasicBlock() {
		super(null);
		this.label = "";
	}

	public BasicBlock(String label) {
		super(null);
		this.label = label;
	}

	public void JumpTo(BasicBlock jumpTo) {
		// TODO Auto-generated method stub

	}

	public void Branch(KonohaIR cond, BasicBlock loopBB, BasicBlock mergeBB) {
		// TODO Auto-generated method stub

	}

	public void Append(KonohaIR cond) {
		// TODO Auto-generated method stub
	}
}

class IRList extends ArrayList<KonohaIR> {
	private static final long	serialVersionUID	= 1L;

	public static IRList unshift(IRList oldList, KonohaIR Val) {
		ArrayList<KonohaIR> newList = new ArrayList<KonohaIR>();
		newList.add(Val);
		for (int i = 0; i < oldList.size(); i++) {
			newList.add(oldList.get(i));
		}
		return (IRList) newList;
	}
}

class TypedNodeList extends ArrayList<TypedNode> {
	private static final long	serialVersionUID	= 1L;

	public static TypedNodeList unshift(TypedNodeList oldList, TypedNode Val) {
		ArrayList<TypedNode> newList = new ArrayList<TypedNode>();
		newList.add(Val);
		for (int i = 0; i < oldList.size(); i++) {
			newList.add(oldList.get(i));
		}
		return (TypedNodeList) newList;
	}
}

class KonohaIRBuilder {
	ArrayList<KonohaIR>	Stack;
	int					StackTop;

	KonohaIRBuilder() {
		this.Stack = new ArrayList<KonohaIR>();
		this.StackTop = 0;
	}

	public KonohaIR Get(int i) {
		KonohaIR ir = this.Stack.get(i);
		assert (ir != null);
		this.Stack.set(i, null);
		return null;
	}

	public IRList Get() {
		IRList list = new IRList();
		for (int i = 0; i < this.Stack.size(); i++) {
			KonohaIR ir = this.Stack.get(i);
			if (ir != null) {
				list.add(ir);
			}
		}
		return list;
	}

	public KonohaIR LogicalAnd(KonohaIR l, KonohaIR r, BlockInfo mergeBB) {
		KonohaIR ir = null;
		BasicBlock HeadBB = l.GetParent();
		BasicBlock ThenBB = r.GetParent();
		BasicBlock MergeBB = mergeBB.Block;
		HeadBB.Append(new OPJumpF(HeadBB, ThenBB, l));
		HeadBB.Append(new OPJump(HeadBB, MergeBB));
		return ir;
	}

	public KonohaIR LogicalOr(KonohaIR l, KonohaIR r) {
		// TODO Auto-generated method stub
		return null;

	}

	public KonohaIR Box(KonohaIR e, KonohaType typeInfo) {
		return null;
	}

	public KonohaIR LoadConst(Object constValue) {
		// TODO Auto-generated method stub
		return null;
	}

	public KonohaIR Local(String parsedText) {
		// TODO Auto-generated method stub
		return null;
	}

	public KonohaIR LoadField(KonohaIR Base, String fieldName) {
		// TODO Auto-generated method stub
		return null;

	}

	public KonohaIR Throw(KonohaIR e) {
		// TODO Auto-generated method stub
		return null;

	}

	public void Assign(KonohaIR l, KonohaIR r) {
		// TODO Auto-generated method stub

	}

	public void Assign(Local l, KonohaIR r) {
		// TODO Auto-generated method stub

	}

	public void DefClass(KonohaType typeInfo, IRList field) {
		// TODO Auto-generated method stub

	}

	public void Call(KonohaIR method, IRList param) {
		// TODO Auto-generated method stub

	}

	public void NewBlock(IRList evaled) {
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

	public void Branch(KonohaIR cond, BasicBlock thenBB, BasicBlock elseBB) {
		// TODO Auto-generated method stub

	}

	public Object JumpTo(BasicBlock mergeBB) {
		// TODO Auto-generated method stub
		return null;
	}

	public void JumpTo(String label) {
		// TODO Auto-generated method stub

	}

	public void LoadLocal(KonohaIR local) {
		// TODO Auto-generated method stub

	}

	public BasicBlock PopLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void PushLabel(String string, BasicBlock newBlock) {
		// TODO Auto-generated method stub

	}

	public void Alloc(KonohaType typeInfo) {
		// TODO Auto-generated method stub

	}

	public void Return(KonohaIR e) {
		// TODO Auto-generated method stub

	}
}

class BlockInfo {
	TypedNode	Node;
	BasicBlock	Block;

	public BlockInfo(TypedNode node, BasicBlock block) {
		this.Node = node;
		this.Block = block;
		if (BasicBlock.DEBUG_MODE) {
			this.Block.label = this.Node.toString() + ":" + this.Block.label;
		}
	}
}

class LocalVariableCollector extends CodeGenerator {
	public LocalVariableCollector() {
		super(null);
	}

	@Override
	public boolean Visit(TypedNode Node) {
		return Node.Evaluate(this);
	}

	@Override
	public void Prepare(KonohaMethod Method) {
		this.LocalVals.clear();
		this.MethodInfo = Method;
		this.AddLocal(Method.ClassInfo, "this");
	}

	@Override
	public void Prepare(KonohaMethod Method, ArrayList<Local> params) {
		this.Prepare(Method);
		for (int i = 0; i < params.size(); i++) {
			Local local = params.get(i);
			this.AddLocal(local.TypeInfo, local.Name);
		}
	}

	@Override
	public boolean ExitDefine(DefineNode Node) {
		return true;
	}

	@Override
	public void EnterConst(ConstNode Node) {
	}

	@Override
	public boolean ExitConst(ConstNode Node) {
		return true;
	}

	@Override
	public void EnterNew(NewNode Node) {
	}

	@Override
	public boolean ExitNew(NewNode Node) {
		return true;
	}

	@Override
	public void EnterNull(NullNode Node) {
	}

	@Override
	public boolean ExitNull(NullNode Node) {
		return true;
	}

	@Override
	public void EnterLocal(LocalNode Node) {
	}

	@Override
	public boolean ExitLocal(LocalNode Node) {
		return true;
	}

	@Override
	public void EnterGetter(GetterNode Node) {
	}

	@Override
	public boolean ExitGetter(GetterNode Node) {
		return true;
	}

	@Override
	public void EnterApply(ApplyNode Node) {
	}

	@Override
	public boolean ExitApply(ApplyNode Node) {
		return true;
	}

	@Override
	public void EnterAnd(AndNode Node) {
	}

	@Override
	public boolean ExitAnd(AndNode Node) {
		return true;
	}

	@Override
	public void EnterOr(OrNode Node) {
	}

	@Override
	public boolean ExitOr(OrNode Node) {
		return true;
	}

	@Override
	public void EnterAssign(AssignNode Node) {
		// FIXME
		Local local = this.FindLocalVariable(Node.SourceToken.ParsedText);
		assert (local != null);
	}

	@Override
	public boolean ExitAssign(AssignNode Node) {
		return true;
	}

	@Override
	public void EnterLet(LetNode Node) {
		this.AddLocal(Node.TypeInfo, Node.VarToken.ParsedText);
	}

	@Override
	public boolean ExitLet(LetNode Node) {
		return true;
	}

	@Override
	public void EnterIf(IfNode Node) {
	}

	@Override
	public boolean ExitIf(IfNode Node) {
		return true;
	}

	@Override
	public void EnterSwitch(SwitchNode Node) {
	}

	@Override
	public boolean ExitSwitch(SwitchNode Node) {
		return true;
	}

	@Override
	public void EnterLoop(LoopNode Node) {
	}

	@Override
	public boolean ExitLoop(LoopNode Node) {
		return true;
	}

	@Override
	public void EnterReturn(ReturnNode Node) {
	}

	@Override
	public boolean ExitReturn(ReturnNode Node) {
		return false;
	}

	@Override
	public void EnterLabel(LabelNode Node) {
	}

	@Override
	public boolean ExitLabel(LabelNode Node) {
		return false;
	}

	@Override
	public void EnterJump(JumpNode Node) {
	}

	@Override
	public boolean ExitJump(JumpNode Node) {
		return false;
	}

	@Override
	public void EnterTry(TryNode Node) {
	}

	@Override
	public boolean ExitTry(TryNode Node) {
		return true;
	}

	@Override
	public void EnterThrow(ThrowNode Node) {
	}

	@Override
	public boolean ExitThrow(ThrowNode Node) {
		return false;
	}

	@Override
	public void EnterFunction(FunctionNode Node) {
	}

	@Override
	public boolean ExitFunction(FunctionNode Node) {
		return true;
	}

	@Override
	public void EnterError(ErrorNode Node) {
	}

	@Override
	public boolean ExitError(ErrorNode Node) {
		return false;
	}

	@Override
	public void EnterDefine(DefineNode Node) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}

public class KonohaVMCodeGen extends CodeGenerator {
	ArrayList<Integer>		Values;
	int						stacktop;
	KonohaIRBuilder			Builder;
	BlockInfo				currentBlock;
	ArrayList<BlockInfo>	blocks;
	LocalVariableCollector	localinfo;

	public KonohaVMCodeGen() {
		super(null);
		this.stacktop = 0;
		this.Values = new ArrayList<Integer>();
		this.Builder = new KonohaIRBuilder();
		this.blocks = new ArrayList<BlockInfo>();
		this.localinfo = new LocalVariableCollector();
	}

	@Override
	public boolean Visit(TypedNode Node) {
		BlockInfo bInfo = this.popBasicBlockIf(Node);
		if (bInfo != null) {
			this.currentBlock = bInfo;
		}
		return Node.Evaluate(this);
	}

	@Override
	public void Prepare(KonohaMethod Method) {
		this.MethodInfo = Method;
		this.AddLocal(Method.ClassInfo, "this");
		this.localinfo.Prepare(Method);
	}

	@Override
	public void Prepare(KonohaMethod Method, ArrayList<Local> params) {
		this.Prepare(Method);
		for (int i = 0; i < params.size(); i++) {
			Local local = params.get(i);
			this.AddLocal(local.TypeInfo, local.Name);
		}
		this.localinfo.Prepare(Method, params);
	}

	@Override
	public CompiledMethod Compile(TypedNode Block) {
		this.localinfo.Visit(Block);
		this.stacktop = this.localinfo.LocalVals.size();
		this.Visit(Block);
		CompiledMethod Mtd = new CompiledMethod(this.MethodInfo);
		// Mtd.CompiledCode = Source;
		return Mtd;
	}

	void pushBlock(BlockInfo binfo) {
		this.blocks.add(binfo);
	}

	BlockInfo popBasicBlockIf(TypedNode Node) {
		for (int i = this.blocks.size() - 1; i > 0; i--) {
			BlockInfo bInfo = this.blocks.get(i);
			if (bInfo != null && bInfo.Node == Node) {
				return this.blocks.remove(i);
			}
		}
		return null;
	}

	BlockInfo popBasicBlock() {
		return this.blocks.remove(this.blocks.size() - 1);
	}

	@Override
	public void EnterAnd(AndNode Node) {
		this.pushBlock(new BlockInfo(Node.LeftNode, new BasicBlock("head")));
		this.pushBlock(new BlockInfo(Node.RightNode, new BasicBlock("then")));
		this.pushBlock(new BlockInfo(null, new BasicBlock("merge")));
	}

	@Override
	public boolean ExitAnd(AndNode Node) {
		BlockInfo mergeBB = this.popBasicBlock();
		KonohaIR L = this.Builder.Get(0);
		KonohaIR R = this.Builder.Get(1);
		this.Builder.LogicalAnd(L, R, mergeBB);
		return true;
	}

	@Override
	public void EnterOr(OrNode Node) {
		this.pushBlock(new BlockInfo(Node.LeftNode, new BasicBlock("head")));
		this.pushBlock(new BlockInfo(Node.RightNode, new BasicBlock("then")));
		this.pushBlock(new BlockInfo(null, new BasicBlock("merge")));
	}

	@Override
	public boolean ExitOr(OrNode Node) {
		KonohaIR L = this.Builder.Get(0);
		KonohaIR R = this.Builder.Get(1);
		this.Builder.LogicalOr(L, R);
		return true;
	}

	@Override
	public void EnterAssign(AssignNode Node) {
	}

	@Override
	public boolean ExitAssign(AssignNode Node) {
		KonohaIR L = this.Builder.Get(0);
		KonohaIR R = this.Builder.Get(0);
		this.Builder.Assign(L, R);
		return true;
	}

	@Override
	public void EnterConst(ConstNode Node) {
	}

	@Override
	public boolean ExitConst(ConstNode Node) {
		Object ConstValue = Node.ConstValue;
		this.Builder.LoadConst(ConstValue);
		return true;
	}

	@Override
	public void EnterDefine(DefineNode Node) {
	}

	@Override
	public boolean ExitDefine(DefineNode Node) {
		return true;
	}

	@Override
	public void EnterError(ErrorNode Node) {
	}

	@Override
	public boolean ExitError(ErrorNode Node) {
		String ErrorMessage = Node.ErrorMessage;
		KonohaIR Err = this.Builder.LoadConst(ErrorMessage);
		this.Builder.Throw(Err);
		return true;
	}

	@Override
	public void EnterGetter(GetterNode Node) {
	}

	@Override
	public boolean ExitGetter(GetterNode Node) {
		KonohaToken TermToken = Node.SourceToken;
		String FieldName = TermToken.ParsedText;
		KonohaIR Base = this.Builder.Get(0);
		this.Builder.LoadField(Base, FieldName);
		return true;
	}

	@Override
	public void EnterFunction(FunctionNode Node) {
	}

	@Override
	public boolean ExitFunction(FunctionNode Node) {
		KonohaMethod Mtd = Node.Mtd;
		IRList Param = this.Builder.Get();
		KonohaIR Method = this.Builder.LoadConst(Mtd);
		KonohaMethod FuncNew = null;
		KonohaIR FuncMtd = this.Builder.LoadConst(FuncNew);
		Param = IRList.unshift(Param, Method);
		this.Builder.Call(FuncMtd, Param);
		return true;
	}

	@Override
	public void EnterIf(IfNode Node) {
	}

	@Override
	public boolean ExitIf(IfNode Node) {
		KonohaIR Cond = this.Builder.Get(0);
		BasicBlock ThenBB = (BasicBlock) this.Builder.Get(1);
		BasicBlock ElseBB = (BasicBlock) this.Builder.Get(2);
		BasicBlock MergeBB = this.Builder.NewBlock();
		this.Builder.Branch(Cond, ThenBB, ElseBB);
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
		this.Builder.JumpTo(Label);
		return true;
	}

	@Override
	public void EnterLabel(LabelNode Node) {
	}

	@Override
	public boolean ExitLabel(LabelNode Node) {
		String Label = Node.Label;
		this.Builder.NewBlock(Label);
		return true;
	}

	@Override
	public void EnterLet(LetNode Node) {
		this.AddLocalVarIfNotDefined(Node.TypeInfo, Node.VarToken.ParsedText);
	}

	@Override
	public boolean ExitLet(LetNode Node) {
		Local local = this.FindLocalVariable(Node.VarToken.ParsedText);
		KonohaIR R = this.Builder.Get(0);
		BasicBlock B = (BasicBlock) this.Builder.Get(1);
		this.Builder.Assign(local, R);
		this.Builder.JumpTo(B);
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
		this.Builder.LoadLocal(this.Builder.Local(FieldName));
		return true;
	}

	@Override
	public void EnterLoop(LoopNode Node) {
		this.Builder.PushLabel("break", this.Builder.NewBlock("break"));
		this.Builder.PushLabel("continue", this.Builder.NewBlock("continue"));
	}

	@Override
	public boolean ExitLoop(LoopNode Node) {
		KonohaIR Cond = this.Builder.Get(0);
		BasicBlock LoopBB = (BasicBlock) this.Builder.Get(1);
		BasicBlock Iter = (BasicBlock) this.Builder.Get(2);
		BasicBlock HeadBB = this.Builder.PopLabel();
		BasicBlock MergeBB = this.Builder.PopLabel();
		BasicBlock IterBB = this.Builder.NewBlock();
		this.Builder.JumpTo(HeadBB);
		HeadBB.Append(Cond);
		HeadBB.Branch(Cond, LoopBB, MergeBB);
		LoopBB.JumpTo(IterBB);
		IterBB.Append(Iter);
		IterBB.JumpTo(HeadBB);
		return true;
	}

	@Override
	public void EnterApply(ApplyNode Node) {
	}

	@Override
	public boolean ExitApply(ApplyNode Node) {
		KonohaMethod Mtd = Node.Method;
		IRList P = this.Builder.Get();
		KonohaIR Method = this.Builder.LoadConst(Mtd);
		this.Builder.Call(Method, P);
		return true;
	}

	@Override
	public void EnterNew(NewNode Node) {
	}

	@Override
	public boolean ExitNew(NewNode Node) {
		this.Builder.Alloc(Node.TypeInfo);
		return true;
	}

	@Override
	public void EnterNull(NullNode Node) {
	}

	@Override
	public boolean ExitNull(NullNode Node) {
		this.Builder.LoadConst(null/* FIXME */);
		return true;
	}

	@Override
	public void EnterReturn(ReturnNode Node) {
	}

	@Override
	public boolean ExitReturn(ReturnNode Node) {
		KonohaIR E = this.Builder.Get(0);
		this.Builder.Return(E);
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
		KonohaIR E = this.Builder.Get(0);
		this.Builder.Throw(E);
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
