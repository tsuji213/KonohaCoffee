package org.KonohaScript.Grammar;

import java.util.ArrayList;

import org.KonohaScript.KonohaConst;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.Parser.KonohaGrammar;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.Parser.TypeEnv;
import org.KonohaScript.Parser.UntypedNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public final class ShellGrammar extends KonohaGrammar implements KonohaConst {

	MiniKonohaGrammar MiniKonoha = new MiniKonohaGrammar();

	// $(ls -la | grep .txt)
	public int ShellToken(KonohaNameSpace ns, String SourceText, int pos, TokenList ParsedTokenList) {
		int start = pos;
		int level = 1;
		pos++;
		if(SourceText.charAt(pos) != '('){
			return -1;
		}
		for(; pos < SourceText.length(); pos++) {
			char ch = SourceText.charAt(pos);
			if(ch == '\\') {
				pos++;
				continue;
			}
			if(ch == '(') {
				level++;
			}else if(ch == ')') {
				level--;
				if(level < 0){
					pos++;
					break;
				}
			}
		}
		KonohaToken Token = new KonohaToken(SourceText.substring(start, pos));
		Token.ResolvedSyntax = ns.GetSyntax("$Shell");
		ParsedTokenList.add(Token);
		return pos;
	}
	
	/*
	 * Command: command name & arguments & redirect
	 * CommandLine: piped commands
	 */
	
	private ArrayList<String> SplitIntoCommands(String CommandLine) {
		ArrayList<String> Commands = new ArrayList<String>();
		int start = 0;
		for(int pos = 0; pos < CommandLine.length(); pos++) {
			char ch = CommandLine.charAt(pos);
			if(ch == '\\') {
				pos++;
				continue;
			}else if(ch == '|') {
				Commands.add(CommandLine.substring(start, pos - 1));
				start = pos + 1;
			}else if(ch == ' ' && start == pos) {
				start++;
			}
		}
		if(start < CommandLine.length() - 1){
			Commands.add(CommandLine.substring(start));
		}
		return Commands;
	}
	
	private ArrayList<String> SplitIntoCommandTokens(String Command){
		ArrayList<String> Tokens = new ArrayList<String>();
		int start = 0;
		int pos = 0;
		while(Command.charAt(pos) == ' '){
			pos++;
		}
		for(; pos < Command.length(); pos++) {
			char ch = Command.charAt(pos);
			if(ch == '\\') {
				pos++;
				continue;
			}else if(ch == ' ') {
				if(start == pos) {
					start++;
				}else{
					Tokens.add(Command.substring(start, pos).replaceAll("\\\\(.)", "$1"));
					start = pos + 1;
				}
			} 
		}
		if(start < Command.length() - 1){
			Tokens.add(Command.substring(start).replaceAll("\\\\(.)", "$1"));
		}
		return Tokens;
	}
	
	private String makeArgumentsString(ArrayList<String> Tokens){
		StringBuilder str = new StringBuilder();
		str.append("[\"");
		for(int i = 1; i < Tokens.size(); i++){
			if(i > 1){
				str.append("\", \"");
			}
			String tk = Tokens.get(i);
			if(tk.equals(">") || tk.equals("<")){
				i++;
				continue;
			}
			str.append(tk);
		}
		str.append("\"]");
		return str.toString();
	}

	public int ParseShell(UntypedNode UNode, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KonohaToken ShellToken = TokenList.get(BeginIdx);
		String CommandLine = ShellToken.ParsedText.substring(2, ShellToken.ParsedText.length() - 1);

		//KonohaDebug.P("Shell: " + CommandLine);
		
		// split commandline by pipe
		ArrayList<String> Commands = SplitIntoCommands(CommandLine);
		
		StringBuilder SourceBuilder = new StringBuilder();
		
		int i = 0;
		for(String str : Commands){
			ArrayList<String> Tokens = SplitIntoCommandTokens(str);
			String procName = "p" + i;
			SourceBuilder.append("SubProc " + procName + " = new SubProc(\"" + Tokens.get(0) + "\");\n");
			SourceBuilder.append(procName + ".SetArguments(" + makeArgumentsString(Tokens) + ");\n");
			if(i > 0){
				SourceBuilder.append("p" + (i-1) + ".pipe(p" + i + ");\n");
			}
			SourceBuilder.append(procName + ".fg();\n");
			i++;
		}
		System.out.println(SourceBuilder.toString());
		
		KonohaNameSpace ns = UNode.NodeNameSpace;
		
		TokenList BufferList = ns.Tokenize(SourceBuilder.toString(), ShellToken.uline);
		int next = BufferList.size();
		ns.PreProcess(BufferList, 0, next, BufferList);
		UntypedNode ShellUNode = ns.Parser.ParseNewNode(ns, null, BufferList, next, BufferList.size(), KonohaConst.AllowEmpty);
		UNode.AddParsedNode(ShellUNode);
		System.out.println("untyped tree: " + ShellUNode);
		
		return BeginIdx + 1;
	}

	public TypedNode TypeShell(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		//KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return null;
	}

	@Override
	public void LoadDefaultSyntax(KonohaNameSpace NameSpace) {
		MiniKonoha.LoadDefaultSyntax(NameSpace);

		NameSpace.AddTokenFunc("$", this, "ShellToken");
		NameSpace.DefineSyntax("$Shell", Term, this, "Shell");
	}
}
