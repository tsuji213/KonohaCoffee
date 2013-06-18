//package org.KonohaScript.Tester;
//
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//public abstract class JavaTestCase implements TestAssert {
//	@Override
//	@Test
//	public void Test() {
//		System.out.println("1:1");
//	}
//
//	@Override
//	@BeforeClass
//	public void Init() {
//		System.out.println("1:2");
//
//	}
//
//	@Override
//	@AfterClass
//	public void Exit() {
//		System.out.println("1:3");
//	}
//
//	protected void Assert(boolean Cond) {
//		org.junit.Assert.assertTrue(Cond);
//	}
//
//	protected void AssertTrue(boolean Cond) {
//		org.junit.Assert.assertTrue(Cond);
//	}
//
//	protected void AssertFalse(boolean Cond) {
//		org.junit.Assert.assertFalse(Cond);
//	}
//
//	protected void AssertEqual(boolean Actual, boolean Expected) {
//		org.junit.Assert.assertEquals(Actual, Expected);
//	}
//
//	protected void AssertEqual(int Actual, int Expected) {
//		org.junit.Assert.assertEquals(Actual, Expected);
//	}
//
//	protected void AssertEqual(float Actual, float Expected, float Delta) {
//		org.junit.Assert.assertEquals(Actual, Expected, Delta);
//	}
//
//	protected void AssertEqual(Object Actual, Object Expected) {
//		org.junit.Assert.assertEquals(Actual, Expected);
//	}
//
//	protected void Fail() {
//		org.junit.Assert.fail();
//	}
//}
