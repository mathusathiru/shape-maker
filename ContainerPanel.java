import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;

/**
 * The ContainerPanel class extends JPanel and serves as a container for drawing polygons.
 */
public class ContainerPanel extends JPanel {

    // Reference to the ContainerFrame to access selected polygons
    ContainerFrame conFrame;

    // Initialise the ContainerFrame for use with this panel
    public ContainerPanel(ContainerFrame cf) {
        conFrame = cf;
    }

    // Overrides paintComponent to paint panel contents
    @Override
    public void paintComponent(Graphics g) {
        // Methods and object uses for accurate painting
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Obtain panel size
        Dimension size = getSize();

        // Clear panel
        g2d.clearRect(0, 0, size.width, size.height);

        // Polygons selected in the ContainerFrame drawn on the panel
        if (conFrame.selectedPolygon != null) {
            conFrame.selectedPolygon.drawPolygon(g2d, size);
        }
    }

}
