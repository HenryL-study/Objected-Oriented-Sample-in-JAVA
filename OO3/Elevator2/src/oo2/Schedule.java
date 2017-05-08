package oo2;

public class Schedule {

	protected Elevator elevator;
	protected RequestQueue requestQueue;
	
	public Schedule() {
		// TODO Auto-generated constructor stub
		elevator = new Elevator();
		requestQueue = new RequestQueue();
	}
	
	public void sche(Request request) throws ElevatorException {
		 if (request.getRequest_type().equals("FR")) {
			 if (request.getRequest_time() > elevator.getTime_now()) {
				elevator.setTime_now(request.getRequest_time());
			}
			elevator.runElevator(request.getTarget_floor());
		 }
		 else{
			 if (request.getRequest_time() > elevator.getTime_now()) {
					elevator.setTime_now(request.getRequest_time());
				}
			 elevator.runElevator(request.getTarget_floor());
		 }
	} 
	
	public void addQueue(String str) throws ElevatorException {
		requestQueue.addQueue(str);
	}
	
	public void startSchedule() {
		Request request;
		while ((request = requestQueue.sendRequest())!=null) {
			try {
				this.sche(request);
			} catch (ElevatorException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				return;
			}
		}
	}
	
}
