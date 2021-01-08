package at.fhhagenberg.sqe.model;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;

import sqelevator.IElevator;

public interface IBuildingWrapper {
	
	/**
	 * Sets the used elevator model
	 * @param model - used elevator model
	 */
	public void setModel(IElevator model);
	
	public void setConnectionString(String url);
	
	public void reconnectToRMI() throws java.rmi.RemoteException, MalformedURLException, NotBoundException;

	/**
	 * Retrieves the number of elevators in the building. 
	 * @return total number of elevators
	 */
	public int getElevatorNum() throws java.rmi.RemoteException; 
	
	/**
	 * Provides the status of the Down button on specified floor (on/off). 
	 * @param floor - floor number whose Down button status is being retrieved 
	 * @return returns boolean to indicate if button is active (true) or not (false)
	 */
	public boolean getFloorButtonDown(int floor) throws java.rmi.RemoteException; 

	/**
	 * Provides the status of the Up button on specified floor (on/off). 
	 * @param floor - floor number whose Up button status is being retrieved 
	 * @return returns boolean to indicate if button is active (true) or not (false)
	 */
	public boolean getFloorButtonUp(int floor) throws java.rmi.RemoteException; 
	
	/**
	 * Retrieves the height of the floors in the building. 
	 * @return floor height (ft)
	 */
	public int getFloorHeight() throws java.rmi.RemoteException; 
	
	/**
	 * Retrieves the number of floors in the building. 
	 * @return total number of floors
	 */
	public int getFloorNum() throws java.rmi.RemoteException;	
}
