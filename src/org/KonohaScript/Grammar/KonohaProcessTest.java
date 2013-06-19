package org.KonohaScript.Grammar;

import org.KonohaScript.Tester.KTestCase;

public class KonohaProcessTest extends KTestCase {

	@Override
	public void Init() {
	}

	@Override
	public void Exit() {
	}

	void testEchoWithNoArgument() {
		KonohaProcess Proc1 = new KonohaProcess("echo");
		Proc1.start();
		Proc1.waitFor();
		this.AssertEqual(Proc1.getStdout(), "");
		this.AssertEqual(Proc1.getStderr(), "");
	}

	void testSimpleEcho() {
		KonohaProcess Proc1 = new KonohaProcess("echo");
		String Args1[] = { "1234" };
		Proc1.setArgument(Args1);
		Proc1.start();
		Proc1.waitFor();
		this.AssertEqual(Proc1.getStdout(), "1234");
		this.AssertEqual(Proc1.getStderr(), "");
	}

	void testPipe() {
		KonohaProcess Proc1 = new KonohaProcess("echo");
		String Args1[] = { "1:2:3" };
		Proc1.setArgument(Args1);

		KonohaProcess Proc2 = new KonohaProcess("cut");
		String Args2[] = { "-d", ":", "-f", "2" };
		Proc1.setArgument(Args2);

		Proc1.pipe(Proc2);

		Proc1.start();
		Proc2.start();

		Proc2.waitFor();

		//this.AssertEqual(Proc1.getStdout(), "1:2:3");
		//this.AssertEqual(Proc1.getStderr(), "");

		this.AssertEqual(Proc2.getStdout(), "2");
		this.AssertEqual(Proc2.getStderr(), "");
	}

	@Override
	public void Test() {
		this.testSimpleEcho();
		this.testEchoWithNoArgument();
		this.testPipe();
	}

	public static void main(String[] args) {
		new KonohaProcessTest().Execute();
	}
}
