package org.KonohaScript.SyntaxTree;

import java.util.ArrayList;

import org.KonohaScript.KClass;
import org.KonohaScript.KMethod;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class MethodCallNode extends TypedNode implements CallableNode {
	public KMethod Method;
	public ArrayList<TypedNode> Params; /* [this, arg1, arg2, ...] */

	/* call self.Method(arg1, arg2, ...) */
	public MethodCallNode(KClass TypeInfo, KToken KeyToken, KMethod Method) {
		super(TypeInfo, KeyToken);
		this.Method = Method;
		this.Params = new ArrayList<TypedNode>();
	}

	public MethodCallNode(KClass TypeInfo, KToken KeyToken, KMethod Method, TypedNode arg1) {
		super(TypeInfo);
		this.Method = Method;
		this.Params = new ArrayList<TypedNode>();
		this.Params.add(arg1);
	}

	public MethodCallNode(KClass TypeInfo, KToken KeyToken, KMethod Method, TypedNode arg1, TypedNode arg2) {
		super(TypeInfo);
		this.Method = Method;
		this.Params = new ArrayList<TypedNode>();
		this.Params.add(arg1);
		this.Params.add(arg2);
	}

	@Override
	public void Append(TypedNode Expr) {
		this.Params.add(Expr);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterMethodCall(this);
		for(TypedNode Node : this.Params) {
			Visitor.Visit(Node);
		}
		return Visitor.ExitMethodCall(this);
	}
}
