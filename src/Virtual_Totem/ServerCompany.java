package Virtual_Totem;

import java.util.ArrayList;

public class ServerCompany {
	
	private String name, totemTopUser, totemBotUser;
	private boolean totemTopTaken, totemBotTaken;
	private ArrayList<String> totemTopList, totemBotList;
	private ArrayList<Integer> totemTopControlList, totemBotControlList;
	private Integer totemTopTimer[], totemBotTimer[];
	
	public ServerCompany(String name) {
		this.name = name;
		
		totemTopList = new ArrayList<String>();
		totemBotList = new ArrayList<String>();
		totemTopControlList = new ArrayList<Integer>();
		totemBotControlList = new ArrayList<Integer>();
		
		//Set the timer to 1 second instead of 0 to have a better synchronization with the clients
		totemTopTimer = new Integer[3];
		totemTopTimer[0]=1;
		totemTopTimer[1]=0;
		totemTopTimer[2]=0;
		totemBotTimer = new Integer[3];
		totemBotTimer[0]=1;
		totemBotTimer[1]=0;
		totemBotTimer[2]=0;
	}
	
	public String getName() {
		return name;
	}

	public String getTotemTopUser() {
		return totemTopUser;
	}
	public void setTotemTopUser(String totemTopUser) {
		this.totemTopUser = totemTopUser;
	}
	public String getTotemBotUser() {
		return totemBotUser;
	}
	public void setTotemBotUser(String totemBotUser) {
		this.totemBotUser = totemBotUser;
	}
	public boolean isTotemTopTaken() {
		return totemTopTaken;
	}
	public void setTotemTopTaken(boolean totemTopTaken) {
		this.totemTopTaken = totemTopTaken;
	}
	public boolean isTotemBotTaken() {
		return totemBotTaken;
	}
	public void setTotemBotTaken(boolean totemBotTaken) {
		this.totemBotTaken = totemBotTaken;
	}
	public ArrayList<String> getTotemTopList() {
		return totemTopList;
	}
	public void setTotemTopList(ArrayList<String> totemTopList) {
		this.totemTopList = totemTopList;
	}
	public ArrayList<String> getTotemBotList() {
		return totemBotList;
	}
	public void setTotemBotList(ArrayList<String> totemBotList) {
		this.totemBotList = totemBotList;
	}
	public ArrayList<Integer> getTotemTopControlList() {
		return totemTopControlList;
	}
	public void setTotemTopControlList(ArrayList<Integer> totemTopControlList) {
		this.totemTopControlList = totemTopControlList;
	}
	public ArrayList<Integer> getTotemBotControlList() {
		return totemBotControlList;
	}
	public void setTotemBotControlList(ArrayList<Integer> totemBotControlList) {
		this.totemBotControlList = totemBotControlList;
	}
	
	public boolean isTotemTopTimerStoped() {
		//Only when the timer is set to 0:0:1h means that is stopped
		return (totemTopTimer[0]+(totemTopTimer[1]*60)+(totemTopTimer[2]*3600) == 1);
	}
	
	public String getTotemTopTimer() {
		//Return the timer in a String mode
		return totemTopTimer[2]+":"+totemTopTimer[1]+":"+totemTopTimer[0];
	}
	public void setTotemTopTimerToCero() {
		//Set the timer to 1 (Because of the synchronization)
		totemTopTimer[0]=1;
		totemTopTimer[1]=0;
		totemTopTimer[2]=0;
	}

	public boolean isTotemBotTimerStoped() {
		//Only when the timer is set to 0:0:1h means that is stopped
		return (totemBotTimer[0]+(totemBotTimer[1]*60)+(totemBotTimer[2]*3600) == 1);
	}
	
	public String getTotemBotTimer() {
		//Return the timer in a String mode
		return totemBotTimer[2]+":"+totemBotTimer[1]+":"+totemBotTimer[0];
	}
	
	public void setTotemBotTimerToCero() {
		//Set the timer to 1 (Because of the synchronization)
		totemBotTimer[0]=1;
		totemBotTimer[1]=0;
		totemBotTimer[2]=0;
	}
	
	public void totemTopTimeIncrement() {
		//Increment the timer in 1 second
		if(totemTopTimer[0]==60) {
			totemTopTimer[0]=0;
			totemTopTimer[1]+=1;
		 }
		 if(totemTopTimer[1]==60) {
			 totemTopTimer[1]=0;
			 totemTopTimer[2]+=1;
		 }
		 totemTopTimer[0]++;
	}
	
	public void totemBotTimeIncrement() {
		//Increment the timer in 1 second
		if(totemBotTimer[0]==60) {
			totemBotTimer[0]=0;
			totemBotTimer[1]+=1;
		 }
		 if(totemBotTimer[1]==60) {
			 totemBotTimer[1]=0;
			 totemBotTimer[2]+=1;
		 }
		 totemBotTimer[0]++;
	}
	
}
