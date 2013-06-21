package org.KonohaScript.Tester;

public class KTestRunnerBase {
	static boolean	DEBUG	= !true;

	void Run(KTestCase TestCase) {
		if(DEBUG) {
			TestCase.Init();
			TestCase.Test();
			TestCase.Exit();
		} else {
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
}
