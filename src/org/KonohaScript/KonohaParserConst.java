
package org.KonohaScript;

public interface KonohaParserConst {
	
	public final static int GetterSymbol = 1;
	public final static int SetterSymbol = 2;
	public final static int MetaSymbol   = 3;
	
	public final static int AllowNewId = -1;
	public final static int NoMatch = -1;
	public final static int BreakPreProcess = -1;
	
	public final static int Error    = 0;
	public final static int Warning = 1;
	public final static int Info     = 2;
	
	public final static int BlockLevel = 0;
	public final static int StatementLevel = 1;
	public final static int ExpressionLevel = 2;

}
