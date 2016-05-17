import java.util.Date;

public class Flight {
	private String id;
	private Airport source;
	private Airport destination;
	private Date landingDate;
	private Date departureDate;
	private long duration;
	private int price;

	public Flight(String id, Airport source, Airport destination, Date date, long duration, int price) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.landingDate = date;
		this.duration = duration;
		this.price = price;
		departureDate = new Date(landingDate.getTime() + duration * 60000);
	}

	public Airport getSource() {
		return source;
	}

	public Airport getDestination() {
		return destination;
	}

	public String getId() {
		return id;
	}

	public Date getLandingDate() {
		return landingDate;
	}

	public long getDuration() {
		return duration;
	}

	public int getPrice() {
		return price;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public String toString() {
		return id + "\t" + source.getName() + "->" + destination.getName();
	}
}
