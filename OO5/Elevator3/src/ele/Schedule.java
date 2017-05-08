package ele;

import java.util.TimerTask;

public class Schedule extends TimerTask {

	private Elevator elevator1;
	private Elevator elevator2;
	private Elevator elevator3;
	private int currentTime;
	
	public Schedule() {
		super();
		// TODO Auto-generated constructor stub
		elevator1 = new Elevator(1);
		elevator2 = new Elevator(2);
		elevator3 = new Elevator(3);
		currentTime = -1;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		currentTime += 1;
		
		boolean running1 = (elevator1.getTimer() == 0) ? false : true;
		boolean running2 = (elevator2.getTimer() == 0) ? false : true;
		boolean running3 = (elevator3.getTimer() == 0) ? false : true;
		
		if (!running1 && elevator1.isIsopen() && elevator1.getOpen_times() == 0) {
			System.out.println("(#1, #" + elevator1.getFloor_now() + ", " + elevator1.getState() + ", " + elevator1.getExercise_Amount() + ", " + currentTime + ")");
		}
		if (!running2 && elevator2.isIsopen() && elevator2.getOpen_times() == 0) {
			System.out.println("(#2, #" + elevator2.getFloor_now() + ", " + elevator2.getState() + ", " + elevator2.getExercise_Amount() + ", " + currentTime + ")");
		}
		if (!running3 && elevator3.isIsopen() && elevator3.getOpen_times() == 0) {
			System.out.println("(#3, #" + elevator3.getFloor_now() + ", " + elevator3.getState() + ", " + elevator3.getExercise_Amount() + ", " + currentTime + ")");
		}
		
		for (int i = 0; i < RequestQueue.getSize(); i++) {
			Request r = RequestQueue.getRequest(i);
			int amount = 99999999;
			int choice = 0;
			//ис╢Ь
			if (r.getRequest_type().equals("FR")) {
				if (r.getTarget_direction().equals(elevator1.getState())
						&&((r.getTarget_direction().equals("UP") && r.getTarget_floor() <= elevator1.getTarget_floor() && r.getTarget_floor() > elevator1.getFloor_now()) 
								|| (r.getTarget_direction().equals("DOWN") && r.getTarget_floor() >= elevator1.getTarget_floor() && r.getTarget_floor() < elevator1.getFloor_now()))) {
					amount = elevator1.getExercise_Amount();
					choice = 1;
				}
				if (elevator2.getExercise_Amount() < amount && r.getTarget_direction().equals(elevator2.getState())
						&&((r.getTarget_direction().equals("UP") && r.getTarget_floor() <= elevator2.getTarget_floor() && r.getTarget_floor() > elevator2.getFloor_now()) 
								|| (r.getTarget_direction().equals("DOWN") && r.getTarget_floor() >= elevator2.getTarget_floor() && r.getTarget_floor() < elevator2.getFloor_now()))) {
					amount = elevator2.getExercise_Amount();
					choice = 2;
				}
				if (elevator3.getExercise_Amount() < amount && r.getTarget_direction().equals(elevator3.getState())
						&&((r.getTarget_direction().equals("UP") && r.getTarget_floor() <= elevator3.getTarget_floor() && r.getTarget_floor() > elevator3.getFloor_now()) 
								|| (r.getTarget_direction().equals("DOWN") && r.getTarget_floor() >= elevator3.getTarget_floor() && r.getTarget_floor() < elevator3.getFloor_now()))) {
					amount = elevator3.getExercise_Amount();
					choice = 3;
				}
				if (choice != 0) {
					switch (choice) {
					case 1:
						if (elevator1.getState().equals("UP") && r.getTarget_floor() > elevator1.getTarget_floor()) {
							elevator1.setTarget(r.getTarget_floor());
						}else if (elevator1.getState().equals("DOWN") && r.getTarget_floor() < elevator1.getTarget_floor()) {
							elevator1.setTarget(r.getTarget_floor());
						}
						elevator1.addRequest(r);
						elevator1.setFloor(r.getTarget_floor(), true);
						RequestQueue.removeRequest(i);
						i--;
						break;
					case 2:
						if (elevator2.getState().equals("UP") && r.getTarget_floor() > elevator2.getTarget_floor()) {
							elevator2.setTarget(r.getTarget_floor());
						}else if (elevator2.getState().equals("DOWN") && r.getTarget_floor() < elevator2.getTarget_floor()) {
							elevator2.setTarget(r.getTarget_floor());
						}
						elevator2.addRequest(r);
						elevator2.setFloor(r.getTarget_floor(), true);
						RequestQueue.removeRequest(i);
						i--;
						break;
					case 3:
						if (elevator3.getState().equals("UP") && r.getTarget_floor() > elevator3.getTarget_floor()) {
							elevator3.setTarget(r.getTarget_floor());
						}else if (elevator3.getState().equals("DOWN") && r.getTarget_floor() < elevator3.getTarget_floor()) {
							elevator3.setTarget(r.getTarget_floor());
						}
						elevator3.addRequest(r);
						elevator3.setFloor(r.getTarget_floor(), true);
						RequestQueue.removeRequest(i);
						i--;
						break;
					default:
						break;
					}
				}
			}else if (r.getRequest_type().equals("ER")) {
				int e_num = r.getfrom_Elevator();
				switch (e_num) {
				case 1:
					if ((elevator1.getState().equals("UP") && r.getTarget_floor() > elevator1.getFloor_now()) 
							|| (elevator1.getState().equals("DOWN") && r.getTarget_floor() < elevator1.getFloor_now())) {
						if (elevator1.getState().equals("UP") && r.getTarget_floor() > elevator1.getTarget_floor()) {
							elevator1.setTarget(r.getTarget_floor());
						}else if (elevator1.getState().equals("DOWN") && r.getTarget_floor() < elevator1.getTarget_floor()) {
							elevator1.setTarget(r.getTarget_floor());
						}
						elevator1.addRequest(r);
						elevator1.setFloor(r.getTarget_floor(), true);
						RequestQueue.removeRequest(i);
						i--;
					}
					break;
				case 2:
					if ((elevator2.getState().equals("UP") && r.getTarget_floor() > elevator2.getFloor_now()) 
							|| (elevator2.getState().equals("DOWN") && r.getTarget_floor() < elevator2.getFloor_now())) {
						if (elevator2.getState().equals("UP") && r.getTarget_floor() > elevator2.getTarget_floor()) {
							elevator2.setTarget(r.getTarget_floor());
						}else if (elevator2.getState().equals("DOWN") && r.getTarget_floor() < elevator2.getTarget_floor()) {
							elevator2.setTarget(r.getTarget_floor());
						}
						elevator2.addRequest(r);
						elevator2.setFloor(r.getTarget_floor(), true);
						RequestQueue.removeRequest(i);
						i--;
					}
					break;
				case 3:
					if ((elevator3.getState().equals("UP") && r.getTarget_floor() > elevator3.getFloor_now()) 
							|| (elevator3.getState().equals("DOWN") && r.getTarget_floor() < elevator3.getFloor_now())) {
						if (elevator3.getState().equals("UP") && r.getTarget_floor() > elevator3.getTarget_floor()) {
							elevator3.setTarget(r.getTarget_floor());
						}else if (elevator3.getState().equals("DOWN") && r.getTarget_floor() < elevator3.getTarget_floor()) {
							elevator3.setTarget(r.getTarget_floor());
						}
						elevator3.addRequest(r);
						elevator3.setFloor(r.getTarget_floor(), true);
						RequestQueue.removeRequest(i);
						i--;
					}
					break;
				default:
					break;
				}
			}
		}
		
		if (elevator1.getState().equals("STOP")) {
			for(int i = 0; i< RequestQueue.getSize();i++)
			{
				Request request = RequestQueue.getRequest(i);
				if (request.getRequest_type().equals("FR")) {
					if (request.getTarget_floor() == elevator1.getFloor_now()) {
						elevator1.addRequest(request);
						elevator1.openElevator();
						RequestQueue.removeRequest(i);
						i--;
						break;
					}else {
						elevator1.addRequest(request);
						elevator1.setFloor(request.getTarget_floor(), true);
						elevator1.setTarget(request.getTarget_floor());
						RequestQueue.removeRequest(i);
						i--;
						break;
					}
				}else if (request.getRequest_type().equals("ER") && request.getfrom_Elevator() == 1) {
					if (request.getTarget_floor() == elevator1.getFloor_now()) {
						elevator1.addRequest(request);
						elevator1.openElevator();
						RequestQueue.removeRequest(i);
						i--;
						break;
					}else {
						elevator1.addRequest(request);
						elevator1.setFloor(request.getTarget_floor(), true);
						elevator1.setTarget(request.getTarget_floor());
						RequestQueue.removeRequest(i);
						i--;
						break;
					}
				}
			}
		}
		
		if (elevator2.getState().equals("STOP")) {
			for(int i = 0; i< RequestQueue.getSize();i++)
			{
				Request request = RequestQueue.getRequest(i);
				if (request.getRequest_type().equals("FR")) {
					if (request.getTarget_floor() == elevator2.getFloor_now()) {
						elevator2.addRequest(request);
						elevator2.openElevator();
						RequestQueue.removeRequest(i);
						i--;
						break;
					}else {
						elevator2.addRequest(request);
						elevator2.setFloor(request.getTarget_floor(), true);
						elevator2.setTarget(request.getTarget_floor());
						RequestQueue.removeRequest(i);
						i--;
						break;
					}
				}else if (request.getRequest_type().equals("ER") && request.getfrom_Elevator() == 2) {
					if (request.getTarget_floor() == elevator2.getFloor_now()) {
						elevator2.addRequest(request);
						elevator2.openElevator();
						RequestQueue.removeRequest(i);
						i--;
						break;
					}else {
						elevator2.addRequest(request);
						elevator2.setFloor(request.getTarget_floor(), true);
						elevator2.setTarget(request.getTarget_floor());
						RequestQueue.removeRequest(i);
						i--;
						break;
					}
				}
			}
		}
		
		if (elevator3.getState().equals("STOP")) {
			for(int i = 0; i< RequestQueue.getSize();i++)
			{
				Request request = RequestQueue.getRequest(i);
				if (request.getRequest_type().equals("FR")) {
					if (request.getTarget_floor() == elevator3.getFloor_now()) {
						elevator3.addRequest(request);
						elevator3.openElevator();
						RequestQueue.removeRequest(i);
						i--;
						break;
					}else {
						elevator3.addRequest(request);
						elevator3.setFloor(request.getTarget_floor(), true);
						elevator3.setTarget(request.getTarget_floor());
						RequestQueue.removeRequest(i);
						i--;
						break;
					}
				}else if (request.getRequest_type().equals("ER") && request.getfrom_Elevator() == 3) {
					if (request.getTarget_floor() == elevator3.getFloor_now()) {
						elevator3.addRequest(request);
						elevator3.openElevator();
						RequestQueue.removeRequest(i);
						i--;
						break;
					}else {
						elevator3.addRequest(request);
						elevator3.setFloor(request.getTarget_floor(), true);
						elevator3.setTarget(request.getTarget_floor());
						RequestQueue.removeRequest(i);
						i--;
						break;
					}
				}
			}
		}
		
		elevator1.runElevator();
		elevator2.runElevator();
		elevator3.runElevator();
	}
	


}


//boolean running1 = (elevator1.getTimer() == 0) ? false : true;
//boolean running2 = (elevator2.getTimer() == 0) ? false : true;
//boolean running3 = (elevator3.getTimer() == 0) ? false : true;
//
//
//if (!running1) {
//	
//	int floor = elevator1.getFloor_now();
//	String direction = elevator1.getState();
//	int amout = elevator1.getExercise_Amount();
//	if (elevator1.isIsopen()) {
//		if (elevator1.getOpen_times() == 0) {
//			System.out.println("(#1, #" + floor + ", " + direction + ", " + amout + ", " + currentTime + ")");
//		}
//		for (int i = 0; i < RequestQueue.getSize(); i++) {
//			Request request = RequestQueue.getRequest(i);
//			if (request.getRequest_type().equals("ER")) {
//				if (request.getfrom_Elevator() == 1 && request.getTarget_floor() == floor) {
//					System.out.println("#1 Done: " + request.toString());
//					RequestQueue.removeRequest(i);
//					i--;
//				} 
//				if (request.getfrom_Elevator() == 1 && elevator1.getTarget_floor() == floor && request.getTarget_floor() > elevator1.getTarget_floor()) {
//					elevator1.setTarget(request.getTarget_floor());
//					System.out.println("#1 Done: " + request.toString());
//					RequestQueue.removeRequest(i);
//					i--;
//				}
//			}else if (request.getRequest_type().equals("FR")) {
//				if (request.getTarget_direction().equals(direction) && request.getTarget_floor() == floor) {
//					System.out.println("#1 Done: " + request.toString());
//					RequestQueue.removeRequest(i);
//					i--;
//				}
//			}
//		}
//	}
//	else{
//		for (int i = 0; i < RequestQueue.getSize(); i++) {
//			//System.out.println(elevator1.getTimer()); 
//			Request request = RequestQueue.getRequest(i);
//			if (request.getRequest_type().equals("ER")) {
//				if (request.getfrom_Elevator() == 1 && request.getTarget_floor() == floor) {
//					if (!elevator1.isIsopen()) {
//						elevator1.openElevator();
//						System.out.println("(#1, #" + floor + ", " + direction + ", " + amout + ", " + currentTime + ")");
//					}
//					System.out.println("#1 Done: " + request.toString());
//					RequestQueue.removeRequest(i);
//					i--;
//				}else if (request.getfrom_Elevator() == 1) {
//					if (request.getTarget_floor() > elevator1.getTarget_floor() && elevator1.getFloor_now() == elevator1.getTarget_floor()) {
//						elevator1.setTarget(request.getTarget_floor());
//						System.out.println("#1 Done: " + request.toString());
//						RequestQueue.removeRequest(i);
//						i--;
//					}else if (request.getTarget_floor() < elevator1.getTarget_floor() && elevator1.getFloor_now() == elevator1.getTarget_floor()) {
//						elevator1.setTarget(request.getTarget_floor());
//						System.out.println("#1 Done: " + request.toString());
//						RequestQueue.removeRequest(i);
//						i--;
//					}
//				}
//			}
//		}
//	}
//}
//if (!running2) {
//	int floor = elevator2.getFloor_now();
//	String direction = elevator2.getState();
//	int amout = elevator2.getExercise_Amount();
//	if (elevator2.isIsopen()) {
//		if (elevator2.getOpen_times() == 0) {
//			System.out.println("(#2, #" + floor + ", " + direction + ", " + amout + ", " + currentTime + ")");
//		}
//		for (int i = 0; i < RequestQueue.getSize(); i++) {
//			Request request = RequestQueue.getRequest(i);
//			if (request.getRequest_type().equals("ER")) {
//				if (request.getfrom_Elevator() == 2 && request.getTarget_floor() == floor) {
//					System.out.println("#2 Done: " + request.toString());
//					RequestQueue.removeRequest(i);
//					i--;
//				}
//				if (request.getfrom_Elevator() == 2 && elevator2.getTarget_floor() == floor && request.getTarget_floor() > elevator2.getTarget_floor()) {
//					elevator2.setTarget(request.getTarget_floor());
//					System.out.println("#2 Done: " + request.toString());
//					RequestQueue.removeRequest(i);
//					i--;
//				}
//			}else if (request.getRequest_type().equals("FR")) {
//				if (request.getTarget_direction().equals(direction) && request.getTarget_floor() == floor) {
//					System.out.println("#2 Done: " + request.toString());
//					RequestQueue.removeRequest(i);
//					i--;
//				}
//			}
//		}
//	}else{
//		for (int i = 0; i < RequestQueue.getSize(); i++) {
//			Request request = RequestQueue.getRequest(i);
//			if (request.getRequest_type().equals("ER")) {
//				if (request.getfrom_Elevator() == 2 && request.getTarget_floor() == floor) {
//					if (!elevator2.isIsopen()) {
//						elevator2.openElevator();
//						System.out.println("(#2, #" + floor + ", " + direction + ", " + amout + ", " + currentTime + ")");
//					}
//					System.out.println("#2 Done: " + request.toString());
//					RequestQueue.removeRequest(i);
//					i--;
//				}else if (request.getfrom_Elevator() == 2) {
//					if (request.getTarget_floor() > elevator2.getTarget_floor() && elevator2.getFloor_now() == elevator2.getTarget_floor()) {
//						elevator2.setTarget(request.getTarget_floor());
//						System.out.println("#2 Done: " + request.toString());
//						RequestQueue.removeRequest(i);
//						i--;
//					}else if (request.getTarget_floor() < elevator2.getTarget_floor() && elevator2.getFloor_now() == elevator2.getTarget_floor()) {
//						elevator2.setTarget(request.getTarget_floor());
//						System.out.println("#2 Done: " + request.toString());
//						RequestQueue.removeRequest(i);
//						i--;
//					}
//				}
//			}
//		}
//	}
//}
//if (!running3) {
//	int floor = elevator3.getFloor_now();
//	String direction = elevator3.getState();
//	int amout = elevator3.getExercise_Amount();
//	if (elevator3.isIsopen()) {
//		if (elevator3.getOpen_times() == 0) {
//			System.out.println("(#3, #" + floor + ", " + direction + ", " + amout + ", " + currentTime + ")");
//		}
//		for (int i = 0; i < RequestQueue.getSize(); i++) {
//			Request request = RequestQueue.getRequest(i);
//			if (request.getRequest_type().equals("ER")) {
//				if (request.getfrom_Elevator() == 3 && request.getTarget_floor() == floor) {
//					System.out.println("#3 Done: " + request.toString());
//					RequestQueue.removeRequest(i);
//					i--;
//				}
//				if (request.getfrom_Elevator() == 3 && elevator3.getTarget_floor() == floor && request.getTarget_floor() > elevator3.getTarget_floor()) {
//					elevator3.setTarget(request.getTarget_floor());
//					System.out.println("#3 Done: " + request.toString());
//					RequestQueue.removeRequest(i);
//					i--;
//				}
//			}
//			else if (request.getRequest_type().equals("FR")) {
//				if (request.getTarget_direction().equals(direction) && request.getTarget_floor() == floor) {
//					System.out.println("#3 Done: " + request.toString());
//					RequestQueue.removeRequest(i);
//					i--;
//				}
//			}
//		}
//	}else{
//		for (int i = 0; i < RequestQueue.getSize(); i++) {
//			Request request = RequestQueue.getRequest(i);
//			if (request.getRequest_type().equals("ER")) {
//				if (request.getfrom_Elevator() == 3 && request.getTarget_floor() == floor) {
//					if (!elevator3.isIsopen()) {
//						elevator3.openElevator();
//						System.out.println("(#3, #" + floor + ", " + direction + ", " + amout + ", " + currentTime + ")");
//					}
//					System.out.println("#3 Done: " + request.toString());
//					RequestQueue.removeRequest(i);
//					i--;
//				}else if (request.getfrom_Elevator() == 3) {
//					if (request.getTarget_floor() > elevator3.getTarget_floor() && elevator3.getFloor_now() == elevator3.getTarget_floor()) {
//						elevator3.setTarget(request.getTarget_floor());
//						System.out.println("#3 Done: " + request.toString());
//						RequestQueue.removeRequest(i);
//						i--;
//					}else if (request.getTarget_floor() < elevator3.getTarget_floor() &&elevator3.getFloor_now() == elevator3.getTarget_floor()) {
//						elevator3.setTarget(request.getTarget_floor());
//						System.out.println("#3 Done: " + request.toString());
//						RequestQueue.removeRequest(i);
//						i--;
//					}
//				}
//			}
//		}
//	}
//}
//
//for (int i = 0; i < RequestQueue.getSize(); i++) {
//	Request request = RequestQueue.getRequest(i);
//	if (request.getRequest_type().equals("FR")) {
//		int floor = request.getTarget_floor();
//		int amount = 99999999;
//		int choose = 0;
//		if (!running1 && elevator1.getState().equals("STOP") && !elevator1.isIsopen()) {
//			amount = elevator1.getExercise_Amount();
//			choose = 1;
//		}
//		if (!running2 && elevator2.getState().equals("STOP") && amount > elevator2.getExercise_Amount() && !elevator2.isIsopen()) {
//			amount = elevator2.getExercise_Amount();
//			choose = 2;
//		}
//		if (!running3 && elevator3.getState().equals("STOP") && amount > elevator3.getExercise_Amount() && !elevator3.isIsopen()) {
//			amount = elevator3.getExercise_Amount();
//			choose = 3;
//		}
//		if (choose != 0) {
//			//System.out.println("aaa");
//			switch (choose) {
//			case 1:
//				if (floor != elevator1.getFloor_now()) {
//					//System.out.println(floor);
//					elevator1.setTarget(floor);
//				}else {
//					elevator1.openElevator();
//					elevator1.setState(request.getTarget_direction());
//					System.out.println("(#1, #" + floor + ", " + elevator1.getState() + ", " + elevator1.getExercise_Amount() + ", " + currentTime + ")");
//				}
//				System.out.println("#1 Done: " + request.toString());
//				RequestQueue.removeRequest(i);
//				i--;
//				break;
//			case 2:
//				if (floor != elevator2.getFloor_now()) {
//					//System.out.println(floor);
//					elevator2.setTarget(floor);
//				}else {
//					elevator2.openElevator();
//					elevator2.setState(request.getTarget_direction());
//					System.out.println("(#2, #" + floor + ", " + elevator2.getState() + ", " + elevator2.getExercise_Amount() + ", " + currentTime + ")");
//				}
//				System.out.println("#2 Done: " + request.toString());
//				RequestQueue.removeRequest(i);
//				i--;
//				break;
//			case 3:
//				if (floor != elevator3.getFloor_now()) {
//					//System.out.println(floor);
//					elevator3.setTarget(floor);
//				}else {
//					elevator3.openElevator();
//					elevator3.setState(request.getTarget_direction());
//					System.out.println("(#3, #" + floor + ", " + elevator3.getState() + ", " + elevator3.getExercise_Amount() + ", " + currentTime + ")");
//				}
//				System.out.println("#3 Done: " + request.toString());
//				RequestQueue.removeRequest(i);
//				i--;
//				break;
//				
//			default:
//				break;
//			}
//		}
//	}
//}
