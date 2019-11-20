import java.io.Serializable;

/**
 * Project 5 - Boarding.java
 *
 * The boarding pass of a passenger
 *
 * N/A
 *
 * @author Benjamin Yan, Vamsi Kolluri
 * @version November 20, 2019
 */
public class BoardingPass implements Serializable {

    private String airline;
    private String gate;
    private String firstName;
    private String lastName;
    private int age;

    public BoardingPass(Passenger passenger, Airline airline, Gate gate) {
        this.airline = airline.getName();
        this.gate = gate.toString();
        this.firstName = passenger.getFirstName();
        this.lastName = passenger.getLastName();
        this.age = passenger.getAge();
    }

    public String getAirline() {
        return airline;
    }

    public String getGate() {
        return gate;
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

    public String toString() {
        return "BOARDING PASS FOR FLIGHT 18000 WITH " + airline + "\nPASSENGER FIRST NAME: " +
                firstName + "\nPASSENGER LAST NAME: " + lastName + "\nPASSENGER AGE: " + age +
                "\nYou can now begin boarding at gate " + gate;
    }
}
