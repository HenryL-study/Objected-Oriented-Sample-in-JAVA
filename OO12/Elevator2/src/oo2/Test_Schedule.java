package oo2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Test_Schedule {
	
	private Schedule  schedule;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		schedule = new Schedule();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_sche() {
		Request request = new Request("ER", 4, 1);
		schedule.sche(request);
		assertEquals(schedule.gElevator().getdirection(), "UP");
		assertEquals(schedule.gElevator().getFloor_now(), 4);
		assertEquals(schedule.gElevator().getTime_now(), 3.5, 0.1);
		request = new Request("FR", 5, "UP" ,0);
		schedule.sche(request);
		assertEquals(schedule.gElevator().getdirection(), "UP");
		assertEquals(schedule.gElevator().getFloor_now(), 5);
		assertEquals(schedule.gElevator().getTime_now(), 5.0, 0.1);
		request = new Request("FR", 6, "UP" ,7);
		schedule.sche(request);
		assertEquals(schedule.gElevator().getdirection(), "UP");
		assertEquals(schedule.gElevator().getFloor_now(), 6);
		assertEquals(schedule.gElevator().getTime_now(), 8.5, 0.1);
		request = new Request("ER", 7, 8);
		schedule.sche(request);
		assertEquals(schedule.gElevator().getdirection(), "UP");
		assertEquals(schedule.gElevator().getFloor_now(), 7);
		assertEquals(schedule.gElevator().getTime_now(), 10.0, 0.1);
	}
	
	@Test
	public void test_startSchedule() {
		try {
			schedule.addQueue("(FR, 5, UP ,0)");
		} catch (ElevatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		schedule.startSchedule();
		assertEquals(schedule.gElevator().getdirection(), "UP");
		assertEquals(schedule.gElevator().getFloor_now(), 5);
		assertEquals(schedule.gElevator().getTime_now(), 3, 0.1);
		
	}
	
	

}
