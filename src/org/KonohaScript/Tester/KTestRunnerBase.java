package org.KonohaScript.Tester;

public abstract class KTestRunnerBase {
	void Run(KTestCase TestCase) {
		try {
			TestCase.Init();
			TestCase.Test();
		}
		catch (Exception e) {
		} finally {
			TestCase.Exit();
		}
	}
}
