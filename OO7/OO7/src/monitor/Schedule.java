package monitor;

import java.util.TimerTask;

//import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

public class Schedule extends TimerTask{
	
	private static int i = 0;

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
