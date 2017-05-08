package oo2;

public class Request {

	private String request_type;
	private int target_direction;
	private int target_floor;
	private int request_time;
	
	private static final int UP = 2;
	//private static final int DOWN = 1;
	//private static final int STOP = 0;
	
	public Request(String request_type,  int target_floor, int target_direction, int request_time) {
		this.request_type = request_type;
		this.target_direction = target_direction;
		this.target_floor = target_floor;
		this.request_time = request_time;
	}
	
	public Request(String request_type, int target_floor, int request_time) {
		this.request_type = request_type;
		this.target_floor = target_floor;
		this.request_time = request_time;
		target_direction = UP;
	}

	public String getRequest_type() {
		return request_type;
	}

	public int getTarget_direction() {
		return target_direction;
	}

	public int getTarget_floor() {
		return target_floor;
	}

	public int getRequest_time() {
		return request_time;
	}

	

}
