package ele;

//import java.util.Collection;
//import java.util.LinkedList;
import java.util.Vector;



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

	private static Vector<Request> request = new Vector<Request>();
	//private int latest_time;
	private static final String UP = "UP";
	private static final String DOWN = "DOWN";
	
	public RequestQueue() {
		//latest_time = 0;
	}

	public static boolean addQueue (String str, int currentTime) throws ElevatorException{
		// TODO Auto-generated constructor stub
		if (str.equals("STOP")) {
			return false;
		}
		str = str.replaceAll("\\s", "");
		if (str.length() > 1000000) {
			str = str.substring(0, 100);
			System.out.println("This command is too loog and it will be ignored. The command is (" +  str + "...");
			return false;
		}
		if (!str.startsWith("(") || !str.endsWith(")")) {
			str = str.substring(0, str.length()%100);
			System.out.println("This command has format error and it will be ignored. The command is " +  str + "...");
			return false;
		}
		
		str = str.substring(1,str.length()-1);
		if (str.endsWith(",")) {
			str = str.substring(0, str.length()%100);
			System.out.println("This command has format error and it will be ignored. The command is (" +  str + "...");
			return false;
		}
		String[] strs = str.split(",");
		if ((strs.length != 3) || (!strs[0].equals("ER") && !strs[0].equals("FR")) || strs[1].length() == 0 || strs[2].length() == 0) {
			str = str.substring(0, str.length()%100);
			System.out.println("This command has format error and it will be ignored. The command is (" +  str + "...");
			return false;
		}
		if (strs[0].equals("ER")) {
			int floor;
			int elevatorNum;
			strs[1] = strs[1].substring(1,strs[1].length());
			try {
				elevatorNum = Integer.parseInt(strs[1]);
				floor = Integer.parseInt(strs[2]);
			} catch (Exception e) {
				// TODO: handle exception
				str = str.substring(0, str.length()%100);
				System.out.println("This command has format error and it will be ignored. The command is (" +  str + "...");
				return false;
			}
			if (floor > 20 || floor <1 || elevatorNum < 1 || elevatorNum > 3) {
				str = str.substring(0, str.length()%100);
				System.out.println("This command has format error and it will be ignored. The command is (" +  str + "...");
				return false;
			}
			
			if(!request.add(new Request(strs[0], floor, elevatorNum, currentTime))){
				throw new ElevatorException("The queue is full.");
			}
		}
		else if (strs[0].equals("FR")) {
			int floor;
			try {
				floor = Integer.parseInt(strs[1]);
			} catch (Exception e) {
				str = str.substring(0, str.length()%100);
				System.out.println("This command has format error and it will be ignored. The command is (" +  str + "...");
				return false;
			}
			String direction;
			if ((!strs[2].equals("UP") && !strs[2].equals("DOWN")) || floor > 20 || floor <1 || (floor == 20 && strs[2].equals("UP") ) || (floor == 1 && strs[2].equals("DOWN"))) {
				str = str.substring(0, str.length()%100);
				System.out.println("This command has format error and it will be ignored. The command is (" +  str + "...");
				return false;
			}else if (strs[2].equals("UP")) {
				direction = UP;
			}else {
				direction = DOWN;
			}
			if(!request.add(new Request(strs[0], floor, direction, currentTime))){
				throw new ElevatorException("The queue is full.");
			}
		}
		return true;
	}

//	public static Request sendRequest() {
//		return request.poll();
//	}
	public static Request getRequest(int i) {
		return request.get(i);
	}
	public static void removeRequest(int i) {
		request.remove(i);
	}
	public static int getSize() {
		return request.size();
	}
	public static void mergeRequest() {
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
}
