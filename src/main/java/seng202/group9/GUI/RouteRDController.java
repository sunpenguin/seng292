package seng202.group9.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group9.Controller.Dataset;
import seng202.group9.Controller.SceneCode;
import seng202.group9.Controller.Session;
import seng202.group9.Core.Route;

import java.util.ArrayList;
import java.util.Optional;


/**
 * The GUI controller class for route_raw_data.fxml.
 * Extends from the abstract class {@link Controller}.
 * Created by Sunguin
 */
public class RouteRDController extends Controller {
    //Setting up the table from the FXML file
    @FXML
    private TableView<Route> tableViewRouteRD;
    @FXML
    private TableColumn<Route, String> rAirlineCol;
    @FXML
    private TableColumn<Route, String> rAirlineIDCol;
    @FXML
    private TableColumn<Route, String> rSourceCol;
    @FXML
    private TableColumn<Route, String> rSourceIDCol;
    @FXML
    private TableColumn<Route, String> rDestCol;
    @FXML
    private TableColumn<Route, String> rDestIDCol;
    @FXML
    private TableColumn<Route, String> rCodeshareCol;
    @FXML
    private TableColumn<Route, String> rStopsCol;
    @FXML
    private TableColumn<Route, String> rEquipmentCol;

    private Dataset theDataSet = null;
    private Session currentSession = null;


    /**
     * Loads the initial route data to the GUI table.
     */
    public void load() {
        if (!checkDataset(SceneCode.ROUTE_RAW_DATA)){
            return;
        }
        //Sets up the table columns to be ready for use for Route data
        rAirlineCol.setCellValueFactory(new PropertyValueFactory<Route, String>("AirlineName"));
        rAirlineIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("AirlineID"));
        rSourceCol.setCellValueFactory(new PropertyValueFactory<Route, String>("DepartureAirport"));
        rSourceIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("SourceID"));
        rDestCol.setCellValueFactory(new PropertyValueFactory<Route, String>("ArrivalAirport"));
        rDestIDCol.setCellValueFactory(new PropertyValueFactory<Route, String>("DestID"));
        rCodeshareCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Code"));
        rStopsCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Stops"));
        rEquipmentCol.setCellValueFactory(new PropertyValueFactory<Route, String>("Equipment"));

        theDataSet = getParent().getCurrentDataset();
        currentSession = getParent().getSession();

        tableViewRouteRD.setItems(FXCollections.observableArrayList(theDataSet.getRoutes()));
        //Allows the selection of multiple entries in the table.
        tableViewRouteRD.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    /**
     * Opens the Route Add form.
     * @see RouteAddController
     */
    public void openAdd() {
        createPopUpStage(SceneCode.ROUTE_ADD, 600, 330);
        tableViewRouteRD.setItems(FXCollections.observableArrayList(theDataSet.getRoutes()));
    }


    /**
     * Opens the Route Filter form.
     * @see RouteFilterController
     */
    public void openFilter() {
        createPopUpStage(SceneCode.ROUTE_FILTER, 600, 330);

        ArrayList<Route> d = new ArrayList();
        for (int key: currentSession.getFilteredRoutes().keySet()){
            d.add(theDataSet.getRouteDictionary().get(currentSession.getFilteredRoutes().get(key)));
        }
        tableViewRouteRD.setItems(FXCollections.observableArrayList(d));
    }


    /**
     * Deletes a single selected route entry from the database.
     * Updates the GUI accordingly.
     * @see Dataset
     */
    public void deleteRoute() {
        //Gets a route from the table and deletes it before updating the table
        ObservableList<Route> toDelete = tableViewRouteRD.getSelectionModel().getSelectedItems();
        if (toDelete != null && toDelete.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Route Delete Confirmation");
            alert.setHeaderText("You are about to delete some data.");
            alert.setContentText("Are you sure you want to delete the selected route(s)?");
            Optional<ButtonType> result = alert.showAndWait();
            Route air = null;
            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (int i = 0; i < toDelete.size(); i++) {
                    air = toDelete.get(i);
                    theDataSet.deleteRoute(air);
                    for (int key : currentSession.getFilteredRoutes().keySet()) {
                        if (currentSession.getFilteredRoutes().get(key) == air.getUniqueKey()) {
                            currentSession.getFilteredRoutes().remove(key);
                            break;
                        }
                    }
                }
                tableViewRouteRD.setItems(FXCollections.observableArrayList(theDataSet.getRoutes()));
            }
        }
    }


    /**
     * Opens the Route Edit form.
     * @see RouteEditController
     */
    public void editRoute() {
        Route toEdit = tableViewRouteRD.getSelectionModel().getSelectedItem();
        if (toEdit != null) {
            currentSession.setRouteToEdit(toEdit.getAirlineName() + toEdit.getDepartureAirport() + toEdit.getArrivalAirport() +
                    toEdit.getCode() + toEdit.getStops() + toEdit.getEquipment());
            createPopUpStage(SceneCode.ROUTE_EDIT, 600, 330);
            tableViewRouteRD.refresh();
        }
    }


    /**
     * Analyses the current data and creates a graph based on the data.
     * @see RouteGraphController
     */
    public void analyse_Button() {
        replaceSceneContent(SceneCode.ROUTE_GRAPHS);
    }


    /**
     * Goes to the route summary page.
     * @see RouteSummaryController
     */
    public void routeSummaryButton() {
        replaceSceneContent(SceneCode.ROUTE_SUMMARY);
        currentSession = getParent().getSession();
    }


    /**
     * Opens a map with the data currently being displayed in the table.
     */
    public void openMap(){
        //check if there is internet connectivity
        if (!getParent().testInet("maps.google.com")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Internet Connection.");
            alert.setHeaderText("Unable to Connect to Google Maps");
            alert.setContentText("As we are unable to connect to Google Maps all applications which are supposed to display maps may not work as intended.");
            alert.showAndWait();
        }else {
            createPopUpStage(SceneCode.POP_UP_ROUTE_MAP, 600, 400);
        }
    }
}
