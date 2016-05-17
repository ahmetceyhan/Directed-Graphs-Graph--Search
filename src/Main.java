import java.text.ParseException;

public class Main {

	/* 		 Directed Graphs, Graph Search						 */
	/* 		 This experiment about airports flight system 		 */
	/*															 */	
	/*		 Name:	 		 Ahmet								 */
	/* 	  	 Surname:		 Ceyhan								 */
	/* 		 Student number: 21303383							 */
	/*															 */
	/* 		 BBM204 Programming Laboratory Experiment 4 	     */
	
	
	/*important parts*/
	/* Tools class is translate any variables to expected type */

	
	public static void main(String args[]) throws ParseException {
		/* I use to Manager class for main jobs */
		Manager manager = new Manager();
		
		/* Change 'System.out.println' predefined setting for write in file Output File */
		manager.createOutputFile(args[3]);

		/* read Airport List File */
		manager.readAirportListFile(args[0]);

		/* reads Flight List File */
		manager.readFlightListFile(args[1]);

		/* read Commands File and process */
		manager.readCommandsFile(args[2]);
	}
}
