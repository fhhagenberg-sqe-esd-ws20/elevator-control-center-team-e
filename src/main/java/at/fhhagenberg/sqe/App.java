package at.fhhagenberg.sqe;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import at.fhhagenberg.sqe.controller.Controller;
import at.fhhagenberg.sqe.controller.ControllerData;
import at.fhhagenberg.sqe.model.BuildingWrapper;
import at.fhhagenberg.sqe.model.DummyElevator;
import at.fhhagenberg.sqe.model.ElevatorWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
    	Parent root = null;
    	FXMLLoader loader;
    	Controller controller = new Controller(new BuildingWrapper(new DummyElevator()), new ElevatorWrapper(new DummyElevator()));
    	
    	try {
	    	URL url = new File("src/main/resources/fxml/eccView.fxml").toURI().toURL();
	    	loader = new FXMLLoader(url);
	    	
	    	loader.setController(controller);
	    	
	    	root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

        var scene = new Scene(root, 640, 480);
        
        stage.setScene(scene);
        stage.setTitle("ECC Team E");
        stage.show();
        
        controller.initStaticBuildingInfo();
        controller.addUIListeners();
        controller.start();
        controller.fillFields();
        
    }

    public static void main(String[] args) {
        launch();
    }

}