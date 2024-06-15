import java.awt.*;
import java.util.Arrays;

/**
 *  RegPolygon class represents a regular polygon and implements the Comparable interface.
 */
public class RegPolygon implements Comparable<RegPolygon> {

    /* Fields to store polygon properties */

    // Polygon colour
    private Color pColor;

    // Unique identifier for polygon
    private int pId;

    // Number of polygon sides
    private int pSides;

    // Starting angle of the first side in radians
    private double pStartingAngle;

    // Polygon radius
    private double pRadius;

    // X-coordinate of polygon centre
    private int polyCenX;

    // Y-coordinate of polygon centre
    private int polyCenY;

    // Array to store X-coordinates of polygon vertices
    private double[] pointsX;

    // Array to store Y-coordinates of polygon vertices
    private double[] pointsY;

    /* Constructor for creating RegPolygon object. */
    public RegPolygon(int sides, double startingAngle, double radius, int id, Color color) {
        this.pSides = sides;
        this.pStartingAngle = startingAngle;
        this.pRadius = radius;
        this.pColor = color;
        this.pId = id;
        pointsX = new double[pSides];
        pointsY = new double[pSides];
    }

    /* Calculates and returns a Polygon object representing polygon vertices */
    private Polygon getPolygonPoints(Dimension dim) {
        // Calculates X-coordinate of polygon center
        polyCenX = dim.width / 2;

        // Calculates Y-coordinate of polygon center
        polyCenY = dim.height / 2;

        // Determine angle increment between each polygon vertex
        double angleIncrement = 2 * Math.PI / pSides;

        // Create new Polygon object to store vertices
        Polygon p = new Polygon();

        // Iterate through each polygon side to calculate and add vertex coordinates
        for (int i = 0; i < pSides; i++) {
            // Calculate X-coordinate of current vertex with cosine
            pointsX[i] = polyCenX + pRadius * Math.cos(pStartingAngle);

            // Calculate the Y-coordinate of current vertex with sine
            pointsY[i] = polyCenY + pRadius * Math.sin(pStartingAngle);

            // Add vertex to the Polygon
            p.addPoint((int) pointsX[i], (int) pointsY[i]);

            // Update the starting angle for the next vertex
            pStartingAngle = pStartingAngle + angleIncrement;
        }

        // Returns Polygon object containing all vertices
        return p;
    }


    /* Draws the polygon on a Graphics2D object within the specified dimension. */
    public void drawPolygon(Graphics2D g, Dimension d) {
        // Sets drawing color to polygon outline colour
        g.setColor(pColor);
        // Draws polygon on the Graphics2D object using the calculated vertices
        g.drawPolygon(getPolygonPoints(d));
    }

    /* Gets polygon ID */
    public int getID() {
        return pId;
    }

    /* Formats polygon ID into a fixed-length string with leading zeros, ensuring a minimum length of 6 digits */
    public String getFormattedId(int id) {
        return String.format("%06d", id);
    }

    /* Compares two polygons based on their IDs; returns -1 if this polygon's ID is less, 1 if greater, 0 if equal */
    @Override
    public int compareTo(RegPolygon o) {
        if (this.getID() > o.getID()) {
            return 1;
        } else if (this.getID() < o.getID()) {
            return -1;
        } else {
            return 0;
        }
    }

    /* Returns a string representation of polygon */
    @Override
    public String toString() {
        return "Polygon ID: " + getFormattedId(pId) +
                "\nPolygon Sides: " + pSides +
                "\nPolygon Radius: " + pRadius +
                "\nStarting Angle: " + pStartingAngle +
                "\nPoints X: " + Arrays.toString(pointsX) +
                "\nPoints Y: " + Arrays.toString(pointsY) +
                "\nPolygon Color: " + pColor.toString();
    }
}
