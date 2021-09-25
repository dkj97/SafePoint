package seng202.team3.view;


import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team3.controller.FilterController;
import seng202.team3.controller.UIDataInterface;

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

    @FXML private ScrollPane crimeDataPanel;

    @FXML
    private WebView mapView;

    private WebEngine webEngine;

    @FXML
    public void  updateCrimeData(ActionEvent e) throws ParseException, ClassNotFoundException {
        updateRegionCrimeData();
        updateMapSettingsData();
        updateRegionDateData();
        crimeDataPanel.setContent(DataPaneConstructor.loadActiveCrimes());
    }

    @FXML
    public void reportCrime (ActionEvent e) {
        UserInputHandler uih = new UserInputHandler();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMap();
        initCrimeSelector();
        initRegionFilterSelector();
        UIDataInterface.initCrimeData();
    }

    private void initMap() {
        webEngine = mapView.getEngine();
        webEngine.load(Objects.requireNonNull(getClass().getClassLoader().getResource("seng202.team3.view/googleMap.html")).toExternalForm());
    }

    public void initCrimeSelector () {
        String[] crimeTypes = {"ALL", "ARSON", "ASSAULT", "BATTERY", "BURGLARY", "CONCEALED CARRY LICENCE", "CRIMINAL DAMAGE", "CRIMINAL SEXUAL ASSAULT", "CRIMINAL TRESPASS", "DECEPTIVE PRACTICE", "HOMICIDE", "INTERFERENCE WITH PUBLIC OFFICER", "INTIMIDATION", "KIDNAPPING", "LIQUOR LAW VIOLATION", "MOTOR VEHICLE THEFT", "NARCOTICS", "OFFENSE INVOLVING CHILDREN", "OTHER NARCOTIC VIOLATION", "OTHER OFFENSE", "PROSTITUTION", "PUBLIC PEACE VIOLATION", "ROBBERY", "SEX OFFENSE", "STALKING", "THEFT", "WEAPONS VIOLATION"};
        for (String crimeType : crimeTypes) {
            crimeSelector.getItems().add(crimeType);
        }
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

