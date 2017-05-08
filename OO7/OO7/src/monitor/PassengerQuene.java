package monitor;

import java.util.Vector;

public class PassengerQuene {
	private static Vector<Passenger> passengers = new Vector<Passenger>();
	private static int size;
	
	public static void pushPassenger(Passenger p){
		if (size <= 400) {
			
			passengers.addElement(p);
			size++;
		}
		else {
			System.out.println("The queue is full!");
		}
	}
	
	public static Passenger pullPassenger(){
		Passenger passenger = passengers.get(0);
		passengers.remove(0);
		size--;
		return passenger;
	}
	
	public static int getsize(){
		return passengers.size();
	}
	
}
