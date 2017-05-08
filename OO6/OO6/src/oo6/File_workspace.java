package oo6;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;

public class File_workspace extends TimerTask{
	
	private File workspace;
	private boolean is_file;
	//Watch a file
	private MyFile file_the_watched;
	private boolean isDelete;
	
	//Watch a Directory
	private HashMap<String, MyFile> watch_files;
	private HashMap<String, MyFile> t_files;
	private HashMap<String, File> watch_Directory;
	private HashMap<String, File> t_Directory;
	//private Vector<MyFile> watch_files;
	//private Vector<MyFile> t_files;
	//private Vector<File> watch_Directory;
	//private Vector<File> t_Directory;
	private int trigger;
	private int task;
	
	
	private static final int renamed = 0;
	private static final int modified = 1;
	private static final int path_changed = 2;
	private static final int size_changed = 3;
	
	private static final int record_summary = 4;
	private static final int record_detail = 5;
	private static final int recover = 6;
	
	public File_workspace(File file, int trigger, int task) {
		this.trigger = trigger;
		this.task = task;
		
		if (file.isFile()) {
			is_file = true;
			workspace = file.getParentFile();
			file_the_watched = new MyFile(file);
		}
		else if (file.isDirectory()) {
			is_file = false;
			workspace = file;
			//watch_files = new HashMap<String, MyFile>();
			t_files = new HashMap<String, MyFile>();
			//watch_Directory = new HashMap<String, File>();
			t_Directory = new HashMap<String, File>();
			
			init_t(workspace);
			watch_files = t_files;
			watch_Directory = t_Directory;
		}
	}

	@Override
	public void run() {
		
		synchronized(this){
			
			boolean is_Delete = false;
			boolean is_Add = false;
			
			/*
			 * renamed modified path-changed size-changed
			 */
			boolean [] status =  {false,false,false,false};
			//System.out.println("aaaa");
			if (is_file) {
				File change = null;
				int type;
				//System.out.println(file_the_watched.exists());
				
				if (isDelete && !file_the_watched.exists()) {
					return;
				}
				
				if (isDelete && file_the_watched.exists()) {
					isDelete = false;
					status[size_changed] = true;
				}
				else if (!file_the_watched.exists()) {
					File P_Directory = new File(workspace.getAbsolutePath());
					numFile numF =  SearchFile(P_Directory, file_the_watched, change);
					type = numF.num;
					change = numF.file;
					//System.out.println(type);
					if (type != -1) {
						status[type] = true;
					}else {
						isDelete = true;
						if (trigger == size_changed) {
							switch (task) {
							case record_summary:
								Summary.addnum(trigger);
								break;
							case record_detail:
								String string = "Trigger: modified " + file_the_watched.getName() + " "  + "-> Delete" ;
								Detail.addDetails(string);
								break;
							default:
								break;
							}
						}
					}
				}else {
					if (file_the_watched.getfile_lastmodified() != file_the_watched.getLastModefied()) {
						status[modified] = true;
					}
					if (file_the_watched.getfile_size() != file_the_watched.getSize()) {
						status[size_changed] = true;
					}
				}
				if (status[trigger]) {
					switch (task) {
					case record_summary:
						Summary.addnum(trigger);
						break;
					case record_detail:
						if (trigger == renamed) {
							String string = "Trigger: renamed " + file_the_watched.getName() + "->" + change.getName();
							Detail.addDetails(string);
						}else if (trigger == modified) {
							String string = "Trigger: modified " + file_the_watched.getName() + " " + file_the_watched.getLastModefied() + "->" + file_the_watched.getfile_lastmodified();
							Detail.addDetails(string);
						}else if (trigger == path_changed) {
							String string = "Trigger: path_changed " + file_the_watched.getName() + " " + file_the_watched.getPath() + "->" + change.getAbsolutePath();
							Detail.addDetails(string);
						}else if (trigger == size_changed) {
							String string = "Trigger: size_changed " + file_the_watched.getName() + " " + file_the_watched.getSize() + "->" + file_the_watched.getfile_size();
							Detail.addDetails(string);
						}
						break;
					case recover:
						if (change.renameTo(new File(file_the_watched.getPath() + "\\" +file_the_watched.getName()))) {
							file_the_watched = new MyFile(change);
						}else {
							System.out.println("Can't recover the " + file_the_watched.getName());
						}
						break;
					default:
						break;
					}
				}
				if (status[renamed] || status[path_changed]) {
					file_the_watched = new MyFile(change);
				}
				file_the_watched.setLastModefied(file_the_watched.getfile_lastmodified());
				file_the_watched.setSize(file_the_watched.getfile_size());
			}else {
				//Directory
				// TODO Diretory
				t_files = new HashMap<String, MyFile>();
				t_Directory = new HashMap<String, File>();
				init_t(workspace);
				
				int d_size = watch_Directory.size();
				int f_size = watch_files.size();
				int t_d_size = t_Directory.size();
				int t_f_size = t_files.size();
				
				File recordDirectory = null;
				//File recordDirectory1 = null;
				MyFile recordFile = null;
				MyFile recordFile1 = null;
				
				//文件夹增删
				if (d_size != t_d_size) {
					//删除
					if (d_size == t_d_size + 1) {
						Iterator<Map.Entry<String, File>> iterator = watch_Directory.entrySet().iterator();
						while (iterator.hasNext()) {
							Map.Entry<String, File> entry =  iterator.next();
							String key = entry.getKey();
							if (!t_Directory.containsKey(key)) {
								status[size_changed] = true;
								recordDirectory = entry.getValue();
								is_Delete = true;
								//watch_Directory = t_Directory;
								break;
							}
						}
					}
					//增加
					else if (d_size == t_d_size - 1) {
						Iterator<Map.Entry<String, File>> iterator = t_Directory.entrySet().iterator();
						while (iterator.hasNext()) {
							Map.Entry<String, File> entry =  iterator.next();
							String key = entry.getKey();
							if (!watch_Directory.containsKey(key)) {
								status[size_changed] = true;
								recordDirectory = entry.getValue();
								is_Add = true;
								//watch_Directory = t_Directory;
								break;
							}
						}
					}
				}else {
					//����ļ���ɾ
					if (f_size != t_f_size) {
						//ɾ���ļ�
						if (f_size == t_f_size + 1) {
							Iterator<Map.Entry<String, MyFile>> iterator = watch_files.entrySet().iterator();
							while (iterator.hasNext()) {
								Map.Entry<String, MyFile> entry =  iterator.next();
								String key = entry.getKey();
								if (!t_files.containsKey(key)) {
									status[size_changed] = true;
									recordFile = entry.getValue();
									is_Delete = true;
									//watch_Directory = t_Directory;
									break;
								}
							}
						}
						//�����ļ�
						else if (f_size == t_f_size - 1) {
							Iterator<Map.Entry<String, MyFile>> iterator = t_files.entrySet().iterator();
							while (iterator.hasNext()) {
								Map.Entry<String, MyFile> entry =  iterator.next();
								String key = entry.getKey();
								if (!watch_files.containsKey(key)) {
									status[size_changed] = true;
									recordFile = entry.getValue();
									is_Add = true;
									//watch_Directory = t_Directory;
									break;
								}
							}
						}
					}else{
						//���Ŀ¼������ DO NOT NEED
//						Iterator<Map.Entry<String, File>> iterator = watch_Directory.entrySet().iterator();
//						while (iterator.hasNext()) {
//							Map.Entry<String, File> entry =  iterator.next();
//							String key = entry.getKey();
//							if (!t_Directory.containsKey(key)) {
//								//status[size_changed] = true;
//								recordDirectory = entry.getValue();
//								// find the folder's name now
//								Iterator<Map.Entry<String, File>> iterator1 = t_Directory.entrySet().iterator();
//								while (iterator1.hasNext()) {
//									Map.Entry<String, File> entry1 =  iterator1.next();
//									String key1 = entry1.getKey();
//									if (!watch_Directory.containsKey(key1)) {
//										//status[size_changed] = true;
//										recordDirectory1 = entry1.getValue();
//										//watch_Directory = t_Directory;
//										break;
//									}
//								}
//								status[]
//								break;
//							}
//						}
						//����ļ��޸ģ�������
						Iterator<Map.Entry<String, MyFile>> iterator = watch_files.entrySet().iterator();
						while (iterator.hasNext()) {
							Map.Entry<String, MyFile> entry =  iterator.next();
							String key = entry.getKey();
							MyFile mFile = entry.getValue();
							if (t_files.containsKey(key)) {
								MyFile tFile = t_files.get(key);
								//�ļ���С�仯
								if (tFile.getSize() != mFile.getSize()) {
									recordFile = mFile;
									recordFile1 = tFile;
									status[modified] = true;
									status[size_changed] = true;
									break;
								}else {
									//�ļ�ʱ��仯
									if (tFile.getLastModefied() != mFile.getLastModefied()) {
										recordFile = mFile;
										recordFile1 = tFile;
										status[modified] = true;
										break;
									}
								}
							}else {
								File file_the_find = null;
								numFile numF = SearchFile(workspace, mFile, file_the_find);
								int type = numF.num;
								file_the_find = numF.file;
								if (type != -1) {
									status[type] = true;
									recordFile = mFile;
									recordFile1 = new MyFile(file_the_find);
									break;
								}
							}
						}
					}
				}
				
				if (status[trigger]) {
					switch (task) {
					case record_summary:
						Summary.addnum(trigger);
						break;
					case record_detail:
						if (trigger == renamed) {
							String string = "Trigger: renamed ";
							if (recordFile != null) {
								string = string + recordFile.getName() + "->" + recordFile1.getName();
							}
							Detail.addDetails(string);
						}else if (trigger == modified) {
							String string = "Trigger: modified " + recordFile.getName() + " " + recordFile.getLastModefied() + "->" + recordFile1.getLastModefied() + " and all of its father folder's modefied time.";
							Detail.addDetails(string);
						}else if (trigger == path_changed) {
							String string = "Trigger: path_changed " + recordFile.getName() + " " + recordFile.getPath() + "->" + recordFile1.getPath();
							Detail.addDetails(string);
						}else if (trigger == size_changed) {
							String string;
							if (recordFile != null) {
								if (is_Add) {
									string = "Trigger: size_changed " + " ADD-> " + recordFile.getName() + " and all of its father folder's size changed.";
								}else if (is_Delete) {
									string = "Trigger: size_changed " + " DELETE-> " + recordFile.getName() + " and all of its father folder's size changed.";
								}else {
									string = "Trigger: size_changed " + recordFile.getName() + " " + recordFile.getSize() + "->" + recordFile1.getSize() + " and all of its father folder's size.";
								}
							}else {
								if (is_Add) {
									string = "Trigger: size_changed " + " ADD-> " + recordDirectory.getName() + " and all of its father folder's size changed.";
								}else if (is_Delete) {
									string = "Trigger: size_changed " + " DELETE-> " + recordDirectory.getName() + " and all of its father folder's size changed.";
								}else{
									string = "";
								}
							}
							
		//					System.out.println(string);
							Detail.addDetails(string);
						}
						break;
					case recover:
						File aaFile = new File(recordFile1.getPath());
						if (aaFile.renameTo(new File(recordFile.getPath()))) {
							recordFile1 = new MyFile(aaFile);
						}else {
							System.out.println("Can't recover the " + file_the_watched.getName());
						}
						break;
					default:
						break;
					}
				}
				//System.out.println(status[0] + " " + status[1] + " " + status[2] + " " + status[3]);
				watch_Directory = t_Directory;
				watch_files = t_files;
			}
		}
		
	}
	
	
	public synchronized void init_t(File wp)
	{
		File [] fileso = wp.listFiles();
		for (File file : fileso) {
			if (file.isDirectory()) {
				t_Directory.put(file.getAbsolutePath(), file);
				init_t(file);
			}else if (file.isFile()) {
				t_files.put(file.getAbsolutePath(), new MyFile(file));
			}
		}
	}
	/*
	 * Watch File
	 * 
	 * -1: not find
	 * 0: renamed: find a rename file in origin directory
	 * 2: path-changed: find the same file in a different directory
	 */
	public synchronized numFile SearchFile(File directory, MyFile file_to_search, File file_the_find) {
		File [] fileso = directory.listFiles();
		numFile re = new numFile(file_the_find, -1);
		for (int i = 0; i < fileso.length; i++) {
			if (fileso[i].isFile()) {
				//rename
				//System.out.println(i + " " + fileso[i].getParent() + " " + file_to_search.getPath() + " " +  fileso[i].length() + " " + file_to_search.getSize());
				if (fileso[i].lastModified() == file_to_search.getLastModefied() && !fileso[i].getName().equals(file_to_search.getName()) 
						&& fileso[i].getParent().equals(file_to_search.getPath()) && fileso[i].length() == file_to_search.getSize())  {
					file_the_find = fileso[i];
					re = new numFile(file_the_find, 0);
					return re;
				}
				//path-changed
				if (fileso[i].lastModified() == file_to_search.getLastModefied() && fileso[i].getName().equals(file_to_search.getName()) 
						&& !fileso[i].getParent().equals(file_to_search.getPath()) && fileso[i].length() == file_to_search.getSize())  {
					file_the_find = fileso[i];
					re = new numFile(file_the_find, 2);
					return re;
				}
			}else if (fileso[i].isDirectory()) {
				re = SearchFile(fileso[i], file_to_search, file_the_find);
				if (re.num != -1) {
					return re;
				}
			}
		}
		return re;
	}

}

class numFile{
	public File file;
	public int num;
	public numFile(File file, int num) {
		this.file = file;
		this.num = num;
	}
	
	
}
