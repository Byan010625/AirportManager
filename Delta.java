import java.util.ArrayList;

/**
 * Project 5 - Delta.java
 *
 * One of the three airlines
 *
 * N/A
 *
 * @author Benjamin Yan, Vamsi Kolluri
 * @version November 20, 2019
 */
public class Delta implements Airline {

    private String name;
    private String amenities;
    private int maxPassengers;
    private int numPassengers;
    private ArrayList<Passenger> passengerList;
    private Gate gate;

    public Delta(String name, String amenities, int maxPassengers, Gate gate) {
        this.name = name;
        this.amenities = amenities;
        this.maxPassengers = maxPassengers;
        this.numPassengers = 0;
        passengerList = new ArrayList<>();
        this.gate = gate;
    }

    public String getName() {
        return name;
    }

    public String getAmenities() {
        return amenities;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public int getNumPassengers() {
        return numPassengers;
    }

    public ArrayList<Passenger> getPassengerList() {
        return this.passengerList;
    }

    public Gate getGate() {
        return this.gate;
    }

    public void addPassenger(Passenger passenger) {
        passengerList.add(passenger);
        numPassengers++;
    }

    public void removePassenger(Passenger passenger) {
        passengerList.remove(passenger);
        numPassengers--;
    }

    public boolean isFull() {
        return this.numPassengers == this.maxPassengers;
    }
}
