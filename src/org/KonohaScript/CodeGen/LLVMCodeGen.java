 package org.KonohaScript.CodeGen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import org.KonohaScript.KLib.*;
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
import org.KonohaScript.SyntaxTree.NodeVisitor.AndNodeAcceptor;
import org.KonohaScript.SyntaxTree.NodeVisitor.IfNodeAcceptor;
import org.KonohaScript.SyntaxTree.NodeVisitor.OrNodeAcceptor;
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
	public void Prepare(KonohaMethod Method, KonohaArray params) {
		this.Prepare(Method);
		for(int i = 0; i < params.size(); i++) {
			Local local = params.get(i);
			this.AddLocal(local.TypeInfo, local.Name);
		}
	}

	@Override 
	public CompiledMethod Compile(TypedNode Block) { //FIXME
		CompiledMethod Mtd = new CompiledMethod(this.MethodInfo);
		
		if(this.MethodInfo != null && this.MethodInfo.MethodName.length() > 0) {
			String fqMethodName = this.getFullyQualifiedMethodName(this.MethodInfo);
			int argsSize = this.LocalVals.size();
			LLVMType[] argsType = new LLVMType[argsSize];
			
			for(int i = 0; i < argsSize; i++) {
				Local local = this.GetLocalVariableByIndex(i);
				argsType[i] = this.builder.convertTypeNameToLLVMType(local.TypeInfo.ShortClassName);
				this.builder.setArgument(local.Name, i);
			}
			
			LLVMType retType = 
					this.builder.convertTypeNameToLLVMType(this.MethodInfo.ClassInfo.ShortClassName);
			this.builder.createFunction(fqMethodName, retType, argsType);
		} else {
			LLVMType[] argsType = {}; //TODO: currently only support void main()
			this.builder.createFunction("main", new LLVMVoidType(), argsType);
		}
		this.builder.createBasicBlock("topBlock");
		
		this.VisitBlock(Block.GetHeadNode());
		Mtd.CompiledCode = "dont support";
		//this.builder.dumpFunction();

		return Mtd;
	}

	public String getFullyQualifiedMethodName(KonohaMethod methodInfo) {
		String methodName = methodInfo.MethodName;
		String retType = methodInfo.ClassInfo.ShortClassName;
		int argsSize = methodInfo.Param.Types.length;
		
		String fqMethodName = retType + "_" + methodName;
		for(int i = 0; i < argsSize; i++) {
			fqMethodName = fqMethodName + "_" + methodInfo.Param.Types[i].ShortClassName;
		}
		
		return fqMethodName;
	}

	public void dumpModule() {
		builder.dumpModule();
	}

	public void execute() {
		builder.execute();
	}

	@Override
 	public boolean ExitDefine(DefineNode Node) { //TODO:
		return true;
	}

	@Override 
	public boolean ExitConst(ConstNode Node) { 
		String typeName = Node.TypeInfo.ShortClassName;
		Object value = Node.ConstValue;
		LLVMValue constValue = this.builder.createConstValue(typeName, value);
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
		LLVMType nullType = this.builder.convertTypeNameToLLVMType(Node.TypeInfo.ShortClassName);
		LLVMValue nullValue = this.builder.createConstNull(nullType);
		this.builder.pushValue(nullValue);
		
		return true;
	}

	@Override
	public void EnterLocal(LocalNode Node) { //FIXME
		this.AddLocalVarIfNotDefined(Node.TypeInfo, Node.FieldName);
	}

	@Override
	public boolean ExitLocal(LocalNode Node) { //TODO: support scope
		LLVMArgument value = this.builder.getArgument(Node.FieldName); //currently only support argument 
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
		for(String op : binaryOpList) {
			if(op.equals(methodName))
				return true;
		}
		return false;
	}

	@Override
	public boolean ExitApply(ApplyNode Node) { //TODO: support unary operator
		LLVMValue retValue = null;
		if(this.isMethodBinaryOperator(Node)) {
			String methodName = Node.Method.MethodName;
			LLVMValue rightValue = this.builder.popValue();
			LLVMValue leftValue = this.builder.popValue();
			retValue = this.builder.createBinaryOp(methodName, leftValue, rightValue, "bopRet");
		} else {
			String fqMethodName = getFullyQualifiedMethodName(Node.Method);
			int size = Node.Params.size();
			LLVMValue[] params = new LLVMValue[size];
			for(int i = size - 1; i > -1; i--) {
				params[i] = this.builder.popValue();
			}
			retValue = this.builder.createCall(fqMethodName, params, "methodRet");
		}
		this.builder.pushValue(retValue);
		return true;
	}

	@Override
	public boolean ExitAnd(AndNode Node) { 
		return true;
	}

	@Override
	public boolean ExitOr(OrNode Node) { 
		return true;
	}

	@Override
	public void EnterAssign(AssignNode Node) { 
		this.AddLocalVarIfNotDefined(Node.TypeInfo, Node.SourceToken.ParsedText);
	}

	@Override
	public boolean ExitAssign(AssignNode Node) { //TODO:
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
//		this.push("while(" + CondExpr + ") {" + LoopBody + IterExpr + "}");
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
//		if(Label.compareTo("continue") == 0) {
//			this.push("");
//		} else if(Label.compareTo("continue") == 0) {
//			this.push("");
//		} else {
//			this.push(Label + ":");
//		}
		return true;
	}

	@Override
	public boolean ExitJump(JumpNode Node) { //TODO: 
//		String Label = Node.Label;
//		if(Label.compareTo("continue") == 0) {
//			this.push("continue;");
//		} else if(Label.compareTo("continue") == 0) {
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
//		for(int i = 0; i < Node.CatchBlock.size(); i = i + 1) {
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
		Visitor.EnterIf(Node);
		Visitor.Visit(Node.CondExpr);
		
		LLVMBasicBlock currentBlock = this.builder.getCurrentBBlock();
		LLVMBasicBlock endBlock = null;
		LLVMValue condition = this.builder.popValue();
		
		//Then Block
		LLVMBasicBlock thenBlock = this.builder.createBasicBlock("thenBlock");
		if(Node.ThenNode != null) {
			this.codeGen.VisitBlock(Node.ThenNode);
		}
		boolean thenBlockHasNotReturnNode = hasNotReturnNode(Node.ThenNode);
		
		//Else Block
		LLVMBasicBlock elseBlock = this.builder.createBasicBlock("elseBlock");
		if(Node.ElseNode != null) {
			this.codeGen.VisitBlock(Node.ElseNode);
		}
		boolean elseBlockHasNotReturnNode = hasNotReturnNode(Node.ElseNode);
		
		//End Block
		if(thenBlockHasNotReturnNode || elseBlockHasNotReturnNode) {
			endBlock = this.builder.createBasicBlock("endBlock");
			if(thenBlockHasNotReturnNode) {
				this.builder.changeCurrentBBlock(thenBlock);
				this.builder.createBranch(endBlock);
			}
			
			if(elseBlockHasNotReturnNode) {
				this.builder.changeCurrentBBlock(elseBlock);
				this.builder.createBranch(endBlock);
			}
		}
		
		// create if 
		this.builder.changeCurrentBBlock(currentBlock);
		this.builder.createIfElse(condition, thenBlock, elseBlock);
		if(endBlock != null) {
			this.builder.changeCurrentBBlock(endBlock);
		}
		
		return Visitor.ExitIf(Node);
	}

	private boolean hasNotReturnNode(TypedNode node) { //FIXME
		String nameOfReturnNodeClass =  "class org.KonohaScript.SyntaxTree.ReturnNode";
		boolean hasNotReturnNode = true;
		TypedNode iterNode = node;
		
		if(iterNode == null) {
			return true;
		}
		
		while(true) {
			if(iterNode.NextNode == null) {
				if(iterNode.getClass().toString().equals(nameOfReturnNodeClass)) {
					hasNotReturnNode = false;
				}
				break;
			}
			iterNode = iterNode.NextNode;
		}
		
		return hasNotReturnNode;
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
		
		//add case block	
		for(int i = 0; i < caseNum; i++) { //FIXME: currently support int type only
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

class LLVMAndNodeAcceptor implements AndNodeAcceptor { 
	private LLVMCodeGen codeGen;
	private LLVMBuilder builder;

	public LLVMAndNodeAcceptor(LLVMCodeGen codeGen, LLVMBuilder builder) {
		this.codeGen = codeGen;
		this.builder = builder;
	}

	@Override
	public boolean Invoke(AndNode Node, NodeVisitor Visitor) { 
		Visitor.EnterAnd(Node);
		Visitor.Visit(Node.LeftNode);
		
		LLVMBasicBlock currentBlock = this.builder.getCurrentBBlock();
		LLVMValue condition = this.builder.popValue();
		
		//Then Block
		LLVMBasicBlock thenBlock = this.builder.createBasicBlock("andThenBlock");
		this.codeGen.VisitBlock(Node.RightNode);
		LLVMValue ret = this.builder.popValue();
		
		//End Block
		LLVMBasicBlock endBlock = this.builder.createBasicBlock("andEndBlock");
		
		// create And 
		this.builder.changeCurrentBBlock(currentBlock);
		this.builder.createCondLogicalOp(condition, thenBlock, endBlock, true);
		
		//create Phi Node
		LLVMType type = this.builder.convertTypeNameToLLVMType("Boolean");
		LLVMValue[] values = {ret, condition};
		LLVMBasicBlock[] blocks = {thenBlock, endBlock};
		LLVMValue andRet = this.builder.createPhiNode(type, "andRet", values, blocks);
		this.builder.pushValue(andRet);
		
		return Visitor.ExitAnd(Node);
	}
}

class LLVMOrNodeAcceptor implements OrNodeAcceptor { 
	private LLVMCodeGen codeGen;
	private LLVMBuilder builder;

	public LLVMOrNodeAcceptor(LLVMCodeGen codeGen, LLVMBuilder builder) {
		this.codeGen = codeGen;
		this.builder = builder;
	}

	@Override
	public boolean Invoke(OrNode Node, NodeVisitor Visitor) { 
		Visitor.EnterOr(Node);
		Visitor.Visit(Node.LeftNode);
		
		LLVMBasicBlock currentBlock = this.builder.getCurrentBBlock();
		LLVMValue condition = this.builder.popValue();
		
		//End Block
		LLVMBasicBlock endBlock = this.builder.createBasicBlock("orEndBlock");
		
		//Else Block
		LLVMBasicBlock elseBlock = this.builder.createBasicBlock("orElseBlock");
		this.codeGen.VisitBlock(Node.RightNode);
		LLVMValue ret = this.builder.popValue();
		
		// create Or
		this.builder.changeCurrentBBlock(currentBlock);
		this.builder.createCondLogicalOp(condition, endBlock, elseBlock, false);
		
		//create Phi Node
		LLVMType type = this.builder.convertTypeNameToLLVMType("Boolean");
		LLVMValue[] values = {condition, ret};
		LLVMBasicBlock[] blocks = {endBlock, elseBlock};
		LLVMValue orRet = this.builder.createPhiNode(type, "orRet", values, blocks);
		this.builder.pushValue(orRet);
		
		return Visitor.ExitOr(Node);
	}
}

class LLVMBuilder { 
	private final int intLength = 64;

	private static boolean isInitialized = false;
	private static boolean isDefinedEmbeddedFunc = false;
	private static LLVMModule module;
	private static HashMap<String, LLVMFunction> definedFucnNameMap; //TODO: future may be removed

	private LLVMInstructionBuilder builder;
	private LLVMBasicBlock currentBBlock;
	private LLVMFunction currentFunc;

	private HashMap<String, Integer> argMap;
	private Stack<LLVMValue> valueStack;

	public static void initBuilder() {
		if(!isInitialized) {
			isInitialized = true;
			
			System.loadLibrary("jllvm");
			module = new LLVMModule("top", LLVMContext.getGlobalContext());
			definedFucnNameMap = new HashMap<String, LLVMFunction>();
			
			// setup embedded method
			new CmdLauncher("clang -emit-llvm -S -O -o embeddedMethod.ll embeddedMethod.c");
			new CmdLauncher("llvm-as embeddedMethod.ll");
			new CmdLauncher("rm embeddedMethod.ll");
		}
	}

	public LLVMBuilder() {
		initBuilder();
		
		builder = new LLVMInstructionBuilder();
		currentFunc = null;
		argMap = new HashMap<String, Integer>();
		valueStack = new Stack<LLVMValue>();
		
		if(!isDefinedEmbeddedFunc) {
			isDefinedEmbeddedFunc = true;
			defineEmbeddedMethod();
		}
	}

	public void dumpFunction() {
		this.currentFunc.dump();
	}	

	public void dumpModule() {
		module.dump();
	}

	public void execute() { //FIXME
		finalizeMainFunc();
		dumpModule();
		module.writeBitcodeToFile("compiledCode.bc");
		
		// execute llvm tool chain
		new CmdLauncher("llvm-link compiledCode.bc embeddedMethod.bc -S -o linkedCode.ll");
		new CmdLauncher("lli linkedCode.ll");
		new CmdLauncher("rm compiledCode.bc");
		new CmdLauncher("rm linkedCode.ll");
		new CmdLauncher("rm embeddedMethod.bc");
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

	public void pushValue(LLVMValue value) {
		this.valueStack.push(value);
	}

	public LLVMValue popValue() {
		return this.valueStack.pop();
	}

	private LLVMFunction createFunctionAbst(String funcName, LLVMType retType, LLVMType[] argsType, boolean isVarArg) {
		LLVMFunctionType funcType = new LLVMFunctionType(retType, argsType, isVarArg);
		LLVMFunction func = new LLVMFunction(module, funcName, funcType);
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
		if(typeName.equals("Integer")) {
			long intValue = (Integer)value;
			return LLVMConstantInteger.constantInteger((LLVMIntegerType)type, intValue, false);
		} else if(typeName.equals("Float")) {	//FIXME
			double floatValue = (Double)value;
			return new LLVMConstantReal(new LLVMFloatType(), floatValue);
		} else if(typeName.equals("Boolean")) {
			boolean boolValue = (Boolean)value;
			return new LLVMConstantBoolean(boolValue);
		} else if(typeName.equals("String")) {
			String strValue = (String)value;
			return new LLVMConstantString(strValue, true);
		} else {
			return null;
		}
	}

	public LLVMConstant createConstNull(LLVMType type) {
		return LLVMConstant.constNull(type);
	}

	public void createReturn(LLVMValue retValue) {
		new LLVMReturnInstruction(builder, retValue);
	}

	public void createBranch(LLVMBasicBlock destBlock) {
		new LLVMBranchInstruction(builder, destBlock);
	}

	public void createIfElse(LLVMValue condition, LLVMBasicBlock thenBlock, LLVMBasicBlock elseBlock) {
		new LLVMBranchInstruction(builder, condition, thenBlock, elseBlock);
	}

	public void createCondLogicalOp(LLVMValue condition, LLVMBasicBlock thenBlock, LLVMBasicBlock endBlock, boolean isAnd) {
		// isAnd == true: create and. isAnd == false: create false.
		LLVMBasicBlock currentBlock = this.getCurrentBBlock();
		
		if(!isAnd) {	//swap block
			LLVMBasicBlock swapBlock = thenBlock;
			thenBlock = endBlock;
			endBlock = swapBlock;
		} 
			
		this.changeCurrentBBlock(thenBlock);
		this.createBranch(endBlock);
		
		this.changeCurrentBBlock(currentBlock);
		new LLVMBranchInstruction(builder, condition, thenBlock, endBlock);
		this.changeCurrentBBlock(endBlock);
	}

	public LLVMType convertTypeNameToLLVMType(String typeName) { //TODO: support user defined class
		if(typeName.equals("Integer")) {
			return new LLVMIntegerType(intLength);
		} else if(typeName.equals("Float")) {
			return new LLVMFloatType();
		} else if(typeName.equals("Void")) {
			return new LLVMVoidType();
		} else if(typeName.equals("Boolean")) {
			return new LLVMIntegerType(1);
		} else if(typeName.equals("String")) {
			return new LLVMPointerType(new LLVMIntegerType(8), 0);
		} else if(typeName.equals("Object")) { //FIXME
			return new LLVMIntegerType(intLength);
		} else {
			System.err.println(typeName + " is not defined type");
			return null;
		}
	}

	public LLVMArgument getArgument(String argName) {
		return this.currentFunc.getParameter(this.argMap.get(argName));
	}

	public LLVMValue createLocalVariable(LLVMValue initValue, String varName) { //FIXME
		LLVMStackAllocation stack = new LLVMStackAllocation(builder, initValue.typeOf(), null, varName);
		new LLVMStoreInstruction(builder, initValue, stack);
		return stack;
	}

	//TODO: support float value at comparison instruction
	public LLVMValue createBinaryOp(String opName, LLVMValue leftValue, LLVMValue rightValue, String retName) {
		//binaryOpList	= { "+", "-", "*", "/", "<", "<=", ">", ">=", "==", "!=", "&&", "||", "&", "|", "^", "<<", ">>" }
		if(opName.equals("+")) {
			return new LLVMAddInstruction(builder, leftValue, rightValue, false, retName);
		} else if(opName.equals("-")) {
			return new LLVMSubtractInstruction(builder, leftValue, rightValue, false, retName);
		} else if(opName.equals("*")) {
			return new LLVMMultiplyInstruction(builder, leftValue, rightValue, false, retName);
		} else if(opName.equals("/")) {	//FIXME
		} else if(opName.equals("<")) {
			LLVMIntPredicate predicate = LLVMIntPredicate.LLVMIntSLT;
			return new LLVMIntegerComparison(builder, predicate, leftValue, rightValue, retName);
		} else if(opName.equals("<=")) {
			LLVMIntPredicate predicate = LLVMIntPredicate.LLVMIntSLE;
			return new LLVMIntegerComparison(builder, predicate, leftValue, rightValue, retName);
		} else if(opName.equals(">")) {
			LLVMIntPredicate predicate = LLVMIntPredicate.LLVMIntSGT;
			return new LLVMIntegerComparison(builder, predicate, leftValue, rightValue, retName);
		} else if(opName.equals(">=")) {
			LLVMIntPredicate predicate = LLVMIntPredicate.LLVMIntSGE;
			return new LLVMIntegerComparison(builder, predicate, leftValue, rightValue, retName);
		} else if(opName.equals("==")) {
			LLVMIntPredicate predicate = LLVMIntPredicate.LLVMIntEQ;
			return new LLVMIntegerComparison(builder, predicate, leftValue, rightValue, retName);
		} else if(opName.equals("!=")) {
			LLVMIntPredicate predicate = LLVMIntPredicate.LLVMIntNE;
			return new LLVMIntegerComparison(builder, predicate, leftValue, rightValue, retName);
		} else if(opName.equals("&&")) { //FIXME
		} else if(opName.equals("||")) { //FIXME
		} else if(opName.equals("&")) {
			LLVMBinaryBitwiseInstruction.BinaryBitwiseInstructionType insType = 
					LLVMBinaryBitwiseInstruction.BinaryBitwiseInstructionType.AND;
			return new LLVMBinaryBitwiseInstruction(insType, builder, leftValue, rightValue, retName);
		} else if(opName.equals("|")) {
			LLVMBinaryBitwiseInstruction.BinaryBitwiseInstructionType insType = 
					LLVMBinaryBitwiseInstruction.BinaryBitwiseInstructionType.OR;
			return new LLVMBinaryBitwiseInstruction(insType, builder, leftValue, rightValue, retName);
		} else if(opName.equals("^")) {
			LLVMBinaryBitwiseInstruction.BinaryBitwiseInstructionType insType = 
					LLVMBinaryBitwiseInstruction.BinaryBitwiseInstructionType.XOR;
			return new LLVMBinaryBitwiseInstruction(insType, builder, leftValue, rightValue, retName);
		} else if(opName.equals("<<")) {
		} else if(opName.equals(">>")) {
		} 
		return null;
	}

	public LLVMValue createCall(String funcName, LLVMValue[] args, String retName) {
		LLVMFunction func = module.getNamedFunction(funcName);
		if(funcName.split("_")[0].equals("Void")) {
			retName = "";
		}
		return new LLVMCallInstruction(builder, func, args, retName);
	}

	//TODO: support user defined class
	public LLVMValue createHeapAllocation(String typeName) {
		LLVMType type = convertTypeNameToLLVMType(typeName);
		return new LLVMHeapAllocation(builder, type, null, "heap");
	}

	public LLVMSwitchInstruction createSwitch(LLVMValue condValue, LLVMBasicBlock defaultBlock, long caseNum) {
		return new LLVMSwitchInstruction(builder, condValue, defaultBlock, caseNum);
	}

	private void finalizeMainFunc() { //FIXME
		changeCurrentFunction(module.getNamedFunction("main"));
		changeCurrentBBlock(currentFunc.getLastBasicBlock());
		createReturn(null);
	}

	public LLVMValue createPhiNode(LLVMType type, String retName, LLVMValue[] values, LLVMBasicBlock[] blocks) {
		LLVMPhiNode phiNode = new LLVMPhiNode(builder, type, retName);
		phiNode.addIncoming(values, blocks);
		return phiNode;
	}

	// embedded method definition
	private void defineEmbeddedMethod() {
		defineMethod_p();
		defineMethod_toString();
	}

	private void defineMethod_p() { //FIXME
		//define method: void_p_Integer_String 
		LLVMType[] argsType_p = {new LLVMIntegerType(intLength), new LLVMPointerType(new LLVMIntegerType(8), 0)};
		createFunction("Void_p_Integer_String", new LLVMVoidType(), argsType_p);
	}

	private void defineMethod_toString() {	//TODO: currently only support int type
		//define method: String_toString_Integer_Integer
		LLVMType[] argsType = {new LLVMIntegerType(intLength), new LLVMIntegerType(intLength)};
		createFunction("String_toString_Integer_Integer", new LLVMPointerType(new LLVMIntegerType(8), 0), argsType);
	}	
}

class CmdLauncher { //output stderr stdout separately	
	public CmdLauncher(String cmd) {
		ProcessBuilder pb = new ProcessBuilder(createRunnableCmd(cmd));
		try {
			Process proc = pb.start();
			StreamGetter stdoutGetter = new StreamGetter(proc.getInputStream());
			StreamGetter stderrGetter = new StreamGetter(proc.getErrorStream());
			
			stdoutGetter.start();
			stderrGetter.start();
			
			proc.waitFor();
			
			stdoutGetter.join();
			stderrGetter.join();
			
			System.out.println(stdoutGetter.getRedirectedStr());
			if(proc.exitValue() != 0) {
				System.out.println(stderrGetter.getRedirectedStr());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}		
	}

	private String[] createRunnableCmd(String cmd) {
		String[] cmdArray = new String[3];
		cmdArray[0] = "bash";
		cmdArray[1] = "-c";
		cmdArray[2] = new String(cmd);
		return cmdArray;
	}

	class StreamGetter extends Thread {
		private BufferedReader br;
		private String redirectedStr;
		
		public StreamGetter(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
			redirectedStr = "";
		}
		
		public void run() {
			String line = null;
			try {
				while((line = br.readLine()) != null) {
					redirectedStr = redirectedStr.concat(line + "\n");
				}
				br.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} 
		}
		
		public String getRedirectedStr() {
			return redirectedStr;
		}
	}
}
