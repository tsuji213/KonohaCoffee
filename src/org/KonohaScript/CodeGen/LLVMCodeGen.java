 package org.KonohaScript.CodeGen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.KonohaScript.KonohaMethod;
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
import org.KonohaScript.SyntaxTree.NodeVisitor;
import org.KonohaScript.SyntaxTree.NullNode;
import org.KonohaScript.SyntaxTree.OrNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.SwitchNode;
import org.KonohaScript.SyntaxTree.ThrowNode;
import org.KonohaScript.SyntaxTree.TryNode;
import org.KonohaScript.SyntaxTree.TypedNode;
import org.jllvm.*;
import org.jllvm.bindings.LLVMRealPredicate;
import org.jllvm.bindings.LLVMIntPredicate;

import sun.font.CreatedFontTracker;

public class LLVMCodeGen extends SourceCodeGen implements NodeVisitor {
	private LLVMBuilder builder;
	private Stack<LLVMBasicBlock> bblockStack;
	private Stack<LLVMValue> valueStack;
	
	public LLVMCodeGen() {
		super(null);
		this.builder = new LLVMBuilder();
		this.bblockStack = new Stack<LLVMBasicBlock>();
		this.valueStack = new Stack<LLVMValue>();
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
	public CompiledMethod Compile(TypedNode Block) { 
		CompiledMethod Mtd = new CompiledMethod(this.MethodInfo);
		
		if (this.MethodInfo != null && this.MethodInfo.MethodName.length() > 0) {
			String methodName = this.MethodInfo.MethodName;
			int argsSize = this.LocalVals.size();
			LLVMType[] argsType = new LLVMType[argsSize];
			
			for (int i = 0; i < argsSize; i++) {
				Local local = this.GetLocalVariableByIndex(i);
				argsType[i] = this.builder.convertTypeNameToLLVMType(local.TypeInfo.ShortClassName);
				this.builder.setArgument(local.Name, i);
			}
			LLVMType retType = 
					this.builder.convertTypeNameToLLVMType(this.MethodInfo.ClassInfo.ShortClassName);
			this.builder.createFunction(methodName, retType, argsType);
		} else {
			LLVMType[] argsType = {new LLVMVoidType()};
			this.builder.createFunction("main", new LLVMVoidType(), argsType);
			if (!Block.getClass().equals("org.KonohaScript.SyntaxTree.BlockNode")) {
				this.builder.createBasicBlock("bblock");
			}
		}
		
		this.Visit(Block);
		Mtd.CompiledCode = "dont support";
		this.builder.dump();
		
		return Mtd;
	}

	@Override
	public boolean ExitDefine(DefineNode Node) { //TODO:
		return true;
	}

	@Override 
	public boolean ExitConst(ConstNode Node) { 
		String typeName = Node.TypeInfo.ShortClassName;
		Object value = Node.ConstValue;
		LLVMValue constValue = 
				this.builder.createLocalVariable(this.builder.createConstValue(typeName, value), "const");
		this.valueStack.push(constValue);
		
		return true;
	}

	@Override
	public boolean ExitNew(NewNode Node) { 
		LLVMValue heapValue = this.builder.createHeapAllocationInstruction(Node.TypeInfo.ShortClassName);
		this.valueStack.push(heapValue);
		return true;
	}

	@Override
	public boolean ExitNull(NullNode Node) { 
		LLVMValue nullValue = this.builder.createNullValue(Node.TypeInfo.ShortClassName);
		this.valueStack.push(nullValue);
		return true;
	}

	@Override
	public void EnterLocal(LocalNode Node) { 
		this.AddLocalVarIfNotDefined(Node.TypeInfo, Node.FieldName);
	}

	@Override
	public boolean ExitLocal(LocalNode Node) { //TODO: support scope
		LLVMArgument arg = this.builder.getArgument(Node.FieldName);
		LLVMValue value =  this.builder.createLocalVariable(arg, Node.FieldName);
		this.valueStack.push(value);
		
		return true;
	}

	@Override
	public void EnterField(GetterNode Node) {
		Local local = this.FindLocalVariable(Node.SourceToken.ParsedText);
		assert (local != null);
	}

	@Override
	public boolean ExitField(GetterNode Node) { //TODO: 
		// String Expr = Node.TermToken.ParsedText;
		// push(Expr + "." + Node.TypeInfo.FieldNames.get(Node.Xindex));
		// push(Expr);
		// FIXME
		//this.push(Node.SourceToken.ParsedText);
		return true;

	}

	@Override
	public boolean ExitApply(ApplyNode Node) { //TODO
//		String methodName = Node.Method.MethodName;
//		if (this.isMethodBinaryOperator(Node)) {
//			String params = this.pop();
//			String thisNode = this.pop();
//			this.push(thisNode + " " + methodName + " " + params);
//		} else {
//			String params = "("
//					+ this.PopNReverseAndJoin(Node.Params.size() - 1, ", ")
//					+ ")";
//			String thisNode = this.pop();
//			this.push(thisNode + "." + methodName + params);
//		}
		return true;
	}

//	@Override
//	public boolean ExitMethodCall(MethodCallNode Node) { //TODO: support unary operator
//		String methodName = Node.Method.MethodName;
//		LLVMValue retValue = null;
//		if (this.isMethodBinaryOperator(Node)) {
//			LLVMValue rightValue = this.valueStack.pop();
//			LLVMValue leftValue = this.valueStack.pop();
//			retValue = this.builder.createBinaryOpInstruction(methodName, leftValue, rightValue, "bopRet");
//		} else {
//			int size = Node.Params.size();
//			LLVMValue[] params = new LLVMValue[size];
//			for (int i = size - 1; i > -1; i--) {
//				params[i] = this.valueStack.pop();
//			}
//			retValue = this.builder.createCallInstruction(methodName, params, "methodRet");
//		}
//		this.valueStack.push(retValue);
//		return true;
//	}

	@Override
	public boolean ExitAnd(AndNode Node) { //TODO: 
//		String Right = this.pop();
//		String Left = this.pop();
//		this.push(Left + " && " + Right);
		return true;
	}

	@Override
	public boolean ExitOr(OrNode Node) { //TODO: 
//		String Right = this.pop();
//		String Left = this.pop();
//		this.push(Left + " || " + Right);
		return true;
	}

	@Override
	public void EnterAssign(AssignNode Node) { //TODO: 
//		this.AddLocalVarIfNotDefined(Node.TypeInfo, Node.TermToken.ParsedText);
	}

	@Override
	public boolean ExitAssign(AssignNode Node) {
//		String Right = this.pop();
//		this.push((this.UseLetKeyword ? "let " : "var ") + Node.TermToken.ParsedText + " = " + Right);
		return true;
	}

	@Override
	public void EnterLet(LetNode Node) { //TODO: 
//		this.AddLocalVarIfNotDefined(Node.TypeInfo, Node.TermToken.ParsedText);
	}

	@Override
	public boolean ExitLet(LetNode Node) { //TODO: 
//		String Block = this.pop();
//		String Right = this.pop();
//		this.push(Node.TermToken.ParsedText + " = " + Right + Block);
		return true;
	}
	
	@Override
	public void EnterIf(IfNode Node) { //FIXME
		this.bblockStack.add(this.builder.getCurrentBBlock());
	}
	
	@Override
	public boolean ExitIf(IfNode Node) { //FIXME
		LLVMBasicBlock elseBlock = this.bblockStack.pop();
		LLVMBasicBlock thenBlock = this.bblockStack.pop();
		LLVMBasicBlock currentBlock = this.bblockStack.pop();
		LLVMValue condition = this.valueStack.pop();
		
		this.builder.changeCurrentBBlock(currentBlock);
		this.builder.createIfElseInstruction(condition, thenBlock, elseBlock);
		
		return true;
	}

	@Override
	public void EnterSwitch(SwitchNode Node) {
		this.bblockStack.add(this.builder.getCurrentBBlock());
	}
	
	@Override
	public boolean ExitSwitch(SwitchNode Node) { //TODO: 
//		int Size = Node.Labels.size();
//		String Exprs = "";
//		for (int i = 0; i < Size; i = i + 1) {
//			String Label = Node.Labels.get(Size - i);
//			String Block = this.pop();
//			Exprs = "case " + Label + ":" + Block + Exprs;
//		}
//		String CondExpr = this.pop();
//		this.push("switch (" + CondExpr + ") {" + Exprs + "}");
		return true;
	}

	@Override
	public boolean ExitLoop(LoopNode Node) { //TODO: 
//		String LoopBody = this.pop();
//		String IterExpr = this.pop();
//		String CondExpr = this.pop();
//		this.push("while (" + CondExpr + ") {" + LoopBody + IterExpr + "}");
		return true;
	}

	@Override
	public boolean ExitReturn(ReturnNode Node) { 
		this.builder.createReturnInstruction(this.valueStack.pop());
		return false;
	}

	@Override
	public boolean ExitLabel(LabelNode Node) { //TODO: 
//		String Label = Node.Label;
//		if (Label.compareTo("continue") == 0) {
//			this.push("");
//		} else if (Label.compareTo("continue") == 0) {
//			this.push("");
//		} else {
//			this.push(Label + ":");
//		}
		return true;
	}

	@Override
	public boolean ExitJump(JumpNode Node) { //TODO: 
//		String Label = Node.Label;
//		if (Label.compareTo("continue") == 0) {
//			this.push("continue;");
//		} else if (Label.compareTo("continue") == 0) {
//			this.push("break;");
//		} else {
//			this.push("goto " + Label);
//		}
		return false;
	}

	@Override
	public boolean ExitTry(TryNode Node) { //TODO: 
//		String FinallyBlock = this.pop();
//		String CatchBlocks = "";
//		for (int i = 0; i < Node.CatchBlock.size(); i = i + 1) {
//			String Block = this.pop();
//			CatchBlocks = "catch() " + Block + CatchBlocks;
//		}
//		String TryBlock = this.pop();
//		this.push("try " + TryBlock + "" + CatchBlocks + FinallyBlock);
		return true;
	}

	@Override
	public boolean ExitThrow(ThrowNode Node) { //TODO: 
//		String Expr = this.pop();
//		this.push("throw " + Expr + ";");
		return false;
	}

	@Override
	public boolean ExitFunction(FunctionNode Node) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean ExitError(ErrorNode Node) { //TODO: 
//		String Expr = this.pop();
//		this.push("throw new Exception(" + Expr + ";");
		return false;
	}

}

class LLVMBuilder {
	private final int intLength = 64;
	
	private LLVMModule module;
	private LLVMInstructionBuilder builder;
	private LLVMBasicBlock currentBBlock;
	private LLVMFunction currentFunc;
	
	private HashMap<String, Integer> argMap;
	
	public LLVMBuilder() {
		System.loadLibrary("jllvm");	
		module = new LLVMModule("top", LLVMContext.getGlobalContext());
		builder = new LLVMInstructionBuilder();
		currentFunc = null;
		argMap = new HashMap<String, Integer>();
		
		defineEmbeddedMethod();
	}
	
	public void dump() {
		module.dump();
	}
	
	public String toString() {
		return module.toString();
	}
	
	public void setArgument(String argName, int index) {
		this.argMap.put(argName, index);
	}
	
	public void changeCurrentFunction(LLVMFunction func) {
		currentFunc = func;
	}
	
	public void changeCurrentBBlock(LLVMBasicBlock bblock) {
		currentBBlock = bblock;
		builder.positionBuilderAtEnd(bblock);
	}
	
	public LLVMBasicBlock getCurrentBBlock() {
		return currentBBlock;
	}
	
	private LLVMFunction createFunctionAbst(String funcName, LLVMType retType, LLVMType[] argsType, boolean isVarArg) {
		LLVMFunctionType funcType = new LLVMFunctionType(retType, argsType, isVarArg);
		LLVMFunction func = new LLVMFunction(this.module, funcName, funcType);	
		changeCurrentFunction(func);
		
		return func;
	}
	
	public LLVMFunction createFunction(String funcName, LLVMType retType, LLVMType[] argsType) {
		return createFunctionAbst(funcName, retType, argsType, false);
	}
	
	public LLVMFunction createVariableFunction(String funcName, LLVMType retType, LLVMType[] argsType) {
		return createFunctionAbst(funcName, retType, argsType, true);
	}
	
	public LLVMBasicBlock createBasicBlock(String bblockName) {
		LLVMBasicBlock bblock = currentFunc.appendBasicBlock(bblockName);
		changeCurrentBBlock(bblock);
		return bblock;
	}
	
	//TODO: support user defined class 
	public LLVMValue createConstValue(String typeName, Object value) {
		LLVMType type = convertTypeNameToLLVMType(typeName);
		if (typeName.equals("Integer")) {
			long intValue = (Integer)value;
			return LLVMConstantInteger.constantInteger((LLVMIntegerType)type, intValue, false);
//		} else if (typeName.equals("Float")) {	//FIXME
//			double floatValue = (Double)value;
//			return new LLVMConstantReal(new LLVMFloatType(), floatValue);
		} else if (typeName.equals("Boolean")) {
			boolean boolValue = (Boolean)value;
			return new LLVMConstantBoolean(boolValue);
		} else if (typeName.equals("String")) {
			String strValue = (String)value;
			return new LLVMConstantString(strValue, true);
		}
		
		return null;
	}
	
	public LLVMValue createNullValue(String typeName) { //TODO: support void null 
		if (typeName.equals("Void")) {
//			LLVMValue[] arg = {createConstValue("Integer", 0)};
//			LLVMValue voidValue = createCallInstruction("$getVoidNull", arg, "voidRet");
//			return createLocalVariable(voidValue, "null");
//			
			LLVMValue constNull = createConstValue("Integer", 0);
			createLocalVariable(constNull, "null");	
			return null;
		} else {
			LLVMValue constNull = createConstValue(typeName, 0);
			return createLocalVariable(constNull, "null");	
		}
	}
	
	public void createReturnInstruction(LLVMValue retValue) {
		new LLVMReturnInstruction(builder, retValue);
	}
	
	public void createIfElseInstruction(LLVMValue condition, LLVMBasicBlock thenBlock, LLVMBasicBlock elseBlock) {
		LLVMBasicBlock currentBlock = getCurrentBBlock();	
		LLVMBasicBlock endBlock = createBasicBlock("end");
		
		// else block
		changeCurrentBBlock(elseBlock);
		createBranchInstruction(endBlock);
		
		//then block
		changeCurrentBBlock(thenBlock);
		createBranchInstruction(endBlock);		
		
		changeCurrentBBlock(currentBlock);
		new LLVMBranchInstruction(builder, condition, thenBlock, elseBlock);
		
		changeCurrentBBlock(endBlock);
	}
	
	public void createBranchInstruction(LLVMBasicBlock destBlock) {
		new LLVMBranchInstruction(builder, destBlock);
	}
	
	public LLVMType convertTypeNameToLLVMType(String typeName) { //TODO: support user defined class
		if (typeName.equals("Integer")) {
			return new LLVMIntegerType(intLength);
		} else if (typeName.equals("Float")) {
			return new LLVMFloatType();
		} else if (typeName.equals("Void")) {
			return new LLVMVoidType();
		} else if (typeName.equals("Boolean")) {
			return new LLVMIntegerType(1);
		} else if (typeName.equals("String")) {
			return new LLVMPointerType(new LLVMIntegerType(8), 0);
		} else if (typeName.equals("Object")) { //FIXME
			return new LLVMIntegerType(intLength);
		}
		return null;
	}
	
	public LLVMArgument getArgument(String argName) {
		return this.currentFunc.getParameter(this.argMap.get(argName));
	}
	
	public LLVMValue createLocalVariable(LLVMValue initValue, String varName) {
		LLVMStackAllocation stack = new LLVMStackAllocation(builder, initValue.typeOf(), null, varName);	
		new LLVMStoreInstruction(builder, initValue, stack);
		return stack;
	}
	
	//TODO: support float value at comparison instruction
	public LLVMValue createBinaryOpInstruction(String opName, LLVMValue leftValue, LLVMValue rightValue, String retName) {
		//binaryOpList	= { "+", "-", "*", "/", "<", "<=", ">", ">=", "==", "!=", "&&", "||", "&", "|", "^", "<<", ">>" }
		if (opName.equals("+")) {
			return new LLVMAddInstruction(builder, leftValue, rightValue, false, retName);
		} else if (opName.equals("-")) {
			return new LLVMSubtractInstruction(builder, leftValue, rightValue, false, retName);
		} else if (opName.equals("*")) {
			return new LLVMMultiplyInstruction(builder, leftValue, rightValue, false, retName);
		} else if (opName.equals("/")) {	//FIXME
		} else if (opName.equals("<")) {
			LLVMIntPredicate predicate = LLVMIntPredicate.LLVMIntSLT;
			return new LLVMIntegerComparison(builder, predicate, leftValue, rightValue, retName);
		} else if (opName.equals("<=")) {
			LLVMIntPredicate predicate = LLVMIntPredicate.LLVMIntSLE;
			return new LLVMIntegerComparison(builder, predicate, leftValue, rightValue, retName);
		} else if (opName.equals(">")) {
			LLVMIntPredicate predicate = LLVMIntPredicate.LLVMIntSGT;
			return new LLVMIntegerComparison(builder, predicate, leftValue, rightValue, retName);
		} else if (opName.equals(">=")) {
			LLVMIntPredicate predicate = LLVMIntPredicate.LLVMIntSGE;
			return new LLVMIntegerComparison(builder, predicate, leftValue, rightValue, retName);
		} else if (opName.equals("==")) {
			LLVMIntPredicate predicate = LLVMIntPredicate.LLVMIntEQ;
			return new LLVMIntegerComparison(builder, predicate, leftValue, rightValue, retName);
		} else if (opName.equals("!=")) {
			LLVMIntPredicate predicate = LLVMIntPredicate.LLVMIntNE;
			return new LLVMIntegerComparison(builder, predicate, leftValue, rightValue, retName);
		} else if (opName.equals("&&")) { //FIXME
		} else if (opName.equals("||")) { //FIXME
		} else if (opName.equals("&")) {
			LLVMBinaryBitwiseInstruction.BinaryBitwiseInstructionType insType = 
					LLVMBinaryBitwiseInstruction.BinaryBitwiseInstructionType.AND;
			return new LLVMBinaryBitwiseInstruction(insType, builder, leftValue, rightValue, retName);
		} else if (opName.equals("|")) {
			LLVMBinaryBitwiseInstruction.BinaryBitwiseInstructionType insType = 
					LLVMBinaryBitwiseInstruction.BinaryBitwiseInstructionType.OR;
			return new LLVMBinaryBitwiseInstruction(insType, builder, leftValue, rightValue, retName);
		} else if (opName.equals("^")) {
			LLVMBinaryBitwiseInstruction.BinaryBitwiseInstructionType insType = 
					LLVMBinaryBitwiseInstruction.BinaryBitwiseInstructionType.XOR;
			return new LLVMBinaryBitwiseInstruction(insType, builder, leftValue, rightValue, retName);
		} else if (opName.equals("<<")) {
		} else if (opName.equals(">>")) {
		} 
		return null;
	}
	
	public LLVMValue createCallInstruction(String funcName, LLVMValue[] args, String retName) {
		LLVMFunction func = module.getNamedFunction(funcName);
		return new LLVMCallInstruction(builder, func, args, retName);
	}
	
	//TODO: support array
	public LLVMValue createHeapAllocationInstruction(String typeName) {
		LLVMType type = convertTypeNameToLLVMType(typeName);
		return new LLVMHeapAllocation(builder, type, null, "heap");
	}
	
	// embedded method definition
	private void defineEmbeddedMethod() {
		defineMethod_p();
		defineMethod_toString();
		defineMethod_$getVoidNull();
	}
	
	private void defineMethod_p() { //FIXME
		// define puts function
		LLVMType[] argsType_printf = {new LLVMPointerType(new LLVMIntegerType(8), 0)};
		createVariableFunction("printf", new LLVMIntegerType(32), argsType_printf);
		
		//define p method
		LLVMType[] argsType_p = {new LLVMVoidType(), new LLVMPointerType(new LLVMIntegerType(8), 0)};
		createFunction("p", new LLVMVoidType(), argsType_p);
		createBasicBlock("bblock");
		LLVMArgument arg = currentFunc.getParameter(1);
		LLVMValue strValue = createLocalVariable(arg, "str");
		
		LLVMValue format = createLocalVariable(new LLVMConstantString("%s\n", true), "format");
		//new LLVMGetElementPointerInstruction(builder, format, format, "format");
	}
	
	private void defineMethod_toString() {	//FIXME
		LLVMType[] argsType = {new LLVMIntegerType(intLength), new LLVMIntegerType(intLength)};
		createFunction("toString", new LLVMPointerType(new LLVMIntegerType(8), 0), argsType);
	}	
	
	private void defineMethod_$getVoidNull() {
		LLVMType[] argsType = {new LLVMIntegerType(intLength)};
		createFunction("$getVoidNull", new LLVMVoidType(), argsType);
		createBasicBlock("bblock");
		createReturnInstruction(null);
	}
}