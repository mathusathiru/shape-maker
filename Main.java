import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // Create main GUI frame instance and add components
        ContainerFrame cFrame = new ContainerFrame();
        cFrame.createComponents();

        // Set the size, visibility, and default close operation for the frame
        cFrame.setSize(1500, 750);
        cFrame.setVisible(true);
        cFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
