package at.fhhagenberg.sqe.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqe.model.IBuildingWrapper;
import at.fhhagenberg.sqe.model.IElevatorWrapper;
import javafx.application.Platform;

@ExtendWith(MockitoExtension.class)
class ControllerTest {
	
	@Mock
	private IBuildingWrapper buildingMock;
	
	@Mock
	private IElevatorWrapper elevatorMock;
	
	private Controller controller;
	
	
	@BeforeEach
	void setUp() throws Exception {
		controller = new Controller(buildingMock, elevatorMock);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testProperty_elevatorNumbers() throws RemoteException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(2);
		controller.initStaticBuildingInfo();
		
		assertEquals(2, controller.getElevatorNumbers());
	}
	
	@Test
	void testProperty_floorHeight() throws RemoteException {
		Mockito.when(buildingMock.getFloorHeight()).thenReturn(3);
		controller.initStaticBuildingInfo();
		
		assertEquals(3, controller.getFloorHeight());
	}
	
	@Test
	void testProperty_floorNumber() throws RemoteException {

		Mockito.when(buildingMock.getFloorNum()).thenReturn(4);
		controller.initStaticBuildingInfo();
		
		assertEquals(4, controller.getFloorNumber());

	}
	
	@Test
	void testProperty_ManualMode() throws RemoteException {
		controller.data.isManualMode.set(true);
		
		assertTrue(controller.getIsManualMode());
	}
	
	@Test
	void testProperty_AutomaticMode() throws RemoteException {
		controller.data.isManualMode.set(false);
		
		assertFalse(controller.getIsManualMode());
	}
	
	@Test
	void testProperty_CurrentElevator() throws RemoteException {
		controller.data.elevatorNumbers.set(5);
		controller.setElevator(1);
		
		assertEquals(1, controller.getCurrentElevator());
	}
	
	@Test
	void testProperty_getButtonsSize() throws RemoteException {
		Mockito.when(buildingMock.getFloorNum()).thenReturn(4);
		controller.initStaticBuildingInfo();
		
		assertEquals(4, controller.getButtons().size());
	}
	
	@Test
	void testinitStaticInfos_throwExeption() throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {
		controller.initStaticBuildingInfo();
		controller.setReconnectErrorText("ReconnError");
		Mockito.doThrow(RemoteException.class).when(buildingMock).getFloorHeight();
		Mockito.doThrow(RemoteException.class).when(buildingMock).reconnectToRMI();
		controller.initStaticBuildingInfo();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
    		assertEquals("ReconnError", controller.getLastError());
       });
	}
	
	@Test
	void testSetTarget_commitedDirectionUp() throws RemoteException {
		controller.initStaticBuildingInfo();
		controller.SetTarget(5);
		Mockito.verify(elevatorMock).setTarget(0, 5);
		Mockito.verify(elevatorMock).setCommittedDirection(0, 0);
	}
	
	@Test
	void testSetTarget_commitedDirectionDown() throws RemoteException {
		controller.initStaticBuildingInfo();
		controller.data.elevatorFloor.set(3);
		controller.SetTarget(2);
		Mockito.verify(elevatorMock).setTarget(0, 2);
		Mockito.verify(elevatorMock).setCommittedDirection(0, 1);
	}
	
	@Test
	void testSetTarget_throwExeption() throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {
		controller.initStaticBuildingInfo();
		controller.setReconnectErrorText("ReconnError");
		Mockito.doThrow(RemoteException.class).when(elevatorMock).setTarget(0, 2);
		Mockito.doThrow(RemoteException.class).when(buildingMock).reconnectToRMI();
		controller.SetTarget(2);
		Thread.sleep(100,0);
        assertAfterJavaFxPlatformEventsAreDone(() -> {
    		assertEquals("ReconnError", controller.getLastError());
       });
	}
	
	
	@Test
	void testConnectionError() throws RemoteException, InterruptedException, MalformedURLException, NotBoundException {
		Mockito.when(elevatorMock.getClockTick()).thenThrow(new RemoteException());
		Mockito.doThrow(RemoteException.class).when(buildingMock).reconnectToRMI();
		controller.setReconnectErrorText("ReconnError");
		controller.update();
		Mockito.verify(buildingMock, Mockito.timeout(100).times(1)).getElevatorNum();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
    		assertEquals("ReconnError", controller.getLastError());
       });
	}
	
	@Test
	void testUpdate_ElevatorButton() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(0);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);
		Mockito.when(elevatorMock.getElevatorButton(0, 0)).thenReturn(false);
		Mockito.when(elevatorMock.getElevatorButton(0, 1)).thenReturn(false);
		Mockito.when(elevatorMock.getElevatorButton(0, 2)).thenReturn(true);
		
		controller.initStaticBuildingInfo();
		controller.update();
		
        assertAfterJavaFxPlatformEventsAreDone(() -> {
        		
			assertEquals(false, controller.getButtons().get(0).elevatorButton.get());
			assertEquals(false, controller.getButtons().get(1).elevatorButton.get());
			assertEquals(true, controller.getButtons().get(2).elevatorButton.get());
			
        });
		
		
	}
	
	@Test
	void testUpdate_FloorButtonDown() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);
		
		Mockito.when(buildingMock.getFloorButtonDown(0)).thenReturn(false);
		Mockito.when(buildingMock.getFloorButtonDown(1)).thenReturn(false);
		Mockito.when(buildingMock.getFloorButtonDown(2)).thenReturn(true);

		controller.initStaticBuildingInfo();
		
		controller.update();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
			
			assertEquals(false, controller.getButtons().get(0).floorButtonDown.get());
			assertEquals(false, controller.getButtons().get(1).floorButtonDown.get());
			assertEquals(true, controller.getButtons().get(2).floorButtonDown.get());
        });
		
	}
	
	@Test
	void testUpdate_FloorButtonUp() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);
		
		Mockito.when(buildingMock.getFloorButtonUp(0)).thenReturn(false);
		Mockito.when(buildingMock.getFloorButtonUp(1)).thenReturn(true);
		Mockito.when(buildingMock.getFloorButtonUp(2)).thenReturn(false);

		controller.initStaticBuildingInfo();
		
		controller.update();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
			assertEquals(false, controller.getButtons().get(0).floorButtonUp.get());
			assertEquals(true, controller.getButtons().get(1).floorButtonUp.get());
			assertEquals(false, controller.getButtons().get(2).floorButtonUp.get());
        });
		
		
	}
	
	@Test
	void testUpdate_ServicedFloors() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);

		Mockito.when(elevatorMock.getServicesFloors(0, 0)).thenReturn(true);
		Mockito.when(elevatorMock.getServicesFloors(0, 1)).thenReturn(false);
		Mockito.when(elevatorMock.getServicesFloors(0, 2)).thenReturn(false);

		controller.initStaticBuildingInfo();
		
		controller.update();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
			assertEquals(true, controller.getButtons().get(0).elevatorServicesFloor.get());
			assertEquals(false, controller.getButtons().get(1).elevatorServicesFloor.get());
			assertEquals(false, controller.getButtons().get(2).elevatorServicesFloor.get());
        });
		
		
	}
	
	@Test
	void testUpdate_CommittedDirection() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);
		
		Mockito.when(elevatorMock.getCommittedDirection(0)).thenReturn(1);
		controller.initStaticBuildingInfo();
		
		controller.update();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
			assertEquals(1, controller.getCommittedDirection());
        });
		
		
	}
	
	@Test
	void testUpdate_Accel() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);
		Mockito.when(elevatorMock.getElevatorAccel(0)).thenReturn(50);
		controller.initStaticBuildingInfo();
		
		controller.update();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
			assertEquals(50, controller.getElevatorAccel());
        });
		
		
	}
	
	@Test
	void testUpdate_DoorStatus() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);
		
		Mockito.when(elevatorMock.getElevatorDoorStatus(0)).thenReturn(1);
		controller.initStaticBuildingInfo();
		
		controller.update();
        assertAfterJavaFxPlatformEventsAreDone(() -> {		
			assertEquals(1, controller.getElevatorDoorStatus());
        });
		
		
	}
	
	@Test
	void testUpdate_Position() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);

		Mockito.when(elevatorMock.getElevatorPosition(0)).thenReturn(2);

		controller.initStaticBuildingInfo();
		
		controller.update();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
			assertEquals(2, controller.getElevatorPosition());
        });
		
		
	}
	
	@Test
	void testUpdate_Speed() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);
		Mockito.when(elevatorMock.getElevatorSpeed(0)).thenReturn(10);

		controller.initStaticBuildingInfo();
		
		controller.update();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
			assertEquals(10, controller.getElevatorSpeed());
        });
		
	}
	
	@Test
	void testUpdate_Capacity() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);
		Mockito.when(elevatorMock.getElevatorCapacity(0)).thenReturn(5);

		controller.initStaticBuildingInfo();
		controller.update();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
			assertEquals(5, controller.getElevatorCapacity());
        });
		
	}
	
	@Test
	void testUpdate_Target() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);
		Mockito.when(elevatorMock.getTarget(0)).thenReturn(4);
		controller.initStaticBuildingInfo();
		
		controller.update();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
			assertEquals(4, controller.getElevatorTarget());
        });
		
	}
	
	@Test
	void testUpdate_Floor() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);

		Mockito.when(elevatorMock.getElevatorFloor(0)).thenReturn(6);
		controller.initStaticBuildingInfo();
		
		controller.update();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
			assertEquals(6, controller.getElevatorFloor());
        });
		
	}
	
	@Test
	void testUpdate_Weight() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);

		Mockito.when(elevatorMock.getElevatorWeight(0)).thenReturn(100);
		controller.initStaticBuildingInfo();
		
		controller.update();
        assertAfterJavaFxPlatformEventsAreDone(() -> {
			assertEquals(100, controller.getElevatorWeight());
        });
		
	}
	
	@Test
	void testTickFails() throws RemoteException, InterruptedException {
		controller = new Controller(buildingMock, elevatorMock);
		Mockito.when(elevatorMock.getClockTick())
		.thenReturn((long) 0,(long) 1,(long) 2,(long) 3,(long) 4,(long) 5,(long) 6,(long) 7,(long) 8,(long) 9,
				(long) 10,(long) 11,(long) 12,(long) 13,(long) 14,(long) 15,(long) 0,(long) 1,(long) 2,(long) 3,
				(long) 4,(long) 5,(long) 6,(long) 7,(long) 8,(long) 9,(long) 10,(long) 11,(long) 12,(long) 13,(long) 14,
				(long) 15,(long) 0,(long) 1,(long) 2,(long) 3,(long) 4,(long) 5,(long) 6,(long) 7,(long) 8,(long) 9,
		(long) 10,(long) 11,(long) 12,(long) 13,(long) 14,(long) 15,(long) 0,(long) 1,(long) 2,(long) 3,
		(long) 4,(long) 5,(long) 6,(long) 7,(long) 8,(long) 9,(long) 10,(long) 11,(long) 12,(long) 13,(long) 14,
		(long) 15);
		controller.setRetryErrorText("RetryError");
		controller.update();
		controller.update();
		controller.update();
		controller.update();
		
        assertAfterJavaFxPlatformEventsAreDone(() -> {
        	assertEquals("RetryError", controller.getLastError());
       });
		
		
	}
	
	@Test
	void testPollingError() throws RemoteException, InterruptedException, MalformedURLException, NotBoundException {
		
		controller = new Controller(buildingMock, elevatorMock);
		Mockito.when(elevatorMock.getClockTick()).thenThrow(new RemoteException());
		Mockito.doThrow(RemoteException.class).when(elevatorMock).reconnectToRMI();
		controller.setReconnectErrorText("Reconnect to RMI failed!");
		controller.update();
		
        assertAfterJavaFxPlatformEventsAreDone(() -> {
    		assertEquals("Reconnect to RMI failed!", controller.getLastError());
       });
        
	}
	
	@Test
	void testSyncSuccessfully() throws RemoteException, InterruptedException, MalformedURLException, NotBoundException {
		
		controller = new Controller(buildingMock, elevatorMock);
		
		Mockito.when(elevatorMock.getClockTick()).thenReturn((long) 0);
		controller.setRetryErrorText("RetryError");
		controller.data.errors.add("RetryError");
		controller.setRetrySuccessText("Synchronisation successfully");
		controller.update();

		
        assertAfterJavaFxPlatformEventsAreDone(() -> {
    		assertEquals("Synchronisation successfully", controller.getLastError());

       });
        
	}
       
	
	@Test
	void testSetElevator() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(3);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(2);
		controller = new Controller(buildingMock, elevatorMock);

		controller.setElevator(2);
		assertEquals(2, controller.getCurrentElevator());
	}
	
	@Test
	void testSetElevatorError() throws RemoteException, InterruptedException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(2);
		controller = new Controller(buildingMock, elevatorMock);

		controller.setElevator(3);
        assertAfterJavaFxPlatformEventsAreDone(() -> {
    		assertEquals("elevatorNumber not available", controller.getLastError());
       });
	}

	@Test
	void testReconnectToRMI() throws Exception {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(1);
		Mockito.when(buildingMock.getFloorHeight()).thenReturn(2);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(0);
		Mockito.when(elevatorMock.getClockTick()).thenThrow(new RemoteException());
		
		controller = new Controller(buildingMock, elevatorMock);
		controller.update();
		
		Mockito.verify(buildingMock, Mockito.atLeastOnce()).reconnectToRMI();
		Mockito.verify(elevatorMock, Mockito.atLeastOnce()).reconnectToRMI();
	}
	
    private void assertAfterJavaFxPlatformEventsAreDone(Runnable runnable) throws InterruptedException {
        waitOnJavaFxPlatformEventsDone();
        runnable.run();
    }

    private void waitOnJavaFxPlatformEventsDone() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Platform.runLater(countDownLatch::countDown);
        countDownLatch.await();
    }
}
