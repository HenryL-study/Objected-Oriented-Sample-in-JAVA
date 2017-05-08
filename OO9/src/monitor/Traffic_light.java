package monitor;

public class Traffic_light{
	private boolean has;
	/*
	 *  1 green
	 * -1 red
	 */
	private int l_r;
	private int u_d;
	
	public boolean repOK() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the true if the rep variant holds for this. otherwise return false.
		 */
		if (Math.abs(l_r) == 1 && Math.abs(u_d) == 1) {
			return true;
		}
		return false;
	}
	public Traffic_light(boolean has) {
		/*
		 * Requires: A boolean.
		 * Modifies: Nothing.
		 * Effects: Initialize a traffic light.
		 */
		this.has = has;
		if (has) {
			this.l_r = 1;
			this.u_d = -1;
		}else {
			this.l_r = 0;
			this.u_d = 0;
		}
		
	}
	/**
	 * @return the has
	 */
	public boolean isHas() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the has.
		 */
		return has;
	}
	/**
	 * @return the l_r
	 */
	public int getL_r() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the l_r.
		 */
		return l_r;
	}
	/**
	 * @return the u_d
	 */
	public int getU_d() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the u_d.
		 */
		return u_d;
	}
	public void change() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Change the light status..
		 */
			l_r = 0-l_r;
			u_d = 0-u_d;
	}
	

}
