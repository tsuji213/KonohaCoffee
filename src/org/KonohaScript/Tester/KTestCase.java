package org.KonohaScript.Tester;

interface TestAssert {
	public String GetTestName();

	/**
	 * Constructor.
	 */
	public void Init();

	/**
	 * Destructor
	 */
	public void Exit();

	/**
	 * Main
	 */
	public void Test();

}

public abstract class KTestCase implements TestAssert {
	String	TestName;

	public KTestCase() {
		String ClassName = this.getClass().getSimpleName();
		//System.out.println(ClassName);
		this.TestName = ClassName;
	}

	protected void Execute() {
		new KTestRunnerBase().Run(this);
	}

	@Override
	public String GetTestName() {
		return this.TestName;
	}

	private void Check(boolean Actual, boolean Expected) {
		if (Actual != Expected) {
			this.PrintErrorInfo(3);
		}
	}

	protected void Assert(boolean Cond) {
		this.AssertTrue(Cond);
	}

	protected void AssertTrue(boolean Cond) {
		this.Check(Cond, true);
	}

	protected void AssertFalse(boolean Cond) {
		this.Check(Cond, false);
	}

	protected void AssertEqual(boolean Actual, boolean Expected) {
		this.Check(Actual, Expected);
	}

	protected void AssertEqual(int Actual, int Expected) {
		this.Check(Actual == Expected, true);
	}

	protected void AssertEqual(float Actual, float Expected, float Delta) {
		this.Check(Actual - Expected <= Delta, true);
	}

	protected void AssertEqual(Object Actual, Object Expected) {
		this.Check(Actual.equals(Expected), true);
	}

	protected void Fail() {
		this.PrintErrorInfo(2);
	}

	private void PrintErrorInfo(int StackIndex) {
		StackTraceElement[] ste = (new Throwable()).getStackTrace();
		String File = ste[StackIndex].getFileName();
		int Line = ste[StackIndex].getLineNumber();
		String ClassName = ste[StackIndex].getClassName();
		String MethodName = ste[StackIndex].getMethodName();

		System.out.println("Test Fail at " + ClassName + "." + MethodName + "(" + File + ":" + Line + ")");

	}
}
