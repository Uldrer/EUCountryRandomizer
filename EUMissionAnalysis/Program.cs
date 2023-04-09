// See https://aka.ms/new-console-template for more information
using System;
using System.Collections;
using System.Linq;

namespace EUMissionAnalysis
{
    class Program
    {
        static void Main(string[] args)
        {
            string fileName1 = "";
            string fileName2 = "";
            if (args.Length == 0)
            {
                Console.WriteLine("Usage: EUMissionAnalysis.exe <save_file_name_1> | <save_file_name_2>");
                return;
            }
            else if(args.Length == 1)
            {
                fileName1 = args[0];
            }
            else
            {
                fileName1 = args[0];
                fileName2 = args[1];
            }

            string dirPath = Directory.GetCurrentDirectory();
            
            string[] linesCountries = System.IO.File.ReadAllLines(dirPath + "\\" + "countries.txt");

            Dictionary<string, string> countryDict = new Dictionary<string, string>();
            
            foreach (string line in linesCountries)
            {
                // Use a tab to indent each line of the file.
                Console.WriteLine("\t" + line);

                string[] splitString = line.Split("\t".ToCharArray());

                if (splitString.Length > 1)
                {
                    countryDict.Add(splitString[0], splitString[1]);
                }
            }

            string[] linesPlayers = System.IO.File.ReadAllLines(dirPath + "\\" + fileName1);

            Dictionary<string, string> playerDict = new Dictionary<string, string>();

            int indexStart = 2;
            int count = 0;
            string playerName = "";
            foreach (string line in linesPlayers)
            {
                if(count >= indexStart)
                {
                    if(count % 2 == 0)
                    {
                        playerName = line.Replace("\t","");
                    }
                    else
                    {
                        string playerId = line.Replace("\t", "");
                        playerDict.Add(playerId, playerName.Replace("\"",""));
                    }
                }
                count++;

                if (count > 15) break;
            }


            Dictionary<string, string> religionDict = new Dictionary<string, string>();
            Dictionary<string, string> provinceDict = new Dictionary<string, string>();
            List<string> freeCityList = new List<string>();
            Dictionary<string, int> provinceDiffDict = new Dictionary<string, int>();
            Dictionary<string, string> overlordDict = new Dictionary<string, string>();

            // Handle several files over files if present
            if (args.Length > 1) 
            {
                Dictionary<string, string> tempReligionDict = new Dictionary<string, string>();
                Dictionary<string, string> tempProvinceDict = new Dictionary<string, string>();
                List<string> tempFreeCityList = new List<string>();
                Dictionary<string, string> tempOverlordDict = new Dictionary<string, string>();

                ParseSaveFile(dirPath, fileName1, countryDict, tempReligionDict, tempProvinceDict, tempFreeCityList, tempOverlordDict);
                ParseSaveFile(dirPath, fileName2, countryDict, religionDict, provinceDict, freeCityList, overlordDict);

                foreach(var countryKey in provinceDict.Keys)
                {
                    int valEarly = Int32.Parse(tempProvinceDict[countryKey]);
                    int valLate = Int32.Parse(provinceDict[countryKey]);
                    provinceDiffDict.Add(countryKey, valLate - valEarly);
                }
            }
            else
            {
                ParseSaveFile(dirPath, fileName1, countryDict, religionDict, provinceDict, freeCityList, overlordDict);
            }
            


            // Printouts combined
            foreach (var country in countryDict.Keys)
            {
                bool freeCity = freeCityList.Contains(countryDict[country]);
                bool hasDiff = provinceDiffDict.Count > 0;
                if (religionDict.ContainsKey(countryDict[country]) && provinceDict.ContainsKey(countryDict[country]))
                {
                    Console.WriteLine("country= " + country + ", religion= " + religionDict[countryDict[country]] + ", provinces= " + provinceDict[countryDict[country]] + " freeCity: " + freeCity + ", " + (hasDiff? " has province diff: " + provinceDiffDict[countryDict[country]] : " has no known province diff." ) );
                }
                else if (religionDict.ContainsKey(countryDict[country]))
                {
                    Console.WriteLine("country= " + country + ", religion= " + religionDict[countryDict[country]] + ", provinces= NOT FOUND " + " freeCity: " + freeCity + ", " + (hasDiff ? " has province diff: " + provinceDiffDict[countryDict[country]] : " has no known province diff."));
                }
                else if (provinceDict.ContainsKey(countryDict[country]))
                {
                    Console.WriteLine("country= " + country + ", religion= NOT FOUND" + ", provinces= " + provinceDict[countryDict[country]] + " freeCity: " + freeCity + ", " + (hasDiff ? " has province diff: " + provinceDiffDict[countryDict[country]] : " has no known province diff."));
                }
                else
                {
                    Console.WriteLine("country= " + country + " NOT FOUND");
                }
            }
            Console.WriteLine();
            Console.WriteLine("FREE Cities");
            // Printouts free cities
            foreach (var country in countryDict.Keys)
            {
                bool freeCity = freeCityList.Contains(countryDict[country]);
                if(freeCity)
                    Console.WriteLine(country);
            }
            Console.WriteLine();
            Console.WriteLine("Expanded countries");
            // Printouts expanded countries
            foreach (var country in countryDict.Keys)
            {
                int diff = provinceDiffDict[countryDict[country]];
                if(diff >= 2)
                {
                    Console.WriteLine(country);
                }
            }
            Console.WriteLine();
            Console.WriteLine("Destroyed countries");
            // Printouts destroyed countries
            foreach (var country in countryDict.Keys)
            {
                string num = provinceDict[countryDict[country]];
                if (num == "0")
                {
                    Console.WriteLine(country);
                }
            }
            Console.WriteLine();

            Console.WriteLine("Subject countries");
            // Printouts subject countries
            foreach (var country in countryDict.Keys)
            {
                if(overlordDict.ContainsKey(countryDict[country]))
                {
                    string overlord = overlordDict[countryDict[country]];
                    if (playerDict.ContainsKey(overlord))
                    {
                        Console.WriteLine(country + " : " + playerDict[overlord]);
                    }
                    else
                    {
                        string noCitationOverlord = overlord.Replace("\"", "");
                        if (countryDict.ContainsValue(noCitationOverlord))
                        {
                            string prettyOverlord = countryDict.FirstOrDefault(x => x.Value == noCitationOverlord).Key;
                            Console.WriteLine(country + " : " + prettyOverlord);
                        }
                        else
                        {
                            Console.WriteLine(country + " : " + overlord);
                        }
                    }
                }
            }
            Console.WriteLine();

            Console.WriteLine("Free countries");
            // Printouts free countries
            foreach (var country in countryDict.Keys)
            {
                if (!overlordDict.ContainsKey(countryDict[country]))
                {
                    int numberOfProvinces = Int32.Parse(provinceDict[countryDict[country]]);

                    if(numberOfProvinces > 0)
                    {
                        Console.WriteLine(country);
                    }
                }
            }
            Console.WriteLine();

            var stop2 = Console.ReadLine();
        }

        public static void ParseSaveFile(string dirPath, string fileName, Dictionary<string, string> countryDict, Dictionary<string, string> religionDict, Dictionary<string, string> provinceDict, List<string> freeCityList, Dictionary<string, string> overlordDict)
        {
            string[] linesSave = System.IO.File.ReadAllLines(dirPath + "\\" + fileName);
            

            foreach (var country in countryDict.Values)
            {
                string searchStringCountry = "\t" + country + "={";
                string searchStringGovRank1 = "\t\tgovernment_rank=";
                string searchStringGovRank2 = "\t\thas_set_government_name=";
                string searchStringFreeCity = "\t\tgovernment_name=\"german_free_city\"";
                string searchStringReligion = "\t\treligion=";
                string searchStringOverlord = "\t\toverlord=";
                string searchStringCities = "\t\tnum_of_cities=";
                string searchStringEnd = "\t}";
                bool foundCountryTag = false;
                bool foundGovTag = false;
                bool foundReligion = false;
                foreach (string line in linesSave)
                {

                    if(line == searchStringCountry)
                    {
                        foundCountryTag = true;
                    }
                    else if(foundCountryTag && !foundGovTag)
                    {
                        // Check if next is goverment rank
                        if(line.StartsWith(searchStringGovRank1) || line.StartsWith(searchStringGovRank2))
                        {
                            foundGovTag = true;
                        }
                        else
                        {
                            // Some other country entry, reset
                            foundCountryTag = false;
                        }
                    }
                    else if(foundGovTag && !foundReligion)
                    {
                        if (line.StartsWith(searchStringReligion))
                        {
                            // Correctus
                            religionDict.Add(country, line.Substring(searchStringReligion.Length));
                            foundReligion = true;
                        }
                        else if(line.Equals(searchStringFreeCity))
                        {
                            freeCityList.Add(country);
                        }
                        else if (line == searchStringEnd)
                        {
                            // Came to the end
                            religionDict.Add(country, "None");
                            provinceDict.Add(country, "0");
                            break;
                        }
                        else
                        {
                            // keep searching we just have to go further down
                        }
                    }
                    else if(foundReligion)
                    {
                        if (line.StartsWith(searchStringOverlord))
                        {
                            // Correctus
                            overlordDict.Add(country, line.Substring(searchStringOverlord.Length));
                        }
                        else if (line.StartsWith(searchStringCities))
                        {
                            // Correctus
                            provinceDict.Add(country, line.Substring(searchStringCities.Length));
                            break;
                        }
                        else if(line == searchStringEnd)
                        {
                            // Came to the end
                            provinceDict.Add(country, "0");
                            break;
                        }
                        else
                        {
                            // keep searching we just have to go further down
                        }
                    }
                    else
                    {
                        // Don't care
                    }
                }
            }
        }
    }
}
