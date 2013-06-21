package org.KonohaScript.Peg.KonohaClass;

import org.KonohaScript.KonohaType;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.Parser.TypeEnv;
import org.KonohaScript.Parser.UntypedNode;
import org.KonohaScript.PegParser.PegParser;
import org.KonohaScript.PegParser.SyntaxAcceptor;
import org.KonohaScript.SyntaxTree.TypedNode;

// action: <Symbol:"class">, <Symbol:$#Symbol>, <Symbol:$#block>
class ClassDefinitionSyntax0 extends SyntaxAcceptor {
	static final int	ClassNameOffset			= ListOffset;
	static final int	ClassParentNameOffset	= ClassNameOffset + 1;
	static final int	ClassBlockOffset		= ClassNameOffset + 2;

	@Override
	public int Parse(PegParser Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		Report("ClassDefinitionSyntax0", NodeSize);
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$ClassDefinition");
		int Index = 0;
		UntypedNode ClassName = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		KonohaArray Block = (KonohaArray) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UNode.SetAtToken(ClassNameOffset, ClassName.KeyToken);
		UNode.SetAtNode(ClassParentNameOffset, null);
		if(Block.size() > 0) {
			UNode.SetAtNode(ClassBlockOffset, (UntypedNode) Block.get(0));
		} else {
			UNode.SetAtNode(ClassBlockOffset, null);
		}
		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:"class">, <Symbol:$#Symbol>, <Symbol:"extends">, <Symbol:$#type>
class ClassDefinitionSyntax1 extends SyntaxAcceptor {
	static final int	ClassNameOffset			= ListOffset;
	static final int	ClassParentNameOffset	= ClassNameOffset + 1;
	static final int	ClassBlockOffset		= ClassNameOffset + 2;

	@Override
	public int Parse(PegParser Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		Report("ClassDefinitionSyntax1", NodeSize);
		UntypedNode UNode = this.CreateNodeWithSyntax(Parser, TokenList.get(BeginIdx), "$ClassDefinitionSyntax");
		int Index = 0;
		UntypedNode ClassName = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UntypedNode SupClassName = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UntypedNode BlockNode = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		UNode.SetAtToken(ClassNameOffset, ClassName.KeyToken);
		UNode.SetAtToken(ClassParentNameOffset, SupClassName.KeyToken);
		UNode.SetAtNode(ClassBlockOffset, BlockNode);
		Parser.ReAssign(NodeSize, UNode);
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}

// action: <Symbol:$ClassDefinition>
class TopLevelDefinitionSyntax0 extends SyntaxAcceptor {
	@Override
	public int Parse(PegParser Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		Report("TopLevelDefinitionSyntax0", NodeSize);
		/* do nothing */
		return EndIdx;
	}

	@Override
	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		/* do nothing */
		return null;
	}
}
