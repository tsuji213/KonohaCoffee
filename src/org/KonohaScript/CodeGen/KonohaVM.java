package org.KonohaScript.CodeGen;

import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaMethod;
import org.KonohaScript.KonohaType;

/****************************************************************************
 * Copyright (c) 2012-2013, Masahiro Ide <ide@konohascript.org> All rights
 * reserved. Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. * Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ***************************************************************************/

enum VMState {
	Continue,
	Exit
}

class KonohaIR {
	BasicBlock	Parent;

	public KonohaIR(BasicBlock Parent) {
		this.Parent = Parent;
	}

	void dump() {
	}

	VMState exec(Object[] Registers, Object[] Stack) {
		return VMState.Exit;
	}

	public static void dump0(String OP) {
	}

	public static void dump1(String OP, String A1Ty, Object A1) {
	}

	public static void dump2(String OP, String A1Ty, Object A1, String A2Ty,
			Object A2) {
	}

	public static void dump3(String OP, String A1Ty, Object A1, String A2Ty,
			Object A2, String A3Ty, Object A3) {
	}

	public static void dump4(String OP, String A1Ty, Object A1, String A2Ty,
			Object A2, String A3Ty, Object A3, String A4Ty, Object A4) {
	}

	public BasicBlock GetParent() {
		return this.Parent;
	}
}

class LineInfo {
	int	FileSymbol;
	int	LineNumber;
}

class KTraceFunc {
	KTraceFunc() {
	}
}

public class KonohaVM {

	public KonohaVM() {
	}

	void Exec(KonohaIR[] Code) {
		Object[] Registers = new Object[10];
		Object[] Stack = new Object[10];
		for(int i = 0; i < Code.length; i++) {
			KonohaIR ir = Code[i];
			VMState state = ir.exec(Registers, Stack);
			if(state == VMState.Exit) {
				break;
			}
		}
	}
}

/* This file was automatically generated by GenLIR.k. Do not edit! */
/* This file was automatically generated by GenLIR.k. Do not edit! */
// #define OPCODE_LoadConst 0
class OPLoadConst extends KonohaIR {
	int			Dst;
	long		Value;
	KonohaType	TypeInfo;

	OPLoadConst(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump3(
			"LoadConst",
			"int",
			this.Dst,
			"long",
			this.Value,
			"KonohaType",
			this.TypeInfo);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		int Dst = this.Dst;
		long Value = this.Value;
		KonohaType TypeInfo = this.TypeInfo;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_Move 1
class OPMove extends KonohaIR {
	int			Dst;
	int			Src;
	KonohaType	TypeInfo;

	OPMove(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump3(
			"Move",
			"int",
			this.Dst,
			"int",
			this.Src,
			"KonohaType",
			this.TypeInfo);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		int Dst = this.Dst;
		int Src = this.Src;
		KonohaType TypeInfo = this.TypeInfo;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_LoadField 2
class OPLoadField extends KonohaIR {
	int			Dst;
	int			Src;
	int			Offset;
	KonohaType	TypeInfo;

	OPLoadField(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump4(
			"LoadField",
			"int",
			this.Dst,
			"int",
			this.Src,
			"int",
			this.Offset,
			"KonohaType",
			this.TypeInfo);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		int Dst = this.Dst;
		int Src = this.Src;
		int Offset = this.Offset;
		KonohaType TypeInfo = this.TypeInfo;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_StoreField 3
class OPStoreField extends KonohaIR {
	int			Dst;
	int			Offset;
	int			Src;
	KonohaType	TypeInfo;

	OPStoreField(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump4(
			"StoreField",
			"int",
			this.Dst,
			"int",
			this.Offset,
			"int",
			this.Src,
			"KonohaType",
			this.TypeInfo);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		int Dst = this.Dst;
		int Offset = this.Offset;
		int Src = this.Src;
		KonohaType TypeInfo = this.TypeInfo;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_New 4
class OPNew extends KonohaIR {
	int			Dst;
	KonohaType	TypeInfo;

	OPNew(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump2("New", "int", this.Dst, "KonohaType", this.TypeInfo);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		int Dst = this.Dst;
		KonohaType TypeInfo = this.TypeInfo;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_Null 5
class OPNull extends KonohaIR {
	int			Dst;
	KonohaType	TypeInfo;

	OPNull(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump2("Null", "int", this.Dst, "KonohaType", this.TypeInfo);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		int Dst = this.Dst;
		KonohaType TypeInfo = this.TypeInfo;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_Box 6
class OPBox extends KonohaIR {
	int			Dst;
	int			Src;
	KonohaType	TypeInfo;

	OPBox(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump3(
			"Box",
			"int",
			this.Dst,
			"int",
			this.Src,
			"KonohaType",
			this.TypeInfo);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		int Dst = this.Dst;
		int Src = this.Src;
		KonohaType TypeInfo = this.TypeInfo;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_Lookup 7
class OPLookup extends KonohaIR {
	int				Dst;
	KonohaNameSpace		NS;
	KonohaMethod	Mtd;
	KonohaType		ThisType;

	OPLookup(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump4(
			"Lookup",
			"int",
			this.Dst,
			"KNameSpace",
			this.NS,
			"KonohaMethod",
			this.Mtd,
			"KonohaType",
			this.ThisType);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		int Dst = this.Dst;
		KonohaNameSpace NS = this.NS;
		KonohaMethod Mtd = this.Mtd;
		KonohaType ThisType = this.ThisType;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_Call 8
class OPCall extends KonohaIR {
	int				Dst;
	KonohaNameSpace		NS;
	KonohaMethod	Mtd;
	KonohaType		ThisType;

	OPCall(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump4(
			"Call",
			"int",
			this.Dst,
			"KNameSpace",
			this.NS,
			"KonohaMethod",
			this.Mtd,
			"KonohaType",
			this.ThisType);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		int Dst = this.Dst;
		KonohaNameSpace NS = this.NS;
		KonohaMethod Mtd = this.Mtd;
		KonohaType ThisType = this.ThisType;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_Ret 9
class OPRet extends KonohaIR {
	OPRet(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump0("Ret");
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_BNot 10
class OPBNot extends KonohaIR {
	int	Dst;
	int	Src;

	OPBNot(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump2("BNot", "int", this.Dst, "int", this.Src);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		int Dst = this.Dst;
		int Src = this.Src;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_Jump 11
class OPJump extends KonohaIR {
	long	Address;

	OPJump(BasicBlock Parent, BasicBlock Traget) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump1("Jump", "long", this.Address);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		long Address = this.Address;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_JumpF 12
class OPJumpF extends KonohaIR {
	long	Address;
	int		Src;

	OPJumpF(BasicBlock Parent, BasicBlock Target, KonohaIR Src) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump2("JumpF", "long", this.Address, "int", this.Src);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		long Address = this.Address;
		int Src = this.Src;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_Try 13
class OPTry extends KonohaIR {
	long	Address;

	OPTry(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump1("Try", "long", this.Address);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		long Address = this.Address;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_Yield 14
class OPYield extends KonohaIR {
	OPYield(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump0("Yield");
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_Error 15
class OPError extends KonohaIR {
	String	ErrorMessage;

	OPError(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump1("Error", "ErrorMessage", this.ErrorMessage);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		String ErrorMessage = this.ErrorMessage;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_SafePoint 16
class OPSafePoint extends KonohaIR {
	LineInfo	linfo;
	int			StackTop;

	OPSafePoint(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump2(
			"SafePoint",
			"LineInfo",
			this.linfo,
			"int",
			this.StackTop);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		LineInfo linfo = this.linfo;
		int StackTop = this.StackTop;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_CheckStack 17
class OPCheckStack extends KonohaIR {
	LineInfo	linfo;

	OPCheckStack(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump1("CheckStack", "LineInfo", this.linfo);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		LineInfo linfo = this.linfo;
		/* do nothing */

		return VMState.Exit;
	}
}

// #define OPCODE_Trace 18
class OPTrace extends KonohaIR {
	LineInfo		linfo;
	int				StackTop;
	KonohaMethod	TraceFunc;

	OPTrace(BasicBlock Parent) {
		super(Parent);
	}

	@Override
	void dump() {
		KonohaIR.dump3(
			"Trace",
			"LineInfo",
			this.linfo,
			"int",
			this.StackTop,
			"KonohaMethod",
			this.TraceFunc);
	}

	@Override
	VMState exec(Object[] Regs, Object[] Stack) {
		LineInfo linfo = this.linfo;
		int StackTop = this.StackTop;
		KonohaMethod TraceFunc = this.TraceFunc;
		/* do nothing */

		return VMState.Exit;
	}
}
