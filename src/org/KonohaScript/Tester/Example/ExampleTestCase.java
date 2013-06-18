package org.KonohaScript.Tester.Example;

import org.KonohaScript.Tester.KTestCase;

public class ExampleTestCase extends KTestCase {
	public static void main(String[] args) {
		ExampleTestCase testCase = new ExampleTestCase();
		testCase.Fail();
		testCase.Assert(true);
		testCase.Assert(false);
	}

	@Override
	public void Init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Test() {
		// TODO Auto-generated method stub

	}
}
