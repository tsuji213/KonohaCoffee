package org.KonohaScript.PegParser;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.TypeEnv;
import org.KonohaScript.UntypedNode;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.SyntaxTree.TypedNode;

public abstract class SyntaxAcceptor {
	public static final int	AcceptorOffset	= 0;
	public static final int	ListOffset		= AcceptorOffset + 1;

	public UntypedNode CreateNodeWithSyntax(SyntaxModule Parser, KonohaToken Token, String SyntaxName) {
		UntypedNode Node = new UntypedNode(Parser.NameSpace, Token);
		Node.SetAtNode(AcceptorOffset, null);
		Node.NodeList.set(AcceptorOffset, this);
		Node.Syntax = Parser.NameSpace.GetSyntax(SyntaxName);
		return Node;
	}

	public TypedNode TypeBinaryOperator(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return KonohaCallExpressionTypeChecker.TypeCheckMethodCall(Gamma, UNode, TypeInfo);
	}

	public void CreateBinaryOperator(SyntaxModule Parser, int NodeSize, String SyntaxName) {
		int Index = 0;
		UntypedNode Left = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		while(Index < NodeSize) {
			KonohaToken OperatorToken = (KonohaToken) Parser.Get(Index, NodeSize);
			UntypedNode Node = this.CreateNodeWithSyntax(Parser, OperatorToken, SyntaxName);
			Index = Index + 1;

			UntypedNode Right = (UntypedNode) Parser.Get(Index, NodeSize);
			Node.SetAtNode(ListOffset, Left);
			Node.SetAtToken(ListOffset + 1, OperatorToken);
			Node.SetAtNode(ListOffset + 2, Right);
			Left = Node;
			Index = Index + 1;
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, Left);
		}
	}

	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		return -1;
	}

	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}

	public void Report(String Message, int NodeSize) {
		//System.out.println(Message + " : " + NodeSize);
	}
}