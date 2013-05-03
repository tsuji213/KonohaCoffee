
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

	public final static int TermRequired     = 0;
	public final static int AllowEmpty       = 1;
	public final static int CreateNullNode   = (1<<1);
	public final static int AllowSkip        = (1<<2);
	public final static int HasNextPattern   = (1<<3);

	public final static int Preliminary      = 0;
	public final static int TypeCheckPolicy_AllowEmpty = 1;
	
	//typedef enum {
//	TypeCheckPolicy_NoPolicy       = 0,
//	TypeCheckPolicy_NoCheck        = (1 << 0),
//	TypeCheckPolicy_AllowVoid      = (1 << 1),
//	TypeCheckPolicy_Coercion       = (1 << 2),
//	TypeCheckPolicy_AllowEmpty     = (1 << 3),
//	TypeCheckPolicy_CONST          = (1 << 4),  /* Reserved */
//	TypeCheckPolicy_Creation       = (1 << 6)   /* TypeCheckNodeByName */
//} TypeCheckPolicy;

}
