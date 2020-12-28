package at.fhhagenberg.sqe.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class ControllerData {
	
	// properties - building
	IntegerProperty elevatorNumbers;
	IntegerProperty floorHeight;
	IntegerProperty floorNumber;
	IntegerProperty currentElevator;
	ObservableMap<Integer, FloorButtons> buttons;
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
		
	public ControllerData() {
		elevatorNumbers = new SimpleIntegerProperty();
		floorHeight = new SimpleIntegerProperty();
		floorNumber = new SimpleIntegerProperty();
		currentElevator = new SimpleIntegerProperty();
		buttons = FXCollections.observableHashMap();
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
	}
}
