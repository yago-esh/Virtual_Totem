package Virtual_Totem;

public class Totem_VT {
	
	private boolean wolf;
	private boolean dragon;
	private String name_wolf;
	private String name_dragon;
	
	public Totem_VT(){
		wolf=false;
		dragon=false;
		name_wolf="";
		name_dragon="";
	}
	
	public void copy_Totem(Totem_VT totem) {
		this.wolf=totem.isWolf();
		this.dragon=totem.isDragon();
		this.name_wolf=totem.getName_wolf();
		this.name_dragon=totem.getName_dragon();
	}
	
	public boolean isWolf() {
		return wolf;
	}

	public void setWolf(boolean wolf) {
		this.wolf = wolf;
	}

	public boolean isDragon() {
		return dragon;
	}

	public void setDragon(boolean dragon) {
		this.dragon = dragon;
	}

	public String getName_wolf() {
		return name_wolf;
	}

	public void setName_wolf(String name_wolf) {
		this.name_wolf = name_wolf;
	}

	public String getName_dragon() {
		return name_dragon;
	}

	public void setName_dragon(String name_dragon) {
		this.name_dragon = name_dragon;
	}
}
