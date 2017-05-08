package monitor;

import java.util.TimerTask;

//import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

public class Schedule extends TimerTask{
	
	private static int i = 0;

	public boolean repOK() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the true if the rep variant holds for this. otherwise return false.
		 */
		if (i >= 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public void  run() {
		// TODO Auto-generated method stub
		if (PassengerQuene.getsize() != 0) {
			Passenger passenger = PassengerQuene.pullPassenger();
			Thread t = new Thread(passenger);
			t.setName("passenger " + i);
			i++;
			t.start();
		}
	}

}
