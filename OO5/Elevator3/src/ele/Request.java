package ele;

public class Request {

	private String request_type;
	private String target_direction;
	private int target_floor;
	private int request_time;
	private int from_Elevator;
	
	//private static final String UP = "UP";
	//private static final int DOWN = 1;
	private static final String STOP = "STOP";
	
	//FR
	public Request(String request_type,  int target_floor, String target_direction, int request_time) {
		this.request_type = request_type;
		this.target_direction = target_direction;
		this.target_floor = target_floor;
		this.request_time = request_time;
		from_Elevator = -1;
	}
	
	//ER
	public Request(String request_type, int target_floor, int from_Elevator, int request_time) {
		this.request_type = request_type;
		this.target_floor = target_floor;
		this.request_time = request_time;
		this.from_Elevator = from_Elevator;
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
				&& request.target_floor == this.target_floor && request.from_Elevator == this.from_Elevator) {
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
	
	public int getfrom_Elevator() {
		return from_Elevator;
	}
	
	@Override
	public String toString(){
		if (request_type.equals("FR")) {
			return "(FR," + target_floor + "," + target_direction + ")";
		}else{
			return "(ER," + " #"+ from_Elevator+ ", " + target_floor + ")";
		}
	}

}
