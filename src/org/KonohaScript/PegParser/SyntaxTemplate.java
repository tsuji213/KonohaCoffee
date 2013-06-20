package org.KonohaScript.PegParser;

import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaType;
import org.KonohaScript.JUtils.KonohaConst;
import org.KonohaScript.JUtils.KonohaDebug;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.Parser.KonohaGrammar;
import org.KonohaScript.Parser.TypeEnv;
import org.KonohaScript.Parser.UntypedNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public abstract class SyntaxTemplate extends KonohaGrammar {
	String		Name;
	KonohaArray	Childrens;

	public SyntaxTemplate(String Name) {
		this.Name = Name;
		this.Childrens = null;
	}

	public void Init(KonohaNameSpace NameSpace, PegParser Module) {
	}

	public int Fail(String SyntaxName, PegParser Parser) {
		this.Report("Fail " + SyntaxName);
		return -1;
	}

	public int BackTrack(PegParser Parser, int pos0, int thunkpos0, int NodeSize0, String message) {
		Parser.Cursor = pos0;
		Parser.ThunkPos = thunkpos0;
		this.Report("BackTrack " + message);
		return NodeSize0;
	}

	public int Match(PegParser Parser, TokenList TokenList) {

		return -1;
	}

	public void Report(String Message) {
		//System.out.println(Message);
	}

	public TypedNode TypePegParser(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		//System.out.println("Syntax : " + this.Name);
		//System.out.println("Node : " + UNode);
		SyntaxAcceptor Acceptor = (SyntaxAcceptor) UNode.NodeList.get(SyntaxAcceptor.AcceptorOffset);
		return Acceptor.TypeCheck(Gamma, UNode, TypeInfo);
	}

	public int ParseUNUSED(UntypedNode UNode, TokenList TokenList, int BeginIdx, int EndIdx, int ParseOption) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return KonohaConst.NoMatch;
	}

	public TypedNode TypeUNUSED(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaDebug.P("** Syntax " + UNode.Syntax + " is undefined **");
		return null;
	}
}