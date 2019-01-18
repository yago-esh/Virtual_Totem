package Virtual_Totem;

import java.util.ArrayList;

public class Server_Client {
	
	private String name, totemTopUser, totemBotUser;
	private boolean totemTopTaken, totemBotTaken;
	private ArrayList<String> totemTopList, totemBotList;
	private ArrayList<Integer> totemTopControlList, totemBotControlList;
	private Integer totemTopTime[], totemBotTime[];
	
	public Server_Client(String name) {
		this.name = name;
		
		totemTopList = new ArrayList<String>();
		totemBotList = new ArrayList<String>();
		totemTopControlList = new ArrayList<Integer>();
		totemBotControlList = new ArrayList<Integer>();
		
		totemTopTime = new Integer[3];
		totemTopTime[0]=1;
		totemTopTime[1]=0;
		totemTopTime[2]=0;
		totemBotTime = new Integer[3];
		totemBotTime[0]=1;
		totemBotTime[1]=0;
		totemBotTime[2]=0;
	}
	
	public void manageList() {
		
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
	public int getTotemTopTime() {
		return totemTopTime[0]+totemTopTime[1]+totemTopTime[2];
	}
	
	public String getTotemTopTimeParse() {
		return totemTopTime[2]+":"+totemTopTime[1]+":"+totemTopTime[0];
	}
	public void setTotemTopTime() {
		totemTopTime[0]=1;
		totemTopTime[1]=0;
		totemTopTime[2]=0;
	}
	public int getTotemBotTime() {
		return totemBotTime[0]+totemBotTime[1]+totemBotTime[2];
	}
	
	public String getTotemBotTimeParse() {
		return totemBotTime[2]+":"+totemBotTime[1]+":"+totemBotTime[0];
	}
	
	public void setTotemBotTime() {
		totemBotTime[0]=1;
		totemBotTime[1]=0;
		totemBotTime[2]=0;
	}
	
	public void totemTopTimeIncrement() {
		if(totemTopTime[0]==60) {
			totemTopTime[0]=0;
			totemTopTime[1]+=1;
		 }
		 if(totemTopTime[1]==60) {
			 totemTopTime[1]=0;
			 totemTopTime[2]+=1;
		 }
		 totemTopTime[0]++;
	}
	
	public void totemBotTimeIncrement() {
		if(totemBotTime[0]==60) {
			totemBotTime[0]=0;
			totemBotTime[1]+=1;
		 }
		 if(totemBotTime[1]==60) {
			 totemBotTime[1]=0;
			 totemBotTime[2]+=1;
		 }
		 totemBotTime[0]++;
	}
	
}
