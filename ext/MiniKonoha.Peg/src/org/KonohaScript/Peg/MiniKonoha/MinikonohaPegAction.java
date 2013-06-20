package org.KonohaScript.Peg.MiniKonoha;

import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaParam;
import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.Parser.KonohaToken;
import org.KonohaScript.Parser.TypeEnv;
import org.KonohaScript.Parser.UntypedNode;
import org.KonohaScript.PegParser.KonohaCallExpressionTypeChecker;
import org.KonohaScript.PegParser.SyntaxAcceptor;
import org.KonohaScript.PegParser.SyntaxModule;
import org.KonohaScript.SyntaxTree.AndNode;
import org.KonohaScript.SyntaxTree.ApplyNode;
import org.KonohaScript.SyntaxTree.AssignNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.DefineNode;
import org.KonohaScript.SyntaxTree.ErrorNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.JumpNode;
import org.KonohaScript.SyntaxTree.LetNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.NewNode;
import org.KonohaScript.SyntaxTree.NullNode;
import org.KonohaScript.SyntaxTree.OrNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.TypedNode;

// action: <Repeat:<Symbol:$TopLevelDefinition>>
class SourceCodeSyntax0 extends SyntaxAcceptor {
	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {

		this.Report("SourceCodeSyntax0", NodeSize);
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

		Parser.ReAssign(NodeSize, List.get(0));
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$statement>
class TopLevelDefinitionSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("TopLevelDefinitionSyntax0", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$functionDefinition>
class TopLevelDefinitionSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("TopLevelDefinitionSyntax1", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$type>, <Symbol:$identifier>, <Symbol:$ParamDeclList>
class functionSignatureSyntax0 extends SyntaxAcceptor {

	static final int	FunctionSignatureOffset	= SyntaxAcceptor.ListOffset;
	static final int	MethodReturnTypeOffset	= FunctionSignatureOffset;
	static final int	MethodClassOffset		= FunctionSignatureOffset + 1;
	static final int	MethodNameOffset		= FunctionSignatureOffset + 2;
	static final int	MethodParamOffset		= FunctionSignatureOffset + 3;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("functionSignatureSyntax0", NodeSize);
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$functionSignature");
		int Index = 0;
		UNode.SetAtToken(MethodReturnTypeOffset, (KonohaToken) Parser.Get(Index, NodeSize)); // ReturnType
		UNode.SetAtNode(MethodClassOffset, null); // Receiver
		Index = Index + 1;
		UntypedNode MethodName = (UntypedNode) Parser.Get(Index, NodeSize);
		UNode.SetAtToken(MethodNameOffset, MethodName.KeyToken); // MethodName
		Index = Index + 1;

		KonohaArray ParamList = (KonohaArray) Parser.Get(Index, NodeSize);
		for(int i = 0; i < ParamList.size(); i++) {
			UNode.SetAtToken(MethodParamOffset + i, (KonohaToken) ParamList.get(i));
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, UNode);
		}
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		System.err.println("@@@@@ " + UNode);
		KonohaType BaseType = UNode.GetTokenType(MethodClassOffset, null);
		if(BaseType == null) {
			BaseType = Gamma.GammaNameSpace.GetGlobalObject().TypeInfo;
		}
		String MethodName = UNode.GetTokenString(MethodNameOffset, "new"/* FIXME */);
		int ParamSize = UNode.NodeList.size() - (MethodParamOffset + 1);
		KonohaType[] ParamData = new KonohaType[ParamSize + 1];
		String[] ArgNames = new String[ParamSize];
		ParamData[0] = UNode.GetTokenType(MethodClassOffset, Gamma.VarType);
		for(int i = 0; i < ParamSize; i = i + 2) {
			KonohaType ParamType = UNode.GetTokenType(MethodParamOffset + i, Gamma.VarType);
			String ParamName = UNode.GetTokenString(MethodParamOffset + i + 1, "");
			ParamData[i + 1] = ParamType;
			ArgNames[i] = ParamName;
		}
		KonohaParam Param = new KonohaParam(ParamSize + 1, ParamData, ArgNames);
		KonohaMethod NewMethod = new KonohaMethod(0, BaseType, MethodName, Param, Gamma.GammaNameSpace, null);
		BaseType.DefineNewMethod(NewMethod);
		return new DefineNode(TypeInfo, UNode.KeyToken, NewMethod);
	}
}

// action: <Symbol:$block>
class functionBodySyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("functionBodySyntax0", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$functionSignature>, <Symbol:$functionBody>
class functionDefinitionSyntax0 extends SyntaxAcceptor {
	static final int	FunctionSignatureOffset	= SyntaxAcceptor.ListOffset;
	static final int	FunctionBodyOffset		= SyntaxAcceptor.ListOffset + 1;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("functionDefinitionSyntax0", NodeSize);
		int Index = 0;
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$functionDefinition");
		UNode.SetAtNode(FunctionSignatureOffset, (UntypedNode) Parser.Get(Index, NodeSize));
		Index = Index + 1;
		KonohaArray Body = (KonohaArray) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		if(Body.size() > 0) {
			UNode.SetAtNode(FunctionBodyOffset, (UntypedNode) Body.get(0));
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, UNode);
		}
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode FuncNode = UNode.TypeNodeAt(FunctionSignatureOffset, Gamma, TypeInfo, 0);
		if(FuncNode instanceof DefineNode) {
			DefineNode Def = (DefineNode) FuncNode;
			KonohaMethod Method = (KonohaMethod) Def.DefInfo;
			UntypedNode Body = UNode.GetAtNode(FunctionBodyOffset);
			Method.ParsedTree = Body;
			//FIXME(ide) TypeCheck for FunctionBody is called when this method are called first time.
			Method.DoCompilation();
			return FuncNode;
		}
		return null;
	}
}

// action: <Symbol:$functionSignature>, <Symbol:";">
class functionDefinitionSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("functionDefinitionSyntax1", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"(">, <Symbol:")">
class ParamDeclListSyntax0 extends SyntaxAcceptor {
	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("ParamDeclListSyntax0", NodeSize);
		Parser.Push(new KonohaArray());
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"(">, <Symbol:$ParamDecls>, <Symbol:")">
class ParamDeclListSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("ParamDeclListSyntax1", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$ParamDecl>, <Repeat:<Group:<Symbol:","> <Symbol:$ParamDecl> >>
class ParamDeclsSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("ParamDeclsSyntax0", NodeSize);
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
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/*do nothing*/
		return null;
	}
}

// action: <Symbol:$type>, <Symbol:$identifier>
class ParamDeclSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("ParamDeclSyntax0", NodeSize);
		KonohaArray List = new KonohaArray();
		int Index = 0;
		KonohaToken TypeDecl = (KonohaToken) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UntypedNode SymbolDecl = (UntypedNode) Parser.Get(Index, NodeSize);
		List.add(TypeDecl);
		List.add(SymbolDecl.KeyToken);
		Parser.ReAssign(NodeSize, List);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"(">, <Symbol:")">
class ParameterListSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("ParameterListSyntax0", NodeSize);
		Parser.Push(new KonohaArray());
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"(">, <Symbol:$Parameters>, <Symbol:")">
class ParameterListSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("ParameterListSyntax1", NodeSize);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$Parameter>, <Repeat:<Group:<Symbol:","> <Symbol:$Parameter> >>
class ParametersSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("ParametersSyntax0", NodeSize);
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
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$expression>
class ParameterSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("ParameterSyntax0", NodeSize);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"null">
class literalSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("literalSyntax0", NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(this.CreateNodeWithSyntax(Parser, KeyToken, "$literal"));
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return new NullNode(TypeInfo);
	}
}

// action: <Symbol:"true">
class literalSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("literalSyntax1", NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(this.CreateNodeWithSyntax(Parser, KeyToken, "$literal"));
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaToken Token = UNode.KeyToken;
		return new ConstNode(Gamma.BooleanType, Token, Boolean.valueOf(Token.ParsedText));
	}
}

// action: <Symbol:"false">
class literalSyntax2 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("literalSyntax2", NodeSize);
		KonohaToken KeyToken = TokenList.get(BeginIdx);
		Parser.Push(this.CreateNodeWithSyntax(Parser, KeyToken, "$literal"));
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaToken Token = UNode.KeyToken;
		return new ConstNode(Gamma.BooleanType, Token, Boolean.valueOf(Token.ParsedText));
	}
}

// action: <Symbol:$intLiteral>
class literalSyntax3 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("literalSyntax3", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$stringLiteral>
class literalSyntax4 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("literalSyntax4", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$Type>
class typeSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("typeSyntax0", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$Type>, <Repeat:<Group:<Symbol:"["> <Symbol:"]"> >>
class typeSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("typeSyntax1", NodeSize);
		int Index = 0;
		KonohaToken BaseTypeToken = (KonohaToken) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		int i = NodeSize;
		while(NodeSize > 1) {
			KonohaToken TypeToken = (KonohaToken) Parser.Get(Index, NodeSize);
			//FIXME Create BaseTypeToken[TypeToken] class
			//BaseTypeToken = ;
			i = i - 1;
		}
		Parser.ReAssign(NodeSize, BaseTypeToken);

		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$block>
class statementSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("statementSyntax0", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$variableDeclaration>
class statementSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("statementSyntax1", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$expressionStatement>
class statementSyntax2 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("statementSyntax2", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$ifStatement>
class statementSyntax3 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("statementSyntax3", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$whileStatement>
class statementSyntax4 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("statementSyntax4", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$breakStatement>
class statementSyntax5 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("statementSyntax5", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$continueStatement>
class statementSyntax6 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("statementSyntax6", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$returnStatement>
class statementSyntax7 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("statementSyntax7", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$identifier>
class variableSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("variableSyntax0", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"=">
class EQSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("EQSyntax0", NodeSize);
		KonohaToken KeyToken = new KonohaToken("=", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$type>, <Symbol:$identifier>, <Symbol:";">
class variableDeclarationSyntax0 extends SyntaxAcceptor {
	static int	VarDeclTypeOffset	= SyntaxAcceptor.ListOffset;
	static int	VarDeclNameOffset	= VarDeclTypeOffset + 1;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("variableDeclarationSyntax0", NodeSize);
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$variableDeclaration");
		int Index = 0;
		KonohaToken VarType = (KonohaToken) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		KonohaToken VarName = (KonohaToken) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UNode.SetAtToken(Index, VarType);
		UNode.SetAtToken(Index, VarName);
		UNode.SetAtToken(Index, null);
		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaType VarType = UNode.GetTokenType(VarDeclTypeOffset, null);
		KonohaToken VarToken = UNode.GetAtToken(VarDeclNameOffset);
		String VarName = UNode.GetTokenString(VarDeclNameOffset, null);
		if(VarType.equals(Gamma.VarType)) {
			return new ErrorNode(TypeInfo, VarToken, "cannot infer variable type");
		}
		assert (VarName != null);
		return new LetNode(VarType, VarToken, null, null);
	}
}

// action: <Symbol:$type>, <Symbol:$variable>, <Symbol:$EQ>, <Symbol:$expression>, <Symbol:";">
class variableDeclarationSyntax1 extends SyntaxAcceptor {
	static int	VarDeclTypeOffset	= SyntaxAcceptor.ListOffset;
	static int	VarDeclNameOffset	= VarDeclTypeOffset + 1;
	static int	VarDeclExprOffset	= VarDeclNameOffset + 1;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("variableDeclarationSyntax1", NodeSize);
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$variableDeclaration");
		int Index = 0;
		KonohaToken VarType = (KonohaToken) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		KonohaToken VarName = (KonohaToken) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		KonohaToken VarExpr = (KonohaToken) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UNode.SetAtToken(Index, VarType);
		UNode.SetAtToken(Index, VarName);
		UNode.SetAtToken(Index, VarExpr);

		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		// FIXME (ide)
		KonohaType VarType = UNode.GetTokenType(VarDeclTypeOffset, null);
		KonohaToken VarToken = UNode.GetAtToken(VarDeclNameOffset);
		String VarName = UNode.GetTokenString(VarDeclNameOffset, null);

		TypedNode Expr = UNode.TypeNodeAt(VarDeclExprOffset, Gamma, TypeInfo, 0);
		if(VarType.equals(Gamma.VarType)) {
			VarType = Expr.TypeInfo;
		}
		assert (VarName != null);
		return new LetNode(VarType, VarToken, Expr, null);
	}
}

// action: <Repeat:<Symbol:$statement>>
class statementsSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("statementsSyntax0", NodeSize);
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
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"{">, <Symbol:$statements>, <Symbol:"}">
class blockSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("blockSyntax0", NodeSize);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

//action: <Symbol:"if">, <Symbol:"(">, <Symbol:$expression>, <Symbol:")">, <Symbol:$block>, <Symbol:"else">, <Symbol:$block>
class ifStatementSyntax0 extends SyntaxAcceptor {
	static int	IfConditionOffset	= ListOffset;
	static int	IfThenBlockOffset	= IfConditionOffset + 1;
	static int	IfElseBlockOffset	= IfConditionOffset + 2;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("ifStatementSyntax0", NodeSize);
		int Index = 0;
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$ifStatement");
		UNode.SetAtNode(IfConditionOffset, (UntypedNode) Parser.Get(Index, NodeSize));
		Index = Index + 1;
		KonohaArray ThenBlock = (KonohaArray) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		KonohaArray ElseBlock = (KonohaArray) Parser.Get(Index, NodeSize);
		Index = Index + 1;

		if(ThenBlock.size() > 0) {
			UNode.SetAtNode(IfThenBlockOffset, (UntypedNode) ThenBlock.get(0));
		}
		if(ElseBlock.size() > 0) {
			UNode.SetAtNode(IfElseBlockOffset, (UntypedNode) ElseBlock.get(0));
		}
		Parser.ReAssign(NodeSize, UNode);

		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return ifStatementSyntax0.TypeCheckIf(Gamma, UNode, TypeInfo);
	}

	static public TypedNode TypeCheckIf(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode CondNode = UNode.TypeNodeAt(IfConditionOffset, Gamma, Gamma.BooleanType, 0);
		if(CondNode.IsError()) {
			return CondNode;
		}
		TypedNode ThenNode = UNode.TypeNodeAt(IfThenBlockOffset, Gamma, TypeInfo, 0);
		if(ThenNode.IsError()) {
			return ThenNode;
		}
		TypedNode ElseNode = null;
		if(UNode.GetAtNode(IfElseBlockOffset) != null) {
			ElseNode = UNode.TypeNodeAt(IfElseBlockOffset, Gamma, ThenNode.TypeInfo, 0);
			if(ElseNode.IsError()) {
				return ElseNode;
			}
		}
		return new IfNode(ThenNode.TypeInfo, CondNode, ThenNode, ElseNode);
	}

}

// action: <Symbol:"if">, <Symbol:"(">, <Symbol:$expression>, <Symbol:")">, <Symbol:$block>
class ifStatementSyntax1 extends SyntaxAcceptor {
	static int	IfConditionOffset	= ListOffset;
	static int	IfThenBlockOffset	= IfConditionOffset + 1;
	static int	IfElseBlockOffset	= IfConditionOffset + 2;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("ifStatementSyntax1", NodeSize);
		int Index = 0;
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$ifStatement");
		UNode.SetAtNode(IfConditionOffset, (UntypedNode) Parser.Get(Index, NodeSize));
		Index = Index + 1;
		KonohaArray ThenBlock = (KonohaArray) Parser.Get(Index, NodeSize);
		Index = Index + 1;

		if(ThenBlock.size() > 0) {
			UNode.SetAtNode(IfThenBlockOffset, (UntypedNode) ThenBlock.get(0));
		}
		UNode.SetAtNode(IfElseBlockOffset, null);
		Parser.ReAssign(NodeSize, UNode);

		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return ifStatementSyntax0.TypeCheckIf(Gamma, UNode, TypeInfo);
	}
}

// action: <Symbol:"while">, <Symbol:"(">, <Symbol:$expression>, <Symbol:")">, <Symbol:$block>
class whileStatementSyntax0 extends SyntaxAcceptor {
	static int	WhileCondOffset	= ListOffset;
	static int	WhileBodyOffset	= WhileCondOffset + 1;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("whileStatementSyntax0", NodeSize);
		int Index = 0;
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$whileStatement");
		UntypedNode Cond = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UntypedNode Body = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UNode.SetAtNode(0, Cond);
		UNode.SetAtNode(1, Body);

		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode Cond = UNode.TypeNodeAt(WhileCondOffset, Gamma, TypeInfo, 0);
		// FIXME (ide) push "break" and "continue" label
		TypedNode Body = UNode.TypeNodeAt(WhileCondOffset, Gamma, TypeInfo, 0);
		// FIXME (ide) pop "break" and "continue" label
		return null;
	}
}

// action: <Symbol:"break">, <Symbol:";">
class breakStatementSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("breakStatementSyntax0", NodeSize);
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$breakStatement");
		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		// FIXME (ide) find "break" label from parent node to check this expression is valid or not
		return new JumpNode(TypeInfo, "break");
	}
}

// action: <Symbol:"continue">, <Symbol:";">
class continueStatementSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("continueStatementSyntax0", NodeSize);
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$continueStatement");
		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		// FIXME (ide) find "continue" label from parent node to check this expression is valid or not
		return new JumpNode(TypeInfo, "break");

	}
}

// action: <Symbol:"return">, <Symbol:";">
class returnStatementSyntax0 extends SyntaxAcceptor {

	static int	ReturnExpressionOffset	= ListOffset;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("returnStatementSyntax0", NodeSize);
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$returnStatement");
		UNode.SetAtNode(ReturnExpressionOffset, null);
		Parser.ReAssign(NodeSize, UNode);

		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return returnStatementSyntax1.TypeCheckReturn(Gamma, UNode, TypeInfo);
	}
}

// action: <Symbol:"return">, <Symbol:$expression>, <Symbol:";">
class returnStatementSyntax1 extends SyntaxAcceptor {
	static int	ReturnExpressionOffset	= ListOffset;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("returnStatementSyntax1", NodeSize);
		int Index = 0;
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$returnStatement");
		UNode.SetAtNode(ReturnExpressionOffset, (UntypedNode) Parser.Get(Index, NodeSize));
		Parser.ReAssign(NodeSize, UNode);

		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return returnStatementSyntax1.TypeCheckReturn(Gamma, UNode, TypeInfo);
	}

	static TypedNode TypeCheckReturn(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode Expr = UNode.TypeNodeAt(ReturnExpressionOffset, Gamma, Gamma.ReturnType, 0);
		if(Expr.IsError()) {
			return Expr;
		}
		return new ReturnNode(Expr.TypeInfo, Expr);
	}
}

// action: <Symbol:$expression>, <Symbol:";">
class expressionStatementSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("expressionStatementSyntax0", NodeSize);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$leftHandSideExpression>, <Symbol:$EQ>, <Symbol:$expression>
class expressionSyntax0 extends SyntaxAcceptor {
	static int	AssignmentLeftOffset	= ListOffset;
	static int	AssignmentExprOffset	= AssignmentLeftOffset + 1;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("expressionSyntax0", NodeSize);
		int Index = 0;
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$expression");

		UntypedNode Left = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UntypedNode Expr = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UNode.SetAtNode(AssignmentLeftOffset, Left);
		UNode.SetAtNode(AssignmentExprOffset, Expr);
		Parser.ReAssign(NodeSize, UNode);

		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode LeftNode = UNode.TypeNodeAt(AssignmentLeftOffset, Gamma, TypeInfo, 0);
		TypedNode RightNode = UNode.TypeNodeAt(AssignmentExprOffset, Gamma, TypeInfo, 0);
		if(LeftNode.TypeInfo.equals(RightNode.TypeInfo)) {
			TypedNode TNode = new AssignNode(TypeInfo, UNode.KeyToken, LeftNode, RightNode);
			return TNode;
		}
		return new ErrorNode(TypeInfo, UNode.KeyToken, "TypeError"); //FIXME
	}
}

// action: <Symbol:$logicalOrExpression>
class expressionSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("expressionSyntax1", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$callExpression>
class leftHandSideExpressionSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("leftHandSideExpressionSyntax0", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$newExpression>
class leftHandSideExpressionSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("leftHandSideExpressionSyntax1", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$memberExpression>, <Symbol:$ParameterList>
class callExpressionSyntax0 extends SyntaxAcceptor {
	static final int	CallExpressionOffset	= KonohaCallExpressionTypeChecker.CallExpressionOffset;
	static final int	CallParameterOffset		= KonohaCallExpressionTypeChecker.CallParameterOffset;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("callExpressionSyntax0", NodeSize);
		int Index = 0;
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, new KonohaToken("$MethodCall"), "$callExpression");
		UNode.SetAtNode(CallExpressionOffset, (UntypedNode) Parser.Get(Index, NodeSize));
		Index = Index + 1;
		KonohaArray Params = (KonohaArray) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		for(int i = 0; i < Params.size(); i++) {
			UntypedNode Node = (UntypedNode) Params.get(i);
			UNode.SetAtNode(CallParameterOffset + i, Node);
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, UNode);
		}

		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return KonohaCallExpressionTypeChecker.TypeCheckMethodCall(Gamma, UNode, TypeInfo);
	}
}

// action: <Symbol:$primary>, <Repeat:<Symbol:$selector>>
class memberExpressionSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("memberExpressionSyntax0", NodeSize);
		int Index = 0;
		UntypedNode Left = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		// (E0 E1 E2 E3) => (E3 (E2 (E1 E0)))
		while(Index < NodeSize) {
			UntypedNode Right = (UntypedNode) Parser.Get(Index, NodeSize);
			Right.AddParsedNode(Left);
			Left = Right;
			Index = Index + 1;
		}
		Parser.ReAssign(NodeSize, Left);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"this">
class primarySyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("primarySyntax0", NodeSize);
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$primary");
		Parser.Push(UNode);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return new LocalNode(Gamma.ThisType, UNode.KeyToken, "this");
	}
}

//action: <Symbol:$literal>
class primarySyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("primarySyntax1", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$identifier>
class primarySyntax2 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("primarySyntax2", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"(">, <Symbol:$expression>, <Symbol:")">
class primarySyntax3 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("primarySyntax3", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"[">, <Symbol:$expression>, <Symbol:"]">
class selectorSyntax0 extends SyntaxAcceptor {
	static int	ArrayIndexExprOffset	= ListOffset;
	static int	ArrayBaseExprOffset		= ArrayIndexExprOffset + 1;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("selectorSyntax0", NodeSize);
		int Index = 0;
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, new KonohaToken("$ArrayAccessor"), "$selector");
		UNode.SetAtNode(ArrayIndexExprOffset, (UntypedNode) Parser.Get(Index, NodeSize));
		Index = Index + 1;
		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode BaseExpr = UNode.TypeNodeAt(ArrayBaseExprOffset, Gamma, TypeInfo, 0);
		TypedNode IndexExpr = UNode.TypeNodeAt(ArrayIndexExprOffset, Gamma, Gamma.IntType, 0);
		if(BaseExpr.IsError()) {
			return BaseExpr;
		}
		if(IndexExpr.IsError()) {
			return IndexExpr;
		}
		//FIXME need check BaseExpr.Typeinfo is subclass of Array
		KonohaMethod Method = BaseExpr.TypeInfo.LookupMethod("get", 1);
		ApplyNode TNode = new ApplyNode(TypeInfo, UNode.KeyToken, Method);
		TNode.Append(BaseExpr);
		TNode.Append(IndexExpr);
		return TNode;
	}
}

// action: <Symbol:".">, <Symbol:$identifier>
class selectorSyntax1 extends SyntaxAcceptor {
	static int	MemberIndexExprOffset	= ListOffset;
	static int	MemberBaseExprOffset	= MemberIndexExprOffset + 1;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("selectorSyntax1", NodeSize);
		int Index = 0;
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, new KonohaToken("$Selector"), "$selector");
		UNode.SetAtNode(MemberIndexExprOffset, (UntypedNode) Parser.Get(Index, NodeSize));
		Index = Index + 1;
		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode BaseExpr = UNode.TypeNodeAt(MemberBaseExprOffset, Gamma, TypeInfo, 0);
		TypedNode IndexExpr = UNode.TypeNodeAt(MemberIndexExprOffset, Gamma, Gamma.IntType, 0);
		if(BaseExpr.IsError()) {
			return BaseExpr;
		}
		if(IndexExpr.IsError()) {
			return IndexExpr;
		}
		//FIXME need check BaseExpr.Typeinfo is subclass of Array
		KonohaMethod Method = BaseExpr.TypeInfo.LookupMethod("get", 1);
		ApplyNode TNode = new ApplyNode(TypeInfo, UNode.KeyToken, Method);
		TNode.Append(BaseExpr);
		TNode.Append(IndexExpr);
		return TNode;
	}
}

// action: <Symbol:$memberExpression>
class newExpressionSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("newExpressionSyntax0", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"new">, <Symbol:$type>, <Symbol:$ParameterList>
class newExpressionSyntax1 extends SyntaxAcceptor {
	static int	NewTypeOffset	= ListOffset;
	static int	NewParamOffset	= KonohaCallExpressionTypeChecker.CallParameterOffset;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("newExpressionSyntax1", NodeSize);
		int Index = 0;
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$newExpression");
		KonohaToken TypeToken = (KonohaToken) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		KonohaArray ParamList = (KonohaArray) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UNode.SetAtToken(NewTypeOffset, TypeToken);
		for(int i = 0; i < ParamList.size(); i++) {
			UntypedNode Param = (UntypedNode) ParamList.get(i);
			UNode.SetAtNode(NewParamOffset + i, Param);
		}
		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		KonohaType BaseType = UNode.GetTokenType(NewTypeOffset, null);
		NewNode Node = new NewNode(BaseType);
		int ParamSize = UNode.NodeList.size() - NewParamOffset;
		KonohaMethod Method = BaseType.LookupMethod("new", ParamSize);
		ApplyNode CallNode = new ApplyNode(TypeInfo, UNode.KeyToken, Method);
		CallNode.Append(Node);
		return KonohaCallExpressionTypeChecker.TypeMethodEachParam(Gamma, BaseType, CallNode, UNode.NodeList, ParamSize);
	}
}

// action: <Symbol:$logicalAndExpression>, <Repeat:<Group:<Symbol:"||"> <Symbol:$logicalAndExpression> >>
class logicalOrExpressionSyntax0 extends SyntaxAcceptor {
	static int	OrLeftExprOffset	= ListOffset;
	static int	OrRightExprOffset	= OrLeftExprOffset + 1;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("logicalOrExpressionSyntax0", NodeSize);
		KonohaToken OperatorToken = new KonohaToken("||");
		int Index = 0;
		UntypedNode Left = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		while(Index < NodeSize) {
			UntypedNode Node = this.CreateNodeWithSyntax(Parser, OperatorToken, "$logicalOrExpression");
			UntypedNode Right = (UntypedNode) Parser.Get(Index, NodeSize);
			Node.SetAtNode(0, Left);
			Node.SetAtNode(1, Right);
			Left = Node;
			Index = Index + 1;
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, Left);
		}
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode LeftNode = UNode.TypeNodeAt(OrLeftExprOffset, Gamma, Gamma.BooleanType, 0);
		if(LeftNode.IsError())
			return LeftNode;
		TypedNode RightNode = UNode.TypeNodeAt(OrRightExprOffset, Gamma, Gamma.BooleanType, 0);
		if(RightNode.IsError())
			return RightNode;

		return new OrNode(RightNode.TypeInfo, UNode.KeyToken, LeftNode, RightNode);
	}
}

// action: <Symbol:$relationExpression>, <Repeat:<Group:<Symbol:"&&"> <Symbol:$relationExpression> >>
class logicalAndExpressionSyntax0 extends SyntaxAcceptor {
	static int	AndLeftExprOffset	= ListOffset;
	static int	AndRightExprOffset	= AndLeftExprOffset + 1;

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("logicalAndExpressionSyntax0", NodeSize);
		KonohaToken OperatorToken = new KonohaToken("&&");
		int Index = 0;
		UntypedNode Left = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		while(Index < NodeSize) {
			UntypedNode Node = this.CreateNodeWithSyntax(Parser, OperatorToken, "$logicalOrExpression");
			UntypedNode Right = (UntypedNode) Parser.Get(Index, NodeSize);
			Node.SetAtNode(0, Left);
			Node.SetAtNode(1, Right);
			Left = Node;
			Index = Index + 1;
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, Left);
		}
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		TypedNode LeftNode = UNode.TypeNodeAt(AndLeftExprOffset, Gamma, Gamma.BooleanType, 0);
		if(LeftNode.IsError())
			return LeftNode;
		TypedNode RightNode = UNode.TypeNodeAt(AndRightExprOffset, Gamma, Gamma.BooleanType, 0);
		if(RightNode.IsError())
			return RightNode;

		return new AndNode(RightNode.TypeInfo, UNode.KeyToken, LeftNode, RightNode);

	}
}

//action: <Symbol:$additiveExpression>, <Symbol:$relationOperator>, <Symbol:$additiveExpression>
class relationExpressionSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("relationExpressionSyntax0", NodeSize);
		this.CreateBinaryOperator(Parser, NodeSize, "$relationExpression");
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return this.TypeBinaryOperator(Gamma, UNode, TypeInfo);
	}
}

// action: <Symbol:$additiveExpression>
class relationExpressionSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("relationExpressionSyntax1", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return this.TypeBinaryOperator(Gamma, UNode, TypeInfo);
	}
}

//action: <Symbol:"==">
class relationOperatorSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("relationOperatorSyntax0", NodeSize);
		KonohaToken KeyToken = new KonohaToken("==", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

//action: <Symbol:"!=">
class relationOperatorSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("relationOperatorSyntax1", NodeSize);
		KonohaToken KeyToken = new KonohaToken("!=", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

//action: <Symbol:">=">
class relationOperatorSyntax2 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("relationOperatorSyntax2", NodeSize);
		KonohaToken KeyToken = new KonohaToken(">=", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

//action: <Symbol:">">
class relationOperatorSyntax3 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("relationOperatorSyntax3", NodeSize);
		KonohaToken KeyToken = new KonohaToken(">", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

//action: <Symbol:"<=">
class relationOperatorSyntax4 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("relationOperatorSyntax4", NodeSize);
		KonohaToken KeyToken = new KonohaToken("<=", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

//action: <Symbol:"<">
class relationOperatorSyntax5 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("relationOperatorSyntax5", NodeSize);
		KonohaToken KeyToken = new KonohaToken("<", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"<<">
class shiftOperatorSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("shiftOperatorSyntax0", NodeSize);
		KonohaToken KeyToken = new KonohaToken("<<", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:">>">
class shiftOperatorSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("shiftOperatorSyntax1", NodeSize);
		KonohaToken KeyToken = new KonohaToken(">>", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"+">
class additiveOperatorSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("additiveOperatorSyntax0", NodeSize);
		KonohaToken KeyToken = new KonohaToken("+", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"-">
class additiveOperatorSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("additiveOperatorSyntax1", NodeSize);
		KonohaToken KeyToken = new KonohaToken("-", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"*">
class multiplicativeOperatorSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("multiplicativeOperatorSyntax0", NodeSize);
		KonohaToken KeyToken = new KonohaToken("*", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"/">
class multiplicativeOperatorSyntax1 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("multiplicativeOperatorSyntax1", NodeSize);
		KonohaToken KeyToken = new KonohaToken("/", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:"%">
class multiplicativeOperatorSyntax2 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("multiplicativeOperatorSyntax2", NodeSize);
		KonohaToken KeyToken = new KonohaToken("%", TokenList.get(BeginIdx).uline);
		Parser.Push(KeyToken);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$multiplicativeExpression>, <Repeat:<Group:<Symbol:$additiveOperator> <Symbol:$multiplicativeExpression> >>
class additiveExpressionSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("additiveExpressionSyntax0", NodeSize);
		this.CreateBinaryOperator(Parser, NodeSize, "$additiveExpression");
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return this.TypeBinaryOperator(Gamma, UNode, TypeInfo);
	}
}

// action: <Symbol:$unaryExpression>, <Repeat:<Group:<Symbol:$multiplicativeOperator> <Symbol:$unaryExpression> >>
class multiplicativeExpressionSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("multiplicativeExpressionSyntax0", NodeSize);
		this.CreateBinaryOperator(Parser, NodeSize, "$multiplicativeExpression");
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return this.TypeBinaryOperator(Gamma, UNode, TypeInfo);
	}
}

// action: <Symbol:$leftHandSideExpression>
class unaryExpressionSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("unaryExpressionSyntax0", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}

// action: <Symbol:$Symbol>
class identifierSyntax0 extends SyntaxAcceptor {

	@Override
	public int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		this.Report("identifierSyntax0", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}
