package monitor;

public class Light_ctl extends Thread{
	private Traffic_light[][] light;
	
	public boolean repOK() {
		/*
		 * Requires: Nothing.
		 * Modifies: Nothing.
		 * Effects: Return the true if the rep variant holds for this. otherwise return false.
		 */
		if (light != null) {
			return true;
		}
		return false;
	}
	
	public Light_ctl(Traffic_light[][] light) {
		/*
		 * Requires: two-dimensional array of Traffic_light.
		 * Modifies: Nothing.
		 * Effects: Initialize the light..
		 */
		this.light = light;
	}



	@Override
	public void run(){
		while (true) {
			for (int i = 0; i < Types.size; i++) {
				for (int j = 0; j < Types.size; j++) {
					light[i][j].change();
				}
			}
			try {
				sleep(Types.BASE_TIME*3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
