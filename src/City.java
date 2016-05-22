import java.util.ArrayList;

public class City {
	String name;
	ArrayList<Airport> airportList = new ArrayList<Airport>();

	public City(String name) {
		this.name = name;
	}

	public void addAirport(Airport newAirport) {
		airportList.add(newAirport);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Airport> getAirportList() {
		return airportList;
	}

	public void setAirportList(ArrayList<Airport> airportList) {
		this.airportList = airportList;
	}
	
	public String toString() {
		return name + airportList;
	}
}
