import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class TokenMachine {
/*overview: TokenMachine manages the services offered by ServiceWindow to Customer in terms of tokens. 
 * Each Customer has to request a token to use his or her expected service. Each Token represents a request of service.  
 * All tokens are managed in queues according to the type of services requested. For each of the requested tokens, 
 * TokenMachine uses the FIFO mode to dispatch whenever a matching service window is ready to serve. 
 * Service window can plug or unplug itself to the TokenMachine to offer or stop its services. 
 * In general, data managed in this class can be defined as (waitingTokenList_Saving, waitingTokenList_Credit, sv_winList, servingTokenList, servedTokenList).
 * 
 * Please note that the following is just for implementing, not for using this class. 
 * Abstract Function can be defined as:
 * AF(tm) = (waitingTokenList_Saving, waitingTokenList_Credit, sv_winList, servedTokenList), where
 * waitingTokenList_Saving=tm.waitingSavingServiceTokens, waitingTokenList_Credit=tm.waitingCreditServiceTokens,
 * sv_winList = tm.pluggedWindows, servedTokenList=tm.servedServiceTokens.
 * The invariant of TokenMachine is:
 * I(tm)=tm.waitingSavingServiceTokens <> null && tm.waitingCreditServiceTokens <> null && tm.servedServiceTokens <> null
 *       && tm.pluggedWindows <> null 
 *       && (for any token in tm.waitingCreditServiceTokens or tm.waitingSavingServiceTokens, token.status == waiting)
 *       && (for any token in tm.servedServiceTokens, token.status == served)
 *       && (for any sw in tm.pluggedWindows, sw.status <> closed)
 *       && (tm.num_savingSW == size of the set {sw in tm.pluggedWindows|sw.type == savingService})
 *       && (tm.num_creditSW == size of the set {sw in tm.pluggedWindows|sw.type == creditService})
 *       && (if any token with status changed into served, should be removed from tm.waitingCreditServiceTokens or tm.waitingSavingServiceTokens,
 *           and managed in tm.servedServiceTokens)
 *       && (for any token t1 and t2 in tm.waitingCreditServiceTokens or tm.waitingSavingServiceTokens, t1 <> t2)
 *       && (for any token t1 and t2 in tm.servedServiceTokens, t1 <> t2)
 *       && (for any service window sw1 and sw2 in tm.pluggedWindows, sw1 <> sw2)
 *       && (for any token t1 in tm.servedServiceTokens, and any token t2 in tm.servingTokens, t1.ID < t2.ID if t1.wid == t2.wid)
 *       && (for any token t1 in tm.servingTokens, and any token t2 in tm.waitingCreditServiceTokens or tm.waitingSavingServiceTokens,
 *          t1.ID < t2.ID)
 *          
  
*/
//rep:
	private Queue<Token> waitingSavingServiceTokens;
	private Queue<Token> waitingCreditServiceTokens;
	private Queue<Token> servingTokens;
	private Queue<Token> servedServiceTokens;
	private Vector<ServiceWindow> pluggedWindows;
	private int count;
	private int num_savingSW;
	private int num_creditSW;
	
	public TokenMachine(){
	//Requires: none
	//Modifies: this
	//returns a valid this.	
		waitingSavingServiceTokens = new LinkedList<Token>();
		waitingCreditServiceTokens = new LinkedList<Token>();
		servedServiceTokens = new LinkedList<Token>();
		pluggedWindows = new Vector<ServiceWindow>();
		servingTokens = new LinkedList<Token>();
		
		count = 0;
		num_savingSW = 0;
		num_creditSW = 0;
	}
	
	synchronized public Token requestService(ServiceType type)
	//this method is for Customer to request a service token, and wait into the queue.
	//Requires: none
	//Modifies: this
	//Effects: if there is service window capable of handling such request, 
	//a new service token (of the type) with increasing ID will be returned, and managed according to type.
	//otherwise, a null token will be returned, and this remains unmodified.
	{
		int waits = 0;
		Token token = null;
		Queue<Token> queue;
		int num=0;
		
		if(type == ServiceType.creditService){
			queue = waitingCreditServiceTokens;
			num = num_creditSW;
		}
		else{
			queue = waitingSavingServiceTokens;
			num = num_savingSW;
		}
		
		if(num > 0){
			waits = queue.size();
			count++;
			token = new Token(count,type, waits);
			queue.add(token);
		}
		
		return token;
	}
	
	public boolean anyWaitingService(){
	//Requires: none
	//Modifies: none
	//Effects: returns true if there is waiting service, returns false otherwise.	
		return (!waitingSavingServiceTokens.isEmpty()||!waitingCreditServiceTokens.isEmpty());
	}
	
	synchronized public void plugServiceWindow(ServiceWindow win){
	//this method is for ServiceWindow to plug itself.
	//Requires: win is not null
	//Modifies: this
	//Effects: win will be added into the list of serving windows, and this will be ready for offering 
	//the type of service that win offers.
		if(!pluggedWindows.contains(win))pluggedWindows.addElement(win);
		if(win.getServiceType()==ServiceType.savingService)num_savingSW++;
		if(win.getServiceType()==ServiceType.creditService)num_creditSW++;
	}
	
	synchronized public void unPlugServiceWindow(ServiceWindow win){
	//Requires: win is not null, and its status is not in serving.
	//Modifies: this	
	//Effects: win is removed from the list offering services managed by this.
		if(pluggedWindows.contains(win)){
			if(win.getServiceType()==ServiceType.savingService)num_savingSW--;
			if(win.getServiceType()==ServiceType.creditService)num_creditSW--;
			pluggedWindows.remove(win);
		}
	}
	
	synchronized public void serveNext(ServiceWindow win){
	//this method is for scheduling thread to select an idle window to assign token to serve.
	//Requires: win is not null, and is idle for serving
	//Modifies: this
	//Effects: a waiting service token (if any) matched to the type of service provided by win will be polled,  
	//         and added into the serving list, all the lost tokens assigned to win will be removed, 
	//	       and win.Serve will be invoked to serve the selected token.
		Token token;
		Queue<Token> queue;
		Iterator<Token> iter=null;
		
		if(win.getStatus() != ServiceWindowStatus.idle)return;
		
		if(win.getServiceType()==ServiceType.creditService)
			queue = waitingCreditServiceTokens;
		else
			queue = waitingSavingServiceTokens;
		
		//here the chance to remove lost tokens.
		iter = servingTokens.iterator();
		while(iter.hasNext()){
			token = iter.next();
			if(token.ServingWindow()==win.getID())  //lost token
				servingTokens.remove(token);
		}
		
		token = queue.poll();  //token will not managed in the queue any way.
		if(token != null){
			servingTokens.add(token);
			token.AssignWindow(win.getID());
			win.Serve(token);
		}
	}
	
	synchronized public void tokenSatisfied(Token token){
	//this method is for Service Window to call whenever a token has been satisfied	
	//Requires: token is not null, and with serving status.
	//Modifies: this
	//Effects: if the token is found in the serving list, will be added into the served token list, and removed from the serving list.
	//         and Token.Satisified will be invoked to become a served token, otherwise this remains unmodified.	
		if(servingTokens.contains(token)){
			token.Satisfied();
			servedServiceTokens.add(token);
			servingTokens.remove(token);
		}
	}
	
	public ServiceWindow pickupWindow(){
	//this method is for scheduling thread to pick up service window to serve customers.	
	//Requires: none.
	//Modifies: none.
	//Effects: an idle service window will be picked and returned.	
		ServiceWindow sw;
		int i=0;
		for(i=0;i<pluggedWindows.size();i++){
			sw = pluggedWindows.get(i);
			if(sw.getStatus()==ServiceWindowStatus.idle)return sw;
		}
		return null;
	}
	
	public int numTokensRequested(){
	//Requires: none
	//Modifies: none
	//Effects: returns the number of tokens requested.	
		return count;
	}
	
	public int numTokensLost(){
	//Requires: none
	//Modifies: none
	//Effects: returns the number of tokens that are not present to service windows, i.e. lost.	
		return count - servedServiceTokens.size();
	}
	
	public int numTokensSatisfied(ServiceType type){
	//Requires: none
	//Modifies: none
	//Effects: returns the number of tokens satisfied according to type.	
		int num=0;
		Token token;
		Iterator<Token> t = servedServiceTokens.iterator();
		while(t.hasNext()){
			token = t.next();
			if(type == token.getServiceType())num++;
		}
		return num;
	}
	
	public long totalWaitingTime(){
	//Requires: none
	//Modifies: none
	//Effects: returns the total waiting time of all the served tokens.	
		long len=0;
		Token token;
		Iterator<Token> t = servedServiceTokens.iterator();
		while(t.hasNext()){
			token = t.next();
			len += token.WaitingTime();
		}
		return len;
	}
	
	public boolean repOK(){
	/* I(tm)=tm.waitingSavingServiceTokens <> null && tm.waitingCreditServiceTokens <> null && tm.servedServiceTokens <> null
		 *       && tm.pluggedWindows <> null 
		 *       && (for any token in tm.waitingCreditServiceTokens or tm.waitingSavingServiceTokens, token.status == waiting)
		 *       && (for any token in tm.servedServiceTokens, token.status == served)
		 *       && (for any sw in tm.pluggedWindows, sw.status <> closed)
		 *       && (tm.num_savingSW == size of the set {sw in tm.pluggedWindows|sw.type == savingService})
		 *       && (tm.num_creditSW == size of the set {sw in tm.pluggedWindows|sw.type == creditService})
		 *       && (if any token with status changed into served, should be removed from tm.waitingCreditServiceTokens or tm.waitingSavingServiceTokens,
		 *           and managed in tm.servedServiceTokens)
		 *       && (for any token t1 and t2 in tm.waitingCreditServiceTokens or tm.waitingSavingServiceTokens, t1 <> t2)
		 *       && (for any token t1 and t2 in tm.servedServiceTokens, t1 <> t2)
		 *       && (for any service window sw1 and sw2 in tm.pluggedWindows, sw1 <> sw2)
		 *       && (for any token t1 in tm.servedServiceTokens, and any token t2 in tm.servingTokens, t1.ID < t2.ID if t1.wid == t2.wid)
		 *       && (for any token t1 in tm.servingTokens, and any token t2 in tm.waitingCreditServiceTokens or tm.waitingSavingServiceTokens,
		 *          t1.ID < t2.ID)
	*/
		Token token, servingToken;
		Iterator<Token> iter, iter2;
		Iterator<ServiceWindow> w_iter;
		ServiceWindow sw;
		int nsaving=0,ncredit=0;
		Vector<Token> freqTokens = new Vector<Token>();
		Vector<ServiceWindow> freqWins = new Vector<ServiceWindow>();

		
		if(waitingSavingServiceTokens==null || waitingCreditServiceTokens==null || 
				servedServiceTokens==null || pluggedWindows==null || servingTokens == null) return false;

		iter = waitingSavingServiceTokens.iterator();
		while(iter.hasNext()){
			token = iter.next();
			if(token.getServiceType()==ServiceType.creditService)return false;
			if(token.getStatus()!=TokenStatus.waiting) return false;
			if(freqTokens.contains(token))return false;
			else freqTokens.add(token);
			iter2 = servingTokens.iterator();
			while(iter2.hasNext()){
				servingToken = iter2.next();
				if(token.getOrder()<=servingToken.getOrder())return false;
			}

		}
		
		freqTokens.clear();
		iter = waitingCreditServiceTokens.iterator();
		while(iter.hasNext()){
			token = iter.next();
			if(token.getServiceType()==ServiceType.savingService)return false;
			if(token.getStatus()!=TokenStatus.waiting) return false;
			if(freqTokens.contains(token))return false;
			else freqTokens.add(token);
			iter2 = servingTokens.iterator();
			while(iter2.hasNext()){
				servingToken = iter2.next();
				if(token.getOrder()<=servingToken.getOrder())return false;
			}
		}
		
		freqTokens.clear();
		iter = servedServiceTokens.iterator();
		while(iter.hasNext()){
			token = iter.next();
			if(waitingCreditServiceTokens.contains(token))return false;
			if(waitingSavingServiceTokens.contains(token))return false;
			if(token.getStatus()!=TokenStatus.served) return false;
			if(freqTokens.contains(token))return false;
			else freqTokens.add(token);
			
			iter2 = servingTokens.iterator();
			while(iter2.hasNext()){
				servingToken = iter2.next();
				if(token.getWindow()==servingToken.getWindow())
					if(token.getOrder()>=servingToken.getOrder())return false;
			}
		}
		
		freqTokens.clear();
		
		w_iter = pluggedWindows.iterator();
		while(w_iter.hasNext()){
			sw = w_iter.next();
			if(sw.getServiceType()==ServiceType.creditService)ncredit++;
			if(sw.getServiceType()==ServiceType.creditService)nsaving++;
			if(sw.getStatus() == ServiceWindowStatus.closed)return false;
			if(freqWins.contains(sw))return false;
			else freqWins.add(sw);
		}
		
		freqWins.clear();
		if(nsaving != num_savingSW || ncredit != num_creditSW) return false;
		
		return true;
	}
}
