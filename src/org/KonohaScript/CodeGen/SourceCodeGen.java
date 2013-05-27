package org.KonohaScript.CodeGen;

import java.util.ArrayList;

import org.KonohaScript.KonohaMethod;
import org.KonohaScript.SyntaxTree.AndNode;
import org.KonohaScript.SyntaxTree.BoxNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.DefineNode;
import org.KonohaScript.SyntaxTree.DefineClassNode;
import org.KonohaScript.SyntaxTree.ErrorNode;
import org.KonohaScript.SyntaxTree.FunctionNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.JumpNode;
import org.KonohaScript.SyntaxTree.LabelNode;
import org.KonohaScript.SyntaxTree.LoopNode;
import org.KonohaScript.SyntaxTree.ApplyNode;
import org.KonohaScript.SyntaxTree.NewNode;
import org.KonohaScript.SyntaxTree.NullNode;
import org.KonohaScript.SyntaxTree.OrNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.SwitchNode;
import org.KonohaScript.SyntaxTree.ThrowNode;
import org.KonohaScript.SyntaxTree.TryNode;
import org.KonohaScript.SyntaxTree.TypedNode;

class IndentGenerator {
	private int		level						= 0;
	private String	currentLevelIndentString	= "";
	private String	indentString				= "\t";

	public IndentGenerator() {
	}

	public IndentGenerator(int tabstop) {
		this.indentString = repeat(" ", tabstop);
	}

	private static String repeat(String str, int n) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < n; ++i) {
			builder.append(str);
		}
		return builder.toString();
	}

	public void setLevel(int level) {
		if (level < 0)
			level = 0;
		if (this.level != level) {
			this.level = level;
			currentLevelIndentString = repeat(indentString, level);
		}
	}

	public void indent(int n) {
		setLevel(level + n);
	}

	public String get() {
		return currentLevelIndentString;
	}

	public String getAndIndent(int diffLevel) {
		String current = currentLevelIndentString;
		indent(diffLevel);
		return current;
	}

	public String indentAndGet(int diffLevel) {
		indent(diffLevel);
		return currentLevelIndentString;
	}
}

public abstract class SourceCodeGen extends CodeGenerator implements ASTVisitor {
	private final ArrayList<String>		Program;
	private final ArrayList<Integer>	CurrentProgramSize;

	protected final IndentGenerator		indentGenerator	= new IndentGenerator(4);
	private static String[]				binaryOpList	= { "+", "-", "*", "/", "<", "<=", ">", ">=", "==", "!=", "&&", "||", "&", "|", "^", "<<", ">>" };

	public SourceCodeGen() {
		this(null);
	}

	public SourceCodeGen(KonohaMethod MethodInfo) {
		super(MethodInfo);
		this.Program = new ArrayList<String>();
		this.CurrentProgramSize = new ArrayList<Integer>();
	}

	protected boolean isMethodBinaryOperator(ApplyNode Node) {
		String methodName = Node.Method.MethodName;
		for (String op : binaryOpList) {
			if (op.equals(methodName))
				return true;
		}
		return false;
	}

	protected int getProgramSize() {
		return this.Program.size();
	}

	protected String pop() {
		return this.Program.remove(this.Program.size() - 1);
	}

	protected String[] PopN(int n) {
		String[] array = new String[n];
		for (int i = 0; i < n; ++i) {
			array[i] = pop();
		}
		return array;
	}
	
	protected String[] PopNReverse(int n) {
		String[] array = new String[n];
		for (int i = 0; i < n; ++i) {
			array[n - i - 1] = pop();
		}
		return array;
	}

	protected StringBuilder PopNWithModifier(StringBuilder builder, int n, boolean reverse, String prefix, String suffix, String delim) {
		if (prefix == null) {
			prefix = "";
		}
		if (suffix == null) {
			suffix = "";
		}
		if (delim == null) {
			delim = "";
		}
		String[] array = reverse ? PopNReverse(n) : PopN(n);
		for (int i = 0; i < n; ++i) {
			if (i > 0) {
				builder.append(delim);
			}
			builder.append(prefix);
			builder.append(array[i]);
			builder.append(suffix);
		}
		return builder;
	}
	
	protected String PopNWithModifier(int n, boolean reverse, String prefix, String suffix, String delim) {
		return PopNWithModifier(new StringBuilder(), n, reverse, prefix, suffix, delim).toString();
	}
	
	protected String PopNAndJoin(int n, String delim) {
		return PopNAndJoin(new StringBuilder(), n, delim).toString();
	}

	protected StringBuilder PopNAndJoin(StringBuilder builder, int n, String delim) {
		return PopNWithModifier(builder, n, false, null, null, delim);
	}

	protected String PopNReverseAndJoin(int n, String delim) {
		return PopNReverseAndJoin(new StringBuilder(), n, delim).toString();
	}

	protected StringBuilder PopNReverseAndJoin(StringBuilder builder, int n, String delim) {
		return PopNWithModifier(builder, n, true, null, null, delim);
	}

	protected String PopNReverseWithSuffix(int n, String suffix) {
		return PopNReverseWithSuffix(new StringBuilder(), n, suffix).toString();
	}

	protected StringBuilder PopNReverseWithSuffix(StringBuilder builder, int n, String suffix) {
		return PopNWithModifier(builder, n, true, null, suffix, null);
	}

	protected String PopNWithSuffix(int n, String suffix) {
		return PopNWithSuffix(new StringBuilder(), n, suffix).toString();
	}

	protected StringBuilder PopNWithSuffix(StringBuilder builder, int n, String suffix) {
		return PopNWithModifier(builder, n, false, null, suffix, null);
	}

	protected String PopNReverseWithPrefix(int n, String prefix) {
		return PopNReverseWithPrefix(new StringBuilder(), n, prefix).toString();
	}

	protected StringBuilder PopNReverseWithPrefix(StringBuilder builder, int n, String prefix) {
		return PopNWithModifier(builder, n, true, prefix, null, null);
	}

	protected String PopNWithPrefix(int n, String prefix) {
		return PopNWithSuffix(new StringBuilder(), n, prefix).toString();
	}

	protected StringBuilder PopNWithPrefix(StringBuilder builder, int n, String prefix) {
		return PopNWithModifier(builder, n, false, prefix, null, null);
	}
	
	protected int PopProgramSize() {
		return this.CurrentProgramSize.remove(this.CurrentProgramSize.size() - 1);
	}

	protected void push(String Program) {
		this.Program.add(Program);
	}

	protected void PushProgramSize() {
		this.CurrentProgramSize.add(this.Program.size());
	}

	@Override
	public boolean Visit(TypedNode Node) {
		return Node.Evaluate(this);
	}

	@Override
	public void EnterDefine(DefineNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterConst(ConstNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterNew(NewNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterNull(NullNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterBox(BoxNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterApply(ApplyNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterAnd(AndNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterOr(OrNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterIf(IfNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterSwitch(SwitchNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterLoop(LoopNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterReturn(ReturnNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterLabel(LabelNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterJump(JumpNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterTry(TryNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterThrow(ThrowNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterFunction(FunctionNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterError(ErrorNode Node) {
		/* do nothing */
	}

	@Override
	public void EnterDefineClass(DefineClassNode Node) {
		/* do nothing */
	}
}