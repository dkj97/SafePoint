package seng202.team3.model;

public class PoliceData extends CrimeData {
    private String caseNumber;
    private char arrestMade;
    private int xCord;
    private int yCord;

    /**
     * Constructor method for the class PoliceData
     * @param data is the String that is received
     */

    public PoliceData(String data) {
        super(data);
    }

    public String getCaseNumber() {
        return String.format("%8s", caseNumber).replace(' ', '0');
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public char isArrestMade() {
        return arrestMade;
    }

    public void setArrestMade(char arrestMade) {
        this.arrestMade = arrestMade;
    }

    public int getxCord() {
        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }
}