package org.KonohaScript.Parser;

import org.KonohaScript.KLib.TokenList;

/*
[$SourceCode:
	[
		<Repeat:<Symbol:$TopLevelDefinition>>
	]
]
*/
class SourceCodeSyntax extends Syntax {
	SourceCodeSyntax() {
		super("$SourceCode");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new TopLevelDefinitionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $SourceCode");
		Parser.PushThunk(SourceCodeSyntax0.Instance, BeginIdx, NodeSize);
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
class TopLevelDefinitionSyntax extends Syntax {
	TopLevelDefinitionSyntax() {
		super("$TopLevelDefinition");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new functionDefinitionSyntax(), false);
		Parser.AddSyntax(this, new statementSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $TopLevelDefinition");
		Parser.PushThunk(TopLevelDefinitionSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $TopLevelDefinition");
		Parser.PushThunk(TopLevelDefinitionSyntax1.Instance, BeginIdx, NodeSize);
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
class functionSignatureSyntax extends Syntax {
	functionSignatureSyntax() {
		super("$functionSignature");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new typeSyntax(), false);
		Parser.AddSyntax(this, new ParamDeclListSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $functionSignature");
		Parser.PushThunk(functionSignatureSyntax0.Instance, BeginIdx, NodeSize);
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
class functionBodySyntax extends Syntax {
	functionBodySyntax() {
		super("$functionBody");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new blockSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $functionBody");
		Parser.PushThunk(functionBodySyntax0.Instance, BeginIdx, NodeSize);
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
class functionDefinitionSyntax extends Syntax {
	functionDefinitionSyntax() {
		super("$functionDefinition");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new functionSignatureSyntax(), false);
		Parser.AddSyntax(this, new functionBodySyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $functionDefinition");
		Parser.PushThunk(functionDefinitionSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $functionDefinition");
		Parser.PushThunk(functionDefinitionSyntax1.Instance, BeginIdx, NodeSize);
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
class ParamDeclListSyntax extends Syntax {
	ParamDeclListSyntax() {
		super("$ParamDeclList");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParamDeclsSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ParamDeclList");
		Parser.PushThunk(ParamDeclListSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ParamDeclList");
		Parser.PushThunk(ParamDeclListSyntax1.Instance, BeginIdx, NodeSize);
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
class ParamDeclsSyntax extends Syntax {
	ParamDeclsSyntax() {
		super("$ParamDecls");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParamDeclSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ParamDecls");
		Parser.PushThunk(ParamDeclsSyntax0.Instance, BeginIdx, NodeSize);
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
class ParamDeclSyntax extends Syntax {
	ParamDeclSyntax() {
		super("$ParamDecl");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new typeSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ParamDecl");
		Parser.PushThunk(ParamDeclSyntax0.Instance, BeginIdx, NodeSize);
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
class ParameterListSyntax extends Syntax {
	ParameterListSyntax() {
		super("$ParameterList");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParametersSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ParameterList");
		Parser.PushThunk(ParameterListSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ParameterList");
		Parser.PushThunk(ParameterListSyntax1.Instance, BeginIdx, NodeSize);
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
class ParametersSyntax extends Syntax {
	ParametersSyntax() {
		super("$Parameters");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParameterSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Parameters");
		Parser.PushThunk(ParametersSyntax0.Instance, BeginIdx, NodeSize);
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
class ParameterSyntax extends Syntax {
	ParameterSyntax() {
		super("$Parameter");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Parameter");
		Parser.PushThunk(ParameterSyntax0.Instance, BeginIdx, NodeSize);
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
class literalSyntax extends Syntax {
	literalSyntax() {
		super("$literal");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new stringLiteralSyntax(), false);
		Parser.AddSyntax(this, new intLiteralSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $literal");
		Parser.PushThunk(literalSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $literal");
		Parser.PushThunk(literalSyntax1.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $literal");
		Parser.PushThunk(literalSyntax2.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action3(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $literal");
		Parser.PushThunk(literalSyntax3.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action4(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $literal");
		Parser.PushThunk(literalSyntax4.Instance, BeginIdx, NodeSize);
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
class typeSyntax extends Syntax {
	typeSyntax() {
		super("$type");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new TypeTokenSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $type");
		Parser.PushThunk(typeSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $type");
		Parser.PushThunk(typeSyntax1.Instance, BeginIdx, NodeSize);
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
class statementSyntax extends Syntax {
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

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(statementSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(statementSyntax1.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(statementSyntax2.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action3(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(statementSyntax3.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action4(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(statementSyntax4.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action5(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(statementSyntax5.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action6(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(statementSyntax6.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action7(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statement");
		Parser.PushThunk(statementSyntax7.Instance, BeginIdx, NodeSize);
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
class variableSyntax extends Syntax {
	variableSyntax() {
		super("$variable");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $variable");
		Parser.PushThunk(variableSyntax0.Instance, BeginIdx, NodeSize);
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
class EQSyntax extends Syntax {
	EQSyntax() {
		super("$EQ");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $EQ");
		Parser.PushThunk(EQSyntax0.Instance, BeginIdx, NodeSize);
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
class variableDeclarationSyntax extends Syntax {
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

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $variableDeclaration");
		Parser.PushThunk(variableDeclarationSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $variableDeclaration");
		Parser.PushThunk(variableDeclarationSyntax1.Instance, BeginIdx, NodeSize);
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
class statementsSyntax extends Syntax {
	statementsSyntax() {
		super("$statements");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new statementSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $statements");
		Parser.PushThunk(statementsSyntax0.Instance, BeginIdx, NodeSize);
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
class blockSyntax extends Syntax {
	blockSyntax() {
		super("$block");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new statementsSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $block");
		Parser.PushThunk(blockSyntax0.Instance, BeginIdx, NodeSize);
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
class ifStatementSyntax extends Syntax {
	ifStatementSyntax() {
		super("$ifStatement");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
		Parser.AddSyntax(this, new blockSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ifStatement");
		Parser.PushThunk(ifStatementSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $ifStatement");
		Parser.PushThunk(ifStatementSyntax1.Instance, BeginIdx, NodeSize);
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
class whileStatementSyntax extends Syntax {
	whileStatementSyntax() {
		super("$whileStatement");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
		Parser.AddSyntax(this, new blockSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $whileStatement");
		Parser.PushThunk(whileStatementSyntax0.Instance, BeginIdx, NodeSize);
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
class breakStatementSyntax extends Syntax {
	breakStatementSyntax() {
		super("$breakStatement");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $breakStatement");
		Parser.PushThunk(breakStatementSyntax0.Instance, BeginIdx, NodeSize);
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
class continueStatementSyntax extends Syntax {
	continueStatementSyntax() {
		super("$continueStatement");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $continueStatement");
		Parser.PushThunk(continueStatementSyntax0.Instance, BeginIdx, NodeSize);
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
class returnStatementSyntax extends Syntax {
	returnStatementSyntax() {
		super("$returnStatement");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $returnStatement");
		Parser.PushThunk(returnStatementSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $returnStatement");
		Parser.PushThunk(returnStatementSyntax1.Instance, BeginIdx, NodeSize);
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
class expressionStatementSyntax extends Syntax {
	expressionStatementSyntax() {
		super("$expressionStatement");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $expressionStatement");
		Parser.PushThunk(expressionStatementSyntax0.Instance, BeginIdx, NodeSize);
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
class expressionSyntax extends Syntax {
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

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $expression");
		Parser.PushThunk(expressionSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $expression");
		Parser.PushThunk(expressionSyntax1.Instance, BeginIdx, NodeSize);
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
		<Symbol:$callEpxression>
	]
	[
		<Symbol:$newExpression>
	]
]
*/
class leftHandSideExpressionSyntax extends Syntax {
	leftHandSideExpressionSyntax() {
		super("$leftHandSideExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new newExpressionSyntax(), false);
		Parser.AddSyntax(this, new callEpxressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $leftHandSideExpression");
		Parser.PushThunk(leftHandSideExpressionSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $leftHandSideExpression");
		Parser.PushThunk(leftHandSideExpressionSyntax1.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $leftHandSideExpression");
		if(Parser.Match("$callEpxression", TokenList) >= 0) {
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
[$callEpxression:
	[
		<Symbol:$memberExpression>
		<Symbol:$ParameterList>
	]
]
*/
class callEpxressionSyntax extends Syntax {
	callEpxressionSyntax() {
		super("$callEpxression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new ParameterListSyntax(), false);
		Parser.AddSyntax(this, new memberExpressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $callEpxression");
		Parser.PushThunk(callEpxressionSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $callEpxression");
		if(Parser.Match("$memberExpression", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$ParameterList", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				return this.action0("$callEpxression", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $callEpxression 0");
		return this.Fail("$callEpxression", Parser);
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
class memberExpressionSyntax extends Syntax {
	memberExpressionSyntax() {
		super("$memberExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new primarySyntax(), false);
		Parser.AddSyntax(this, new selectorSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $memberExpression");
		Parser.PushThunk(memberExpressionSyntax0.Instance, BeginIdx, NodeSize);
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
class primarySyntax extends Syntax {
	primarySyntax() {
		super("$primary");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new literalSyntax(), false);
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $primary");
		Parser.PushThunk(primarySyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $primary");
		Parser.PushThunk(primarySyntax1.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $primary");
		Parser.PushThunk(primarySyntax2.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action3(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $primary");
		Parser.PushThunk(primarySyntax3.Instance, BeginIdx, NodeSize);
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
class selectorSyntax extends Syntax {
	selectorSyntax() {
		super("$selector");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new identifierSyntax(), false);
		Parser.AddSyntax(this, new expressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $selector");
		Parser.PushThunk(selectorSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $selector");
		Parser.PushThunk(selectorSyntax1.Instance, BeginIdx, NodeSize);
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
class newExpressionSyntax extends Syntax {
	newExpressionSyntax() {
		super("$newExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new typeSyntax(), false);
		Parser.AddSyntax(this, new ParameterListSyntax(), false);
		Parser.AddSyntax(this, new memberExpressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $newExpression");
		Parser.PushThunk(newExpressionSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $newExpression");
		Parser.PushThunk(newExpressionSyntax1.Instance, BeginIdx, NodeSize);
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
class logicalOrExpressionSyntax extends Syntax {
	logicalOrExpressionSyntax() {
		super("$logicalOrExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new logicalAndExpressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $logicalOrExpression");
		Parser.PushThunk(logicalOrExpressionSyntax0.Instance, BeginIdx, NodeSize);
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
class logicalAndExpressionSyntax extends Syntax {
	logicalAndExpressionSyntax() {
		super("$logicalAndExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new relationExpressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $logicalAndExpression");
		Parser.PushThunk(logicalAndExpressionSyntax0.Instance, BeginIdx, NodeSize);
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
class relationExpressionSyntax extends Syntax {
	relationExpressionSyntax() {
		super("$relationExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new additiveExpressionSyntax(), false);
		Parser.AddSyntax(this, new relationOperatorSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationExpression");
		Parser.PushThunk(relationExpressionSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationExpression");
		Parser.PushThunk(relationExpressionSyntax1.Instance, BeginIdx, NodeSize);
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
class relationOperatorSyntax extends Syntax {
	relationOperatorSyntax() {
		super("$relationOperator");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationOperator");
		Parser.PushThunk(relationOperatorSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationOperator");
		Parser.PushThunk(relationOperatorSyntax1.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationOperator");
		Parser.PushThunk(relationOperatorSyntax2.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action3(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationOperator");
		Parser.PushThunk(relationOperatorSyntax3.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action4(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationOperator");
		Parser.PushThunk(relationOperatorSyntax4.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action5(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $relationOperator");
		Parser.PushThunk(relationOperatorSyntax5.Instance, BeginIdx, NodeSize);
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
class shiftOperatorSyntax extends Syntax {
	shiftOperatorSyntax() {
		super("$shiftOperator");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $shiftOperator");
		Parser.PushThunk(shiftOperatorSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $shiftOperator");
		Parser.PushThunk(shiftOperatorSyntax1.Instance, BeginIdx, NodeSize);
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
class additiveOperatorSyntax extends Syntax {
	additiveOperatorSyntax() {
		super("$additiveOperator");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $additiveOperator");
		Parser.PushThunk(additiveOperatorSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $additiveOperator");
		Parser.PushThunk(additiveOperatorSyntax1.Instance, BeginIdx, NodeSize);
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
class multiplicativeOperatorSyntax extends Syntax {
	multiplicativeOperatorSyntax() {
		super("$multiplicativeOperator");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $multiplicativeOperator");
		Parser.PushThunk(multiplicativeOperatorSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $multiplicativeOperator");
		Parser.PushThunk(multiplicativeOperatorSyntax1.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action2(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $multiplicativeOperator");
		Parser.PushThunk(multiplicativeOperatorSyntax2.Instance, BeginIdx, NodeSize);
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
class additiveExpressionSyntax extends Syntax {
	additiveExpressionSyntax() {
		super("$additiveExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new additiveOperatorSyntax(), false);
		Parser.AddSyntax(this, new multiplicativeExpressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $additiveExpression");
		Parser.PushThunk(additiveExpressionSyntax0.Instance, BeginIdx, NodeSize);
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
class multiplicativeExpressionSyntax extends Syntax {
	multiplicativeExpressionSyntax() {
		super("$multiplicativeExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new multiplicativeOperatorSyntax(), false);
		Parser.AddSyntax(this, new unaryExpressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $multiplicativeExpression");
		Parser.PushThunk(multiplicativeExpressionSyntax0.Instance, BeginIdx, NodeSize);
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
class unaryExpressionSyntax extends Syntax {
	unaryExpressionSyntax() {
		super("$unaryExpression");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new leftHandSideExpressionSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $unaryExpression");
		Parser.PushThunk(unaryExpressionSyntax0.Instance, BeginIdx, NodeSize);
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
class identifierSyntax extends Syntax {
	identifierSyntax() {
		super("$identifier");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new SymbolSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $identifier");
		Parser.PushThunk(identifierSyntax0.Instance, BeginIdx, NodeSize);
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
