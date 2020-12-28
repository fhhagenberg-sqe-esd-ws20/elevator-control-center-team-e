package at.fhhagenberg.sqe;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
    	try {
	    	URL url = new File("src/main/resources/fxml/eccView.fxml").toURI().toURL();
	    	root = FXMLLoader.load(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

        var scene = new Scene(root, 640, 480);

        stage.setScene(scene);
        stage.setTitle("ECC Team E");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}