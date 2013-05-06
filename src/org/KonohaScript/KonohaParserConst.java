
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

	// Syntax
	public final static int Term = 1;	
	public final static int BinaryOperator = 1 << 1;
	public final static int SuffixOperator = 1 << 2;
	public final static int LeftJoin       = 1 << 3;
	public final static int PrecedenceShift = 4;
	public final static int Precedence_CStyleValue    =        (1  << PrecedenceShift);
	public final static int Precedence_CPPStyleScope  =        (50 << PrecedenceShift);
	public final static int Precedence_CStyleSuffixCall     = (100 << PrecedenceShift);  /*x(); x[]; x.x x->x x++ */
	public final static int Precedence_CStylePrefixOperator = (200 << PrecedenceShift);  /*++x; --x; sizeof x &x +x -x !x (T)x  */
//	Precedence_CppMember      = 300;  /* .x ->x */
	public final static int Precedence_CStyleMUL      = (400 << PrecedenceShift);  /* x * x; x / x; x % x*/
	public final static int Precedence_CStyleADD      = (500 << PrecedenceShift);  /* x + x; x - x */
	public final static int Precedence_CStyleSHIFT    = (600 << PrecedenceShift);  /* x << x; x >> x */
	public final static int Precedence_CStyleCOMPARE  = (700 << PrecedenceShift);
	public final static int Precedence_CStyleEquals   = (800 << PrecedenceShift);
	public final static int Precedence_CStyleBITAND   = (900 << PrecedenceShift);
	public final static int Precedence_CStyleBITXOR   = (1000 << PrecedenceShift);
	public final static int Precedence_CStyleBITOR    = (1100 << PrecedenceShift);
	public final static int Precedence_CStyleAND      = (1200 << PrecedenceShift);
	public final static int Precedence_CStyleOR       = (1300 << PrecedenceShift);
	public final static int Precedence_CStyleTRINARY  = (1400 << PrecedenceShift);  /* ? : */
	public final static int Precedence_CStyleAssign   = (1500 << PrecedenceShift);
	public final static int Precedence_CStyleCOMMA    = (1600 << PrecedenceShift);
	public final static int Precedence_Error          = (1700 << PrecedenceShift);
	public final static int Precedence_Statement      = (1900 << PrecedenceShift);
	public final static int Precedence_CStyleDelim    = (2000 << PrecedenceShift);

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
