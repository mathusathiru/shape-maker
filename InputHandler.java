import javax.swing.*;

/**
 * Handles input validation and clearing of text fields in the ContainerFrame.
 */
public class InputHandler {

    // ContainerFrame reference to handle input validation.
    private ContainerFrame theFrame;

    public InputHandler(ContainerFrame frame) {

        this.theFrame = frame;
    }


    // Validates ID field input in the ContainerFrame; returns the validated ID or -1 if validation fails
    protected int validateIDInput() {

        // Get text from  ID field
        String idText = theFrame.idField.getText();

        // Checks if the ID field is empty
        if (idText.equals("")) {
            System.out.println("Error: ID is null; please enter a valid 6 digit integer ID value\n");
            JOptionPane.showMessageDialog(null, "Please enter a 6 digit ID value",
                    "Missing Value", JOptionPane.WARNING_MESSAGE);
            return -1;
        }

        try {
            // Checks if the ID has exactly 6 digits
            if (idText.length() != 6) {
                // Checks to see if the ID is a valid number
                Integer.parseInt(idText);
                System.out.println("Error: Invalid ID " + idText + " (too short at " + idText.length() +
                        " characters); ID must be a 6 digit integer value e.g. 126658\n");
                JOptionPane.showMessageDialog(null, "Invalid ID " + idText + ": ID is too short" +
                        " and must be a 6 digit number e.g. 126658", "Invalid ID", JOptionPane.ERROR_MESSAGE);
                clearIDField();
                return -1;
            }

            // Returns parsed valid ID
            return Integer.parseInt(idText);

        } catch (NumberFormatException e) {
            // Handles cases where parsing to an int failed
            System.out.println("Error: Invalid ID " + idText + " - ID must be a valid 6 digit number e.g. 126658\n");
            JOptionPane.showMessageDialog(null, "Invalid ID " + idText + " - ID must be a valid" +
                    " 6 digit number e.g. 126658", "Invalid ID", JOptionPane.ERROR_MESSAGE);
            clearIDField();
            return -1;
        }
    }

    // Validates side input in ContainerFrame; returns the validated number of sides or -1 if validation fails
    protected int validateSideInput() {

        // Get the text from sides field
        String sideText = theFrame.sidesField.getText();

        // Check if sides field is empty
        if (sideText.equals("")) {
            System.out.println("Error: number of sides is null; please enter a positive integer value for the number" +
                    " of polygon sides e.g. 8\n");
            JOptionPane.showMessageDialog(null, "Please enter a whole number for the number " +
                            "of sides",  "Missing Value", JOptionPane.WARNING_MESSAGE);
            return -1;
        }

        try {
            // Attempt to parse sides as an integer
            int sides = Integer.parseInt(sideText);

            // Checks if the number of sides is negative
            if (sides < 0) {
                System.out.println("Error: Invalid Side Number: " + sides + " - please enter a positive value for the" +
                        " number of polygon sides e.g. 8\n");
                JOptionPane.showMessageDialog(null, "Number of sides must be a positive whole" +
                                " number", "Invalid Sides", JOptionPane.ERROR_MESSAGE);
                clearSidesField();
                return -1;
            }
            // Displays an error if the number of sides is less than 3; invalid polygon
            else if (sides < 3) {
                JOptionPane.showMessageDialog(null, "Please enter a side number of three " +
                                "or above for a valid polygon\n 0-2 sides will have an empty polygon or a " +
                                "single straight line", "Invalid Sides", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error Invalid Side Number: " + sides + " - will not print a valid polygon; " +
                        "the smallest possible number of sides for a valid polygon is three (for a triangle)," +
                        "\nentering a value less than three will print an empty display (no sides or 1 side) or a " +
                        "straight line (2 sides)\n");
                clearSidesField();
                return -1;
            }

            // Returns validated sides number
            return sides;

        } catch (NumberFormatException e) {
            // Handles cases where parsing to an int failed
            System.out.println("Error: Invalid Side Number " + sideText + " - please enter a positive integer value " +
                    "for the number of polygon sides, e.g. 8\n");
            JOptionPane.showMessageDialog(null, "Number of sides must be a whole integer number",
                    "Invalid Sides", JOptionPane.ERROR_MESSAGE);
            clearSidesField();
            return -1;
        }
    }

    /*
     * Validates the input for the angle in the ContainerFrame.
     * Returns the validated angle or -1 if validation fails.
     */
    protected double validateAngleInput() {

        // Get the text from the angle field.
        String angleText = theFrame.angleField.getText();

        // Check if the angle field is empty.
        if (angleText.equals("")) {
            System.out.println("Error: angle value is null; please enter an angle value such as 90, or 0 for no " +
                    "angle rotation\n");
            JOptionPane.showMessageDialog(null, "Please enter an angle value",
                    "Missing Value", JOptionPane.WARNING_MESSAGE);
            return -1;
        }

        try {
            // Parse the angle as a double.
            return Double.parseDouble(angleText);

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid Angle: " + angleText + " - please enter an angle value such " +
                    "as 90, or 0 for no angle rotation\n");
            // Handle the case where parsing the angle as a double fails.
            JOptionPane.showMessageDialog(null, "Angle must be a valid number (can be decimal)",
                    "Invalid Angle", JOptionPane.ERROR_MESSAGE);
            clearAngleField();
            return -1;
        }
    }


    // Validates radius input in ContainerFrame; returns the validated radius or -1 if validation fails
    protected double validateRadiusInput() {

        // Gets text from radius field
        String radiusText = theFrame.radiusField.getText();

        // Checks if the radius field is empty
        if (radiusText.equals("")) {
            System.out.println("Error: radius value is null; please enter a positive radius value such as 40\n");
            JOptionPane.showMessageDialog(null, "Please enter a radius value",
                    "Missing Value", JOptionPane.WARNING_MESSAGE);
            return -1;
        }

        try {
            // Parses radius as a double
            double radius = Double.parseDouble(radiusText);

            // Checks if the radius is a negative number
            if (radius < 0) {
                System.out.println("Error: Invalid Radius " + radius + " - please enter a positive radius value " +
                        "such as 40\n");
                JOptionPane.showMessageDialog(null, "Radius must be a positive number",
                        "Invalid Radius", JOptionPane.ERROR_MESSAGE);
                clearRadiusField();
                return -1;
            }
            // Displays an error if the radius is 0
            else if (radius == 0) {
                System.out.println("Error: Invalid Radius 0 - polygon will not be displayed; please enter a positive" +
                        " radius value above 0\n");
                JOptionPane.showMessageDialog(null, "Radius set to 0; polygon will not be " +
                        "displayed", "Invalid Radius", JOptionPane.ERROR_MESSAGE);
                clearRadiusField();
                return -1;
            }
            return radius;

        } catch (NumberFormatException e) {
            // Handles cases where parsing the radius as a double failed
            System.out.println("Error: Invalid Radius: " + radiusText + " - please enter a positive radius value " +
                    "such as 40\n");
            JOptionPane.showMessageDialog(null, "Radius must be a valid number (can be decimal)",
                    "Invalid Radius", JOptionPane.ERROR_MESSAGE);
            clearRadiusField();
            return -1;
        }
    }


    // Validates search ID input in the ContainerFrame; returns the validated ID or -1 if validation fails
    protected int validateSearchIDInput(String searchID) {

        // Checks if the searchID is empty
        if (searchID.equals("")) {
            System.out.println("Error: Search ID is null; please enter a valid 6 digit integer Search ID value\n");
            JOptionPane.showMessageDialog(null, "Please enter a 6 digit ID value",
                    "Missing Value", JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        try {
            // Checks if searchID has exactly 6 digits
            System.out.println("Error: Invalid Search ID " + searchID + " - ID must be a 6 digit integer value e.g. " +
                    "126658\n");
            if (searchID.length() != 6) {
                JOptionPane.showMessageDialog(null, "Invalid Search ID " + searchID + ": ID " +
                        "must be a 6 digit number such as 126658", "Invalid Search ID", JOptionPane.ERROR_MESSAGE);

                clearIDField();
                return -1;
            }

            // Parses the searchID as an integer
            return Integer.parseInt(searchID);

        } catch (NumberFormatException e) {
            // Handles cases where parsing the searchID as an integer fails
            System.out.println("Error: Invalid Search ID " + searchID + " - ID must be a valid 6 digit number e.g " +
                    "126658\n");
            JOptionPane.showMessageDialog(null, "Invalid Search ID " + searchID + " - ID must " +
                    "be a valid 6 digit number e.g. 126658", "Invalid ID", JOptionPane.ERROR_MESSAGE);
            clearIDField();
            return -1;
        }
    }

    // Clears the content of the sidesField in the ContainerFrame
    protected void clearSidesField() {
        JTextField sidesField = theFrame.sidesField;
        sidesField.setText("");
    }

    // Clears the content of the angleField in the ContainerFrame
    protected void clearAngleField() {
        JTextField angleField = theFrame.angleField;
        angleField.setText("");
    }

    // Clears the content of the radiusField in the ContainerFrame
    protected void clearRadiusField() {
        JTextField radiusField = theFrame.radiusField;
        radiusField.setText("");
    }

    // Clears the content of the idField in the ContainerFrame
    protected void clearIDField() {
        JTextField idField = theFrame.idField;
        idField.setText("");
    }


}
