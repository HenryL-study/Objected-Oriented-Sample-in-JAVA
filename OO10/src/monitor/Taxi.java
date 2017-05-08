package monitor;

import java.util.Random;


//import javafx.util.Pair;

public abstract class Taxi implements Runnable{
	protected int now_x;
	protected int now_y;
	protected int state;
	protected int ID;
	protected int credit;
	protected Passenger passenger;
	protected int Direction;
	protected int exDirection;
	protected int time;
	protected int rest_count;
	
	public boolean repOK() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the true if the rep variant holds for this. otherwise return false.
		 */
		if (0<=now_x && now_x<80 && 0<=now_y && now_y < 80 && 4<= state && state <=7 && 
				0<= ID && ID <100 && credit >= 0 && -1<= Direction && Direction <=3 
				&& -1<= exDirection && exDirection <=3 && time >= 0 && 0<=rest_count && rest_count <=200) {
			return true;
		}
		return false;
	}
	
	public Taxi(int id) {
		/*
		 * Requires: Taxi id.
		 * Modifies: Nothing.
		 * Effects: Initialize a taxi.
		 */
		// TODO Auto-generated constructor stub
		this.ID = id;
		state = Types.WAIT;
		passenger = null;
		Random random = new Random();
		now_x = random.nextInt(80);
		now_y = random.nextInt(80);
		Direction = random.nextInt(4);
		exDirection = -1;
		rest_count = Types.WAIT_TIME;
		credit = 0;
		time = 0;
	}
	
	public void setPassenger(Passenger passenger) {
		/*
		 * Requires: A passenger.
		 * Modifies: this.passenger and credit.
		 * Effects: Allocate a passenger to this taxi.
		 */
		this.passenger = passenger;
		credit+=3;
	}
	
	public void run() {
		fuckrun();
	}
	
	public abstract void fuckrun();
	
	protected void runTaxi(int di){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: run the taxi.
		 */
		while (true) {
			if (exDirection == -1) {
				break;
			}
			if ((Direction == Types.UP && exDirection == Types.RIGHT) || (Direction == Types.DOWN && exDirection == Types.LEFT) ||
					(Direction == Types.LEFT && exDirection == Types.UP) || (Direction == Types.RIGHT && exDirection == Types.DOWN)) {
				break;
			}
			if ((Direction == Types.UP || Direction == Types.DOWN) && Map.canPass(now_x, now_y, 1)) {
				break;
			}
			if ((Direction == Types.LEFT || Direction == Types.RIGHT) && Map.canPass(now_x, now_y, 0)) {
				break;
			}
//			if (ID == 0) {
//				System.out.println(Direction + " " + exDirection + " " + Map.getlight(now_x, now_y));
//			}
			try {
				Thread.sleep(Types.BASE_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map.addFlow(now_x, now_y, Direction);
		Map.minusFlow(now_x, now_y, exDirection);
		switch (di) {
		case Types.UP:
			now_x--;
			exDirection = Types.DOWN;
			break;
		case Types.DOWN:
			now_x++;
			exDirection = Types.UP;
			break;
		case Types.RIGHT:
			now_y++;
			exDirection = Types.LEFT;
			break;
		case Types.LEFT:
			now_y--;
			exDirection = Types.RIGHT;
			break;
		default:
			break;
		}
	}
	
	public int getID(){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the taxi'ID.
		 */
		return ID;
	}
	
	public int getCredit(){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the taxi' credit.
		 */
		return credit;
	}
	
	public int getState(){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the taxi' state.
		 */
		return state;
	}
	
	/**
	 * @return the now_x
	 */
	public int getNow_x() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the taxi' x now.
		 */
		return now_x;
	}

	/**
	 * @return the now_y
	 */
	public int getNow_y() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the taxi' y now.
		 */
		return now_y;
	}
	
	public int getTime() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the time.
		 */
		return time;
	}
}

