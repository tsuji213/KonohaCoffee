package org.KonohaScript.Parser;

import org.KonohaScript.KonohaToken;
import org.KonohaScript.KLib.KonohaArray;
import org.KonohaScript.KLib.TokenList;

class Parser {
	int	Cursor;
	int	ThunkPos;

	public boolean Match(String SyntaxName, TokenList TokenList) {
		return false;
	}

	public boolean MatchToken(String SyntaxName, TokenList TokenList, int Index) {
		return false;
	}

	BaseSyntax	RootSyntax;

	public void SetRootSyntax(BaseSyntax Syntax) {
		this.RootSyntax = Syntax;
	}

	public void parse(TokenList TokenList) {
		this.RootSyntax.Match(this, TokenList);
	}
}

class BaseSyntax {
	int Fail(String SyntaxName, Parser Parser) {
		return -1;
	}

	int Match(Parser Parser, TokenList TokenList) {
		return -1;
	}

	void Report(String Message) {
		System.out.println(Message);
	}
}

public class SyntaxModule {
	KonohaArray	EntryPoints;
	KonohaArray	SyntaxTable;

	public SyntaxModule() {
		this.EntryPoints = new KonohaArray();
		this.SyntaxTable = new KonohaArray();
	}

	int	Cursor;
	int	ThunkPos;

	public boolean Match(String SyntaxName, TokenList TokenList) {
		return false;
	}

	public boolean MatchToken(String SyntaxName, TokenList TokenList, int Index) {
		return false;
	}

	BaseSyntax	RootSyntax;

	public void SetRootSyntax(BaseSyntax Syntax) {
		this.RootSyntax = Syntax;
	}

	public void parse(TokenList TokenList) {
		this.RootSyntax.Match(this, TokenList);
	}

	boolean AlreadyRegistered(Syntax Syntax) {
		for (int i = 0; i < this.SyntaxTable.size(); i++) {
			Syntax RegistedSyntax = (Syntax) this.SyntaxTable.get(i);
			if (Syntax == RegistedSyntax) {
				return true;
			}
		}
		return false;
	}

	void AddSyntax(Syntax Syntax, boolean TopLevelSyntax) {
		this.AddSyntax(null, Syntax, TopLevelSyntax);
	}

	void AddSyntax(Syntax ParentSyntax, Syntax Syntax, boolean TopLevelSyntax) {
		if (TopLevelSyntax) {
			this.EntryPoints.add(Syntax);
		}
		if (!this.AlreadyRegistered(Syntax)) {
			this.SyntaxTable.add(Syntax);
		}
	}

	void Freeze() {
	}

	void ComputePriority(KonohaToken Token, int[] priority) {
		for (int i = 0; i < priority.length; i++) {
			priority[i] = i;
		}
		if (Token.ResolvedSyntax != null) {
			//String HintInfo = Token.ResolvedSyntax.SyntaxName;
		}
	}

	public int Match(TokenList TokenList, int BeginIdx, int EndIdx) {
		assert (BeginIdx >= 0 && BeginIdx <= EndIdx);
		int Priority[] = new int[this.EntryPoints.size()];
		KonohaToken BeginToken = TokenList.get(BeginIdx);
		this.ComputePriority(BeginToken, Priority);
		return 0;
	}

	public static void main(String[] args) {
		SyntaxModule module = new SyntaxModule();
		Syntax addSyntax = new AdditiveExpression();
		Syntax mulSyntax = new MultiplicativeExpression();
		Syntax unarySyntax = new UnaryExpression();
		Syntax primeSyntax = new PrimaryExpression();
		Syntax literalSyntax = new Literal();
		Syntax intLiteralSyntax = new IntLiteral();
		Syntax exprSyntax = new Expression();

		module.AddSyntax(exprSyntax, true);
		module.AddSyntax(exprSyntax, primeSyntax, false);

		module.AddSyntax(primeSyntax, literalSyntax, false);
		module.AddSyntax(primeSyntax, addSyntax, false);
		module.AddSyntax(primeSyntax, exprSyntax, false);

		module.AddSyntax(literalSyntax, intLiteralSyntax, false);

		module.AddSyntax(addSyntax, addSyntax, false);
		module.AddSyntax(addSyntax, mulSyntax, false);

		module.AddSyntax(mulSyntax, mulSyntax, false);
		module.AddSyntax(mulSyntax, unarySyntax, false);

		module.AddSyntax(unarySyntax, primeSyntax, false);

		module.Freeze();
	}

}

abstract class Syntax {
	String		Name;
	KonohaArray	Childrens;

	public Syntax(String Name) {
		this.Name = Name;
		this.Childrens = null;
	}

	public int Match(KonohaToken TokenList, int BeginIdx, int EndIdx) {
		return 0;
	}
}

class Expression extends Syntax {
	// $PrimaryExpression
	public Expression() {
		super("Expression");
	}
}

class PrimaryExpression extends Syntax {
	// $Literal
	// $AdditiveExpression
	// "(" $Expression ")"
	public PrimaryExpression() {
		super("PrimaryExpression");
	}
}

class Literal extends Syntax {
	// "true"
	// "false"
	// $IntLiteral
	public Literal() {
		super("Literal");
	}
}

class IntLiteral extends Syntax {
	// INT_LITERAL
	public IntLiteral() {
		super("IntLiteral");
	}
}

class AdditiveExpression extends Syntax {
	// $MultiplicativeExpression + $AdditiveExpression
	public AdditiveExpression() {
		super("AdditiveExpression");
	}
}

class MultiplicativeExpression extends Syntax {
	// $UnaryExpression * $MultiplicativeExpr
	public MultiplicativeExpression() {
		super("MultiplicativeExpression");
	}
}

class UnaryExpression extends Syntax {
	// $PrimaryExpression
	public UnaryExpression() {
		super("UnaryExpression");
	}
}
