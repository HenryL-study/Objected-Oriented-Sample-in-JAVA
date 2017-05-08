package oo2;

import java.util.LinkedList;


class ElevatorException extends Exception  
{  
    /**
	 * 
	 */
	private static final long serialVersionUID = -6885933568946727341L;

	public ElevatorException(String msg)  
    {  
        super(msg);  
    }  
}  


public class RequestQueue{
	/*
	 * @Overview 
	 * 		stimulate the queue of request.
	 * @Process Specifications
	 * 		see below
	 * @Indicated Object
	 * 		see below
	 * @Abstract Function
	 * 		AF(c) = (request queue, latest_time), where request queue = c.request, latest_time = c.latest_time
	 * @Invariance
	 * 		request != null && latest_time>=0
	 */
	private LinkedList<Request> request = new LinkedList<Request>();
	private int latest_time;
	private static final String UP = "UP";
	private static final String DOWN = "DOWN";
	
	public RequestQueue() {
		/*
		 * Requires: Nothing.
		 * Modifies: this.
		 * Effects: Initialize the RequestQueue
		 */
		latest_time = 0;
	}

	public void addQueue (String str) throws ElevatorException{
		/*
		 * Requires: str != null.
		 * Modifies: this.
		 * Effects: Add a request into the RequestQueue
		 */
		// TODO Auto-generated constructor stub
		str = str.replaceAll("\\s", "");
		if (str.length() > 1000000) {
			str = str.substring(0, 100);
			System.out.println("This command is too loog and it will be ignored. The command is (" +  str + "...");
			return;
		}
		if (!str.startsWith("(") || !str.endsWith(")")) {
			str = str.substring(0, str.length()%100);
			System.out.println("This command has format error and it will be ignored. The command is " +  str + "...");
			return;
		}
		
		str = str.substring(1,str.length()-1);
		if (str.endsWith(",")) {
			str = str.substring(0, str.length()%100);
			System.out.println("This command has format error and it will be ignored. The command is (" +  str + "...");
			return;
		}
		String[] strs = str.split(",");
		if ((strs.length != 3 && strs.length !=4) || (strs.length == 3 && !strs[0].equals("ER")) || (strs.length == 4 && !strs[0].equals("FR"))) {
			str = str.substring(0, str.length()%100);
			System.out.println("This command has format error and it will be ignored. The command is (" +  str + "...");
			return;
		}
		if (strs.length == 3) {
			int floor;
			int time;
			try {
				floor = Integer.parseInt(strs[1]);
				time = Integer.parseInt(strs[2]);
			} catch (Exception e) {
				// TODO: handle exception
				str = str.substring(0, str.length()%100);
				System.out.println("This command has format error and it will be ignored. The command is (" +  str + "...");
				return;
			}
			if (floor > 10 || floor <1 || time < 0) {
				str = str.substring(0, str.length()%100);
				System.out.println("This command has format error and it will be ignored. The command is (" +  str + "...");
				return;
			}
			if (latest_time > time) {
				throw new ElevatorException("Time Wrong.");
			}
			
			if(!request.offer(new Request(strs[0], floor, time))){
				throw new ElevatorException("The queue is full.");
			}
			latest_time = time;
		}
		else if (strs.length == 4) {
			int floor;
			int time;
			try {
				floor = Integer.parseInt(strs[1]);
				time = Integer.parseInt(strs[3]);
			} catch (Exception e) {
				str = str.substring(0, str.length()%100);
				System.out.println("This command has format error and it will be ignored. The command is (" +  str + "...");
				return;
			}
			String direction;
			if ((!strs[2].equals("UP") && !strs[2].equals("DOWN")) || floor > 10 || floor <1 || (floor == 10 && strs[2].equals("UP") ) || (floor == 1 && strs[2].equals("DOWN")) || time < 0) {
				str = str.substring(0, str.length()%100);
				System.out.println("This command has format error and it will be ignored. The command is (" +  str + "...");
				return;
			}else if (strs[2].equals("UP")) {
				direction = UP;
			}else {
				direction = DOWN;
			}
			if (latest_time > time) {
				throw new ElevatorException("Time Wrong.");
			}
			if(!request.offer(new Request(strs[0], floor, direction, time))){
				throw new ElevatorException("The queue is full.");
			}
			latest_time = time;
		}
		
	}

	public Request sendRequest() {
		/*
		 * Requires: Nothing.
		 * Modifies: this.
		 * Effects: Send a request.
		 */
		return request.poll();
	}
	public Request getRequest(int i) {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Get the ith request.
		 */
		return request.get(i);
	}
	public void removeRequest(int i) {
		/*
		 * Requires: Nothing.
		 * Modifies: this.
		 * Effects: Remove the ith request.
		 */
		request.remove(i);
	}
	public int getSize() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Get the request's size.
		 */
		return request.size();
	}
	public void mergeRequest() {
		/*
		 * Requires: Nothing.
		 * Modifies: this.
		 * Effects: Merge the same request.
		 */
		for (int i = 0; i < request.size(); i++) {
			Request re = request.get(i);
			for (int j = i+1; j < request.size(); j++) {
				if (re.equals(request.get(j))) {
					request.remove(j);
					j--;
				}
			}
		}
	}
	
	public void clean() {
		/*
		 * Requires: Nothing.
		 * Modifies: this.
		 * Effects: Remove all the request.
		 */
		request.removeAll(request);
	}
}
