import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * ColourSelector class provides a graphical user interface for selecting RGB colors
 * With sliders for adjusting the red, green, and blue components, color display panel, and a button to confirm color
 * selection
 */
public class ColourSelector {

    // JFrame containing the colour chooser, and uses the ContainerFrame for the selected colour
    protected JFrame frame;
    protected ContainerFrame containerFrame;

    // RGB sliders
    protected JSlider redSlider;
    protected JSlider greenSlider;
    protected JSlider blueSlider;

    // Labels to display current values of RGB sliders
    protected JLabel redValue;
    protected JLabel greenValue;
    protected JLabel blueValue;

    // Panel displaying the selected colour from the user
    protected JPanel square;

    // Button confirming colour selection
    protected JButton selectColourButton;

    // Event handler for colour selector
    protected ColourSelectorHandler handler;

    public ColourSelector(ContainerFrame frame) {
        this.containerFrame = frame;
    }

    /*
     * Creates graphical user interface for the colour selector
     */
    public void createColorSelector() {

        // Creates new instance of ColourSelectorHandler to handle events for the colour selector
        handler = new ColourSelectorHandler(this);

        // Creates a new JFrame for the colour chooser with a JPanel to organise the components with GridBagLayout
        frame = new JFrame("Colour Chooser");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // JPanel containing organizing RGB sliders with a GridBagLayout and adds empty borders around it
        JPanel sliderSection = new JPanel();
        sliderSection.setLayout(new GridBagLayout());
        sliderSection.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints sliderSectionGbc = new GridBagConstraints();

        // JLabels for each RGB slider, with height adjustments
        JLabel redLabel = new JLabel("Red");
        redLabel.setPreferredSize(new Dimension(50, redLabel.getPreferredSize().height));
        JLabel greenLabel = new JLabel("Green");
        greenLabel.setPreferredSize(new Dimension(50, greenLabel.getPreferredSize().height));
        JLabel blueLabel = new JLabel("Blue");
        blueLabel.setPreferredSize(new Dimension(50, blueLabel.getPreferredSize().height));

        // Creates JSliders for each component with a horizontal orientation, a range of 0 to 255, and an initial value of 0
        redSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);

        // Creates a JLabels to display the current value of the sliders and sets initial values (defaulting to black)
        redValue = new JLabel(String.valueOf(redSlider.getValue()));
        redValue.setPreferredSize(new Dimension(30, redValue.getPreferredSize().height));
        greenValue = new JLabel(String.valueOf(greenSlider.getValue()));
        greenValue.setPreferredSize(new Dimension(30, greenValue.getPreferredSize().height));
        blueValue = new JLabel(String.valueOf(blueSlider.getValue()));
        blueValue.setPreferredSize(new Dimension(30, blueValue.getPreferredSize().height));


        // Sets grid positions for each label, slider and value in the slider section, and create spacing
        sliderSectionGbc.gridx = 0;
        sliderSectionGbc.gridy = 0;
        sliderSection.add(redLabel, sliderSectionGbc);

        sliderSectionGbc.gridx = 1;
        sliderSection.add(redSlider, sliderSectionGbc);

        sliderSectionGbc.gridx = 2;
        sliderSection.add(redValue, sliderSectionGbc);

        sliderSectionGbc.gridy = 1;
        sliderSection.add(Box.createRigidArea(new Dimension(0, 20)), sliderSectionGbc);

        sliderSectionGbc.gridx = 0;
        sliderSectionGbc.gridy = 2;
        sliderSection.add(greenLabel, sliderSectionGbc);

        sliderSectionGbc.gridx = 1;
        sliderSection.add(greenSlider, sliderSectionGbc);

        sliderSectionGbc.gridx = 2;
        sliderSection.add(greenValue, sliderSectionGbc);

        sliderSectionGbc.gridy = 3;
        sliderSection.add(Box.createRigidArea(new Dimension(0, 20)), sliderSectionGbc);

        sliderSectionGbc.gridx = 0;
        sliderSectionGbc.gridy = 4;
        sliderSection.add(blueLabel, sliderSectionGbc);

        sliderSectionGbc.gridx = 1;
        sliderSection.add(blueSlider, sliderSectionGbc);

        sliderSectionGbc.gridx = 2;
        sliderSection.add(blueValue, sliderSectionGbc);

        // JPanel to contain the slider section with GridBagLayout, and adds it to sliderSection, with grid positions
        JPanel sliderSectionContainer = new JPanel(new GridBagLayout());
        sliderSectionContainer.add(sliderSection);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.67;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        // Add sliderSectionContainer to main panel
        panel.add(sliderSectionContainer, gbc);


        // JPanel for colourDisplay selection, with GridBagLayout constraints
        JPanel colorDisplaySection = new JPanel();
        colorDisplaySection.setLayout(new GridBagLayout());
        GridBagConstraints colorDisplaySectionGbc = new GridBagConstraints();

        /*
         * Creates a square JPanel to represent the selected color, with size, background colour based on sliders,
         * and a border to distinguish the square; alongside grid positions and spacing
         */
        square = new JPanel();
        square.setPreferredSize(new Dimension(100, 100));
        square.setBackground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
        square.setBorder(new LineBorder(Color.black, 1));

        colorDisplaySectionGbc.gridx = 0;
        colorDisplaySectionGbc.gridy = 0;
        colorDisplaySection.add(square, colorDisplaySectionGbc);
        colorDisplaySectionGbc.gridy = 1;
        colorDisplaySection.add(Box.createRigidArea(new Dimension(0, 20)), colorDisplaySectionGbc);

        // Creates a JButton with an ActionListener to record the selected colour and a ToolTip for information
        selectColourButton = new JButton("Select Colour");
        selectColourButton.setToolTipText("Confirm outline outline colour for the polygon");
        selectColourButton.addActionListener(handler);

        // Sets grid position for the button and adds it to colour selection
        colorDisplaySectionGbc.gridy = 2;
        colorDisplaySection.add(selectColourButton, colorDisplaySectionGbc);

        // Sets grid positioning for the colour display selection in the main panel and addition of the section
        gbc.gridx = 1;
        gbc.weightx = 0.33;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(colorDisplaySection, gbc);

        // Adds main panel to the frame
        frame.add(panel);


        // Adds ChangeListener to the redSlider, greenSlider, and blueSlider to handle changes in slider values
        redSlider.addChangeListener(handler);
        greenSlider.addChangeListener(handler);
        blueSlider.addChangeListener(handler);

        // Sets properties of main frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(550, 250);
        frame.setResizable(false);
        frame.setVisible(true);

    }

}