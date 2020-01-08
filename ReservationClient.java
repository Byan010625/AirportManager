import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Project 5 - ReservationClient.java + ResponseHandler.java
 *
 * The Client side of the Airport Manager
 *
 * Reused old code from HW 11
 *
 * @author Benjamin Yan, Vamsi Kolluri
 * @version December 2, 2019
 */

public class ReservationClient {

    private static final String TITLE_HOST = "Hostname?";
    private static final String TITLE_PORT = "Port?";
    private static String resLbl1 = "<html><h1>Welcome to Purdue University Flight " +
            "Reservation System</h1></html>";
    private static String resLbl2 = "<html><h1>Do you want to book a flight Today?</h1></html>";
    private static String resLbl3 = "<html><h1>Choose a Flight from the Drop down Menu</h1></html>";
    private static String resLbl4 = "Are you Sure that you want to book a Flight";
    private static String resLbl5 = "<html><h1>Please Input your Information below</h1></html>";
    private static String resLbl51 = "<html><h3>What is your First Name:</h3></html>";
    private static String resLbl52 = "<html><h3>What is your Last Name:</h3></html>";
    private static String resLbl53 = "<html><h3>What is your Age:</h3></html>";
    private static String resLbl6 = "Fight data displaying for ";
    private static String resLbl61 = "<html><h3> Enjoy your flight!</h3><html>";
    private static String resLbl62 = "<html><h3>Flight is now boarding at Gate </h3><html>";

    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    private static String passengerFirstName;
    private static String passengerLastName;
    private static int passengerAge;
    private static Delta deltaAirline;
    private static Alaska alaskaAirline;
    private static Southwest southwestAirline;
    private static Gate deltaGate;
    private static Gate alaskaGate;
    private static Gate southwestGate;
    private static BoardingPass passengerBoardingPass;
    private static Socket socket;
    private static String[] passengerList;
    private static Passenger newPassenger;
    private static JTextArea txtArea6;

    public static boolean alaskaFull = false;
    public static boolean deltaFull = false;
    public static boolean southwestFull = false;


    JFrame frame = new JFrame("Purdue University Flight Reservation System");

    JPanel panelMain = new JPanel();
    // Screen1
    JPanel panel1 = new JPanel();
    JLabel screenOneLabelOne = new JLabel(resLbl1);
    JButton btnBookFlt1 = new JButton("Book Flight");
    JButton btnExit1 = new JButton("Exit");
    // Screen2
    JPanel panel2 = new JPanel();
    JLabel screenTwoLabelOne = new JLabel(resLbl2);
    JButton btnBookFlt2 = new JButton("Yes, I want to book a Flight");
    JButton btnExit2 = new JButton("Exit");
    // Screen3
    JPanel panel3 = new JPanel();
    JLabel screenThreeLabelOne = new JLabel(resLbl3);
    JButton btnChooseFlt = new JButton("Choose Flight");
    JButton btnExit3 = new JButton("Exit");
    JComboBox airlinesList;
    JLabel airlineMsg = new JLabel("Delta Airlines is proud to be one of the five premier Airlines @ Purdue");
    // Screen 4
    JPanel panel4 = new JPanel();
    JLabel screenFourLabelOne = new JLabel(resLbl4);
    JButton btnAltFlt1 = new JButton("No I want a different Flight");
    JButton btnBookFlt3 = new JButton("Yes, I want this Flight");
    JButton btnExit4 = new JButton("Exit");
    // Screen 5
    JPanel panel5 = new JPanel();
    JLabel screenFiveLabelOne = new JLabel(resLbl5);
    JButton btnNext5 = new JButton("Next");
    JButton btnExit5 = new JButton("Exit");
    JTextField fName;
    JTextField lName;
    JTextField ageTxt;
    // Screen 6
    JPanel panel6 = new JPanel();
    JButton btnRefresh = new JButton("Refresh Flight Status");
    JButton btnExit6 = new JButton("Exit");

    Airline line = new Delta();
    static String hostname;
    static String portString;

    // All Screens are put in Card layout
    CardLayout cl = new CardLayout();

    public ReservationClient() {
        renderGUI();
        initServerConnection();
    }

    private void initServerConnection() {
        BufferedWriter socketWriter;
        BufferedReader socketReader;
        String request;
        String response;
        try {
            socket = new Socket(hostname, Integer.parseInt(portString));
            // Get Reader, Writer to make server requests and read responses
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error making Server Connection to: " + hostname + "@" + portString);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void renderGUI() {

        frame.setSize(1000, 1000);
        panelMain.setLayout(cl);
        // Create Screens
        createScreen1();
        //createScreen2();
        //createScreen3();
        //createScreen4();
        //createScreen5();
        //createScreen6();

        // panelMain is the container for all cards
        // Identify Each screen and associate with Card Identifier

        panelMain.add(panel1, "Screen1");
        //panelMain.add(panel2, "Screen2");
        //panelMain.add(panel3, "Screen3");
        //panelMain.add(panel4, "Screen4");
        //panelMain.add(panel5, "Screen5");
        //panelMain.add(panel6, "Screen6");

        // Show Main Panel
        cl.show(panelMain, "Screen1");

        // Action Listeners
        btnBookFlt1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createScreen2();
                panelMain.add(panel2, "Screen2");
                cl.show(panelMain, "Screen2");
            }
        });

        btnExit1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = "Thank you for using the Purdue Airline Management System!";
                JOptionPane.showConfirmDialog(null,
                        message, "Thank You!", JOptionPane.DEFAULT_OPTION);
                frame.dispose();
                try {
                    oos.close();
                    ois.close();
                    socket.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        });

        btnBookFlt2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createScreen3();
                panelMain.add(panel3, "Screen3");
                cl.show(panelMain, "Screen3");
            }
        });
        btnExit2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    oos.close();
                    ois.close();
                    socket.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        });
        btnChooseFlt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createScreen4();
                panelMain.add(panel4, "Screen4");
                cl.show(panelMain, "Screen4");
            }
        });
        btnExit3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = "Thank you for using the Purdue Airline Management System!";
                JOptionPane.showConfirmDialog(null,
                        message, "Thank You!", JOptionPane.DEFAULT_OPTION);
                frame.dispose();
                try {
                    oos.close();
                    ois.close();
                    socket.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        });
        btnExit4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = "Thank you for using the Purdue Airline Management System!";
                JOptionPane.showConfirmDialog(null,
                        message, "Thank You!", JOptionPane.DEFAULT_OPTION);
                frame.dispose();
                try {
                    oos.close();
                    ois.close();
                    socket.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        });
        btnAltFlt1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(panelMain, "Screen3");
            }
        });
        btnBookFlt3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createScreen5();
                panelMain.add(panel5, "Screen5");
                cl.show(panelMain, "Screen5");
            }
        });
        btnExit5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = "Thank you for using the Purdue Airline Management System!";
                JOptionPane.showConfirmDialog(null,
                        message, "Thank You!", JOptionPane.DEFAULT_OPTION);
                frame.dispose();
                try {
                    oos.close();
                    ois.close();
                    socket.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        });
        btnNext5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = "Are all the details entered correct?\n" +
                        "The passenger's name is " + fName.getText() + " " + lName.getText() +
                        " and age is " + ageTxt.getText() + ".\n" +
                        "If all the information shown is correct, select the" +
                        " Yes button below, otherwise select the No button";
                int input = JOptionPane.showConfirmDialog(frame,
                        message, "Confirm Info", JOptionPane.YES_NO_OPTION);
                if (input == JOptionPane.YES_OPTION) {
                    //Add the passenger to reservations.txt

                    if (!checkInputs(fName.getText(), lName.getText(), ageTxt.getText())) {
                        //do nothing
                    } else {

                        passengerFirstName = fName.getText();
                        passengerLastName = lName.getText();
                        passengerAge = Integer.parseInt(ageTxt.getText());

                        newPassenger = new Passenger(passengerFirstName, passengerLastName, passengerAge, line);

                        switch (line.toString()) {
                            case "Alaska":
                                passengerBoardingPass = new BoardingPass(newPassenger, line, alaskaGate);
                                break;
                            case "Delta":
                                passengerBoardingPass = new BoardingPass(newPassenger, line, deltaGate);
                                break;
                            case "Southwest":
                                passengerBoardingPass = new BoardingPass(newPassenger, line, southwestGate);
                                break;
                        }

                        createScreen6();
                        panelMain.add(panel6, "Screen6");
                        cl.show(panelMain, "Screen6");
                    }
                }
            }
        });
        btnExit6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = "Thank you for using the Purdue Airline Management System!";
                JOptionPane.showConfirmDialog(null,
                        message, "Thank You!", JOptionPane.DEFAULT_OPTION);
                frame.dispose();
                try {
                    oos.close();
                    ois.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String passengersAsString = "";

                try {
                    oos.writeObject(newPassenger);
                    oos.flush();

                    if (newPassenger.getAirline().toString().equals("Alaska")) {
                        alaskaAirline = (Alaska) ois.readObject();
                        for (int i = 0; i < alaskaAirline.getPassengerStrings().size(); i++) {
                            passengersAsString += alaskaAirline.getPassengerStrings().get(i) + '\n';
                        }
                    } else if (newPassenger.getAirline().toString().equals("Delta")) {
                        deltaAirline = (Delta) ois.readObject();
                        for (int i = 0; i < deltaAirline.getPassengerStrings().size(); i++) {
                            passengersAsString += deltaAirline.getPassengerStrings().get(i) + '\n';
                        }
                    } else if (newPassenger.getAirline().toString().equals("Southwest")) {
                        southwestAirline = (Southwest) ois.readObject();
                        for (int i = 0; i < southwestAirline.getPassengerStrings().size(); i++) {
                            passengersAsString += southwestAirline.getPassengerStrings().get(i) + '\n';
                        }
                    }
                } catch (IOException | ClassNotFoundException io) {
                    io.printStackTrace();
                }

                passengersAsString += newPassenger.displayPassenger();
                txtArea6.setText(passengersAsString);
            }
        });

        // Render the frame with all Cards
        frame.add(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private static boolean checkInputs(String first, String last, String age) {
        if (first.equals("") || first == null || !checkString(first)) {
            JOptionPane.showMessageDialog(null, "Please Enter a Valid Name",
                    "Error, Invalid Name",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (last.equals("") || last == null || !checkString(last)) {
            JOptionPane.showMessageDialog(null, "Please Enter a Valid Name",
                    "Error, Invalid Name",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } else
            try {
                Integer.parseInt(age);
                if (Integer.parseInt(age) <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please Enter a Valid Age",
                        "Error, Invalid Age",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

        return true;
    }

    private static boolean checkString(String s) {

        boolean output = true;
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= 65 && s.charAt(i) <= 90) && !(s.charAt(i) >= 97 || s.charAt(i) <= 122) &&
                    !(s.charAt(i) == 45)) {
                output = false;
            }
        }

        return output;
    }

    private void createScreen1() {
        // First Panel
        GridBagLayout layout = new GridBagLayout();
        panel1.setLayout(layout);
        addComp(panel1, screenOneLabelOne, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE);

        ImageIcon image = new ImageIcon("/Users/vamsikolluri/Documents/java projects/Project05/images/purdue.png");
        JLabel resImage = new JLabel(image, JLabel.CENTER);
        addComp(panel1, resImage, 0, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.add(btnExit1);
        btnPanel.add(btnBookFlt1);
        addComp(panel1, btnPanel, 0, 2, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE);
        panel1.setBackground(Color.GRAY);
    }

    private void createScreen2() {
        // Second Panel
        GridBagLayout layout2 = new GridBagLayout();
        panel2.setLayout(layout2);
        addComp(panel2, screenTwoLabelOne, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JPanel btnPanel2 = new JPanel();
        btnPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel2.add(btnExit2);
        btnPanel2.add(btnBookFlt2);
        addComp(panel2, btnPanel2, 0, 3, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE);
        panel2.setBackground(Color.GRAY);

    }

    private void createScreen3() {
        // Third Panel
        // Making a call to the server to find out how many current passengers there are.
        // If flight is full, remove the option from the JComboBox.
        alaskaAirline = new Alaska();
        deltaAirline = new Delta();
        southwestAirline = new Southwest();

        alaskaGate = new Gate();
        deltaGate = new Gate();
        southwestGate = new Gate();

        try {
            oos.writeObject(new Alaska());
            oos.flush();
            alaskaAirline = (Alaska) ois.readObject();

            oos.writeObject(new Delta());
            oos.flush();
            deltaAirline = (Delta) ois.readObject();

            oos.writeObject(new Southwest());
            oos.flush();
            southwestAirline = (Southwest) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<String> airlinesAvailable = new ArrayList<>();

        if (deltaAirline.getPassengerStrings().size() < deltaAirline.numberOfSeats()) {
            airlinesAvailable.add("Delta");
        }

        if (southwestAirline.getPassengerStrings().size() < southwestAirline.numberOfSeats()) {
            airlinesAvailable.add("Southwest");
        }

        if (alaskaAirline.getPassengerStrings().size() < alaskaAirline.numberOfSeats()) {
            airlinesAvailable.add("Alaska");
        }

        String[] airlines = new String[airlinesAvailable.size()];

        int i = 0;

        for (String string : airlinesAvailable) {
            airlines[i] = string;
            i++;
        }

        GridBagLayout layout3 = new GridBagLayout();
        panel3.setLayout(layout3);
        addComp(panel3, screenThreeLabelOne, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        // Create a combo box that will hold the Airlines
//        String[] airlines = {"Delta", "SouthWest", "Alaska"};
        airlinesList = new JComboBox(airlines);
        addComp(panel3, airlinesList, 0, 1, 1, 3, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        // Get Airlines Message
        airlinesList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String airline = (String) cb.getSelectedItem();

                if (airline.equals("Delta")) {
                    line = new Delta();
                    airlineMsg.setText(Delta.getMessage());
                } else if (airline.equals("Alaska")) {
                    line = new Alaska();
                    airlineMsg.setText(Alaska.getMessage());
                } else if (airline.equals("Southwest")) {
                    line = new Southwest();
                    airlineMsg.setText(Southwest.getMessage());
                }

            }
        });
        addComp(panel3, airlineMsg, 0, 2, 1, 3, GridBagConstraints.WEST, GridBagConstraints.NONE);
        JPanel btnPanel3 = new JPanel();
        btnPanel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel3.add(btnExit3);
        btnPanel3.add(btnChooseFlt);
        addComp(panel3, btnPanel3, 0, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        panel3.setBackground(Color.GRAY);


        //Dealing with hitting the backslash key to open the window
        JRootPane jrp = frame.getRootPane();
        ActionMap am = jrp.getActionMap();
        InputMap im = jrp.getInputMap(jrp.WHEN_IN_FOCUSED_WINDOW);
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SLASH, 0, true);
        im.put(stroke, "open");
        AbstractAction abstractAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPassengerDialog(line);
            }
        };

        am.put("open", abstractAction);
    }

    private void createScreen4() {
        // Fourth Panel
        GridBagLayout layout4 = new GridBagLayout();
        panel4.setLayout(layout4);
        addComp(panel4, screenFourLabelOne, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JPanel btnPanel4 = new JPanel();
        btnPanel4.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel4.add(btnExit4);
        btnPanel4.add(btnAltFlt1);
        btnPanel4.add(btnBookFlt3);
        addComp(panel4, btnPanel4, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        panel4.setBackground(Color.GRAY);
    }

    private void createScreen5() {
        // Fifth Panel
        GridBagLayout layout5 = new GridBagLayout();
        panel5.setLayout(layout5);
        addComp(panel5, screenFiveLabelOne, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        JLabel fNameLbl = new JLabel(resLbl51);
        addComp(panel5, fNameLbl, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        fName = new JTextField(40);
        addComp(panel5, fName, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        JLabel lastNameLbl = new JLabel(resLbl52);
        addComp(panel5, lastNameLbl, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        lName = new JTextField(40);
        addComp(panel5, lName, 0, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        JLabel ageLbl = new JLabel(resLbl53);
        addComp(panel5, ageLbl, 0, 5, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        ageTxt = new JTextField(40);
        addComp(panel5, ageTxt, 0, 6, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JPanel btnPanel5 = new JPanel();
        btnPanel5.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel5.add(btnExit5);
        btnPanel5.add(btnNext5);
        addComp(panel5, btnPanel5, 0, 7, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        panel5.setBackground(Color.GRAY);
    }

    private void createScreen6() {
        // Screen 6
        GridBagLayout layout6 = new GridBagLayout();
        panel6.setLayout(layout6);
        JLabel screenSixLabelOne = new JLabel("<html><h3>" + resLbl6 + line.getAirlineName() + "</h3></html>");
        addComp(panel6, screenSixLabelOne, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        JLabel screenSixLabelTwo = new JLabel(resLbl61);
        addComp(panel6, screenSixLabelTwo, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        String gateToGo = "";

        switch (line.toString()) {
            case "Alaska":
                gateToGo = alaskaGate.toString();
                break;
            case "Delta":
                gateToGo = deltaGate.toString();
                break;
            case "Southwest":
                gateToGo = southwestGate.toString();
                break;
        }

        JLabel screenSixLabelThree = new JLabel(resLbl62 + " " + gateToGo); //+ line.getGate().toString()
        addComp(panel6, screenSixLabelThree, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JScrollPane jsp = new JScrollPane(panel6);

        txtArea6 = new JTextArea(6, 40);
        String passengersAsString = "";

        switch (line.toString()) {
            case "Alaska":
                for (int i = 0; i < alaskaAirline.getPassengerStrings().size(); i++) {
                    passengersAsString += alaskaAirline.getPassengerStrings().get(i) + '\n';
                }
                break;
            case "Delta":
                for (int i = 0; i < deltaAirline.getPassengerStrings().size(); i++) {
                    passengersAsString += deltaAirline.getPassengerStrings().get(i) + '\n';
                }
                break;
            case "Southwest":
                for (int i = 0; i < southwestAirline.getPassengerStrings().size(); i++) {
                    passengersAsString += southwestAirline.getPassengerStrings().get(i) + '\n';
                }
                break;
        }

        txtArea6.setText(passengersAsString);

        JLabel screenSixBoardingPass = new JLabel(passengerBoardingPass.toString());
        //txtArea6.setText(passengerBoardingPass.toString());
        addComp(panel6, txtArea6, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        addComp(panel6, screenSixBoardingPass, 0, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        JPanel btnPanel6 = new JPanel();
        btnPanel6.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel6.add(btnExit6);
        btnPanel6.add(btnRefresh);
        addComp(panel6, btnPanel6, 0, 7, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        panel6.setBackground(Color.GRAY);
    }

    private void showPassengerDialog(Airline currentAirline) {

        passengerList = new String[31];

        try {
            oos.writeObject(currentAirline);
            oos.flush();

            switch (currentAirline.toString()) {
                case "Alaska":
                    alaskaAirline = (Alaska) ois.readObject();
                    currentAirline = alaskaAirline;
                    passengerList = new String[alaskaAirline.getPassengerStrings().size() + 1];
                    passengerList[0] = "Passenger List for " + currentAirline.getAirlineName() + " " +
                            (currentAirline.getPassengerStrings().size()) + " : " + currentAirline.numberOfSeats();
                    for (int i = 1; i < passengerList.length; i++) {
                        passengerList[i] = alaskaAirline.getPassengerStrings().get(i - 1);
                    }
                    break;
                case "Delta":
                    deltaAirline = (Delta) ois.readObject();
                    currentAirline = deltaAirline;
                    passengerList = new String[deltaAirline.getPassengerStrings().size() + 1];
                    passengerList[0] = "Passenger List for " + currentAirline.getAirlineName() +
                            " " + (currentAirline.getPassengerStrings().size()) +
                            " : " + currentAirline.numberOfSeats();
                    for (int i = 1; i < passengerList.length; i++) {
                        passengerList[i] = deltaAirline.getPassengerStrings().get(i - 1);
                    }
                    break;
                case "Southwest":
                    southwestAirline = (Southwest) ois.readObject();
                    currentAirline = southwestAirline;
                    passengerList = new String[southwestAirline.getPassengerStrings().size() + 1];
                    passengerList[0] = "Passenger List for " + currentAirline.getAirlineName() + " " +
                            (currentAirline.getPassengerStrings().size()) +
                            " : " + currentAirline.numberOfSeats();
                    for (int i = 1; i < passengerList.length; i++) {
                        passengerList[i] = southwestAirline.getPassengerStrings().get(i - 1);
                    }
                    break;
                default:
                    System.out.println("problem in showPassengerDialog");
                    break;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Show Passenger list for the Airline
        JFrame floatingFrame = new JFrame("Passenger  List");
        floatingFrame.setSize(400, 300);
        GridBagLayout layout = new GridBagLayout();
        floatingFrame.setLayout(layout);

        JPanel dlgPanel = new JPanel();

        String lblTxt = "<html><h2>Passenger List for " + currentAirline.getAirlineName() +
                " " + currentAirline.getPassengerStrings().size() + ":" +
                currentAirline.numberOfSeats() + '\n' + "</h2></html>";
        JLabel dlgLbl = new JLabel(lblTxt);
        //addComp(dlgPanel, dlgLbl, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        JList passengers = new JList(passengerList);
        addComp(dlgPanel, passengers, 0, 1, 1, 3,
                GridBagConstraints.CENTER, GridBagConstraints.NONE);
        JScrollPane scrollbar1 = new JScrollPane(passengers, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton dlgExit = new JButton("Exit");
        btnPanel.add(dlgExit);
        addComp(dlgPanel, btnPanel, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        dlgPanel.setBackground(Color.GRAY);
        floatingFrame.add(dlgPanel);

        dlgExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                floatingFrame.dispose();
            }
        });

        floatingFrame.setTitle("Passenger  List");
        floatingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        floatingFrame.add(dlgPanel);
        floatingFrame.add(scrollbar1);
        floatingFrame.pack();
        floatingFrame.setVisible(true);

        JRootPane jrp = floatingFrame.getRootPane();
        ActionMap am = jrp.getActionMap();
        InputMap im = jrp.getInputMap(jrp.WHEN_IN_FOCUSED_WINDOW);
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
        im.put(stroke, "close");
        AbstractAction abstractAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                floatingFrame.dispose();
            }
        };

        am.put("close", abstractAction);
    }

    private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth,
                         int compHeight, int place, int stretch) {

        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = xPos;
        gridConstraints.gridy = yPos;
        gridConstraints.gridwidth = compWidth;
        gridConstraints.gridheight = compHeight;
        gridConstraints.weightx = 100;
        gridConstraints.weighty = 100;
        gridConstraints.insets = new Insets(5, 5, 5, 5);
        gridConstraints.anchor = place;
        gridConstraints.fill = stretch;
        thePanel.add(comp, gridConstraints);
    }

    private static boolean isParsable(String string) {
        return string.chars()
                .mapToObj(Character::isDigit)
                .reduce(Boolean::logicalAnd)
                .orElse(Boolean.FALSE);
    } //isParsable


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Stage 1
                hostname = JOptionPane.showInputDialog(null, "What is the hostname you'd like to connect",
                        TITLE_HOST, JOptionPane.OK_CANCEL_OPTION);
                if (hostname != null) {
                    // Stage 2
                    portString = JOptionPane.showInputDialog(null, "What is the port you'd like to connect",
                            TITLE_PORT, JOptionPane.OK_CANCEL_OPTION);
                }
                if (portString != null && isParsable(portString)) {
                    new ReservationClient();
                } else {
                    String message = "Invalid Host & Port !!!";
                    JOptionPane.showConfirmDialog(null,
                            message, "Thank You!", JOptionPane.DEFAULT_OPTION);
                    System.out.println("Exiting...");
                }
            }
        });
    }

    /**
     * Project 5 - ResponseListener.java
     *
     * The Response Listener
     *
     * N/A
     *
     * @author Benjamin Yan, Vamsi Kolluri
     * @version December 2, 2019
     */

    static class ResponseListener {

        public static void main(String[] args) {
            ReservationServer server;

            try {
                server = new ReservationServer();
            } catch (IOException e) {
                e.printStackTrace();

                return;
            }
            server.serveClients();
        }
    }

}