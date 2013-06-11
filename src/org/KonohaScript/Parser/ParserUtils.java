package org.KonohaScript.Parser;

import org.KonohaScript.KonohaType;
import org.KonohaScript.TypeEnv;
import org.KonohaScript.UntypedNode;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.SyntaxTree.TypedNode;

class intLiteral0 extends SyntaxAcceptor {
	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("intLiteral0 : " + NodeSize);
		UntypedNode Node = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		Parser.Push(Node);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

class intLiteralSyntax extends SyntaxTemplate {
	public intLiteralSyntax() {
		super("$intLiteral");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new intLiteral0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $intLiteral");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $intLiteral");
		if(Parser.MatchToken("$IntegerLiteral", TokenList, Parser.Cursor) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$intLiteral", Parser, pos0, NodeSize);
		}
		Parser.Cursor = pos0;
		Parser.ThunkPos = thunkpos0;
		NodeSize = NodeSize0;
		this.Report("BackTrack $intLiteral 0");
		return this.Fail("$intLiteral", Parser);
	}
}

class stringLiteral0 extends SyntaxAcceptor {
	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("stringLiteral0 : " + NodeSize);
		UntypedNode Node = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		Parser.Push(Node);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

class stringLiteralSyntax extends SyntaxTemplate {
	public stringLiteralSyntax() {
		super("$stringLiteral");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new stringLiteral0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $stringLiteral");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $stringLiteral");
		if(Parser.MatchToken("$StringLiteral", TokenList, Parser.Cursor) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$stringLiteral", Parser, pos0, NodeSize);
		}
		Parser.Cursor = pos0;
		Parser.ThunkPos = thunkpos0;
		NodeSize = NodeSize0;
		this.Report("BackTrack $stringLiteral 0");
		return this.Fail("$stringLiteral", Parser);
	}
}

class Symbol0 extends SyntaxAcceptor {

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("Symbol0 : " + NodeSize);
		UntypedNode Node = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		Parser.Push(Node);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

class SymbolSyntax extends SyntaxTemplate {
	public SymbolSyntax() {
		super("$Symbol");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new Symbol0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Symbol");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $Symbol");
		if(Parser.MatchToken("$Symbol", TokenList, Parser.Cursor) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$Symbol", Parser, pos0, NodeSize);
		}
		Parser.Cursor = pos0;
		Parser.ThunkPos = thunkpos0;
		NodeSize = NodeSize0;
		this.Report("BackTrack $Symbol 0");
		return this.Fail("$Symbol", Parser);
	}
}

class Type0 extends SyntaxAcceptor {

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("Type0 : " + NodeSize);
		UntypedNode Node = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		Parser.Push(Node);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

class TypeTokenSyntax extends SyntaxTemplate {
	public TypeTokenSyntax() {
		super("$Type");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new Type0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Type");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $Type");
		if(Parser.MatchToken("$Type", TokenList, Parser.Cursor) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$Type", Parser, pos0, NodeSize);
		}
		Parser.Cursor = pos0;
		Parser.ThunkPos = thunkpos0;
		NodeSize = NodeSize0;
		this.Report("BackTrack $Type 0");
		return this.Fail("$Type", Parser);
	}
}