
import java.util.ArrayList;
import java.util.Date;

public class RoutePlanner {
	/* this class flight search */
	private City departureCity;
	private City arrivalCity;

	/* keep all route as a String for return */
	private ArrayList<String> outputList = new ArrayList<String>();

	/* keep all possible route */
	private ArrayList<Route> routesList = new ArrayList<Route>();

	public RoutePlanner(City departureCity, City arrivalCity) {
		this.departureCity = departureCity;
		this.arrivalCity = arrivalCity;
	}

	public ArrayList<String> listAllFlights(Date landingDate) {
		Route route = new Route();
		for (Airport dept : departureCity.getAirportList())
			for (Airport arr : arrivalCity.getAirportList())
				findAllFlightPlans(dept, arr, route, landingDate);

		for (Route routee : routesList) {
			outputList.add(routee.toString());
		}
		return outputList;
	}

	public ArrayList<String> listProperFlights(Date landingDate) {
		Route route = new Route();
		for (Airport dept : departureCity.getAirportList())
			for (Airport arr : arrivalCity.getAirportList())
				findAllFlightPlans(dept, arr, route, landingDate);
		int cheapest = -1;
		long quickest = -1;

		for (Route routee : routesList) {
			if (cheapest == -1 || quickest == -1) {
				quickest = routee.getTotalTime();
				cheapest = routee.getTotalPrice();
			}
			if (quickest > routee.getTotalTime())
				quickest = routee.getTotalTime();
			if (cheapest > routee.getTotalPrice())
				cheapest = routee.getTotalPrice();
		}

		for (Route routee : routesList) {
			if (quickest == routee.getTotalTime() || cheapest == routee.getTotalPrice())
				outputList.add(routee.toString());
		}
		return outputList;
	}

	public ArrayList<String> listCheapestFlights(Date landingDate) {
		Route route = new Route();
		for (Airport dept : departureCity.getAirportList())
			for (Airport arr : arrivalCity.getAirportList())
				findAllFlightPlans(dept, arr, route, landingDate);
		int cheapest = -1;

		for (Route routee : routesList) {
			if (cheapest == -1) {
				cheapest = routee.getTotalPrice();
			}
			if (cheapest > routee.getTotalPrice())
				cheapest = routee.getTotalPrice();
		}

		for (Route routee : routesList) {
			if (cheapest == routee.getTotalPrice())
				outputList.add(routee.toString());
		}
		return outputList;
	}

	public ArrayList<String> listQuickestFlights(Date landingDate) {
		Route route = new Route();
		for (Airport dept : departureCity.getAirportList())
			for (Airport arr : arrivalCity.getAirportList())
				findAllFlightPlans(dept, arr, route, landingDate);

		long quickest = -1;

		for (Route routee : routesList) {
			if (quickest == -1) {
				quickest = routee.getTotalTime();
			}
			if (quickest > routee.getTotalTime())
				quickest = routee.getTotalTime();
		}

		for (Route routee : routesList) {
			if (quickest == routee.getTotalTime())
				outputList.add(routee.toString());
		}
		return outputList;
	}

	public ArrayList<String> listCheaperFlights(Date landingDate, int maxPrice) {///////////////////
		Route route = new Route();
		for (Airport dept : departureCity.getAirportList())
			for (Airport arr : arrivalCity.getAirportList())
				findAllFlightPlans(dept, arr, route, landingDate);

		int cheapest = -1;
		long quickest = -1;
		for (Route routee : routesList) {
			if (maxPrice > routee.getTotalPrice()) {
				if (cheapest == -1 || quickest == -1) {
					quickest = routee.getTotalTime();
					cheapest = routee.getTotalPrice();
				}
				if (quickest > routee.getTotalTime())
					quickest = routee.getTotalTime();
				if (cheapest > routee.getTotalPrice())
					cheapest = routee.getTotalPrice();
			}
		}

		for (Route routee : routesList) {
			if (maxPrice > routee.getTotalPrice())
				if (quickest == routee.getTotalTime() || cheapest == routee.getTotalPrice())
					outputList.add(routee.toString());
		}

		return outputList;
	}

	public ArrayList<String> listQuickerFlights(Date landingDate, Date latestDateTime) {////////////////////
		Route route = new Route();
		for (Airport dept : departureCity.getAirportList())
			for (Airport arr : arrivalCity.getAirportList())
				findAllFlightPlans(dept, arr, route, landingDate);
		int cheapest = -1;
		long quickest = -1;
		for (Route routee : routesList) {
			if (latestDateTime.compareTo(routee.getLandingDate()) == 1) {
				if (cheapest == -1 || quickest == -1) {
					quickest = routee.getTotalTime();
					cheapest = routee.getTotalPrice();
				}
				if (quickest > routee.getTotalTime())
					quickest = routee.getTotalTime();
				if (cheapest > routee.getTotalPrice())
					cheapest = routee.getTotalPrice();
			}
		}

		for (Route routee : routesList) {
			if (latestDateTime.compareTo(routee.getLandingDate()) > 0) {
				if (quickest == routee.getTotalTime() || cheapest == routee.getTotalPrice())
					outputList.add(routee.toString());
			}
		}
		return outputList;
	}

	public ArrayList<String> listExcludingFlights(Date landingDate, String company) {///////////////////
		Route route = new Route();
		for (Airport dept : departureCity.getAirportList())
			for (Airport arr : arrivalCity.getAirportList())
				findAllFlightPlans(dept, arr, route, landingDate);

		int cheapest = -1;
		long quickest = -1;
		for (Route routee : routesList) {
			for (int i = 0; i < routee.getList().size(); i++) {
				if (routee.getList().get(i).getId().startsWith(company))
					break;
				if (i == routee.getList().size() - 1) {
					if (cheapest == -1 || quickest == -1) {
						quickest = routee.getTotalTime();
						cheapest = routee.getTotalPrice();
					}
					if (quickest > routee.getTotalTime())
						quickest = routee.getTotalTime();
					if (cheapest > routee.getTotalPrice())
						cheapest = routee.getTotalPrice();
				}
			}
		}

		for (Route routee : routesList) {
			if (quickest == routee.getTotalTime() || cheapest == routee.getTotalPrice())
				for (int i = 0; i < routee.getList().size(); i++) {
					if (routee.getList().get(i).getId().startsWith(company))
						break;
					if (i == routee.getList().size() - 1)
						outputList.add(routee.toString());

				}
		}
		return outputList;
	}

	public ArrayList<String> listOnlyFromFlights(Date landingDate, String company) {//////////////////
		Route route = new Route();
		for (Airport dept : departureCity.getAirportList())
			for (Airport arr : arrivalCity.getAirportList())
				findAllFlightPlans(dept, arr, route, landingDate);

		int cheapest = -1;
		long quickest = -1;
		for (Route routee : routesList) {
			for (int i = 0; i < routee.getList().size(); i++) {
				if (!routee.getList().get(i).getId().startsWith(company))
					break;
				if (i == routee.getList().size() - 1) {
					if (cheapest == -1 || quickest == -1) {
						quickest = routee.getTotalTime();
						cheapest = routee.getTotalPrice();
					}
					if (quickest > routee.getTotalTime())
						quickest = routee.getTotalTime();
					if (cheapest > routee.getTotalPrice())
						cheapest = routee.getTotalPrice();
				}
			}
		}

		for (Route routee : routesList) {
			for (int i = 0; i < routee.getList().size(); i++) {
				if (!routee.getList().get(i).getId().startsWith(company))
					break;
				if (i == routee.getList().size() - 1)
					outputList.add(routee.toString());
			}
		}
		return outputList;
	}

	/* find all possible routes */
	private void findAllFlightPlans(Airport dept, Airport arr, Route route, Date departureDate) {
		/* if current city is destination */
		if (dept.equals(arr)) {
			Route newRoute = new Route();
			for (Flight fligh : route.getList())
				newRoute.add(fligh);
			/* add routeList newRoute */
			routesList.add(newRoute);
			return;
		}
		for (Flight flight : dept.getDepartures()) {
			if (dept.isMark())
				continue;
			if (departureDate.compareTo(flight.getLandingDate()) < 1) {
				route.add(flight);
				dept.setMark(true);
				findAllFlightPlans(flight.getDestination(), arr, route, flight.getDepartureDate());
				dept.setMark(false);
				route.remove();
			}
		}
	}
}
