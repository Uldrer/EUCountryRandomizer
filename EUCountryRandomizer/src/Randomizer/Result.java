package Randomizer;

public class Result {
	private Country country;
	private Player player;
	
	public Result()
	{
		this.country = null;
		this.player = null;
	}
	
	public Result(Country country, Player player)
	{
		this.country = country;
		this.player = player;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public String toString() {
		
		if(this.player == null || this.country == null) return "Failed result";
		
		return "Result: " + this.player.getName() + " - " + this.country.getName();
	}
	
	
	
	
}
