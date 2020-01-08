import java.io.Serializable;

/**
 * Project 5 - Passenger.java
 *
 * A passenger for a flight
 *
 * N/A
 *
 * @author Benjamin Yan, Vamsi Kolluri
 * @version December 2, 2019
 */

public class Passenger implements Serializable {
    private String firstName;
    private String lastName;
    private int age;
    private Airline airline;

    public Passenger(String firstName, String lastName, int age, Airline airline) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.airline = airline;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public Airline getAirline() {
        return this.airline;
    }

    @Override
    public String toString() {
        return "Passenger";
    }

    public String displayPassenger() {
        String toReturn = firstName.substring(0, 1) + ". " + lastName + ", " + age;
        toReturn = toReturn.toUpperCase();
        return toReturn;
    }
}