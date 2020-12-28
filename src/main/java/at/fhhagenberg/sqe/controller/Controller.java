package at.fhhagenberg.sqe.controller;

import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import at.fhhagenberg.sqe.model.IBuildingWrapper;
import at.fhhagenberg.sqe.model.IElevatorWrapper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableMap;



public class Controller {
	public class FloorButtons{
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
	
	private IBuildingWrapper building;
	private IElevatorWrapper elevator;
	private static int FETCH_INTERVAL = 100;
	private static int MAX_RETRIES = 5;
	
	// properties - building
	private IntegerProperty elevatorNumbers;
	private IntegerProperty floorHeight;
	private IntegerProperty floorNumber;
	private IntegerProperty currentElevator;
	private ObservableMap<Integer, FloorButtons> buttons;
	private BooleanProperty isManualMode;	// if false, elevators are in automatic mode
	
	// properties - elevator
	private IntegerProperty committedDirection;	// up=0, down=1 and uncommitted=2
	private IntegerProperty elevatorAccel;
	private IntegerProperty elevatorDoorStatus; // 1=open and 2=closed
	private IntegerProperty elevatorFloor;
	private IntegerProperty elevatorPosition;
	private IntegerProperty elevatorSpeed;
	private IntegerProperty elevatorWeight;
	private IntegerProperty elevatorCapacity;
	private IntegerProperty elevatorTarget;
	
	// error properties
	private StringProperty error;
	
	public Controller(IBuildingWrapper bw, IElevatorWrapper ew) {
		building = bw;
		elevator = ew;
		
		elevatorNumbers = new SimpleIntegerProperty();
		floorHeight = new SimpleIntegerProperty();
		floorNumber = new SimpleIntegerProperty();
		currentElevator = new SimpleIntegerProperty();
		buttons = new SimpleMapProperty<Integer, FloorButtons>();
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
		
		this.initStaticBuildingInfo();
		this.start();
	}
	
	public void SetTarget(int target) {
		if(!isManualMode.get()) return;
		
		try {
			elevator.setTarget(currentElevator.get(), target);
		} catch (RemoteException e) {
			error.set(e.getMessage());
		}
	}
	
	private void initStaticBuildingInfo() {
		try {
			elevatorNumbers.set(building.getElevatorNum());
			floorHeight.set(building.getFloorHeight());
			floorNumber.set(building.getFloorNum());
			isManualMode.set(true);
			currentElevator.set(0);
			
			for(int i = 0; i < floorNumber.get(); i++) {
				buttons.put(i, new FloorButtons(false, false, false, true));
			}
			
		} catch (RemoteException e) {
			error.set(e.getMessage());
		}
	}
	
	private void start() {
		ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
		es.scheduleAtFixedRate(this::scheduleFetch, FETCH_INTERVAL, FETCH_INTERVAL, TimeUnit.MILLISECONDS);
	}
	
	private synchronized void scheduleFetch() {
		try {
			long tick;
			int cnt = 0;
			do {
				tick = elevator.getClockTick();
				
				for(int i = 0; i < floorNumber.get(); i++) {
					var tmp = buttons.get(i);
					tmp.elevatorButton.set(elevator.getElevatorButton(currentElevator.get(), i));
					tmp.floorButtonDown.set(building.getFloorButtonDown(i));
					tmp.floorButtonUp.set(building.getFloorButtonUp(i));
					tmp.elevatorServicesFloor.set(elevator.getServicesFloors(currentElevator.get(), i));
				}
				
				committedDirection.set(elevator.getCommittedDirection(currentElevator.get()));
				elevatorAccel.set(elevator.getElevatorAccel(currentElevator.get()));
				elevatorDoorStatus.set(elevator.getElevatorDoorStatus(currentElevator.get()));
				elevatorFloor.set(elevator.getElevatorFloor(currentElevator.get()));
				elevatorPosition.set(elevator.getElevatorPosition(currentElevator.get()));
				elevatorSpeed.set(elevator.getElevatorSpeed(currentElevator.get()));
				elevatorWeight.set(elevator.getElevatorWeight(currentElevator.get()));
				elevatorCapacity.set(elevator.getElevatorCapacity(currentElevator.get()));
				elevatorTarget.set(elevator.getTarget(currentElevator.get()));
				
				cnt++;
				if(cnt == MAX_RETRIES) {
					throw new RemoteException("Reached maximum retries while updating elevator.");
				}
			} while (tick != elevator.getClockTick());
			
		} catch (RemoteException e) {
			error.set(e.getMessage());
		}
	}
}
