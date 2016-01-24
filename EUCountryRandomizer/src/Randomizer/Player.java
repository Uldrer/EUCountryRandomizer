package Randomizer;

public class Player {

	private int id;
	private int tier;
	private String name;
	
	public Player(String name, int id, int tier)
	{
		this.name = name;
		this.id = id;
		this.tier = tier;
	}
	
	public Player()
	{
		this.name = "Default";
		this.id = 0;
		this.tier = 1;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString()
	{
		return "<" + id + ", " + name + ", " + tier + ">";
	}
	
}
