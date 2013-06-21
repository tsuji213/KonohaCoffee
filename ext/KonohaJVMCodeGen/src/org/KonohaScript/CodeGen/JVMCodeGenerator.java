package org.KonohaScript.CodeGen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.KonohaScript.KonohaBuilder;
import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaMethodInvoker;
import org.KonohaScript.KonohaObject;
import org.KonohaScript.KonohaType;
import org.KonohaScript.NativeMethodInvoker;
import org.KonohaScript.KLib.KonohaArray;
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
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class CodeGenException extends RuntimeException {
	private static final long	serialVersionUID	= 1L;

	CodeGenException() {
		super();
	}

	CodeGenException(String msg) {
		super(msg);
	}
}

abstract class BinaryOperator {

	abstract void codeGen();

}

class JVMBuilder implements Opcodes {

	MethodVisitor					methodVisitor;
	Stack							stack;
	HashMap<String, BinaryOperator>	binaryOperatorMap;
	private HashMap<String, String>	typeDescriptorMap;
	HashMap<String, String>			methodDescriptorMap;

	JVMBuilder() {
		this.initTypeDescriptorMap();
		this.initBinaryOpcodeMap();
		this.stack = new Stack();
		this.methodDescriptorMap = new HashMap<String, String>();
	}

	class Stack {
		private int	stackTop		= 0;
		private int	maxStackSize	= 0;

		public int getStackTop() {
			return this.stackTop;
		}

		public void setStackTop(int stackTop) {
			this.stackTop = stackTop;
		}

		public int getMaxStackSize() {
			return this.maxStackSize;
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
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.push();
				JVMBuilder.this.methodVisitor.visitInsn(IADD);
			}
		};

		BinaryOperator opSUB = new BinaryOperator() {
			@Override
			void codeGen() {
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.push();
				JVMBuilder.this.methodVisitor.visitInsn(ISUB);
			}
		};

		BinaryOperator opMUL = new BinaryOperator() {
			@Override
			void codeGen() {
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.push();
				JVMBuilder.this.methodVisitor.visitInsn(IMUL);
			}
		};

		BinaryOperator opDIV = new BinaryOperator() {
			@Override
			void codeGen() {
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.push();
				JVMBuilder.this.methodVisitor.visitInsn(IDIV);
			}
		};

		BinaryOperator opREM = new BinaryOperator() {
			@Override
			void codeGen() {
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.push();
				JVMBuilder.this.methodVisitor.visitInsn(IREM);
			}
		};

		BinaryOperator opLT = new BinaryOperator() {
			@Override
			void codeGen() {
				Label FALSE = new Label();
				Label END = new Label();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.push();
				JVMBuilder.this.methodVisitor.visitJumpInsn(IF_ICMPGE, FALSE); // condition
				JVMBuilder.this.methodVisitor.visitInsn(ICONST_1); // true
				JVMBuilder.this.methodVisitor.visitJumpInsn(GOTO, END);
				JVMBuilder.this.methodVisitor.visitLabel(FALSE);
				JVMBuilder.this.methodVisitor.visitInsn(ICONST_0); // false
				JVMBuilder.this.methodVisitor.visitLabel(END);
			}
		};

		BinaryOperator opLE = new BinaryOperator() {
			@Override
			void codeGen() {
				Label FALSE = new Label();
				Label END = new Label();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.push();
				JVMBuilder.this.methodVisitor.visitJumpInsn(IF_ICMPGT, FALSE); // condition
				JVMBuilder.this.methodVisitor.visitInsn(ICONST_1); // true 
				JVMBuilder.this.methodVisitor.visitJumpInsn(GOTO, END);
				JVMBuilder.this.methodVisitor.visitLabel(FALSE);
				JVMBuilder.this.methodVisitor.visitInsn(ICONST_0); // false
				JVMBuilder.this.methodVisitor.visitLabel(END);
			}
		};

		BinaryOperator opGT = new BinaryOperator() {
			@Override
			void codeGen() {
				Label FALSE = new Label();
				Label END = new Label();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.push();
				JVMBuilder.this.methodVisitor.visitJumpInsn(IF_ICMPLE, FALSE); // condition
				JVMBuilder.this.methodVisitor.visitInsn(ICONST_1); // true
				JVMBuilder.this.methodVisitor.visitJumpInsn(GOTO, END);
				JVMBuilder.this.methodVisitor.visitLabel(FALSE);
				JVMBuilder.this.methodVisitor.visitInsn(ICONST_0); // false
				JVMBuilder.this.methodVisitor.visitLabel(END);
			}
		};

		BinaryOperator opGE = new BinaryOperator() {
			@Override
			void codeGen() {
				Label FALSE = new Label();
				Label END = new Label();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.push();
				JVMBuilder.this.methodVisitor.visitJumpInsn(IF_ICMPLT, FALSE); // condition
				JVMBuilder.this.methodVisitor.visitInsn(ICONST_1); // true
				JVMBuilder.this.methodVisitor.visitJumpInsn(GOTO, END);
				JVMBuilder.this.methodVisitor.visitLabel(FALSE);
				JVMBuilder.this.methodVisitor.visitInsn(ICONST_0); // false
				JVMBuilder.this.methodVisitor.visitLabel(END);
			}
		};

		BinaryOperator opEQ = new BinaryOperator() {
			@Override
			void codeGen() {
				Label FALSE = new Label();
				Label END = new Label();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.push();
				JVMBuilder.this.methodVisitor.visitJumpInsn(IF_ICMPNE, FALSE); // condition
				JVMBuilder.this.methodVisitor.visitInsn(ICONST_1); // true
				JVMBuilder.this.methodVisitor.visitJumpInsn(GOTO, END);
				JVMBuilder.this.methodVisitor.visitLabel(FALSE);
				JVMBuilder.this.methodVisitor.visitInsn(ICONST_0); // false
				JVMBuilder.this.methodVisitor.visitLabel(END);
			}
		};

		BinaryOperator opNE = new BinaryOperator() {
			@Override
			void codeGen() {
				Label FALSE = new Label();
				Label END = new Label();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.pop();
				JVMBuilder.this.stack.push();
				JVMBuilder.this.methodVisitor.visitJumpInsn(IF_ICMPEQ, FALSE); // condition
				JVMBuilder.this.methodVisitor.visitInsn(ICONST_1); // true
				JVMBuilder.this.methodVisitor.visitJumpInsn(GOTO, END);
				JVMBuilder.this.methodVisitor.visitLabel(FALSE);
				JVMBuilder.this.methodVisitor.visitInsn(ICONST_0); // false
				JVMBuilder.this.methodVisitor.visitLabel(END);
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
		return "L" + type.ShortClassName + ";"; // FIXME
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
		return this.methodDescriptorMap.get(className + "." + methodName);
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

public class JVMCodeGenerator extends CodeGenerator implements Opcodes, KonohaBuilder {

	private final JVMBuilder					builder;
	private final HashMap<String, ClassWriter>	classWriterMap;

	public JVMCodeGenerator(KonohaMethod MethodInfo) {
		super(MethodInfo);

		this.builder = new JVMBuilder();
		this.classWriterMap = new HashMap<String, ClassWriter>();
		ClassWriter defaultClassWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		defaultClassWriter.visit(V1_5, ACC_PUBLIC, "org/KonohaScript/CodeGen/Script", null, "java/lang/Object", null);
		this.classWriterMap.put("Script", defaultClassWriter);
	}

	public JVMCodeGenerator() {
		this(null);
	}

	public void OutputClassFile(String className, String dir) throws IOException {
		ClassWriter classWriter = this.classWriterMap.get(className);
		classWriter.visitEnd();
		byte[] ba = classWriter.toByteArray();
		File file = new File(dir, className + ".class");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(ba);
		} finally {
			if(fos != null) {
				fos.close();
			}
		}
	}

	@Override
	public void Prepare(KonohaMethod Method) {
		this.LocalVals.clear();
		this.MethodInfo = Method;
		// if(!Method.Is(StaticMethod)) {
		//		this.AddLocal(Method.ClassInfo, "this");
		// }
	}

	@Override
	public void Prepare(KonohaMethod Method, KonohaArray params) {
		this.Prepare(Method);
		for(int i = 0; i < params.size(); i++) {
			Local local = (Local) params.get(i);
			this.AddLocal(local.TypeInfo, local.Name);
		}
	}

	@Override
	public KonohaMethodInvoker Compile(TypedNode Block) {
		if(this.MethodInfo != null && this.MethodInfo.MethodName.length() > 0) {

			// String className = this.MethodInfo.ClassInfo.ShortClassName;
			String className = "Script"; // TODO support user defined class

			ClassWriter cw = this.classWriterMap.get(className);
			String methodName = this.MethodInfo.MethodName;
			String methodDescriptor = this.builder.getMethodDescriptor(this.MethodInfo);
			MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, methodName, methodDescriptor, null, null);
			this.builder.methodDescriptorMap.put(className + "." + methodName, methodDescriptor);
			mv.visitCode();
			this.builder.methodVisitor = mv;
			// TODO fix access modifier
			// if(!Method.Is(StaticMethod)) {
			// 		...
			// }

			this.VisitList(Block.GetHeadNode());

			int maxStack = this.builder.stack.getMaxStackSize();
			int maxLocal = this.LocalVals.size();
			mv.visitMaxs(maxStack, maxLocal);
			mv.visitEnd();
		}

		KonohaMethodInvoker mtd = new NativeMethodInvoker(this.MethodInfo.Param, null/* FIXME */);
		return mtd;
	}

	@Override
	public boolean VisitDefine(DefineNode Node) {
		return true;
	}

	@Override
	public boolean VisitConst(ConstNode Node) {
		Object constValue = Node.ConstValue;
		this.builder.LoadConst(constValue);
		return true;
	}

	@Override
	public boolean VisitNew(NewNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean VisitNull(NullNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean VisitLocal(LocalNode Node) {
		String FieldName = Node.FieldName;
		Local local;
		if((local = this.FindLocalVariable(FieldName)) == null) {
			throw new CodeGenException("local variable not found:" + FieldName);
		}
		this.builder.LoadLocal(local);
		return true;
	}

	@Override
	public boolean VisitGetter(GetterNode Node) {
		// TODO Auto-generated method stub
		Node.BaseNode.Evaluate(this);
		return true;
	}

	private boolean isMethodBinaryOperator(ApplyNode Node) {
		String methodName = Node.Method.MethodName;
		for(String op : this.builder.binaryOperatorMap.keySet()) {
			if(op.equals(methodName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean VisitApply(ApplyNode Node) {
		for(int i = 0; i < Node.Params.size(); i++) {
			TypedNode Param = (TypedNode) Node.Params.get(i);
			Param.Evaluate(this);
		}
		if(this.isMethodBinaryOperator(Node)) {
			String opName = Node.Method.MethodName;
			this.builder.BinaryOp(opName);
		} else {
			int opcode = INVOKESTATIC; // support other opcode
			String methodName = Node.Method.MethodName;
			this.builder.Call(opcode, "Script", methodName); // TODO support other class method
		}
		return true;
	}

	@Override
	public boolean VisitAnd(AndNode Node) {
		// TODO Auto-generated method stub
		Node.LeftNode.Evaluate(this);
		Node.RightNode.Evaluate(this);
		return false;
	}

	@Override
	public boolean VisitOr(OrNode Node) {
		// TODO Auto-generated method stub
		Node.LeftNode.Evaluate(this);
		Node.RightNode.Evaluate(this);

		return false;
	}

	@Override
	public boolean VisitAssign(AssignNode Node) {
		// TODO Auto-generated method stub
		Node.LeftNode.Evaluate(this);
		Node.RightNode.Evaluate(this);
		return true;
	}

	@Override
	public boolean VisitLet(LetNode Node) {
		// TODO Auto-generated method stub
		Node.ValueNode.Evaluate(this);
		VisitList(Node.BlockNode);

		return true;
	}

	@Override
	public boolean VisitIf(IfNode Node) {
		Label ELSE = new Label();
		Label END = new Label();
		MethodVisitor mv = this.builder.methodVisitor;
		Node.CondExpr.Evaluate(this);
		mv.visitJumpInsn(IFEQ, ELSE);

		// Then
		if(Node.ThenNode != null) {
			Node.ThenNode.Evaluate(this);
		}
		mv.visitJumpInsn(GOTO, END);

		// Else
		mv.visitLabel(ELSE);
		if(Node.ElseNode != null) {
			Node.ElseNode.Evaluate(this);
		}

		// End
		mv.visitLabel(END);
		return true;
	}

	@Override
	public boolean VisitSwitch(SwitchNode Node) {
		// TODO Auto-generated method stub
		Node.CondExpr.Evaluate(this);
		for(int i = 0; i < Node.Blocks.size(); i++) {
			TypedNode Block = (TypedNode) Node.Blocks.get(i);
			this.VisitList(Block);
		}
		return true;
	}

	@Override
	public boolean VisitLoop(LoopNode Node) {
		// TODO Auto-generated method stub
		Node.CondExpr.Evaluate(this);
		Node.IterationExpr.Evaluate(this);
		VisitList(Node.LoopBody);
		return true;
	}

	@Override
	public boolean VisitReturn(ReturnNode Node) {
		//FIXME check Node.Expr null check
		Node.Expr.Evaluate(this);

		assert (Node.TypeInfo != null);

		//FIXME (ide) check return type
		MethodVisitor mv = this.builder.methodVisitor;
		if(Node.TypeInfo.ShortClassName.equals("Void")) {
			mv.visitInsn(RETURN);
		} else if(Node.TypeInfo.ShortClassName.equals("Integer")) {
			mv.visitInsn(IRETURN);
		} else {
			mv.visitInsn(ARETURN);
		}
		return true;
	}

	@Override
	public boolean VisitLabel(LabelNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean VisitJump(JumpNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean VisitTry(TryNode Node) {
		// TODO Auto-generated method stub
		this.VisitList(Node.TryBlock);
		for(int i = 0; i < Node.CatchBlock.size(); i++) {
			TypedNode Block = (TypedNode) Node.CatchBlock.get(i);
			TypedNode Exception = (TypedNode) Node.TargetException.get(i);
			this.VisitList(Block);
		}
		this.VisitList(Node.FinallyBlock);

		return true;
	}

	@Override
	public boolean VisitThrow(ThrowNode Node) {
		// TODO Auto-generated method stub
		Node.Expr.Evaluate(this);
		return false;
	}

	@Override
	public boolean VisitFunction(FunctionNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean VisitError(ErrorNode Node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object EvalAtTopLevel(TypedNode Node, KonohaObject GlobalObject) {
		this.Prepare(null);
		KonohaMethodInvoker Invoker = this.Compile(Node);
		return Invoker.Invoke(null);
	}

	@Override
	public KonohaMethodInvoker Build(TypedNode Node, KonohaMethod Method) {
		this.Prepare(Method);
		return this.Compile(Node);
	}

}
