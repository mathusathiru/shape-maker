import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Handler class for managing action events in the ContainerFrame
 * Implements ActionListener to respond to button clicks, and other events e.g. key events are handled too
 */
class ContainerFrameHandler implements ActionListener {

    // Manages associated ContainerFrame and InputHandler
    private ContainerFrame theFrame;
    private InputHandler inputHandler;

    public ContainerFrameHandler(ContainerFrame frame) {
        this.theFrame = frame;
        this.inputHandler = new InputHandler(frame);
    }

    // Handles key events for ID input field, restricting input length to 6 characters
    protected void handleIDKeyInput(KeyEvent e, JTextField textField) {
        // Consumes key event if input length exceeds 6 characters
        if (textField.getText().length() >= 6) {
            e.consume();
        }
    }

    /* ActionListener responding to button clicks in ContainerFrame
     * Coordinates adding, searching, sorting, displaying buttons, and handles colour button
     */
    public void actionPerformed(ActionEvent e) {

        // Check if event source is the addPolygon button
        if (e.getSource() == theFrame.addPolygon) {

            // Adds new ID from ID field input (and checks for duplicate ID), validation done within the method
            int id = addNewID();
            // If the ID generation fails (returns -1), exit the method
            if (id == -1) {
                return;
            }

            // Validates input for number of polygon sides
            int sides = inputHandler.validateSideInput();
            // If the side input is invalid (returns -1), exit the method
            if (sides == -1) {
                return;
            }

            // Validates input for polygon angle
            double angle = inputHandler.validateAngleInput();
            // If the angle input is invalid (returns -1), exit the method
            if (angle == -1) {
                return;
            }

            // Validates input for polygon radius
            double radius = inputHandler.validateRadiusInput();
            // If the radius input is invalid (returns -1), exit the method
            if (radius == -1) {
                return;
            }

            // Obtains selected colour from current point in ContainerPanel
            Color color = theFrame.selectedColor;

            // Prints line of dashes for visual separation
            System.out.println("-".repeat(150));
            // Validation message for successfully validated polygon
            System.out.println("Validation checks passed for polygon " + theFrame.idField.getText());

            // Check if the color is not selected; if not, use default (black), otherwise, print the selected color
            if (color == null) {
                color = Color.BLACK;
                System.out.println("Default colour selected for polygon " + theFrame.idField.getText() + " (black)");
            } else {
                System.out.println("Colour selected for polygon " + theFrame.idField.getText() + ": red("
                        + color.getRed() + "), green(" + color.getRed() + "), " + "blue(" + color.getRed() + ")");
            }

            // Creates a new polygon with the validated parameters
            RegPolygon polygon = new RegPolygon(sides, angle, radius, id, color);
            // Adds the polygon to the list of polygons
            theFrame.getPolygonList().add(polygon);
            System.out.println("Polygon " + theFrame.idField.getText() + " successfully added to list");
            // Prints a line of dashes for visual separation
            System.out.println("-".repeat(150));

            // Sets selected polygon in the ContainerFrame to the newly added polygon
            theFrame.selectedPolygon = polygon;
            // Repaints ContainerFrame to update the drawing panel with the new polygon
            theFrame.repaint();

            // Prints a message indicating the currently displayed polygon
            System.out.println("Current Polygon: " + theFrame.idField.getText() + "\n");

            // Clears text fields for the next input
            clearTextFields();
            inputHandler.clearIDField();

        }

        // Check if event source is searchButton
        else if (e.getSource() == theFrame.searchButton) {

            // Obtains list of polygons from ContainerFrame
            ArrayList<RegPolygon> polygonList = theFrame.getPolygonList();

            // Checks if polygon list is empty
            if (polygonList.size() == 0) {
                System.out.println("Polygon list is empty; cannot search for a polygon\n");
                // Displays a message dialog indicating that there are no polygons to display
                JOptionPane.showMessageDialog(null, "No polygons have been added\n" +
                                "Add polygons first to enable search functionality",
                        "No Polygons", JOptionPane.INFORMATION_MESSAGE);
            } else {

                // Creates a text field for user input
                JTextField idInput = new JTextField(10);
                // Adds a key listener to handle ID input restrictions (prevents input of more than 6 characters)
                idInput.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        handleIDKeyInput(e, idInput);
                    }
                });

                // Creates object array for input message, including ID input field and input text field
                Object[] message = {
                        "Enter ID to search:", idInput
                };

                // Shows dialog box with ID input field for searching
                int option = JOptionPane.showConfirmDialog(null, message, "Search Polygon",
                        JOptionPane.OK_CANCEL_OPTION);

                // Proceed with polygon searching if OK is clicked
                if (option == JOptionPane.OK_OPTION) {
                    // Obtains the search ID from the input field
                    String searchID = idInput.getText();
                    // Searches for polygon with specified ID
                    theFrame.selectedPolygon = searchPolygon(searchID);
                    // Repaints drawing panel to update display with selected polygon
                    theFrame.repaint();
                }

                // Clears text fields for the next input
                clearTextFields();

            }

        }

        // Checks if event source is colourButton
        else if (e.getSource() == theFrame.colourButton) {

            // If the colour chooser has not been initialised, create and display it for the user
            if (theFrame.colourChooser == null) {
                theFrame.colourChooser = new ColourSelector(theFrame);
                theFrame.colourChooser.createColorSelector();
                // Set location for colour chooser
                theFrame.colourChooser.frame.setLocationRelativeTo(theFrame);
            } else {
                // If the colour chooser has been initialised, show its frame and set its location on the screen
                theFrame.colourChooser.frame.setLocationRelativeTo(theFrame);
                theFrame.colourChooser.frame.setVisible(true);
            }

        }

        // Checks if event source is sortButton
        else if (e.getSource() == theFrame.sortButton) {

            // Obtains list of polygons from getter method in ContainerFrame
            ArrayList<RegPolygon> polygonList = theFrame.getPolygonList();

            if (polygonList.size() > 1) {
                // Sort the list of polygons if the size of the list is greater than one
                Collections.sort(polygonList);
            }

            // Displays a message indicating the number of polygons sorted
            showSortMessage(polygonList.size());

            // Clears text fields to remove current input values, if any, for a fresh interface
            clearTextFields();
            inputHandler.clearIDField();

        }

        // Checks if event source is displayButton
        else if (e.getSource() == theFrame.displayButton) {

            // Obtains list of polygons from getter method in ContainerFrame
            ArrayList<RegPolygon> polygonList = theFrame.getPolygonList();

            // Checks if the polygon list is empty
            if (polygonList.size() == 0) {
                System.out.println("Polygon list is empty; no polygons to display\n");
                // Displays a message dialog indicating that there are no polygons to display
                JOptionPane.showMessageDialog(null, "No polygons to display",
                        "Display", JOptionPane.INFORMATION_MESSAGE);
            } else {

                // Displays number of polygons that will be displayed
                if (polygonList.size() == 1) {
                    System.out.println("Displaying " + polygonList.size() + " polygon:\n");
                } else if (polygonList.size() > 1) {
                    System.out.println("Displaying " + polygonList.size() + " polygons:\n");
                }

                // Iterate through polygon list and display details of each one in the command line
                for (int i = 0; i < polygonList.size(); i++) {
                    RegPolygon p = polygonList.get(i);
                    System.out.println(p);

                    // If not the last polygon, print a line of dashes to separate polygons and their information
                    if (i < polygonList.size() - 1) {
                        String dash = "-";
                        System.out.println(dash.repeat(150));
                    } else {
                        System.out.println();
                    }
                }

                // Displays a message dialog indicating the number of polygons printed to the command line
                if (polygonList.size() == 1) {
                    JOptionPane.showMessageDialog(null, "1 polygon printed to command line",
                            "Display", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, polygonList.size() +
                                    " polygons printed to command line",
                            "Display", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            // Clears text fields to remove current input values, if any, for a fresh interface
            clearTextFields();
            inputHandler.clearIDField();
        }

    }

    /*
        Validates an input polygon ID for adding to a new polygon, ensuring it does not already exist in the list of
        polygons; returns the new ID if validation passes, otherwise, displays an error message and returns -1
     */
    private int addNewID() {

        // Validates input ID using validateIDInput input handler
        int id = inputHandler.validateIDInput();

        // If the input ID is invalid, return -1
        if (id == -1) {
            return -1;
        }

        // Obtains the list of polygons from the ContainerFrame
        ArrayList<RegPolygon> polygonList = theFrame.getPolygonList();

        // Checks if input ID already exists in the list of polygons
        for (RegPolygon p : polygonList) {
            if (p.getID() == id) {
                System.out.println("Error: ID " + theFrame.idField.getText() +  "already exists in the polygon list\n");
                // Display an error message for duplicate ID
                JOptionPane.showMessageDialog(null, "ID " + theFrame.idField.getText() +
                        " already exists\nUse a new ID value","Duplicate ID", JOptionPane.ERROR_MESSAGE);

                // Clears ID field for input of a new polygon ID
                inputHandler.clearIDField();
                return -1;
            }
        }

        // Returns validated unique ID
        return id;
    }

    /*
     * Searches for a polygon with the specified ID in the list of polygons and validates the input ID before
     * performing the search; if the ID is not valid, returns null.
     * If a polygon with the given ID is found, returns the polygon; otherwise, display an error message
     */
    private RegPolygon searchPolygon(String searchId) {

        // Validates input ID, returns an empty panel if not
        int id = inputHandler.validateSearchIDInput(searchId);
        if (id == -1) {
            System.out.println("Current Polygon: none - add or search for a polygon\n");
            return null;
        }

        // Search for the polygon with the provided ID
        for (RegPolygon p : theFrame.getPolygonList()) {
            if (p.getID() == id) {
                System.out.println("Polygon " + searchId + " successfully retrieved\n");
                System.out.println("Current Polygon: " + searchId + "\n");
                return p;
            }
        }

        // Displays an error message if the polygon with the ID is not found
        System.out.println("Error: ID " + searchId +  " was not found in the polygon list\n");
        JOptionPane.showMessageDialog(null, "No polygon found with ID: " + searchId,
                "ID Not Found", JOptionPane.ERROR_MESSAGE);
        System.out.println("Current Polygon: none - add or search for a polygon\n");

        return null;
    }


    /*
     * Displays a message dialog and command line message indicating the number of polygons sorted
     */
    private void showSortMessage(int numPolygons) {

        if (numPolygons == 0) {
            // Display a message if there are no polygons to sort
            System.out.println("No polygons to sort (empty polygon list)\n");
            JOptionPane.showMessageDialog(null, "There are no polygons to sort",
                    "No Polygons", JOptionPane.INFORMATION_MESSAGE);
        } else if (numPolygons == 1) {
            System.out.println("1 polygon in polygon list; no need for sorting\n");
            // Display a message if only one polygon is present, which does not need sorting
            JOptionPane.showMessageDialog(null, "There is 1 polygon present, so there is " +
                            "no need for sorting",
                    "1 Polygon Sorted", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println(numPolygons + " polygons sorted\n");
            // Display a message indicating the number of polygons sorted (more than 1)
            JOptionPane.showMessageDialog(null, numPolygons + " polygons sorted",
                    "Polygons Sorted", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /*
     * Clears the text fields related to polygon input, including the number of sides, starting angle, and radius
     * Utilises InputHandler and ensures the fields are empty for new inputs (user does not have to manually remove)
     */
    private void clearTextFields() {
        // Clears text field for the number of sides
        inputHandler.clearSidesField();

        // Clears text field for the starting angle
        inputHandler.clearAngleField();

        // Clears text field for the radius
        inputHandler.clearRadiusField();
    }

}