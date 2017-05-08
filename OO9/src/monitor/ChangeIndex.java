package monitor;

public class ChangeIndex {
	private Index index;
	/*
	 * 1 right road 
	 * 2 down road
	 */
	private int change;
	
	public boolean repOK() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the true if the rep variant holds for this. otherwise return false.
		 */
		if (index != null && 0 <= change && change <=3) {
			return true;
		}else {
			return false;
		}
	}
	
	public ChangeIndex(Index index, int change) {
		/*
		 * Requires: One Index variable and the change direction.
		 * Modifies: Nothing.
		 * Effects: Construct a ChangeIndex.
		 */
		this.index = index;
		this.change = change;
	}

	/**
	 * @return the index
	 */
	public Index getIndex() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the index.
		 */
		return index;
	}

	/**
	 * @return the change
	 */
	public int getChange() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the change.
		 */
		return change;
	}
	
}
