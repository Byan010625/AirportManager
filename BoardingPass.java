import java.io.Serializable;

/**
 * Project 5 - BoardingPass.java
 *
 * The Boarding Pass for a Passenger
 *
 * N/A
 *
 * @author Benjamin Yan, Vamsi Kolluri
 * @version December 2, 2019
 */

public class BoardingPass implements Serializable {
    private Passenger passenger;
    private Airline airline;
    private Gate gate;

    public BoardingPass(Passenger passenger, Airline airline, Gate gate) {
        this.passenger = passenger;
        this.airline = airline;
        this.gate = gate;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    @Override
    public String toString() {
        return "<html>BOARDING PASS FOR FLIGHT " + getAirline().getFlightNumber() + " WITH " +
                getAirline().getAirlineName() + "<br>" +
                "PASSENGER FIRST NAME: " + passenger.getFirstName().toUpperCase() + "<br>" +
                "PASSENGER LAST NAME: " + passenger.getLastName().toUpperCase() + "<br>" +
                "PASSENGER AGE: " + passenger.getAge() + "<br>" +
                "You can now begin boarding at " + gate.toString() + "</html>";
    }
}
