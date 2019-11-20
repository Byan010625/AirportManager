import java.io.Serializable;
import java.util.ArrayList;

/**
 * Project 5 - Airline.java
 *
 * Interface for the three airlines
 *
 * N/A
 *
 * @author Benjamin Yan, Vamsi Kolluri
 * @version November 20, 2019
 */

public interface Airline extends Serializable {

    String getName();
    String getAmenities();
    int getMaxPassengers();
    int getNumPassengers();
    ArrayList<Passenger> getPassengerList();
    boolean isFull();
    Gate getGate();
    void addPassenger(Passenger passenger);
    void removePassenger(Passenger passenger);


}
