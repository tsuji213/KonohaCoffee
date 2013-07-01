package org.KonohaScript.Grammar;
import java.nio.ByteBuffer;

public class Pointer {
	final int Index;
	int Offset;
	final MemoryBlock MemoryBlockRef;
	final byte[] ByteArrayRef;

	public Pointer(MemoryBlock MemoryBlockRef, int Offset) {
		this.Index = MemoryBlockRef.BeginIndex;
		this.Offset = Offset;
		this.MemoryBlockRef = MemoryBlockRef;
		this.ByteArrayRef = MemoryBlockRef.ByteArrayRef;
	}

	private void SetByteBuf(ByteBuffer Buf) {
		Buf.get(ByteArrayRef, Index + Offset + 4, Buf.capacity());
	}

	private ByteBuffer GetByteBuf(int Size) {
		return ByteBuffer.wrap(ByteArrayRef, Index + Offset + 4, Size);
	}

	public byte GetByteValue() {
		return ByteArrayRef[Index + Offset + 4];
	}

	public void SetByteValue(byte Value) {
		ByteArrayRef[Index + Offset + 4] = Value;
	}

	public char GetCharValue() {
		return GetByteBuf(2).getChar();
	}

	public void SetCharValue(char Value) {
		ByteBuffer buf = ByteBuffer.allocate(2);
		buf.putChar(Value);
		buf.flip();
		SetByteBuf(buf);
	}

	public short GetShortValue() {
		return GetByteBuf(2).getShort();
	}

	public void SetShortValue(short Value) {
		ByteBuffer buf = ByteBuffer.allocate(2);
		buf.putShort(Value);
		buf.flip();
		SetByteBuf(buf);
	}

	public int GetIntValue() {
		return GetByteBuf(4).getInt();
	}

	public void SetIntValue(int Value) {
		ByteBuffer buf = ByteBuffer.allocate(4);
		buf.putInt(Value);
		buf.flip();
		SetByteBuf(buf);
	}

	public long GetLongValue() {
		return GetByteBuf(8).getLong();
	}

	public void SetLongValue(long Value) {
		ByteBuffer buf = ByteBuffer.allocate(8);
		buf.putLong(Value);
		buf.flip();
		SetByteBuf(buf);
	}

	public float GetFloatValue() {
		return GetByteBuf(4).getFloat();
	}

	public void SetFloatValue(float Value) {
		ByteBuffer buf = ByteBuffer.allocate(4);
		buf.putFloat(Value);
		buf.flip();
		SetByteBuf(buf);
	}

	public double GetDoubleValue() {
		return GetByteBuf(8).getDouble();
	}

	public void SetDoubleValue(double Value) {
		ByteBuffer buf = ByteBuffer.allocate(8);
		buf.putDouble(Value);
		buf.flip();
		SetByteBuf(buf);
	}

	public Pointer GetPointerValue() {
		int BeginIndex = this.GetIntValue();
		int Offset = this.Add(4).GetIntValue();
		Memory MemoryRef = this.MemoryBlockRef.MemoryRef;
		byte[] ByteArrayRef = BeginIndex < 0 ? MemoryRef.Stack : MemoryRef.Heap;
		BeginIndex = BeginIndex < 0 ? -BeginIndex : BeginIndex;
		int Size = ByteBuffer.wrap(ByteArrayRef, BeginIndex, 4).getInt();
		MemoryBlock block = new MemoryBlock(MemoryRef, this.ByteArrayRef, BeginIndex, Size);

		Pointer p = new Pointer(block, Offset);
		return p;
	}

	public void SetPointerValue(Pointer Value) {
		int index = Value.MemoryBlockRef.BeginIndex;
		if(Value.ByteArrayRef == Value.MemoryBlockRef.MemoryRef.Stack){
			index = -index;
		}
		this.SetIntValue(index);
		this.Add(4).SetIntValue(Value.Offset);
	}

	public Pointer Add(int n) {
		return new Pointer(this.MemoryBlockRef, this.Offset + n);
	}

	public Pointer Sub(int n) {
		return this.Add(-n);
	}

	public int Sub(Pointer p) {
		if(this.ByteArrayRef != p.ByteArrayRef){
			throw new IllegalArgumentException();
		}
		return this.Index - p.Index;
	}

	public void Free(){
		//MemoryRef.Free(this);
	}
}
