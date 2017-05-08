package oo2;

public class Elevator {
	
	private String direction;
	private double time_now;
	private int floor_now;
	//private String realdirection;
	
	private static final String UP = "UP";
	private static final String DOWN = "DOWN";
//	private static final String STOP = "STOP";
	
	public Elevator() {
		// TODO Auto-generated constructor stub
		direction = UP;
		time_now = 0;
		floor_now = 1;
		//realdirection = UP;
	}
	public double getTime_now() {
		return time_now;
	}
	public void setTime_now(double time_now) {
		this.time_now = time_now;
	}
	public void runElevator(int target_floor) {
//		if (target_floor > floor_now) { 
//			direction = UP;
//		}else if (target_floor < floor_now) {
//			direction = DOWN;
//		}else {
//			if (direction.equals(STOP)) {
//				return;
//			}
//			direction = STOP;
//		}
//		time_now += Math.abs(floor_now - target_floor) * 0.5;
//		floor_now = target_floor;
//		//(n, UP/DOWN, t)
//		if (direction != STOP) {
//			System.out.println("(" + floor_now + ", " + direction +", " + time_now + ")");
//		}
//		time_now += 1;
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
		return floor_now;
	}
	
//	public String getDirection() {
//		return this.direction;
//	}
//	public void runElevator(int from_floor, String direction) {
//
//		if (from_floor > floor_now) { 
//			direction = UP;
//		}else if (from_floor < floor_now) {
//			direction = DOWN;
//		}else {
//			if (direction.equals(STOP)) {
//				return;
//			}
//			direction = STOP;
//		}
//		time_now += Math.abs(floor_now - from_floor) * 0.5;
//		floor_now = from_floor;
//		//(n, UP/DOWN, t)
//		if (direction != STOP) {
//			System.out.println("(" + floor_now + ", " + direction +", " + time_now + ")");
//		}
//		time_now += 1;
//	}
}
