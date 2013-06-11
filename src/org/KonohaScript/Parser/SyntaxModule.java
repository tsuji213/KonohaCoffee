package org.KonohaScript.Parser;

import org.KonohaScript.KonohaGrammar;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaSyntax;
import org.KonohaScript.KonohaToken;
import org.KonohaScript.KonohaType;
import org.KonohaScript.TypeEnv;
import org.KonohaScript.UntypedNode;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.KLib.KonohaMap;
import org.KonohaScript.KLib.TokenList;
import org.KonohaScript.SyntaxTree.TypedNode;

class SyntaxModule extends KonohaGrammar {
	int				Cursor;
	int				ThunkPos;
	int				EndIdx;

	KonohaArray		EntryPoints;
	KonohaMap		SyntaxTable;
	SyntaxTemplate	RootSyntax;

	KonohaArray		ThunkObjects;
	KonohaArray		UNodeStack;
	KonohaArray		ThunkRangeBegins;
	KonohaArray		ThunkRangeEnd;
	KonohaArray		ThunkNodeSizes;
	KonohaNameSpace	NameSpace;

	public SyntaxModule(KonohaNameSpace NS) {
		super();
		this.EntryPoints = new KonohaArray();
		this.SyntaxTable = new KonohaMap();
		this.ThunkObjects = new KonohaArray();
		this.ThunkRangeBegins = new KonohaArray();
		this.ThunkRangeEnd = new KonohaArray();
		this.ThunkNodeSizes = new KonohaArray();
		this.UNodeStack = new KonohaArray();
		this.NameSpace = NS;
	}

	public int Match(String SyntaxName, TokenList TokenList) {
		SyntaxTemplate Syn = (SyntaxTemplate) this.SyntaxTable.get(SyntaxName);
		if(Syn != null) {
			return Syn.Match(this, TokenList);
		}
		return -1;
	}

	public int MatchToken(String SyntaxName, TokenList TokenList, int Index) {
		if(Index < this.EndIdx) {
			KonohaToken Token = TokenList.get(Index);
			KonohaSyntax Syntax = Token.ResolvedSyntax;
			//System.out.println("Token:" + Token.ParsedText + "// Expected:" + SyntaxName);
			if(SyntaxName.equals(Syntax.SyntaxName)) {
				this.Cursor = this.Cursor + 1;
				return this.Cursor;
			}
			if(Syntax.SyntaxName.equals("$Symbol") && Token.ParsedText.equals(SyntaxName)) {
				this.Cursor = this.Cursor + 1;
				return this.Cursor;
			}
		}
		return -1;
	}

	public void SetRootSyntax(SyntaxTemplate Syntax) {
		this.RootSyntax = Syntax;
		this.AddSyntax(this.RootSyntax, true);
	}

	int MatchSyntax(TokenList TokenList, int BeginIdx, int EndIdx) {
		assert (BeginIdx >= 0 && BeginIdx <= EndIdx);
		this.Cursor = BeginIdx;
		this.EndIdx = EndIdx;
		int Pos = this.RootSyntax.Match(this, TokenList);
		System.out.println("BeginIdx=" + BeginIdx + "CurrentIdx=" + Pos + ", EndIdx=" + this.EndIdx);
		return this.ConstructSyntaxTree(TokenList, BeginIdx, EndIdx);
	}

	int ConstructSyntaxTree(TokenList TokenList, int BeginIdx, int EndIdx) {
		int Pos = BeginIdx;
		for(int i = 0; i < this.ThunkPos; i++) {
			SyntaxAcceptor Action = (SyntaxAcceptor) this.ThunkObjects.get(i);
			int Begin = ((Integer) this.ThunkRangeBegins.get(i)).intValue();
			int End = ((Integer) this.ThunkRangeEnd.get(i)).intValue();
			int NodeSize = ((Integer) this.ThunkNodeSizes.get(i)).intValue();
			Pos = Action.Parse(this, TokenList, Begin, End, NodeSize);
		}
		return Pos;
	}

	public UntypedNode Parse(TokenList TokenList, int BeginIdx, int EndIdx) {
		this.EndIdx = this.MatchSyntax(TokenList, BeginIdx, EndIdx);
		UntypedNode ret = null;
		if(this.UNodeStack.size() > 0) {
			ret = (UntypedNode) this.Pop();
		}
		return ret;
	}

	public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}

	boolean AlreadyRegistered(SyntaxTemplate Syntax) {
		return this.SyntaxTable.get(Syntax.Name) != null;
	}

	void AddSyntax(SyntaxTemplate Syntax, boolean TopLevelSyntax) {
		this.AddSyntax(null, Syntax, TopLevelSyntax);
	}

	void AddSyntax(SyntaxTemplate ParentSyntax, SyntaxTemplate Syntax, boolean TopLevelSyntax) {
		if(TopLevelSyntax) {
			this.EntryPoints.add(Syntax);
		}
		if(!this.AlreadyRegistered(Syntax)) {
			this.SyntaxTable.put(Syntax.Name, Syntax);
			this.NameSpace.DefineSyntax(Syntax.Name, 0, Syntax, "UNUSED", "SyntaxModule");
			Syntax.Init(this);

		}
	}

	void Freeze() {
	}

	public TokenList Filter(TokenList List, String SyntaxName) {
		TokenList newList = new TokenList();
		for(int i = 0; i < List.size(); i++) {
			KonohaToken token = List.get(i);
			if(!token.ResolvedSyntax.SyntaxName.equals(SyntaxName)) {
				newList.add(token);
			}
		}
		return newList;
	}

	public void DumpSyntax() {
		String[] keys = this.SyntaxTable.keys();
		for(int i = 0; i < this.SyntaxTable.size(); i++) {
			SyntaxTemplate syntax = (SyntaxTemplate) this.SyntaxTable.get(keys[i]);
			System.out.println(syntax.Name);
		}
	}

	public void PushThunk(SyntaxAcceptor Action, int BeginIdx, int NodeSize) {
		int thunkpos = this.ThunkPos;
		KonohaArray Actions = this.ThunkObjects;
		KonohaArray BeginIndexes = this.ThunkRangeBegins;
		KonohaArray EndIndexes = this.ThunkRangeEnd;
		KonohaArray NodeIndexes = this.ThunkNodeSizes;
		while(BeginIndexes.size() <= thunkpos) {
			Actions.add(null);
			BeginIndexes.add(0);
			EndIndexes.add(0);
			NodeIndexes.add(0);
		}
		Actions.set(thunkpos, Action);
		BeginIndexes.set(thunkpos, BeginIdx);
		EndIndexes.set(thunkpos, this.Cursor);
		NodeIndexes.set(thunkpos, NodeSize);

		this.ThunkPos = thunkpos + 1;
	}

	public void Push(Object UNode) {
		this.UNodeStack.add(UNode);
	}

	public Object Pop() {
		assert (this.UNodeStack.size() > 0);
		return this.UNodeStack.remove(this.UNodeStack.size() - 1);
	}

	public Object Get(int Index, int NodeSize) {
		return this.UNodeStack.get(this.UNodeStack.size() - NodeSize + Index);
	}

	public void ReAssign(int NodeSize, Object Value) {
		while(NodeSize > 0) {
			this.Pop();
			NodeSize = NodeSize - 1;
		}
		this.Push(Value);
	}

	//	void ComputePriority(KonohaToken Token, int[] priority) {
	//		for(int i = 0; i < priority.length; i++) {
	//			priority[i] = i;
	//		}
	//		if(Token.ResolvedSyntax != null) {
	//			//String HintInfo = Token.ResolvedSyntax.SyntaxName;
	//		}
	//	}

}

abstract class SyntaxAcceptor {
	public static final int	AcceptorOffset	= 0;
	public static final int	ListOffset		= AcceptorOffset + 1;

	UntypedNode CreateNode(SyntaxModule Parser, KonohaToken Token) {
		UntypedNode Node = new UntypedNode(Parser.NameSpace, Token);
		Node.SetAtNode(AcceptorOffset, null);
		Node.NodeList.set(AcceptorOffset, this);
		return Node;
	}

	static void CreateBinaryOperator(SyntaxModule Parser, int NodeSize, KonohaToken DefaultOperatorToken) {
		int Index = 0;
		UntypedNode Left = (UntypedNode) Parser.Get(Index, NodeSize);
		Index = Index + 1;
		while(Index < NodeSize) {
			UntypedNode Node = null;
			if(DefaultOperatorToken != null) {
				Node = new UntypedNode(Parser.NameSpace, DefaultOperatorToken);
			} else {
				KonohaToken OperatorToken = (KonohaToken) Parser.Get(Index, NodeSize);
				Node = new UntypedNode(Parser.NameSpace, OperatorToken);
				Index = Index + 1;
			}
			UntypedNode Right = (UntypedNode) Parser.Get(Index, NodeSize);
			Node.SetAtNode(0, Left);
			Node.SetAtNode(1, Right);
			Left = Node;
			Index = Index + 1;
		}
		if(NodeSize > 0) {
			Parser.ReAssign(NodeSize, Left);
		}
	}

	int Parse(SyntaxModule Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {
		return -1;
	}

	TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {
		return null;
	}
}
