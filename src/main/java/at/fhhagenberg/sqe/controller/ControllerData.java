package at.fhhagenberg.sqe.controller;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ControllerData {
	
	// properties - building
	IntegerProperty elevatorNumbers;
	IntegerProperty floorHeight;
	IntegerProperty floorNumber;
	IntegerProperty currentElevator;
	ObservableMap<Integer, FloorButtons> buttons;
	ObservableList<FloorButtons> buttonList;
	BooleanProperty isManualMode;	// if false, elevators are in automatic mode
	
	// properties - elevator
	IntegerProperty committedDirection;	// up=0, down=1 and uncommitted=2
	IntegerProperty elevatorAccel;
	IntegerProperty elevatorDoorStatus; // 1=open and 2=closed
	IntegerProperty elevatorFloor;
	IntegerProperty elevatorPosition;
	IntegerProperty elevatorSpeed;
	IntegerProperty elevatorWeight;
	IntegerProperty elevatorCapacity;
	IntegerProperty elevatorTarget;
	
	// error properties
	ObservableList<String> errors;
	
	// properties - ui elements
	ObservableList<Integer> elevators;
	StringProperty elevatorDoorStatusString;
		
	public ControllerData() {
		elevatorNumbers = new SimpleIntegerProperty();
		floorHeight = new SimpleIntegerProperty();
		floorNumber = new SimpleIntegerProperty();
		currentElevator = new SimpleIntegerProperty();
		buttons = FXCollections.observableHashMap();
		buttonList = FXCollections.observableArrayList(buttons.values());
		isManualMode = new SimpleBooleanProperty();
		
		committedDirection = new SimpleIntegerProperty();
		elevatorAccel = new SimpleIntegerProperty();
		elevatorDoorStatus = new SimpleIntegerProperty();
		elevatorFloor = new SimpleIntegerProperty();
		elevatorPosition = new SimpleIntegerProperty();
		elevatorSpeed = new SimpleIntegerProperty();
		elevatorWeight = new SimpleIntegerProperty();
		elevatorCapacity = new SimpleIntegerProperty();
		elevatorTarget = new SimpleIntegerProperty();
		
		errors = FXCollections.observableArrayList();
		
		elevators = FXCollections.observableArrayList();
		for(int i = 0; i < elevatorNumbers.get(); i++) {
			elevators.add(i);
		}
		
		elevatorDoorStatusString = new SimpleStringProperty("unknown");
		elevatorDoorStatus.addListener((o, oldVal, newVal) -> {
			// 1=open and 2=closed
			Platform.runLater(() -> {
				if(newVal.intValue() == 1) {
					elevatorDoorStatusString.set("open");
				} else if (newVal.intValue() == 2) {
					elevatorDoorStatusString.set("closed");
				} else {
					elevatorDoorStatusString.set("unknown");
				}
			});
			
		});
	}
	
	/**
	 * Get the number of available elevators
	 * @return The number of available elevators
	 */
	public int getElevatorNumbers() {
		return elevatorNumbers.get();
	}
	
	/**
	 * Get the height of the floor
	 * @return The height of the floor
	 */
	public int getFloorHeight() {
		return floorHeight.get();
	}
	
	/**
	 * Get the number of floors
	 * @return The number of floors
	 */
	public int getFloorNumber() {
		return floorNumber.get();
	}
	
	/**
	 * Get the current active elevator
	 * @return The current active elevator
	 */
	public int getCurrentElevator() {
		return currentElevator.get();
	}
	
	/**
	 * Get all available floor-buttons
	 * @return The available floor-buttons
	 */
	public ObservableMap<Integer, FloorButtons> getButtons() {
		return buttons;
	}
	
	/**
	 * Query the status of the manual mode
	 * @return The status of the manual mode
	 */
	public boolean getIsManualMode() {
		return isManualMode.get();
	}
	
	/**
	 * Get the current committed direction of the elevator
	 * @return The committed direction
	 */
	public int getCommittedDirection() {
		return committedDirection.get();
	}
	
	/**
	 * Get the current acceleration of the elevator
	 * @return The acceleration of the elevator
	 */
	public int getElevatorAccel() {
		return elevatorAccel.get();
	}
	
	/**
	 * Get the current door status of the current elevator
	 * @return The door status of the current elevator
	 */
	public int getElevatorDoorStatus() {
		return elevatorDoorStatus.get();
	}
	
	/**
	 * Get the current floor of the current elevator
	 * @return The current floor of the current elevator
	 */
	public int getElevatorFloor() {
		return elevatorFloor.get();
	}
	
	/**
	 * Get the current position of the current elevator
	 * @return The position of the elevator
	 */
	public int getElevatorPosition() {
		return elevatorPosition.get();
	}
	
	/**
	 * Get the current speed of the current elevator
	 * @return The speed of the elevator
	 */
	public int getElevatorSpeed() {
		return elevatorSpeed.get();
	}
	
	/**
	 * Get the current weight of the current elevator
	 * @return The weight of the elevator
	 */
	public int getElevatorWeight() {
		return elevatorWeight.get();
	}
	
	/**
	 * Get the capacitiy of the current elevator
	 * @return The capacitiy of the elevator
	 */
	public int getElevatorCapacity() {
		return elevatorCapacity.get();
	}
	
	/**
	 * Get the current target of the current elevator
	 * @return The target of the elevator
	 */
	public int getElevatorTarget() {
		return elevatorTarget.get();
	}

}
