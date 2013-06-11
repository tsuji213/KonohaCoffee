package org.KonohaScript.Parser;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.TypeEnv;
import org.KonohaScript.UntypedNode;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.SyntaxTree.TypedNode;

// action: <Symbol:$Term>, <Repeat:<Group:<Symbol:$AddOp> <Symbol:$Term> >>
class ExpSyntax0 extends SyntaxAcceptor {
	static final ExpSyntax0	Instance	= new ExpSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		//System.out.println("ExpSyntax0 : " + NodeSize);
		int Index = 0;
		Object[] List = new Object[NodeSize];
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		while(Index < NodeSize) {
			List[Index] = Parser.Get(Index, NodeSize);
			Index = Index + 1;
			List[Index] = Parser.Get(Index, NodeSize);
			Index = Index + 1;
		}
		Parser.ReAssign(NodeSize, List[0]);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$Factor>, <Repeat:<Group:<Symbol:$MulOp> <Symbol:$Factor> >>
class TermSyntax0 extends SyntaxAcceptor {
	static final TermSyntax0	Instance	= new TermSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		//System.out.println("TermSyntax0 : " + NodeSize);
		int Index = 0;
		Object[] List = new Object[NodeSize];
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		while(Index < NodeSize) {
			List[Index] = Parser.Get(Index, NodeSize);
			Index = Index + 1;
			List[Index] = Parser.Get(Index, NodeSize);
			Index = Index + 1;
		}
		Parser.ReAssign(NodeSize, List[0]);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$Number>
class FactorSyntax0 extends SyntaxAcceptor {
	static final FactorSyntax0	Instance	= new FactorSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		//System.out.println("FactorSyntax0 : " + NodeSize);
		int Index = 0;
		Object[] List = new Object[NodeSize];
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		Parser.ReAssign(NodeSize, List[0]);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$Group>
class FactorSyntax1 extends SyntaxAcceptor {
	static final FactorSyntax1	Instance	= new FactorSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		//System.out.println("FactorSyntax1 : " + NodeSize);
		int Index = 0;
		Object[] List = new Object[NodeSize];
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		Parser.ReAssign(NodeSize, List[0]);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"number">
class NumberSyntax0 extends SyntaxAcceptor {
	static final NumberSyntax0	Instance	= new NumberSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		//System.out.println("NumberSyntax0 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(new UntypedNode(Parser.NameSpace, KeyToken));

		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$OpenParen>, <Symbol:$Exp>, <Symbol:$CloseParen>
class GroupSyntax0 extends SyntaxAcceptor {
	static final GroupSyntax0	Instance	= new GroupSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		//System.out.println("GroupSyntax0 : " + NodeSize);
		int Index = 0;
		Object[] List = new Object[NodeSize];
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		Parser.ReAssign(NodeSize, List[0]);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"+">
class AddOpSyntax0 extends SyntaxAcceptor {
	static final AddOpSyntax0	Instance	= new AddOpSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		//System.out.println("AddOpSyntax0 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(new UntypedNode(Parser.NameSpace, KeyToken));

		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"-">
class AddOpSyntax1 extends SyntaxAcceptor {
	static final AddOpSyntax1	Instance	= new AddOpSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		//System.out.println("AddOpSyntax1 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(new UntypedNode(Parser.NameSpace, KeyToken));

		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"*">
class MulOpSyntax0 extends SyntaxAcceptor {
	static final MulOpSyntax0	Instance	= new MulOpSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		//System.out.println("MulOpSyntax0 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(new UntypedNode(Parser.NameSpace, KeyToken));

		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"/">
class MulOpSyntax1 extends SyntaxAcceptor {
	static final MulOpSyntax1	Instance	= new MulOpSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		//System.out.println("MulOpSyntax1 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(new UntypedNode(Parser.NameSpace, KeyToken));

		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"(">
class OpenParenSyntax0 extends SyntaxAcceptor {
	static final OpenParenSyntax0	Instance	= new OpenParenSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		//System.out.println("OpenParenSyntax0 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(new UntypedNode(Parser.NameSpace, KeyToken));

		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:")">
class CloseParenSyntax0 extends SyntaxAcceptor {
	static final CloseParenSyntax0	Instance	= new CloseParenSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		//System.out.println("CloseParenSyntax0 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(new UntypedNode(Parser.NameSpace, KeyToken));

		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}
