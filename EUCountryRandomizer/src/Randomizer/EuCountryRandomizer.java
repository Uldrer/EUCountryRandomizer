package Randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EuCountryRandomizer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		boolean useRegionResult = false;
		String playerConfig = "", tierConfig = "", regionConfig = "", simpleResultFile = "", regionResultFile = "";
		
		if(!(args.length == 1 && args[0].equals("-d")))
		{
			// cmd argument handling
			if(!(args.length == 3 || args.length == 6))
			{
				// Incorrect parameters
				printHelp();
				return;
			}
			
			playerConfig = args[0];
			tierConfig = args[1];
			simpleResultFile = args[2];
			
			if(args.length == 6)
			{
				if(args[3].equals("-r"))
				{
					useRegionResult = true;
				}
				regionConfig = args[4];
				regionResultFile = args[5];
			}
		}
		else 
		{
			playerConfig = "resources/config/players.xml";
			tierConfig = "resources/config/tiers_europe.xml";
			regionConfig = "resources/config/regions_europe.xml";
			simpleResultFile = "resources/config/result_simple.xml";
			regionResultFile = "resources/config/result_region.xml";
			useRegionResult = true;
		}
		
		
		// Load player config
		Helpers.ConfigLoader configLoader = new Helpers.ConfigLoader();
		List<Player> playerList = configLoader.readPlayerConfig(playerConfig);
		
		// Load tier config
		List<Tier> tierList = configLoader.readTierConfig(tierConfig);
		
		// Load region config
		List<Region> regionList = configLoader.readRegionConfig(regionConfig);
		
		// Simple random function
		List<Result> result1 = getSimpleRandomization(playerList, tierList);
		
		// Save result to config
		Helpers.ConfigSaver configSaver = new Helpers.ConfigSaver();
		configSaver.saveResultConfig(simpleResultFile, result1);
		
		if(useRegionResult)
		{
			List<Result> result2 = getRegionRandomization(playerList, tierList, regionList);
			configSaver.saveResultConfig(regionResultFile, result2);
		}
		
		
		
	}
	
	private static void printHelp()
	{
		System.out.println("Usage: EuCountryRandomizer <player_config> <tier_config>  <result_simple_file> [-r <region_config> <result_region_file>]");
	}
	
	private static Random rand = new Random();
	
	private static List<Result> getSimpleRandomization(List<Player> players, List<Tier> tiers)
	{
		List<Result> results = new ArrayList<Result>();
		
		// Copy tiers list
		List<Tier> tierCopy = new ArrayList<Tier>();
		for(Tier t : tiers)
		{
			tierCopy.add(new Tier(t));
		}
		
		// Randomize countries according to tier without weight
		for(Player player : players)
		{
			// Find correct tier
			for(Tier tier : tierCopy)
			{
				if(tier.getId() == player.getTier())
				{
					// Choose a country index at random
					int countryIndex = rand.nextInt(tier.getCountries().size());
					
					// Get the chosen country and remove it from tier list to avoid doubles
					Country chosenCountry = tier.removeCountry(countryIndex);
					
					// Add result to results
					results.add(new Result(chosenCountry, player));
					break;
				}
			}
		}
		
		return results;
	}
	
	private static List<Result> getRegionRandomization(List<Player> players, List<Tier> tiers, List<Region> regions)
	{
		List<Result> results = new ArrayList<Result>();
		
		// Randomize countries according to tier and region, random walk until all scores are ok
		boolean done = false;
		int counter = 1;
		while(!done)
		{
			done = randomRegionWalk(players, tiers, regions, results);
			counter++;
		}
		
		System.out.println("Region iterations required: " + counter);
		
		return results;
	}
	
	private static boolean randomRegionWalk(List<Player> players, List<Tier> tiers, List<Region> regions, List<Result> results)
	{
		// Clear results
		results.clear();
		
		// Copy tiers list
		List<Tier> tierCopy = new ArrayList<Tier>();
		for(Tier t : tiers)
		{
			tierCopy.add(new Tier(t));
		}
				
		for(Player player : players)
		{
			// Find correct tier
			for(Tier tier : tierCopy)
			{
				if(tier.getId() == player.getTier())
				{
					// Choose a country index at random
					int countryIndex = rand.nextInt(tier.getCountries().size());
					
					// Get the chosen country and remove it from tier list to avoid doubles
					Country chosenCountry = tier.removeCountry(countryIndex);
					
					// Add result to results
					results.add(new Result(chosenCountry, player));
					break;
				}
			}
		}
		
		List<Integer> regionScore = new ArrayList<Integer>();
		
		// Check that region scores are ok
		for(Region region : regions)
		{
			int score = region.getScore();
			int currentScore = 0;
			
			for(Result res : results)
			{
				if(region.Contains(res.getCountry().getName()))
				{
					currentScore += tierCopy.size() + 1 - res.getPlayer().getTier();
				}
			}
			regionScore.add(currentScore);
			
			if(currentScore > score) return false;
		}
		
		System.out.println("Score for regions;");
		int count = 0;
		for(Integer i : regionScore)
		{
			System.out.println("Region " + regions.get(count).getName() + ", end_score: " + i);
			count++;
		}
		
		return true;
		
	}

}
