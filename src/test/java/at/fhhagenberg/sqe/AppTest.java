package at.fhhagenberg.sqe;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import at.fhhagenberg.sqe.model.IBuildingWrapper;
import at.fhhagenberg.sqe.model.IElevatorWrapper;
import at.fhhagenberg.sqe.pageobject.AppPO;
import javafx.stage.Stage;

@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
class AppTest {
    private int target; 

    @Mock
	private IBuildingWrapper buildingMock;
	
	@Mock
	private IElevatorWrapper elevatorMock;
	
	App app;
	AppPO po;
	
	private void setupMock() throws Exception {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(2);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);
		Mockito.when(elevatorMock.getElevatorCapacity(0)).thenReturn(100);
		Mockito.when(elevatorMock.getElevatorSpeed(0)).thenReturn(200);
		Mockito.when(elevatorMock.getTarget(0)).thenReturn(target);
		Mockito.when(elevatorMock.getElevatorDoorStatus(0)).thenReturn(2);
		Mockito.when(elevatorMock.getServicesFloors(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
	}
    
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     * @throws Exception 
     */
    @Start
    public void start(Stage stage) throws Exception {
    	
    	try {
    		target = 99;
			setupMock();
		} catch (RemoteException e) {
			e.printStackTrace();
		}    	
        app = new App(buildingMock, elevatorMock);
        app.start(stage);
        po = new AppPO();
    }

    /**
     * @brief Asserts floor number
     * @param robot - Will be injected by the test runner.
     * @throws Exception 
     */
    @Test
    void testFloorNumber(FxRobot robot) throws Exception {
    	Mockito.verify(buildingMock, Mockito.timeout(100).times(2)).getElevatorNum();

        po.VerifyLabel(po.GetFloorNumberLabel(), Integer.toString(3));
    }
    
    /**
     * @brief Asserts speed
     * @param robot - Will be injected by the test runner.
     * @throws Exception 
     */
    @Test
    void testSpeed(FxRobot robot) throws Exception {
    	Mockito.verify(buildingMock, Mockito.timeout(100).times(2)).getElevatorNum();

        po.VerifyLabel(po.GetSpeedLabel(), Integer.toString(200));
    }
    
    /**
     * @brief Checks Direction Images
     * @param robot - Will be injected by the test runner.
     * @throws Exception 
     */
    @Test
    void testDirectionUncommitted(FxRobot robot) throws Exception {
    	Mockito.when(elevatorMock.getCommittedDirection(0)).thenReturn(2);
    	Mockito.verify(elevatorMock, Mockito.timeout(100).atLeastOnce()).getCommittedDirection(0);
    	
    	app.getController().setCommittedDirection(2);
    	
        po.VerifyEquals(po.GetUpActive(robot), false);
        po.VerifyEquals(po.GetDownActive(robot), false);
    }
    
    /**
     * @brief Checks Direction Images
     * @param robot - Will be injected by the test runner.
     * @throws Exception 
     */
    @Test
    void testDirectionDown(FxRobot robot) throws Exception {
    	Mockito.when(elevatorMock.getCommittedDirection(0)).thenReturn(1);
    	Mockito.verify(elevatorMock, Mockito.timeout(100).atLeastOnce()).getCommittedDirection(0);
    	
    	app.getController().setCommittedDirection(1);
    	
        po.VerifyEquals(po.GetUpActive(robot), false);
        po.VerifyEquals(po.GetDownActive(robot), true);
    }
  
    /**
     * @brief Asserts door status
     * @param robot - Will be injected by the test runner.
     * @throws Exception 
     */
    @Test
    void testDoorStatus(FxRobot robot) throws Exception {
    	Mockito.verify(buildingMock, Mockito.timeout(100).times(2)).getElevatorNum();

        po.VerifyLabel(po.GetDoorsLabel(), "closed");
    }

    /**
     * @brief Asserts error state
     * @param robot - Will be injected by the test runner.
     * @throws Exception 
     */
    @Test
    void testCorrectErrorState(FxRobot robot) throws Exception {
    	
    	Mockito.when(elevatorMock.getClockTick()).thenThrow(new RemoteException());
    	Mockito.doThrow(RemoteException.class).when(elevatorMock).reconnectToRMI();
    	
    	app.getController().setReconnectErrorText("ReconnectFailed");
    	
    	Mockito.verify(buildingMock, Mockito.timeout(150).times(1)).reconnectToRMI();
        po.VerifyEquals(po.GetFirstError(robot), "ReconnectFailed");
    }

}