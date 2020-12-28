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
	}

}
