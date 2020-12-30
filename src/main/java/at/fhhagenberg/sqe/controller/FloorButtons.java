package at.fhhagenberg.sqe.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class FloorButtons {
	private Controller rootController;
	
	IntegerProperty floorNr;
	BooleanProperty floorButtonDown;
	BooleanProperty floorButtonUp;
	BooleanProperty elevatorButton;
	BooleanProperty elevatorServicesFloor;
	
	StringProperty downPic;
	
	public FloorButtons(Controller root, int nr, boolean down, boolean up, boolean btn, boolean service) {
		rootController = root;
		
		floorNr = new SimpleIntegerProperty();
		floorButtonDown = new SimpleBooleanProperty();
		floorButtonUp = new SimpleBooleanProperty();
		elevatorButton = new SimpleBooleanProperty();
		elevatorServicesFloor = new SimpleBooleanProperty();
		
		downPic = new SimpleStringProperty();
		
		floorNr.set(nr);
		floorButtonDown.set(down);
		floorButtonUp.set(up);
		elevatorButton.set(btn);
		elevatorServicesFloor.set(service);
		downPic.set("@../icons/down_empty.png");
	}
	
	public int getFloorNr() {
		return floorNr.get();
	}
	public boolean getFloorButtonDown() {
		return floorButtonDown.get();
	}
	public boolean getFloorButtonUp() {
		return floorButtonUp.get();
	}
	public boolean getElevatorButton() {
		return elevatorButton.get();
	}
	public boolean getElevatorServicesFloor() {
		return elevatorServicesFloor.get();
	}
	public String getDownPic() {
		return downPic.get();
	}

	@FXML
	void doSetTarget(ActionEvent event) {
		event.consume();
		// no worki
		rootController.data.errors.add("target");
		rootController.SetTarget(getFloorNr());
	}
}
