import java.util.ArrayList;

/**
 * Project 5 - Southwest.java
 *
 * The Southwest Airline
 *
 * N/A
 *
 * @author Benjamin Yan, Vamsi Kolluri
 * @version December 2, 2019
 */

public class Southwest implements Airline {
    private String airlineName;
    private static String message;
    private String flightNumber;
    private int numberOfSeats;
    private ArrayList<String> passengerStrings;

    public Southwest() {
        this.airlineName = "Southwest Airlines";
        this.message = "Southwest Airlines is proud to offer flights to Purdue University. " +
                "We are happy to offer free inflight WiFi, as well as amazing snacks.";
        this.flightNumber = "SW 18000";
        this.numberOfSeats = 30;
        // Mock Passenger list
        passengerStrings = new ArrayList<>();
    }

    @Override
    public String getAirlineName() {
        return this.airlineName;
    }

    public static String getMessage() {
        return message;
    }

    @Override
    public String getFlightNumber() {
        return this.flightNumber;
    }

    @Override
    public int numberOfSeats() {
        return this.numberOfSeats;
    }

    public void setPassengerStrings(ArrayList<String> passengerStrings) {
        this.passengerStrings = passengerStrings;
    }

    public ArrayList<String> getPassengerStrings() {
        return this.passengerStrings;
    }

    public void addToPassengerStrings(String passenger) {
        this.passengerStrings.add(passenger);
    }

    public String toString() {
        return "Southwest";
    }
}