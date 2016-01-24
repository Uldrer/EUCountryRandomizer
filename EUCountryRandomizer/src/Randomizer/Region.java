package Randomizer;

import java.util.ArrayList;
import java.util.List;

public class Region {
	private String name;
	private int id;
	private int score;
	private List<Country> countries;
	
	public Region()
	{
		name = "Default";
		id = 0;
		countries = new ArrayList<Country>();
		score = 7;
	}

	public void addCountry(Country country)
	{
		if(countries != null)
		{
			if(!countries.contains(country))
			{
				countries.add(country);
			}
		}
	}
	
	public boolean Contains(String countryName)
	{
		for(Country c : countries)
		{
			if(c.getName().equals(countryName))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		String output = "Region [name=" + name + ", id=" + id + ", score=" + score + ", ";
		
		for(Country c : countries)
		{
			output += c.getName() + ", ";
		}
		
		output += "]";
		
		return output;
	}
}
