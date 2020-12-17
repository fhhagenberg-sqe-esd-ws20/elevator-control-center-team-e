package at.fhhagenberg.sqe.model;

import java.rmi.RemoteException;

public class ElevatorWrapper implements IElevatorWrapper {

	private IElevator model;
	
	public ElevatorWrapper(IElevator model) {
		this.model = model;
	}
	
	@Override
	public int getCommittedDirection(int elevatorNumber) throws RemoteException {
		return model.getCommittedDirection(elevatorNumber);
	}

	@Override
	public int getElevatorAccel(int elevatorNumber) throws RemoteException {
		return model.getElevatorAccel(elevatorNumber);
	}

	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor) throws RemoteException {
		return model.getElevatorButton(elevatorNumber, floor);
	}

	@Override
	public int getElevatorDoorStatus(int elevatorNumber) throws RemoteException {
		return model.getElevatorDoorStatus(elevatorNumber);
	}

	@Override
	public int getElevatorFloor(int elevatorNumber) throws RemoteException {
		return model.getElevatorFloor(elevatorNumber);
	}

	@Override
	public int getElevatorPosition(int elevatorNumber) throws RemoteException {
		return model.getElevatorPosition(elevatorNumber);
	}

	@Override
	public int getElevatorSpeed(int elevatorNumber) throws RemoteException {
		return model.getElevatorSpeed(elevatorNumber);
	}

	@Override
	public int getElevatorWeight(int elevatorNumber) throws RemoteException {
		return model.getElevatorWeight(elevatorNumber);
	}

	@Override
	public int getElevatorCapacity(int elevatorNumber) throws RemoteException {
		return model.getElevatorCapacity(elevatorNumber);
	}

	@Override
	public boolean getServicesFloors(int elevatorNumber, int floor) throws RemoteException {
		return model.getServicesFloors(elevatorNumber, floor);
	}

	@Override
	public int getTarget(int elevatorNumber) throws RemoteException {
		return model.getTarget(elevatorNumber);
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, int direction) throws RemoteException {
		model.setCommittedDirection(elevatorNumber, direction);
	}

	@Override
	public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws RemoteException {
		model.setServicesFloors(elevatorNumber, floor, service);
	}

	@Override
	public void setTarget(int elevatorNumber, int target) throws RemoteException {
		model.setTarget(elevatorNumber, target);
	}

}
