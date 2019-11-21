import java.io.Serializable;
import java.util.Random;
/**
 * Project 5 - Gate.java
 *
 * The gate of a flight
 *
 * N/A
 *
 * @author Benjamin Yan, Vamsi Kolluri
 * @version November 20, 2019
 */
public class Gate implements Serializable {

    private String terminal;
    private String gate;

    public Gate() {

        Random random = new Random();

        int terminalLetter = random.nextInt(3);
        switch (terminalLetter) {
            case 0:
                terminal = "A";
                break;
            case 1:
                terminal = "B";
                break;
            case 2:
                terminal = "C";
                break;
            default:
                terminal = "Problem in Gate Constructor";
                break;
        }

        int gateNumber = random.nextInt(18) + 1;
        gate = Integer.toString(gateNumber);
    }

    public String toString() {
        return terminal + gate;
    }
}
