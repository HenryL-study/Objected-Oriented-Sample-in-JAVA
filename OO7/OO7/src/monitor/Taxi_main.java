package monitor;

import java.util.Timer;

public class Taxi_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new Map();
		} catch (MapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			return;
		}
//		Taxi taxi = new Taxi(0);
//		Thread thread = new Thread(taxi);
//		thread.start();

		Thread [] taxis = new Thread[100];
		for (int i = 0; i < taxis.length; i++) {
			taxis[i] = new Thread(new Taxi(i));
			taxis[i].setName("TAXI" + i);
		}
		Thread thread = new Thread(new Passenger_Monitor());
		thread.setName("Passenger_Monitor");
		Schedule schedule = new Schedule();
		Timer timer = new Timer();
		timer.schedule(schedule, 0, 100);
		thread.start();
		
		for (int i = 0; i < 100; i++) {
			taxis[i].start();
		}
		
		//-----------INPUT YOUR TEST CODE------------------
		// eg. System.out.println("taxis[i].getNow_x()")
	}

}
