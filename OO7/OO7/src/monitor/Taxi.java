package monitor;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;


//import javafx.util.Pair;

public class Taxi implements Runnable{
	private int now_x;
	private int now_y;
	private int state;
	private int ID;
	private int credit;
	private Passenger passenger;
	//接乘客 或 到目的地的路程
	private Vector<Integer> way_to_go;
	private int Direction;
	private int time;
	private int rest_count;
	
	public Taxi(int id) {
		// TODO Auto-generated constructor stub
		this.ID = id;
		state = Types.WAIT;
		passenger = null;
		Random random = new Random();
		now_x = random.nextInt(80);
		now_y = random.nextInt(80);
		Direction = random.nextInt(4);
		rest_count = Types.WAIT_TIME;
		credit = 0;
		time = 0;
	}
	
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
		credit+=3;
	}
	
	public void run() {
		try {

			while (true) {
				try {
					Thread.sleep(Types.BASE_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				time++;
				switch (state) {
				case Types.WAIT:
					if (passenger != null) {
						state = Types.GETPSG;
						//System.out.println("Taxi " + ID +": go to get " + passenger.toString());
						try {
							way_to_go = Map.shortestPath(now_x, now_y, passenger.getLocation().getX(), passenger.getLocation().getY());
						} catch (MapException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (way_to_go.size() == 0) {
							state = Types.REST;
							rest_count = Types.REST_TIME;
							//passenger = null;
						}else {
							Direction = way_to_go.get(0);
							way_to_go.remove(0);
						}
					}else if (rest_count == 0) {
						state = Types.REST;
						rest_count = Types.REST_TIME;
					}else {
						Vector<Integer> d = new Vector<Integer>();
						for (int i = 0; i < 4; i++) {
							if (!((now_x == 0 && i == Types.UP) || (now_x == 79 && i == Types.DOWN) || (now_y == 0 && i == Types.LEFT) || (now_y == 79 && i == Types.RIGHT)
								||(i == Types.UP && !Map.isConnect(new Index(now_x, now_y), new Index(now_x-1, now_y))) || (i == Types.DOWN && !Map.isConnect(new Index(now_x, now_y), new Index(now_x+1, now_y))) 
								|| (i == Types.LEFT && !Map.isConnect(new Index(now_x, now_y), new Index(now_x, now_y-1))) || (i == Types.RIGHT && !Map.isConnect(new Index(now_x, now_y), new Index(now_x, now_y+1))))) {
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
								//System.out.println(ID + " 抢单成功 + now: " + now_x + " " + now_y);
							}
						}
						rest_count--;
					}
					break;
				case Types.GETPSG:
					if (way_to_go.size() == 0) {
						//System.out.println("Taxi " + ID +": geted " + passenger.toString());
						state = Types.REST;
						rest_count = Types.REST_TIME;
						try {
							way_to_go = Map.shortestPath(now_x, now_y, passenger.getDestination().getX(), passenger.getDestination().getY()) ;
						} catch (MapException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else {
						//System.out.println("Taxi "+ ID + ": " + now_x + " " + now_y);
						Direction = way_to_go.get(0);
						way_to_go.remove(0);
					}
					break;
				case Types.SERVING:
					if (way_to_go.size() == 0) {
						System.out.println("Taxi " + ID +": served " + passenger.toString());
						state = Types.REST;
						rest_count = Types.REST_TIME;
						passenger = null;
					}else {
						//System.out.println(time +": Taxi "+ ID + ": " + now_x + " " + now_y);
						Direction = way_to_go.get(0);
						way_to_go.remove(0);
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
									d.addElement(i);
								}
							}
							if (d.size() == 0) {
								System.out.println(now_x + " " + now_y);
							}
							
							Direction = d.elementAt(new Random().nextInt(d.size()));
							rest_count = Types.WAIT_TIME;
						}else {
							state = Types.SERVING;
							if (way_to_go.size() == 0) {
								System.out.println("Taxi " + ID +": served " + passenger.toString());
								state = Types.REST;
								rest_count = Types.REST_TIME;
								passenger = null;
							}else {
								Direction = way_to_go.get(0);
								way_to_go.remove(0);
							}
						}
					}else {
						rest_count--;
					}
					break;
				default:
					break;
				}
				if (state != Types.REST) {
					switch (Direction) {
					case Types.UP:
						now_x--;
						break;
					case Types.DOWN:
						now_x++;
						break;
					case Types.RIGHT:
						now_y++;
						break;
					case Types.LEFT:
						now_y--;
						break;
					default:
						break;
					}
					
				}
				if (now_x<0 || now_x>79 || now_y<0 || now_y > 79) {
					System.out.println("Taxi " + ID + " IS BROKEN QAQ " + now_x + "  " + now_y + " " + Direction);
					break;
				}else {
					//System.out.println("Taxi " + ID  + ":  " + now_x + "  " + now_y );
				}
			}
		
		} catch (Exception e) {
			// TODO: handle exception 
			System.out.println("Taxi find error.");
		}
		
	}
	
	public int getID(){
		return ID;
	}
	
	public int getCredit(){
		return credit;
	}
	
	public int getState(){
		return state;
	}
	
	/**
	 * @return the now_x
	 */
	public int getNow_x() {
		return now_x;
	}

	/**
	 * @return the now_y
	 */
	public int getNow_y() {
		return now_y;
	}
	
	public int getTime() {
		return time;
	}
}

