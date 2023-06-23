package Museum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Simulation input class.
 */
public class SimulationInput {
    /**
     * Simulation input array. 
     */
    public static int[] inputArray = new int[7];
    /**
     * Thieves number config offset.
     */
    public static int thievesNumber = 0;
    /**
     * Maximum displacement config offset.
     */
    public static int maximumDisplacement = 1;
    /**
     * Painting rooms config offset.
     */
    public static int paintingRooms = 2;
    /**
     * Maximum room distance config offset.
     */
    public static int maxRoomDistance = 3;
    /**
     * Maximum room paintings config offset.
     */
    public static int maxRoomPaintings = 4;
    /**
     * Maximum members number config offset.
     */
    public static int partyMembersNumber = 5;
    /**
     * Party separation limit config offset.
     */
    public static int partySeparationLimit = 6;
    
    /**
     * Get simulation input.
     */
    public static void getSimulationInput() {
        File config = new File("config.ini");

        if (config.exists()) {
            System.out.println("Found config file");
            parseConfigFile(config);
        }
        else {
            System.out.println("Config file not found");
            generateConfigFile();
            parseConfigFile(config);
        }
    }

    /**
     * Generate simulation input config file.
     */
    public static void generateConfigFile() {
        try {
            System.out.println("Generating default config file...");
            FileWriter file = new FileWriter("config.ini");

            file.write("; HeistToTheMuseum config file\n\n; Note - you may change some configuration values\n");

            file.write("7		; number of thieves\n");
            file.write("6		; thief's maximum displacement\n");

            file.write("5		; rooms with paintings\n");
            file.write("30		; maximum room distance\n");
            file.write("16		; maximum paintings per room\n");

            file.write("3		; assualt parties members\n");
            file.write("3		; assault parties separation limit\n");

            file.close();
            System.out.println("Config File created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse config file simulation input.
     * 
     * @param file config file pointer
     */
    public static void parseConfigFile(File file) {
        try {
            System.out.println("Parsing config file...");
            Scanner fileReader = new Scanner(file);

            int inputIndex = 0;
            String stringBuffer = "";

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                if (line.length() == 0 || line.charAt(0) == ';')
                    continue;
                
                for (int i = 0; i < line.length() && Character.isDigit(line.charAt(i)); i++) {
                    stringBuffer += line.charAt(i);
                }

                inputArray[inputIndex++] = Integer.parseInt(stringBuffer);

                // clear string buffer
                stringBuffer = "";
            }

            System.out.println("Config parsing completed");
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
