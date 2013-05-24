//package org.KonohaScript.CodeGen;
//
////import org.KonohaScript.SyntaxTree.AndNode;
////import org.KonohaScript.SyntaxTree.AssignNode;
////import org.KonohaScript.SyntaxTree.BlockNode;
////import org.KonohaScript.SyntaxTree.BoxNode;
////import org.KonohaScript.SyntaxTree.ConstNode;
////import org.KonohaScript.SyntaxTree.DefineClassNode;
////import org.KonohaScript.SyntaxTree.DoneNode;
////import org.KonohaScript.SyntaxTree.ErrorNode;
////import org.KonohaScript.SyntaxTree.FieldNode;
////import org.KonohaScript.SyntaxTree.FunctionNode;
////import org.KonohaScript.SyntaxTree.IfNode;
////import org.KonohaScript.SyntaxTree.JumpNode;
////import org.KonohaScript.SyntaxTree.LabelNode;
////import org.KonohaScript.SyntaxTree.LetNode;
////import org.KonohaScript.SyntaxTree.LocalNode;
////import org.KonohaScript.SyntaxTree.LoopNode;
////import org.KonohaScript.SyntaxTree.MethodCallNode;
////import org.KonohaScript.SyntaxTree.NewNode;
////import org.KonohaScript.SyntaxTree.NullNode;
////import org.KonohaScript.SyntaxTree.OrNode;
////import org.KonohaScript.SyntaxTree.ReturnNode;
////import org.KonohaScript.SyntaxTree.SwitchNode;
////import org.KonohaScript.SyntaxTree.ThrowNode;
////import org.KonohaScript.SyntaxTree.TryNode;
////import org.KonohaScript.SyntaxTree.TypedNode;
////import org.objectweb.asm.Opcodes;
////import org.objectweb.asm.tree.MethodNode;
////
////class JavaMethod extends MethodNode implements Opcodes {
////}
//
//public class JVMCodeGenerator extends CodeGenerator implements ASTVisitor {
//	public JVMCodeGenerator() {
//		super(null);
//	}
//
//	@Override
//	public CompiledMethod Compile(TypedNode Block) {
//		CompiledMethod Mtd = new CompiledMethod(this.MethodInfo);
//		return null;
//	}
//
//	@Override
//	public boolean Visit(TypedNode Node) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void EnterDone(DoneNode Node) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean ExitDone(DoneNode Node) {
//		// TODO Auto-generated method stub
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
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
//		return false;
//	}
//
//}
