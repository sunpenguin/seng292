package seng202.group9.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;
import seng202.group9.Controller.App;
import seng202.group9.Controller.SceneCode;

public class MenuController implements Initializable{

	App parent;

	public void importAirports(){
		JOptionPane.showMessageDialog(null, "This is not Implemented yet");
	}
	
	public void importAirlines(){
		JOptionPane.showMessageDialog(null, "This is not Implemented yet");
	}

	public void importRoutes(){
		JOptionPane.showMessageDialog(null, "This is not Implemented yet");
	}
	
	public void importFlightData(){
		JOptionPane.showMessageDialog(null, "This is not Implemented yet");
	}

	/**
	 * Load Airline Raw Data Function.
	 */
	public void viewAirlineRawData() {
		try {
            AirlineRDController summaryController = (AirlineRDController) parent.replaceSceneContent(SceneCode.AIRLINE_RAW_DATA);
            summaryController.setApp(parent);
            summaryController.loadTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewAirportRawData() {
		try {
            AirportRDController summaryController = (AirportRDController) parent.replaceSceneContent(SceneCode.AIRPORT_RAW_DATA);
            summaryController.setApp(parent);
            summaryController.loadTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewRouteRawData() {
		try {
			RouteRDController summaryController = (RouteRDController) parent.replaceSceneContent(SceneCode.ROUTE_RAW_DATA);
			summaryController.setApp(parent);
			summaryController.loadTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewAirlineSummary() {
		try {
			AirlineSummaryController summaryController = (AirlineSummaryController) parent.replaceSceneContent(SceneCode.AIRLINE_SUMMARY);
			summaryController.setApp(parent);
			summaryController.loadTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewAirportSummary() {
		try {
			AirportSummaryController summaryController = (AirportSummaryController) parent.replaceSceneContent(SceneCode.AIRPORT_SUMMARY);
			summaryController.setApp(parent);
			summaryController.loadTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewRouteSummary() {
		try {
			RouteSummaryController summaryController = (RouteSummaryController) parent.replaceSceneContent(SceneCode.ROUTE_SUMMARY);
			summaryController.setApp(parent);
			summaryController.loadTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setApp(App parent){
		this.parent = parent;
	}

	/**
	 * Load Flight Summary Function.
	 */
	public void viewFlightSummary() {
		try {
			FlightSummaryController summaryController = (FlightSummaryController) parent.replaceSceneContent(SceneCode.FLIGHT_SUMMARY);
			summaryController.setApp(parent);
			summaryController.flightPathListView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load Flight Raw Data Function.
	 */
	public void viewFlightRawData() {
		try {
			FlightRawDataController rawDataController = (FlightRawDataController)
					parent.replaceSceneContent(SceneCode.FLIGHT_RAW_DATA);
			rawDataController.setApp(parent);
			rawDataController.loadTables();
			rawDataController.flightPathListView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
