package monitor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;


class MapException extends Exception  
{  
    /**
	 * 
	 */
	private static final long serialVersionUID = -6885933568946727341L;

	public MapException(String msg)  
    {  
        super(msg);  
    }  
}  

public class Map {
	private static final int [][] map = new int [Types.size][Types.size];
	private static Vector<Passenger>[][] map_p;
	
	
	@SuppressWarnings("unchecked")
	public Map() throws MapException{
		//map_p = new Vector<Passenger>[size][size];
		map_p = new Vector[Types.size][Types.size];
		for (int i = 0; i < Types.size; i++) {
			for (int j = 0; j < Types.size; j++) {
				map_p[i][j] = new Vector<Passenger>();
			}
		}
		if (!init_map()) {
			throw new MapException("Can't init Map");
		}
	}
	
	public static boolean isConnect(Index a, Index b) {
//		if (a.getX() < 0 || a.getX() > 79 || a.getY() < 0 || a.getY() > 79 || b.getX() < 0 || b.getX() > 79 || b.getY() < 0 || b.getY() > 79) {
//			return false;
//		}
		try {
			if ((a.getX() < b.getX() && (map[a.getX()][a.getY()] == 2 || map[a.getX()][a.getY()] == 3)) || (a.getX() > b.getX() && (map[b.getX()][b.getY()] == 2 || map[b.getX()][b.getY()] == 3))
					|| ((a.getY() < b.getY()) && (map[a.getX()][a.getY()] == 1 || map[a.getX()][a.getY()] == 3)) || ((a.getY() > b.getY()) && (map[b.getX()][b.getY()] == 1 || map[b.getX()][b.getY()] == 3))) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(a.getX() + " " + a.getY() + " " + b.getX() + " " + b.getY());
			return false;
		}
		
	}
	
	public static Vector<Passenger> findPasg(int x, int y) {
		Vector<Passenger> pas = new Vector<Passenger>();
		for (int i = x-2; i <= x+2; i++) {
			for(int j = y-2; j <= y+2; j++){
				if (i < 0 || j < 0 || i >79 || j > 79) {
					continue;
				}
				for (int j2 = 0; j2 < map_p[i][j].size(); j2++) {
					//System.out.println(i + " " + j + " " + x + " " + y);
					pas.addElement(map_p[i][j].get(j2));
				}
			}
		}
		return pas;
	}

	public static Vector<Integer> shortestPath(int x1, int y1, int x2, int y2) throws MapException{
		Vector<Integer> path = new Vector<Integer>();
		int[][] m = new int[Types.size][Types.size];
		for (int i = 0; i < Types.size; i++) {
			for (int j = 0; j < Types.size; j++) {
				m[i][j] = -1;
			}
		}
		Index start = new Index(x1, y1);
		m[x1][y1] = 0;
		LinkedList<Index> queue = new LinkedList<Index>();
		queue.offer(start);
		while (queue.size() != 0) {
			Index index = queue.poll();
			int x = index.getX();
			int y = index.getY();
			if (y-1 >=0 && (map[x][y-1] == 3 || map[x][y-1] == 1) && m[x][y-1] == -1) {
				m[x][y-1] = Types.RIGHT;
				queue.offer(new Index(x, y-1));
				if (x == x2 && y-1 == y2) {
					break;
				}
			}
			if (x-1 >=0 && (map[x-1][y] == 3 || map[x-1][y] == 2) && m[x-1][y] == -1) {
				m[x-1][y] = Types.DOWN;
				queue.offer(new Index(x-1, y));
				if (x-1 == x2 && y == y2) {
					break;
				}
			}
			if (y+1 < Types.size && (map[x][y] == 3 || map[x][y] == 1) && m[x][y+1] == -1) {
				m[x][y+1] = Types.LEFT;
				queue.offer(new Index(x, y+1));
				if (x == x2 && y+1 == y2) {
					break;
				}
			}
			if (x+1 < Types.size && (map[x][y] == 3 || map[x][y] == 2) && m[x+1][y] == -1) {
				m[x+1][y] = Types.UP;
				queue.offer(new Index(x+1, y));
				if (x+1 == x2 && y == y2) {
					break;
				}
			}
		}
		while (x2 != x1 || y2 != y1) {
			switch (m[x2][y2]) {
			case Types.UP:
				path.add(0, Types.DOWN);
				x2--;
				break;
			case Types.DOWN:
				path.add(0, Types.UP);
				x2++;
				break;
			case Types.LEFT:
				path.add(0, Types.RIGHT);
				y2--;
				break;
			case Types.RIGHT:
				path.add(0, Types.LEFT);
				y2++;
				break;
			default:
				throw new MapException("Wrong Direction!");
			}
		}
		return path;
	}
	
	
	
	public static void addReq(int x, int y, Passenger p) {
		if (map_p[x][y].contains(p)) {
			return;
		}
		map_p[x][y].addElement(p);
	}
	
	public static void deleteReq(int x, int y, Passenger p) {
		map_p[x][y].removeElement(p);
	}
	
	private boolean init_map()
	{
		FileReader fileReader;
		BufferedReader br = null;
		String text;
		boolean success = true;
		try {
			fileReader = new FileReader("Map.txt");
			br = new BufferedReader(fileReader);
			for (int i = 0; i < Types.size; i++) {
				if ((text = br.readLine()) == null) {
					success = false;
				}
				for (int j = 0; j < Types.size; j++) {
					map[i][j] = text.charAt(j) - '0';
					if(map[i][j] > 3 || map[i][j] < 0){
						br.close();
						return false;
					}
				}
			}
			br.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			success =false;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			success = false;
		}
		
		return success;
	}
}
