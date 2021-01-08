package at.fhhagenberg.sqe;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import at.fhhagenberg.sqe.controller.Controller;
import at.fhhagenberg.sqe.controller.ControllerData;
import at.fhhagenberg.sqe.model.BuildingWrapper;
import at.fhhagenberg.sqe.model.DummyElevator;
import at.fhhagenberg.sqe.model.ElevatorWrapper;
import at.fhhagenberg.sqe.model.IBuildingWrapper;
import at.fhhagenberg.sqe.model.IElevatorWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sqelevator.IElevator;

/**
 * JavaFX App
 */
public class App extends Application {

	private IBuildingWrapper used_buildingwrapper;
	private IElevatorWrapper used_elevatorwrapper;
	private final String url = "rmi://localhost/ElevatorSim";
	
	public App() {
		//IElevator controller = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
		IElevator used_elevator = new DummyElevator();   // TODO use Simulator
		try {
			used_elevator = (IElevator) Naming.lookup(this.url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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