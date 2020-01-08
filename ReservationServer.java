import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Project 5 - ReservationServer.java + ClientHandler.java
 *
 * The server for this program
 *
 * Reused old code from HW 11
 *
 * @author Benjamin Yan, Vamsi Kolluri, LC2
 * @version December 2, 2019
 */

public class ReservationServer {

    private ServerSocket serverSocket;

    public ReservationServer() throws IOException {
        this.serverSocket = new ServerSocket(0);
    } //CountdownServer

    public void serveClients() {
        Socket clientSocket;
        ClientHandler handler;
        Thread handlerThread;
        int connectionCount = 0;

        System.out.printf("<Now serving clients on port %d...>%n", this.serverSocket.getLocalPort());

        while (true) {
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();

                break;
            } //end try catch

            handler = new ClientHandler(clientSocket);

            handlerThread = new Thread(handler);

            handlerThread.start();

            System.out.printf("<Client %d connected...>%n", connectionCount);

            connectionCount++;
        }
    }

    @Override
    public int hashCode() {
        int result = 23;

        result = result * 31 * Objects.hashCode(this.serverSocket);

        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof ReservationServer) {
            return Objects.equals(this.serverSocket, ((ReservationServer) object).serverSocket);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String format = "ReservationServer[%s]";

        return String.format(format, this.serverSocket);
    }

    /**
     * Project 5 - ClientHandler.java
     *
     *
     * N/A
     *
     * @author Benjamin Yan, Vamsi Kolluri
     * @version December 2, 2019
     */

    class ClientHandler implements Runnable {

        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) throws NullPointerException {
            Objects.requireNonNull(clientSocket, "the specified client socket is null");

            this.clientSocket = clientSocket;
        }

        public ArrayList<String> firstCall(Airline airline) {
            ArrayList<String> fileContents = new ArrayList<>();
            ArrayList<String> toReturn = new ArrayList<>();
            try {
                int index = 0;
                int numIndex = 0;
                BufferedReader bfr = new BufferedReader(new FileReader("reservations.txt"));
                String line = bfr.readLine();

                while (line != null) {
                    fileContents.add(line);
                    line = bfr.readLine();
                }

                switch (airline.toString()) {
                    case "Alaska":
                        index = fileContents.indexOf("DELTA");
                        numIndex = fileContents.indexOf("ALASKA");
                        break;
                    case "Delta":
                        index = fileContents.indexOf("SOUTHWEST");
                        numIndex = fileContents.indexOf("DELTA");
                        break;
                    case "Southwest":
                        index = fileContents.indexOf("EOF");
                        numIndex = fileContents.indexOf("SOUTHWEST");
                        break;
                }
                for (int i = numIndex + 3; i < index; i++) {
                    toReturn.add(fileContents.get(i));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return toReturn;
        }

        public boolean currentNumPassengers(Airline airline) {
            int index = 0;
            int numPassengers = 0;
            try {
                ArrayList<String> fileContents = new ArrayList<>();

                BufferedReader bfr = new BufferedReader(new FileReader("reservations.txt"));
                switch (airline.toString()) {
                    case "Alaska":
                        index = fileContents.indexOf("DELTA");
                        break;
                    case "Delta":
                        index = fileContents.indexOf("SOUTHWEST");
                        break;
                    case "Southwest":
                        index = fileContents.indexOf("EOF");
                        break;
                }

                String passengerFrac = fileContents.get(index + 2);

                String[] twoNums = passengerFrac.split("/");

                return twoNums[0].equals(twoNums[1]);

            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }

            return false;
        }


        //Adding a new passenger reservations.txt

        public void writeNewPassenger(Airline airline, Passenger passenger, ArrayList<String> passengerList) {

            try {
                passengerList.add(passenger.displayPassenger());

                int index = 0;
                int numIndex = 0;
                ArrayList<String> fileContents = new ArrayList<>();
                Path out = Paths.get("reservations.txt");

                BufferedReader bfr = new BufferedReader(new FileReader("reservations.txt"));
                String line = bfr.readLine();

                while (line != null) {
                    fileContents.add(line);
                    line = bfr.readLine();
                }

                switch (airline.toString()) {
                    case "Alaska":
                        index = fileContents.indexOf("DELTA");
                        numIndex = fileContents.indexOf("ALASKA");
                        break;
                    case "Delta":
                        index = fileContents.indexOf("SOUTHWEST");
                        numIndex = fileContents.indexOf("DELTA");
                        break;
                    case "Southwest":
                        index = fileContents.indexOf("EOF");
                        numIndex = fileContents.indexOf("SOUTHWEST");
                        break;
                }

                fileContents.add(index, passenger.displayPassenger());

                String passengerNum = fileContents.get(numIndex + 1);
                String[] stringArray = passengerNum.split("/");

                int numPassengers = Integer.parseInt(stringArray[0]);
                int totalPassengers = Integer.parseInt(stringArray[1]);

                numPassengers++;

                fileContents.set(numIndex + 1, numPassengers + "/" + totalPassengers);

                Files.write(out, fileContents, Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            Alaska alaskaAirline = new Alaska();
            Delta deltaAirline = new Delta();
            Southwest southwestAirline = new Southwest();

            try {
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                Object obj;

                FileOutputStream fos = new FileOutputStream(new File("reservations.txt"), true);
                PrintWriter pw = new PrintWriter(fos);

                ArrayList<String> passengerList;

                while ((obj = ois.readObject()) != null) {
                    String input = obj.toString();
                    switch (input) {
                        case "Alaska":
                            alaskaAirline = (Alaska) obj;
                            alaskaAirline.setPassengerStrings(firstCall(alaskaAirline));
                            oos.writeObject(alaskaAirline);
                            oos.flush();
                            break;
                        case "Delta":
                            deltaAirline = (Delta) obj;
                            deltaAirline.setPassengerStrings(firstCall(deltaAirline));
                            oos.writeObject(deltaAirline);
                            oos.flush();
                            break;
                        case "Southwest":
                            southwestAirline = (Southwest) obj;
                            southwestAirline.setPassengerStrings(firstCall(southwestAirline));
                            oos.writeObject(southwestAirline);
                            oos.flush();
                            break;
                        case "Passenger":
                            Passenger infoPassenger = (Passenger) obj;
                            switch (infoPassenger.getAirline().toString()) {
                                case "Alaska":
                                    passengerList = alaskaAirline.getPassengerStrings();
                                    writeNewPassenger(alaskaAirline, infoPassenger, passengerList);
                                    alaskaAirline.addToPassengerStrings(infoPassenger.displayPassenger());
                                    oos.writeObject(alaskaAirline);
                                    oos.flush();
                                    break;
                                case "Delta":
                                    passengerList = deltaAirline.getPassengerStrings();
                                    writeNewPassenger(deltaAirline, infoPassenger, passengerList);
                                    deltaAirline.addToPassengerStrings(infoPassenger.displayPassenger());
                                    oos.writeObject(deltaAirline);
                                    oos.flush();
                                    break;
                                case "Southwest":
                                    passengerList = deltaAirline.getPassengerStrings();
                                    writeNewPassenger(southwestAirline, infoPassenger, passengerList);
                                    southwestAirline.addToPassengerStrings(infoPassenger.displayPassenger());
                                    oos.writeObject(southwestAirline);
                                    oos.flush();
                                    break;
                            }
                            break;
                    }
                }
            } catch (EOFException e) {
                //This isn't a problem
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int hashCode() {
            int result = 23;

            result = result * 31 * Objects.hashCode(this.clientSocket);

            return result;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            } else if (object instanceof ClientHandler) {
                return Objects.equals(this.clientSocket, ((ClientHandler) object).clientSocket);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            String format = "ClientHandler[%s]";

            return String.format(format, this.clientSocket);
        }
    }
}