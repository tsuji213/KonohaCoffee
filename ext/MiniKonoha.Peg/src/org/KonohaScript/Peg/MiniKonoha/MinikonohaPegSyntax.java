package org.KonohaScript.Peg.MiniKonoha;

import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.PegParser.SyntaxAcceptor;
import org.KonohaScript.PegParser.SyntaxModule;
import org.KonohaScript.PegParser.SyntaxTemplate;

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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new TopLevelDefinitionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new SourceCodeSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $SourceCode");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $SourceCode");
		while(true) {
			if(Parser.Match("$TopLevelDefinition", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				continue;
			}
			return action0("$SourceCode", Parser, pos0, NodeSize);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new functionDefinitionSyntax(), false);
		Parser.AddSyntax(this, new statementSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new TopLevelDefinitionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $TopLevelDefinition");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new TopLevelDefinitionSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $TopLevelDefinition");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $TopLevelDefinition");
		if(Parser.Match("$statement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action0("$TopLevelDefinition", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $TopLevelDefinition 0");
		if(Parser.Match("$functionDefinition", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action1("$TopLevelDefinition", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $TopLevelDefinition 0");
		return Fail("$TopLevelDefinition", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new typeSyntax(), false);
		Parser.AddSyntax(this, new ParamDeclListSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new functionSignatureSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $functionSignature");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $functionSignature");
		if(Parser.Match("$type", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$identifier", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.Match("$ParamDeclList", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					return action0("$functionSignature", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $functionSignature 0");
		return Fail("$functionSignature", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new blockSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new functionBodySyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $functionBody");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $functionBody");
		if(Parser.Match("$block", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action0("$functionBody", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $functionBody 0");
		return Fail("$functionBody", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new functionSignatureSyntax(), false);
		Parser.AddSyntax(this, new functionBodySyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new functionDefinitionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $functionDefinition");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new functionDefinitionSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $functionDefinition");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $functionDefinition");
		if(Parser.Match("$functionSignature", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$functionBody", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				return action0("$functionDefinition", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $functionDefinition 0");
		if(Parser.Match("$functionSignature", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
				return action1("$functionDefinition", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $functionDefinition 0");
		return Fail("$functionDefinition", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParamDeclsSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ParamDeclListSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $ParamDeclList");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new ParamDeclListSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $ParamDeclList");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $ParamDeclList");
		if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
				return action0("$ParamDeclList", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ParamDeclList 0");
		if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$ParamDecls", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
					return action1("$ParamDeclList", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ParamDeclList 0");
		return Fail("$ParamDeclList", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParamDeclSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ParamDeclsSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $ParamDecls");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $ParamDecls");
		if(Parser.Match("$ParamDecl", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.MatchToken("$Camma", TokenList, Parser.Cursor) >= 0) {
					if(Parser.Match("$ParamDecl", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						continue;
					}
				}
				return action0("$ParamDecls", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ParamDecls 0");
		return Fail("$ParamDecls", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new typeSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ParamDeclSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $ParamDecl");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $ParamDecl");
		if(Parser.Match("$type", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$identifier", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				return action0("$ParamDecl", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ParamDecl 0");
		return Fail("$ParamDecl", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParametersSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ParameterListSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $ParameterList");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new ParameterListSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $ParameterList");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $ParameterList");
		if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
				return action0("$ParameterList", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ParameterList 0");
		if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$Parameters", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
					return action1("$ParameterList", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ParameterList 0");
		return Fail("$ParameterList", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParameterSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ParametersSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $Parameters");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $Parameters");
		if(Parser.Match("$Parameter", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.MatchToken("$Camma", TokenList, Parser.Cursor) >= 0) {
					if(Parser.Match("$Parameter", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						continue;
					}
				}
				return action0("$Parameters", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $Parameters 0");
		return Fail("$Parameters", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ParameterSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $Parameter");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $Parameter");
		if(Parser.Match("$expression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action0("$Parameter", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $Parameter 0");
		return Fail("$Parameter", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new stringLiteralSyntax(), false);
		Parser.AddSyntax(this, new intLiteralSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new literalSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $literal");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new literalSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $literal");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor2	= new literalSyntax2();

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $literal");
		Parser.PushThunk(this.Acceptor2, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor3	= new literalSyntax3();

	int action3(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $literal");
		Parser.PushThunk(this.Acceptor3, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor4	= new literalSyntax4();

	int action4(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $literal");
		Parser.PushThunk(this.Acceptor4, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $literal");
		if(Parser.MatchToken("null", TokenList, Parser.Cursor) >= 0) {
			return action0("$literal", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $literal 0");
		if(Parser.MatchToken("true", TokenList, Parser.Cursor) >= 0) {
			return action1("$literal", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $literal 0");
		if(Parser.MatchToken("false", TokenList, Parser.Cursor) >= 0) {
			return action2("$literal", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $literal 0");
		if(Parser.Match("$intLiteral", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action3("$literal", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $literal 0");
		if(Parser.Match("$stringLiteral", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action4("$literal", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $literal 0");
		return Fail("$literal", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new TypeTokenSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new typeSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $type");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new typeSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $type");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $type");
		if(Parser.Match("$Type", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action0("$type", Parser, pos0, NodeSize);
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
				return action1("$type", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $type 0");
		return Fail("$type", Parser);
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
	public void Init(SyntaxModule Parser) {
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
		Report("Accept $statement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new statementSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $statement");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor2	= new statementSyntax2();

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $statement");
		Parser.PushThunk(this.Acceptor2, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor3	= new statementSyntax3();

	int action3(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $statement");
		Parser.PushThunk(this.Acceptor3, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor4	= new statementSyntax4();

	int action4(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $statement");
		Parser.PushThunk(this.Acceptor4, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor5	= new statementSyntax5();

	int action5(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $statement");
		Parser.PushThunk(this.Acceptor5, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor6	= new statementSyntax6();

	int action6(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $statement");
		Parser.PushThunk(this.Acceptor6, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor7	= new statementSyntax7();

	int action7(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $statement");
		Parser.PushThunk(this.Acceptor7, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $statement");
		if(Parser.Match("$block", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action0("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$variableDeclaration", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action1("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$expressionStatement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action2("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$ifStatement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action3("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$whileStatement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action4("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$breakStatement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action5("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$continueStatement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action6("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		if(Parser.Match("$returnStatement", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action7("$statement", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $statement 0");
		return Fail("$statement", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new variableSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $variable");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $variable");
		if(Parser.Match("$identifier", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action0("$variable", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $variable 0");
		return Fail("$variable", Parser);
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
	public void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new EQSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $EQ");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $EQ");
		if(Parser.MatchToken("$Equal", TokenList, Parser.Cursor) >= 0) {
			return action0("$EQ", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $EQ 0");
		return Fail("$EQ", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new typeSyntax(), false);
		Parser.AddSyntax(this, new variableSyntax(), false);
		Parser.AddSyntax(this, new expressionSyntax(), false);
		Parser.AddSyntax(this, new EQSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new variableDeclarationSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $variableDeclaration");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new variableDeclarationSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $variableDeclaration");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $variableDeclaration");
		if(Parser.Match("$type", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$identifier", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
					return action0("$variableDeclaration", Parser, pos0, NodeSize);
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
							return action1("$variableDeclaration", Parser, pos0, NodeSize);
						}
					}
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $variableDeclaration 0");
		return Fail("$variableDeclaration", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new statementSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new statementsSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $statements");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $statements");
		while(true) {
			if(Parser.Match("$statement", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				continue;
			}
			return action0("$statements", Parser, pos0, NodeSize);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new statementsSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new blockSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $block");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $block");
		if(Parser.MatchToken("$LCBrace", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$statements", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$RCBrace", TokenList, Parser.Cursor) >= 0) {
					return action0("$block", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $block 0");
		return Fail("$block", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
		Parser.AddSyntax(this, new blockSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new ifStatementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $ifStatement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new ifStatementSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $ifStatement");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $ifStatement");
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
									return action0("$ifStatement", Parser, pos0, NodeSize);
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
							return action1("$ifStatement", Parser, pos0, NodeSize);
						}
					}
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $ifStatement 0");
		return Fail("$ifStatement", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
		Parser.AddSyntax(this, new blockSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new whileStatementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $whileStatement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $whileStatement");
		if(Parser.MatchToken("while", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
				if(Parser.Match("$expression", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
						if(Parser.Match("$block", TokenList) >= 0) {
							NodeSize = NodeSize + 1;
							return action0("$whileStatement", Parser, pos0, NodeSize);
						}
					}
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $whileStatement 0");
		return Fail("$whileStatement", Parser);
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
	public void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new breakStatementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $breakStatement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $breakStatement");
		if(Parser.MatchToken("break", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
				return action0("$breakStatement", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $breakStatement 0");
		return Fail("$breakStatement", Parser);
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
	public void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new continueStatementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $continueStatement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $continueStatement");
		if(Parser.MatchToken("continue", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
				return action0("$continueStatement", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $continueStatement 0");
		return Fail("$continueStatement", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new returnStatementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $returnStatement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new returnStatementSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $returnStatement");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $returnStatement");
		if(Parser.MatchToken("return", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
				return action0("$returnStatement", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $returnStatement 0");
		if(Parser.MatchToken("return", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$expression", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
					return action1("$returnStatement", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $returnStatement 0");
		return Fail("$returnStatement", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new expressionStatementSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $expressionStatement");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $expressionStatement");
		if(Parser.Match("$expression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.MatchToken("$SemiColon", TokenList, Parser.Cursor) >= 0) {
				return action0("$expressionStatement", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $expressionStatement 0");
		return Fail("$expressionStatement", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new logicalOrExpressionSyntax(), false);
		Parser.AddSyntax(this, new expressionSyntax(), false);
		Parser.AddSyntax(this, new leftHandSideExpressionSyntax(), false);
		Parser.AddSyntax(this, new EQSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new expressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $expression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new expressionSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $expression");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $expression");
		if(Parser.Match("$leftHandSideExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$EQ", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.Match("$expression", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					return action0("$expression", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $expression 0");
		if(Parser.Match("$logicalOrExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action1("$expression", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $expression 0");
		return Fail("$expression", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new newExpressionSyntax(), false);
		Parser.AddSyntax(this, new callExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new leftHandSideExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $leftHandSideExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new leftHandSideExpressionSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $leftHandSideExpression");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $leftHandSideExpression");
		if(Parser.Match("$callExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action0("$leftHandSideExpression", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $leftHandSideExpression 0");
		if(Parser.Match("$newExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action1("$leftHandSideExpression", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $leftHandSideExpression 0");
		return Fail("$leftHandSideExpression", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParameterListSyntax(), false);
		Parser.AddSyntax(this, new memberExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new callExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $callExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $callExpression");
		if(Parser.Match("$memberExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$ParameterList", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				return action0("$callExpression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $callExpression 0");
		return Fail("$callExpression", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new primarySyntax(), false);
		Parser.AddSyntax(this, new selectorSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new memberExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $memberExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $memberExpression");
		if(Parser.Match("$primary", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.Match("$selector", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					continue;
				}
				return action0("$memberExpression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $memberExpression 0");
		return Fail("$memberExpression", Parser);
	}
}

/*
[$primary:
	[
		<Symbol:"this">
	]
	[
		<Symbol:$literal>
	]
	[
		<Symbol:$identifier>
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new literalSyntax(), false);
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new primarySyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $primary");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new primarySyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $primary");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor2	= new primarySyntax2();

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $primary");
		Parser.PushThunk(this.Acceptor2, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor3	= new primarySyntax3();

	int action3(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $primary");
		Parser.PushThunk(this.Acceptor3, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $primary");
		if(Parser.MatchToken("this", TokenList, Parser.Cursor) >= 0) {
			return action0("$primary", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $primary 0");
		if(Parser.Match("$literal", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action1("$primary", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $primary 0");
		if(Parser.Match("$identifier", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action2("$primary", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $primary 0");
		if(Parser.MatchToken("$LBrace", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$expression", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$RBrace", TokenList, Parser.Cursor) >= 0) {
					return action3("$primary", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $primary 0");
		return Fail("$primary", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new selectorSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $selector");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new selectorSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $selector");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $selector");
		if(Parser.MatchToken("$LParenthesis", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$expression", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.MatchToken("$RParenthesis", TokenList, Parser.Cursor) >= 0) {
					return action0("$selector", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $selector 0");
		if(Parser.MatchToken("$Dot", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$identifier", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				return action1("$selector", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $selector 0");
		return Fail("$selector", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new typeSyntax(), false);
		Parser.AddSyntax(this, new ParameterListSyntax(), false);
		Parser.AddSyntax(this, new memberExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new newExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $newExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new newExpressionSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $newExpression");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $newExpression");
		if(Parser.Match("$memberExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action0("$newExpression", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $newExpression 0");
		if(Parser.MatchToken("new", TokenList, Parser.Cursor) >= 0) {
			if(Parser.Match("$type", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.Match("$ParameterList", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					return action1("$newExpression", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $newExpression 0");
		return Fail("$newExpression", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new logicalAndExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new logicalOrExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $logicalOrExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $logicalOrExpression");
		if(Parser.Match("$logicalAndExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.MatchToken("||", TokenList, Parser.Cursor) >= 0) {
					if(Parser.Match("$logicalAndExpression", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						continue;
					}
				}
				return action0("$logicalOrExpression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $logicalOrExpression 0");
		return Fail("$logicalOrExpression", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new relationExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new logicalAndExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $logicalAndExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $logicalAndExpression");
		if(Parser.Match("$relationExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.MatchToken("&&", TokenList, Parser.Cursor) >= 0) {
					if(Parser.Match("$relationExpression", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						continue;
					}
				}
				return action0("$logicalAndExpression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $logicalAndExpression 0");
		return Fail("$logicalAndExpression", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new additiveExpressionSyntax(), false);
		Parser.AddSyntax(this, new relationOperatorSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new relationExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $relationExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new relationExpressionSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $relationExpression");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $relationExpression");
		if(Parser.Match("$additiveExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$relationOperator", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.Match("$additiveExpression", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					return action0("$relationExpression", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationExpression 0");
		if(Parser.Match("$additiveExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action1("$relationExpression", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationExpression 0");
		return Fail("$relationExpression", Parser);
	}
}

/*
[$relationOperator:
	[
		<Symbol:"=">
		<Symbol:"=">
	]
	[
		<Symbol:"!">
		<Symbol:"=">
	]
	[
		<Symbol:">">
		<Symbol:"=">
	]
	[
		<Symbol:">">
	]
	[
		<Symbol:"<">
		<Symbol:"=">
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
	public void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new relationOperatorSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $relationOperator");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new relationOperatorSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $relationOperator");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor2	= new relationOperatorSyntax2();

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $relationOperator");
		Parser.PushThunk(this.Acceptor2, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor3	= new relationOperatorSyntax3();

	int action3(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $relationOperator");
		Parser.PushThunk(this.Acceptor3, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor4	= new relationOperatorSyntax4();

	int action4(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $relationOperator");
		Parser.PushThunk(this.Acceptor4, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor5	= new relationOperatorSyntax5();

	int action5(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $relationOperator");
		Parser.PushThunk(this.Acceptor5, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $relationOperator");
		if(Parser.MatchToken("$Equal", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$Equal", TokenList, Parser.Cursor) >= 0) {
				return action0("$relationOperator", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationOperator 0");
		if(Parser.MatchToken("$Exclamation", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$Equal", TokenList, Parser.Cursor) >= 0) {
				return action1("$relationOperator", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationOperator 0");
		if(Parser.MatchToken("$GraterThan", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$Equal", TokenList, Parser.Cursor) >= 0) {
				return action2("$relationOperator", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationOperator 0");
		if(Parser.MatchToken("$GraterThan", TokenList, Parser.Cursor) >= 0) {
			return action3("$relationOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationOperator 0");
		if(Parser.MatchToken("$LessThan", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$Equal", TokenList, Parser.Cursor) >= 0) {
				return action4("$relationOperator", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationOperator 0");
		if(Parser.MatchToken("$LessThan", TokenList, Parser.Cursor) >= 0) {
			return action5("$relationOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $relationOperator 0");
		return Fail("$relationOperator", Parser);
	}
}

/*
[$shiftOperator:
	[
		<Symbol:"<">
		<Symbol:"<">
	]
	[
		<Symbol:">">
		<Symbol:">">
	]
]
*/
class shiftOperatorSyntax extends SyntaxTemplate {
	shiftOperatorSyntax() {
		super("$shiftOperator");
	}

	@Override
	public void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new shiftOperatorSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $shiftOperator");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new shiftOperatorSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $shiftOperator");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $shiftOperator");
		if(Parser.MatchToken("$LessThan", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$LessThan", TokenList, Parser.Cursor) >= 0) {
				return action0("$shiftOperator", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $shiftOperator 0");
		if(Parser.MatchToken("$GraterThan", TokenList, Parser.Cursor) >= 0) {
			if(Parser.MatchToken("$GraterThan", TokenList, Parser.Cursor) >= 0) {
				return action1("$shiftOperator", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $shiftOperator 0");
		return Fail("$shiftOperator", Parser);
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
	public void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new additiveOperatorSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $additiveOperator");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new additiveOperatorSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $additiveOperator");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $additiveOperator");
		if(Parser.MatchToken("$Plus", TokenList, Parser.Cursor) >= 0) {
			return action0("$additiveOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $additiveOperator 0");
		if(Parser.MatchToken("$Minus", TokenList, Parser.Cursor) >= 0) {
			return action1("$additiveOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $additiveOperator 0");
		return Fail("$additiveOperator", Parser);
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
	public void Init(SyntaxModule Parser) {
	}

	public SyntaxAcceptor	Acceptor0	= new multiplicativeOperatorSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $multiplicativeOperator");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor1	= new multiplicativeOperatorSyntax1();

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $multiplicativeOperator");
		Parser.PushThunk(this.Acceptor1, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	public SyntaxAcceptor	Acceptor2	= new multiplicativeOperatorSyntax2();

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $multiplicativeOperator");
		Parser.PushThunk(this.Acceptor2, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $multiplicativeOperator");
		if(Parser.MatchToken("$Star", TokenList, Parser.Cursor) >= 0) {
			return action0("$multiplicativeOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $multiplicativeOperator 0");
		if(Parser.MatchToken("$Slash", TokenList, Parser.Cursor) >= 0) {
			return action1("$multiplicativeOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $multiplicativeOperator 0");
		if(Parser.MatchToken("$Percent", TokenList, Parser.Cursor) >= 0) {
			return action2("$multiplicativeOperator", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $multiplicativeOperator 0");
		return Fail("$multiplicativeOperator", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new additiveOperatorSyntax(), false);
		Parser.AddSyntax(this, new multiplicativeExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new additiveExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $additiveExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $additiveExpression");
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
				return action0("$additiveExpression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $additiveExpression 0");
		return Fail("$additiveExpression", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new multiplicativeOperatorSyntax(), false);
		Parser.AddSyntax(this, new unaryExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new multiplicativeExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $multiplicativeExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $multiplicativeExpression");
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
				return action0("$multiplicativeExpression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $multiplicativeExpression 0");
		return Fail("$multiplicativeExpression", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new leftHandSideExpressionSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new unaryExpressionSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $unaryExpression");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $unaryExpression");
		if(Parser.Match("$leftHandSideExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action0("$unaryExpression", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $unaryExpression 0");
		return Fail("$unaryExpression", Parser);
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
	public void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new SymbolSyntax(), false);
	}

	public SyntaxAcceptor	Acceptor0	= new identifierSyntax0();

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		Report("Accept $identifier");
		Parser.PushThunk(this.Acceptor0, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	public int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		Report("Enter $identifier");
		if(Parser.Match("$Symbol", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return action0("$identifier", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $identifier 0");
		return Fail("$identifier", Parser);
	}
}
