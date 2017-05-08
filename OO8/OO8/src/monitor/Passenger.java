package monitor;

import java.util.Vector;

public class Passenger implements Runnable{
	private Index location;
	private Index destination;
	private Vector<Taxi> taxis;
	
	public Passenger(Index location, Index destination) {
		/*
		 * Requires: Two Indexes.
		 * Modifies: this.
		 * Effects: Initialize the passenger.
		 */
		this.location = location;
		this.destination = destination;
		this.taxis = new Vector<Taxi>();
		//Map.addReq(location.getX(), location.getY(), this);
	}
	
	public boolean addTaix(Taxi taxi) {
		/*
		 * Requires: A taxi.
		 * Modifies: taxis.
		 * Effects: Add the taxi into taxis.
		 */
		if (!taxis.contains(taxi)) {
			taxis.addElement(taxi);
			return true;
		}else {
			return false;
		}
	}
	
	public Taxi selectTaxi(){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Arrange a taxi to serve this passenger.
		 */
		Vector<Taxi> HC_taxis = new Vector<Taxi>();
		Taxi taxi = null;
		//int i =0;
		while (taxis.size() !=0 && taxis.get(0).getState() != Types.WAIT) {
			taxis.remove(0);
		}
		if (taxis.size() == 0) {
			return null;
		}
		taxi = taxis.get(0);
		HC_taxis.addElement(taxi);
		for (int i = 1; i < taxis.size(); i++) {
			Taxi temp = taxis.get(i);
			if (temp.getCredit() > taxi.getCredit() && temp.getState() == Types.WAIT) {
				HC_taxis.removeAllElements();
				taxi = temp;
				HC_taxis.addElement(taxi);
			}else if (temp.getCredit() == taxi.getCredit() && temp.getState() == Types.WAIT) {
				HC_taxis.addElement(temp);
			}
		}
		taxi = HC_taxis.get(0);
		int length = 0;
		try {
			length = Map.shortestPath(taxi.getNow_x(), taxi.getNow_y(), location.getX(), location.getY()).size();
		} catch (MapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		for (int j = 1; j < HC_taxis.size(); j++) {
			int temp = 0;
			try {
				temp = Map.shortestPath(HC_taxis.get(j).getNow_x(), HC_taxis.get(j).getNow_y(), location.getX(), location.getY()).size();
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			if (temp < length) {
				length = temp;
				taxi = HC_taxis.get(j);
			}
		}
		return taxi;
	}

	/**
	 * @return the location
	 */
	public Index getLocation() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the location.
		 */
		return location;
	}

	/**
	 * @return the destination
	 */
	public Index getDestination() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the destination.
		 */
		return destination;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Map.addReq(location.getX(), location.getY(), this);
		try {
			Thread.sleep(Types.CALL_TIME);
			//wait(Types.CALL_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if (taxis.size()>0) {
//			System.out.println(this.toString() + " have taxi.");
//			for (int i = 0; i < taxis.size(); i++) {
//				System.out.print(taxis.get(i).getID() + " " + taxis.get(i).getState() + " " + taxis.get(i).getTime() + "|| ");
//			}
//		}
//		System.out.println("");
			Taxi taxi;
			if ((taxi = selectTaxi()) != null) {
				taxi.setPassenger(this);
				//System.out.println(this.toString() + " " + taxi.getID());
			}else {
				//System.out.println(this.toString() + " can't find any taxi.");
			}
			Map.deleteReq(location.getX(), location.getY(), this);
			//System.out.println("END");
	}
	
	@Override
	public String toString() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the passenger's string.
		 */
		return "(" + location.getX() + "," + location.getY() + ")" + " to (" + destination.getX() + "," + destination.getY() + ")";
	}
	
	
}
