package org.KonohaScript.CodeGen;

import java.util.ArrayList;
import java.util.HashMap;

import org.KonohaScript.KClass;
import org.KonohaScript.KMethod;
import org.KonohaScript.SyntaxTree.AndNode;
import org.KonohaScript.SyntaxTree.ApplyNode;
import org.KonohaScript.SyntaxTree.AssignNode;
import org.KonohaScript.SyntaxTree.BlockNode;
import org.KonohaScript.SyntaxTree.BoxNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.DefineClassNode;
import org.KonohaScript.SyntaxTree.DefineNode;
import org.KonohaScript.SyntaxTree.ErrorNode;
import org.KonohaScript.SyntaxTree.FieldNode;
import org.KonohaScript.SyntaxTree.FunctionNode;
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

public class LeafJSCodeGen extends SourceCodeGen implements ASTVisitor {
	private final boolean	UseLetKeyword	= false;

	private ArrayList<HashMap<String, Integer>> LocalVariableRenameTables = new ArrayList<HashMap<String, Integer>>();

	public LeafJSCodeGen() {
		super(null);
	}

	private void AddLocalVariableRenameRule(String Name){
		int nameUsedTimes = 0;
		int N = LocalVariableRenameTables.size();
		if(!LocalVariableRenameTables.get(N - 1).containsKey(Name)){
			for(int i = 0; i < N - 1; ++i){
				if(LocalVariableRenameTables.get(i).containsKey(Name)){
					nameUsedTimes++;
				}
			}
			LocalVariableRenameTables.get(N - 1).put(Name, nameUsedTimes);
		}
	}

	private String GetRenamedLocalName(String originalName){
		int N = LocalVariableRenameTables.size();
		for(int i = N - 1; i >= 0; --i){
			HashMap<String, Integer> map = LocalVariableRenameTables.get(i);
			if(map.containsKey(originalName)){
				if(map.get(originalName) > 0){
					return originalName + map.get(originalName);
				}
			}
		}
		return originalName;
	}

	@Override
	Local AddLocal(KClass Type, String Name) {
		AddLocalVariableRenameRule(Name);
		return super.AddLocal(Type, Name);
	};

	@Override Local AddLocalVarIfNotDefined(KClass Type, String Name) {
		AddLocalVariableRenameRule(Name);
		return super.AddLocalVarIfNotDefined(Type, Name);
	};

	@Override
	public void Prepare(KMethod Method) {
		this.LocalVals.clear();
		this.MethodInfo = Method;
		this.AddLocal(Method.ClassInfo, "this");
	}

	@Override
	public void Prepare(KMethod Method, ArrayList<Local> params) {
		this.Prepare(Method);
		for(int i = 0; i < params.size(); i++) {
			Local local = params.get(i);
			this.AddLocal(local.TypeInfo, local.Name);
		}
	}

	@Override
	public CompiledMethod Compile(TypedNode Block) {
		this.Visit(Block);
		CompiledMethod Mtd = new CompiledMethod(this.MethodInfo);
		assert (this.getProgramSize() == 1);
		String Source = this.pop();
		if(this.MethodInfo != null && this.MethodInfo.MethodName.length() > 0) {
			Local thisNode = this.FindLocalVariable("this");
			StringBuilder FuncBuilder = new StringBuilder();

			// FuncBuilder.append(thisNode.TypeInfo.ShortClassName);
			// FuncBuilder.append(".");
			String MethodName = this.MethodInfo.MethodName;
			FuncBuilder.append("var ");
			FuncBuilder.append(MethodName);
			FuncBuilder.append(" = function(");

			for(int i = 1; i < this.LocalVals.size(); i++) {
				Local local = this.GetLocalVariableByIndex(i);
				if(i != 1) {
					FuncBuilder.append(", ");
				}
				FuncBuilder.append(local.Name);
			}
			FuncBuilder.append(") ");
			FuncBuilder.append(Source);
			FuncBuilder.append(";");
			Source = FuncBuilder.toString();
		}
		Mtd.CompiledCode = Source;
		return Mtd;
	}

	@Override
	public boolean ExitDefine(DefineNode Node) {
		return true;
	}

	@Override
	public boolean ExitConst(ConstNode Node) {
		this.push(Node.ConstValue.toString());
		return true;
	}

	@Override
	public boolean ExitNew(NewNode Node) {
		this.push("new " + Node.TypeInfo.ShortClassName.toString() + "()");
		return true;
	}

	@Override
	public boolean ExitNull(NullNode Node) {
		this.push("null");
		return true;
	}

	@Override
	public void EnterLocal(LocalNode Node) {
		this.AddLocalVarIfNotDefined(Node.TypeInfo, Node.FieldName);
	}

	@Override
	public boolean ExitLocal(LocalNode Node) {
		this.push(Node.FieldName);
		return true;
	}

	@Override
	public void EnterField(FieldNode Node) {
		Local local = this.FindLocalVariable(Node.TermToken.ParsedText);
		assert (local != null);
	}

	@Override
	public boolean ExitField(FieldNode Node) {
		// String Expr = Node.TermToken.ParsedText;
		// push(Expr + "." + Node.TypeInfo.FieldNames.get(Node.Xindex));
		// push(Expr);
		// FIXME
		this.push(Node.SourceToken.ParsedText);
		return true;

	}

	@Override
	public boolean ExitBox(BoxNode Node) {
		/* do nothing */
		return true;

	}

	@Override
	public boolean ExitApply(ApplyNode Node) {
		String methodName = Node.Method.MethodName;
		if(this.isMethodBinaryOperator(Node)) {
			String params = this.pop();
			String thisNode = this.pop();
			this.push(thisNode + " " + methodName + " " + params);
		} else {
			String params = "(" + this.PopNReverseAndJoin(Node.Params.size() - 1, ", ") + ")";
			String thisNode = this.pop();
			this.push(thisNode + "." + methodName + params);
		}
		return true;
	}

	@Override
	public boolean ExitAnd(AndNode Node) {
		String Right = this.pop();
		String Left = this.pop();
		this.push(Left + " && " + Right);
		return true;
	}

	@Override
	public boolean ExitOr(OrNode Node) {
		String Right = this.pop();
		String Left = this.pop();
		this.push(Left + " || " + Right);
		return true;
	}

	@Override
	public void EnterAssign(AssignNode Node) {
		this.AddLocalVarIfNotDefined(Node.TypeInfo, Node.TermToken.ParsedText);
	}

	@Override
	public boolean ExitAssign(AssignNode Node) {
		String Right = this.pop();
		this.push((this.UseLetKeyword ? "let " : "var ") + Node.TermToken.ParsedText + " = " + Right);
		return true;
	}

	@Override
	public void EnterLet(LetNode Node) {
		this.AddLocalVarIfNotDefined(Node.TypeInfo, Node.TermToken.ParsedText);
	}

	@Override
	public boolean ExitLet(LetNode Node) {
		String Block = this.pop();
		String Right = this.pop();
		this.push(Node.TermToken.ParsedText + " = " + Right + Block);
		return true;
	}

	@Override
	public void EnterBlock(BlockNode Node) {
		this.PushProgramSize();
		this.indentGenerator.indent(1);
	}

	@Override
	public boolean ExitBlock(BlockNode Node) {
		IndentGenerator g = this.indentGenerator;
		int Size = this.getProgramSize() - this.PopProgramSize();
		this.push("{\n" + g.get() + this.PopNReverseAndJoin(Size, ";\n" + g.get()) + ";\n" + g.indentAndGet(-1) + "}");
		return true;
	}

	@Override
	public boolean ExitIf(IfNode Node) {
		String ElseBlock = this.pop();
		String ThenBlock = this.pop();
		String CondExpr = this.pop();
		if(Node.ElseNode != null) {
			this.push("if(" + CondExpr + ") " + ThenBlock + " else " + ElseBlock);
		} else {
			this.push("if(" + CondExpr + ") " + ThenBlock);
		}
		return true;
	}

	@Override
	public boolean ExitSwitch(SwitchNode Node) {
		int Size = Node.Labels.size();
		String Exprs = "";
		for(int i = 0; i < Size; i = i + 1) {
			String Label = Node.Labels.get(Size - i);
			String Block = this.pop();
			Exprs = "case " + Label + ":" + Block + Exprs;
		}
		String CondExpr = this.pop();
		this.push("switch (" + CondExpr + ") {" + Exprs + "}");
		return true;
	}

	@Override
	public boolean ExitLoop(LoopNode Node) {
		String LoopBody = this.pop();
		String IterExpr = this.pop();
		String CondExpr = this.pop();
		this.push("while (" + CondExpr + ") {" + LoopBody + IterExpr + "}");
		return true;

	}

	@Override
	public boolean ExitReturn(ReturnNode Node) {
		String Expr = this.pop();
		this.push("return " + Expr);
		return false;
	}

	@Override
	public boolean ExitLabel(LabelNode Node) {
		String Label = Node.Label;
		if(Label.compareTo("continue") == 0) {
			this.push("");
		} else if(Label.compareTo("continue") == 0) {
			this.push("");
		} else {
			this.push(Label + ":");
		}
		return true;
	}

	@Override
	public boolean ExitJump(JumpNode Node) {
		String Label = Node.Label;
		if(Label.compareTo("continue") == 0) {
			this.push("continue;");
		} else if(Label.compareTo("continue") == 0) {
			this.push("break;");
		} else {
			this.push("goto " + Label);
		}
		return false;
	}

	@Override
	public boolean ExitTry(TryNode Node) {
		String FinallyBlock = this.pop();
		String CatchBlocks = this.PopNReverseWithPrefix(Node.CatchBlock.size(), "catch() ");
		String TryBlock = this.pop();
		this.push("try " + TryBlock + "" + CatchBlocks + "finally " + FinallyBlock);
		return true;
	}

	@Override
	public boolean ExitThrow(ThrowNode Node) {
		String Expr = this.pop();
		this.push("throw " + Expr + ";");
		return false;
	}

	@Override
	public boolean ExitFunction(FunctionNode Node) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean ExitError(ErrorNode Node) {
		String Expr = this.pop();
		this.push("throw new Exception(" + Expr + ");");
		return false;
	}

	@Override
	public boolean ExitDefineClass(DefineClassNode Node) {
		String Exprs = this.PopNReverseWithSuffix(Node.Fields.size(), ";");
		String Value = "class + " + Node.TypeInfo.ShortClassName + " ";
		if(Node.TypeInfo.SearchSuperMethodClass != null) {
			Value = Value + Node.TypeInfo.ShortClassName + " ";
		}
		this.push(Value + "{" + Exprs + "}");

		return true;
	}
}
