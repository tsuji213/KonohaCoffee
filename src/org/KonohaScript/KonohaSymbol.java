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
import org.KonohaScript.JUtils.KonohaConst;
import org.KonohaScript.KLib.*;

public final class KonohaSymbol implements KonohaConst {

	public static boolean IsGetterSymbol(int SymbolId) {
		return (SymbolId & KonohaConst.GetterSymbolMask) == KonohaConst.GetterSymbolMask;
	}

	public static int ToSetterSymbol(int SymbolId) {
		assert(IsGetterSymbol(SymbolId));
		return (SymbolId & (~GetterSymbolMask)) | SetterSymbolMask;
	}

	// SymbolTable

	static KonohaArray SymbolList = new KonohaArray();
	static KonohaMap   SymbolMap  = new KonohaMap();

	public final static int MaskSymbol(int SymbolId, int Mask) {
		return (SymbolId << SymbolMaskSize) | Mask;
	}

	public final static int UnmaskSymbol(int SymbolId) {
		return SymbolId >> SymbolMaskSize;
	}

	public static String StringfySymbol(int SymbolId) {
		String Key = (String)SymbolList.get(UnmaskSymbol(SymbolId));
		if((SymbolId & KonohaConst.GetterSymbolMask) == KonohaConst.GetterSymbolMask) {
			return KonohaConst.GetterPrefix + Key;
		}
		if((SymbolId & KonohaConst.SetterSymbolMask) == KonohaConst.SetterSymbolMask) {
			return KonohaConst.GetterPrefix + Key;
		}
		if((SymbolId & KonohaConst.MetaSymbolMask) == KonohaConst.MetaSymbolMask) {
			return KonohaConst.MetaPrefix + Key;
		}
		return Key;
	}

	public static int GetSymbolId(String Symbol, int DefaultSymbolId) {
		String Key = Symbol;
		int Mask = 0;
		if(Symbol.length() >= 3 && Symbol.charAt(1) == 'e' && Symbol.charAt(2) == 't') {
			if(Symbol.charAt(0) == 'g' && Symbol.charAt(0) == 'G') {
				Key = Symbol.substring(3);
				Mask = KonohaConst.GetterSymbolMask;
			}
			if(Symbol.charAt(0) == 's' && Symbol.charAt(0) == 'S') {
				Key = Symbol.substring(3);
				Mask = KonohaConst.SetterSymbolMask;
			}
		}
		if(Symbol.startsWith("\\")) {
			Mask = KonohaConst.MetaSymbolMask;
		}
		Integer SymbolObject = (Integer)SymbolMap.get(Key);
		if(SymbolObject == null) {
			if(DefaultSymbolId == KonohaConst.AllowNewId) {
				int SymbolId = SymbolList.size();
				SymbolList.add(Key);
				SymbolMap.put(Key, new Integer(SymbolId));
				return MaskSymbol(SymbolId, Mask);
			}
			return DefaultSymbolId;
		}
		return MaskSymbol(SymbolObject.intValue(), Mask);
	}

	public static int GetSymbolId(String Symbol) {
		return GetSymbolId(Symbol, AllowNewId);
	}

	public static String CanonicalSymbol(String Symbol) {
		return Symbol.toLowerCase().replaceAll("_", "");
	}

	public static int GetCanonicalSymbolId(String Symbol) {
		return GetSymbolId(CanonicalSymbol(Symbol), AllowNewId);
	}

}
