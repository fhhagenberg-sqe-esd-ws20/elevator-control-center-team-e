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

	private IBuildingWrapper usedBuildingwrapper;
	private IElevatorWrapper usedElevatorwrapper;
	private IElevator usedElevator;
	private static final String url = "rmi://localhost/ElevatorSim";
	private Controller controller;

	
	public App() {
		usedElevator = null;
		while(usedElevator == null) {
			try {
				usedElevator = (IElevator) Naming.lookup(url);
			} catch (Exception e) {
				  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		          alert.setTitle("No simulator connection");
		          alert.setHeaderText("Click ok when simulator is running");
		          alert.setContentText("Cancel to stop application");
		          alert.showAndWait().ifPresent(rs -> {
		        	    if (rs != ButtonType.OK) {
							Platform.exit();
							System.exit(0);
		        	    }
		        	});
			} 
		}
		
		usedBuildingwrapper = new BuildingWrapper(usedElevator);
		usedElevatorwrapper = new ElevatorWrapper(usedElevator);
		usedBuildingwrapper.setConnectionString(url);
		usedElevatorwrapper.setConnectionString(url);
	}
	
	public App(IBuildingWrapper bw, IElevatorWrapper ew) {
		usedBuildingwrapper = bw;
		usedElevatorwrapper = ew;
	}
	
	public Controller getController() {
		return controller;
	}
	
    @Override
    public void start(Stage stage) {
    	Parent root = null;
    	FXMLLoader loader;
    	IBuildingWrapper building = usedBuildingwrapper;
    	IElevatorWrapper elevator = usedElevatorwrapper;
    	controller = new Controller(building, elevator);
    	
    	try {
	    	URL urlFxml = new File("src/main/resources/fxml/eccView.fxml").toURI().toURL();
	    	loader = new FXMLLoader(urlFxml);
	    	
	    	loader.setController(controller);
	    	
	    	root = loader.load();
		} catch (IOException e) {
			java.lang.System.out.println(e.getMessage()); 
		}

        var scene = new Scene(root, 640, 480);
        
        stage.setScene(scene);
        stage.setTitle("ECC Team E");
        stage.show();
        
        var EVENTHANDLER = new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }};
            
        stage.setOnCloseRequest(EVENTHANDLER);
        
        controller.initStaticBuildingInfo();
        controller.addUIListeners();
        controller.start();
        controller.fillFields();
        
    }

    public static void main(String[] args) {
        launch();
    }

}