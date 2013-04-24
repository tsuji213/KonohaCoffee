package org.KonohaScript.CodeGen;

import java.util.ArrayList;

public class SwitchNode extends TypedNode {
	/* switch CondExpr {
	 *  Label[0]:
	 *    Blocks[0];
	 *  Label[1]:
	 *    Blocks[2];
	 *  ...
	 * }
	 */
	TypedNode CondExpr;
	ArrayList<TypedNode> Labels;
	ArrayList<TypedNode> Blocks;
}
