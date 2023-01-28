import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** AppDriver class creates the containing
 *  window for the simulation.
 *
 * @author RMizelle
 */
public class AppDriver {
    //window dimensions
    public static int WIDTH = 900;
    public static int HEIGHT = 900;

    private static JFrame frame;

    public static MainPanel panel;

    public static void main(String[] args) {
        panel = new MainPanel();
        frame = new JFrame("Automata");
        //frame.setIconImage(new ImageIcon("TEMP").getImage()); //Image Icon
        // frame attributes
        frame.setContentPane(panel);
        frame.setSize(WIDTH + 17, HEIGHT + 40);
        frame.setLocation(50, 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        //Exports Database on Close
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //TO-DO
                frame.dispose();
                System.exit(0);
            }
        });

    }
}
