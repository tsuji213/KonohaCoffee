package org.KonohaScript.Grammar;
import org.KonohaScript.Tester.KTestCase;

public class MemoryTest extends KTestCase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MemoryTest().Execute();
	}

	private Memory Memory;

	@Override
	public void Init() {
		this.Memory = new Memory();
	}

	@Override
	public void Exit() {
	}

	@Override
	public void Test() {
		Pointer intPtr = this.Memory.AllocaHeap(4);
		intPtr.SetIntValue(12345678);
		this.AssertEqual(intPtr.GetIntValue(), 12345678);

		Pointer doublePtr = this.Memory.AllocaHeap(8);
		doublePtr.SetDoubleValue(1.41421356);
		this.AssertEqual(doublePtr.GetDoubleValue(), 1.41421356);
		this.AssertEqual(doublePtr.GetLongValue(), 4609047870834485215l);

		Pointer pointerPtr = this.Memory.AllocaHeap(4);
		pointerPtr.SetPointerValue(intPtr);
		this.AssertEqual(pointerPtr.GetPointerValue().GetIntValue(), intPtr.GetIntValue());

		Pointer intArrayPtr = this.Memory.AllocaHeap(4, 10);
		for(int i = 0; i < 10; i++){
			intArrayPtr.Add(i*4).SetIntValue(i); // a[i] = i;
		}
		for(int i = 0; i < 10; i++){
			this.AssertEqual(intArrayPtr.Add(i*4).GetIntValue(), i);
		}
		
	}

}
