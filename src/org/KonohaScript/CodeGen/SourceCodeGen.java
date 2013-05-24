package org.KonohaScript.CodeGen;

import java.util.ArrayList;

import org.KonohaScript.KMethod;
import org.KonohaScript.SyntaxTree.MethodCallNode;

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

public abstract class SourceCodeGen extends CodeGenerator {
	private ArrayList<String> Program;
	private ArrayList<Integer> CurrentProgramSize;

	protected final IndentGenerator indentGenerator = new IndentGenerator(4);
	private static String[] binaryOpList = {"+", "-", "*", "/", "<", "<=", ">", ">=", "==", "!=", "&&", "||", "&", "|", "^", "<<", ">>"};

	public SourceCodeGen() {
		this(null);
	}
	public SourceCodeGen(KMethod MethodInfo) {
		super(MethodInfo);
		this.Program = new ArrayList<String>();
		this.CurrentProgramSize = new ArrayList<Integer>();
	}

	protected boolean isMethodBinaryOperator(MethodCallNode Node) {
		String methodName = Node.Method.MethodName;
		for(String op : binaryOpList){
			if(op.equals(methodName)) return true;
		}
		return false;
	}

	protected int getProgramSize(){
		return this.Program.size();
	}

	protected String pop() {
		return this.Program.remove(this.Program.size() - 1);
	}

	private String[] PopN(int n) {
		String[] array = new String[n];
		for(int i = 0; i < n; ++i){
			array[i] = pop();
		}
		return array;
	}

	private String PopNAndJoin(int n, String delim) {
		return PopNAndJoin(new StringBuilder(), n ,delim).toString();
	}

	private StringBuilder PopNAndJoin(StringBuilder builder, int n, String delim) {
		if(delim == null){
			delim = "";
		}
		String[] array = PopN(n);
		for(int i = 0; i < n; ++i){
			if(i > 0){
				builder.append(delim);
			}
			builder.append(array[i]);
		}
		return builder;
	}

	private String[] PopNReverse(int n) {
		String[] array = new String[n];
		for(int i = 0; i < n; ++i){
			array[n-i-1] = pop();
		}
		return array;
	}

	protected String PopNReverseAndJoin(int n, String delim) {
		return PopNReverseAndJoin(new StringBuilder(), n ,delim).toString();
	}

	protected StringBuilder PopNReverseAndJoin(StringBuilder builder, int n, String delim) {
		if(delim == null){
			delim = "";
		}
		String[] array = PopNReverse(n);
		for(int i = 0; i < n; ++i){
			if(i > 0){
				builder.append(delim);
			}
			builder.append(array[i]);
		}
		return builder;
	}

	protected String PopNReverseWithSuffix(int n, String suffix) {
		return PopNReverseWithSuffix(new StringBuilder(), n ,suffix).toString();
	}

	protected StringBuilder PopNReverseWithSuffix(StringBuilder builder, int n, String suffix) {
		if(suffix == null){
			suffix = "";
		}
		String[] array = PopNReverse(n);
		for(int i = 0; i < n; ++i){
			builder.append(array[i]);
			builder.append(suffix);
		}
		return builder;
	}

	private String PopNWithSuffix(int n, String suffix) {
		return PopNWithSuffix(new StringBuilder(), n ,suffix).toString();
	}

	private StringBuilder PopNWithSuffix(StringBuilder builder, int n, String suffix) {
		if(suffix == null){
			suffix = "";
		}
		String[] array = PopN(n);
		for(int i = 0; i < n; ++i){
			builder.append(array[i]);
			builder.append(suffix);
		}
		return builder;
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

}