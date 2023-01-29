import javax.swing.JPanel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;

/** Database Class imports and exports
 *  MatrixData from a text document.
 *  Additionally, renders the database
 *  graphically.
 *
 * @author RMizelle
 */
public class Database extends JPanel {
    private final ArrayList<Location> locations;
    private File data;

    /** 0-arg constructor implements ArrayList of Countries
     *  of Cells objects from a text document.
     */
    public Database() {
        locations = new ArrayList<>();

        //creates resource folder if necessary
        File directory = new File("resources");
        if (!directory.exists()) {
            System.out.println("New Resources Directory Generated");
            directory.mkdir();
        }
        //checks for data.txt
        try {
            data = new File("resources/data.txt");
            //if data.txt does not exist, instantiates empty Database
            if (data.createNewFile()) {
                System.out.println("New Data File Generated");
            }
            //else data.txt exists, calls import method
            else {
                System.out.println("Accessing Data...");
                if (importData()) {
                    System.out.println("Data Retrieved Successfully");
                }
                else {
                    System.out.println("ERROR: Failed to Retrieve Data");
                }
            }
        }
        catch (IOException e) {
            //Error when generating Database
            System.out.println("ERROR: Could not generate Database");
            e.printStackTrace();
        }
    }

    /** importData method instantiates database using data.txt
     *  @return true if successfully instantiated database, false if error
     */
    private boolean importData() {
        try {
            //creates scanner
            Scanner input = new Scanner(data);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                //splits up data fields
                String[] parts = line.split("#");
                locations.add(new Location(parts[0], parts[1]));
            }
            //closes scanner
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Couldn't Read File");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int databaseSize() {
        return locations.size();
    }

    /** Returns a random location within database
     *
     * @return a random location
     */
    public Location getRandomLocation() {
        return locations.get((int) (Math.random() * locations.size()));
    }
}

/** Location object stores a country
 *  and it's capital
 */
class Location {
    private String country;
    private String capital;

    /** 2-arg constructor istantiates country and capital
     *
     * @param country name
     * @param capital name
     */
    public Location(String country, String capital) {
        this.country = country;
        this.capital = capital;
    }

    // Getter Methods //
    public String getCountry() {
        return country;
    }

    public String getCapital() {
        return capital;
    }

    @Override
    public String toString() {
        return "Country: " + country + "\nCapital: " + capital;
    }
}
 class Tester {
    public static void main(String[] args) {
        Database database = new Database();
        for(int i = 0; i < 4; i++) {
            Location random = database.getRandomLocation();
            System.out.println(random);
        }
    }
}
