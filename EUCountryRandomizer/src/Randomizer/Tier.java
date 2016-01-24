package Randomizer;

import java.util.ArrayList;
import java.util.List;

public class Tier {

	private int id;
	private List<Country> countryList;
	
	public Tier()
	{
		this.countryList = new ArrayList<Country>();
		this.id = 0;
	}
	
	public Tier(Tier tier)
	{
		this.countryList = new ArrayList<Country>();
		this.id = tier.getId();
		
		for(Country c : tier.getCountries())
		{
			this.countryList.add(c);
		}
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public List<Country> getCountries()
	{
		return countryList;
	}
	
	public void addCountry(Country country)
	{
		if(countryList != null)
		{
			if(!countryList.contains(country))
			{
				countryList.add(country);
			}
		}
	}
	
	public Country removeCountry(int index)
	{
		if(index < countryList.size())
		{
			Country indexCountry = countryList.get(index);
			countryList.remove(index);
			return indexCountry;
		}
		else 
		{
			return new Country();
		}
	}
	
	@Override
	public String toString()
	{
		String output = "<" + id + ", ";
		
		for(Country c : countryList)
		{
			output += c.toString() + ", ";
		}
		
		output += ">";
		
		return output;
		
	}
}
