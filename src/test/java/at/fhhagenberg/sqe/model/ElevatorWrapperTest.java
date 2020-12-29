package at.fhhagenberg.sqe.model;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ElevatorWrapperTest {
	@Mock
	private IElevator elevatorMock;
	
	private ElevatorWrapper elevatorWrapper;
	
	@BeforeEach
	void setUp() throws Exception {
		elevatorWrapper = new ElevatorWrapper(elevatorMock);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetCommittedDirection() throws RemoteException {
		Mockito.when(elevatorMock.getCommittedDirection(0)).thenReturn(1);		
		assertEquals(1, elevatorWrapper.getCommittedDirection(0));
		
		Mockito.verify(elevatorMock).getCommittedDirection(0);
	}

	@Test
	void testGetElevatorAccel() throws RemoteException {
		Mockito.when(elevatorMock.getElevatorAccel(0)).thenReturn(5);		
		assertEquals(5, elevatorWrapper.getElevatorAccel(0));
		
		Mockito.verify(elevatorMock).getElevatorAccel(0);
	}
	
	@Test
	void testGetElevatorButton() throws RemoteException {
		Mockito.when(elevatorMock.getElevatorButton(0, 10)).thenReturn(true);		
		assertEquals(true, elevatorWrapper.getElevatorButton(0, 10));
		
		Mockito.verify(elevatorMock).getElevatorButton(0, 10);
	}
	
	@Test
	void testGetElevatorDoorStatus() throws RemoteException {
		Mockito.when(elevatorMock.getElevatorDoorStatus(0)).thenReturn(2);		
		assertEquals(2, elevatorWrapper.getElevatorDoorStatus(0));
		
		Mockito.verify(elevatorMock).getElevatorDoorStatus(0);
	}
	
	@Test
	void testGetElevatorFloor() throws RemoteException {
		Mockito.when(elevatorMock.getElevatorFloor(0)).thenReturn(6);		
		assertEquals(6, elevatorWrapper.getElevatorFloor(0));
		
		Mockito.verify(elevatorMock).getElevatorFloor(0);
	}
	
	@Test
	void testGetElevatorPosition() throws RemoteException {
		Mockito.when(elevatorMock.getElevatorPosition(0)).thenReturn(50);		
		assertEquals(50, elevatorWrapper.getElevatorPosition(0));
		
		Mockito.verify(elevatorMock).getElevatorPosition(0);
	}
	
	@Test
	void testGetElevatorSpeed() throws RemoteException {
		Mockito.when(elevatorMock.getElevatorSpeed(0)).thenReturn(-5);		
		assertEquals(-5, elevatorWrapper.getElevatorSpeed(0));
		
		Mockito.verify(elevatorMock).getElevatorSpeed(0);
	}
	
	@Test
	void testGetElevatorWeight() throws RemoteException {
		Mockito.when(elevatorMock.getElevatorWeight(0)).thenReturn(1100);		
		assertEquals(1100, elevatorWrapper.getElevatorWeight(0));
		
		Mockito.verify(elevatorMock).getElevatorWeight(0);
	}
	
	@Test
	void testGetElevatorCapacity() throws RemoteException {
		Mockito.when(elevatorMock.getElevatorCapacity(0)).thenReturn(6);		
		assertEquals(6, elevatorWrapper.getElevatorCapacity(0));
		
		Mockito.verify(elevatorMock).getElevatorCapacity(0);
	}
	
	@Test
	void testGetServicesFloors() throws RemoteException {
		Mockito.when(elevatorMock.getServicesFloors(0, 5)).thenReturn(true);		
		assertEquals(true, elevatorWrapper.getServicesFloors(0, 5));
		
		Mockito.verify(elevatorMock).getServicesFloors(0, 5);
	}
	
	@Test
	void testGetTarget() throws RemoteException {
		Mockito.when(elevatorMock.getTarget(0)).thenReturn(10);		
		assertEquals(10, elevatorWrapper.getTarget(0));
		
		Mockito.verify(elevatorMock).getTarget(0);
	}
	
	@Test
	void testSetCommittedDirection() throws RemoteException {
		elevatorWrapper.setCommittedDirection(0, 1);		
		Mockito.verify(elevatorMock).setCommittedDirection(0, 1);
	}
	
	@Test
	void testSetServicesFloors() throws RemoteException {
		elevatorWrapper.setServicesFloors(0, 1, true);		
		Mockito.verify(elevatorMock).setServicesFloors(0, 1, true);
	}
	
	@Test
	void testSetTarget() throws RemoteException {
		elevatorWrapper.setTarget(0, 5);		
		Mockito.verify(elevatorMock).setTarget(0, 5);
	}
	
	@Test
	void testGetClockTick() throws RemoteException {
		long tick = 5;
		Mockito.when(elevatorMock.getClockTick()).thenReturn(tick);		
		assertEquals(tick, elevatorWrapper.getClockTick());
		
		Mockito.verify(elevatorMock).getClockTick();
	}
}
