import java.util.ArrayList;

/**
 * Project 5 - Delta.java
 *
 * The Delta Airline
 *
 * N/A
 *
 * @author Benjamin Yan, Vamsi Kolluri
 * @version December 2, 2019
 */

public class Delta implements Airline {
    private String airlineName;
    private static String message;
    private String flightNumber;
    private int numberOfSeats;
    private ArrayList<String> passengerStrings;

    public Delta() {
        this.airlineName = "Delta Airlines";
        message = "Delta Airlines is proud to be one of the five premier Airlines at " +
                "Purdue University.\n" +
                "We are extremely exceptional services with free WiFi for all customers.";
        this.flightNumber = "DL 18000";
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
        return "Delta";
    }
}
