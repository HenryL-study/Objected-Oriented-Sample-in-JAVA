package oo2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Test_Request {
	
	private  Request req;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void test_init() {
		/*
		 * 直接修改属性值，不测
		 */
		
	}
	@Test
	public void test_equal() {
		req = new Request("FR", 5, "UP", 2);
		Request req2 = new Request("FR", 5, "UP", 2);
		assertTrue(req.equals(req2));
		req2 = new Request("FR", 3, "UP", 2);
		assertFalse(req.equals(req2));
		req2 = new Request("ER", 3, 2);
		assertFalse(req.equals(req2));
		req2 = new Request("FR", 3, "DOWN", 2);
		assertFalse(req.equals(req2));
		req2 = new Request("FR", 3, "UP", 4);
		assertFalse(req.equals(req2));
	}
	@Test
	public void test_toString() {
		req = new Request("FR", 5, "UP", 2);
		assertEquals("(FR,5,UP,2)", req.toString());
		req = new Request("ER", 5, 2);
		assertEquals("(ER,5,2)", req.toString());		
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
}
