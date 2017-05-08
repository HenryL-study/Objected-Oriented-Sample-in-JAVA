package ele;

public class Floor {

	private boolean[] floors;
	
	public Floor() {
		// TODO Auto-generated constructor stub
		floors = new boolean[21];
		for (int i = 0; i < floors.length; i++) {
			floors[i] = false;
		}
	}
	public boolean getFloor(int i) {
		return floors[i];
	}
	
	public void setFloor(int i, boolean re) {
		floors[i] = re;
	}

}
