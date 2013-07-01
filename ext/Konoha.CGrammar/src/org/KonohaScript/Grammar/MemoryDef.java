package org.KonohaScript.Grammar;

import org.KonohaScript.KonohaDef;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaParam;
import org.KonohaScript.KonohaType;
import org.KonohaScript.JUtils.KonohaConst;

public class MemoryDef extends KonohaDef implements KonohaConst {

	@Override
	public void MakeDefinition(KonohaNameSpace ns) {
		KonohaType MemoryClass = ns.LookupHostLangType(Memory.class);
		KonohaType MemoryBlockClass = ns.LookupHostLangType(MemoryBlock.class);
		KonohaType PointerClass = ns.LookupHostLangType(Pointer.class);

		// define Constructor
		String MN_new = "New";
		KonohaParam Process_String_Param = KonohaParam.ParseOf(ns, "Memory");
		MemoryClass.DefineMethod(0, MN_new, Process_String_Param, this, MN_new);

		KonohaParam Pointer_Param = KonohaParam.ParseOf(ns, "void Pointer pointer");
		KonohaParam Pointer_int_Param = KonohaParam.ParseOf(ns, "Pointer int size");
		KonohaParam Pointer_int_int_Param = KonohaParam.ParseOf(ns, "Pointer int size int num");

		MemoryClass.DefineMethod(0, "AllocaHeap", Pointer_int_Param, this, "Memory_AllocaHeap_Size");
		MemoryClass.DefineMethod(0, "AllocaHeap", Pointer_int_int_Param, this, "Memory_AllocaHeap_SizeNum");
		MemoryClass.DefineMethod(0, "AllocaStack", Pointer_int_Param, this, "Memory_AllocaStack_Size");
		MemoryClass.DefineMethod(0, "AllocaStack", Pointer_int_int_Param, this, "Memory_AllocaStack_SizeNum");
		MemoryClass.DefineMethod(0, "Free", Pointer_Param, this, "Memory_Free");

		MemoryBlockClass.DefineMethod(0, "GetPointer", Pointer_int_Param, this, "MemoryBlock_GetPointer");

		PointerClass.DefineMethod(0, "GetCharValue", KonohaParam.ParseOf(ns, "int"), this, "Pointer_GetByteValue");
		PointerClass.DefineMethod(0, "SetCharValue", KonohaParam.ParseOf(ns, "void int Value"), this, "Pointer_SetByteValue");
		PointerClass.DefineMethod(0, "GetIntValue", KonohaParam.ParseOf(ns, "int"), this, "Pointer_GetIntValue");
		PointerClass.DefineMethod(0, "SetIntValue", KonohaParam.ParseOf(ns, "void int Value"), this, "Pointer_SetIntValue");
		PointerClass.DefineMethod(0, "GetPointerValue", KonohaParam.ParseOf(ns, "Pointer"), this, "Pointer_GetPointerValue");
		PointerClass.DefineMethod(0, "SetPointerValue", KonohaParam.ParseOf(ns, "void Pointer Value"), this, "Pointer_SetPointerValue");
	}

	public static Memory Memory_New() {
		return new Memory();
	}

	public static Pointer Memory_AllocaHeap_Size(Memory self, int Size) {
		return self.AllocaHeap(Size);
	}

	public static Pointer Memory_AllocaHeap_SizeNum(Memory self, int Size, int Num) {
		return self.AllocaHeap(Size, Num);
	}

	public static Pointer Memory_AllocaStack_Size(Memory self, int Size) {
		return self.AllocaStack(Size);
	}

	public static Pointer Memory_AllocaStack_SizeNum(Memory self, int Size, int Num) {
		return self.AllocaStack(Size, Num);
	}

	public void Memory_Free(Memory self, Pointer Ptr) {
		self.Free(Ptr);
	}

	public Pointer MemoryBlock_GetPointer(MemoryBlock self, int Offset){
		return self.GetPointer(Offset);
	}

	public int Pointer_GetByteValue(Pointer self){
		return (int)self.GetByteValue();
	}

	public void Pointer_SetByteValue(Pointer self, int Value){
		self.SetByteValue((byte)Value);
	}

	public int Pointer_GetShortValue(Pointer self){
		return self.GetShortValue();
	}

	public void Pointer_SetShortValue(Pointer self, int Value){
		self.SetShortValue((short)Value);
	}

	public int Pointer_GetIntValue(Pointer self){
		return self.GetIntValue();
	}

	public void Pointer_SetIntValue(Pointer self, int Value){
		self.SetIntValue(Value);
	}

	public long Pointer_GetLongValue(Pointer self){
		return self.GetLongValue();
	}

	public void Pointer_SetLongValue(Pointer self, long Value){
		self.SetLongValue(Value);
	}

	public double Pointer_GetFloatValue(Pointer self){
		return (double)self.GetFloatValue();
	}

	public void Pointer_SetFloatValue(Pointer self, double Value){
		self.SetFloatValue((float)Value);
	}
	
	public double Pointer_GetDoubleValue(Pointer self){
		return self.GetDoubleValue();
	}

	public void Pointer_SetDoubleValue(Pointer self, double Value){
		self.SetDoubleValue(Value);
	}
	
	public Pointer Pointer_GetPointerValue(Pointer self){
		return self.GetPointerValue();
	}

	public void Pointer_SetPointerValue(Pointer self, Pointer Value){
		self.SetPointerValue(Value);
	}
}
