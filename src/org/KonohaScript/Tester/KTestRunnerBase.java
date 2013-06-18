package org.KonohaScript.Tester;

public abstract class KTestRunnerBase {
	void Run(KTestCase TestCase) {
		try {
			TestCase.Init();
			TestCase.Test();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		catch (Error e) {
			e.printStackTrace();
		} finally {
			TestCase.Exit();
		}
	}
}
