package seng202.team3.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Child class for CrimeData class which takes the existing data and formats it
 * @author Danish Jahangir
 */

public class PoliceData extends CrimeData {
    private String caseNumber;
    private String arrestMade;
    private String domestic;
    private String beat;
    private String ward;
    private int xCoord;
    private int yCoord;

    /**
     * Constructor method for the class PoliceData
     * @param id is the String that identifies a particular Crime
     * @param data is an ArrayList containing strings of all Crimes
     * @throws ParseException checks if the format is correct when string is parsed.
     */
    public PoliceData(String id, ArrayList<String> data) throws ParseException {
        super(id);
        formatPoliceData(data);
    }

    public String getCaseNumber() {
        return String.format("%8s", caseNumber).replace(' ', '0');
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String isArrestMade() {
        return arrestMade;
    }

    public void setArrestMade(String arrestMade) {
        if (Objects.equals(arrestMade, "Y")) {
            this.arrestMade = "YES";
        } else {
            this.arrestMade = "NO";
        }
    }

    public int getXCoord() {
        return xCoord;
    }

    public void setXCoord(String xCoord) {
        try {
            this.xCoord = Integer.parseInt(xCoord);
        } catch (Exception e){
            this.xCoord = 0;
        }
    }

    public int getYCoord() {
        return yCoord;
    }

    public void setYCoord(String yCoord) {
        try {
            this.yCoord = Integer.parseInt(yCoord);
        } catch (Exception e){
            this.yCoord = 0;
        }
    }

    public String getDomestic() {return domestic;}

    public void setDomestic(String domestic) {
        if (Objects.equals(domestic, "Y")) {
            this.domestic = "YES";
        } else {
            this.domestic = "NO";
        }
    }

    public String getWard() {return ward;}

    public void setWard(String ward) {this.ward = ward;}

    public String getBeat() {return beat;}

    public void setBeat(String beat) {this.beat = beat;}


    /**
     * Function that splits the given string to get the required fields
     * @param data is the ArrayList of String which is to be formatted
     * @throws ParseException checks if the format is correct when string is parsed.
     */
    public void formatPoliceData(ArrayList<String> data) throws ParseException {
        setCaseNumber(data.get(0));
        setDate(data.get(1));
        setAddress(data.get(2));
        setCrimeType(data.get(4));
        setArrestMade(data.get(7));
        setDomestic(data.get(8));
        setBeat(data.get(9));
        setWard(data.get(10));
        setXCoord(data.get(12));
        setYCoord(data.get(13));
        setLatitude(data.get(14));
        setLongitude(data.get(15));
        setLocation(data.get(14) + ", " + data.get(15));
    }
}
