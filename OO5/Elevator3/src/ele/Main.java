package ele;

import java.util.Timer; 


/*
(ER,#1,4)
(ER,#1,3)
(ER,#1,2)
(ER,#2,4)
(ER,#2,3)
(ER,#2,2)
(ER,#3,4)
(ER,#3,3)
(ER,#3,2)
 * 
 */


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Request_monitor request_monitor = new Request_monitor();
		Schedule schedule = new Schedule();
		Thread rm = new Thread(request_monitor);
		Timer timer = new Timer(true);
		rm.start();
		timer.schedule(schedule, 0, 100);
		
	}

}
