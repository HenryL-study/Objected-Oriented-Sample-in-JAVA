package oo2;

/*
 * @Overview 
 * 		stimulate the elevator.
 * @Process Specifications
 * 		see below
 * @Indicated Object
 * 		see below
 * @Abstract Function
 * 		AF(c) = (direction, time, floor), where direction = c.direction, time = c.time_now, floor = c,floor_now
 * @Invariance
 * 		direction != null && time_now>=0 && 0<floor_now<11
 */

public class Elevator {
	
	private String direction;
	private double time_now;
	private int floor_now;
	
	private static final String UP = "UP";
	private static final String DOWN = "DOWN";
	
	public Elevator() {
		/*
		 * Requires: Nothing.
		 * Modifies: this.
		 * Effects: Initialize the elevator
		 */
		direction = UP;
		time_now = 0;
		floor_now = 1;
	}
	
	public void setfloor_now(int floor_now) {
		/*
		 * Requires: 1<= floor_now < 11.
		 * Modifies: time_now.
		 * Effects: set the time_now.
		 */
		this.floor_now = floor_now;
	}
	public double getTime_now() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: get the time_now.
		 */
		return time_now;
	}
	public void setTime_now(double time_now) {
		/*
		 * Requires: Nothing.
		 * Modifies: time_now.
		 * Effects: set the time_now.
		 */
		this.time_now = time_now;
	}
	public void runElevator(int target_floor) {
		/*
		 * Requires: 10 >= target_floor >= 1.
		 * Modifies: this.
		 * Effects: run the elevator.
		 */

		if (target_floor > floor_now) { 
			direction = UP;
		}else if (target_floor < floor_now) {
			direction = DOWN;
		}
		if (target_floor != floor_now) {
			time_now += Math.abs(floor_now - target_floor) * 0.5;
			floor_now = target_floor;
			System.out.println("(" + floor_now + ", " + direction +", " + time_now + ")");
			time_now += 1;
		}
	}
	
	public void runElevator_first(int target_floor) {
		/*
		 * Requires: 10 >= target_floor >= 1.
		 * Modifies: this.
		 * Effects: first running of the elevator.
		 */
		if (target_floor > floor_now) { 
			direction = UP;
		}else if (target_floor < floor_now) {
			direction = DOWN;
		}
		if (target_floor != floor_now) {
			time_now += Math.abs(floor_now - target_floor) * 0.5;
			floor_now = target_floor;
			System.out.println("(" + floor_now + ", " + direction +", " + time_now + ")");
			time_now += 1;
		}else{
			time_now +=1;
		}
	}

	public int getFloor_now() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: get the floor_now.
		 */
		return floor_now;
	}
	
	public String getdirection() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: get the direction.
		 */
		return direction;
	}
	
	public boolean repOK() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the true if the rep variant holds for this. otherwise return false.
		 */
		if (direction != null && time_now>=0 && (0<floor_now || floor_now<11)) {
			return true;
		}
		return false;
	}

}
