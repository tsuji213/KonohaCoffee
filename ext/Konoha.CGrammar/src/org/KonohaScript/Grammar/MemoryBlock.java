package org.KonohaScript.Grammar;
import java.nio.ByteBuffer;

public class MemoryBlock {
	public final Memory MemoryRef;
	public final int BeginIndex;
	public final int Size;
	public final byte[] ByteArrayRef;

	public MemoryBlock(Memory MemoryRef, byte[] ByteArrayRef, int BeginIndex, int Size){
		this.MemoryRef = MemoryRef;
		this.BeginIndex = BeginIndex;
		this.Size = Size;
		this.ByteArrayRef = ByteArrayRef;
		ByteBuffer.wrap(ByteArrayRef, BeginIndex, 4).putInt(Size);
	}

	public Pointer GetPointer(int Offset){
		return new Pointer(this, Offset);
	}
}
