package org.KonohaScript.Grammar;

public class Memory {
	public byte[]	Heap;
	public byte[]	Stack;
	public int		CurrentHeapPos;
	public int		CurrentStackPos;

	public Memory() {
		Heap = new byte[1024];
		Stack = new byte[1024];
	}

	Pointer AllocaHeap(int Size) {
		return AllocaHeap(1, Size);
	}

	Pointer AllocaHeap(int Size, int Num) {
		System.out.println("malloc("+Size*Num+");");
		int TotalSize = 4 + Size * Num;
		MemoryBlock block = new MemoryBlock(this, this.Heap, this.CurrentHeapPos, TotalSize);
		this.CurrentHeapPos += TotalSize;
		return block.GetPointer(0);
	}

	Pointer AllocaStack(int Size) {
		return AllocaHeap(1, Size);
	}

	Pointer AllocaStack(int Size, int Num) {
		int TotalSize = 4 + Size * Num;
		MemoryBlock block = new MemoryBlock(this, this.Stack, this.CurrentStackPos, TotalSize);
		this.CurrentStackPos += TotalSize;
		return block.GetPointer(0);
	}

	public void Free(Pointer Ptr) {
		// TODO
	}

}
