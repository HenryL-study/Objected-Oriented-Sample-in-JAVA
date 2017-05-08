package oo2;

public class Request {
	
	/*
	 * @Overview 
	 * 		stimulate the request.
	 * @Process Specifications
	 * 		see below
	 * @Indicated Object
	 * 		see below
	 * @Abstract Function
	 * 		AF(c) = (request_type, target_direction, floor), where request_type = c.request_type, target_direction = c.target_direction, 
	 * target_floor = c,target_floor, request_time = c.request_time, 
	 * @Invariance
	 * 		request_type != null && target_direction != null  && 0<target_floor<11 && request_time >= 0
	 */

	private String request_type;
	private String target_direction;
	private int target_floor;
	private int request_time;
	
	private static final String STOP = "STOP";
	
	public Request(String request_type,  int target_floor, String target_direction, int request_time) {
		/*
		 * Requires: request_type is ER or FR, 1<=target_floor<=10, .target_direction is UP or DOWN, request_time >= 0
		 * Modifies: this.
		 * Effects: Initialize the Request
		 */
		this.request_type = request_type;
		this.target_direction = target_direction;
		this.target_floor = target_floor;
		this.request_time = request_time;
	}
	
	public Request(String request_type, int target_floor, int request_time) {
		/*
		 * Requires: request_type is ER or FR, 1<=target_floor<=10,  request_time >= 0.
		 * Modifies: this.
		 * Effects: Initialize the Request
		 */
		this.request_type = request_type;
		this.target_floor = target_floor;
		this.request_time = request_time;
		target_direction = STOP;
	}

	public String getRequest_type() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: get the request_type.
		 */
		return request_type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: return the two things whether it is equal.
		 */
		// TODO Auto-generated method stub
		Request request = (Request)obj;
		if (request.request_type.equals(this.request_type) && request.request_time == this.request_time && request.target_direction.equals(this.target_direction)
				&& request.target_floor == this.target_floor) {
			return true;
		}else {
			return false;
		}
	}

	public String getTarget_direction() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: get the target_direction.
		 */
		return target_direction;
	}

	public int getTarget_floor() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: get the target_floor.
		 */
		return target_floor;
	}

	public int getRequest_time() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: get the request_time.
		 */
		return request_time;
	}
	
	@Override
	public String toString(){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: get the Request¡®s information..
		 */
		if (request_type.equals("FR")) {
			return "(FR," + target_floor + "," + target_direction + "," + request_time + ")";
		}else{
			return "(ER," + target_floor + "," + request_time + ")";
		}
	}
	
	public boolean repOK() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the true if the rep variant holds for this. otherwise return false.
		 */
		if (target_direction != null && request_time>=0 && (0<target_floor || target_floor<11) && request_type != null) {
			return true;
		}
		return false;
	}

}
