package org.KonohaScript.Tester;

import org.KonohaScript.CodeGen.JVMCodeGenTest;
import org.KonohaScript.CodeGen.LeafJSCodeGenTest;
import org.KonohaScript.CodeGen.LLVMCodeGenTest;
import org.KonohaScript.Peg.MiniKonoha.SyntaxModuleParserTest;
public class KTestRunner extends KTestRunnerBase {
	public static void main(String[] args) {
		KTestRunner Runner = new KTestRunner();
		Runner.Run(new JVMCodeGenTest());
		Runner.Run(new LeafJSCodeGenTest());
		Runner.Run(new LLVMCodeGenTest());
		Runner.Run(new SyntaxModuleParserTest());
	}
}
