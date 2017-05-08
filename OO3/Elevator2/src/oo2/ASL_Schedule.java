package oo2;

import java.util.ArrayList;

public class ASL_Schedule extends Schedule {
	
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
		// TODO Auto-generated constructor stub
		super();
		hasmain = false;
		firstrun = true;
		mainRequest = null;
//		floor = new Floor();
	}
	
	@Override
	public void sche(Request request) throws ElevatorException {
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
		Request request;
		double max_time;
		String mainDirection;
		if (requestQueue.getSize() == 0) {
			System.out.println("Please input requests.");
			return;
		}
		requestQueue.mergeRequest();
		if (requestQueue.getRequest(0).getRequest_time() != 0) {
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
					if ((request.getRequest_type().equals(FR)) && ((request.getTarget_direction().equals(mainDirection))
							&& ((request.getTarget_direction().equals(UP) && request.getTarget_floor() <= mainRequest.getTarget_floor() && request.getTarget_floor() > floor_now)
									||(request.getTarget_direction().equals(DOWN) && request.getTarget_floor() >= mainRequest.getTarget_floor() && request.getTarget_floor() < floor_now)))) {
						incidentalRequest.add(requestQueue.getRequest(j));
						requestQueue.removeRequest(j);
						j--;
						floor.setFloor(request.getTarget_floor(), true);
					}else if (request.getRequest_type().equals(ER) && 
							((mainDirection.equals(UP) && request.getTarget_floor() > floor_now)) 
							|| (mainDirection.equals(DOWN) && request.getTarget_floor() < floor_now)
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
			//OUTPUT elevator
//			for (int i = 0; i < incidentalRequest.size(); i++) {
//				if (incidentalRequest.get(i).getRequest_type().equals(ER) && incidentalRequest.get(i).getTarget_floor() > mainRequest.getTarget_floor()){
//					break;
//				}
//				try {
//					sche(incidentalRequest.get(i));
//					incidentalRequest.remove(i);
//					i--;
//				} catch (ElevatorException e) {
//					// TODO Auto-generated catch block
//					System.out.println(e.getMessage());
//					return;
//				}
//			}
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
						try {
							sche(incidentalRequest.get(i));
							incidentalRequest.remove(i);
							i--;
						} catch (ElevatorException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
			
			try {
				sche(mainRequest);
			} catch (ElevatorException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				return;
			}
			if (incidentalRequest.isEmpty()) {
				mainRequest = null;
				hasmain = false;
			}else{
				mainRequest = incidentalRequest.get(0);
				incidentalRequest.remove(0);
				hasmain = true;
			}
//			if (incidentalRequest.size() == 0) {
//				try {
//					sche(mainRequest);
//					hasmain = false;
//					mainRequest = null;
//					continue;
//				} catch (ElevatorException e) {
//					// TODO Auto-generated catch block
//					System.out.println(e.getMessage());
//					return;
//				}
//			}
//			request = incidentalRequest.get(incidentalRequest.size()-1);
//			if (request.getRequest_type().equals(ER) && request.getTarget_floor() > mainRequest.getTarget_floor()) {
//				try {
//					sche(mainRequest);
//				} catch (ElevatorException e) {
//					// TODO Auto-generated catch block
//					System.out.println(e.getMessage());
//					return;
//				}
//				mainRequest = request;
//			}else {
//				try {
//					sche(request);
//					sche(mainRequest);
//				} catch (ElevatorException e) {
//					// TODO Auto-generated catch block
//					System.out.println(e.getMessage());
//					return;
//				}
//				hasmain = false;
//				mainRequest = null;
//			}
		}
	}

}
