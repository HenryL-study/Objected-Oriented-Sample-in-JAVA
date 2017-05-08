package oo2;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;




public class Test_Elevator {
	private  Elevator ele;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//pb.readmap();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void test_init() {
		assertEquals("UP", ele.getdirection());
		assertEquals(ele.getTime_now(), 0, 0.1);
		assertEquals(ele.getFloor_now(), 1);
	}
	@Test
	public void test_runElevator() {
		ele.runElevator(1);
		assertEquals(ele.getdirection(), "UP");
		assertEquals(ele.getTime_now(), 0, 0.1);
		assertEquals(ele.getFloor_now(), 1);
		ele.setfloor_now(5);
		ele.runElevator(8);
		assertEquals(ele.getdirection(), "UP");
		assertEquals(ele.getTime_now(), 2.5, 0.1);
		assertEquals(ele.getFloor_now(), 8);
		ele.runElevator(5);
		assertEquals(ele.getdirection(), "DOWN");
		assertEquals(ele.getTime_now(), 5, 0.1);
		assertEquals(ele.getFloor_now(), 5);
	}
	@Test
	public void test_runElevator_first() {
		ele.runElevator_first(1);
		assertEquals(ele.getdirection(), "UP");
		//System.out.println(ele.getTime_now());
		assertEquals(ele.getTime_now(), 1, 0.1);
		assertEquals(ele.getFloor_now(), 1);
		ele.setfloor_now(5);
		ele.runElevator_first(8);
		assertEquals(ele.getdirection(), "UP");
		assertEquals(ele.getTime_now(), 3.5, 0.1);
		assertEquals(ele.getFloor_now(), 8);
		ele.runElevator_first(5);
		assertEquals(ele.getdirection(), "DOWN");
		assertEquals(ele.getTime_now(), 6, 0.1);
		assertEquals(ele.getFloor_now(), 5);
		
	}
	
	@Before
	public void setUp() throws Exception {
		ele = new Elevator();
	}

	@After
	public void tearDown() throws Exception {
	}

}