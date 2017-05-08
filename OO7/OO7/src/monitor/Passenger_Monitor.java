package monitor;

import java.util.Random;

public class Passenger_Monitor implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
//-------------------------------------------------------------------------------------------------------
		Random random = new Random();
		for (int i = 0; i < 300; i++) {
			addPSG(new Index(random.nextInt(80), random.nextInt(80)), new Index(random.nextInt(80), random.nextInt(80)));
		}
//------------------------------------------------------------------------------------------------------
	}
	
	private void addPSG(Index loc, Index des){
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
