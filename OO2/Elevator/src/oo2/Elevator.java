package oo2;

public class Elevator {
	
	private String direction;
	private double time_now;
	private int floor_now;
	
	private static final String UP = "UP";
	private static final String DOWN = "DOWN";
	//private static final int STOP = 0;
	
	public Elevator() {
		// TODO Auto-generated constructor stub
		direction = UP;
		time_now = 0;
		floor_now = 1;
	}
	public double getTime_now() {
		return time_now;
	}
	public void setTime_now(double time_now) {
		this.time_now = time_now;
	}
	public void runElevator(int target_floor) {
		if (target_floor > floor_now) { 
			direction = UP;
		}else if (target_floor < floor_now) {
			direction = DOWN;
		}
		time_now += Math.abs(floor_now - target_floor) * 0.5;
		floor_now = target_floor;
		//(n, UP/DOWN, t)
		System.out.println("(" + floor_now + ", " + direction +", " + time_now + ")");
		time_now += 1;
	}
	public void runElevator(int from_floor, int direction) {

		time_now += Math.abs(floor_now - from_floor) * 0.5;
		floor_now = from_floor;
		if (direction == 2) {
			this.direction = UP;
		}else {
			this.direction = DOWN;
		}
		
		System.out.println("(" + floor_now + ", " + this.direction +", " + time_now + ")");
		time_now += 1;
	}

}
