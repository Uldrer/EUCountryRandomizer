package Helpers;

import java.io.FileOutputStream;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import Randomizer.Result;

public class ConfigSaver {

	
	public void saveResultConfig(String configFile, List<Result> results)
	{
		try
		{
			// create an XMLOutputFactory
		    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		    // create XMLEventWriter
		    XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(configFile));
		    // create an EventFactory
		    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		    XMLEvent end = eventFactory.createDTD("\n");
		    // create and write Start Tag
		    StartDocument startDocument = eventFactory.createStartDocument();
		    eventWriter.add(startDocument);
		    eventWriter.add(end);

		    
		    
		    // create config open tag
		    StartElement configStartElement = eventFactory.createStartElement("",
		        "", "config");
		    eventWriter.add(configStartElement);
		    eventWriter.add(end);
		    
		    
		    StartElement resultStartElement = eventFactory.createStartElement("",
			        "", "result");
		    XMLEvent tab = eventFactory.createDTD("\t");
		    
		    for(Result res : results)
		    {
		    	eventWriter.add(tab);
		    	eventWriter.add(resultStartElement);
		    	eventWriter.add(end);
		    	
		    	// Write the different nodes
			    createNode(eventWriter, "Player", res.getPlayer().getName());
			    createNode(eventWriter, "Country", res.getCountry().getName());
			    
			    eventWriter.add(tab);
			    eventWriter.add(eventFactory.createEndElement("", "", "result"));
			    eventWriter.add(end);
		    }
		    

		    eventWriter.add(eventFactory.createEndElement("", "", "config"));
		    eventWriter.add(end);
		    eventWriter.add(eventFactory.createEndDocument());
		    eventWriter.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	  }

	  private void createNode(XMLEventWriter eventWriter, String name,
	      String value) throws XMLStreamException {
	
	    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	    XMLEvent end = eventFactory.createDTD("\n");
	    XMLEvent tab = eventFactory.createDTD("\t");
	    // create Start node
	    StartElement sElement = eventFactory.createStartElement("", "", name);
	    eventWriter.add(tab);
	    eventWriter.add(tab);
	    eventWriter.add(sElement);
	    // create Content
	    Characters characters = eventFactory.createCharacters(value);
	    eventWriter.add(characters);
	    // create End node
	    EndElement eElement = eventFactory.createEndElement("", "", name);
	    eventWriter.add(eElement);
	    eventWriter.add(end);
	}
}
