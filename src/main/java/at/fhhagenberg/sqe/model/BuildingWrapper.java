package at.fhhagenberg.sqe.model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import sqelevator.IElevator;

public class BuildingWrapper implements IBuildingWrapper {

	private IElevator model;
	private String url;
	
	public BuildingWrapper(IElevator model) {
		this.model = model;
		this.url = "";
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
	public void setModel(IElevator model) {
		this.model = model;
	}
	
	@Override
	public IElevator getModel() {
		return model;
	}

	@Override
	public void setConnectionString(String url) {
		this.url = url;
	}
	
	@Override
	public String getConnectionString() {
		return url;
	}

	@Override
	public void reconnectToRMI() throws RemoteException, MalformedURLException, NotBoundException {
		this.model = (IElevator) Naming.lookup(this.url);
	}
}
