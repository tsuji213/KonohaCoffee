package org.KonohaScript.CodeGen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.KonohaScript.*;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.SyntaxTree.*;
import org.KonohaScript.SyntaxTree.LabelNode;
import org.KonohaScript.SyntaxTree.NodeVisitor.IfNodeAcceptor;
import org.objectweb.asm.*;

class CodeGenException extends RuntimeException {
	CodeGenException() {
		super();
	}

	CodeGenException(String msg) {
		super(msg);
	}
}

class JVMIfNodeAcceptor implements IfNodeAcceptor, Opcodes {

	private JVMBuilder builder;

	public JVMIfNodeAcceptor(JVMBuilder builder) {
		this.builder = builder;
	}

	@Override
	public boolean Invoke(IfNode Node, NodeVisitor Visitor) {
		Label ELSE = new Label();
		Label END = new Label();
		MethodVisitor mv = this.builder.methodVisitor;
		Visitor.EnterIf(Node);
		Visitor.Visit(Node.CondExpr);
		mv.visitJumpInsn(IFEQ, ELSE);
		
		// Then
		if(Node.ThenNode != null) {
			Visitor.Visit(Node.ThenNode);
		}
		mv.visitJumpInsn(GOTO, END);
		
		// Else
		mv.visitLabel(ELSE);
		if(Node.ElseNode != null) {
			Visitor.Visit(Node.ElseNode);
		}
		
		// End
		mv.visitLabel(END);
		
		return true;
	}

}

abstract class BinaryOperator {

	abstract void codeGen();

}

class JVMBuilder implements Opcodes {

	MethodVisitor methodVisitor;
	Stack stack;
	HashMap<String, BinaryOperator> binaryOperatorMap;
	private HashMap<String, String> typeDescriptorMap;
	HashMap<String, String> methodDescriptorMap;

	JVMBuilder() {
		this.initTypeDescriptorMap();
		this.initBinaryOpcodeMap();
		this.stack = new Stack();
		this.methodDescriptorMap = new HashMap<String, String>();
	}	

	class Stack {
		private int stackTop = 0;
		private int maxStackSize = 0;
		
		public int getStackTop() {
			return stackTop;
		}
		
		public void setStackTop(int stackTop) {
			this.stackTop = stackTop;
		}
		
		public int getMaxStackSize() {
			return maxStackSize;
		}
		
		public void setMaxStackSize(int maxStackSize) {
			this.maxStackSize = maxStackSize;
		}
		
		public void push() {
			int st = this.getStackTop() + 1;
			this.setStackTop(st);
			if(st > this.getMaxStackSize()) {
				this.setMaxStackSize(st);
			}
		}
		
		public void pop() {
			this.setStackTop(this.getStackTop() - 1);
		}
	}

	private void initBinaryOpcodeMap() {
		BinaryOperator opADD = new BinaryOperator() {
			@Override
			void codeGen() {
				stack.pop();
				stack.pop();
				stack.push();
				methodVisitor.visitInsn(IADD);
			}
		};
		
		BinaryOperator opSUB = new BinaryOperator() {
			@Override
			void codeGen() {
				stack.pop();
				stack.pop();
				stack.push();
				methodVisitor.visitInsn(ISUB);
			}
		};
			
		BinaryOperator opMUL = new BinaryOperator() {
			@Override
			void codeGen() {
				stack.pop();
				stack.pop();
				stack.push();
				methodVisitor.visitInsn(IMUL);
			}
		};
		
		BinaryOperator opDIV = new BinaryOperator() {
			@Override
			void codeGen() {
				stack.pop();
				stack.pop();
				stack.push();
				methodVisitor.visitInsn(IDIV);
			}
		};
		
		BinaryOperator opREM = new BinaryOperator() {
			@Override
			void codeGen() {
				stack.pop();
				stack.pop();
				stack.push();
				methodVisitor.visitInsn(IREM);
			}
		};
		
		BinaryOperator opLT = new BinaryOperator() {
			@Override
			void codeGen() {
				Label FALSE = new Label();
				Label END = new Label();
				stack.pop();
				stack.pop();
				stack.push();
				methodVisitor.visitJumpInsn(IF_ICMPGE, FALSE); // condition
				methodVisitor.visitInsn(ICONST_1); // true
				methodVisitor.visitJumpInsn(GOTO, END);
				methodVisitor.visitLabel(FALSE);
				methodVisitor.visitInsn(ICONST_0); // false
				methodVisitor.visitLabel(END);
			}
		};		
		
		BinaryOperator opLE = new BinaryOperator() {
			@Override
			void codeGen() {
				Label FALSE = new Label();
				Label END = new Label();
				stack.pop();
				stack.pop();
				stack.push();
				methodVisitor.visitJumpInsn(IF_ICMPGT, FALSE); // condition
				methodVisitor.visitInsn(ICONST_1); // true 
				methodVisitor.visitJumpInsn(GOTO, END);
				methodVisitor.visitLabel(FALSE);
				methodVisitor.visitInsn(ICONST_0); // false
				methodVisitor.visitLabel(END);			
			}
		};
		
		BinaryOperator opGT = new BinaryOperator() {
			@Override
			void codeGen() {
				Label FALSE = new Label();
				Label END = new Label();
				stack.pop();
				stack.pop();
				stack.push();
				methodVisitor.visitJumpInsn(IF_ICMPLE, FALSE); // condition
				methodVisitor.visitInsn(ICONST_1); // true
				methodVisitor.visitJumpInsn(GOTO, END);
				methodVisitor.visitLabel(FALSE);
				methodVisitor.visitInsn(ICONST_0); // false
				methodVisitor.visitLabel(END);			
			}
		};
		
		BinaryOperator opGE = new BinaryOperator() {
			@Override
			void codeGen() {
				Label FALSE = new Label();
				Label END = new Label();
				stack.pop();
				stack.pop();
				stack.push();
				methodVisitor.visitJumpInsn(IF_ICMPLT, FALSE); // condition
				methodVisitor.visitInsn(ICONST_1); // true
				methodVisitor.visitJumpInsn(GOTO, END);
				methodVisitor.visitLabel(FALSE);
				methodVisitor.visitInsn(ICONST_0); // false
				methodVisitor.visitLabel(END);			
			}
		};
		
		BinaryOperator opEQ = new BinaryOperator() {
			@Override
			void codeGen() {
				Label FALSE = new Label();
				Label END = new Label();
				stack.pop();
				stack.pop();
				stack.push();
				methodVisitor.visitJumpInsn(IF_ICMPNE, FALSE); // condition
				methodVisitor.visitInsn(ICONST_1); // true
				methodVisitor.visitJumpInsn(GOTO, END);
				methodVisitor.visitLabel(FALSE);
				methodVisitor.visitInsn(ICONST_0); // false
				methodVisitor.visitLabel(END);			
			}
		};		
		
		BinaryOperator opNE = new BinaryOperator() {
			@Override
			void codeGen() {
				Label FALSE = new Label();
				Label END = new Label();
				stack.pop();
				stack.pop();
				stack.push();
				methodVisitor.visitJumpInsn(IF_ICMPEQ, FALSE); // condition
				methodVisitor.visitInsn(ICONST_1); // true
				methodVisitor.visitJumpInsn(GOTO, END);
				methodVisitor.visitLabel(FALSE);
				methodVisitor.visitInsn(ICONST_0); // false
				methodVisitor.visitLabel(END);			
			}
		};
		
		this.binaryOperatorMap = new HashMap<String, BinaryOperator>();
		this.binaryOperatorMap.put("+", opADD);
		this.binaryOperatorMap.put("-", opSUB);
		this.binaryOperatorMap.put("*", opMUL);
		this.binaryOperatorMap.put("/", opDIV);
		this.binaryOperatorMap.put("%", opREM);
		this.binaryOperatorMap.put("<", opLT);
		this.binaryOperatorMap.put("<=", opLE);
		this.binaryOperatorMap.put(">", opGT);
		this.binaryOperatorMap.put(">=", opGE);
		this.binaryOperatorMap.put("==", opEQ);
		this.binaryOperatorMap.put("!=", opNE);
		// add other binary operator
	}

	private void initTypeDescriptorMap() {
		this.typeDescriptorMap = new HashMap<String, String>();
		this.typeDescriptorMap.put("Void", Type.getType(void.class).getDescriptor());
		this.typeDescriptorMap.put("Boolean", Type.getType(boolean.class).getDescriptor());
		this.typeDescriptorMap.put("Integer", Type.getType(int.class).getDescriptor());
		this.typeDescriptorMap.put("Object", Type.getType(Object.class).getDescriptor());
		// other class	
	}

	private String getTypeDescriptor(KonohaType type) {
		String descriptor;
		if((descriptor = this.typeDescriptorMap.get(type.ShortClassName)) != null) {
			return descriptor;
		}
		return "L"+type.ShortClassName+";"; // FIXME
	}

	String getMethodDescriptor(KonohaMethod method) {
		KonohaType returnType = method.GetReturnType(null);
		ArrayList<KonohaType> paramTypes = new ArrayList<KonohaType>(Arrays.asList(method.Param.Types));
		paramTypes.remove(0);
		StringBuilder signature = new StringBuilder();
		signature.append("(");
		for(KonohaType param : paramTypes) {
			signature.append(this.getTypeDescriptor(param));
		}
		signature.append(")");
		signature.append(this.getTypeDescriptor(returnType));
		return signature.toString();
	}

	String getMethodDescriptor(String className, String methodName) {
		return this.methodDescriptorMap.get(className+"."+methodName);
	}

	void LoadLocal(Local local) {
		KonohaType type = local.TypeInfo;
		//TODO check KonohaType -> Type
		
		this.stack.push();
		this.methodVisitor.visitVarInsn(ILOAD, local.Index);
		// this.asmctx.getMethodVisitor().visitVarInsn(type.getOpcode(ILOAD), local.Index);
	}

	void StoreLocal(Local local) {
		KonohaType type = local.TypeInfo;
		//TODO check KonohaType -> Type
		
		this.stack.pop();
		this.methodVisitor.visitVarInsn(ISTORE, local.Index);
		// this.asmctx.getMethodVisitor().visitVarInsn(type.getOpcode(ISTORE), local.Index);
	}

	void LoadConst(Object o) {
		this.stack.push();
		this.methodVisitor.visitLdcInsn(o);
	}

	void BinaryOp(String opName) {
		this.binaryOperatorMap.get(opName).codeGen();
	}

	void Call(int opcode, String className, String methodName) {
		String owner = "org/KonohaScript/CodeGen/" + className;
		String methodDescriptor = this.getMethodDescriptor(className, methodName);
		this.methodVisitor.visitMethodInsn(opcode, owner, methodName, methodDescriptor);
	}

}

public class JVMCodeGenerator extends CodeGenerator implements Opcodes {

	private JVMBuilder builder;
	private HashMap<String, ClassWriter> classWriterMap;

	public JVMCodeGenerator(KonohaMethod MethodInfo) {
		super(MethodInfo);
		
		this.builder = new JVMBuilder();
		this.classWriterMap = new HashMap<String, ClassWriter>();
		ClassWriter defaultClassWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		defaultClassWriter.visit(V1_5, ACC_PUBLIC, "org/KonohaScript/CodeGen/Script", null, "java/lang/Object", null);
		this.classWriterMap.put("Script", defaultClassWriter);
		
		this.IfNodeAcceptor = new JVMIfNodeAcceptor(this.builder);
	}

	public JVMCodeGenerator() {
		this(null);
	}

	public void OutputClassFile(String className, String dir) throws IOException {
		ClassWriter classWriter = this.classWriterMap.get(className);
		classWriter.visitEnd();
		byte[] ba = classWriter.toByteArray();
		File file = new File(dir, className+".class");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(ba);
		} finally {
			if(fos != null) fos.close();
		}
	}	

	public void Prepare(KonohaMethod Method) {
		this.LocalVals.clear();
		this.MethodInfo = Method;
		// if(!Method.Is(StaticMethod)) {
		//		this.AddLocal(Method.ClassInfo, "this");
		// }
	}

	public void Prepare(KonohaMethod Method, KonohaArray params) {
		this.Prepare(Method);
		for(int i = 0; i < params.size(); i++) {
			Local local = (Local)params.get(i);
			this.AddLocal(local.TypeInfo, local.Name);
		}	
	}

	@Override
	public CompiledMethod Compile(TypedNode Block) {
		if(this.MethodInfo != null && this.MethodInfo.MethodName.length() > 0) {
			
			// String className = this.MethodInfo.ClassInfo.ShortClassName;
			String className = "Script"; // TODO support user defined class
			
			ClassWriter cw = this.classWriterMap.get(className);
			String methodName = this.MethodInfo.MethodName;
			String methodDescriptor = this.builder.getMethodDescriptor(this.MethodInfo);
			MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, methodName, methodDescriptor, null, null);
			this.builder.methodDescriptorMap.put(className+"."+methodName, methodDescriptor);
			mv.visitCode();
			this.builder.methodVisitor = mv;
			// TODO fix access modifier
			// if(!Method.Is(StaticMethod)) {
			// 		...
			// }
			
			this.VisitBlock(Block.GetHeadNode());
			
			int maxStack = this.builder.stack.getMaxStackSize();
			int maxLocal = this.LocalVals.size();
			mv.visitMaxs(maxStack, maxLocal);
			mv.visitEnd();
		}
		
		CompiledMethod mtd = new CompiledMethod(this.MethodInfo);
		mtd.CompiledCode = "don't support";
		return mtd;
	}

	@Override
	public boolean Visit(TypedNode Node) {
		return Node.Evaluate(this);
	}

	public boolean VisitBlock(TypedNode Node){
		boolean ret = true;
		if(Node != null){
			ret &= this.Visit(Node);
			for(TypedNode n = Node.NextNode; ret && n != null; n = n.NextNode){
				ret &= this.Visit(n);
			}
		}
		return ret;
	}	

	@Override
	public void EnterDefine(DefineNode Node) {
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
		Object constValue = Node.ConstValue;
		this.builder.LoadConst(constValue);
		return true;
	}

	@Override
	public void EnterNew(NewNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitNew(NewNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterNull(NullNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitNull(NullNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterLocal(LocalNode Node) {
		Local local = this.FindLocalVariable(Node.FieldName);
		assert(local != null);
	}

	@Override
	public boolean ExitLocal(LocalNode Node) {
		String FieldName = Node.FieldName;
		Local local;
		if((local = this.FindLocalVariable(FieldName)) == null) {
			throw new CodeGenException("local variable not found:" + FieldName);
		}
		this.builder.LoadLocal(local);
		return true;
	}

	@Override
	public void EnterGetter(GetterNode Node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean ExitGetter(GetterNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterApply(ApplyNode Node) {
	}

	private boolean isMethodBinaryOperator(ApplyNode Node) {
		String methodName = Node.Method.MethodName;
		for(String op : this.builder.binaryOperatorMap.keySet()) {
			if(op.equals(methodName))
				return true;
		}
		return false;
	}

	@Override
	public boolean ExitApply(ApplyNode Node) {
		if(this.isMethodBinaryOperator(Node)) {
			String opName = Node.Method.MethodName;
			this.builder.BinaryOp(opName);
		}
		else {
			int opcode = INVOKESTATIC; // support other opcode
			String methodName =  Node.Method.MethodName;
			this.builder.Call(opcode, "Script", methodName); // TODO support other class method
		}
		return true;
	}

	@Override
	public void EnterAnd(AndNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitAnd(AndNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterOr(OrNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitOr(OrNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterAssign(AssignNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitAssign(AssignNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterLet(LetNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitLet(LetNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterIf(IfNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitIf(IfNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterSwitch(SwitchNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitSwitch(SwitchNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterLoop(LoopNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitLoop(LoopNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterReturn(ReturnNode Node) {
	}

	@Override
	public boolean ExitReturn(ReturnNode Node) {
		assert(Node.TypeInfo != null);
		
		MethodVisitor mv = this.builder.methodVisitor;
		if(Node.TypeInfo.ShortClassName.equals("Void")) {
			mv.visitInsn(RETURN);
		}
		else if(Node.TypeInfo.ShortClassName.equals("Integer")) {
			mv.visitInsn(IRETURN);
		}
		else {
			mv.visitInsn(ARETURN);
		}
		return true;
	}

	@Override
	public void EnterLabel(LabelNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitLabel(LabelNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterJump(JumpNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitJump(JumpNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterTry(TryNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitTry(TryNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterThrow(ThrowNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitThrow(ThrowNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterFunction(FunctionNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitFunction(FunctionNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void EnterError(ErrorNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ExitError(ErrorNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

}
