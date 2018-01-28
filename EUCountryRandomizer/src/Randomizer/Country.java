package Randomizer;

public class Country {

	private String name;
	
	public Country()
	{
		this.name = "Default";
	}
	
	public Country(String name)
	{
		this.name = name;
	}

	@Override
	public String toString() {
		return "Country [name=" + name + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (!Country.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
	    final Country other = (Country) obj;
	    if ((this.getName() == null) ? (other.getName() != null) : !this.getName().equals(other.getName())) {
	        return false;
	    }
	    return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
