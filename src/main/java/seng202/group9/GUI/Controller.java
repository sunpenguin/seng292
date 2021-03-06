package seng202.group9.GUI;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group9.Controller.App;
import seng202.group9.Controller.SceneCode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gondr on 16/09/2016.
 */
public abstract class Controller implements Initializable{
    private App parent;

    /**
     * set Parent app
     * @param parent
     */
    public void setApp(App parent){
        this.parent = parent;
    }

    /**
     * get parent App
     * @return
     */
    public App getParent(){
        return parent;
    }

    /**
     * functions to load
     */
    public abstract void load();

    /**
     * Replaces content and does normal loading
     * @param scene
     */
    public void replaceSceneContent(SceneCode scene){
        try {
            Controller controller = (Controller)
                    parent.replaceSceneContent(scene);
            controller.setApp(parent);
            controller.load();
            controller.loadOnce();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkDataset(){
        return checkDataset(SceneCode.INITIAL);
    }

    public boolean checkDataset(SceneCode scene){
        //if the dataset is null then we want to change to the initial and give a warning.
        //Also then let them selecthe data set
        if (getParent().getCurrentDataset() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Dataset");
            alert.setHeaderText("No Dataset is currently selected.");
            alert.setContentText("Please Create a Dataset to store your Information in.");
            alert.showAndWait();
            createPopUpStage(SceneCode.DATASET_CONTROLLER, 600, 400);
            if (getParent().getCurrentDataset() == null){
                replaceSceneContent(SceneCode.INITIAL);
            }else {
                replaceSceneContent(scene);
            }
            return false;
        }else{
            return true;
        }
    }

    /**
     * Creates a popup window with a specific fxml scene
     * @param scene
     * @param width
     * @param height
     */
    public Stage createPopUpStage(SceneCode scene, int width, int height) {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = getClass().getClassLoader().getResourceAsStream(scene.getFilePath());
        Parent page = null;
        try {
            page = loader.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //set contorller and call default calls
        Controller controller = (Controller) loader.getController();
        controller.setApp(parent);
        controller.load();
        controller.loadOnce();
        //create a new stage to popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        //inner layout constraints
        VBox container = new VBox();
        container.getChildren().add(page);
        Scene popupScene = new Scene(container, width, height);
        //show
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
        return popupStage;
    }

    /**
     * Functions here will only load once and after the load function.
     */
    public void loadOnce(){

    }

    /**
     * Initialize statement before anything loads.
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {

    }

}