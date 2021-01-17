package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxService;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import at.fhhagenberg.sqe.model.IBuildingWrapper;
import at.fhhagenberg.sqe.model.IElevatorWrapper;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
public class AppTest {
    private int target; 

    @Mock
	private IBuildingWrapper buildingMock;
	
	@Mock
	private IElevatorWrapper elevatorMock;
	
	private void setupMock() throws RemoteException {
		Mockito.when(buildingMock.getElevatorNum()).thenReturn(2);
		Mockito.when(buildingMock.getFloorNum()).thenReturn(3);
		Mockito.when(elevatorMock.getTarget(0)).thenReturn(target);
		Mockito.when(elevatorMock.getElevatorDoorStatus(0)).thenReturn(2);
		Mockito.when(elevatorMock.getServicesFloors(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
	}
    
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) {
    	
    	try {
    		target = 99;
			setupMock();
		} catch (RemoteException e) {
			e.printStackTrace();
		}    	
        var app = new App(buildingMock, elevatorMock);
        app.start(stage);
    }

    /**
     * @brief Asserts static information
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testStaticInformation(FxRobot robot) {
    	FxAssert.verifyThat("#lbFloor", LabeledMatchers.hasText(Integer.toString(0)));
    	FxAssert.verifyThat("#lbPayload", LabeledMatchers.hasText(Integer.toString(0)));
    	FxAssert.verifyThat("#lbSpeed", LabeledMatchers.hasText(Integer.toString(0)));
    	FxAssert.verifyThat("#lbDoors", LabeledMatchers.hasText("closed"));
    	FxAssert.verifyThat("#floorNumber", LabeledMatchers.hasText(Integer.toString(3)));
    }
    
    
    /**
     * @brief Asserts that the correct error state is shown in the GUI
     * @param robot - Will be injected by the test runner.
     * @throws RemoteException 
     * @throws InterruptedException 
     * @throws NotBoundException 
     * @throws MalformedURLException 
     */
    @Test
    public void testCorrectErrorState(FxRobot robot) throws RemoteException, InterruptedException, MalformedURLException, NotBoundException {
    	Mockito.when(elevatorMock.getClockTick()).thenThrow(new RemoteException());
    	Mockito.doThrow(RemoteException.class).when(elevatorMock).reconnectToRMI();
    	Thread.sleep(150, 0);

    	assertEquals(robot.lookup("#lvErrors").queryAs(ListView.class).getItems().get(0), "Reconnect to RMI failed! ");
    }
    
}