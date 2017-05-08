package oo2;

import java.util.ArrayList;

public class ASL_Schedule extends Schedule {
	
	/*
	 * @Overview 
	 * 		Schedule the elevator by a little smart way.
	 * @Process Specifications
	 * 		see below
	 * @Indicated Object
	 * 		see below
	 * @Abstract Function
	 * 		AF(c) = (elevator, requestQueue, mainRequest), where elevator = c.elevator, requestQueue = c.requestQueue  mainRequest = c.mainRequest
	 * @Invariance
	 * 		elevator != null && requestQueue !=null
	 */
	
	private Request mainRequest;
	private boolean hasmain;
//	private Floor floor;
	boolean firstrun;
	private static final String FR = "FR";
	private static final String ER = "ER";
	private static final String UP = "UP";
	private static final String DOWN = "DOWN";
	private static final String STOP = "STOP";

	public ASL_Schedule() {
		/*
		 * Requires: Nothing.
		 * Modifies: this.
		 * Effects: Initialize the scheduler.
		 */
		super();
		hasmain = false;
		firstrun = true;
		mainRequest = null;
//		floor = new Floor();
	}
	
	@Override
	public void sche(Request request){
		/*
		 * Requires: Nothing.
		 * Modifies: this.
		 * Effects: Schedule once..
		 */
		 if (firstrun) {
			elevator.runElevator_first(request.getTarget_floor());
			firstrun = false;
		 }
		 else{
			 elevator.runElevator(request.getTarget_floor());
		 }
	}
	
	@Override
	public void startSchedule() {
		/*
		 * Requires: Nothing.
		 * Modifies: elevator.
		 * Effects: start schedule.
		 */
		Request request;
		double max_time;
		String mainDirection;
		if (requestQueue.getSize() == 0) {
			System.out.println("Please input requests.");
			return;
		}
		requestQueue.mergeRequest();
		if (requestQueue.getRequest(0).getRequest_time() != 0) {
			requestQueue.clean();
			System.out.println("初始时间必须为0。");
			return;
		}
		ArrayList<Request> incidentalRequest = new ArrayList<Request>();
		while (requestQueue.getSize() != 0 || !incidentalRequest.isEmpty() || hasmain) {
			if (!hasmain) {
				mainRequest = requestQueue.sendRequest();
				hasmain = true;
			}
			//find request
			Floor floor = new Floor();
			if (mainRequest.getRequest_time() > elevator.getTime_now() ) {
				elevator.setTime_now(mainRequest.getRequest_time());
				firstrun = true;
			}else if ((mainRequest.getRequest_time() == elevator.getTime_now()-0.5) && (mainRequest.getTarget_floor() == elevator.getFloor_now())) {
				elevator.setTime_now(elevator.getTime_now() + 1);
			}
			if (mainRequest.getTarget_floor() > elevator.getFloor_now()) {
				mainDirection = UP;
			}else if (mainRequest.getTarget_floor() < elevator.getFloor_now()) {
				mainDirection = DOWN;
			}else {
				mainDirection = STOP;
			}
			max_time = elevator.getTime_now() + Math.abs(mainRequest.getTarget_floor() - elevator.getFloor_now()) * 0.5;
			int floor_now;
			if (mainDirection == UP) {
				floor_now = elevator.getFloor_now() + 1;
			}else {
				floor_now = elevator.getFloor_now() - 1;
			}
			 
			for (double i = elevator.getTime_now()+0.5; i <= max_time; i = i + 0.5) {
				if (requestQueue.getSize() == 0) {
					break;
				}
				for (int j = 0; j < requestQueue.getSize(); j++) {
					request = requestQueue.getRequest(j);
					if (request.getRequest_time() > i || (request.getRequest_time() == i && request.getTarget_floor() == floor_now && !floor.getFloor(floor_now))) {
						continue;
					}
					if (request.getRequest_time() == i && request.getTarget_floor() == floor_now && floor.getFloor(floor_now)) {
						incidentalRequest.add(requestQueue.getRequest(j));
						requestQueue.removeRequest(j);
						j--;
						continue;
					}
//					if (request.getRequest_time() == i) {
//						if (request.getTarget_floor() == floor_now) {
//							if (floor.getFloor(floor_now)) {
//								incidentalRequest.add(requestQueue.getRequest(j));
//								requestQueue.removeRequest(j);
//								j--;
//								continue;
//							}else{
//								System.out.println("??");
//							}
//						}
//					}
					if ((request.getRequest_type().equals(FR)) && ((request.getTarget_direction().equals(mainDirection))
							&& ((request.getTarget_direction().equals(UP) && request.getTarget_floor() <= mainRequest.getTarget_floor() && request.getTarget_floor() > floor_now)
									||(request.getTarget_direction().equals(DOWN) && request.getTarget_floor() >= mainRequest.getTarget_floor() && request.getTarget_floor() < floor_now)))) {
						incidentalRequest.add(requestQueue.getRequest(j));
						requestQueue.removeRequest(j);
						j--;
						floor.setFloor(request.getTarget_floor(), true);
					}else if (request.getRequest_type().equals(ER) && 
							((mainDirection.equals(UP) && request.getTarget_floor() >= floor_now)) 
							|| (mainDirection.equals(DOWN) && request.getTarget_floor() <= floor_now)
							) {
						incidentalRequest.add(requestQueue.getRequest(j));
						requestQueue.removeRequest(j);
						j--;
						floor.setFloor(request.getTarget_floor(), true);
					}
				}
				if (floor.getFloor(floor_now)) {
					i++;
					max_time++;
				}
				if (mainDirection == UP) {
					floor_now++;
				}else {
					floor_now--;
				}
			}
			// OUTPUT incidentalRequest
			System.out.print(mainRequest.toString() + "( ");
			for (int i = 0; i < incidentalRequest.size(); i++) {
				System.out.print(incidentalRequest.get(i).toString() + " ");
			}
			System.out.println(")");
			if (mainDirection == UP) {
				floor_now = elevator.getFloor_now() + 1;
			}else {
				floor_now = elevator.getFloor_now() - 1;
			}
			//int from = elevator.getFloor_now();
			int to = mainRequest.getTarget_floor();
			for ( ;floor_now<=to;floor_now++){
				for (int i = 0; i < incidentalRequest.size(); i++) {
					if (incidentalRequest.get(i).getTarget_floor() == floor_now) {
						sche(incidentalRequest.get(i));
						incidentalRequest.remove(i);
						i--;
					}
				}
			}
				sche(mainRequest);
			
			if (incidentalRequest.isEmpty()) {
				mainRequest = null;
				hasmain = false;
			}else{
				mainRequest = incidentalRequest.get(0);
				incidentalRequest.remove(0);
				hasmain = true;
			}
		}
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
