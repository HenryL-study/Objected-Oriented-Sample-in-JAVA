
public class ServiceWindow {
	private int winID;
	private ServiceType type;
	private int tokensServed;
	private long servingTime; //records the length of time of serving
	private long openTimePoint;//records the time when the servingToken was served 
	private ServiceWindowStatus status;
	Token servingToken;
	TokenMachine t_machine;
	
	public ServiceWindow(int wID, ServiceType t,TokenMachine tm){
	//Requires: tm is not null
		winID = wID;
		type = t;
		tokensServed = 0;
		servingTime=0;
		openTimePoint = 0;
		servingToken = null;
		status = ServiceWindowStatus.closed;
		t_machine = tm;
		
	}
	
	public int RequestsServed(){
		return tokensServed;
	}
	
	public void Open(){
		if (status == ServiceWindowStatus.closed) status = ServiceWindowStatus.idle;
		servingTime = System.currentTimeMillis();
		t_machine.plugServiceWindow(this);
	}
	
	public boolean Close(){
		if (status == ServiceWindowStatus.idle){
			status = ServiceWindowStatus.closed;
			servingTime += System.currentTimeMillis()-openTimePoint;
			t_machine.unPlugServiceWindow(this);   //unplug itself from TokenMachine whenever it is closed
			return true;
		}
		return false;
	}
	
	public boolean Serve(Token token){
	//Requires: assigned token is not null, and matches the type of service offered by this window
	//Modifies: this
	//Effects: returns false if this windows is not idle; otherwise returns true, changes the status into serving, records the token
	//being served and increases the counts of tokens served.
		if (status == ServiceWindowStatus.idle){
			servingToken = token;
			tokensServed ++;
			status = ServiceWindowStatus.serving;
			Serving();
			return true;
		}
		return false;
	}
	
	private void Serving(){
		System.out.println("Is Serving:" + servingToken.toString());
	}
	
	public Token FinishCurrService(){
	//Requires: none
	//Modifies: this
	//Effects: returns null if this is not in serving status, otherwise returns the served token, changes the status to idle,
	//         and notify TokenMachine that the current token has been satisfied.
		if(status != ServiceWindowStatus.serving)return null;
		else{
			status = ServiceWindowStatus.idle;
			t_machine.tokenSatisfied(servingToken);
			System.out.println("Serving Finished for:" + servingToken.toString());
			return servingToken;
		}	
	}
	
	public ServiceType getServiceType(){
		return type;
	}

	public ServiceWindowStatus getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return winID;
	}
	
	public long getTotalServingTime(){
		if(servingTime == 0) servingTime = System.currentTimeMillis()-openTimePoint;
		return servingTime;
	}
}
