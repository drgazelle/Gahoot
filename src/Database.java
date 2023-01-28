import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import kong.unirest.*;

import javax.swing.JPanel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.Collection;
import java.util.Scanner;

/** Database Class imports and exports
 *  MatrixData from a text document.
 *  Additionally, renders the database
 *  graphically.
 *
 * @author RMizelle
 */
public class Database extends JPanel {
    private final ArrayList<String[]> database;
    private File data;

    /** 0-arg constructor implements ArrayList of Countries
     *  of Cells objects from a text document.
     */
    public Database() {
        database = new ArrayList<>();

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
                String[] parts = line.split("##");
                //removes brackets from data fields
                for(int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].substring(1, parts[i].length() - 1).trim();
                }
                //gets name
                String name = parts[0];

                //get size
                String[] temp = parts[1].split("x");
                int[] size = new int[2];
                size[0] = Integer.parseInt(temp[0].trim());
                size[1] = Integer.parseInt(temp[1].trim());

                //get live cells
                ArrayList<int[]> cells = new ArrayList<>();
                for(int i = 2; i < parts.length; i++) {
                    //new splits String into parts and converts to ints
                    int[] cell = new int[2];
                    temp = parts[i].split(",");
                    cell[0] = Integer.parseInt(temp[0].trim());
                    cell[1] = Integer.parseInt(temp[1].trim());
                    cells.add(cell);
                }
                //adds new MatrixData to database
                //database.add(new MatrixData(name, size, cells));
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

    /** exportData method takes existing
     *  Database and implements data.txt
     *  @return true if successful, false if error
     */
    public boolean exportDatabase() {
        //TO-DO
        return true;
    }

    public int databaseSize() {
        return database.size();
    }

    /** Accesses wikicollections api for patterns based of search term
     *
     * <p> https://rapidapi.com/timjacksonm-1jw8F2hFW3d/api/the-game-of-life </p>
     *
     * @param s search term
     */
    public void addFromSearch(String s) {

        int count = 5;

        HttpResponse<String> response = Unirest.get("https://the-game-of-life.p.rapidapi.com/wikicollection/search/title?value=" + s + "&select=%5B%22author%22%2C%22title%22%2C%22description%22%2C%22size%22%2C%22rleString%22%2C%22date%22%5D&count=" + count)
                .header("X-RapidAPI-Key", "4ce993ab37mshadac634a5fbad3ep1a4c4fjsn041896a40067")
                .header("X-RapidAPI-Host", "the-game-of-life.p.rapidapi.com")
                .asString();

        //error checks connection
        if (response.getStatus() != 200) {
            System.out.println("ERROR: Failed to Access WikiCollections API [Code" + response.getStatus() + "]");
            Unirest.shutDown();
            return;
        }
        System.out.println("Successful Connection [Code " + response.getStatus() + "]");
        Unirest.shutDown();
        Gson gson = new Gson();
        JsonArray elements = JsonParser.parseString(response.getBody()).getAsJsonArray();

        for (JsonElement e : elements) {
            //results.add(gson.fromJson(e.getAsJsonObject(), MatrixData.class)); //ARCHIVED UNTIL FUNCTIONAL
        }
    }
}

/** Location object stores a country
 *  and it's capital
 */
class Location {
    private String country;
    private String capital;

    public Location() {}

    public String getCountry() {
        return country;
    }

    public String getCapital() {
        return capital;
    }
}