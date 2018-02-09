package Virtual_Totem;

public class Totem_VT {
	
	private boolean wolf_taken;
	private boolean dragon_taken;
	private String name_wolf_taken;
	private String name_dragon_taken;
	
	public Totem_VT(){
		wolf_taken=false;
		dragon_taken=false;
		name_wolf_taken="";
		name_dragon_taken="";
	}
	
	public void copy_Totem(Totem_VT totem) {
		this.wolf_taken=totem.iswolf_taken();
		this.dragon_taken=totem.isdragon_taken();
		this.name_wolf_taken=totem.getName_wolf_taken();
		this.name_dragon_taken=totem.getName_dragon_taken();
	}
	
	public boolean iswolf_taken() {
		return wolf_taken;
	}

	public void setwolf_taken(boolean wolf_taken) {
		this.wolf_taken = wolf_taken;
	}

	public boolean isdragon_taken() {
		return dragon_taken;
	}

	public void setdragon_taken(boolean dragon_taken) {
		this.dragon_taken = dragon_taken;
	}

	public String getName_wolf_taken() {
		return name_wolf_taken;
	}

	public void setName_wolf_taken(String name_wolf_taken) {
		this.name_wolf_taken = name_wolf_taken;
	}

	public String getName_dragon_taken() {
		return name_dragon_taken;
	}

	public void setName_dragon_taken(String name_dragon_taken) {
		this.name_dragon_taken = name_dragon_taken;
	}
}
