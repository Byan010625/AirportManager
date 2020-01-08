import java.util.ArrayList;

/**
 * Project 5 - Alaska.java
 *
 * The Alaska Airline
 *
 * N/A
 *
 * @author Benjamin Yan, Vamsi Kolluri
 * @version December 2, 2019
 */

public class Alaska implements Airline {
    private String airlineName;
    private static String message;
    private String flightNumber;
    private int numberOfSeats;
    private ArrayList<String> passengerStrings;

    public Alaska() {
        this.airlineName = "Alaska Airlines";
        message = "Alaska Airlines is proud to serve the strong and knowledgeable\n" +
                "Boilermakers from Purdue University.";
        this.flightNumber = "AK 18000";
        this.numberOfSeats = 25;
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

    @Override
    public String toString() {
        return "Alaska";
    }
}