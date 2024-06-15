import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class serves as the event handler for the ColourSelector class, implementing ActionListener and ChangeListener
 * Handles button click events and slider value change events
 */
public class ColourSelectorHandler implements ActionListener, ChangeListener {

    // Reference to the associated ColourSelector instance
    private final ColourSelector selector;

    public ColourSelectorHandler(ColourSelector selector) {
        this.selector = selector;
    }

    /*
     * Handles the action performed event, for button click event in ColourSelector window
     * Disposes the ColourSelector window and updates the selected color in the container frame
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtains the current background color of the square in the ColourSelector
        Color selectedColor = selector.square.getBackground();

        // Find the parent window of the button and disposes it
        Container parent = selector.selectColourButton.getParent();
        while (!(parent instanceof Window window)) {
            parent = parent.getParent();
        }
        window.dispose();

        // Updates the button in the main frame to the colour selected for the square background
        selector.containerFrame.selectedColor = selectedColor;
        selector.containerFrame.colourButton.setBackground(selectedColor);
    }


    /*
     * Handles the state changed event, specifically for the slider value change event.
     * Updates the color of the square and the corresponding value label based on the slider that triggered the event.
     */
    /*
     * Overrides the stateChanged method of the ChangeListener interface to handle changes in the state of sliders.
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        // Get the source of the event, which should be a JSlider.
        JSlider slider = (JSlider) e.getSource();

        // Check which slider triggered the event and update the color square and corresponding value label.
        if (slider == selector.redSlider) {
            colorSquare(selector);
            selector.redValue.setText(String.valueOf(slider.getValue()));
        } else if (slider == selector.greenSlider) {
            colorSquare(selector);
            selector.greenValue.setText(String.valueOf(slider.getValue()));
        } else if (slider == selector.blueSlider) {
            colorSquare(selector);
            selector.blueValue.setText(String.valueOf(slider.getValue()));
        }
    }


    /*
     * Updates the color of the square based on the current values of red, green, and blue sliders.
     */
    /*
     * Updates the background color of the color square in the ColourSelector based on the current slider values.
     */
    private void colorSquare(ColourSelector selector) {
        // Get the current values of the red, green, and blue sliders.
        int red = selector.redSlider.getValue();
        int green = selector.greenSlider.getValue();
        int blue = selector.blueSlider.getValue();

        // Create a new Color object based on the slider values and set it as the background color of the square.
        Color newColor = new Color(red, green, blue);
        selector.square.setBackground(newColor);
    }

}
