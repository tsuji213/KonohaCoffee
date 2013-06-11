package org.KonohaScript.Parser;

import org.KonohaScript.KLib.TokenList;

/*
[$Exp:
	[
		<Symbol:$Term>
		<Repeat:<Group:<Symbol:$AddOp> <Symbol:$Term> >>
	]
]
*/
class ExpSyntax extends Syntax {
	ExpSyntax() {
		super("$Exp");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new TermSyntax(), false);
		Parser.AddSyntax(this, new AddOpSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Exp");
		Parser.PushThunk(ExpSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $Exp");
		if(Parser.Match("$Term", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.Match("$AddOp", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					if(Parser.Match("$Term", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						continue;
					}
				}
				return this.action0("$Exp", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $Exp 0");
		return this.Fail("$Exp", Parser);
	}
}

/*
[$Term:
	[
		<Symbol:$Factor>
		<Repeat:<Group:<Symbol:$MulOp> <Symbol:$Factor> >>
	]
]
*/
class TermSyntax extends Syntax {
	TermSyntax() {
		super("$Term");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new FactorSyntax(), false);
		Parser.AddSyntax(this, new MulOpSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Term");
		Parser.PushThunk(TermSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $Term");
		if(Parser.Match("$Factor", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			while(true) {
				if(Parser.Match("$MulOp", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					if(Parser.Match("$Factor", TokenList) >= 0) {
						NodeSize = NodeSize + 1;
						continue;
					}
				}
				return this.action0("$Term", Parser, pos0, NodeSize);
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $Term 0");
		return this.Fail("$Term", Parser);
	}
}

/*
[$Factor:
	[
		<Symbol:$Number>
	]
	[
		<Symbol:$Group>
	]
]
*/
class FactorSyntax extends Syntax {
	FactorSyntax() {
		super("$Factor");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new GroupSyntax(), false);
		Parser.AddSyntax(this, new NumberSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Factor");
		Parser.PushThunk(FactorSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Factor");
		Parser.PushThunk(FactorSyntax1.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $Factor");
		if(Parser.Match("$Number", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action0("$Factor", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $Factor 0");
		if(Parser.Match("$Group", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			return this.action1("$Factor", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $Factor 0");
		return this.Fail("$Factor", Parser);
	}
}

/*
[$Number:
	[
		<Symbol:"number">
	]
]
*/
class NumberSyntax extends Syntax {
	NumberSyntax() {
		super("$Number");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Number");
		Parser.PushThunk(NumberSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $Number");
		if(Parser.MatchToken("number", TokenList, Parser.Cursor) >= 0) {
			return this.action0("$Number", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $Number 0");
		return this.Fail("$Number", Parser);
	}
}

/*
[$Group:
	[
		<Symbol:$OpenParen>
		<Symbol:$Exp>
		<Symbol:$CloseParen>
	]
]
*/
class GroupSyntax extends Syntax {
	GroupSyntax() {
		super("$Group");
	}

	@Override
	void Init(SyntaxModule Parser) {
		Parser.AddSyntax(this, new OpenParenSyntax(), false);
		Parser.AddSyntax(this, new CloseParenSyntax(), false);
		Parser.AddSyntax(this, new ExpSyntax(), false);
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $Group");
		Parser.PushThunk(GroupSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $Group");
		if(Parser.Match("$OpenParen", TokenList) >= 0) {
			NodeSize = NodeSize + 1;
			if(Parser.Match("$Exp", TokenList) >= 0) {
				NodeSize = NodeSize + 1;
				if(Parser.Match("$CloseParen", TokenList) >= 0) {
					NodeSize = NodeSize + 1;
					return this.action0("$Group", Parser, pos0, NodeSize);
				}
			}
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $Group 0");
		return this.Fail("$Group", Parser);
	}
}

/*
[$AddOp:
	[
		<Symbol:"+">
	]
	[
		<Symbol:"-">
	]
]
*/
class AddOpSyntax extends Syntax {
	AddOpSyntax() {
		super("$AddOp");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $AddOp");
		Parser.PushThunk(AddOpSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $AddOp");
		Parser.PushThunk(AddOpSyntax1.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $AddOp");
		if(Parser.MatchToken("+", TokenList, Parser.Cursor) >= 0) {
			return this.action0("$AddOp", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $AddOp 0");
		if(Parser.MatchToken("-", TokenList, Parser.Cursor) >= 0) {
			return this.action1("$AddOp", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $AddOp 0");
		return this.Fail("$AddOp", Parser);
	}
}

/*
[$MulOp:
	[
		<Symbol:"*">
	]
	[
		<Symbol:"/">
	]
]
*/
class MulOpSyntax extends Syntax {
	MulOpSyntax() {
		super("$MulOp");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $MulOp");
		Parser.PushThunk(MulOpSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	int action1(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $MulOp");
		Parser.PushThunk(MulOpSyntax1.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $MulOp");
		if(Parser.MatchToken("*", TokenList, Parser.Cursor) >= 0) {
			return this.action0("$MulOp", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $MulOp 0");
		if(Parser.MatchToken("/", TokenList, Parser.Cursor) >= 0) {
			return this.action1("$MulOp", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $MulOp 0");
		return this.Fail("$MulOp", Parser);
	}
}

/*
[$OpenParen:
	[
		<Symbol:"(">
	]
]
*/
class OpenParenSyntax extends Syntax {
	OpenParenSyntax() {
		super("$OpenParen");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $OpenParen");
		Parser.PushThunk(OpenParenSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $OpenParen");
		if(Parser.MatchToken("(", TokenList, Parser.Cursor) >= 0) {
			return this.action0("$OpenParen", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $OpenParen 0");
		return this.Fail("$OpenParen", Parser);
	}
}

/*
[$CloseParen:
	[
		<Symbol:")">
	]
]
*/
class CloseParenSyntax extends Syntax {
	CloseParenSyntax() {
		super("$CloseParen");
	}

	@Override
	void Init(SyntaxModule Parser) {
	}

	int action0(String SyntaxName, SyntaxModule Parser, int BeginIdx, int NodeSize) {
		this.Report("Accept $CloseParen");
		Parser.PushThunk(CloseParenSyntax0.Instance, BeginIdx, NodeSize);
		return Parser.Cursor;
	}

	@Override
	int Match(SyntaxModule Parser, TokenList TokenList) {
		int NodeSize = 0;
		int pos0 = Parser.Cursor;
		int thunkpos0 = Parser.ThunkPos;
		int NodeSize0 = NodeSize;
		this.Report("Enter $CloseParen");
		if(Parser.MatchToken(")", TokenList, Parser.Cursor) >= 0) {
			return this.action0("$CloseParen", Parser, pos0, NodeSize);
		}
		NodeSize = this.BackTrack(Parser, pos0, thunkpos0, NodeSize0, "BackTrack $CloseParen 0");
		return this.Fail("$CloseParen", Parser);
	}
}
