package at.fhhagenberg.sqe.model;

import java.rmi.RemoteException;

public class BuildingWrapper implements IBuildingWrapper {

	private IElevator model;
	
	public BuildingWrapper(IElevator model) {
		this.model = model;
	}
	
	@Override
	public int getElevatorNum() throws RemoteException {
		return model.getElevatorNum();
	}

	@Override
	public boolean getFloorButtonDown(int floor) throws RemoteException {
		return model.getFloorButtonDown(floor);
	}

	@Override
	public boolean getFloorButtonUp(int floor) throws RemoteException {
		return model.getFloorButtonUp(floor);
	}

	@Override
	public int getFloorHeight() throws RemoteException {
		return model.getFloorHeight();
	}

	@Override
	public int getFloorNum() throws RemoteException {
		return model.getFloorNum();
	}

	@Override
	public long getClockTick() throws RemoteException {
		return model.getClockTick();
	}

}
