package oo6;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;


public class Main_oo6 {

	private static final int MAX_REQUESTS = 10;
	
	private static final int renamed = 0;
	private static final int modified = 1;
	private static final int path_changed = 2;
	private static final int size_changed = 3;
	
	private static final int record_summary = 4;
	private static final int record_detail = 5;
	private static final int recover = 6;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String [] read = new String[MAX_REQUESTS];
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in ));
		int num = 0;
		Timer [] timers = new Timer[10];
		File_workspace [] file_workspaces = new File_workspace[8];
		int t_num = 0;
		
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
						System.out.println("Too many requests. The program will ONLY handle 8 requests.");
						break;
					}
				}while (!read[num-1].equals("run"));
			 for (int j = 0; j < num-1; j++) {
				String temp = read[j];
				String [] sp = temp.split("\\s");
				int trigger;
				int task;
				if (sp.length !=5 || !sp[0].equals("IF") || !sp[3].equals("THEN")) {
					System.out.println("This request has wrong format and it wll not be process.");
					continue;
				}
				File file = new File(sp[1]);
				if (sp[2].equals("renamed") && file.isFile() && file.exists()) {
					trigger = renamed;
				}else if (sp[2].equals("modified")) {
					trigger = modified;
				}else if (sp[2].equals("path_changed") && file.isFile() && file.exists()) {
					trigger = path_changed;
				}else if (sp[2].equals("size_changed")) {
					trigger = size_changed;
				}else {
					System.out.println("This request has wrong format and it wll not be process.");
					continue;
				}
				
				if (sp[4].equals("record_summary")) {
					task = record_summary;
				}else if(sp[4].equals("record_detail")) {
					task = record_detail;
				}else if(sp[4].equals("recover") && (trigger == renamed || trigger == path_changed)) {
					task = recover;
				}else {
					System.out.println("This request has wrong format and it wll not be process.");
					continue;
				}
				if (!file.exists()) {
					System.out.println("File/Dir not exist.");
					continue;
				}
				file_workspaces[t_num] = new File_workspace(file, trigger, task); 
				timers[t_num] = new Timer();
				t_num++;
			} 
			 
			 //Frequency 10s/time
			 for (int i = 0; i < t_num; i++) {
				timers[i].schedule(file_workspaces[i], 0, 3000);
			}
			 
			 Timer timer_Detail = new Timer(true);
			 timer_Detail.schedule(new Detail(), 0, 15000);
			 Timer timer_Summary = new Timer(true);
			 timer_Summary.schedule(new Summary(), 0, 15000);
//--------------------> Insert the file change Tread here<--------------------------------------//
			 
			 Thread rm = new Thread(new Explorer());
			 rm.run();
//---------------------------------------END--------------------------------------------------------//
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Program Find ERROR, Please check your requests");
		}
		
//		File file = new File("C:\\Users\\Henry\\Desktop");
//		//File file2 = new File("C:\\Users\\Henry\\Desktop\\test.txt");
//		 //File f7 = new File("asdsdsa.txt");
//		boolean a = file.isDirectory();
//		System.out.println(a + " " + file.exists()+": " + file.getParent() + "\n" +file.lastModified() + " " + file.length());
	}

}
