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
		Process Proc1 = new Process("echo");
		Proc1.start();
		Proc1.waitFor();
		this.AssertEqual(Proc1.getStdout(), "\n");
		this.AssertEqual(Proc1.getStderr(), "");
	}

	void testSimpleEcho() {
		Process Proc1 = new Process("echo");
		String Args1[] = { "1234" };
		Proc1.setArgument(Args1);
		Proc1.start();
		Proc1.waitFor();
		this.AssertEqual(Proc1.getStdout(), "1234\n");
		this.AssertEqual(Proc1.getStderr(), "");
	}

	void testPipe() {
		Process Proc1 = new Process("echo");
		String Args1[] = { "1:2:3" };
		Proc1.setArgument(Args1);
		Proc1.start();

		Process Proc2 = new Process("cut");
		String Args2[] = { "-d", ":", "-f", "2" };
		Proc2.setArgument(Args2);
		Proc2.start();

		Proc1.pipe(Proc2);

		Proc2.waitFor();

		//this.AssertEqual(Proc1.getStdout(), "1:2:3");
		//this.AssertEqual(Proc1.getStderr(), "");

		this.AssertEqual(Proc2.getStdout(), "2\n");
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
