package monitor;

public class Index {
	
	private final int x;
	private final int y;
	
	public boolean repOK() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the true if the rep variant holds for this. otherwise return false.
		 */
		return true;
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the x.
		 */
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the y.
		 */
		return y;
	}

	public Index(int x, int y) {
		/*
		 * Requires: Two integer.
		 * Modifies: Nothing.
		 * Effects: Construct a index.
		 */
		this.x = x;
		this.y = y;
	}
	
	
	
}
