import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.ParseException;

public class Manager {

	/* keep cities */
	private ArrayList<City> cityList = new ArrayList<City>();
	/* keep flights */
	private ArrayList<Flight> flightList = new ArrayList<Flight>();
	/* keep airports */
	private ArrayList<Airport> airportList = new ArrayList<Airport>();

	//////////////////// Airport List Create Functions
	public void readAirportListFile(String filePath) {
		/* for read file */
		TextFileReader fileReader = new TextFileReader();
		/* takes all lines from had had read file */
		ArrayList<String> lines = fileReader.read(filePath);
		for (String currentLine : lines) {
			/* sent file line to cityBuilder */
			cityBuilder(currentLine);
		}
	}

	/* takes line and add as a new city */
	private void cityBuilder(String line) {
		Tools tool = new Tools();
		/* split line based on tab char */
		String[] words = tool.tabSplitter(line);
		/* words[0] is city name, before words[0] are airports */
		/* create city */
		String cityName = words[0];
		City newCity = new City(cityName);
		for (int i = 1; i < words.length; i++) {
			/* create airport */
			Airport newAirport = new Airport(words[i]);
			/* add airport to airportList */
			airportList.add(newAirport);
			/* add airport to newCity's airport list */
			newCity.addAirport(newAirport);
		}
		cityList.add(newCity);
	}
	//////////////////// Airport List Create Functions

	//////////////////// Flight List Create Functions
	public void readFlightListFile(String filePath) throws ParseException {
		/* for read file */
		TextFileReader fileReader = new TextFileReader();
		/* takes all lines from had had read file */
		ArrayList<String> lines = fileReader.read(filePath);
		for (String currentLine : lines) {
			/* sent file line to flightBuilder */
			flightBuilder(currentLine);
		}
		/* sent file line to flightBuilder */
		flightsBinding();
	}

	/* takes line and add as a new flight */
	private void flightBuilder(String line) throws ParseException {
		Tools tool = new Tools();
		/* split line based on tab char */
		String[] words = tool.tabSplitter(line);
		if (words.length == 5) {
			String flightId = words[0];
			String way = words[1];
			/* split line based on '->' symbol */
			String[] dept_arr = tool.arrowSplitter(way);
			String dept = dept_arr[0];
			String arr = dept_arr[1];
			String deptDate = words[2];
			String duration = words[3];
			String price = words[4];

			for (Airport airport : airportList) {
				if (airport.getName().equals(dept)) {
					for (Airport destAirport : airportList) {
						if (destAirport.getName().equals(arr)) {
							/* create flight */
							Flight newFlight = new Flight(flightId, airport, destAirport,
									tool.deperatureStringToDate(deptDate), tool.durationToMinute(duration),
									Integer.parseInt(price));
							/* add flight to flightList */
							flightList.add(newFlight);
							break;
						}
					}
					break;
				}
			}
		} else/* if flight file format is incorrect */
			System.out.println("Wrong flight line information on flight file");
	}

	/* transfer all flights on flightList to source and destination */
	public void flightsBinding() {
		for (Flight flight : flightList) {
			flight.getSource().addDepartures(flight);
			flight.getDestination().addArrivals(flight);
		}
	}
	//////////////////// Flight List Create Functions

	//////////////////// Commands
	public void readCommandsFile(String filePath) {
		/* for read file */
		TextFileReader fileReader = new TextFileReader();
		/* takes all lines from had had read file */
		ArrayList<String> lines = fileReader.read(filePath);
		for (String currentLine : lines) {
			/* sent command line to flightBuilder */
			commandActuator(currentLine);
		}
	}

	/* takes command and run */
	public void commandActuator(String command) {
		Tools tool = new Tools();
		/* split line based on tab char */
		String[] words = tool.tabSplitter(command);
		/* split line based on '->' symbol */
		String[] route = tool.arrowSplitter(words[1]);
		System.out.println("Command : " + command);
		if (route[0].equals(route[1])) {
			System.out.println("Flight can not be possible among same cities");
			System.out.println(System.getProperty("line.separator"));
			return;
		}
		if (words[0].equals("listall"))
			listAll(route[0], route[1], words[2]);
		if (words[0].equals("listproper"))
			listProper(route[0], route[1], words[2]);
		if (words[0].equals("listcheapest"))
			listCheapest(route[0], route[1], words[2]);
		if (words[0].equals("listquickest"))
			listQuickest(route[0], route[1], words[2]);
		if (words[0].equals("listcheaper"))
			listCheaper(route[0], route[1], words[2], words[3]);
		if (words[0].equals("listquicker"))
			listQuicker(route[0], route[1], words[2], words[3]);
		if (words[0].equals("listexcluding"))
			listExcluding(route[0], route[1], words[2], words[3]);
		if (words[0].equals("listonlyfrom"))
			listOnlyFrom(route[0], route[1], words[2], words[3]);
		System.out.println(System.getProperty("line.separator"));
	}
	//////////////////// Commands

	private void listAll(String departureName, String arrivalName, String startDate) {
		Tools tool = new Tools();
		City departureCity = cityFinder(departureName);
		City arrivalCity = cityFinder(arrivalName);

		if (departureCity == null) {
			System.out.println("Departure City can not exist");
			return;
		}
		if (arrivalCity == null) {
			System.out.println("Arrival City can not exist");
			return;
		}
		/* create RoutePlanner */
		RoutePlanner planner = new RoutePlanner(departureCity, arrivalCity);
		/* RoutePlanner's listAllFlights functions returned all path as a string*/
		ArrayList<String> list = planner.listAllFlights(tool.startStringToDate(startDate));
		if (list.size() == 0)
			System.out.println("No suitable flight plan is found");
		for (String line : list)
			System.out.println(line);
	}

	// private void listAllFlight(City departureAirport, City arrivalAirport) {
	// RoutePlanner planner = new RoutePlanner(departureAirport,
	// arrivalAirport);
	// System.out.println(planner.allFlights());
	// }

	private void listProper(String departureName, String arrivalName, String startDate) {
		Tools tool = new Tools();
		City departureCity = cityFinder(departureName);
		City arrivalCity = cityFinder(arrivalName);

		if (departureCity == null) {
			System.out.println("Departure City can not exist");
			return;
		}
		if (arrivalCity == null) {
			System.out.println("Arrival City can not exist");
			return;
		}

		RoutePlanner planner = new RoutePlanner(departureCity, arrivalCity);
		/* RoutePlanner's listProperFlights functions returned all path as a string*/
		ArrayList<String> list = planner.listProperFlights(tool.startStringToDate(startDate));
		if (list.size() == 0)
			System.out.println("No suitable flight plan is found");
		for (String line : list)
			System.out.println(line);
	}

	private void listCheapest(String departureName, String arrivalName, String startDate) {
		Tools tool = new Tools();
		City departureCity = cityFinder(departureName);
		City arrivalCity = cityFinder(arrivalName);

		if (departureCity == null) {
			System.out.println("Departure City can not exist");
			return;
		}
		if (arrivalCity == null) {
			System.out.println("Arrival City can not exist");
			return;
		}

		RoutePlanner planner = new RoutePlanner(departureCity, arrivalCity);
		/* RoutePlanner's listCheapestFlights functions returned all path as a string*/

		ArrayList<String> list = planner.listCheapestFlights(tool.startStringToDate(startDate));
		if (list.size() == 0)
			System.out.println("No suitable flight plan is found");
		for (String line : list)
			System.out.println(line);
	}

	private void listQuickest(String departureName, String arrivalName, String startDate) {
		Tools tool = new Tools();
		City departureCity = cityFinder(departureName);
		City arrivalCity = cityFinder(arrivalName);

		if (departureCity == null) {
			System.out.println("Departure City can not exist");
			return;
		}
		if (arrivalCity == null) {
			System.out.println("Arrival City can not exist");
			return;
		}

		RoutePlanner planner = new RoutePlanner(departureCity, arrivalCity);
		/* RoutePlanner's listQuickestFlights functions returned all path as a string*/

		ArrayList<String> list = planner.listQuickestFlights(tool.startStringToDate(startDate));
		if (list.size() == 0)
			System.out.println("No suitable flight plan is found");
		for (String line : list)
			System.out.println(line);
	}

	private void listCheaper(String departureName, String arrivalName, String startDate, String maxPrice) {
		Tools tool = new Tools();
		City departureCity = cityFinder(departureName);
		City arrivalCity = cityFinder(arrivalName);

		if (departureCity == null) {
			System.out.println("Departure City can not exist");
			return;
		}
		if (arrivalCity == null) {
			System.out.println("Arrival City can not exist");
			return;
		}

		RoutePlanner planner = new RoutePlanner(departureCity, arrivalCity);
		/* RoutePlanner's listCheaperFlights functions returned all path as a string*/

		ArrayList<String> list = planner.listCheaperFlights(tool.startStringToDate(startDate),
				Integer.parseInt(maxPrice));
		if (list.size() == 0)
			System.out.println("No suitable flight plan is found");
		for (String line : list)
			System.out.println(line);
	}

	private void listQuicker(String departureName, String arrivalName, String startDate, String latestDateTime) {
		Tools tool = new Tools();
		City departureCity = cityFinder(departureName);
		City arrivalCity = cityFinder(arrivalName);

		if (departureCity == null) {
			System.out.println("Departure City can not exist");
			return;
		}
		if (arrivalCity == null) {
			System.out.println("Arrival City can not exist");
			return;
		}

		RoutePlanner planner = new RoutePlanner(departureCity, arrivalCity);
		/* RoutePlanner's listQuickerFlights functions returned all path as a string*/

		ArrayList<String> list = planner.listQuickerFlights(tool.startStringToDate(startDate),
				tool.deperatureStringToDate(latestDateTime));
		if (list.size() == 0)
			System.out.println("No suitable flight plan is found");
		for (String line : list)
			System.out.println(line);
	}

	private void listExcluding(String departureName, String arrivalName, String startDate, String company) {
		Tools tool = new Tools();
		City departureCity = cityFinder(departureName);
		City arrivalCity = cityFinder(arrivalName);

		if (departureCity == null) {
			System.out.println("Departure City can not exist");
			return;
		}
		if (arrivalCity == null) {
			System.out.println("Arrival City can not exist");
			return;
		}

		RoutePlanner planner = new RoutePlanner(departureCity, arrivalCity);
		/* RoutePlanner's listExcludingFlights functions returned all path as a string*/

		ArrayList<String> list = planner.listExcludingFlights(tool.startStringToDate(startDate), company);
		if (list.size() == 0)
			System.out.println("No suitable flight plan is found");
		for (String line : list)
			System.out.println(line);
	}

	private void listOnlyFrom(String departureName, String arrivalName, String startDate, String company) {
		Tools tool = new Tools();
		City departureCity = cityFinder(departureName);
		City arrivalCity = cityFinder(arrivalName);

		if (departureCity == null) {
			System.out.println("Departure City can not exist");
			return;
		}
		if (arrivalCity == null) {
			System.out.println("Arrival City can not exist");
			return;
		}

		RoutePlanner planner = new RoutePlanner(departureCity, arrivalCity);
		/* RoutePlanner's listOnlyFromFlights functions returned all path as a string*/

		ArrayList<String> list = planner.listOnlyFromFlights(tool.startStringToDate(startDate), company);
		if (list.size() == 0)
			System.out.println("No suitable flight plan is found");
		for (String line : list)
			System.out.println(line);
	}

	public void createOutputFile(String filePath) {
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			TeeOutputStream myOut = new TeeOutputStream(System.out, fos);
			PrintStream ps = new PrintStream(myOut);
			System.setOut(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	/*takes city name and returned city as a City*/
	private City cityFinder(String cityName) {
		City departureAirport = null;
		for (City temp : cityList) {
			if (temp.getName().equals(cityName)) {
				departureAirport = temp;
				break;
			}
		}
		return departureAirport;
	}
}
