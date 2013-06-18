package org.KonohaScript.Tester;

public abstract class KTestRunnerBase {
	void Run(KTestCase TestCase) {
		try {
			TestCase.Init();
			TestCase.Test();
		} finally {
			TestCase.Exit();
		}
	}
}
