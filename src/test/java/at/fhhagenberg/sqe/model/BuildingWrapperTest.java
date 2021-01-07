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

import sqelevator.IElevator;

@ExtendWith(MockitoExtension.class)
class BuildingWrapperTest {
	@Mock
	private IElevator elevatorMock;
	
	private BuildingWrapper buildingWrapper;

	@BeforeEach
	void setUp() throws Exception {
		buildingWrapper = new BuildingWrapper(elevatorMock);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetElevatorNumber() throws RemoteException {
		Mockito.when(elevatorMock.getElevatorNum()).thenReturn(5);
		assertEquals(5, buildingWrapper.getElevatorNum());
		
		Mockito.verify(elevatorMock).getElevatorNum();
	}

	@Test
	void testGetFloorButtonDown() throws RemoteException {
		Mockito.when(elevatorMock.getFloorButtonDown(0)).thenReturn(true);
		Mockito.when(elevatorMock.getFloorButtonDown(1)).thenReturn(false);
		
		assertTrue(buildingWrapper.getFloorButtonDown(0));
		assertFalse(buildingWrapper.getFloorButtonDown(1));
		
		Mockito.verify(elevatorMock).getFloorButtonDown(0);
		Mockito.verify(elevatorMock).getFloorButtonDown(1);
	}
	
	@Test
	void testGetFloorButtonUp() throws RemoteException {
		Mockito.when(elevatorMock.getFloorButtonUp(0)).thenReturn(true);
		Mockito.when(elevatorMock.getFloorButtonUp(1)).thenReturn(false);
		
		assertTrue(buildingWrapper.getFloorButtonUp(0));
		assertFalse(buildingWrapper.getFloorButtonUp(1));
		
		Mockito.verify(elevatorMock).getFloorButtonUp(0);
		Mockito.verify(elevatorMock).getFloorButtonUp(1);
	}
	
	@Test
	void testGetFloorHeight() throws RemoteException {
		Mockito.when(elevatorMock.getFloorHeight()).thenReturn(82);
		assertEquals(82, buildingWrapper.getFloorHeight());
		
		Mockito.verify(elevatorMock).getFloorHeight();
	}
	
	@Test
	void testGetFloorNum() throws RemoteException {
		Mockito.when(elevatorMock.getFloorNum()).thenReturn(10);
		assertEquals(10, buildingWrapper.getFloorNum());
		
		Mockito.verify(elevatorMock).getFloorNum();
	}
}
