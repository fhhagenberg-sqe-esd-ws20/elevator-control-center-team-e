package at.fhhagenberg.sqe.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class FloorButtons {
	public IntegerProperty floorNr;
	public BooleanProperty floorButtonDown;
	public BooleanProperty floorButtonUp;
	public BooleanProperty elevatorButton;
	public BooleanProperty elevatorServicesFloor;
	
	public FloorButtons(int nr, boolean down, boolean up, boolean btn, boolean service) {
		floorNr = new SimpleIntegerProperty();
		floorButtonDown = new SimpleBooleanProperty();
		floorButtonUp = new SimpleBooleanProperty();
		elevatorButton = new SimpleBooleanProperty();
		elevatorServicesFloor = new SimpleBooleanProperty();
		
		floorNr.set(nr);
		floorButtonDown.set(down);
		floorButtonUp.set(up);
		elevatorButton.set(btn);
		elevatorServicesFloor.set(service);
	}
	
	public int getFloorNr() {
		return floorNr.get();
	}
	
	@FXML
	void doSetTarget(ActionEvent event) {
		event.consume();
		System.out.println("Hello, World! (from " + getFloorNr() + ")");
	}
}
