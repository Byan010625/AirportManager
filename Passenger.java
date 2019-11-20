import java.io.Serializable;

/**
 * Project 5 - Passenger.java
 *
 * The passengers on a flight
 *
 * N/A
 *
 * @author Benjamin Yan, Vamsi Kolluri
 * @version November 20, 2019
 */

public class Passenger implements Serializable {

    private String firstName;
    private String lastName;
    private int age;

    public Passenger(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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
}
