package monitor;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class Normal_Taxi extends Taxi {

	public Normal_Taxi(int id) {
		/*
		 * Requires: Taxi id.
		 * Modifies: Nothing.
		 * Effects: Initialize a taxi.
		 */
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fuckrun() {
		// TODO Auto-generated method stub
		/*
		 * Requires: Nothing.
		 * Modifies: this.
		 * Effects: run the taxi.
		 */
		try {
			while (true) {
				time++;
				switch (state) {
				case Types.WAIT:
					if (passenger != null) {
						state = Types.GETPSG;
						//System.out.println("Taxi " + ID +": go to get " + passenger.toString());
						Direction = Map.shortestPath2(now_x, now_y, passenger.getLocation().getX(), passenger.getLocation().getY());
						if (Direction == -1) {
							state = Types.REST;
							rest_count = Types.REST_TIME;
						}else {
							runTaxi(Direction);
						}
					}else if (rest_count == 0) {
						state = Types.REST;
						rest_count = Types.REST_TIME;
					}else {
						//TODO Change with car flow.
						Vector<Integer> d = new Vector<Integer>();
						for (int i = 0; i < 4; i++) {
							if (!((now_x == 0 && i == Types.UP) || (now_x == 79 && i == Types.DOWN) || (now_y == 0 && i == Types.LEFT) || (now_y == 79 && i == Types.RIGHT)
								||(i == Types.UP && !Map.isConnect(new Index(now_x, now_y), new Index(now_x-1, now_y))) || (i == Types.DOWN && !Map.isConnect(new Index(now_x, now_y), new Index(now_x+1, now_y))) 
								|| (i == Types.LEFT && !Map.isConnect(new Index(now_x, now_y), new Index(now_x, now_y-1))) || (i == Types.RIGHT && !Map.isConnect(new Index(now_x, now_y), new Index(now_x, now_y+1))))) {
								if (d.size() > 0 && (Map.getFlow(now_x, now_y, i) > Map.getFlow(now_x, now_y, d.get(0)))) {
									continue;
								}
								if (d.size() > 0 && (Map.getFlow(now_x, now_y, i) < Map.getFlow(now_x, now_y, d.get(0)))) {
									d.removeAllElements();
								}
								d.addElement(i);
							}
						}
						if (d.size() == 0) {
							System.out.println(now_x + " " + now_y);
						}
						Direction = d.elementAt(new Random().nextInt(d.size()));
						
						
						Vector<Passenger> passengers = Map.findPasg(now_x, now_y);
						for (Iterator<Passenger> iterator = passengers.iterator(); iterator.hasNext();) {
							Passenger passenger = (Passenger) iterator.next();
							if (passenger.addTaix(this)) {
								credit++;
								//System.out.println(ID + " ÇÀµ¥³É¹¦ + now: " + now_x + " " + now_y);
							}
						}
						runTaxi(Direction);
						rest_count--;
					}
					break;
				case Types.GETPSG:
					Direction = Map.shortestPath2(now_x, now_y, passenger.getLocation().getX(), passenger.getLocation().getY());
					if (Direction == -1) {
						state = Types.REST;
						rest_count = Types.REST_TIME;
					}else {
						runTaxi(Direction);
					}
					break;
				case Types.SERVING:
					Direction = Map.shortestPath2(now_x, now_y, passenger.getDestination().getX(), passenger.getDestination().getY());
					if (Direction == -1) {
						System.out.println("Taxi " + ID +": served " + passenger.toString());
						state = Types.REST;
						rest_count = Types.REST_TIME;
						passenger = null;
					}else {
						runTaxi(Direction);
					}
					break;
				case Types.REST:
					if (rest_count == 0) {
						if (passenger == null) {
							state = Types.WAIT;
							Vector<Integer> d = new Vector<Integer>();
							for (int i = 0; i < 4; i++) {
								if (!((now_x == 0 && i == Types.UP) || (now_x == 79 && i == Types.DOWN) || (now_y == 0 && i == Types.LEFT) || (now_y == 79 && i == Types.RIGHT)
									||(i == Types.UP && !Map.isConnect(new Index(now_x, now_y), new Index(now_x-1, now_y))) || (i == Types.DOWN && !Map.isConnect(new Index(now_x, now_y), new Index(now_x+1, now_y))) 
									|| (i == Types.LEFT && !Map.isConnect(new Index(now_x, now_y), new Index(now_x, now_y-1))) || (i == Types.RIGHT && !Map.isConnect(new Index(now_x, now_y), new Index(now_x, now_y+1))))) {
									if (d.size() > 0 && (Map.getFlow(now_x, now_y, i) > Map.getFlow(now_x, now_y, d.get(0)))) {
										continue;
									}
									if (d.size() > 0 && (Map.getFlow(now_x, now_y, i) < Map.getFlow(now_x, now_y, d.get(0)))) {
										d.removeAllElements();
									}
									d.addElement(i);
								}
							}
							if (d.size() == 0) {
								System.out.println(now_x + " " + now_y);
							}
							Direction = d.elementAt(new Random().nextInt(d.size()));
							rest_count = Types.WAIT_TIME;
							runTaxi(Direction);
						}else {
							state = Types.SERVING;
							Direction = Map.shortestPath2(now_x, now_y, passenger.getDestination().getX(), passenger.getDestination().getY());
							if (Direction == -1) {
								System.out.println("Taxi " + ID +": served " + passenger.toString());
								state = Types.REST;
								rest_count = Types.REST_TIME;
								passenger = null;
							}else {
								runTaxi(Direction);
							}
						}
					}else {
						rest_count--;
					}
					break;
				default:
					break;
				}
				if (now_x<0 || now_x>79 || now_y<0 || now_y > 79) {
					System.out.println("Taxi " + ID + " IS BROKEN QAQ " + now_x + "  " + now_y + " " + Direction);
					break;
				}
				try {
					Thread.sleep(Types.BASE_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		} catch (Exception e) {
			// TODO: handle exception 
			e.printStackTrace();
			System.out.println("Taxi find error.");
		}
	}
	
	@Override
	public boolean repOK() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the true if the rep variant holds for this. otherwise return false.
		 */
		
		return super.repOK();
	}

}
