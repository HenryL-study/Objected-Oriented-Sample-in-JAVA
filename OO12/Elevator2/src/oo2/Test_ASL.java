package oo2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Test_ASL {

	private ASL_Schedule as;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		as = new ASL_Schedule();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		as.startSchedule();
		try {
			as.addQueue("(FR,8,UP,2)");
		} catch (ElevatorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		as.startSchedule();
		assertEquals(as.gElevator().getdirection(), "UP");
		assertEquals(as.gElevator().getFloor_now(), 1);
		assertEquals(as.gElevator().getTime_now(), 0, 0.1);
	}
	@Test
	public void test2(){
		try {
			as.addQueue("(FR,8,UP,0)");
			as.addQueue("(FR,4,DOWN,1)");
			as.addQueue("(ER,3,1)");
			as.addQueue("(ER,4,1)");
			as.addQueue("(ER,5,1)");
			as.addQueue("(ER,6,1)");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		as.startSchedule();
		assertEquals(as.gElevator().getdirection(), "DOWN");
		assertEquals(as.gElevator().getFloor_now(), 3);
		assertEquals(as.gElevator().getTime_now(), 12.0, 0.1);
	}
	@Test
	public void test3(){
		try {
			as.addQueue("(FR,8,UP,0)");
			as.addQueue("(ER,3,1)");
			as.addQueue("(ER,5,2)");
			as.addQueue("(ER,8,3)");
			as.addQueue("(FR,5,UP,6)");
			as.addQueue("(ER,6,100)");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		as.startSchedule();
		assertEquals(as.gElevator().getdirection(), "UP");
		assertEquals(as.gElevator().getFloor_now(), 6);
		assertEquals(as.gElevator().getTime_now(), 101.5, 0.1);
	}
	@Test
	public void test4(){
		try {
			as.addQueue("(ER,3,0)");
			as.addQueue("(ER,3,0)");
			as.addQueue("(ER,3,0)");
			as.addQueue("(ER,3,0)");
			as.addQueue("(ER,3,0)");
			as.addQueue("(ER,5,2)");
			as.addQueue("(ER,8,3)");
			as.addQueue("(FR,5,UP,6)");
			as.addQueue("(ER,6,100)");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		as.startSchedule();
		assertEquals(as.gElevator().getdirection(), "UP");
		assertEquals(as.gElevator().getFloor_now(), 6);
		assertEquals(as.gElevator().getTime_now(), 101.5, 0.1);
	}
	
	@Test
	public void test5(){
		try {
			as.addQueue("(ER,6,0)");
			as.addQueue("(ER,10,0)");
			as.addQueue("(ER,7,3)");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		as.startSchedule();
		assertEquals(as.gElevator().getdirection(), "UP");
		assertEquals(as.gElevator().getFloor_now(), 10);
		assertEquals(as.gElevator().getTime_now(), 7.5, 0.1);
	}
	
	@Test
	public void test6(){
		try {
			as.addQueue("(ER,4,0)");
			as.addQueue("(ER,2,0)");
			as.addQueue("(ER,8,1)");
			as.addQueue("(ER,6,2)");
			as.addQueue("(ER,4,2)");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		as.startSchedule();
		assertEquals(as.gElevator().getdirection(), "UP");
		assertEquals(as.gElevator().getFloor_now(), 8);
		assertEquals(as.gElevator().getTime_now(), 7.5, 0.1);
	}
	
	@Test
	public void test7(){
		try {
			as.addQueue("(FR,6,DOWN,0)");
			as.addQueue("(FR,4,DOWN,0)");
			as.addQueue("(FR,9,DOWN,0)");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		as.startSchedule();
		assertEquals(as.gElevator().getdirection(), "UP");
		assertEquals(as.gElevator().getFloor_now(), 9);
		assertEquals(as.gElevator().getTime_now(), 9.0, 0.1);
	}
	
	@Test
	public void test8(){
		try {
			as.addQueue("(FR,6,UP,0)");
			as.addQueue("(FR,4,UP,0)");
			as.addQueue("(FR,9,UP,0)");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		as.startSchedule();
		assertEquals(as.gElevator().getdirection(), "UP");
		assertEquals(as.gElevator().getFloor_now(), 9);
		assertEquals(as.gElevator().getTime_now(), 7.0, 0.1);
	}
	
	@Test
	public void test9(){
		try {
			as.addQueue("(FR,1,UP,0)");
			as.addQueue("(FR,1,UP,1)");
			as.addQueue("(FR,1,UP,2)");
			as.addQueue("(ER,2,2)");
			as.addQueue("(FR,1,UP,3)");
			as.addQueue("(FR,1,UP,4)");
			as.addQueue("(FR,1,UP,5)");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		as.startSchedule();
		assertEquals(as.gElevator().getdirection(), "DOWN");
		assertEquals(as.gElevator().getFloor_now(), 1);
		assertEquals(as.gElevator().getTime_now(), 6.0, 0.1);
	}
	
	@Test
	public void test61(){
		try {
			as.addQueue("(ER,4,0)");
			as.addQueue("(ER,4,2)");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		as.startSchedule();
		assertEquals(as.gElevator().getdirection(), "UP");
		assertEquals(as.gElevator().getFloor_now(), 4);
		assertEquals(as.gElevator().getTime_now(), 3.5, 0.1);
	}
	
//	@Test
//	public void test91(){
//		try {
//			as.addQueue("(FR,7,UP,0)");
//			as.addQueue("(ER,1,3)");
////			as.addQueue("(ER,6,7)");
////			as.addQueue("(FR,1,UP,7)");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		as.startSchedule();
//		//System.out.println("??");
//	}

}
