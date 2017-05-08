package oo2;

public class Request {

	private String request_type;
	private String target_direction;
	private int target_floor;
	private int request_time;
	
	//private static final String UP = "UP";
	//private static final int DOWN = 1;
	private static final String STOP = "STOP";
	
	public Request(String request_type,  int target_floor, String target_direction, int request_time) {
		this.request_type = request_type;
		this.target_direction = target_direction;
		this.target_floor = target_floor;
		this.request_time = request_time;
	}
	
	public Request(String request_type, int target_floor, int request_time) {
		this.request_type = request_type;
		this.target_floor = target_floor;
		this.request_time = request_time;
		target_direction = STOP;
	}

	public String getRequest_type() {
		return request_type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
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
		return target_direction;
	}

	public int getTarget_floor() {
		return target_floor;
	}

	public int getRequest_time() {
		return request_time;
	}
	
	@Override
	public String toString(){
		if (request_type.equals("FR")) {
			return "(FR," + target_floor + "," + target_direction + "," + request_time + ")";
		}else{
			return "(ER," + target_floor + "," + request_time + ")";
		}
	}

}
