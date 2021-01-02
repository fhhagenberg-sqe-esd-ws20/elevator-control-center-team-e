package at.fhhagenberg.sqe;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import at.fhhagenberg.sqe.controller.Controller;
import at.fhhagenberg.sqe.controller.ControllerData;
import at.fhhagenberg.sqe.model.BuildingWrapper;
import at.fhhagenberg.sqe.model.DummyElevator;
import at.fhhagenberg.sqe.model.ElevatorWrapper;
import at.fhhagenberg.sqe.model.IBuildingWrapper;
import at.fhhagenberg.sqe.model.IElevatorWrapper;
import at.fhhagenberg.sqe.model.IElevator;
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

	private IBuildingWrapper used_buildingwrapper;
	private IElevatorWrapper used_elevatorwrapper;
	
	public App() {
		IElevator used_elevator = new DummyElevator();   // TODO use Simulator
		used_buildingwrapper = new BuildingWrapper(used_elevator);
		used_elevatorwrapper = new ElevatorWrapper(used_elevator);
	}
	
	public App(IBuildingWrapper bw, IElevatorWrapper ew) {
		used_buildingwrapper = bw;
		used_elevatorwrapper = ew;
	}
	
    @Override
    public void start(Stage stage) {
    	Parent root = null;
    	FXMLLoader loader;
    	IBuildingWrapper building = used_buildingwrapper;
    	IElevatorWrapper elevator = used_elevatorwrapper;
    	Controller controller = new Controller(building, elevator);
    	
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