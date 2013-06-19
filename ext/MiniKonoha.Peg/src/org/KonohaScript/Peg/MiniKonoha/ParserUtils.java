package org.KonohaScript.Peg.MiniKonoha;

import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.Parser.TypeEnv;
import org.KonohaScript.Parser.UntypedNode;
import org.KonohaScript.PegParser.SyntaxAcceptor;
import org.KonohaScript.PegParser.SyntaxModule;
import org.KonohaScript.PegParser.SyntaxTemplate;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.TypedNode;

class intLiteral0 extends SyntaxAcceptor {
	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("intLiteral0", NodeSize);
		UntypedNode Node = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$intLiteral");
		Parser.Push(Node);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaToken Token = UNode.KeyToken;
		return new ConstNode(Gamma.IntType, Token, Integer.valueOf(Token.ParsedText));
	}
}

class intLiteralSyntax extends SyntaxTemplate {
	public intLiteralSyntax() {
		super("$intLiteral");
	}

	@Override
	public void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new intLiteral0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $intLiteral");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {

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
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("stringLiteral0", NodeSize);
		UntypedNode Node = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$stringLiteral");
		Parser.Push(Node);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaToken Token = UNode.KeyToken;
		/* FIXME: handling of escape sequence */
		return new ConstNode(Gamma.StringType, Token, Token.ParsedText);
	}
}

class stringLiteralSyntax extends SyntaxTemplate {
	public stringLiteralSyntax() {
		super("$stringLiteral");
	}

	@Override
	public void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new stringLiteral0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $stringLiteral");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {

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
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("Symbol0", NodeSize);
		UntypedNode Node = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$Symbol");
		Parser.Push(Node);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		// case: Symbol is LocalVariable
		String Name = UNode.KeyToken.ParsedText;
		TypeInfo = Gamma.GetLocalType(Name);
		if(TypeInfo != null) {
			return new LocalNode(TypeInfo, UNode.KeyToken, Name);
		}

		// case: Symbol is MethodName
		KonohaType BaseType = Gamma.GetLocalType("this");
		KonohaMethod Method = BaseType.LookupMethod(Name, -1);
		if(Method != null) {
			KonohaType MethodType = Gamma.GammaNameSpace.LookupHostLangType(KonohaMethod.class);
			return new ConstNode(MethodType, UNode.KeyToken, Method);
		}

		// case: Symbol is undefined name
		return Gamma.NewErrorNode(UNode.KeyToken, "undefined name: " + Name);
	}
}

class SymbolSyntax extends SyntaxTemplate {
	public SymbolSyntax() {
		super("$Symbol");
	}

	@Override
	public void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new Symbol0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Symbol");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {

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
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("Type0", NodeSize);
		Parser.Push(TokenList.get(BeginIdx));
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

class TypeTokenSyntax extends SyntaxTemplate {
	public TypeTokenSyntax() {
		super("$Type");
	}

	@Override
	public void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new Type0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Type");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {

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