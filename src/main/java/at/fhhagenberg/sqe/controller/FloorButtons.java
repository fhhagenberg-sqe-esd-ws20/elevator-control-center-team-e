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
	BooleanProperty isCurrentFloor;
	
	boolean setTarget = false;
	
	public FloorButtons(Controller root, int nr, boolean down, boolean up, boolean btn, boolean service) {
		rootController = root;
		
		floorNr = new SimpleIntegerProperty();
		floorButtonDown = new SimpleBooleanProperty();
		floorButtonUp = new SimpleBooleanProperty();
		elevatorButton = new SimpleBooleanProperty();
		elevatorServicesFloor = new SimpleBooleanProperty();
		isCurrentFloor = new SimpleBooleanProperty();
		
		floorNr.set(nr);
		floorButtonDown.set(down);
		floorButtonUp.set(up);
		elevatorButton.set(btn);
		elevatorServicesFloor.set(service);
		
		floorButtonDown.addListener((o, oldVal, newVal) -> {
			if(root.lvFloors != null) root.lvFloors.refresh(); // force proper update of floors
		});
		floorButtonUp.addListener((o, oldVal, newVal) -> {
			if(root.lvFloors != null) root.lvFloors.refresh(); // force proper update of floors
		});
		elevatorButton.addListener((o, oldVal, newVal) -> {
			if(root.lvFloors != null) root.lvFloors.refresh(); // force proper update of floors
		});
		elevatorServicesFloor.addListener((o, oldVal, newVal) -> {
			if(root.lvFloors != null) root.lvFloors.refresh(); // force proper update of floors
		});
		isCurrentFloor.addListener((o, oldVal, newVal) -> {
			if(root.lvFloors != null) root.lvFloors.refresh(); // force proper update of floors
		});
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
	public boolean getIsCurrentFloor() {
		return isCurrentFloor.get();
	}

	@FXML
	void doSetTarget(ActionEvent event) {
		event.consume();
		setTarget = true;
		//rootController.SetTarget(getFloorNr());
	}
}
