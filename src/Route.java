import java.util.ArrayList;
import java.util.Date;

public class Route implements Cloneable {
	ArrayList<Flight> route = new ArrayList<Flight>();
	Flight firstFlight = null;/*keep first flight*/
	Flight lastFlight;/*keep last flight*/
	long totalTime;
	int totalPrice = 0;
	String toStringStore = "";

	public void add(Flight flight) {
		route.add(flight);
		if (firstFlight == null) {
			firstFlight = flight;
			lastFlight = flight;
			toStringStore += flight;
		} else {
			lastFlight = flight;
			toStringStore += "||" + flight;
			totalTime = lastFlight.getLandingDate().getTime() - firstFlight.getLandingDate().getTime();
		}
		totalPrice += flight.getPrice();

	}

	public void remove() {
		route.remove(route.size() - 1);
	}

	public ArrayList<Flight> getList() {
		return route;
	}

	public long getTotalTime() {
		return totalTime + lastFlight.getDuration() * 60000;
	}

	public long getLandingTime() {
		return lastFlight.getLandingDate().getTime() + lastFlight.getDuration() * 60000;
	}

	public Date getLandingDate() {
		return new Date(lastFlight.getLandingDate().getTime() + lastFlight.getDuration() * 60000);
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public Flight getLastFlight() {
		return route.get(route.size() - 1);
	}

	public String toString() {
		Tools tool = new Tools();
		return toStringStore + "\t" + tool.msToDuration(totalTime + lastFlight.getDuration() * 60000) + "/"
				+ totalPrice;
	}
}
