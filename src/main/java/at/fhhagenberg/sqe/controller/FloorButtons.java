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
	
	/**
	 * Represents a floor-button
	 * @param root - the root-controller of the floor-button
	 * @param nr - which floornumber the floor-button represents
 	 * @param down - status of the down-button
	 * @param up - status of the up-button
	 * @param btn - status of the stop-button
	 * @param service - if the floor is servicable
	 */
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
	
	/**
	 * Get the floor number
	 * @return The floor number
	 */
	public int getFloorNr() {
		return floorNr.get();
	}
	
	/**
	 * Get the status of the Floor-ButtonDown
	 * @return The ButtonDown status
	 */
	public boolean getFloorButtonDown() {
		return floorButtonDown.get();
	}
	
	/**
	 * Get the status of the Floor-ButtonUp
	 * @return The ButtonUp status
	 */
	public boolean getFloorButtonUp() {
		return floorButtonUp.get();
	}
	
	/**
	 * Get the elevator button
	 * @return The elevator button
	 */
	public boolean getElevatorButton() {
		return elevatorButton.get();
	}
	
	/**
	 * Get the status of the elevator ServicesFloor
	 * @return The status of the ServicesFloor
	 */
	public boolean getElevatorServicesFloor() {
		return elevatorServicesFloor.get();
	}
	
	/**
	 * Get the status of the current floor
	 * @return The status of the current floor
	 */
	public boolean getIsCurrentFloor() {
		return isCurrentFloor.get();
	}

	/**
	 * Set the target of the elevator
	 * @param event - The event to consume
	 */
	@FXML
	void doSetTarget(ActionEvent event) {
		event.consume();
		setTarget = true;
		//rootController.SetTarget(getFloorNr());
	}
}
