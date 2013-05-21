//package org.KonohaScript.CodeGen;
//
//import java.util.ArrayList;
//
//import org.KonohaScript.KMethod;
//import org.KonohaScript.SyntaxTree.AndNode;
//import org.KonohaScript.SyntaxTree.AssignNode;
//import org.KonohaScript.SyntaxTree.BlockNode;
//import org.KonohaScript.SyntaxTree.BoxNode;
//import org.KonohaScript.SyntaxTree.ConstNode;
//import org.KonohaScript.SyntaxTree.DefineClassNode;
//import org.KonohaScript.SyntaxTree.DoneNode;
//import org.KonohaScript.SyntaxTree.ErrorNode;
//import org.KonohaScript.SyntaxTree.FieldNode;
//import org.KonohaScript.SyntaxTree.FunctionNode;
//import org.KonohaScript.SyntaxTree.IfNode;
//import org.KonohaScript.SyntaxTree.JumpNode;
//import org.KonohaScript.SyntaxTree.LabelNode;
//import org.KonohaScript.SyntaxTree.LetNode;
//import org.KonohaScript.SyntaxTree.LocalNode;
//import org.KonohaScript.SyntaxTree.LoopNode;
//import org.KonohaScript.SyntaxTree.MethodCallNode;
//import org.KonohaScript.SyntaxTree.NewNode;
//import org.KonohaScript.SyntaxTree.NullNode;
//import org.KonohaScript.SyntaxTree.OrNode;
//import org.KonohaScript.SyntaxTree.ReturnNode;
//import org.KonohaScript.SyntaxTree.SwitchNode;
//import org.KonohaScript.SyntaxTree.ThrowNode;
//import org.KonohaScript.SyntaxTree.TryNode;
//import org.KonohaScript.SyntaxTree.TypedNode;
//
//public class LLVMCodeGen extends CodeGenerator implements ASTVisitor {
//
//	public LLVMCodeGen() {
//		super(null);
//	}
//
//	@Override
//	public void Prepare(KMethod Method) {
//		this.LocalVals.clear();
//		this.MethodInfo = Method;
//		this.AddLocal(Method.ClassInfo, "this");
//	}
//
//	@Override
//	public void Prepare(KMethod Method, ArrayList<Local> params) {
//		this.Prepare(Method);
//		for (int i = 0; i < params.size(); i++) {
//			Local local = params.get(i);
//			this.AddLocal(local.TypeInfo, local.Name);
//		}
//	}
//
//	@Override
//	public CompiledMethod Compile(TypedNode Block) {
//		this.Visit(Block);
//		CompiledMethod Mtd = new CompiledMethod();
//		assert (this.Program.size() == 1);
//		String Prog = this.Program.remove(0);
//		if (this.MethodInfo != null) {
//			Local thisNode = this.FindLocalVariable("this");
//			String Signature = this.MethodInfo.GetReturnType(null/* FIXME */).ShortClassName + " " +
//					thisNode.TypeInfo.ShortClassName + "." +
//					this.MethodInfo.MethodName;
//			String Param = "";
//			for (int i = 1; i < this.LocalVals.size(); i++) {
//				Local local = this.GetLocalVariableByIndex(i);
//				if (i != 1) {
//					Param = Param + ", ";
//				}
//				Param = Param + local.TypeInfo.ShortClassName + " " + local.Name;
//			}
//			Prog = Signature + "(" + Param + ")" + "{\n" + Prog + "\n}";
//		}
//		Mtd.CompiledCode = Prog;
//		return Mtd;
//	}
//
//	@Override
//	public boolean Visit(TypedNode Node) {
//		return Node.Evaluate(this);
//	}
//
//	@Override
//	public void EnterDone(DoneNode Node) {
//	}
//
//	@Override
//	public boolean ExitDone(DoneNode Node) {
//		return true;
//	}
//
//	@Override
//	public void EnterConst(ConstNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitConst(ConstNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterNew(NewNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitNew(NewNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterNull(NullNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitNull(NullNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterLocal(LocalNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitLocal(LocalNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterField(FieldNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitField(FieldNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterBox(BoxNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitBox(BoxNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterMethodCall(MethodCallNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitMethodCall(MethodCallNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterAnd(AndNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitAnd(AndNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterOr(OrNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitOr(OrNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterAssign(AssignNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitAssign(AssignNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterLet(LetNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitLet(LetNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterBlock(BlockNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitBlock(BlockNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterIf(IfNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitIf(IfNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterSwitch(SwitchNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitSwitch(SwitchNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterLoop(LoopNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitLoop(LoopNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterReturn(ReturnNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitReturn(ReturnNode Node) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void EnterLabel(LabelNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitLabel(LabelNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterJump(JumpNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitJump(JumpNode Node) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void EnterTry(TryNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitTry(TryNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterThrow(ThrowNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitThrow(ThrowNode Node) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void EnterFunction(FunctionNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitFunction(FunctionNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void EnterError(ErrorNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitError(ErrorNode Node) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void EnterDefineClass(DefineClassNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitDefineClass(DefineClassNode Node) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//}
