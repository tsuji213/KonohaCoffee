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
import org.KonohaScript.SyntaxTree.NodeVisitor.IfNodeAcceptor;
import org.KonohaScript.SyntaxTree.NodeVisitor.SwitchNodeAcceptor;
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

public class LLVMCodeGen extends CodeGenerator {
	private LLVMBuilder builder;
	
	public LLVMCodeGen() {
		super(null);
		this.builder = new LLVMBuilder();
		
		// initialize LLVMNodeAcceptor
		this.IfNodeAcceptor = new LLVMIfNodeAcceptor(this, this.builder);
		this.SwitchNodeAcceptor = new LLVMSwitchNodeAcceptor(this, this.builder);
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
	public CompiledMethod Compile(TypedNode Block) { //FIXME
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
		}
		this.builder.createBasicBlock("topBlock");
		
		this.VisitBlock(Block.GetHeadNode());
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
		this.builder.pushValue(constValue);
		
		return true;
	}

	@Override
	public boolean ExitNew(NewNode Node) { 
		LLVMValue heapValue = this.builder.createHeapAllocation(Node.TypeInfo.ShortClassName);
		this.builder.pushValue(heapValue);
		return true;
	}

	@Override
	public boolean ExitNull(NullNode Node) { 
		LLVMValue nullValue = this.builder.createNullValue(Node.TypeInfo.ShortClassName);
		this.builder.pushValue(nullValue);
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
		this.builder.pushValue(value);
		
		return true;
	}

	@Override
	public void EnterGetter(GetterNode Node) {
		Local local = this.FindLocalVariable(Node.SourceToken.ParsedText);
		assert (local != null);
	}

	@Override
	public boolean ExitGetter(GetterNode Node) { //TODO: 
		// String Expr = Node.TermToken.ParsedText;
		// push(Expr + "." + Node.TypeInfo.FieldNames.get(Node.Xindex));
		// push(Expr);
		// FIXME
		//this.push(Node.SourceToken.ParsedText);
		return true;

	}

	static final String[] binaryOpList	= 
		{ "+", "-", "*", "/","<", "<=", ">", ">=", "==", "!=", "&&", "||", "&", "|", "^", "<<", ">>"};

	private boolean isMethodBinaryOperator(ApplyNode Node) {
		String methodName = Node.Method.MethodName;
		for (String op : binaryOpList) {
			if (op.equals(methodName))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean ExitApply(ApplyNode Node) { //TODO: support unary operator
		String methodName = Node.Method.MethodName;
		LLVMValue retValue = null;
		if (this.isMethodBinaryOperator(Node)) {
			LLVMValue rightValue = this.builder.popValue();
			LLVMValue leftValue = this.builder.popValue();
			retValue = this.builder.createBinaryOp(methodName, leftValue, rightValue, "bopRet");
		} else {
			int size = Node.Params.size();
			LLVMValue[] params = new LLVMValue[size];
			for (int i = size - 1; i > -1; i--) {
				params[i] = this.builder.popValue();
			}
			retValue = this.builder.createCall(methodName, params, "methodRet");
		}
		this.builder.pushValue(retValue);
		return true;
	}

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
	public void EnterAssign(AssignNode Node) { 
		this.AddLocalVarIfNotDefined(Node.TypeInfo, Node.SourceToken.ParsedText);
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
	public boolean ExitLoop(LoopNode Node) { //TODO: 
//		String LoopBody = this.pop();
//		String IterExpr = this.pop();
//		String CondExpr = this.pop();
//		this.push("while (" + CondExpr + ") {" + LoopBody + IterExpr + "}");
		return true;
	}

	@Override
	public boolean ExitReturn(ReturnNode Node) { 
		this.builder.createReturn(this.builder.popValue());
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

	@Override
	public void EnterDefine(DefineNode Node) {
	}

	@Override
	public void EnterConst(ConstNode Node) {
	}

	@Override
	public void EnterNew(NewNode Node) {
	}

	@Override
	public void EnterNull(NullNode Node) {
	}

	@Override
	public void EnterApply(ApplyNode Node) {
	}

	@Override
	public void EnterAnd(AndNode Node) {
	}

	@Override
	public void EnterOr(OrNode Node) {
	}

	@Override
	public void EnterLoop(LoopNode Node) {
	}

	@Override
	public void EnterReturn(ReturnNode Node) {
	}

	@Override
	public void EnterLabel(LabelNode Node) {
	}

	@Override
	public void EnterJump(JumpNode Node) {
	}

	@Override
	public void EnterTry(TryNode Node) {
	}

	@Override
	public void EnterThrow(ThrowNode Node) {
	}

	@Override
	public void EnterFunction(FunctionNode Node) {
	}

	@Override
	public void EnterError(ErrorNode Node) {
	}
}

class LLVMIfNodeAcceptor implements IfNodeAcceptor {
	private LLVMCodeGen codeGen;
	private LLVMBuilder builder;
	
	public LLVMIfNodeAcceptor(LLVMCodeGen codeGen, LLVMBuilder builder) {
		this.codeGen = codeGen;
		this.builder = builder;
	}

	@Override
	public boolean Invoke(IfNode Node, NodeVisitor Visitor) {
		System.out.println("LLVMIfNodeAcceptor");
		Visitor.EnterIf(Node);
		Visitor.Visit(Node.CondExpr);
		
		LLVMBasicBlock currentBlock = this.builder.getCurrentBBlock();
		LLVMValue condition = this.builder.popValue();
		
		//Then Block
		LLVMBasicBlock thenBlock = this.builder.createBasicBlock("thenBlock");
		if (Node.ThenNode != null) {
			this.codeGen.VisitBlock(Node.ThenNode);	
		}
		
		//Else Block
		LLVMBasicBlock elseBlock = this.builder.createBasicBlock("elseBlock");
		if (Node.ElseNode != null) {
			this.codeGen.VisitBlock(Node.ElseNode);	
		}
		
		// create if 
		this.builder.changeCurrentBBlock(currentBlock);
		this.builder.createBinaryBranch(condition, thenBlock, elseBlock);
		
		return Visitor.ExitIf(Node);
	}
}

class LLVMSwitchNodeAcceptor implements SwitchNodeAcceptor { //TODO: support break statement
	private LLVMCodeGen codeGen;
	private LLVMBuilder builder;
	
	public LLVMSwitchNodeAcceptor(LLVMCodeGen codeGen, LLVMBuilder builder) {
		this.codeGen = codeGen;
		this.builder = builder;
	}
	
	@Override
	public boolean Invoke(SwitchNode Node, NodeVisitor Visitor) {
		Visitor.EnterSwitch(Node);
		Visitor.Visit(Node.CondExpr);
		
		LLVMBasicBlock currentBlock = this.builder.getCurrentBBlock();	
		
		
		LLVMValue condition = this.builder.popValue();
		long caseNum = Node.Blocks.size();
		
		//create switch
		LLVMBasicBlock endSwitchBlock = this.builder.createBasicBlock("endSwitchBlock");
		LLVMBasicBlock defaultBlock = this.builder.createBasicBlock("default");
		this.builder.createBranch(endSwitchBlock);
		
		this.builder.changeCurrentBBlock(currentBlock);
		LLVMSwitchInstruction switchIns = 
				this.builder.createSwitch(condition, defaultBlock, caseNum);
		
		//case block		
		for (int i = 0; i < caseNum; i++) { //FIXME: currently support int type only
			String labelName = Node.Labels.get(i);
			LLVMBasicBlock caseBlock = this.builder.createBasicBlock(labelName);
			this.codeGen.VisitBlock(Node.Blocks.get(i));
			
			LLVMValue caseLabel = 
					this.builder.createConstValue("Integer", Integer.parseInt(labelName));
			switchIns.addCase(caseLabel, caseBlock);
		}
		
		this.builder.changeCurrentBBlock(endSwitchBlock);
		
		return Visitor.ExitSwitch(Node);
	}
	
}

class LLVMBuilder { //TODO: use single module
	private final int intLength = 64;
	
	private LLVMModule module;
	private LLVMInstructionBuilder builder;
	private LLVMBasicBlock currentBBlock;
	private LLVMFunction currentFunc;
	
	private HashMap<String, Integer> argMap;
	private Stack<LLVMValue> valueStack;
	private Stack<LLVMBasicBlock> bblockStack;
	private HashMap<String, LLVMFunction> definedFucnNameMap;
	
	public LLVMBuilder() {
		System.loadLibrary("jllvm");	
		module = new LLVMModule("top", LLVMContext.getGlobalContext());
		builder = new LLVMInstructionBuilder();
		currentFunc = null;
		argMap = new HashMap<String, Integer>();
		valueStack = new Stack<LLVMValue>();
		bblockStack = new Stack<LLVMBasicBlock>();
		definedFucnNameMap = new HashMap<String, LLVMFunction>();
		//defineEmbeddedMethod();
	}
	
	public void dump() {
		//this.currentFunc.dump();
		this.module.dump();
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
	
	public void pushBBlock(LLVMBasicBlock bblock) {
		this.bblockStack.push(bblock);
	}
	
	public LLVMBasicBlock popBBlock() {
		return this.bblockStack.pop();
	}
	
	public void pushValue(LLVMValue value) {
		this.valueStack.push(value);
	}
	
	public LLVMValue popValue() {
		return this.valueStack.pop();
	}
	
	private LLVMFunction createFunctionAbst(String funcName, LLVMType retType, LLVMType[] argsType, boolean isVarArg) {
		LLVMFunctionType funcType = new LLVMFunctionType(retType, argsType, isVarArg);
		LLVMFunction func = new LLVMFunction(this.module, funcName, funcType);	
		changeCurrentFunction(func);
		definedFucnNameMap.put(funcName, func);
		
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
		} else if (typeName.equals("Float")) {	//FIXME
			double floatValue = (Double)value;
			return new LLVMConstantReal(new LLVMFloatType(), floatValue);
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
//			LLVMValue voidValue = createCall("$getVoidNull", arg, "voidRet");
//			return createLocalVariable(voidValue, "null");
			
			LLVMValue constNull = createConstValue("Integer", 0);
			return createLocalVariable(constNull, "null");
		} else {
			LLVMValue constNull = createConstValue(typeName, 0);
			return createLocalVariable(constNull, "null");	
		}
	}
	
	public void createReturn(LLVMValue retValue) {
		new LLVMReturnInstruction(builder, retValue);
	}
	
	public void createBranch(LLVMBasicBlock destBlock) {
		new LLVMBranchInstruction(builder, destBlock);
	}
	
	//TODO: remove branch instruction if not reachable
	public void createBinaryBranch(LLVMValue condition, LLVMBasicBlock thenBlock, LLVMBasicBlock elseBlock) {
		LLVMBasicBlock currentBlock = this.getCurrentBBlock();
		LLVMBasicBlock endBlock = this.createBasicBlock("endBlock");
		
		this.changeCurrentBBlock(thenBlock);
		this.createBranch(endBlock);
		
		this.changeCurrentBBlock(elseBlock);
		this.createBranch(endBlock);		
		
		this.changeCurrentBBlock(currentBlock);
		new LLVMBranchInstruction(builder, condition, thenBlock, elseBlock);
		this.changeCurrentBBlock(endBlock);
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
	public LLVMValue createBinaryOp(String opName, LLVMValue leftValue, LLVMValue rightValue, String retName) {
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
	
	public LLVMValue createCall(String funcName, LLVMValue[] args, String retName) {
		temporaryDefineMethod(funcName);
		LLVMFunction func = module.getNamedFunction(funcName);
		return new LLVMCallInstruction(builder, func, args, retName);
	}
	
	private void temporaryDefineMethod(String funcName) {	//FIXME: this is a temporary method. future removed
		if (funcName.equals("fibo")) {
			if (this.definedFucnNameMap.get(funcName) == null) {
				LLVMType[] argsType = {new LLVMVoidType(), new LLVMIntegerType(intLength)};
				createFunction(funcName, new LLVMIntegerType(intLength), argsType);	
			}
		} else if (funcName.equals("p")) {
			if (this.definedFucnNameMap.get(funcName) == null) {
				LLVMType[] argsType_p = {new LLVMVoidType(), new LLVMPointerType(new LLVMIntegerType(8), 0)};
				createFunction("p", new LLVMVoidType(), argsType_p);	
			}
		} else if (funcName.equals("toString")) {	
			if (this.definedFucnNameMap.get(funcName) == null) {
				LLVMType[] argsType = {new LLVMIntegerType(intLength), new LLVMIntegerType(intLength)};
				createFunction("toString", new LLVMPointerType(new LLVMIntegerType(8), 0), argsType);	
			}
		}
	}
	
	//TODO: support array
	public LLVMValue createHeapAllocation(String typeName) {
		LLVMType type = convertTypeNameToLLVMType(typeName);
		return new LLVMHeapAllocation(builder, type, null, "heap");
	}
	
	public LLVMSwitchInstruction createSwitch(LLVMValue condValue, LLVMBasicBlock defaultBlock, long caseNum) {
		return new LLVMSwitchInstruction(builder, condValue, defaultBlock, caseNum);
	}
	
	// embedded method definition
	private void defineEmbeddedMethod() {
		defineMethod_p();
		defineMethod_toString();
		defineMethod_$getVoidNull();
	}
	
	private void defineMethod_p() { //FIXME
		// define puts function
//		LLVMType[] argsType_printf = {new LLVMPointerType(new LLVMIntegerType(8), 0)};
//		createVariableFunction("printf", new LLVMIntegerType(32), argsType_printf);
		
		//define p method
		LLVMType[] argsType_p = {new LLVMVoidType(), new LLVMPointerType(new LLVMIntegerType(8), 0)};
		createFunction("p", new LLVMVoidType(), argsType_p);
//		createBasicBlock("bblock");
//		LLVMArgument arg = currentFunc.getParameter(1);
//		LLVMValue strValue = createLocalVariable(arg, "str");
//		
//		LLVMValue format = createLocalVariable(new LLVMConstantString("%s\n", true), "format");
		//new LLVMGetElementPointerInstruction(builder, format, format, "format");
	}
	
	private void defineMethod_toString() {	//FIXME
		LLVMType[] argsType = {new LLVMIntegerType(intLength), new LLVMIntegerType(intLength)};
		createFunction("toString", new LLVMPointerType(new LLVMIntegerType(8), 0), argsType);
	}	
	
	private void defineMethod_$getVoidNull() {
		LLVMType[] argsType = {new LLVMIntegerType(intLength)};
		LLVMFunction definedFunc = createFunction("$getVoidNull", new LLVMVoidType(), argsType);
		createBasicBlock("bblock");
		createReturn(null);
	}
}