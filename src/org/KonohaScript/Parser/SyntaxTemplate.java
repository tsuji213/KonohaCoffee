package org.KonohaScript.Parser;

import org.KonohaScript.KonohaConst;
import org.KonohaScript.KonohaDebug;
import org.KonohaScript.KonohaType;
import org.KonohaScript.TypeEnv;
import org.KonohaScript.UntypedNode;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.SyntaxTree.TypedNode;

public abstract class SyntaxTemplate {
	String		Name;
	KonohaArray	Childrens;

	public SyntaxTemplate(String Name) {
		this.Name = Name;
		this.Childrens = null;
	}

	public void Init(SyntaxModule Module) {
	}

	public int Fail(String SyntaxName, SyntaxModule Parser) {
		this.Report("Fail " + SyntaxName);
		return -1;
	}

	public int BackTrack(SyntaxModule Parser, int pos0, int thunkpos0, int NodeSize0, String message) {
		Parser.Cursor = pos0;
		Parser.ThunkPos = thunkpos0;
		this.Report("BackTrack " + message);
		return NodeSize0;
	}

	public int Match(SyntaxModule Parser, TokenList TokenList) {

		return -1;
	}

	public void Report(String Message) {
		//System.out.println(Message);
	}

	public TypedNode TypeSyntaxModule(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
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