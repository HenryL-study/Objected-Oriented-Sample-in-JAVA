package oo2;

public class Schedule {
	
	private Elevator elevator = new Elevator();
	
	public Schedule() {
		// TODO Auto-generated constructor stub
	}
	
	public void sche(Request request) throws ElevatorException {
		 if (request.getRequest_type().equals("FR")) {
			 if (request.getRequest_time() > elevator.getTime_now()) {
				elevator.setTime_now(request.getRequest_time());
			}
			elevator.runElevator(request.getTarget_floor(), request.getTarget_direction());
		 }
		 else{
			 if (request.getRequest_time() > elevator.getTime_now()) {
					elevator.setTime_now(request.getRequest_time());
				}
			 elevator.runElevator(request.getTarget_floor());
		 }
	} 
	
}
