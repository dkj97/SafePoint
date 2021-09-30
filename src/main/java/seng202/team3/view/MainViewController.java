package seng202.team3.view;


import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team3.model.CrimeData;
import seng202.team3.model.DataManager;
import seng202.team3.controller.FilterController;
import seng202.team3.controller.UIDataInterface;

/**
 * Main FXML controller for main-view.fxml
 */
public class MainViewController implements Initializable {

    //FXML components for filtering
    @FXML private TextField regionFilter;
    @FXML private ChoiceBox crimeSelector;
    @FXML private CheckBox policeDataToggle;
    @FXML private CheckBox userDataToggle;
    @FXML private CheckBox arrestMadeToggle;
    @FXML private CheckBox graphToggle;
    @FXML private CheckBox regionFilteringToggle;
    @FXML private ChoiceBox regionFilteringKey;
    @FXML private CheckBox dateSortToggle;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private CheckBox compareCrimesToggle;

    @FXML private ScrollPane crimeDataPanel;

    @FXML
    private WebView mapView;

    private WebEngine webEngine;

    /**
     * Handles update crime button on main GUI. This calls functions to update filters and get new data for the
     * crime data panel
     * @param e input variable
     * @throws ParseException if user input is invalid
     * @throws ClassNotFoundException if CrimeData is invalid
     */
    @FXML
    public void updateCrimeData(ActionEvent e) throws ParseException, ClassNotFoundException {
        updateRegionCrimeData();
        updateMapSettingsData();
        updateRegionDateData();
        DataPaneConstructor.reloadData();
        crimeDataPanel.setContent(DataPaneConstructor.cullPanes(0.0));
        crimeDataPanel.setVvalue(0.0);
        webEngine.executeScript("removeMarkers()");
        loadData1();
        //System.out.println(crimeDataPanel.getVvalue());
    }

    /**
     * Handles when a user clicks the report crime button on main GUI
     * @param e input variable
     */
    @FXML
    public void reportCrime (ActionEvent e) {
        new ReportCrimeWindow();
    }

    /**
     * Handles when the compare crimes option is clicked
     * @param e input variable
     */
    @FXML
    public void compareCrimeToggle (ActionEvent e) {
        UIDataInterface.setCompareCrimes(compareCrimesToggle.isSelected());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UIDataInterface.initCrimeData();
        initMap();
        initCrimeSelector();
        initRegionFilterSelector();

        //https://stackoverflow.com/questions/31069300/how-to-fire-event-when-scrolling-up-javafx
        crimeDataPanel.vvalueProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    //culls data pane to only show visible ones when the scroll bar is moved.
                    crimeDataPanel.setContent(DataPaneConstructor.cullPanes (crimeDataPanel.getVvalue()));
                });
    }


    /**
     * Loads the CrimeData to the map. This uses the DataManager call to get the activeCrimeData
     */
    public void loadData() {
        ArrayList<CrimeData> tempActiveCrimeData = new ArrayList<>();
        tempActiveCrimeData = DataManager.getData();
        String json = new Gson().toJson(tempActiveCrimeData);
        webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                webEngine.executeScript("getAllActiveCrimeData(" + json + ")");

            }
        });

    }

    public void loadData1() {

        ArrayList<CrimeData> tempActiveCrimeData = new ArrayList<CrimeData>();
        tempActiveCrimeData = DataManager.getData();
        String json = new Gson().toJson(tempActiveCrimeData);
        webEngine.executeScript("getAllActiveCrimeData(" + json + ")");
    }

    private void initMap() {
        webEngine = mapView.getEngine();
        webEngine.load(Objects.requireNonNull(getClass().getClassLoader().getResource("seng202.team3.view/googleMap.html")).toExternalForm());
        loadData();
    }

    public void initCrimeSelector () {
        ReportCrimeController.constructCrimeChoiceBox(crimeSelector);
    }

    public void initRegionFilterSelector () {
        String[] filterTypes = {"HIGH FREQUENCY", "LOW FREQUENCY", "HIGH RISK AREAS", "LOW RISK AREAS"};
        for (String filterType : filterTypes) {
            regionFilteringKey.getItems().add(filterType);
        }
    }

    public void updateRegionCrimeData () {
        if (!Objects.equals(regionFilter.getText(), "")) {
            FilterController.setActiveLocation(regionFilter.getText());
        }
        FilterController.setActiveCrimeType((String) crimeSelector.getValue());
    }

    public void updateMapSettingsData () {
        FilterController.setPoliceDataActive(policeDataToggle.isSelected());
        FilterController.setUserDataActive(userDataToggle.isSelected());
        FilterController.setArrestMade(arrestMadeToggle.isSelected());
    }

    public void updateRegionDateData () throws ParseException {
        FilterController.setRegionDataActive(regionFilteringToggle.isSelected());
        FilterController.setRegionFilteringKey((String) regionFilteringKey.getValue());
        FilterController.setDateFiltering(dateSortToggle.isSelected());
        if (startDate.getValue() != null) {
            String startDateStr = startDate.getValue().toString();
            FilterController.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr));
        }
        if (endDate.getValue() != null) {
            String endDateStr = endDate.getValue().toString();
            FilterController.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr));
        }
    }

    public WebEngine getWebEngine() {return webEngine;}

}

