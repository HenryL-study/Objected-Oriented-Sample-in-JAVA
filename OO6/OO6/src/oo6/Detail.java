package oo6;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Vector;

public class Detail extends TimerTask{

	private static Vector<String> detailInfo = new Vector<String>();
	
	public static synchronized void addDetails(String details) {
		// TODO Auto-generated method stub
		detailInfo.addElement(details);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (this) {
			//System.out.println("Detail");
			File write = new File("detail.txt");
			if (write.exists()) {
				write.delete();
			}
			try {
				write.createNewFile();
				FileWriter fileWriter = new FileWriter(write);
				for (int i = 0; i < detailInfo.size(); i++) {
					fileWriter.write(detailInfo.get(i) + "\n");
					fileWriter.flush();
				}
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Can't create detail.txt. Writing failed.");
				return;
			}
		}
	}

}
