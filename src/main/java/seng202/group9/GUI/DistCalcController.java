package seng202.group9.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airport;
import seng202.group9.Controller.App;
import seng202.group9.Controller.Dataset;
import seng202.group9.Core.Airline;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.Group;

/**
 * Created by michael on 17/09/2016.
 */
public class DistCalcController extends Controller {
    @FXML
    ListView<String> airportOne;
    @FXML
    ListView<String> airportTwo;
    @FXML
    Label answerBox;

    Dataset currentData = null;
    HashMap<String, Airport> current_Airports;
    SimpleStringProperty bound = new SimpleStringProperty("Answer");

    private void fill_boxes(){
        HashMap<String, Airport> current_Airports = currentData.getAirportDictionary();
        ArrayList<String> names = new ArrayList<String>();
        for(String name : current_Airports.keySet()){
            names.add(name);
        }
        ObservableList<String> usablenames = FXCollections.observableArrayList(names);
        airportOne.setItems(usablenames);
        airportTwo.setItems(usablenames);
    }

    public void calculateButton(){
        Airport airport1 = currentData.getAirports().get((airportOne.getSelectionModel().getSelectedIndices().get(0)));
        Airport airport2 = currentData.getAirports().get((airportTwo.getSelectionModel().getSelectedIndices().get(0)));
        double distance = airport1.calculateDistance(airport2);
        bound.setValue(String.valueOf(Math.round(distance)) + "km");
        System.out.println(bound);
    }

    public void load(){
        currentData = getParent().getCurrentDataset();
        answerBox.textProperty().bind(bound);
        fill_boxes();
    }

}
