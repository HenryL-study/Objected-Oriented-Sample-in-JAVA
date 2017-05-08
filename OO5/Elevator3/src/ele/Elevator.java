package ele;

import java.util.Vector;

public class Elevator {
	private String state;
	private boolean isopen;
	private int floor_now;
	private int target_floor;
	private int exercise_Amount;
	private int open_times;
	private int timer;
	private Floor floor;
	private Vector<Request> requests;
	private int Ele_num;
	
	private static final String UP = "UP";
	private static final String DOWN = "DOWN";
	private static final String STOP = "STOP";
//	private static final String OPEN = "OPEN";
	
	public Elevator(int num) {
		// TODO Auto-generated constructor stub
		state = STOP;
		floor_now = 1;
		target_floor = 1;
		exercise_Amount = 0;
		open_times = 0;
		timer = 0;
		isopen = false;
		floor = new Floor();
		requests = new Vector<Request>();
		Ele_num = num;
	}
	
	public void addRequest(Request req) {
		requests.add(req);
	}
	
	public void seach_print(int num) {
		for (int i = 0; i < requests.size(); i++) {
			if (requests.get(i).getTarget_floor() == num) {
				System.out.println("#" + Ele_num + ": " + requests.get(i).toString());
				requests.remove(i);
				i--;
			}
		}
	}
	
	public void setFloor(int num, boolean re) {
		floor.setFloor(num, re);
	}
	
	public void runElevator() {
		if (timer == 0) {
			if (isopen) {
				seach_print(floor_now);
				if (open_times != 59) {
					//System.out.println(floor_now + "+ ot " + open_times); 
					open_times++;
				}else if (open_times == 59) {
					open_times = 0;
					isopen = false;
					if (target_floor > floor_now) { 
						state = UP;
					}else if (target_floor < floor_now) {
						state = DOWN;
					}else {
						state = STOP;
					}
				}
			}else if (state.equals(UP)) {
				timer++;
			}else if (state.equals(DOWN)) {
				timer++;
			}
		}else{
			timer++;
			if (timer == 30) {
				if (state.equals(UP)) {
					floor_now++;
					exercise_Amount++;
					if (floor.getFloor(floor_now)) {
						isopen = true;
						floor.setFloor(floor_now, false);
					}
				}else if (state.equals(DOWN)) {
					floor_now--;
					exercise_Amount++;
					if (floor.getFloor(floor_now)) {
						isopen = true;
						floor.setFloor(floor_now, false);
					}
				}
				timer = 0;
			}
		}
	}
	
	
	public void openElevator() {
		isopen = true; 
	}
	
	public void setTarget(int target) {
		
		if (target > target_floor) {
			state = UP;
		}else  if (target < target_floor) {
			state = DOWN;
		}else{
			state = STOP;
		}
		target_floor = target;
		//System.out.println(target + state);
	}
	

	public int getFloor_now() {
		return floor_now;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getTarget_floor() {
		return target_floor;
	}
	
	public int getExercise_Amount() {
		return exercise_Amount;
	}
	
	public int getTimer() {
		return timer;
	}
	
	public int getOpen_times() {
		return open_times;
	}

	public boolean isIsopen() {
		return isopen;
	}
	
}
