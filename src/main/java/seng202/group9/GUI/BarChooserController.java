package seng202.group9.GUI;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.Session;

import java.util.ArrayList;

/**
 * Created by michael on 24/09/2016.
 */
public class BarChooserController extends Controller{

    @FXML
    ChoiceBox datatypechooser;
    @FXML
    ListView graph_against;
    @FXML
    ListView graph_options;
    @FXML
    CheckBox usefilter;

    ObservableList airportOptions = FXCollections.observableArrayList("Name", "ICAO", "IATA FFA", "Altitude",
            "City", "Country");

    ObservableList airlineOptions = FXCollections.observableArrayList("ID", "Name", "ICAO", "IATA", "Alias",
            "Call Sign", "Active", "Country");

    ObservableList routeOptions = FXCollections.observableArrayList("ID", "Stops", "Codeshare", "Equipment", "Airline",
            "Departure Airport", "Arival airport");

    ArrayList<ObservableList> allOptions = new ArrayList<ObservableList>();

    public void buildGraph() {
        Session currentsession = this.getParent().getSession();
        currentsession.setSelectedgraphoptions(graph_against.getSelectionModel().getSelectedItem().toString());
        currentsession.setSelectedgraphagainst(graph_options.getSelectionModel().getSelectedItem().toString());
        currentsession.setUsefilter(usefilter.isSelected());
        currentsession.setForceGraph(Boolean.FALSE);
        replaceSceneContent(SceneCode.ROUTE_ANALYSER);
    }

    public void returnToSelection(){replaceSceneContent(SceneCode.PIE_GRAPH_CHOOSER);}

    public void changeTables(){
        int temp = datatypechooser.getSelectionModel().getSelectedIndex();
        graph_against.setItems(allOptions.get(temp));
        graph_options.setItems(allOptions.get(temp));
    }

    public void load(){
        datatypechooser.setItems(FXCollections.observableArrayList("Airports","Airlines","Routes"));
        datatypechooser.getSelectionModel().selectFirst();
        datatypechooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                changeTables();
            }
        });
        graph_against.setItems(airportOptions);
        graph_against.getSelectionModel().selectFirst();
        graph_options.setItems(airportOptions);
        graph_options.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        graph_options.getSelectionModel().selectFirst();
        allOptions.add(airportOptions);
        allOptions.add(airlineOptions);
        allOptions.add(routeOptions);
    }
}