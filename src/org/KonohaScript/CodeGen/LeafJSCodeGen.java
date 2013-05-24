package org.KonohaScript.CodeGen;

import java.util.ArrayList;

import org.KonohaScript.KMethod;
import org.KonohaScript.SyntaxTree.AndNode;
import org.KonohaScript.SyntaxTree.AssignNode;
import org.KonohaScript.SyntaxTree.BlockNode;
import org.KonohaScript.SyntaxTree.BoxNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.DefineClassNode;
import org.KonohaScript.SyntaxTree.DefNode;
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

class IndentGenerator{
	private int level = 0;
	private String currentLevelIndentString = "";
	private String indentString = "\t";

	public IndentGenerator(){
	}

	public IndentGenerator(int tabstop){
		this.indentString = repeat(" ", tabstop);
	}

	private static String repeat(String str, int n){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < n; ++i){
			builder.append(str);
		}
		return builder.toString();
	}

	public void setLevel(int level){
		if(level < 0) level = 0;
		if(this.level != level){
			this.level = level;
			currentLevelIndentString = repeat(indentString, level);
		}
	}

	public void indent(int n){
		setLevel(level + n);
	}

	public String get(){
		return currentLevelIndentString;
	}

	public String getAndIndent(int diffLevel){
		String current = currentLevelIndentString;
		indent(diffLevel);
		return current;
	}

	public String indentAndGet(int diffLevel){
		indent(diffLevel);
		return currentLevelIndentString;
	}
}

public class LeafJSCodeGen extends SourceCodeGen implements ASTVisitor {
	private boolean UseLetKeyword = false;

	public LeafJSCodeGen() {
		super(null);
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
	public CompiledMethod Compile(TypedNode Block) {
		this.Visit(Block);
		CompiledMethod Mtd = new CompiledMethod(MethodInfo);
		assert (this.getProgramSize() == 1);
		String Source = pop();
		if (this.MethodInfo != null && this.MethodInfo.MethodName.length() > 0) {
			Local thisNode = this.FindLocalVariable("this");
			StringBuilder FuncBuilder = new StringBuilder();

			//FuncBuilder.append(thisNode.TypeInfo.ShortClassName);
			//FuncBuilder.append(".");
			String MethodName = this.MethodInfo.MethodName;
			FuncBuilder.append(MethodName);
			FuncBuilder.append(" = function(");

			for (int i = 1; i < this.LocalVals.size(); i++) {
				Local local = this.GetLocalVariableByIndex(i);
				if (i != 1) {
					FuncBuilder.append(", ");
				}
				FuncBuilder.append(local.Name);
			}
			FuncBuilder.append(")");
			FuncBuilder.append(Source);
			FuncBuilder.append(";");
			Source = FuncBuilder.toString();
		}
		Mtd.CompiledCode = Source;
		return Mtd;
	}

	@Override
	public boolean Visit(TypedNode Node) {
		return Node.Evaluate(this);
	}


	@Override
	public void EnterDone(DefNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitDone(DefNode Node) {
		return true;
	}

	@Override
	public void EnterConst(ConstNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitConst(ConstNode Node) {
		push(Node.ConstValue.toString());
		return true;
	}

	@Override
	public void EnterNew(NewNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitNew(NewNode Node) {
		push("new " + Node.TypeInfo.ShortClassName.toString() + "()");
		return true;
	}

	@Override
	public void EnterNull(NullNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitNull(NullNode Node) {
		push("null");
		return true;
	}

	@Override
	public void EnterLocal(LocalNode Node) {
		AddLocalVarIfNotDefined(Node.TypeInfo, Node.FieldName);
	}

	@Override
	public boolean ExitLocal(LocalNode Node) {
		push(Node.FieldName);
		return true;
	}

	@Override
	public void EnterField(FieldNode Node) {
		Local local = this.FindLocalVariable(Node.TermToken.ParsedText);
		assert (local != null);
	}

	@Override
	public boolean ExitField(FieldNode Node) {
		//String Expr = Node.TermToken.ParsedText;
		// push(Expr + "." + Node.TypeInfo.FieldNames.get(Node.Xindex));
		//push(Expr);
		// FIXME
		push(Node.SourceToken.ParsedText);
		return true;

	}

	@Override
	public void EnterBox(BoxNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitBox(BoxNode Node) {
		/* do nothing */return true;

	}

	@Override
	public void EnterMethodCall(MethodCallNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitMethodCall(MethodCallNode Node) {
		String methodName = Node.Method.MethodName;
		if(isMethodBinaryOperator(Node)){
			String params = pop();
			String thisNode = pop();
			push(thisNode + " " + methodName + " " + params);
		}else{
			String params = "(" + PopNReverseAndJoin(Node.Params.size() - 1, ", ") + ")";
			String thisNode = pop();
			push(thisNode + "." + methodName + params);
		}
		return true;
	}

	@Override
	public void EnterAnd(AndNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitAnd(AndNode Node) {
		String Right = pop();
		String Left = pop();
		push(Left + " && " + Right);
		return true;
	}

	@Override
	public void EnterOr(OrNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitOr(OrNode Node) {
		String Right = pop();
		String Left = pop();
		push(Left + " || " + Right);
		return true;
	}

	@Override
	public void EnterAssign(AssignNode Node) {
		AddLocalVarIfNotDefined(Node.TypeInfo, Node.TermToken.ParsedText);
	}

	@Override
	public boolean ExitAssign(AssignNode Node) {
		String Right = pop();
		push((UseLetKeyword ? "let " : "var ") + Node.TermToken.ParsedText + " = " + Right);
		return true;
	}

	@Override
	public void EnterLet(LetNode Node) {
		AddLocalVarIfNotDefined(Node.TypeInfo, Node.TermToken.ParsedText);
	}

	@Override
	public boolean ExitLet(LetNode Node) {
		String Block = pop();
		String Right = pop();
		push(Node.TermToken.ParsedText + " = " + Right + Block);
		return true;
	}

	@Override
	public void EnterBlock(BlockNode Node) {
		this.PushProgramSize();
		indentGenerator.indent(1);
	}

	@Override
	public boolean ExitBlock(BlockNode Node) {
		IndentGenerator g = indentGenerator;
		int Size = this.getProgramSize() - this.PopProgramSize();
		push("{\n" + g.get() + PopNReverseAndJoin(Size, ";\n" + g.get()) + ";\n" + g.indentAndGet(-1) + "}");
		return true;
	}

	@Override
	public void EnterIf(IfNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitIf(IfNode Node) {
		String ElseBlock = pop();
		String ThenBlock = pop();
		String CondExpr = pop();
		if(Node.ElseNode instanceof BlockNode && ((BlockNode)Node.ElseNode).ExprList.size() > 0){
			push("if (" + CondExpr + ") " + ThenBlock + " else " + ElseBlock);
		}else{
			push("if (" + CondExpr + ") " + ThenBlock);
		}
		return true;
	}

	@Override
	public void EnterSwitch(SwitchNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitSwitch(SwitchNode Node) {
		int Size = Node.Labels.size();
		String Exprs = "";
		for (int i = 0; i < Size; i = i + 1) {
			String Label = Node.Labels.get(Size - i);
			String Block = pop();
			Exprs = "case " + Label + ":" + Block + Exprs;
		}
		String CondExpr = pop();
		push("switch (" + CondExpr + ") {" + Exprs + "}");
		return true;
	}

	@Override
	public void EnterLoop(LoopNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitLoop(LoopNode Node) {
		String LoopBody = pop();
		String IterExpr = pop();
		String CondExpr = pop();
		push("while (" + CondExpr + ") {" + LoopBody + IterExpr + "}");
		return true;

	}

	@Override
	public void EnterReturn(ReturnNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitReturn(ReturnNode Node) {
		String Expr = pop();
		push("return " + Expr);
		return false;
	}

	@Override
	public void EnterLabel(LabelNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitLabel(LabelNode Node) {
		String Label = Node.Label;
		if (Label.compareTo("continue") == 0) {
			push("");
		} else if (Label.compareTo("continue") == 0) {
			push("");
		} else {
			push(Label + ":");
		}
		return true;
	}

	@Override
	public void EnterJump(JumpNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitJump(JumpNode Node) {
		String Label = Node.Label;
		if (Label.compareTo("continue") == 0) {
			push("continue;");
		} else if (Label.compareTo("continue") == 0) {
			push("break;");
		} else {
			push("goto " + Label);
		}
		return false;
	}

	@Override
	public void EnterTry(TryNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitTry(TryNode Node) {
		String FinallyBlock = pop();
		String CatchBlocks = "";
		for (int i = 0; i < Node.CatchBlock.size(); i = i + 1) {
			String Block = pop();
			CatchBlocks = "catch() " + Block + CatchBlocks;
		}
		String TryBlock = pop();
		push("try " + TryBlock + "" + CatchBlocks + FinallyBlock);
		return true;
	}

	@Override
	public void EnterThrow(ThrowNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitThrow(ThrowNode Node) {
		String Expr = pop();
		push("throw " + Expr + ";");
		return false;
	}

	@Override
	public void EnterFunction(FunctionNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitFunction(FunctionNode Node) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void EnterError(ErrorNode Node) {
		/* do nothing */
	}

	@Override
	public boolean ExitError(ErrorNode Node) {
		String Expr = pop();
		push("throw new Exception(" + Expr + ";");
		return false;
	}

	@Override
	public void EnterDefineClass(DefineClassNode Node) {
	}

	@Override
	public boolean ExitDefineClass(DefineClassNode Node) {
		String Exprs = "";
		int Size = Node.Fields.size();
		for (int i = 0; i < Size; i = i + 1) {
			String Expr = this.pop();
			Exprs = Expr + ";" + Exprs;
		}
		String Value = "class + " + Node.TypeInfo.ShortClassName + " ";
		if (Node.TypeInfo.SearchSuperMethodClass != null) {
			Value = Value + Node.TypeInfo.ShortClassName + " ";
		}
		this.push(Value + "{" + Exprs + "}");

		return true;
	}
}
