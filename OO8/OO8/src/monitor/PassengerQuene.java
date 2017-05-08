package monitor;

import java.util.Vector;

public class PassengerQuene {
	private static Vector<Passenger> passengers = new Vector<Passenger>();
	private static int size;
	
	public static void pushPassenger(Passenger p){
		/*
		 * Requires: A passenger.
		 * Modifies: passengers.
		 * Effects: if passengers' size less than 400 then add the passenger into passengers.
		 */
		if (size <= 400) {
			
			passengers.addElement(p);
			size++;
		}
		else {
			System.out.println("The queue is full!");
		}
	}
	
	public static Passenger pullPassenger(){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Push a passenger and return.
		 */
		Passenger passenger = passengers.get(0);
		passengers.remove(0);
		size--;
		return passenger;
	}
	
	public static int getsize(){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the passengers' size now..
		 */
		return passengers.size();
	}
	
}
