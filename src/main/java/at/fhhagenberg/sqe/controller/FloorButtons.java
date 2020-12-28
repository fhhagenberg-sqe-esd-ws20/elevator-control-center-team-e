package at.fhhagenberg.sqe.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class FloorButtons {
	public BooleanProperty floorButtonDown;
	public BooleanProperty floorButtonUp;
	public BooleanProperty elevatorButton;
	public BooleanProperty elevatorServicesFloor;
	
	public FloorButtons(boolean down, boolean up, boolean btn, boolean service) {
		floorButtonDown = new SimpleBooleanProperty();
		floorButtonUp = new SimpleBooleanProperty();
		elevatorButton = new SimpleBooleanProperty();
		elevatorServicesFloor = new SimpleBooleanProperty();
		
		floorButtonDown.set(down);
		floorButtonUp.set(up);
		elevatorButton.set(btn);
		elevatorServicesFloor.set(service);
	}
}
