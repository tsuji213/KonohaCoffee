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

package org.KonohaScript;
import java.util.ArrayList;

public class KToken {
	long uline;
	String Text;
//			kArray  *GroupTokenList;
//			kNode   *parsedNode;
	int tokenType;
	int symbol;
	KSyntax resolvedSyntaxInfo;
//		union {
//			ksymbol_t   tokenType;           // (resolvedSyntaxInfo == NULL)
////		ksymbol_t   symbol;      // symbol (resolvedSyntaxInfo != NULL)
//		};
//		union {
//			kuhalfword_t   indent;               // indent when kw == TokenType_Indent
//			kuhalfword_t   openCloseChar;
//		};
//		ksymbol_t   symbol;
//		union {
//			ktypeattr_t resolvedTypeId;      // typeid if KSymbol_TypePattern
//			ksymbol_t   ruleNameSymbol;      // pattern rule
//		};
	
	
	// Debug
	
	void Dump() {
		System.out.println("("+(int)uline+") '" + Text + "'");
	}
	
	static void DumpTokenList(ArrayList<KToken> list, int beginIdx, int endIdx) {
		for(int i = beginIdx; i < endIdx; i++) {
			KToken token = list.get(i);
			token.Dump();
		}
	}
	
	static void DumpTokenList(ArrayList<KToken> list) {
		DumpTokenList(list, 0, list.size());
	}
	
}

