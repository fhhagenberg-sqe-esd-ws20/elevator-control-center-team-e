package at.fhhagenberg.sqe.controller;

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
	StringProperty error;
	
	// properties - ui elements
	ObservableList<Integer> elevators;
		
	public ControllerData() {
		elevatorNumbers = new SimpleIntegerProperty();
		floorHeight = new SimpleIntegerProperty();
		floorNumber = new SimpleIntegerProperty();
		currentElevator = new SimpleIntegerProperty();
		buttons = FXCollections.observableHashMap();
		buttons.put(42, new FloorButtons(false, false, false, false));
		buttonList = FXCollections.observableArrayList(buttons.values());
		System.out.println(buttons.values());
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
		
		error = new SimpleStringProperty();
		
		elevators = FXCollections.observableArrayList();
		for(int i = 0; i < 5; i++) {
			elevators.add(i);
		}
	}
	
	public int getElevatorNumbers() {
		return elevatorNumbers.get();
	}
	public int getFloorHeight() {
		return floorHeight.get();
	}
	public int getFloorNumber() {
		return floorNumber.get();
	}
	public int getCurrentElevator() {
		return currentElevator.get();
	}
	public ObservableMap<Integer, FloorButtons> getButtons() {
		return buttons;
	}
	public boolean getIsManualMode() {
		return isManualMode.get();
	}
	public int getCommittedDirection() {
		return committedDirection.get();
	}
	public int getElevatorAccel() {
		return elevatorAccel.get();
	}
	public int getElevatorDoorStatus() {
		return elevatorDoorStatus.get();
	}
	public int getElevatorFloor() {
		System.out.println("Henlo" + elevatorFloor.get());
		return elevatorFloor.get();
	}
	public int getElevatorPosition() {
		return elevatorPosition.get();
	}
	public int getElevatorSpeed() {
		return elevatorSpeed.get();
	}
	public int getElevatorWeight() {
		return elevatorWeight.get();
	}
	public int getElevatorCapacity() {
		return elevatorCapacity.get();
	}
	public int getElevatorTarget() {
		return elevatorTarget.get();
	}
	public String getError() {
		return error.get();
	}

}
