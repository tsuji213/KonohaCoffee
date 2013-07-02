package org.KonohaScript.SyntaxTree;

interface INodeVisitor {
	boolean VisitList(TypedNode NodeList);

	boolean VisitDefine(DefineNode Node);

	boolean VisitConst(ConstNode Node);

	boolean VisitNew(NewNode Node);

	boolean VisitNull(NullNode Node);

	boolean VisitLocal(LocalNode Node);

	boolean VisitGetter(GetterNode Node);

	boolean VisitApply(ApplyNode Node);

	boolean VisitAnd(AndNode Node);

	boolean VisitOr(OrNode Node);

	boolean VisitAssign(AssignNode Node);

	boolean VisitLet(LetNode Node);

	boolean VisitIf(IfNode Node);

	boolean VisitSwitch(SwitchNode Node);

	boolean VisitLoop(LoopNode Node);

	boolean VisitReturn(ReturnNode Node);

	boolean VisitLabel(LabelNode Node);

	boolean VisitJump(JumpNode Node);

	boolean VisitTry(TryNode Node);

	boolean VisitThrow(ThrowNode Node);

	boolean VisitFunction(FunctionNode Node);

	boolean VisitError(ErrorNode Node);
}

public abstract class NodeVisitor implements INodeVisitor {
	@Override
	public boolean VisitList(TypedNode Node) {
		boolean Ret = true;
		while(Ret && Node != null) {
			Ret &= Node.Evaluate(this);
			Node = Node.NextNode;
		}
		return Ret;
	}
}