package seng202.team3.controller;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Writes data found in an input file ot an output file
 * @author roryh and Danish
 */
public class WriteCSV extends Importer {

    /**
     * Reads data from a csv input file and writes it to an output file
     * @param outputPath Location of output file
     * @param inputCrimes Input Crimes to be added
     */
    public static void writeDataLineByLine(String outputPath, ArrayList<String[]> inputCrimes)
    {
        try {

            File file = new File(outputPath);
            FileWriter outputFile = new FileWriter(file, true);
            CSVWriter writer = new CSVWriter(outputFile);

            // Adding header to csv
            if (file.length() == 0 ) {
                String[] header = { "Case#", "DATE OF OCCURRENCE", "BLOCK", "PRIMARY DESCRIPTION",
                        "ARREST", "DOMESTIC", "BEAT", "WARD","X COORDINATE",
                        "Y COORDINATE", "LATITUDE", "LONGITUDE", "OBJECT TYPE"};
                writer.writeNext(header);
            }

            // Writing crimes
            assert inputCrimes != null;
            for (String[] crime : inputCrimes) {
                writer.writeNext(crime);
            }

            // Closing writer connection
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
