package oo6;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;

public class Summary extends TimerTask{
	
	private static int[] triggers = {0,0,0,0};

	
	public static synchronized void addnum(int index) {
		triggers[index]++;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (this) {
			//System.out.println("Sum");
			File write = new File("summary.txt");
			if (write.exists()) {
				write.delete();
			}
			try {
				write.createNewFile();
				FileWriter fileWriter = new FileWriter(write);
				fileWriter.write("renamed: " + triggers[0] + "  " + "modified: " + triggers[1] + "  " + "path-changed: " + triggers[2] + "  " + "size-changed: " + triggers[3] + "  ");
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Can't create summary.txt. Writing failed.");
				return;
			}
		}
	}


}
