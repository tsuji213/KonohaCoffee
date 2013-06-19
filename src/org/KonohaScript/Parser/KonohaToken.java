/****************************************************************************
 * Copyright (c) 2012, the Konoha project authors. All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ***************************************************************************/

package org.KonohaScript.Parser;

import org.KonohaScript.JUtils.KonohaDebug;
import org.KonohaScript.KLib.TokenList;

public final class KonohaToken {

	public long		uline;
	public String	ParsedText;

	public boolean EqualsText(String text) {
		return this.ParsedText.equals(text);
	}

	public KonohaToken(String text) {
		this.ParsedText = text;
	}

	public KonohaToken(String text, long uline) {
		this.ParsedText = text;
		this.uline = uline;
	}

	@Override
	public String toString() {
		return this.ParsedText + "@" + (int) this.uline;
	}

	final static int	ErrorTokenFlag	= 1;
	final static int	GroupTokenFlag	= (1 << 1);
	int					TokenFlag;

	public boolean IsErrorToken() {
		return ((this.TokenFlag & ErrorTokenFlag) == ErrorTokenFlag);
	}

	public boolean IsGroupToken() {
		return ((this.TokenFlag & GroupTokenFlag) == GroupTokenFlag);
	}

	public KonohaSyntax	ResolvedSyntax;
	public Object		ResolvedObject;

	public void SetGroup(KonohaSyntax Syntax, TokenList GroupList) {
		assert (Syntax != null);
		this.ResolvedSyntax = Syntax;
		this.ResolvedObject = GroupList;
		this.TokenFlag |= GroupTokenFlag;
	}

	public TokenList GetGroupList() {
		return (TokenList) this.ResolvedObject;
	}

	public String SetErrorMessage(String msg) {
		this.ResolvedSyntax = KonohaSyntax.ErrorSyntax;
		this.TokenFlag |= ErrorTokenFlag;
		this.ResolvedObject = msg;
		return msg;
	}

	public String GetErrorMessage() {
		return (String) this.ResolvedObject;
	}

	// Debug
	private final static String	Tab	= "  ";

	void Dump(int Level) {
		String Syntax = (this.ResolvedSyntax == null) ? "null" : this.ResolvedSyntax.SyntaxName;
		System.out.println("[" + Syntax + "+" + (int) this.uline + "] '" + this.ParsedText + "'");
		if(this.IsGroupToken()) {
			TokenList group = this.GetGroupList();
			DumpTokenList(Level + 1, null, group, 0, group.size());
		}
	}

	public static void DumpTokenList(int Level, String Message, TokenList TokenList, int BeginIdx, int EndIdx) {
		if(Message != null) {
			KonohaDebug.Indent(Level, Tab);
			System.out.println("Begin: " + Message);
			Level++;
		}
		for(int i = BeginIdx; i < EndIdx; i++) {
			KonohaToken Token = TokenList.get(i);
			KonohaDebug.Indent(Level, Tab);
			System.out.print("<" + i + "> ");
			Token.Dump(Level);
		}
		if(Message != null) {
			Level--;
			KonohaDebug.Indent(Level, Tab);
			System.out.println("End: " + Message);
		}
	}

	public static void DumpTokenList(TokenList TokenList) {
		DumpTokenList(0, null, TokenList, 0, TokenList.size());
	}

}
