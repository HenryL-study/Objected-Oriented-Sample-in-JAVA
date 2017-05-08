package oo2;

public class Schedule {
	
	/*
	 * @Overview 
	 * 		Schedule the elevator.
	 * @Process Specifications
	 * 		see below
	 * @Indicated Object
	 * 		see below
	 * @Abstract Function
	 * 		AF(c) = (elevator, requestQueue), where elevator = c.elevator, requestQueue = c.requestQueue
	 * @Invariance
	 * 		elevator != null && requestQueue !=null
	 */

	protected Elevator elevator;
	protected RequestQueue requestQueue;
	
	public Schedule() {
		/*
		 * Requires: Nothing.
		 * Modifies: this.
		 * Effects: Initialize the scheduler.
		 */
		elevator = new Elevator();
		requestQueue = new RequestQueue();
	}
	
	public void sche(Request request){
		/*
		 * Requires: request is not null and appropriate.
		 * Modifies: elevator.
		 * Effects: Schedule once..
		 */
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
		/*
		 * Requires: Nothing.
		 * Modifies: requestQueue.
		 * Effects: Add a request into requestQuene.
		 */
		requestQueue.addQueue(str);
	}
	
	public void startSchedule() {
		/*
		 * Requires: Nothing.
		 * Modifies: requestQueue.
		 * Effects: Schedule once..
		 */
		Request request;
		while ((request = requestQueue.sendRequest())!=null) {
				this.sche(request);
		}
	}
	
	public Elevator gElevator(){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: get the elevator.
		 */
		return this.elevator;
	}
	
	public boolean repOK() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the true if the rep variant holds for this. otherwise return false.
		 */
		if (elevator != null && requestQueue != null) {
			return true;
		}
		return false;
	}
	
}
