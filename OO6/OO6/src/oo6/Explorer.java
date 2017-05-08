package oo6;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Explorer implements Runnable{
	
	

	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
//--------------------> Insert the file change CODE here<--------------------------------------//
		 

//---------------------------------------END--------------------------------------------------------//
		/*
		 * TEST codes
		 */
//		for (int i = 0; i < 10; i++) {
//			addFile("C:\\Users\\Henry\\Desktop\\oo7\\" + i + "dd.txt");
//			try {
//				Thread.sleep(15000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		for (int i = 0; i < 5; i++) {
//			File file = new File("C:\\Users\\Henry\\Desktop\\oo7\\" + i + "dd.txt");
//			String string = "C:\\Users\\Henry\\Desktop\\oo7\\cc\\";
//			move(file, string);
//			try {
//				Thread.sleep(15000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		writefile(new File("C:\\Users\\Henry\\Desktop\\oo7\\aa.txt"));
//		try {
//			Thread.sleep(15000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		File file = new File("C:\\Users\\Henry\\Desktop\\oo7\\aa.txt");
//		String string = "C:\\Users\\Henry\\Desktop\\oo7\\cc\\";
//		move(file, string);
//		try {
//			Thread.sleep(15000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		rename(new File("C:\\Users\\Henry\\Desktop\\oo7\\cc\\aa.txt"), "C:\\Users\\Henry\\Desktop\\oo7\\cc\\ii.txt");
//		
//		try {
//			Thread.sleep(15000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		deleteFile("C:\\Users\\Henry\\Desktop\\oo7\\cc\\aa.txt");
	}
	
	public synchronized String getName(File file) {
		return file.getName();
	}
	
	public synchronized long getSize(File file) {
		return file.length();
	}
	
	public synchronized long getlastModified(File file) {
		return file.lastModified();
	}
	/*
	 * name should be a complete abusolute path 
	 */
	public synchronized void rename(File oldfile, String name) {
		if (!oldfile.exists() || oldfile.isDirectory()) {
			System.out.println("Can't rename this file");
			return;
		}
		File newfile = new File(name);
		if (oldfile.renameTo(newfile)) {
			System.out.println("Rename success!");
		}else {
			System.out.println("Rename failed.");
		}
	}
	public synchronized void move(File file, String path) {
		File check = new File(path);
		if (!check.isDirectory() || !file.exists() || file.isDirectory()) {
			System.out.println("Path is not a Directory! or file wrong!");
			return;
		}
		File to = new File(path + file.getName());
		if (file.renameTo(to)) {
			System.out.println("move success!");
		}else {
			System.out.println("move failed.");
		}
	}
	public synchronized void addFile(String path) {
		File newfile = new File(path);
		if (newfile.exists() || newfile.isDirectory()) {
			System.out.println("File exist or isDir.");
		}else {
			try {
				newfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}
	public synchronized void addDir(String path) {
		File newfile = new File(path);
		if (newfile.exists() || newfile.isDirectory()) {
			System.out.println("Directory exist.");
		}else {
			newfile.mkdir();
		}
	}
	
	public synchronized void deleteFile(String path) {
		File newfile = new File(path);
		if (!newfile.exists()) {
			System.out.println("File not exist.");
		}else {
			newfile.delete();
		}
	}
	
	public synchronized void writefile(File file) {
		
		String content = "ADD";
		
		try (FileOutputStream fop = new FileOutputStream(file, true)) {
			// if file doesn't exists, then create it
			if (!file.exists() || file.isDirectory()) {
				System.out.println("File not exist.");
			    return;
			   }
			// get the content in bytes
			byte[] contentInBytes = content.getBytes();
			fop.write(contentInBytes); 
			fop.flush();
			fop.close();
			System.out.println("Done");
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println(e.getMessage());
		}
	}
}
