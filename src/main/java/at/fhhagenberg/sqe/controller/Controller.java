package at.fhhagenberg.sqe.controller;

import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import at.fhhagenberg.sqe.model.IBuildingWrapper;
import at.fhhagenberg.sqe.model.IElevatorWrapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Controller {
	
	private IBuildingWrapper building;
	private IElevatorWrapper elevator;
	private static int FETCH_INTERVAL = 100;
	private static int MAX_RETRIES = 4;
	
	@FXML
	public ControllerData data;
	
	public Controller(IBuildingWrapper bw, IElevatorWrapper ew) {
		building = bw;
		elevator = ew;
		
		data = new ControllerData();
		
		// also needs to be called in App after data is initialized by FXML
		// this.initStaticBuildingInfo();
		
		// for tests better to call it separate
		// this.start();
	}
	
	public void SetTarget(int target) {
		System.out.println("Set Target (" + target + ")");
		data.elevatorPosition.setValue(42);
		
		if(!data.isManualMode.get()) return;
		
		try {
			elevator.setTarget(data.currentElevator.get(), target);
		} catch (RemoteException e) {
			data.errors.add(e.getMessage());
		}
	}
	
	public void initStaticBuildingInfo() {
		try {
			data.elevatorNumbers.set(building.getElevatorNum());
			data.floorHeight.set(building.getFloorHeight());
			data.floorNumber.set(building.getFloorNum());
			data.isManualMode.set(true);
			data.currentElevator.set(0);
			
			for(int i = 0; i < data.floorNumber.get(); i++) {
				data.buttons.put(i, new FloorButtons(this, i, false, false, false, true));
			}
			
		} catch (RemoteException e) {
			data.errors.add(e.getMessage());
		}
		
		// if 0 is default:
		imgElevDown.setVisible(true);
		imgElevUp.setVisible(false);
		/* Property Listener */
		data.committedDirection.addListener((o, oldVal, newVal) -> {
			// up=0, down=1 and uncommitted=2
			if(newVal.intValue() == 1) {
				imgElevDown.setVisible(false);
				imgElevUp.setVisible(true);
			} else if(newVal.intValue() == 0) {
				imgElevDown.setVisible(true);
				imgElevUp.setVisible(false);
			}
			else {
				imgElevDown.setVisible(false);
				imgElevUp.setVisible(false);
			}
		});
		data.elevatorDoorStatus.addListener((o, oldVal, newVal) -> {
			
		});
	}
	
	public void start() {	
		UpdateService service = new UpdateService(building, elevator, data);
		service.setPeriod(Duration.millis(FETCH_INTERVAL));
		service.start();
	}
	
	public void setElevator(int elevator) {
		if(elevator >= this.getElevatorNumbers()) {
			data.errors.add("elevatorNumber not available");
			return;
		}
		data.currentElevator.set(elevator);
		clearProperties();
	}
	
	private void clearProperties() {
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
	
	private static class UpdateService extends ScheduledService<Boolean> {
		private IBuildingWrapper building;
		private IElevatorWrapper elevator;
		private ControllerData data;
		
		public UpdateService(IBuildingWrapper bw, IElevatorWrapper ew, ControllerData cd) {
			building = bw;
			elevator = ew;
			data = cd;
		}
		
		@Override
		protected Task<Boolean> createTask() {
			return new Task<Boolean>() {

				@Override
				protected Boolean call() throws Exception {
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
						data.errors.add(e.getMessage());
						return false;
					}
					return true;
				}
				
			};
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
	public String getLastError() {
		return data.errors.get(data.errors.size() - 1);
	}

	/*
	 * UI Section
	 */
	
	@FXML
	ListView<Object> lvFloors;
	@FXML
	ListView<Object> lvErrors;
	@FXML
	ComboBox<Integer> cmbElevators;
	@FXML
	RadioButton rbManual;
	@FXML
	ToggleGroup tgMode;
	@FXML
	Label lbFloor;
	@FXML
	Label lbPayload;
	@FXML
	Label lbSpeed;
	@FXML
	Label lbDoors;
	@FXML
	Label lbTarget;
	
	@FXML
	ImageView imgElevDown;
	@FXML
	ImageView imgElevUp;

	
	public void fillFields() {
		// listview lvFloors
		ObservableList<Object> floors = FXCollections.observableArrayList(data.buttons.values());
		lvFloors.setItems(floors);

		// combobox cmbElevators
		ObservableList<Integer> elevators = FXCollections.observableArrayList();
		for(int i = 0; i < getElevatorNumbers(); i++) {
			elevators.add(i);
		}
		cmbElevators.setItems(elevators);
		cmbElevators.getSelectionModel().select(0);
		cmbElevators.valueProperty().addListener((o, oldVal, newVal) -> {
			data.currentElevator.set(newVal);
		});
		
		// auto/manual (radio buttons)
		tgMode.selectedToggleProperty().addListener((o, oldVal, newVal) -> {
			data.isManualMode.set(rbManual.isSelected());
		});
		
		lbFloor.textProperty().bind(data.elevatorFloor.asString());
		lbPayload.textProperty().bind(data.elevatorWeight.asString());
		lbSpeed.textProperty().bind(data.elevatorSpeed.asString());
		lbDoors.textProperty().bind(data.elevatorDoorStatusString);
		lbTarget.textProperty().bind(data.elevatorTarget.asString());
	}
	
}
