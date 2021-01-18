package at.fhhagenberg.sqe;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import at.fhhagenberg.sqe.controller.Controller;
import at.fhhagenberg.sqe.model.BuildingWrapper;
import at.fhhagenberg.sqe.model.ElevatorWrapper;
import at.fhhagenberg.sqe.model.IBuildingWrapper;
import at.fhhagenberg.sqe.model.IElevatorWrapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sqelevator.IElevator;

/**
 * JavaFX App
 */
public class App extends Application {

	private IBuildingWrapper used_buildingwrapper;
	private IElevatorWrapper used_elevatorwrapper;
	private IElevator used_elevator;
	private final String url = "rmi://localhost/ElevatorSim";

	
	public App() {
		used_elevator = null;
		while(used_elevator == null) {
			try {
				used_elevator = (IElevator) Naming.lookup(this.url);
			} catch (Exception e) {
				  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		          alert.setTitle("No Simulation Connection");
		          alert.setHeaderText("Click ok when simulation started");
		          alert.setContentText("Cancle to stop Application");
		          alert.showAndWait().ifPresent(rs -> {
		        	    if (rs != ButtonType.OK) {
							Platform.exit();
							System.exit(0);
		        	    }
		        	});
			} 
		}
		
		used_buildingwrapper = new BuildingWrapper(used_elevator);
		used_elevatorwrapper = new ElevatorWrapper(used_elevator);
		used_buildingwrapper.setConnectionString(this.url);
		used_elevatorwrapper.setConnectionString(this.url);
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
	    	URL url_fxml = new File("src/main/resources/fxml/eccView.fxml").toURI().toURL();
	    	loader = new FXMLLoader(url_fxml);
	    	
	    	loader.setController(controller);
	    	
	    	root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

        var scene = new Scene(root, 640, 480);
        
        stage.setScene(scene);
        stage.setTitle("ECC Team E");
        stage.show();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        
        controller.initStaticBuildingInfo();
        controller.addUIListeners();
        controller.start();
        controller.fillFields();
        
    }

    public static void main(String[] args) {
        launch();
    }

}