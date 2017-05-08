package monitor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicIntegerArray;


class MapException extends Exception  
{  
	private static final long serialVersionUID = -6885933568946727341L;

	public MapException(String msg){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Nothing
		 */
        super(msg);  
    }  
}  

public class Map {
	private static final int [][] map = new int [Types.size][Types.size];
	private static final int [][] map2 = new int [Types.size][Types.size];
	private static final int [][] connect = new int[Types.size][Types.size];
	private static final Traffic_light[][] light = new Traffic_light[Types.size][Types.size];
	private static Vector<Passenger>[][] map_p;
	private static Vector<ChangeIndex> changeIndex;
	private static AtomicIntegerArray flows;
	
	public boolean repOK() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the true if the rep variant holds for this. otherwise return false.
		 */
		if (map != null && connect != null && light != null &&  map_p != null && changeIndex != null && flows != null && map2 != null) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public Map() throws MapException{
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Initialize the flows, map_p, changeIndex, map, map2
		 */
		//map_p = new Vector<Passenger>[size][size];
		flows = new AtomicIntegerArray(12640);
		map_p = new Vector[Types.size][Types.size];
		changeIndex = new Vector<ChangeIndex>();
		for (int i = 0; i < Types.size; i++) {
			for (int j = 0; j < Types.size; j++) {
				map_p[i][j] = new Vector<Passenger>();
			}
		}
		if (!init_map()) {
			throw new MapException("Can't init Map");
		}
		init_lights();
		for (int i = 0; i < Types.size; i++) {
			for (int j = 0; j < Types.size; j++) {
				map2[i][j] = map[i][j];
			}
		} 
	}
	
	public static boolean isConnect(Index a, Index b) {
		/*
		 * Requires: Two indexes which is border upon.
		 * Modifies: Nothing.
		 * Effects: check this two indexes whether border upon.
		 */
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
	
	public static boolean isConnect_t(Index a, Index b) {
		/*
		 * Requires: Two indexes which is border upon.
		 * Modifies: Nothing.
		 * Effects: check this two indexes whether border upon.
		 */
		try {
			if ((a.getX() < b.getX() && (map2[a.getX()][a.getY()] == 2 || map2[a.getX()][a.getY()] == 3)) || (a.getX() > b.getX() && (map2[b.getX()][b.getY()] == 2 || map2[b.getX()][b.getY()] == 3))
					|| ((a.getY() < b.getY()) && (map2[a.getX()][a.getY()] == 1 || map2[a.getX()][a.getY()] == 3)) || ((a.getY() > b.getY()) && (map2[b.getX()][b.getY()] == 1 || map2[b.getX()][b.getY()] == 3))) {
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
		/*
		 * Requires: Two integers which is an index.
		 * Modifies: Nothing.
		 * Effects: Find all the passengers near the index which passed in then return a Vector contains them.
		 */
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
		/*
		 * Requires: Four integers which are two indexes.
		 * Modifies: Nothing.
		 * Effects: Find the shortest path of this two indexes.
		 */
		Vector<Integer> path = new Vector<Integer>();
		if (x1 == x2 && y1 == y2) {
			return path;
		}
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
	
	private static Vector<Integer> shortestPath_t(int x1, int y1, int x2, int y2) throws MapException{
		/*
		 * Requires: Four integers which are two indexes.
		 * Modifies: Nothing.
		 * Effects: Find the shortest path of this two indexes.
		 */
		Vector<Integer> path = new Vector<Integer>();
		if (x1 == x2 && y1 == y2) {
			return path;
		}
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
			if (y-1 >=0 && (map2[x][y-1] == 3 || map2[x][y-1] == 1) && m[x][y-1] == -1) {
				m[x][y-1] = Types.RIGHT;
				queue.offer(new Index(x, y-1));
				if (x == x2 && y-1 == y2) {
					break;
				}
			}
			if (x-1 >=0 && (map2[x-1][y] == 3 || map2[x-1][y] == 2) && m[x-1][y] == -1) {
				m[x-1][y] = Types.DOWN;
				queue.offer(new Index(x-1, y));
				if (x-1 == x2 && y == y2) {
					break;
				}
			}
			if (y+1 < Types.size && (map2[x][y] == 3 || map2[x][y] == 1) && m[x][y+1] == -1) {
				m[x][y+1] = Types.LEFT;
				queue.offer(new Index(x, y+1));
				if (x == x2 && y+1 == y2) {
					break;
				}
			}
			if (x+1 < Types.size && (map2[x][y] == 3 || map2[x][y] == 2) && m[x+1][y] == -1) {
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
	
	
	
	public static int shortestPath2(int x1, int y1, int x2, int y2){
		/*
		 * Requires: Four integers which are two indexes.
		 * Modifies: Nothing.
		 * Effects: Find the first step of the shortest and least car flow path of this two indexes and return. 
		 */
		if (x1 == x2 && y1 == y2) {
			return -1;
		}
		//TODO 
		int [] distance = {10000,10000,10000,10000};
		if (x1-1 >=0 && (map[x1-1][y1] == 3 || map[x1-1][y1] == 2)) {
			try {
				distance[0] = shortestPath(x1-1, y1, x2, y2).size();
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (x1+1 < 80 && (map[x1][y1] == 3 || map[x1][y1] == 2)) {
			try {
				distance[1] = shortestPath(x1+1, y1, x2, y2).size();
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (y1-1 >=0 && (map[x1][y1-1] == 3 || map[x1][y1-1] == 1)) {
			try {
				distance[2] = shortestPath(x1, y1-1, x2, y2).size();
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (y1+1 < 80 && (map[x1][y1] == 3 || map[x1][y1] == 1)) {
			try {
				distance[3] = shortestPath(x1, y1+1, x2, y2).size();
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int shortest = distance[0];
		int di = Types.UP;
		for (int i = 1; i < distance.length; i++) {
			if (distance[i] == 10000) {
				continue;
			}
			if ((shortest > distance[i]) || (shortest == distance[i] && getFlow(x1, y1, di) > getFlow(x1, y1, i))) {
				shortest = distance[i];
				di = i;
			}
		}
		return di;
	}
	
	public static int shortestPath2_t(int x1, int y1, int x2, int y2){
		/*
		 * Requires: Four integers which are two indexes.
		 * Modifies: Nothing.
		 * Effects: Find the first step of the shortest and least car flow path of this two indexes and return. 
		 */
		if (x1 == x2 && y1 == y2) {
			return -1;
		}
		//TODO 
		int [] distance = {10000,10000,10000,10000};
		if (x1-1 >=0 && (map2[x1-1][y1] == 3 || map2[x1-1][y1] == 2)) {
			try {
				distance[0] = shortestPath_t(x1-1, y1, x2, y2).size();
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (x1+1 < 80 && (map2[x1][y1] == 3 || map2[x1][y1] == 2)) {
			try {
				distance[1] = shortestPath_t(x1+1, y1, x2, y2).size();
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (y1-1 >=0 && (map2[x1][y1-1] == 3 || map2[x1][y1-1] == 1)) {
			try {
				distance[2] = shortestPath_t(x1, y1-1, x2, y2).size();
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (y1+1 < 80 && (map2[x1][y1] == 3 || map2[x1][y1] == 1)) {
			try {
				distance[3] = shortestPath_t(x1, y1+1, x2, y2).size();
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int shortest = distance[0];
		int di = Types.UP;
		for (int i = 1; i < distance.length; i++) {
			if (distance[i] == 10000) {
				continue;
			}
			if ((shortest > distance[i]) || (shortest == distance[i] && getFlow(x1, y1, di) > getFlow(x1, y1, i))) {
				shortest = distance[i];
				di = i;
			}
		}
		return di;
	}
	
	

	public static void addReq(int x, int y, Passenger p) {
		/*
		 * Requires: Two integers which is an index and a passenger.
		 * Modifies: Nothing.
		 * Effects: Map the passenger into the map_p. 
		 */
		
		if (map_p[x][y].contains(p)) {
			return;
		}
		map_p[x][y].addElement(p);
	}
	
	public static void deleteReq(int x, int y, Passenger p) {
		/*
		 * Requires: Two integers which is an index and a passenger.
		 * Modifies: Nothing.
		 * Effects: delete the passenger in the map_p. 
		 */
		map_p[x][y].removeElement(p);
	}
	
	private boolean init_map(){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Initialize the map by Map.txt.
		 */
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
		try {
			fileReader = new FileReader("Connect.txt");
			br = new BufferedReader(fileReader);
			for (int i = 0; i < Types.size; i++) {
				if ((text = br.readLine()) == null) {
					success = false;
				}
				for (int j = 0; j < Types.size; j++) {
					connect[i][j] = text.charAt(j) - '0';
					if(connect[i][j] > 1 || connect[i][j] < 0){
						br.close();
						return false;
					}
				}
			}
			br.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			success =false;
			e.printStackTrace();
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		} catch (Exception e) {
			success = false;
		}
		
		return success;
	}
	
	private void init_lights(){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Initialize the light.
		 */
		for (int i = 0; i < Types.size; i++) {
			for (int j = 0; j < Types.size; j++) {
				if (connect[i][j] == 0 || countConnect(i, j) < 3) {
					light[i][j] = new Traffic_light(false);
				}else {
					light[i][j] = new Traffic_light(true);
				}
			}
		}
		new Light_ctl(light).start();
	}
	
	private int countConnect(int i, int j) {
		/*
		 * Requires: Two integers.
		 * Modifies: Nothing.
		 * Effects: Get the number of connected path.
		 */
		int co = 0;
		if (i-1 >= 0 && (map[i-1][j] == 2 || map[i-1][j] == 3)) {
			co++;
		}
		if (i+1 < 80 && (map[i][j] == 2 || map[i][j] == 3)) {
			co++;
		}
		if (j-1 >= 0 && (map[i][j-1] == 1 || map[i][j-1] == 3)) {
			co++;
		}
		if (j+1 < 80 && (map[i][j] == 1 || map[i][j] == 3)) {
			co++;
		}
		return co;
	}

	public synchronized static boolean deletePath(Index co, int num){
		/*
		 * Requires: An index in map which needs to be changed to the num.
		 * Modifies: Nothing.
		 * Effects: Delete a path in the map..
		 */
		if (changeIndex.size() > 5 || co.getX() >= 80 || co.getX() < 0 || co.getY() >= 80 || co.getY() <0) {
			return false;
		}
		int x = co.getX();
		int y = co.getY();
		synchronized(map){
			if (map[x][y] == 3) {
				if (num != 1 && num != 2) {
					return false;
				}else {
					map[x][y] = num;
					if (num == 1) {
						changeIndex.addElement(new ChangeIndex(co, 2));
					}else {
						changeIndex.addElement(new ChangeIndex(co, 1));
					}
					return true;
				}
			}else if (map[x][y] == 0) {
				return false;
			}else if (map[x][y] == 1) {
				if (num == 0) {
					map[x][y] = num;
					changeIndex.addElement(new ChangeIndex(co, 1));
					return true;
				}else {
					return false;
				}
			}else if (map[x][y] == 2){
				if (num == 0) {
					map[x][y] = num;
					changeIndex.addElement(new ChangeIndex(co, 2));
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
	}
	
	public synchronized static void recoverPath(int i){
		/*
		 * Requires: A number which is a index of changeIndex.
		 * Modifies: Nothing.
		 * Effects: Recover a path in map.
		 */
		ChangeIndex ci = changeIndex.get(i);
		changeIndex.remove(i);
		int x = ci.getIndex().getX();
		int y = ci.getIndex().getY();
		if (ci.getChange() == 1) {
			if (map[x][y] == 0) {
				map[x][y] = 1;
			}else{
				map[x][y] = 3;
			}
		}else{
			if (map[x][y] == 0) {
				map[x][y] = 2;
			}else{
				map[x][y] = 3;
			}
		}
	}
	
	public static Vector<ChangeIndex> getChanged(){
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the changeIndex.
		 */
		return changeIndex;
	}
	
	/*
	 * 
	 */
	public static void addFlow(int x, int y, int direction){
		/*
		 * Requires: An Index and a direction..
		 * Modifies: flows.
		 * Effects: Add the flow in corresponding edge.
		 */
		if (direction < 0 || direction > 3) {
			return;
		}
		int i;
		switch (direction) {
		case Types.UP:
			 i = x*79 + (x-1)*80 + y;
			flows.incrementAndGet(i);
			break;
		case Types.DOWN:
			i = (x+1)*79 + x*80 + y;
			flows.incrementAndGet(i);
			break;
		case Types.LEFT:
			i = x*79 + x*80 + y-1;
			flows.incrementAndGet(i);
			break;
		case Types.RIGHT:
			i = x*79 + x*80 + y;
			flows.incrementAndGet(i);
			break;
		default:
			break;
		}
	}
	
	public static void addFlow_t(int x, int y, int direction){
		/*
		 * Requires: An Index and a direction..
		 * Modifies: flows.
		 * Effects: Add the flow in corresponding edge.
		 */
		if (direction < 0 || direction > 3) {
			return;
		}
		if (direction == Types.UP) {
			for (Iterator<ChangeIndex> iterator = changeIndex.iterator(); iterator.hasNext();) {
				ChangeIndex changeIndex2 = (ChangeIndex) iterator.next();
				if (changeIndex2.getIndex().equals(new Index(x-1, y)) && changeIndex2.getChange() == 2) {
					return;
				}
			}
		}else if (direction == Types.LEFT) {
			for (Iterator<ChangeIndex> iterator = changeIndex.iterator(); iterator.hasNext();) {
				ChangeIndex changeIndex2 = (ChangeIndex) iterator.next();
				if (changeIndex2.getIndex().equals(new Index(x, y-1)) && changeIndex2.getChange() == 1) {
					return;
				}
			}
		}else if (direction == Types.RIGHT) {
			for (Iterator<ChangeIndex> iterator = changeIndex.iterator(); iterator.hasNext();) {
				ChangeIndex changeIndex2 = (ChangeIndex) iterator.next();
				if (changeIndex2.getIndex().equals(new Index(x, y)) && changeIndex2.getChange() == 1) {
					return;
				}
			}
		}else {
			for (Iterator<ChangeIndex> iterator = changeIndex.iterator(); iterator.hasNext();) {
				ChangeIndex changeIndex2 = (ChangeIndex) iterator.next();
				if (changeIndex2.getIndex().equals(new Index(x, y)) && changeIndex2.getChange() == 2) {
					return;
				}
			}
		}
		int i;
		switch (direction) {
		case Types.UP:
			 i = x*79 + (x-1)*80 + y;
			flows.incrementAndGet(i);
			break;
		case Types.DOWN:
			i = (x+1)*79 + x*80 + y;
			flows.incrementAndGet(i);
			break;
		case Types.LEFT:
			i = x*79 + x*80 + y-1;
			flows.incrementAndGet(i);
			break;
		case Types.RIGHT:
			i = x*79 + x*80 + y;
			flows.incrementAndGet(i);
			break;
		default:
			break;
		}
	}
	
	public static void minusFlow(int x, int y, int direction){
		/*
		 * Requires: An Index and a direction..
		 * Modifies: flows.
		 * Effects: Minus the flow in corresponding edge.
		 */
		if (direction < 0 || direction > 3) {
			return;
		}
		int i;
		switch (direction) {
		case Types.UP:
			 i = x*79 + (x-1)*80 + y;
			flows.decrementAndGet(i);
			break;
		case Types.DOWN:
			i = (x+1)*79 + x*80 + y;
			flows.decrementAndGet(i);
			break;
		case Types.LEFT:
			i = x*79 + x*80 + y-1;
			flows.decrementAndGet(i);
			break;
		case Types.RIGHT:
			i = x*79 + x*80 + y;
			flows.decrementAndGet(i);
			break;
		default:
			break;
		}
	}
	
	public static void minusFlow_t(int x, int y, int direction){
		/*
		 * Requires: An Index and a direction..
		 * Modifies: flows.
		 * Effects: Minus the flow in corresponding edge.
		 */
		if (direction < 0 || direction > 3) {
			return;
		}
		if (direction == Types.UP) {
			for (Iterator<ChangeIndex> iterator = changeIndex.iterator(); iterator.hasNext();) {
				ChangeIndex changeIndex2 = (ChangeIndex) iterator.next();
				if (changeIndex2.getIndex().equals(new Index(x-1, y)) && changeIndex2.getChange() == 2) {
					return;
				}
			}
		}else if (direction == Types.LEFT) {
			for (Iterator<ChangeIndex> iterator = changeIndex.iterator(); iterator.hasNext();) {
				ChangeIndex changeIndex2 = (ChangeIndex) iterator.next();
				if (changeIndex2.getIndex().equals(new Index(x, y-1)) && changeIndex2.getChange() == 1) {
					return;
				}
			}
		}else if (direction == Types.RIGHT) {
			for (Iterator<ChangeIndex> iterator = changeIndex.iterator(); iterator.hasNext();) {
				ChangeIndex changeIndex2 = (ChangeIndex) iterator.next();
				if (changeIndex2.getIndex().equals(new Index(x, y)) && changeIndex2.getChange() == 1) {
					return;
				}
			}
		}else {
			for (Iterator<ChangeIndex> iterator = changeIndex.iterator(); iterator.hasNext();) {
				ChangeIndex changeIndex2 = (ChangeIndex) iterator.next();
				if (changeIndex2.getIndex().equals(new Index(x, y)) && changeIndex2.getChange() == 2) {
					return;
				}
			}
		}
		
		int i;
		switch (direction) {
		case Types.UP:
			 i = x*79 + (x-1)*80 + y;
			flows.decrementAndGet(i);
			break;
		case Types.DOWN:
			i = (x+1)*79 + x*80 + y;
			flows.decrementAndGet(i);
			break;
		case Types.LEFT:
			i = x*79 + x*80 + y-1;
			flows.decrementAndGet(i);
			break;
		case Types.RIGHT:
			i = x*79 + x*80 + y;
			flows.decrementAndGet(i);
			break;
		default:
			break;
		}
	}

	public static int getFlow(int x, int y, int direction){
		/*
		 * Requires: An Index and a direction..
		 * Modifies: Nothing.
		 * Effects: Return the flow in corresponding edge.
		 */
		if (direction < 0 || direction > 3) {
			return -1;
		}
		int i, flow;
		switch (direction) {
		case Types.UP:
			 i = x*79 + (x-1)*80 + y;
			 flow =flows.get(i);
			break;
		case Types.DOWN:
			i = (x+1)*79 + x*80 + y;
			flow = flows.get(i);
			break;
		case Types.LEFT:
			i = x*79 + x*80 + y-1;
			flow = flows.get(i);
			break;
		case Types.RIGHT:
			i = x*79 + x*80 + y;
			flow = flows.get(i);
			break;
		default:
			flow = -1;
			break;
		}
		return flow; 
	}
	
	public static boolean haslight(int x, int y) {
		/*
		 * Requires: Two integers.
		 * Modifies: Nothing.
		 * Effects: Return the light[x][y].isHas().
		 */
		return light[x][y].isHas();
	}
	
	public static boolean canPass(int x, int y, int di) {
		/*
		 * Requires: Three integers.
		 * Modifies: Nothing.
		 * Effects: Return the whether can pass.
		 */
		/*
		 * 0 lr
		 * 1 ud
		 */
		if (light[x][y].isHas() == false) {
			return true;
		}
		if ((di == 0 && light[x][y].getL_r() == 1) || (di == 1 && light[x][y].getU_d() == 1)) {
			return true;
		}
		return false;
	}
}
