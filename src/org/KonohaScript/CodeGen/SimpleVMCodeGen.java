package org.KonohaScript.CodeGen;

import java.util.ArrayList;

import org.KonohaScript.AST.AndNode;
import org.KonohaScript.AST.AssignNode;
import org.KonohaScript.AST.BlockNode;
import org.KonohaScript.AST.BoxNode;
import org.KonohaScript.AST.ConstNode;
import org.KonohaScript.AST.DoneNode;
import org.KonohaScript.AST.ErrorNode;
import org.KonohaScript.AST.FieldNode;
import org.KonohaScript.AST.FunctionNode;
import org.KonohaScript.AST.IfNode;
import org.KonohaScript.AST.JumpNode;
import org.KonohaScript.AST.LabelNode;
import org.KonohaScript.AST.LetNode;
import org.KonohaScript.AST.LocalNode;
import org.KonohaScript.AST.LoopNode;
import org.KonohaScript.AST.MethodCallNode;
import org.KonohaScript.AST.NewNode;
import org.KonohaScript.AST.NullNode;
import org.KonohaScript.AST.OrNode;
import org.KonohaScript.AST.ReturnNode;
import org.KonohaScript.AST.SwitchNode;
import org.KonohaScript.AST.ThrowNode;
import org.KonohaScript.AST.TryNode;
import org.KonohaScript.AST.TypedNode;

class Local {
	int Index;
	String Name;

	public Local(int index, String name) {
		this.Index = index;
		this.Name = name;
	}
}

public class SimpleVMCodeGen implements CodeGenerator {
	ArrayList<String> Program;
	ArrayList<Local> LocalVals;

	public SimpleVMCodeGen() {
		this.Program = new ArrayList<String>();
		this.LocalVals = new ArrayList<Local>();
	}

	@Override
	public CompiledMethod Compile(TypedNode Block) {
		Visit(Block);
		CompiledMethod Mtd = new CompiledMethod();
		assert (this.Program.size() == 1);
		Mtd.CompiledCode = this.Program.remove(0);
		return Mtd;
	}

	@Override
	public void Visit(TypedNode Node) {
		Node.Evaluate(this);
	}

	@Override
	public void EnterDone(DoneNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitDone(DoneNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterConst(ConstNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitConst(ConstNode Node) {
		if (Node.IsUnboxedNode()) {
			if (/* IsInt(Node) */true) {
				this.Program.add(Integer.toString((int) Node.ConstValue));
			} else if (/* IsFloat(Node) */false) {
				this.Program.add(Double.toString(Double
						.longBitsToDouble(Node.ConstValue)));
			} else if (/* IsBoolean(Node) */false) {
				this.Program.add(Boolean.toString(Node.ConstValue == 0));
			}
		} else {
			this.Program.add(Node.ConstObject.toString());
		}
	}

	@Override
	public void EnterNew(NewNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitNew(NewNode Node) {
		this.Program.add(Node.ClassInfo.ShortClassName.toString());
	}

	@Override
	public void EnterNull(NullNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitNull(NullNode Node) {
		this.Program.add("null");
	}

	Local AddLocalVarIfNotDefined(String Text) {
		for (Local l : this.LocalVals) {
			if (l.Name.compareTo(Text) == 0) {
				return l;
			}
		}
		Local local = new Local(this.LocalVals.size(), Text);
		this.LocalVals.add(local);
		return local;
	}

	@Override
	public void EnterLocal(LocalNode Node) {
		AddLocalVarIfNotDefined(Node.TermToken.ParsedText);
	}

	@Override
	public void ExitLocal(LocalNode Node) {
		this.Program.add(Node.TermToken.ParsedText);

	}

	@Override
	public void EnterField(FieldNode Node) {
		AddLocalVarIfNotDefined(Node.TermToken.ParsedText);
	}

	@Override
	public void ExitField(FieldNode Node) {
		String Expr = Node.TermToken.ParsedText;
		this.Program.add(Expr + "."
				+ Node.ClassInfo.FieldNames.get(Node.Xindex));
	}

	@Override
	public void EnterBox(BoxNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitBox(BoxNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterMethodCall(MethodCallNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitMethodCall(MethodCallNode Node) {
		String thisNode = this.Program.remove(this.Program.size() - 1);
		String Params = "";
		String methodName = "mtd";
		int ParamSize = Node.Params.size();
		for (int i = 0; i < ParamSize; i = i + 1) {
			String Expr = this.Program.remove(this.Program.size() - 1);
			if (i != 0) {
				Params = "," + Params;
			}
			Params = Expr + Params;
		}
		this.Program.add(thisNode + "." + methodName + "(" + Params + ")");
	}

	@Override
	public void EnterAnd(AndNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitAnd(AndNode Node) {
		String Left = this.Program.remove(this.Program.size() - 1);
		String Right = this.Program.remove(this.Program.size() - 1);
		this.Program.add(Left + " && " + Right);
	}

	@Override
	public void EnterOr(OrNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitOr(OrNode Node) {
		String Left = this.Program.remove(this.Program.size() - 1);
		String Right = this.Program.remove(this.Program.size() - 1);
		this.Program.add(Left + " || " + Right);
	}

	@Override
	public void EnterAssign(AssignNode Node) {
		AddLocalVarIfNotDefined(Node.TermToken.ParsedText);
	}

	@Override
	public void ExitAssign(AssignNode Node) {
		String Right = this.Program.remove(this.Program.size() - 1);
		this.Program.add(Node.TermToken.ParsedText + " = " + Right + ";");
	}

	@Override
	public void EnterLet(LetNode Node) {
		AddLocalVarIfNotDefined(Node.TermToken.ParsedText);
	}

	@Override
	public void ExitLet(LetNode Node) {
		String Right = this.Program.remove(this.Program.size() - 1);
		String Block = this.Program.remove(this.Program.size() - 1);
		this.Program.add(Node.TermToken.ParsedText + " = " + Right + ";"
				+ Block);
	}

	@Override
	public void EnterBlock(BlockNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitBlock(BlockNode Node) {
		String Exprs = "";
		int Size = Node.ExprList.size();
		for (int i = 0; i < Size; i = i + 1) {
			String Expr = this.Program.remove(this.Program.size() - 1);
			if (i != 0) {
				Exprs = "," + Exprs;
			}
			Exprs = Expr + Exprs;
		}
		this.Program.add("{" + Exprs + "}");
	}

	@Override
	public void EnterIf(IfNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitIf(IfNode Node) {
		String ElseBlock = this.Program.remove(this.Program.size() - 1);
		String ThenBlock = this.Program.remove(this.Program.size() - 1);
		String CondExpr = this.Program.remove(this.Program.size() - 1);
		this.Program.add("if (" + CondExpr + ") {" + ThenBlock + " else "
				+ ElseBlock);
	}

	@Override
	public void EnterSwitch(SwitchNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitSwitch(SwitchNode Node) {
		int Size = Node.Labels.size();
		String Exprs = "";
		for (int i = 0; i < Size; i = i + 1) {
			String Label = Node.Labels.get(Size - i);
			String Block = this.Program.remove(this.Program.size() - 1);
			Exprs = "case " + Label + ":" + Block + Exprs;
		}
		String CondExpr = this.Program.remove(this.Program.size() - 1);
		this.Program.add("switch (" + CondExpr + ") {" + Exprs + "}");
	}

	@Override
	public void EnterLoop(LoopNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitLoop(LoopNode Node) {
		String LoopBody = this.Program.remove(this.Program.size() - 1);
		String IterExpr = this.Program.remove(this.Program.size() - 1);
		String CondExpr = this.Program.remove(this.Program.size() - 1);
		this.Program.add("while (" + CondExpr + ") {" + LoopBody + IterExpr
				+ "}");
	}

	@Override
	public void EnterReturn(ReturnNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitReturn(ReturnNode Node) {
		String Expr = this.Program.remove(this.Program.size() - 1);
		this.Program.add("return " + Expr + ";");
	}

	@Override
	public void EnterLabel(LabelNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitLabel(LabelNode Node) {
		String Label = Node.Label;
		if (Label.compareTo("continue") == 0) {
			this.Program.add("");
		} else if (Label.compareTo("continue") == 0) {
			this.Program.add("");
		} else {
			this.Program.add(Label + ":");
		}
	}

	@Override
	public void EnterJump(JumpNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitJump(JumpNode Node) {
		String Label = Node.Label;
		if (Label.compareTo("continue") == 0) {
			this.Program.add("continue;");
		} else if (Label.compareTo("continue") == 0) {
			this.Program.add("break;");
		} else {
			this.Program.add("goto " + Label);
		}
	}

	@Override
	public void EnterTry(TryNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitTry(TryNode Node) {
		String FinallyBlock = this.Program.remove(this.Program.size() - 1);
		String CatchBlocks = "";
		for (int i = 0; i < Node.CatchBlock.size(); i = i + 1) {
			String Block = this.Program.remove(this.Program.size() - 1);
			CatchBlocks = "catch() " + Block + CatchBlocks;
		}
		String TryBlock = this.Program.remove(this.Program.size() - 1);
		this.Program.add("try " + TryBlock + "" + CatchBlocks + FinallyBlock);
	}

	@Override
	public void EnterThrow(ThrowNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitThrow(ThrowNode Node) {
		String Expr = this.Program.remove(this.Program.size() - 1);
		this.Program.add("throw " + Expr + ";");
	}

	@Override
	public void EnterFunction(FunctionNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitFunction(FunctionNode Node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EnterError(ErrorNode Node) {
		/* do nothing */
	}

	@Override
	public void ExitError(ErrorNode Node) {
		String Expr = this.Program.remove(this.Program.size() - 1);
		this.Program.add("throw new Exception(" + Expr + ";");
	}
}
