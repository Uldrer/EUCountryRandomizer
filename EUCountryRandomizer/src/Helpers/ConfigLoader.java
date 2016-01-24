package Helpers;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import Randomizer.Player;
import Randomizer.Region;
import Randomizer.Tier;
import Randomizer.Country;

public class ConfigLoader {
	
	static final String TIER = "Tier";
	static final String ID = "ID";
	static final String COUNTRY = "Country";
	static final String NAME = "Name";
	static final String PLAYER = "Player";
	static final String REGION = "Region";
	static final String SCORE = "Score";

	/**
	 * @param configFile
	 */
	public List<Player> readPlayerConfig(String configFile)
	{
		List<Player> items = new ArrayList<Player>();
	    try {
	      // First, create a new XMLInputFactory
	      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	      // Setup a new eventReader
	      InputStream in = new FileInputStream(configFile);
	      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
	      // read the XML document
	      Player item = null;

	      while (eventReader.hasNext()) {
	        XMLEvent event = eventReader.nextEvent();

	        if (event.isStartElement()) {
	          StartElement startElement = event.asStartElement();
	          
	          // If we have an item element, we create a new item
	          if (startElement.getName().getLocalPart() == (PLAYER)) {
	            item = new Player();
	            // We read the attributes from this tag and add the date
	            // attribute to our object
	            Iterator<Attribute> attributes = startElement.getAttributes();
	            while (attributes.hasNext()) {
	              Attribute attribute = attributes.next();
	              if (attribute.getName().toString().equals(ID)) {
	                item.setId(Integer.parseInt(attribute.getValue()));
	              }
	            }
	          }
	          
	          if (event.isStartElement()) {
	        	  if (event.asStartElement().getName().getLocalPart()
	                  .equals(NAME)) {
	                event = eventReader.nextEvent();
	                item.setName(event.asCharacters().getData());
	                continue;
	             }
	          }
	          
	          if (event.isStartElement()) {
	        	  if (event.asStartElement().getName().getLocalPart()
	                  .equals(TIER)) {
	                event = eventReader.nextEvent();
	                item.setTier(Integer.parseInt(event.asCharacters().getData()));
	                continue;
	             }
	          }
	        }
	        // If we reach the end of an item element, we add it to the list
	        if (event.isEndElement()) {
	          EndElement endElement = event.asEndElement();
	          if (endElement.getName().getLocalPart() == (PLAYER)) {
	            items.add(item);
	          }
	        }
	      }
	    }
	    catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } 
	    catch (XMLStreamException e) {
	      e.printStackTrace();
	    }
	    
	    for(Player pl : items)
	    {
	    	//System.out.println(pl.toString());
	    }
	    
	    return items;
	}
	
	public List<Tier> readTierConfig(String configFile)
	{
		List<Tier> items = new ArrayList<Tier>();
	    try {
	      // First, create a new XMLInputFactory
	      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	      // Setup a new eventReader
	      InputStream in = new FileInputStream(configFile);
	      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
	      // read the XML document
	      Tier item = null;

	      while (eventReader.hasNext()) {
	        XMLEvent event = eventReader.nextEvent();

	        if (event.isStartElement()) {
	          StartElement startElement = event.asStartElement();
	          
	          // If we have an item element, we create a new item
	          if (startElement.getName().getLocalPart() == (TIER)) {
	            item = new Tier();
	            // We read the attributes from this tag and add the date
	            // attribute to our object
	            Iterator<Attribute> attributes = startElement.getAttributes();
	            while (attributes.hasNext()) {
	              Attribute attribute = attributes.next();
	              if (attribute.getName().toString().equals(ID)) {
	                item.setId(Integer.parseInt(attribute.getValue()));
	              }
	            }
	          }
	          
	          if (event.isStartElement()) {
	        	  if (event.asStartElement().getName().getLocalPart()
	                  .equals(COUNTRY)) {
	                event = eventReader.nextEvent();
	                item.addCountry(new Country(event.asCharacters().getData()));
	                continue;
	             }
	          }
	        }
	        // If we reach the end of an item element, we add it to the list
	        if (event.isEndElement()) {
	          EndElement endElement = event.asEndElement();
	          if (endElement.getName().getLocalPart() == (TIER)) {
	            items.add(item);
	          }
	        }
	      }
	    }
	    catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } 
	    catch (XMLStreamException e) {
	      e.printStackTrace();
	    }
	    
	    for(Tier t : items)
	    {
	    	//System.out.println(t.toString());
	    }
	    
	    return items;
	}
	
	public List<Region> readRegionConfig(String configFile)
	{
		List<Region> items = new ArrayList<Region>();
	    try {
	      // First, create a new XMLInputFactory
	      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	      // Setup a new eventReader
	      InputStream in = new FileInputStream(configFile);
	      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
	      // read the XML document
	      Region item = null;

	      while (eventReader.hasNext()) {
	        XMLEvent event = eventReader.nextEvent();

	        if (event.isStartElement()) {
	          StartElement startElement = event.asStartElement();
	          
	          // If we have an item element, we create a new item
	          if (startElement.getName().getLocalPart() == (REGION)) {
	            item = new Region();
	            // We read the attributes from this tag and add the date
	            // attribute to our object
	            Iterator<Attribute> attributes = startElement.getAttributes();
	            while (attributes.hasNext()) {
	              Attribute attribute = attributes.next();
	              if (attribute.getName().toString().equals(ID)) {
	                item.setId(Integer.parseInt(attribute.getValue()));
	              }
	            }
	          }
	          
	          if (event.isStartElement()) {
	        	  if (event.asStartElement().getName().getLocalPart()
	                  .equals(NAME)) {
	                event = eventReader.nextEvent();
	                item.setName(event.asCharacters().getData());
	                continue;
	             }
	          }
	          
	          if (event.isStartElement()) {
	        	  if (event.asStartElement().getName().getLocalPart()
	                  .equals(COUNTRY)) {
	                event = eventReader.nextEvent();
	                item.addCountry(new Country(event.asCharacters().getData()));
	                continue;
	             }
	          }
	          
	          if (event.isStartElement()) {
	        	  if (event.asStartElement().getName().getLocalPart()
	                  .equals(SCORE)) {
	                event = eventReader.nextEvent();
	                item.setScore(Integer.parseInt(event.asCharacters().getData()));
	                continue;
	             }
	          }
	        }
	        // If we reach the end of an item element, we add it to the list
	        if (event.isEndElement()) {
	          EndElement endElement = event.asEndElement();
	          if (endElement.getName().getLocalPart() == (REGION)) {
	            items.add(item);
	          }
	        }
	      }
	    }
	    catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } 
	    catch (XMLStreamException e) {
	      e.printStackTrace();
	    }
	    
	    for(Region r : items)
	    {
	    	//System.out.println(r.toString());
	    }
	    
	    return items;
	}

}
