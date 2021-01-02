package at.fhhagenberg.sqe;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import at.fhhagenberg.sqe.model.IBuildingWrapper;
import at.fhhagenberg.sqe.model.IElevatorWrapper;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
public class AppTest {
    private Button button;
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
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testEndToEndScenario(FxRobot robot) {
    	try {
			Mockito.doAnswer(invocation -> {
				target = invocation.getArgument(1);
				Mockito.when(elevatorMock.getTarget(0)).thenReturn(target);
				return null;
			}).when(elevatorMock).setTarget(Mockito.anyInt(), Mockito.anyInt());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
    	
    	FxAssert.verifyThat("#lbTarget", LabeledMatchers.hasText(Integer.toString(target)));
    	
    	robot.clickOn(".button");
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("set target"));
        try {
			Thread.sleep(150, 0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        FxAssert.verifyThat("#lbTarget", LabeledMatchers.hasText(Integer.toString(target)));
    }
}