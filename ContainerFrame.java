import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * ContainerFrame class extends JFrame and represents the main frame of the Polygon Drawing Tool
 */
public class ContainerFrame extends JFrame {

    // Components and data for the ContainerFrame

    // Color selector for choosing polygon colors from the ColourSelector window
    protected ColourSelector colourChooser;

    // Represents currently selected polygon for drawing
    protected RegPolygon selectedPolygon;

    // Selected color for the polygon
    protected Color selectedColor;

    // Text field for entering the number of sides of a polygon
    protected JTextField sidesField;

    // Text field for entering the starting angle of a polygon
    protected JTextField angleField;

    // Text field for entering the radius of a polygon
    protected JTextField radiusField;

    // Text field for entering the ID of a polygon
    protected JTextField idField;

    // Checkbox for selecting fill option
    protected JCheckBox fillCheckBox;

    // Button for adding a new polygon
    protected JButton addPolygon;

    // Button for searching for a polygon by ID
    protected JButton searchButton;

    // Button for sorting polygons by their IDs
    protected JButton sortButton;

    // Button for choosing the color of a polygon
    protected JButton colourButton;

    // Button for displaying polygons
    protected JButton displayButton;
    
    // Text area for displaying shape updates and information
    protected JTextArea textArea;

    // ArrayList to store all created polygons
    private final ArrayList<RegPolygon> polygonList = new ArrayList<>();

    //Creates and initialises the components for the ContainerFrame
    public void createComponents() {

        // Event handler for ContainerFrame, concerns ActionListener buttons
        ContainerFrameHandler handler = new ContainerFrameHandler(this);

        // Creates a new JPanel for entering polygon details
        JPanel inputPanel = new JPanel();

        // Sets input panel layout manager to GridBagLayout
        GridBagLayout gb = new GridBagLayout();
        inputPanel.setLayout(gb);

        // Creates GridBagConstraints for controlling GridBagLayout layout
        GridBagConstraints gbc = new GridBagConstraints();

        // Creates a JLabel for ID, aligns text to the left and sets spacing around the label
        JLabel idLabel = new JLabel("ID:");
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Set the constraints for the label in the GridBagLayout
        gb.setConstraints(idLabel, gbc);

        // Add the label to the input panel
        inputPanel.add(idLabel, gbc);

        // Creates JTextField for ID input with a width of 5 columns
        idField = new JTextField(5);

        // Adds a KeyListener to ID field for handling key input, handled in ContainerFrameHandler
        idField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                handler.handleIDKeyInput(e, idField);
            }
        });

        // Sets spacing for ID field
        gbc.insets = new Insets(5, 0, 5, 20);

        // Sets constraints for the field in the GridBagLayout
        gb.setConstraints(idField, gbc);

        // Add field to the input panel
        inputPanel.add(idField, gbc);


        // Creates JLabel for sides, aligns text left and adds spacing and constraints, add to inputPanel
        JLabel sidesLabel = new JLabel("Number of Sides:");
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);
        gb.setConstraints(sidesLabel, gbc);
        inputPanel.add(sidesLabel, gbc);

        // Creates JTextField for entering side number, with spacing and constraints
        sidesField = new JTextField(5);
        gbc.insets = new Insets(0, 0, 0, 20);
        gb.setConstraints(sidesField, gbc);

        // Add sides field to the input panel
        inputPanel.add(sidesField, gbc);


        // Creates JLabel for angle, aligns text left and adds spacing and constraints, add to inputPanel
        JLabel angleLabel = new JLabel("Starting Angle:");
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);
        gb.setConstraints(angleLabel, gbc);
        inputPanel.add(angleLabel, gbc);

        // Creates JTextField starting angle with 5 column width, with spacing, constraints and added to inputPanel
        angleField = new JTextField(5);
        gbc.insets = new Insets(5, 0, 5, 20);
        gb.setConstraints(angleField, gbc);
        inputPanel.add(angleField, gbc);


        // Creates JLabel for radius, aligns text left and adds spacing and constraints, add to inputPanel
        JLabel radiusLabel = new JLabel("Radius:");
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);
        gb.setConstraints(radiusLabel, gbc);
        inputPanel.add(radiusLabel, gbc);

        // Creates JTextField for radius with 5 column width, with spacing, constraints and added to inputPanel
        radiusField = new JTextField(5);
        gbc.insets = new Insets(5, 0, 5, 20);
        gb.setConstraints(radiusField, gbc);
        inputPanel.add(radiusField, gbc);

        // Create a JLabel for the fill text
        JLabel fillLabel = new JLabel("Fill:");
        gbc.insets = new Insets(5, 5, 5, 5);
        gb.setConstraints(fillLabel, gbc);
        inputPanel.add(fillLabel, gbc);

        // Create a JCheckBox for selecting fill option
        fillCheckBox = new JCheckBox();
        gbc.insets = new Insets(5, 0, 5, 20);
        gb.setConstraints(fillCheckBox, gbc);
        inputPanel.add(fillCheckBox, gbc);

        // Creates JLabel for radius, aligns text left and adds spacing and constraints, add to inputPanel
        JLabel colorLabel = new JLabel("Colour:");
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);
        gb.setConstraints(colorLabel, gbc);
        inputPanel.add(colorLabel, gbc);

        // Create a JButton for choosing polygon colour and set size of button
        colourButton = new JButton("");
        colourButton.setPreferredSize(new Dimension(35, 35));

        // Set the initial background color of the color button to black (default colour)
        colourButton.setBackground(Color.BLACK);

        // Prevents colour changes in button when momentarily clicked and ensures that a solid colour is present
        colourButton.setContentAreaFilled(false);
        colourButton.setOpaque(true);

        // Add ActionListener to colour button for handling color selection
        colourButton.addActionListener(handler);

        // Set tooltip for colour button to provide information about colour selection
        colourButton.setToolTipText("Choose an outline colour\nfor the polygon");

        // Set spacing, constraints for button and add to inputPanel
        gbc.insets = new Insets(5, 5, 5, 5);
        gb.setConstraints(colourButton, gbc);
        inputPanel.add(colourButton, gbc);

        // Create the main panel to hold the drawPanel and textPanel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();

        // Draw panel for displaying polygons
        JPanel drawPanel = new ContainerPanel(this);
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.weightx = 2.0;
        gbcMain.weighty = 1.0;
        gbcMain.fill = GridBagConstraints.BOTH;
        mainPanel.add(drawPanel, gbcMain);

        // Text area for displaying output of shape information, opening with a welcome message
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(5, 5, 5, 5));
        textArea.setText("Welcome to Polygon Drawing Tool!\n\n" +
                        "- use the top bar menu to choose customised information for adding polygons\n" +
                        "- use the bottom bar menu to add, search, sort and display polygons\n" +
                        "- the command line displays all program activity, and displays polygons\n" +
                        "- please note that all polygons will be deleted upon closing this window\n\n" +
                        "Current Polygon: none; add a polygon\n"
        );

        // Add a scroll pane for the text area, to scroll up and down the text output
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 200)); 

        gbcMain.gridx = 1;
        gbcMain.weightx = 1.0; 
        gbcMain.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbcMain);

        /* Sets initial delay of ToolTipManager to 0 milliseconds, to eliminate any delay before showing tooltips, and
        ensures that tooltips are displayed immediately upon hovering over components */
        ToolTipManager.sharedInstance().setInitialDelay(0);

        // Button to add a new polygon, with a tooltip about the button and ActionListener for the button
        addPolygon = new JButton("Add");
        addPolygon.setToolTipText("<html>Enter a unique ID and shape details in <br>the menu above to add a new " +
                "polygon</html>");
        addPolygon.addActionListener(handler);

        // Button to search a polygon by ID, with a tooltip about the button and ActionListener for the button
        searchButton = new JButton("Search");
        searchButton.setToolTipText("Search for a polygon with a unique ID");
        searchButton.addActionListener(handler);

        // Button to sort polygons by ID, with a tooltip about the button and ActionListener for the button
        sortButton = new JButton("Sort");
        sortButton.setToolTipText("Sort polygons by their respective IDs");
        sortButton.addActionListener(handler);

        // Button to display polygons by ID, with a tooltip about the button and ActionListener for the button
        displayButton = new JButton("Display");
        displayButton.setToolTipText("Display all polygons in the command line");
        displayButton.addActionListener(handler);


        // Creates JPanel to contain buttons with a centered FlowLayout, and custom gaps between buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add addPolygon button to the button panel with spacing
        buttonPanel.add(addPolygon);
        buttonPanel.add(Box.createHorizontalStrut(10));

        // Add searchButton button to the button panel with spacing
        buttonPanel.add(searchButton);
        buttonPanel.add(Box.createHorizontalStrut(10));

        // Add searchButton button to the button panel with spacing
        buttonPanel.add(sortButton);
        buttonPanel.add(Box.createHorizontalStrut(10));

        // Add displayButton to the button panel
        buttonPanel.add(displayButton);

        // Add the input panel to the top of the frame
        add(inputPanel, BorderLayout.NORTH);

        // Add the main panel to the center of the frame
        add(mainPanel, BorderLayout.CENTER);

        // Add the button panel to the bottom of the frame
        add(buttonPanel, BorderLayout.SOUTH);

    }

    // Retrieves the list of polygons stored in ContainerFrame
    public ArrayList<RegPolygon> getPolygonList() {
        // Returns ArrayList containing polygons
        return polygonList;
    }

}
