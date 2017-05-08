package monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.Vector;

public class Taxi_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Initialize all the classes and make this program running.
		 */
		try {
			new Map();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			return;
		}

		Thread [] taxis = new Thread[100];
		Taxi [] taxis2 = new Taxi[100];
		
		for (int i = 0; i < taxis.length; i++) {
			taxis2[i] = new Taxi(i);
			taxis[i] = new Thread(taxis2[i]);
			taxis[i].setName("TAXI" + i);
		}
		Thread thread = new Thread(new Passenger_Monitor(taxis2));
		thread.setName("Passenger_Monitor");
		Schedule schedule = new Schedule();
		Timer timer = new Timer();
		timer.schedule(schedule, 0, 100);
		thread.start();
		
		for (int i = 0; i < 100; i++) {
			taxis[i].start();
		}
		
//		if (schedule.repOK() && taxis2[0].repOK()) {
//			System.out.println("asdsad");
//		}else {
//			System.out.println("wrong");
//		}
		
		String read;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in ));
		try {
			 do{
					try {
						read = br.readLine();
					  }catch (IOException e) {
						  e.printStackTrace();
						  return;
					  } 
					if (read == null) {
						System.out.println("GOT CTRL+Z!\n DO NOT DO THAT PLEASE~");
						return;
					}
					if (read.equals("recover")) {
						Vector<ChangeIndex> cIndexs = Map.getChanged();
						if (cIndexs.size() == 0) {
							System.out.println("NOTHING CHANGED. Can't recover.");
							continue;
						}
						System.out.println("Now changed " + cIndexs.size() + " paths. Input the num that you want to recover.");
						for (int i = 0; i < cIndexs.size(); i++) {
							System.out.println(i + ": Index (" + cIndexs.get(i).getIndex().getX() + "," + cIndexs.get(i).getIndex().getY() + ") " + cIndexs.get(i).getChange());
						}
						int num;
						try {
							read = br.readLine();
							num = Integer.parseInt(read);
						  }catch (Exception e) {
							  e.printStackTrace();
							  System.out.println("WRONG INPUT. Cant recover.");
							  continue;
						 }
						if (num >=0 && num < cIndexs.size()) {
							Map.recoverPath(num);
							System.out.println("Recover successful!");
						}else {
							System.out.println("WRONG INPUT. Cant recover.");
							continue;
						}
					}else {
						String [] nums = read.split(" ");
						if (nums.length == 3) {
							try {
								int x = Integer.parseInt(nums[0]);
								int y = Integer.parseInt(nums[1]);
								int change = Integer.parseInt(nums[2]);
								if (Map.deletePath(new Index(x, y), change)) {
									System.out.println("Delete successful!");
								}else {
									System.out.println("Wrong input.");
								}
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}else {
							System.out.println("Wrong input.");
						}
					}
				}while (true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Program Find ERROR, Please check your input");
		}
	}

}
