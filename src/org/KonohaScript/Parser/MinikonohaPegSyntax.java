package org.KonohaScript.Parser;

import org.KonohaScript.KLib.TokenList;

/*
[$SourceCode:
	[
		<Repeat:<Symbol:$TopLevelDefinition>>
	]
]
*/
class SourceCodeSyntax extends SyntaxTemplate {
	SourceCodeSyntax() {
		super("$SourceCode");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new TopLevelDefinitionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new SourceCodeSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $SourceCode");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $SourceCode");
		while(true) {
			if(Parser.Match("$TopLevelDefinition", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				continue;
			}
			return this.action0("$SourceCode", Parser, pos0, NodeSize);
		}
	}
}

/*
[$TopLevelDefinition:
	[
		<Symbol:$statement>
	]
	[
		<Symbol:$functionDefinition>
	]
]
*/
class TopLevelDefinitionSyntax extends SyntaxTemplate {
	TopLevelDefinitionSyntax() {
		super("$TopLevelDefinition");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new functionDefinitionSyntax(), false);
		Parser.AddSyntax(this, new statementSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new TopLevelDefinitionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $TopLevelDefinition");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new TopLevelDefinitionSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $TopLevelDefinition");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $TopLevelDefinition");
		if(Parser.Match("$statement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$TopLevelDefinition", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $TopLevelDefinition 0");
		if(Parser.Match("$functionDefinition", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action1("$TopLevelDefinition", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $TopLevelDefinition 0");
		return this.Fail("$TopLevelDefinition", Parser);
	}
}

/*
[$functionSignature:
	[
		<Symbol:$type>
		<Symbol:$identifier>
		<Symbol:$ParamDeclList>
	]
]
*/
class functionSignatureSyntax extends SyntaxTemplate {
	functionSignatureSyntax() {
		super("$functionSignature");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new typeSyntax(), false);
		Parser.AddSyntax(this, new ParamDeclListSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new functionSignatureSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $functionSignature");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $functionSignature");
		if(Parser.Match("$type", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$identifier", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.Match("$ParamDeclList", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					return this.action0("$functionSignature", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $functionSignature 0");
		return this.Fail("$functionSignature", Parser);
	}
}

/*
[$functionBody:
	[
		<Symbol:$block>
	]
]
*/
class functionBodySyntax extends SyntaxTemplate {
	functionBodySyntax() {
		super("$functionBody");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new blockSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new functionBodySyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $functionBody");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $functionBody");
		if(Parser.Match("$block", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$functionBody", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $functionBody 0");
		return this.Fail("$functionBody", Parser);
	}
}

/*
[$functionDefinition:
	[
		<Symbol:$functionSignature>
		<Symbol:$functionBody>
	]
	[
		<Symbol:$functionSignature>
		<Symbol:";">
	]
]
*/
class functionDefinitionSyntax extends SyntaxTemplate {
	functionDefinitionSyntax() {
		super("$functionDefinition");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new functionSignatureSyntax(), false);
		Parser.AddSyntax(this, new functionBodySyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new functionDefinitionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $functionDefinition");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new functionDefinitionSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $functionDefinition");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $functionDefinition");
		if(Parser.Match("$functionSignature", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$functionBody", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				return this.action0("$functionDefinition", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $functionDefinition 0");
		if(Parser.Match("$functionSignature", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
				return this.action1("$functionDefinition", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $functionDefinition 0");
		return this.Fail("$functionDefinition", Parser);
	}
}

/*
[$ParamDeclList:
	[
		<Symbol:"(">
		<Symbol:")">
	]
	[
		<Symbol:"(">
		<Symbol:$ParamDecls>
		<Symbol:")">
	]
]
*/
class ParamDeclListSyntax extends SyntaxTemplate {
	ParamDeclListSyntax() {
		super("$ParamDeclList");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParamDeclsSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ParamDeclListSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ParamDeclList");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new ParamDeclListSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ParamDeclList");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $ParamDeclList");
		if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
				return this.action0("$ParamDeclList", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ParamDeclList 0");
		if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$ParamDecls", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
					return this.action1("$ParamDeclList", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ParamDeclList 0");
		return this.Fail("$ParamDeclList", Parser);
	}
}

/*
[$ParamDecls:
	[
		<Symbol:$ParamDecl>
		<Repeat:<Group:<Symbol:","> <Symbol:$ParamDecl> >>
	]
]
*/
class ParamDeclsSyntax extends SyntaxTemplate {
	ParamDeclsSyntax() {
		super("$ParamDecls");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParamDeclSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ParamDeclsSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ParamDecls");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $ParamDecls");
		if(Parser.Match("$ParamDecl", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.MatchToken("$Camma", TokenList, Parser.Cursor) >= 0) {
					if(Parser.Match("$ParamDecl", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						continue;
					}
				}
				return this.action0("$ParamDecls", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ParamDecls 0");
		return this.Fail("$ParamDecls", Parser);
	}
}

/*
[$ParamDecl:
	[
		<Symbol:$type>
		<Symbol:$identifier>
	]
]
*/
class ParamDeclSyntax extends SyntaxTemplate {
	ParamDeclSyntax() {
		super("$ParamDecl");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new typeSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ParamDeclSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ParamDecl");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $ParamDecl");
		if(Parser.Match("$type", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$identifier", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				return this.action0("$ParamDecl", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ParamDecl 0");
		return this.Fail("$ParamDecl", Parser);
	}
}

/*
[$ParameterList:
	[
		<Symbol:"(">
		<Symbol:")">
	]
	[
		<Symbol:"(">
		<Symbol:$Parameters>
		<Symbol:")">
	]
]
*/
class ParameterListSyntax extends SyntaxTemplate {
	ParameterListSyntax() {
		super("$ParameterList");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParametersSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ParameterListSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ParameterList");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new ParameterListSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ParameterList");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $ParameterList");
		if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
				return this.action0("$ParameterList", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ParameterList 0");
		if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$Parameters", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
					return this.action1("$ParameterList", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ParameterList 0");
		return this.Fail("$ParameterList", Parser);
	}
}

/*
[$Parameters:
	[
		<Symbol:$Parameter>
		<Repeat:<Group:<Symbol:","> <Symbol:$Parameter> >>
	]
]
*/
class ParametersSyntax extends SyntaxTemplate {
	ParametersSyntax() {
		super("$Parameters");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParameterSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ParametersSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Parameters");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $Parameters");
		if(Parser.Match("$Parameter", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.MatchToken("$Camma", TokenList, Parser.Cursor) >= 0) {
					if(Parser.Match("$Parameter", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						continue;
					}
				}
				return this.action0("$Parameters", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $Parameters 0");
		return this.Fail("$Parameters", Parser);
	}
}

/*
[$Parameter:
	[
		<Symbol:$expression>
	]
]
*/
class ParameterSyntax extends SyntaxTemplate {
	ParameterSyntax() {
		super("$Parameter");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ParameterSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Parameter");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $Parameter");
		if(Parser.Match("$expression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$Parameter", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $Parameter 0");
		return this.Fail("$Parameter", Parser);
	}
}

/*
[$literal:
	[
		<Symbol:"null">
	]
	[
		<Symbol:"true">
	]
	[
		<Symbol:"false">
	]
	[
		<Symbol:$intLiteral>
	]
	[
		<Symbol:$stringLiteral>
	]
]
*/
class literalSyntax extends SyntaxTemplate {
	literalSyntax() {
		super("$literal");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new stringLiteralSyntax(), false);
		Parser.AddSyntax(this, new intLiteralSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new literalSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $literal");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new literalSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $literal");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor2	= new literalSyntax2();

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $literal");
		Parser.PushThunk(this.Acceptor2, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor3	= new literalSyntax3();

	int action3(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $literal");
		Parser.PushThunk(this.Acceptor3, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor4	= new literalSyntax4();

	int action4(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $literal");
		Parser.PushThunk(this.Acceptor4, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $literal");
		if(Parser.MatchToken("null", TokenList, Parser.Cursor) >= 0) {
			return this.action0("$literal", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $literal 0");
		if(Parser.MatchToken("true", TokenList, Parser.Cursor) >= 0) {
			return this.action1("$literal", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $literal 0");
		if(Parser.MatchToken("false", TokenList, Parser.Cursor) >= 0) {
			return this.action2("$literal", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $literal 0");
		if(Parser.Match("$intLiteral", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action3("$literal", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $literal 0");
		if(Parser.Match("$stringLiteral", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action4("$literal", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $literal 0");
		return this.Fail("$literal", Parser);
	}
}

/*
[$type:
	[
		<Symbol:$Type>
	]
	[
		<Symbol:$Type>
		<Repeat:<Group:<Symbol:"["> <Symbol:"]"> >>
	]
]
*/
class typeSyntax extends SyntaxTemplate {
	typeSyntax() {
		super("$type");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new TypeTokenSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new typeSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $type");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new typeSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $type");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $type");
		if(Parser.Match("$Type", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$type", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $type 0");
		if(Parser.Match("$Type", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.MatchToken("$LParenthesis", TokenList, Parser.Cursor) >= 0) {
					if(Parser.MatchToken("$RParenthesis", TokenList, Parser.Cursor) >= 0) {
						continue;
					}
				}
				return this.action1("$type", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $type 0");
		return this.Fail("$type", Parser);
	}
}

/*
[$statement:
	[
		<Symbol:$block>
	]
	[
		<Symbol:$variableDeclaration>
	]
	[
		<Symbol:$expressionStatement>
	]
	[
		<Symbol:$ifStatement>
	]
	[
		<Symbol:$whileStatement>
	]
	[
		<Symbol:$breakStatement>
	]
	[
		<Symbol:$continueStatement>
	]
	[
		<Symbol:$returnStatement>
	]
]
*/
class statementSyntax extends SyntaxTemplate {
	statementSyntax() {
		super("$statement");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new returnStatementSyntax(), false);
		Parser.AddSyntax(this, new whileStatementSyntax(), false);
		Parser.AddSyntax(this, new continueStatementSyntax(), false);
		Parser.AddSyntax(this, new breakStatementSyntax(), false);
		Parser.AddSyntax(this, new expressionStatementSyntax(), false);
		Parser.AddSyntax(this, new ifStatementSyntax(), false);
		Parser.AddSyntax(this, new variableDeclarationSyntax(), false);
		Parser.AddSyntax(this, new blockSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new statementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new statementSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor2	= new statementSyntax2();

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(this.Acceptor2, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor3	= new statementSyntax3();

	int action3(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(this.Acceptor3, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor4	= new statementSyntax4();

	int action4(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(this.Acceptor4, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor5	= new statementSyntax5();

	int action5(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(this.Acceptor5, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor6	= new statementSyntax6();

	int action6(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(this.Acceptor6, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor7	= new statementSyntax7();

	int action7(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(this.Acceptor7, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $statement");
		if(Parser.Match("$block", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$variableDeclaration", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action1("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$expressionStatement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action2("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$ifStatement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action3("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$whileStatement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action4("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$breakStatement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action5("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$continueStatement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action6("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$returnStatement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action7("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		return this.Fail("$statement", Parser);
	}
}

/*
[$variable:
	[
		<Symbol:$identifier>
	]
]
*/
class variableSyntax extends SyntaxTemplate {
	variableSyntax() {
		super("$variable");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new variableSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $variable");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $variable");
		if(Parser.Match("$identifier", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$variable", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $variable 0");
		return this.Fail("$variable", Parser);
	}
}

/*
[$EQ:
	[
		<Symbol:"=">
	]
]
*/
class EQSyntax extends SyntaxTemplate {
	EQSyntax() {
		super("$EQ");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new EQSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $EQ");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $EQ");
		if(Parser.MatchToken("$Equal", TokenList, Parser.Cursor) >= 0) {
			return this.action0("$EQ", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $EQ 0");
		return this.Fail("$EQ", Parser);
	}
}

/*
[$variableDeclaration:
	[
		<Symbol:$type>
		<Symbol:$identifier>
		<Symbol:";">
	]
	[
		<Symbol:$type>
		<Symbol:$variable>
		<Symbol:$EQ>
		<Symbol:$expression>
		<Symbol:";">
	]
]
*/
class variableDeclarationSyntax extends SyntaxTemplate {
	variableDeclarationSyntax() {
		super("$variableDeclaration");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new typeSyntax(), false);
		Parser.AddSyntax(this, new variableSyntax(), false);
		Parser.AddSyntax(this, new expressionSyntax(), false);
		Parser.AddSyntax(this, new EQSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new variableDeclarationSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $variableDeclaration");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new variableDeclarationSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $variableDeclaration");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $variableDeclaration");
		if(Parser.Match("$type", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$identifier", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
					return this.action0("$variableDeclaration", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $variableDeclaration 0");
		if(Parser.Match("$type", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$variable", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.Match("$EQ", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					if(Parser.Match("$expression", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
							return this.action1("$variableDeclaration", Parser, pos0, NodeSize);
						}
					}
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $variableDeclaration 0");
		return this.Fail("$variableDeclaration", Parser);
	}
}

/*
[$statements:
	[
		<Repeat:<Symbol:$statement>>
	]
]
*/
class statementsSyntax extends SyntaxTemplate {
	statementsSyntax() {
		super("$statements");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new statementSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new statementsSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statements");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $statements");
		while(true) {
			if(Parser.Match("$statement", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				continue;
			}
			return this.action0("$statements", Parser, pos0, NodeSize);
		}
	}
}

/*
[$block:
	[
		<Symbol:"{">
		<Symbol:$statements>
		<Symbol:"}">
	]
]
*/
class blockSyntax extends SyntaxTemplate {
	blockSyntax() {
		super("$block");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new statementsSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new blockSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $block");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $block");
		if(Parser.MatchToken("$LCBrace", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$statements", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$RCBrace", TokenList, Parser.Cursor) >= 0) {
					return this.action0("$block", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $block 0");
		return this.Fail("$block", Parser);
	}
}

/*
[$ifStatement:
	[
		<Symbol:"if">
		<Symbol:"(">
		<Symbol:$expression>
		<Symbol:")">
		<Symbol:$block>
		<Symbol:"else">
		<Symbol:$block>
	]
	[
		<Symbol:"if">
		<Symbol:"(">
		<Symbol:$expression>
		<Symbol:")">
		<Symbol:$block>
	]
]
*/
class ifStatementSyntax extends SyntaxTemplate {
	ifStatementSyntax() {
		super("$ifStatement");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
		Parser.AddSyntax(this, new blockSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ifStatementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ifStatement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new ifStatementSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ifStatement");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $ifStatement");
		if(Parser.MatchToken("if", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
				if(Parser.Match("$expression", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
						if(Parser.Match("$block", TokenList) >= 0) {
							NodeSize = NodeSize + 1;
							if(Parser.MatchToken("else", TokenList, Parser.Cursor) >= 0) {
								if(Parser.Match("$block", TokenList) >= 0) {
									NodeSize = NodeSize + 1;
									return this.action0("$ifStatement", Parser, pos0, NodeSize);
								}
							}
						}
					}
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ifStatement 0");
		if(Parser.MatchToken("if", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
				if(Parser.Match("$expression", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
						if(Parser.Match("$block", TokenList) >= 0) {
							NodeSize = NodeSize + 1;
							return this.action1("$ifStatement", Parser, pos0, NodeSize);
						}
					}
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ifStatement 0");
		return this.Fail("$ifStatement", Parser);
	}
}

/*
[$whileStatement:
	[
		<Symbol:"while">
		<Symbol:"(">
		<Symbol:$expression>
		<Symbol:")">
		<Symbol:$block>
	]
]
*/
class whileStatementSyntax extends SyntaxTemplate {
	whileStatementSyntax() {
		super("$whileStatement");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
		Parser.AddSyntax(this, new blockSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new whileStatementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $whileStatement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $whileStatement");
		if(Parser.MatchToken("while", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
				if(Parser.Match("$expression", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
						if(Parser.Match("$block", TokenList) >= 0) {
							NodeSize = NodeSize + 1;
							return this.action0("$whileStatement", Parser, pos0, NodeSize);
						}
					}
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $whileStatement 0");
		return this.Fail("$whileStatement", Parser);
	}
}

/*
[$breakStatement:
	[
		<Symbol:"break">
		<Symbol:";">
	]
]
*/
class breakStatementSyntax extends SyntaxTemplate {
	breakStatementSyntax() {
		super("$breakStatement");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new breakStatementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $breakStatement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $breakStatement");
		if(Parser.MatchToken("break", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
				return this.action0("$breakStatement", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $breakStatement 0");
		return this.Fail("$breakStatement", Parser);
	}
}

/*
[$continueStatement:
	[
		<Symbol:"continue">
		<Symbol:";">
	]
]
*/
class continueStatementSyntax extends SyntaxTemplate {
	continueStatementSyntax() {
		super("$continueStatement");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new continueStatementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $continueStatement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $continueStatement");
		if(Parser.MatchToken("continue", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
				return this.action0("$continueStatement", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $continueStatement 0");
		return this.Fail("$continueStatement", Parser);
	}
}

/*
[$returnStatement:
	[
		<Symbol:"return">
		<Symbol:";">
	]
	[
		<Symbol:"return">
		<Symbol:$expression>
		<Symbol:";">
	]
]
*/
class returnStatementSyntax extends SyntaxTemplate {
	returnStatementSyntax() {
		super("$returnStatement");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new returnStatementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $returnStatement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new returnStatementSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $returnStatement");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $returnStatement");
		if(Parser.MatchToken("return", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
				return this.action0("$returnStatement", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $returnStatement 0");
		if(Parser.MatchToken("return", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$expression", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
					return this.action1("$returnStatement", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $returnStatement 0");
		return this.Fail("$returnStatement", Parser);
	}
}

/*
[$expressionStatement:
	[
		<Symbol:$expression>
		<Symbol:";">
	]
]
*/
class expressionStatementSyntax extends SyntaxTemplate {
	expressionStatementSyntax() {
		super("$expressionStatement");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new expressionStatementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $expressionStatement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $expressionStatement");
		if(Parser.Match("$expression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
				return this.action0("$expressionStatement", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $expressionStatement 0");
		return this.Fail("$expressionStatement", Parser);
	}
}

/*
[$expression:
	[
		<Symbol:$leftHandSideExpression>
		<Symbol:$EQ>
		<Symbol:$expression>
	]
	[
		<Symbol:$logicalOrExpression>
	]
]
*/
class expressionSyntax extends SyntaxTemplate {
	expressionSyntax() {
		super("$expression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new logicalOrExpressionSyntax(), false);
		Parser.AddSyntax(this, new expressionSyntax(), false);
		Parser.AddSyntax(this, new leftHandSideExpressionSyntax(), false);
		Parser.AddSyntax(this, new EQSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new expressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $expression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new expressionSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $expression");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $expression");
		if(Parser.Match("$leftHandSideExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$EQ", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.Match("$expression", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					return this.action0("$expression", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $expression 0");
		if(Parser.Match("$logicalOrExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action1("$expression", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $expression 0");
		return this.Fail("$expression", Parser);
	}
}

/*
[$leftHandSideExpression:
	[
		<Symbol:$callExpression>
	]
	[
		<Symbol:$newExpression>
	]
]
*/
class leftHandSideExpressionSyntax extends SyntaxTemplate {
	leftHandSideExpressionSyntax() {
		super("$leftHandSideExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new newExpressionSyntax(), false);
		Parser.AddSyntax(this, new callExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new leftHandSideExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $leftHandSideExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new leftHandSideExpressionSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $leftHandSideExpression");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $leftHandSideExpression");
		if(Parser.Match("$callExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$leftHandSideExpression", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $leftHandSideExpression 0");
		if(Parser.Match("$newExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action1("$leftHandSideExpression", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $leftHandSideExpression 0");
		return this.Fail("$leftHandSideExpression", Parser);
	}
}

/*
[$callExpression:
	[
		<Symbol:$memberExpression>
		<Symbol:$ParameterList>
	]
]
*/
class callExpressionSyntax extends SyntaxTemplate {
	callExpressionSyntax() {
		super("$callExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParameterListSyntax(), false);
		Parser.AddSyntax(this, new memberExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new callExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $callExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $callExpression");
		if(Parser.Match("$memberExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$ParameterList", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				return this.action0("$callExpression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $callExpression 0");
		return this.Fail("$callExpression", Parser);
	}
}

/*
[$memberExpression:
	[
		<Symbol:$primary>
		<Repeat:<Symbol:$selector>>
	]
]
*/
class memberExpressionSyntax extends SyntaxTemplate {
	memberExpressionSyntax() {
		super("$memberExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new primarySyntax(), false);
		Parser.AddSyntax(this, new selectorSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new memberExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $memberExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $memberExpression");
		if(Parser.Match("$primary", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.Match("$selector", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					continue;
				}
				return this.action0("$memberExpression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $memberExpression 0");
		return this.Fail("$memberExpression", Parser);
	}
}

/*
[$primary:
	[
		<Symbol:"this">
	]
	[
		<Symbol:$identifier>
	]
	[
		<Symbol:$literal>
	]
	[
		<Symbol:"(">
		<Symbol:$expression>
		<Symbol:")">
	]
]
*/
class primarySyntax extends SyntaxTemplate {
	primarySyntax() {
		super("$primary");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new literalSyntax(), false);
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new primarySyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $primary");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new primarySyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $primary");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor2	= new primarySyntax2();

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $primary");
		Parser.PushThunk(this.Acceptor2, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor3	= new primarySyntax3();

	int action3(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $primary");
		Parser.PushThunk(this.Acceptor3, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $primary");
		if(Parser.MatchToken("this", TokenList, Parser.Cursor) >= 0) {
			return this.action0("$primary", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $primary 0");
		if(Parser.Match("$identifier", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action1("$primary", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $primary 0");
		if(Parser.Match("$literal", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action2("$primary", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $primary 0");
		if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$expression", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
					return this.action3("$primary", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $primary 0");
		return this.Fail("$primary", Parser);
	}
}

/*
[$selector:
	[
		<Symbol:"[">
		<Symbol:$expression>
		<Symbol:"]">
	]
	[
		<Symbol:".">
		<Symbol:$identifier>
	]
]
*/
class selectorSyntax extends SyntaxTemplate {
	selectorSyntax() {
		super("$selector");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new selectorSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $selector");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new selectorSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $selector");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $selector");
		if(Parser.MatchToken("$LParenthesis", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$expression", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$RParenthesis", TokenList, Parser.Cursor) >= 0) {
					return this.action0("$selector", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $selector 0");
		if(Parser.MatchToken("$Dot", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$identifier", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				return this.action1("$selector", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $selector 0");
		return this.Fail("$selector", Parser);
	}
}

/*
[$newExpression:
	[
		<Symbol:$memberExpression>
	]
	[
		<Symbol:"new">
		<Symbol:$type>
		<Symbol:$ParameterList>
	]
]
*/
class newExpressionSyntax extends SyntaxTemplate {
	newExpressionSyntax() {
		super("$newExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new typeSyntax(), false);
		Parser.AddSyntax(this, new ParameterListSyntax(), false);
		Parser.AddSyntax(this, new memberExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new newExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $newExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new newExpressionSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $newExpression");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $newExpression");
		if(Parser.Match("$memberExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$newExpression", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $newExpression 0");
		if(Parser.MatchToken("new", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$type", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.Match("$ParameterList", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					return this.action1("$newExpression", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $newExpression 0");
		return this.Fail("$newExpression", Parser);
	}
}

/*
[$logicalOrExpression:
	[
		<Symbol:$logicalAndExpression>
		<Repeat:<Group:<Symbol:"||"> <Symbol:$logicalAndExpression> >>
	]
]
*/
class logicalOrExpressionSyntax extends SyntaxTemplate {
	logicalOrExpressionSyntax() {
		super("$logicalOrExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new logicalAndExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new logicalOrExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $logicalOrExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $logicalOrExpression");
		if(Parser.Match("$logicalAndExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.MatchToken("||", TokenList, Parser.Cursor) >= 0) {
					if(Parser.Match("$logicalAndExpression", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						continue;
					}
				}
				return this.action0("$logicalOrExpression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $logicalOrExpression 0");
		return this.Fail("$logicalOrExpression", Parser);
	}
}

/*
[$logicalAndExpression:
	[
		<Symbol:$relationExpression>
		<Repeat:<Group:<Symbol:"&&"> <Symbol:$relationExpression> >>
	]
]
*/
class logicalAndExpressionSyntax extends SyntaxTemplate {
	logicalAndExpressionSyntax() {
		super("$logicalAndExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new relationExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new logicalAndExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $logicalAndExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $logicalAndExpression");
		if(Parser.Match("$relationExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.MatchToken("&&", TokenList, Parser.Cursor) >= 0) {
					if(Parser.Match("$relationExpression", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						continue;
					}
				}
				return this.action0("$logicalAndExpression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $logicalAndExpression 0");
		return this.Fail("$logicalAndExpression", Parser);
	}
}

/*
[$relationExpression:
	[
		<Symbol:$additiveExpression>
		<Symbol:$relationOperator>
		<Symbol:$additiveExpression>
	]
	[
		<Symbol:$additiveExpression>
	]
]
*/
class relationExpressionSyntax extends SyntaxTemplate {
	relationExpressionSyntax() {
		super("$relationExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new additiveExpressionSyntax(), false);
		Parser.AddSyntax(this, new relationOperatorSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new relationExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new relationExpressionSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationExpression");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $relationExpression");
		if(Parser.Match("$additiveExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$relationOperator", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.Match("$additiveExpression", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					return this.action0("$relationExpression", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationExpression 0");
		if(Parser.Match("$additiveExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action1("$relationExpression", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationExpression 0");
		return this.Fail("$relationExpression", Parser);
	}
}

/*
[$relationOperator:
	[
		<Symbol:"==">
	]
	[
		<Symbol:"!=">
	]
	[
		<Symbol:">=">
	]
	[
		<Symbol:">">
	]
	[
		<Symbol:"<=">
	]
	[
		<Symbol:"<">
	]
]
*/
class relationOperatorSyntax extends SyntaxTemplate {
	relationOperatorSyntax() {
		super("$relationOperator");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new relationOperatorSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationOperator");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new relationOperatorSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationOperator");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor2	= new relationOperatorSyntax2();

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationOperator");
		Parser.PushThunk(this.Acceptor2, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor3	= new relationOperatorSyntax3();

	int action3(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationOperator");
		Parser.PushThunk(this.Acceptor3, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor4	= new relationOperatorSyntax4();

	int action4(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationOperator");
		Parser.PushThunk(this.Acceptor4, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor5	= new relationOperatorSyntax5();

	int action5(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationOperator");
		Parser.PushThunk(this.Acceptor5, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $relationOperator");
		if(Parser.MatchToken("==", TokenList, Parser.Cursor) >= 0) {
			return this.action0("$relationOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationOperator 0");
		if(Parser.MatchToken("!=", TokenList, Parser.Cursor) >= 0) {
			return this.action1("$relationOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationOperator 0");
		if(Parser.MatchToken(">=", TokenList, Parser.Cursor) >= 0) {
			return this.action2("$relationOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationOperator 0");
		if(Parser.MatchToken("$GraterThan", TokenList, Parser.Cursor) >= 0) {
			return this.action3("$relationOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationOperator 0");
		if(Parser.MatchToken("<=", TokenList, Parser.Cursor) >= 0) {
			return this.action4("$relationOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationOperator 0");
		if(Parser.MatchToken("$LessThan", TokenList, Parser.Cursor) >= 0) {
			return this.action5("$relationOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationOperator 0");
		return this.Fail("$relationOperator", Parser);
	}
}

/*
[$shiftOperator:
	[
		<Symbol:"<<">
	]
	[
		<Symbol:">>">
	]
]
*/
class shiftOperatorSyntax extends SyntaxTemplate {
	shiftOperatorSyntax() {
		super("$shiftOperator");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new shiftOperatorSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $shiftOperator");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new shiftOperatorSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $shiftOperator");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $shiftOperator");
		if(Parser.MatchToken("<<", TokenList, Parser.Cursor) >= 0) {
			return this.action0("$shiftOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $shiftOperator 0");
		if(Parser.MatchToken(">>", TokenList, Parser.Cursor) >= 0) {
			return this.action1("$shiftOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $shiftOperator 0");
		return this.Fail("$shiftOperator", Parser);
	}
}

/*
[$additiveOperator:
	[
		<Symbol:"+">
	]
	[
		<Symbol:"-">
	]
]
*/
class additiveOperatorSyntax extends SyntaxTemplate {
	additiveOperatorSyntax() {
		super("$additiveOperator");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new additiveOperatorSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $additiveOperator");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new additiveOperatorSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $additiveOperator");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $additiveOperator");
		if(Parser.MatchToken("$Plus", TokenList, Parser.Cursor) >= 0) {
			return this.action0("$additiveOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $additiveOperator 0");
		if(Parser.MatchToken("$Minus", TokenList, Parser.Cursor) >= 0) {
			return this.action1("$additiveOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $additiveOperator 0");
		return this.Fail("$additiveOperator", Parser);
	}
}

/*
[$multiplicativeOperator:
	[
		<Symbol:"*">
	]
	[
		<Symbol:"/">
	]
	[
		<Symbol:"%">
	]
]
*/
class multiplicativeOperatorSyntax extends SyntaxTemplate {
	multiplicativeOperatorSyntax() {
		super("$multiplicativeOperator");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new multiplicativeOperatorSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $multiplicativeOperator");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new multiplicativeOperatorSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $multiplicativeOperator");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor2	= new multiplicativeOperatorSyntax2();

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $multiplicativeOperator");
		Parser.PushThunk(this.Acceptor2, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $multiplicativeOperator");
		if(Parser.MatchToken("$Star", TokenList, Parser.Cursor) >= 0) {
			return this.action0("$multiplicativeOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $multiplicativeOperator 0");
		if(Parser.MatchToken("$Slash", TokenList, Parser.Cursor) >= 0) {
			return this.action1("$multiplicativeOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $multiplicativeOperator 0");
		if(Parser.MatchToken("$Percent", TokenList, Parser.Cursor) >= 0) {
			return this.action2("$multiplicativeOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $multiplicativeOperator 0");
		return this.Fail("$multiplicativeOperator", Parser);
	}
}

/*
[$additiveExpression:
	[
		<Symbol:$multiplicativeExpression>
		<Repeat:<Group:<Symbol:$additiveOperator> <Symbol:$multiplicativeExpression> >>
	]
]
*/
class additiveExpressionSyntax extends SyntaxTemplate {
	additiveExpressionSyntax() {
		super("$additiveExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new additiveOperatorSyntax(), false);
		Parser.AddSyntax(this, new multiplicativeExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new additiveExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $additiveExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $additiveExpression");
		if(Parser.Match("$multiplicativeExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.Match("$additiveOperator", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					if(Parser.Match("$multiplicativeExpression", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						continue;
					}
				}
				return this.action0("$additiveExpression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $additiveExpression 0");
		return this.Fail("$additiveExpression", Parser);
	}
}

/*
[$multiplicativeExpression:
	[
		<Symbol:$unaryExpression>
		<Repeat:<Group:<Symbol:$multiplicativeOperator> <Symbol:$unaryExpression> >>
	]
]
*/
class multiplicativeExpressionSyntax extends SyntaxTemplate {
	multiplicativeExpressionSyntax() {
		super("$multiplicativeExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new multiplicativeOperatorSyntax(), false);
		Parser.AddSyntax(this, new unaryExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new multiplicativeExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $multiplicativeExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $multiplicativeExpression");
		if(Parser.Match("$unaryExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.Match("$multiplicativeOperator", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					if(Parser.Match("$unaryExpression", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						continue;
					}
				}
				return this.action0("$multiplicativeExpression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $multiplicativeExpression 0");
		return this.Fail("$multiplicativeExpression", Parser);
	}
}

/*
[$unaryExpression:
	[
		<Symbol:$leftHandSideExpression>
	]
]
*/
class unaryExpressionSyntax extends SyntaxTemplate {
	unaryExpressionSyntax() {
		super("$unaryExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new leftHandSideExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new unaryExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $unaryExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $unaryExpression");
		if(Parser.Match("$leftHandSideExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$unaryExpression", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $unaryExpression 0");
		return this.Fail("$unaryExpression", Parser);
	}
}

/*
[$identifier:
	[
		<Symbol:$Symbol>
	]
]
*/
class identifierSyntax extends SyntaxTemplate {
	identifierSyntax() {
		super("$identifier");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new SymbolSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new identifierSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $identifier");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $identifier");
		if(Parser.Match("$Symbol", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$identifier", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $identifier 0");
		return this.Fail("$identifier", Parser);
	}
}
