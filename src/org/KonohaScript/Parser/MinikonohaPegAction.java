package org.KonohaScript.Parser;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.TypeEnv;
import org.KonohaScript.UntypedNode;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.SyntaxTree.TypedNode;

// action: <Repeat:<Symbol:$TopLevelDefinition>>
class SourceCodeSyntax0 extends SyntaxAcceptor {
	static final SourceCodeSyntax0	Instance	= new SourceCodeSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("SourceCodeSyntax0 : " + NodeSize);
		int Index = 0;
		Object TopDecl = null;
		while(Index < NodeSize) {
			TopDecl = Parser.Get(Index, NodeSize);
			Index = Index + 1;
		}
		Parser.ReAssign(NodeSize, TopDecl);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$statement>
class TopLevelDefinitionSyntax0 extends SyntaxAcceptor {
	static final TopLevelDefinitionSyntax0	Instance	= new TopLevelDefinitionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("TopLevelDefinitionSyntax0 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$functionDefinition>
class TopLevelDefinitionSyntax1 extends SyntaxAcceptor {
	static final TopLevelDefinitionSyntax1	Instance	= new TopLevelDefinitionSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("TopLevelDefinitionSyntax1 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$type>, <Symbol:$identifier>, <Symbol:$ParamDeclList>
class functionSignatureSyntax0 extends SyntaxAcceptor {
	static final functionSignatureSyntax0	Instance	= new functionSignatureSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("functionSignatureSyntax0 : " + NodeSize);
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, new KonohaToken("$MethodDecl"));
		int Index = 0;
		UNode.SetAtNode(0, (UntypedNode) Parser.Get(Index, NodeSize)); // ReturnType
		UNode.SetAtNode(1, null); // Receiver
		Index = Index + 1;
		UNode.SetAtNode(2, (UntypedNode) Parser.Get(Index, NodeSize)); // MethodName
		UNode.SetAtNode(3, null); // MethodBody
		Index = Index + 1;

		KonohaArray ParamList = (KonohaArray) Parser.Get(Index, NodeSize);
		for(int i = 0; i < ParamList.size(); i++) {
			UNode.SetAtToken(i + 4, (KonohaToken) ParamList.get(i));
		}
		Index = Index + 1;
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, UNode);
		}
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$block>
class functionBodySyntax0 extends SyntaxAcceptor {
	static final functionBodySyntax0	Instance	= new functionBodySyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("functionBodySyntax0 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$functionSignature>, <Symbol:$functionBody>
class functionDefinitionSyntax0 extends SyntaxAcceptor {
	static final functionDefinitionSyntax0	Instance	= new functionDefinitionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("functionDefinitionSyntax0 : " + NodeSize);
		int Index = 0;
		UntypedNode UNode = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		KonohaArray Body = (KonohaArray) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		if(Body.size() > 0) {
			UNode.SetAtNode(3, (UntypedNode) Body.get(0));
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, UNode);
		}
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$functionSignature>, <Symbol:";">
class functionDefinitionSyntax1 extends SyntaxAcceptor {
	static final functionDefinitionSyntax1	Instance	= new functionDefinitionSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("functionDefinitionSyntax1 : " + NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"(">, <Symbol:")">
class ParamDeclListSyntax0 extends SyntaxAcceptor {
	static final ParamDeclListSyntax0	Instance	= new ParamDeclListSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("ParamDeclListSyntax0 : " + NodeSize);
		Parser.Push(new KonohaArray());
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"(">, <Symbol:$ParamDecls>, <Symbol:")">
class ParamDeclListSyntax1 extends SyntaxAcceptor {
	static final ParamDeclListSyntax1	Instance	= new ParamDeclListSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("ParamDeclListSyntax1 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$ParamDecl>, <Repeat:<Group:<Symbol:","> <Symbol:$ParamDecl> >>
class ParamDeclsSyntax0 extends SyntaxAcceptor {
	static final ParamDeclsSyntax0	Instance	= new ParamDeclsSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("ParamDeclsSyntax0 : " + NodeSize);
		int Index = 0;
		KonohaArray List = new KonohaArray();
		KonohaArray Param = (KonohaArray) Parser.Get(Index, NodeSize);
		List.add(Param.get(0)); // Type
		List.add(Param.get(1)); // ParamName
		Index = Index + 1;
		while(Index < NodeSize) {
			Param = (KonohaArray) Parser.Get(Index, NodeSize);
			List.add(Param.get(0)); // Type
			List.add(Param.get(1)); // ParamName
			Index = Index + 1;
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, List);
		}
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$type>, <Symbol:$identifier>
class ParamDeclSyntax0 extends SyntaxAcceptor {
	static final ParamDeclSyntax0	Instance	= new ParamDeclSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("ParamDeclSyntax0 : " + NodeSize);
		KonohaArray List = new KonohaArray();
		int Index = 0;
		UntypedNode TypeDecl = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UntypedNode SymbolDecl = (UntypedNode) Parser.Get(Index, NodeSize);
		List.add(TypeDecl.KeyToken);
		List.add(SymbolDecl.KeyToken);
		Parser.ReAssign(NodeSize, List);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"(">, <Symbol:")">
class ParameterListSyntax0 extends SyntaxAcceptor {
	static final ParameterListSyntax0	Instance	= new ParameterListSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("ParameterListSyntax0 : " + NodeSize);
		Parser.Push(new KonohaArray());
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"(">, <Symbol:$Parameters>, <Symbol:")">
class ParameterListSyntax1 extends SyntaxAcceptor {
	static final ParameterListSyntax1	Instance	= new ParameterListSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("ParameterListSyntax1 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$Parameter>, <Repeat:<Group:<Symbol:","> <Symbol:$Parameter> >>
class ParametersSyntax0 extends SyntaxAcceptor {
	static final ParametersSyntax0	Instance	= new ParametersSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("ParametersSyntax0 : " + NodeSize);
		int Index = 0;
		KonohaArray List = new KonohaArray();
		List.add(Parser.Get(Index, NodeSize));
		Index = Index + 1;
		while(Index < NodeSize) {
			List.add(Parser.Get(Index, NodeSize));
			Index = Index + 1;
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, List);
		}
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$expression>
class ParameterSyntax0 extends SyntaxAcceptor {
	static final ParameterSyntax0	Instance	= new ParameterSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("ParameterSyntax0 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"null">
class literalSyntax0 extends SyntaxAcceptor {
	static final literalSyntax0	Instance	= new literalSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("literalSyntax0 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(new UntypedNode(Parser.NameSpace, KeyToken));
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"true">
class literalSyntax1 extends SyntaxAcceptor {
	static final literalSyntax1	Instance	= new literalSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("literalSyntax1 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(new UntypedNode(Parser.NameSpace, KeyToken));
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"false">
class literalSyntax2 extends SyntaxAcceptor {
	static final literalSyntax2	Instance	= new literalSyntax2();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("literalSyntax2 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(new UntypedNode(Parser.NameSpace, KeyToken));
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$intLiteral>
class literalSyntax3 extends SyntaxAcceptor {
	static final literalSyntax3	Instance	= new literalSyntax3();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("literalSyntax3 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$stringLiteral>
class literalSyntax4 extends SyntaxAcceptor {
	static final literalSyntax4	Instance	= new literalSyntax4();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("literalSyntax4 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$Type>
class typeSyntax0 extends SyntaxAcceptor {
	static final typeSyntax0	Instance	= new typeSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("typeSyntax0 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$Type>, <Repeat:<Group:<Symbol:"["> <Symbol:"]"> >>
class typeSyntax1 extends SyntaxAcceptor {
	static final typeSyntax1	Instance	= new typeSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("typeSyntax1 : " + NodeSize);
		int Index = 0;
		Object[] List = new Object[NodeSize];
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		int i = EndIdx;
		// Type
		while(i > 0) {
			KonohaToken Open = TokenList.get(i - 1);
			KonohaToken Close = TokenList.get(i - 0);
			if(!(Open.ParsedText.equals("[") && Close.ParsedText.equals("]"))) {
				break;
			}
			// Type = Array of Type
			i = i - 2;
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, List[0]);
		}
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$block>
class statementSyntax0 extends SyntaxAcceptor {
	static final statementSyntax0	Instance	= new statementSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("statementSyntax0 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$variableDeclaration>
class statementSyntax1 extends SyntaxAcceptor {
	static final statementSyntax1	Instance	= new statementSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("statementSyntax1 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$expressionStatement>
class statementSyntax2 extends SyntaxAcceptor {
	static final statementSyntax2	Instance	= new statementSyntax2();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("statementSyntax2 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$ifStatement>
class statementSyntax3 extends SyntaxAcceptor {
	static final statementSyntax3	Instance	= new statementSyntax3();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("statementSyntax3 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$whileStatement>
class statementSyntax4 extends SyntaxAcceptor {
	static final statementSyntax4	Instance	= new statementSyntax4();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("statementSyntax4 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$breakStatement>
class statementSyntax5 extends SyntaxAcceptor {
	static final statementSyntax5	Instance	= new statementSyntax5();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("statementSyntax5 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$continueStatement>
class statementSyntax6 extends SyntaxAcceptor {
	static final statementSyntax6	Instance	= new statementSyntax6();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("statementSyntax6 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$returnStatement>
class statementSyntax7 extends SyntaxAcceptor {
	static final statementSyntax7	Instance	= new statementSyntax7();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("statementSyntax7 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$identifier>
class variableSyntax0 extends SyntaxAcceptor {
	static final variableSyntax0	Instance	= new variableSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("variableSyntax0 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"=">
class EQSyntax0 extends SyntaxAcceptor {
	static final EQSyntax0	Instance	= new EQSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("EQSyntax0 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(new UntypedNode(Parser.NameSpace, KeyToken));
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$type>, <Symbol:$identifier>, <Symbol:";">
class variableDeclarationSyntax0 extends SyntaxAcceptor {
	static final variableDeclarationSyntax0	Instance	= new variableDeclarationSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("variableDeclarationSyntax0 : " + NodeSize);
		int Index = 0;
		Object[] List = new Object[NodeSize];
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, List[0]);
		}
		throw new RuntimeException();
		//return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$type>, <Symbol:$variable>, <Symbol:$EQ>, <Symbol:$expression>, <Symbol:";">
class variableDeclarationSyntax1 extends SyntaxAcceptor {
	static final variableDeclarationSyntax1	Instance	= new variableDeclarationSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("variableDeclarationSyntax1 : " + NodeSize);
		int Index = 0;
		Object[] List = new Object[NodeSize];
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, List[0]);
		}
		throw new RuntimeException();
		//return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Repeat:<Symbol:$statement>>
class statementsSyntax0 extends SyntaxAcceptor {
	static final statementsSyntax0	Instance	= new statementsSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("statementsSyntax0 : " + NodeSize);
		int Index = 0;
		KonohaArray List = new KonohaArray();
		while(Index < NodeSize) {
			List.add(Parser.Get(Index, NodeSize));
			Index = Index + 1;
		}
		for(int i = 0; i < List.size() - 1; i++) {
			UntypedNode Current = (UntypedNode) List.get(i);
			UntypedNode Next = (UntypedNode) List.get(i + 1);
			Current.LinkNode(Next);
		}
		Parser.ReAssign(NodeSize, List);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"{">, <Symbol:$statements>, <Symbol:"}">
class blockSyntax0 extends SyntaxAcceptor {
	static final blockSyntax0	Instance	= new blockSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("blockSyntax0 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

//action: <Symbol:"if">, <Symbol:"(">, <Symbol:$expression>, <Symbol:")">, <Symbol:$block>, <Symbol:"else">, <Symbol:$block>
class ifStatementSyntax0 extends SyntaxAcceptor {
	static final ifStatementSyntax0	Instance	= new ifStatementSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("ifStatementSyntax0 : " + NodeSize);
		int Index = 0;
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		UNode.SetAtNode(0, (UntypedNode) Parser.Get(Index, NodeSize));
		Index = Index + 1;
		KonohaArray ThenBlock = (KonohaArray) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		KonohaArray ElseBlock = (KonohaArray) Parser.Get(Index, NodeSize);
		Index = Index + 1;

		if(ThenBlock.size() > 0) {
			UNode.SetAtNode(1, (UntypedNode) ThenBlock.get(0));
		}
		if(ElseBlock.size() > 0) {
			UNode.SetAtNode(2, (UntypedNode) ElseBlock.get(0));
		}
		Parser.ReAssign(NodeSize, UNode);

		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"if">, <Symbol:"(">, <Symbol:$expression>, <Symbol:")">, <Symbol:$block>
class ifStatementSyntax1 extends SyntaxAcceptor {
	static final ifStatementSyntax1	Instance	= new ifStatementSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("ifStatementSyntax1 : " + NodeSize);
		int Index = 0;
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		UNode.SetAtNode(0, (UntypedNode) Parser.Get(Index, NodeSize));
		Index = Index + 1;
		KonohaArray ThenBlock = (KonohaArray) Parser.Get(Index, NodeSize);
		Index = Index + 1;

		if(ThenBlock.size() > 0) {
			UNode.SetAtNode(1, (UntypedNode) ThenBlock.get(0));
		}

		UNode.SetAtNode(2, null);

		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"while">, <Symbol:"(">, <Symbol:$expression>, <Symbol:")">, <Symbol:$block>
class whileStatementSyntax0 extends SyntaxAcceptor {
	static final whileStatementSyntax0	Instance	= new whileStatementSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("whileStatementSyntax0 : " + NodeSize);
		int Index = 0;
		Object[] List = new Object[NodeSize];
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		UNode.SetAtNode(0, (UntypedNode) List[0]);
		UNode.SetAtNode(1, (UntypedNode) List[1]);

		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, UNode);
		}
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"break">, <Symbol:";">
class breakStatementSyntax0 extends SyntaxAcceptor {
	static final breakStatementSyntax0	Instance	= new breakStatementSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("breakStatementSyntax0 : " + NodeSize);
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"continue">, <Symbol:";">
class continueStatementSyntax0 extends SyntaxAcceptor {
	static final continueStatementSyntax0	Instance	= new continueStatementSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("continueStatementSyntax0 : " + NodeSize);
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"return">, <Symbol:";">
class returnStatementSyntax0 extends SyntaxAcceptor {
	static final returnStatementSyntax0	Instance	= new returnStatementSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("returnStatementSyntax0 : " + NodeSize);
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		UNode.SetAtNode(0, null);
		Parser.ReAssign(NodeSize, UNode);

		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"return">, <Symbol:$expression>, <Symbol:";">
class returnStatementSyntax1 extends SyntaxAcceptor {
	static final returnStatementSyntax1	Instance	= new returnStatementSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("returnStatementSyntax1 : " + NodeSize);
		int Index = 0;
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		UNode.SetAtNode(0, (UntypedNode) Parser.Get(Index, NodeSize));
		Parser.ReAssign(NodeSize, UNode);

		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, UNode);
		}
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$expression>, <Symbol:";">
class expressionStatementSyntax0 extends SyntaxAcceptor {
	static final expressionStatementSyntax0	Instance	= new expressionStatementSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("expressionStatementSyntax0 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$leftHandSideExpression>, <Symbol:$EQ>, <Symbol:$expression>
class expressionSyntax0 extends SyntaxAcceptor {
	static final expressionSyntax0	Instance	= new expressionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("expressionSyntax0 : " + NodeSize);
		int Index = 0;
		Object[] List = new Object[NodeSize];
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, List[0]);
		}
		throw new RuntimeException();
		//return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$logicalOrExpression>
class expressionSyntax1 extends SyntaxAcceptor {
	static final expressionSyntax1	Instance	= new expressionSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("expressionSyntax1 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$callEpxression>
class leftHandSideExpressionSyntax0 extends SyntaxAcceptor {
	static final leftHandSideExpressionSyntax0	Instance	= new leftHandSideExpressionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("leftHandSideExpressionSyntax0 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$newExpression>
class leftHandSideExpressionSyntax1 extends SyntaxAcceptor {
	static final leftHandSideExpressionSyntax1	Instance	= new leftHandSideExpressionSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("leftHandSideExpressionSyntax1 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$memberExpression>, <Symbol:$ParameterList>
class callEpxressionSyntax0 extends SyntaxAcceptor {
	static final callEpxressionSyntax0	Instance	= new callEpxressionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("callEpxressionSyntax0 : " + NodeSize);
		int Index = 0;
		Object[] List = new Object[NodeSize];
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, new KonohaToken("$MethodCall"));
		UNode.SetAtNode(0, (UntypedNode) Parser.Get(Index, NodeSize));
		Index = Index + 1;
		KonohaArray Params = (KonohaArray) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		for(int i = 0; i < Params.size(); i++) {
			UntypedNode Node = (UntypedNode) Params.get(i);
			UNode.SetAtNode(i + 2, Node);
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, UNode);
		}

		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$primary>, <Repeat:<Symbol:$selector>>
class memberExpressionSyntax0 extends SyntaxAcceptor {
	static final memberExpressionSyntax0	Instance	= new memberExpressionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("memberExpressionSyntax0 : " + NodeSize);
		int Index = 0;
		UntypedNode Left = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		while(Index < NodeSize) {
			UntypedNode Right = (UntypedNode) Parser.Get(Index, NodeSize);
			Right.SetAtNode(0, Left);
			Left = Right;
			Index = Index + 1;
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, Left);
		}
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"this">
class primarySyntax0 extends SyntaxAcceptor {
	static final primarySyntax0	Instance	= new primarySyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("primarySyntax0 : " + NodeSize);
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		Parser.Push(UNode);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$identifier>
class primarySyntax1 extends SyntaxAcceptor {
	static final primarySyntax1	Instance	= new primarySyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("primarySyntax1 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$literal>
class primarySyntax2 extends SyntaxAcceptor {
	static final primarySyntax2	Instance	= new primarySyntax2();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("primarySyntax2 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"(">, <Symbol:$expression>, <Symbol:")">
class primarySyntax3 extends SyntaxAcceptor {
	static final primarySyntax3	Instance	= new primarySyntax3();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("primarySyntax3 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"[">, <Symbol:$expression>, <Symbol:"]">
class selectorSyntax0 extends SyntaxAcceptor {
	static final selectorSyntax0	Instance	= new selectorSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("selectorSyntax0 : " + NodeSize);
		int Index = 0;
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, new KonohaToken("[]"));
		UNode.SetAtNode(1, (UntypedNode) Parser.Get(Index, NodeSize));
		Index = Index + 1;
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, UNode);
		}
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:".">, <Symbol:$identifier>
class selectorSyntax1 extends SyntaxAcceptor {
	static final selectorSyntax1	Instance	= new selectorSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("selectorSyntax1 : " + NodeSize);
		int Index = 0;
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, new KonohaToken("."));
		UNode.SetAtNode(0, (UntypedNode) Parser.Get(Index, NodeSize));
		Index = Index + 1;
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, UNode);
		}

		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$memberExpression>
class newExpressionSyntax0 extends SyntaxAcceptor {
	static final newExpressionSyntax0	Instance	= new newExpressionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("newExpressionSyntax0 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"new">, <Symbol:$type>, <Symbol:$ParameterList>
class newExpressionSyntax1 extends SyntaxAcceptor {
	static final newExpressionSyntax1	Instance	= new newExpressionSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("newExpressionSyntax1 : " + NodeSize);
		int Index = 0;
		UntypedNode UNode = new UntypedNode(Parser.NameSpace, TokenList.get(BeginIdx));
		Object[] List = new Object[NodeSize];
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		List[Index] = Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UNode.SetAtNode(0, (UntypedNode) List[0]);
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, List[0]);
		}
		throw new RuntimeException();
		//return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$logicalAndExpression>, <Repeat:<Group:<Symbol:"||"> <Symbol:$logicalAndExpression> >>
class logicalOrExpressionSyntax0 extends SyntaxAcceptor {
	static final logicalOrExpressionSyntax0	Instance	= new logicalOrExpressionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("logicalOrExpressionSyntax0 : " + NodeSize);
		KonohaToken OperatorToken = new KonohaToken("||");
		SyntaxAcceptor.CreateBinaryOperator(Parser, NodeSize, OperatorToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$relationExpression>, <Repeat:<Group:<Symbol:"&&"> <Symbol:$relationExpression> >>
class logicalAndExpressionSyntax0 extends SyntaxAcceptor {
	static final logicalAndExpressionSyntax0	Instance	= new logicalAndExpressionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("logicalAndExpressionSyntax0 : " + NodeSize);
		KonohaToken OperatorToken = new KonohaToken("&&");
		SyntaxAcceptor.CreateBinaryOperator(Parser, NodeSize, OperatorToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

//action: <Symbol:$additiveExpression>, <Symbol:$relationOperator>, <Symbol:$additiveExpression>
class relationExpressionSyntax0 extends SyntaxAcceptor {
	static final relationExpressionSyntax0	Instance	= new relationExpressionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("relationExpressionSyntax0 : " + NodeSize);
		SyntaxAcceptor.CreateBinaryOperator(Parser, NodeSize, null);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$additiveExpression>
class relationExpressionSyntax1 extends SyntaxAcceptor {
	static final relationExpressionSyntax1	Instance	= new relationExpressionSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("relationExpressionSyntax1 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

//action: <Symbol:"==">
class relationOperatorSyntax0 extends SyntaxAcceptor {
	static final relationOperatorSyntax0	Instance	= new relationOperatorSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("relationOperatorSyntax0 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

//action: <Symbol:"!=">
class relationOperatorSyntax1 extends SyntaxAcceptor {
	static final relationOperatorSyntax1	Instance	= new relationOperatorSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("relationOperatorSyntax1 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

//action: <Symbol:">=">
class relationOperatorSyntax2 extends SyntaxAcceptor {
	static final relationOperatorSyntax2	Instance	= new relationOperatorSyntax2();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("relationOperatorSyntax2 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

//action: <Symbol:">">
class relationOperatorSyntax3 extends SyntaxAcceptor {
	static final relationOperatorSyntax3	Instance	= new relationOperatorSyntax3();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("relationOperatorSyntax3 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

//action: <Symbol:"<=">
class relationOperatorSyntax4 extends SyntaxAcceptor {
	static final relationOperatorSyntax4	Instance	= new relationOperatorSyntax4();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("relationOperatorSyntax4 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

//action: <Symbol:"<">
class relationOperatorSyntax5 extends SyntaxAcceptor {
	static final relationOperatorSyntax5	Instance	= new relationOperatorSyntax5();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("relationOperatorSyntax5 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"<<">
class shiftOperatorSyntax0 extends SyntaxAcceptor {
	static final shiftOperatorSyntax0	Instance	= new shiftOperatorSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("shiftOperatorSyntax0 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:">>">
class shiftOperatorSyntax1 extends SyntaxAcceptor {
	static final shiftOperatorSyntax1	Instance	= new shiftOperatorSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("shiftOperatorSyntax1 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"+">
class additiveOperatorSyntax0 extends SyntaxAcceptor {
	static final additiveOperatorSyntax0	Instance	= new additiveOperatorSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("additiveOperatorSyntax0 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"-">
class additiveOperatorSyntax1 extends SyntaxAcceptor {
	static final additiveOperatorSyntax1	Instance	= new additiveOperatorSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("additiveOperatorSyntax1 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"*">
class multiplicativeOperatorSyntax0 extends SyntaxAcceptor {
	static final multiplicativeOperatorSyntax0	Instance	= new multiplicativeOperatorSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("multiplicativeOperatorSyntax0 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"/">
class multiplicativeOperatorSyntax1 extends SyntaxAcceptor {
	static final multiplicativeOperatorSyntax1	Instance	= new multiplicativeOperatorSyntax1();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("multiplicativeOperatorSyntax1 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"%">
class multiplicativeOperatorSyntax2 extends SyntaxAcceptor {
	static final multiplicativeOperatorSyntax2	Instance	= new multiplicativeOperatorSyntax2();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("multiplicativeOperatorSyntax2 : " + NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$multiplicativeExpression>, <Repeat:<Group:<Symbol:$additiveOperator> <Symbol:$multiplicativeExpression> >>
class additiveExpressionSyntax0 extends SyntaxAcceptor {
	static final additiveExpressionSyntax0	Instance	= new additiveExpressionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("additiveExpressionSyntax0 : " + NodeSize);
		SyntaxAcceptor.CreateBinaryOperator(Parser, NodeSize, null);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$unaryExpression>, <Repeat:<Group:<Symbol:$multiplicativeOperator> <Symbol:$unaryExpression> >>
class multiplicativeExpressionSyntax0 extends SyntaxAcceptor {
	static final multiplicativeExpressionSyntax0	Instance	= new multiplicativeExpressionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("multiplicativeExpressionSyntax0 : " + NodeSize);
		SyntaxAcceptor.CreateBinaryOperator(Parser, NodeSize, null);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$leftHandSideExpression>
class unaryExpressionSyntax0 extends SyntaxAcceptor {
	static final unaryExpressionSyntax0	Instance	= new unaryExpressionSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("unaryExpressionSyntax0 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$Symbol>
class identifierSyntax0 extends SyntaxAcceptor {
	static final identifierSyntax0	Instance	= new identifierSyntax0();

	@Override
	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		System.out.println("identifierSyntax0 : " + NodeSize);
		return EndIdx;
	}

	@Override
	TypedNode TypeCheck(SyntaxModule Parser, TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}
