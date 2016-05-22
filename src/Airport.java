import java.util.ArrayList;

public class Airport {

	private String name;
	private ArrayList<Flight> arrivals = new ArrayList<Flight>();
	private ArrayList<Flight> departures = new ArrayList<Flight>();
	public boolean mark;

	public Airport(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public ArrayList<Flight> getArrivals() {
		return arrivals;
	}

	public void addArrivals(Flight arrivals) {
		this.arrivals.add(arrivals);
	}

	public ArrayList<Flight> getDepartures() {
		return departures;
	}

	public void addDepartures(Flight departures) {
		this.departures.add(departures);
	}

	public boolean isMark() {
		return mark;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}
}
