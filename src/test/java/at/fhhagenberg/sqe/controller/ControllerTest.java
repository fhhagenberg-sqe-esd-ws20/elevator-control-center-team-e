package at.fhhagenberg.sqe.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqe.model.IBuildingWrapper;
import at.fhhagenberg.sqe.model.IElevatorWrapper;

@ExtendWith(MockitoExtension.class)
class ControllerTest {
	
	@Mock
	private IBuildingWrapper buildingMock;
	
	@Mock
	private IElevatorWrapper elevatorMock;
	
	private Controller controller;
	
	
	@BeforeEach
	void setUp() throws Exception {

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testPropertyAfterCtor() throws RemoteException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(2);
		Mockito.when(buildingMock.getFloorHeight()).thenReturn(3);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(4);
		controller = new Controller(buildingMock, elevatorMock);
		
		assertEquals(2, controller.getElevatorNumbers());
		assertEquals(3, controller.getFloorHeight());
		assertEquals(4, controller.getFloorNumber());
		assertTrue(controller.getIsManualMode());
		assertEquals(0, controller.getCurrentElevator());
		assertEquals(4, controller.getButtons().size());
	}
	
	@Test
	void testSetTarget() throws RemoteException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(2);
		Mockito.when(buildingMock.getFloorHeight()).thenReturn(3);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(4);
		controller = new Controller(buildingMock, elevatorMock);

		controller.SetTarget(5);
		Mockito.verify(elevatorMock).setTarget(0, 5);
	}
	
	
	@Test
	void testError() throws RemoteException {
		Mockito.when(buildingMock.getElevatorNum()).thenThrow(new RemoteException("failed connecting"));
		controller = new Controller(buildingMock, elevatorMock);
		
		assertEquals("failed connecting", controller.getLastError());
	}
	
	@Test
	void testStart() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorHeight()).thenReturn(2);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);
		controller = new Controller(buildingMock, elevatorMock);
		
		Mockito.when(elevatorMock.getElevatorButton(0, 0)).thenReturn(true).thenReturn(true);
		Mockito.when(elevatorMock.getElevatorButton(0, 1)).thenReturn(false).thenReturn(true);
		Mockito.when(elevatorMock.getElevatorButton(0, 2)).thenReturn(true).thenReturn(true);
		
		Mockito.when(buildingMock.getFloorButtonDown(0)).thenReturn(false).thenReturn(false);
		Mockito.when(buildingMock.getFloorButtonDown(1)).thenReturn(false).thenReturn(false);
		Mockito.when(buildingMock.getFloorButtonDown(2)).thenReturn(true).thenReturn(false);
		
		Mockito.when(buildingMock.getFloorButtonUp(0)).thenReturn(false).thenReturn(true);
		Mockito.when(buildingMock.getFloorButtonUp(1)).thenReturn(true).thenReturn(false);
		Mockito.when(buildingMock.getFloorButtonUp(2)).thenReturn(false).thenReturn(true);
		
		Mockito.when(elevatorMock.getServicesFloors(0, 0)).thenReturn(true).thenReturn(false);
		Mockito.when(elevatorMock.getServicesFloors(0, 1)).thenReturn(false).thenReturn(true);
		Mockito.when(elevatorMock.getServicesFloors(0, 2)).thenReturn(false).thenReturn(false);
		
		Mockito.when(elevatorMock.getCommittedDirection(0)).thenReturn(1).thenReturn(2);
		Mockito.when(elevatorMock.getElevatorAccel(0)).thenReturn(50).thenReturn(51);
		Mockito.when(elevatorMock.getElevatorDoorStatus(0)).thenReturn(1).thenReturn(2);
		Mockito.when(elevatorMock.getElevatorFloor(0)).thenReturn(3).thenReturn(4);
		Mockito.when(elevatorMock.getElevatorPosition(0)).thenReturn(2).thenReturn(3);
		Mockito.when(elevatorMock.getElevatorSpeed(0)).thenReturn(10).thenReturn(11);
		Mockito.when(elevatorMock.getElevatorWeight(0)).thenReturn(100).thenReturn(101);
		Mockito.when(elevatorMock.getElevatorCapacity(0)).thenReturn(5).thenReturn(6);
		Mockito.when(elevatorMock.getTarget(0)).thenReturn(4).thenReturn(5);
		
		controller.start();
		Thread.sleep(130, 0);
		
		assertEquals(true, controller.getButtons().get(0).elevatorButton.get());
		assertEquals(false, controller.getButtons().get(1).elevatorButton.get());
		assertEquals(true, controller.getButtons().get(2).elevatorButton.get());
		
		assertEquals(false, controller.getButtons().get(0).floorButtonDown.get());
		assertEquals(false, controller.getButtons().get(1).floorButtonDown.get());
		assertEquals(true, controller.getButtons().get(2).floorButtonDown.get());
		
		assertEquals(false, controller.getButtons().get(0).floorButtonUp.get());
		assertEquals(true, controller.getButtons().get(1).floorButtonUp.get());
		assertEquals(false, controller.getButtons().get(2).floorButtonUp.get());
		
		assertEquals(true, controller.getButtons().get(0).elevatorServicesFloor.get());
		assertEquals(false, controller.getButtons().get(1).elevatorServicesFloor.get());
		assertEquals(false, controller.getButtons().get(2).elevatorServicesFloor.get());
		
		assertEquals(1, controller.getCommittedDirection());
		assertEquals(50, controller.getElevatorAccel());
		assertEquals(1, controller.getElevatorDoorStatus());
		assertEquals(3, controller.getElevatorFloor());
		assertEquals(2, controller.getElevatorPosition());
		assertEquals(10, controller.getElevatorSpeed());
		assertEquals(100, controller.getElevatorWeight());
		assertEquals(5, controller.getElevatorCapacity());
		assertEquals(4, controller.getElevatorTarget());
		
		Thread.sleep(100, 0);
		
		assertEquals(true, controller.getButtons().get(0).elevatorButton.get());
		assertEquals(true, controller.getButtons().get(1).elevatorButton.get());
		assertEquals(true, controller.getButtons().get(2).elevatorButton.get());
		
		assertEquals(false, controller.getButtons().get(0).floorButtonDown.get());
		assertEquals(false, controller.getButtons().get(1).floorButtonDown.get());
		assertEquals(false, controller.getButtons().get(2).floorButtonDown.get());
		
		assertEquals(true, controller.getButtons().get(0).floorButtonUp.get());
		assertEquals(false, controller.getButtons().get(1).floorButtonUp.get());
		assertEquals(true, controller.getButtons().get(2).floorButtonUp.get());
		
		assertEquals(false, controller.getButtons().get(0).elevatorServicesFloor.get());
		assertEquals(true, controller.getButtons().get(1).elevatorServicesFloor.get());
		assertEquals(false, controller.getButtons().get(2).elevatorServicesFloor.get());
		
		assertEquals(2, controller.getCommittedDirection());
		assertEquals(51, controller.getElevatorAccel());
		assertEquals(2, controller.getElevatorDoorStatus());
		assertEquals(4, controller.getElevatorFloor());
		assertEquals(3, controller.getElevatorPosition());
		assertEquals(11, controller.getElevatorSpeed());
		assertEquals(101, controller.getElevatorWeight());
		assertEquals(6, controller.getElevatorCapacity());
		assertEquals(5, controller.getElevatorTarget());
		
	}
	
	@Test
	void testTickFails() throws RemoteException, InterruptedException {
		
		controller = new Controller(buildingMock, elevatorMock);
		Mockito.when(elevatorMock.getClockTick()).thenReturn((long) 0).thenReturn((long) 1).thenReturn((long) 2).thenReturn((long) 3).
		thenReturn((long) 4).thenReturn((long) 5).thenReturn((long) 6).thenReturn((long) 7);
		controller.start();
		Thread.sleep(710, 0);
		assertEquals("Reached maximum retries while updating elevator.", controller.getLastError());
		
	}
	
	@Test
	void testPollingError() throws RemoteException, InterruptedException {
		
		controller = new Controller(buildingMock, elevatorMock);
		Mockito.when(elevatorMock.getClockTick()).thenThrow(new RemoteException("no connection"));
		controller.start();
		Thread.sleep(110, 0);
		assertEquals("no connection", controller.getLastError());
		
	}
	
	@Test
	void testSetTargetElevatorReachedTarget() throws RemoteException, InterruptedException {
		
		controller = new Controller(buildingMock, elevatorMock);
		Mockito.when(elevatorMock.getTarget(0)).thenReturn(0).thenReturn(1).thenReturn(2);
		controller.start();
		Thread.sleep(110, 0);
		assertEquals(0, controller.getElevatorTarget());
		controller.SetTarget(2);
		Thread.sleep(100, 0);
		assertEquals(1, controller.getElevatorTarget());
		Thread.sleep(100, 0);
		assertEquals(2, controller.getElevatorTarget());
		
		Mockito.verify(elevatorMock).setTarget(0, 2);
		
	}
	
	@Test
	void testSetElevator() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(3);
		controller = new Controller(buildingMock, elevatorMock);

		controller.setElevator(2);
		assertEquals(2, controller.getCurrentElevator());
		
		assertEquals(0, controller.getCommittedDirection());
		assertEquals(0, controller.getElevatorAccel());
		assertEquals(0, controller.getElevatorDoorStatus());
		assertEquals(0, controller.getElevatorFloor());
		assertEquals(0, controller.getElevatorPosition());
		assertEquals(0, controller.getElevatorSpeed());
		assertEquals(0, controller.getElevatorWeight());
		assertEquals(0, controller.getElevatorCapacity());
		assertEquals(0, controller.getElevatorTarget());
	}

	@Test
	void testReconnectToRMI() throws Exception {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorHeight()).thenReturn(2);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(0);
		Mockito.when(elevatorMock.getClockTick()).thenThrow(new RemoteException());
		
		controller = new Controller(buildingMock, elevatorMock);
		controller.start();
		Thread.sleep(200, 0);
		
		Mockito.verify(buildingMock, Mockito.atLeastOnce()).reconnectToRMI();
		Mockito.verify(elevatorMock, Mockito.atLeastOnce()).reconnectToRMI();
	}
}
