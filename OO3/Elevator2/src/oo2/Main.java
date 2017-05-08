package oo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 
 * @author Nonsense
 *1.
 *(FR,3,DOWN,0)
 *(FR,1,UP,1)
 *(ER,1,2)
 *(ER,1,4)
 */

public class Main {
	
	private static final int MAX_REQUESTS = 200;
	
	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String [] read = new String[MAX_REQUESTS+1];
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in ));
		int num = 0;
		ASL_Schedule schedule = new ASL_Schedule();
		try {
			 do{
					try {
						read[num] = br.readLine();
					  }catch (IOException e) {
						  e.printStackTrace();
						  return;
					  }
					if (read[num] == null) {
						System.out.println("Please input something.");
						return;
					}
					num++;
					if (num == MAX_REQUESTS) {
						System.out.println("Too many requests. The program will ONLY handle the first 1000 requests.");
						break;
					}
				}while (!read[num-1].equals("run"));
				for (int j = 0; j < num-1; j++) {
					try {
						schedule.addQueue(read[j]);
					} catch (ElevatorException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
						return;
					}
				}
				schedule.startSchedule();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Program Find ERROR, Please check your requests");
		}
		
		System.out.println("Program Stopped.");
	}
}
