package org.KonohaScript.Tester;

import org.KonohaScript.Konoha;
import org.KonohaScript.KonohaBuilder;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.JUtils.KonohaConst;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.Parser.KonohaGrammar;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.Parser.TypeEnv;
import org.KonohaScript.Parser.UntypedNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public abstract class KParserTester extends KTestCase {

	protected Object CompileAndCheck(KonohaNameSpace NameSpace, String Source) {
		TokenList BufferList = NameSpace.Tokenize(Source, 0);
		TokenList TokenList = new TokenList();
		NameSpace.PreProcess(BufferList, 0, BufferList.size(), TokenList);
		KonohaToken.DumpTokenList(0, "Dump::", TokenList, 0, TokenList.size());
		UntypedNode UNode = NameSpace.Parser.ParseNewNode(NameSpace, null, TokenList, 0, TokenList.size(), 0);

		System.out.println("untyped tree: " + UNode);
		TypeEnv Gamma = new TypeEnv(NameSpace, null);
		TypedNode TNode = TypeEnv.TypeCheckEachNode(Gamma, UNode, Gamma.VoidType, KonohaConst.DefaultTypeCheckPolicy);
		KonohaBuilder Builder = NameSpace.GetBuilder();
		Object ResultValue = Builder.EvalAtTopLevel(TNode, NameSpace.GetGlobalObject());

		return ResultValue;
	}

	Konoha						konoha;
	protected KonohaNameSpace	NameSpace;

	public void Init(KonohaGrammar Grammar) {
		this.konoha = new Konoha(Grammar, "org.KonohaScript.CodeGen.ASTInterpreter");
		this.NameSpace = this.konoha.DefaultNameSpace;
	}

	@Override
	public void Exit() {
	}
}
