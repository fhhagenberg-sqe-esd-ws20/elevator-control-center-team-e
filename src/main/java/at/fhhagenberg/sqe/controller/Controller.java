package at.fhhagenberg.sqe.controller;

import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import at.fhhagenberg.sqe.model.IBuildingWrapper;
import at.fhhagenberg.sqe.model.IElevatorWrapper;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;

public class Controller {
	
	private IBuildingWrapper building;
	private IElevatorWrapper elevator;
	private static int FETCH_INTERVAL = 100;
	private static int MAX_RETRIES = 4;
	
	@FXML
	private ControllerData data = new ControllerData();
	
	public Controller(IBuildingWrapper bw, IElevatorWrapper ew) {
		building = bw;
		elevator = ew;
		
		this.initStaticBuildingInfo();
		
		// for tests better to call it separate
		// this.start();
	}
	
	public void SetTarget(int target) {
		if(!data.isManualMode.get()) return;
		
		try {
			elevator.setTarget(data.currentElevator.get(), target);
		} catch (RemoteException e) {
			data.error.set(e.getMessage());
		}
	}
	
	private void initStaticBuildingInfo() {
		try {
			data.elevatorNumbers.set(building.getElevatorNum());
			data.floorHeight.set(building.getFloorHeight());
			data.floorNumber.set(building.getFloorNum());
			data.isManualMode.set(true);
			data.currentElevator.set(0);
			
			for(int i = 0; i < data.floorNumber.get(); i++) {
				data.buttons.put(i, new FloorButtons(false, false, false, true));
			}
			
		} catch (RemoteException e) {
			data.error.set(e.getMessage());
		}
	}
	
	public void start() {
		ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
		es.scheduleAtFixedRate(this::scheduleFetch, FETCH_INTERVAL, FETCH_INTERVAL, TimeUnit.MILLISECONDS);
	}
	
	public void setElevator(int elevator) {
		if(elevator >= this.getElevatorNumbers()) {
			data.error.set("elevatorNumber not available");
			return;
		}
		data.currentElevator.set(elevator);
		clearProberties();
	}
	
	private void clearProberties() {
		for(int i = 0; i < data.floorNumber.get(); i++) {
			var tmp = data.buttons.get(i);
			tmp.elevatorButton.set(false);
			tmp.floorButtonDown.set(false);
			tmp.floorButtonUp.set(false);
			tmp.elevatorServicesFloor.set(true);
		}
		
		data.committedDirection.set(0);
		data.elevatorAccel.set(0);
		data.elevatorDoorStatus.set(0);
		data.elevatorFloor.set(0);
		data.elevatorPosition.set(0);
		data.elevatorSpeed.set(0);
		data.elevatorWeight.set(0);
		data.elevatorCapacity.set(0);
		data.elevatorTarget.set(0);
	}
	
	private synchronized void scheduleFetch() {
		try {
			long tick;
			int cnt = 0;
			do {
				tick = elevator.getClockTick();
				
				for(int i = 0; i < data.floorNumber.get(); i++) {
					var tmp = data.buttons.get(i);
					tmp.elevatorButton.set(elevator.getElevatorButton(data.currentElevator.get(), i));
					tmp.floorButtonDown.set(building.getFloorButtonDown(i));
					tmp.floorButtonUp.set(building.getFloorButtonUp(i));
					tmp.elevatorServicesFloor.set(elevator.getServicesFloors(data.currentElevator.get(), i));
				}
				
				data.committedDirection.set(elevator.getCommittedDirection(data.currentElevator.get()));
				data.elevatorAccel.set(elevator.getElevatorAccel(data.currentElevator.get()));
				data.elevatorDoorStatus.set(elevator.getElevatorDoorStatus(data.currentElevator.get()));
				data.elevatorFloor.set(elevator.getElevatorFloor(data.currentElevator.get()));
				data.elevatorPosition.set(elevator.getElevatorPosition(data.currentElevator.get()));
				data.elevatorSpeed.set(elevator.getElevatorSpeed(data.currentElevator.get()));
				data.elevatorWeight.set(elevator.getElevatorWeight(data.currentElevator.get()));
				data.elevatorCapacity.set(elevator.getElevatorCapacity(data.currentElevator.get()));
				data.elevatorTarget.set(elevator.getTarget(data.currentElevator.get()));
				
				if(cnt++ == MAX_RETRIES) {
					throw new RemoteException("Reached maximum retries while updating elevator.");
				}
			} while (tick != elevator.getClockTick());
			
		} catch (RemoteException e) {
			data.error.set(e.getMessage());
		}
	}
	
	public int getElevatorNumbers() {
		return data.elevatorNumbers.get();
	}
	public int getFloorHeight() {
		return data.floorHeight.get();
	}
	public int getFloorNumber() {
		return data.floorNumber.get();
	}
	public int getCurrentElevator() {
		return data.currentElevator.get();
	}
	public ObservableMap<Integer, FloorButtons> getButtons() {
		return data.buttons;
	}
	public boolean getIsManualMode() {
		return data.isManualMode.get();
	}
	public int getCommittedDirection() {
		return data.committedDirection.get();
	}
	public int getElevatorAccel() {
		return data.elevatorAccel.get();
	}
	public int getElevatorDoorStatus() {
		return data.elevatorDoorStatus.get();
	}
	public int getElevatorFloor() {
		return data.elevatorFloor.get();
	}
	public int getElevatorPosition() {
		return data.elevatorPosition.get();
	}
	public int getElevatorSpeed() {
		return data.elevatorSpeed.get();
	}
	public int getElevatorWeight() {
		return data.elevatorWeight.get();
	}
	public int getElevatorCapacity() {
		return data.elevatorCapacity.get();
	}
	public int getElevatorTarget() {
		return data.elevatorTarget.get();
	}
	public String getError() {
		return data.error.get();
	}
	
	
}
