package ele;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Request_monitor implements Runnable {

	private final long initial_time;
	private static final int MAX_REQUESTS = 1000;
	
	public Request_monitor() {
		// TODO Auto-generated constructor stub
		initial_time = System.currentTimeMillis() / 100;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println("aaa");
		String read;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in ));
		int num = 0;
		try {
			 do{
					try {
						read = br.readLine();
					  }catch (IOException e) {
						  e.printStackTrace();
						  return;
					  } 
					int currentTime = (int) (System.currentTimeMillis() / 100 - initial_time);
					if (read == null) {
						System.out.println("Please input something.");
						return;
					}
					try {
						if (RequestQueue.addQueue(read, currentTime)) {
							num++;
						}
					} catch (ElevatorException e) {
						// TODO: handle exception
						System.out.println(e.getMessage());
					}
					if (num == MAX_REQUESTS) {
						System.out.println("Too many requests. The program will ONLY handle the first 1000 requests.");
						break;
					}
				}while (!read.equals("STOP"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Program Find ERROR, Please check your requests");
		}
	}
	
}
