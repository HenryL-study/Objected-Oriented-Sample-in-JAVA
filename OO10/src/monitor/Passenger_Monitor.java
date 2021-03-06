package monitor;

import java.util.Iterator;
import java.util.Random;

public class Passenger_Monitor implements Runnable{
	
	private Taxi [] taxis;
	
	public boolean repOK() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the true if the rep variant holds for this. otherwise return false.
		 */
		if (taxis != null) {
			return true;
		}
		return false;
	}

	public Passenger_Monitor(Taxi[] taxis) {
		/*
		 * Requires: An array of Taxi
		 * Modifies: this.taxis
		 * Effects: set the taxis
	     */
		this.taxis = taxis;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
        /*
         * -----------INPUT YOUR TEST CODE------------------
         */
		
		//------------------------ADD PASSENGER----------------------------------------------------------
		Random random = new Random();
		for (int i = 0; i < 300; i++) {
			addPSG(new Index(random.nextInt(80), random.nextInt(80)), new Index(random.nextInt(80), random.nextInt(80)));
		}
		//-----------------------CHECK TAXI STATUS-------------------------------------------------------
				// eg. System.out.println(taxis[i].getNow_x())
//		while (true) {
//			try {
//				Thread.sleep(Types.BASE_TIME);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(taxis[5].getNow_x() + " " + taxis[5].getNow_y());
//		}
		Tracking_Taxi taxi = (Tracking_Taxi)taxis[90];
		if (taxi.getServe_times() >= 0) {
			for (Iterator <Index> iterator = taxi.iterator(0); iterator.hasNext();) {
				Index index = (Index)iterator.next();
				System.out.println("x,y : " + index.getX() + "," + index.getY());
			}
		}
	}
	
	private void addPSG(Index loc, Index des){
		/*
		 * Requires: Two Index variables which indicate the passenger location and destination.
		 * Modifies: Nothing.
		 * Effects: Construct a passenger and then add the passenger request into the passengers.
		 */
		if (loc == null || des == null) {
			return;
		}
		if (loc.getX() > 79 || loc.getX() <0 || loc.getY() > 79 || loc.getY() < 0 || des.getX() > 79 || des.getX() <0 || des.getY() > 79 || des.getY() < 0) {
			return;
		}
		Passenger passenger = new  Passenger(loc, des);
		PassengerQuene.pushPassenger(passenger);
		try {
			Thread.sleep(Types.BASE_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
