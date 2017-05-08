package monitor;

public class ChangeIndex {
	private Index index;
	/*
	 * 1 right road 
	 * 2 down road
	 */
	private int change;
	
	public ChangeIndex(Index index, int change) {
		/*
		 * Requires: Two Index variables.
		 * Modifies: this
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
