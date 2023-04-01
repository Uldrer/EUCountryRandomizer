// See https://aka.ms/new-console-template for more information
using System.Collections;
namespace EUMissionAnalysis
{
    class Program
    {
        static void Main(string[] args)
        {
            string fileName;
            if(args.Length == 0)
            {
                Console.WriteLine("Usage: EUMissionAnalysis.exe <save_file_name>");
                return;
            }
            else
            {
                fileName = args[0];
            }

            string dirPath = Directory.GetCurrentDirectory();
            string[] linesSave = System.IO.File.ReadAllLines(dirPath + "\\" + fileName);
            string[] linesCountries = System.IO.File.ReadAllLines(dirPath + "\\" + "countries.txt");

            Dictionary<string, string> countryDict= new Dictionary<string, string>();

            foreach (string line in linesCountries)
            {
                // Use a tab to indent each line of the file.
                Console.WriteLine("\t" + line);

                string[] splitString = line.Split("\t".ToCharArray());

                if(splitString.Length > 1)
                {
                    countryDict.Add(splitString[0], splitString[1]);
                }
            }

            //var stop1 = Console.ReadLine();

            Dictionary<string, string> religionDict = new Dictionary<string, string>();
            Dictionary<string, string> provinceDict = new Dictionary<string, string>();

            foreach (var country in countryDict.Values)
            {
                string searchStringCountry = "\t" + country + "={";
                string searchStringGovRank1 = "\t\tgovernment_rank=";
                string searchStringGovRank2 = "\t\thas_set_government_name=";
                string searchStringReligion = "\t\treligion=";
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
                        else
                        {
                            // keep searching we just have to go further down
                        }
                    }
                    else if(foundReligion)
                    {
                        if (line.StartsWith(searchStringCities))
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

            foreach(var country in countryDict.Keys)
            {
                if(religionDict.ContainsKey(countryDict[country]) && provinceDict.ContainsKey(countryDict[country]))
                {
                    Console.WriteLine("country= " + country + ", religion= " + religionDict[countryDict[country]] + ", provinces= " + provinceDict[countryDict[country]]);
                }
                else if(religionDict.ContainsKey(countryDict[country]))
                {
                    Console.WriteLine("country= " + country + ", religion= " + religionDict[countryDict[country]] + ", provinces= NOT FOUND " );
                }
                else if (provinceDict.ContainsKey(countryDict[country]))
                {
                    Console.WriteLine("country= " + country + ", religion= NOT FOUND" + ", provinces= " + provinceDict[countryDict[country]]);
                }
                else
                {
                    Console.WriteLine("country= " + country + " NOT FOUND");
                }
            }

            


            var stop2 = Console.ReadLine();
        }
    }
}
