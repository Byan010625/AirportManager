import java.io.Serializable;
import java.util.ArrayList;

/**
 * Project 5 - Airline.java
 *
 * The 3 airlines at Purdue Airport
 *
 * N/A
 *
 * @author Benjamin Yan, Vamsi Kolluri
 * @version December 2, 2019
 */

public interface Airline extends Serializable {
    String getAirlineName();

    String getFlightNumber();

    int numberOfSeats();

    ArrayList<String> getPassengerStrings();
}