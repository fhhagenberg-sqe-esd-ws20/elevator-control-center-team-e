package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import at.fhhagenberg.sqe.model.IBuildingWrapper;
import at.fhhagenberg.sqe.model.IElevatorWrapper;
import at.fhhagenberg.sqe.pageobject.AppPO;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
	
	private void setupMock() throws RemoteException {
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
     */
    @Start
    public void start(Stage stage) {
    	
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
     * @throws RemoteException 
     * @throws InterruptedException 
     */
    @Test
    void testFloorNumber(FxRobot robot) throws RemoteException, InterruptedException {
    	Mockito.verify(buildingMock, Mockito.timeout(100).times(2)).getElevatorNum();

        po.assertAfterJavaFxPlatformEventsAreDone(() -> {
        	FxAssert.verifyThat(po.GetFloorNumberLabel(), LabeledMatchers.hasText(Integer.toString(3)));
       });

    }
    
    /**
     * @brief Asserts speed
     * @param robot - Will be injected by the test runner.
     * @throws RemoteException 
     * @throws InterruptedException 
     */
    @Test
    void testSpeed(FxRobot robot) throws RemoteException, InterruptedException {
    	Mockito.verify(buildingMock, Mockito.timeout(100).times(2)).getElevatorNum();

        po.assertAfterJavaFxPlatformEventsAreDone(() -> {
        	FxAssert.verifyThat(po.GetSpeedLabel(), LabeledMatchers.hasText(Integer.toString(200)));
       });

    }
    
    /**
     * @brief Asserts door status
     * @param robot - Will be injected by the test runner.
     * @throws RemoteException 
     * @throws InterruptedException 
     */
    @Test
    void testDoorStatus(FxRobot robot) throws RemoteException, InterruptedException {
    	Mockito.verify(buildingMock, Mockito.timeout(100).times(2)).getElevatorNum();

        po.assertAfterJavaFxPlatformEventsAreDone(() -> {
        	FxAssert.verifyThat(po.GetDoorsLabel(), LabeledMatchers.hasText("closed"));
       });

    }
    
    
    /**
     * @brief Asserts error state
     * @param robot - Will be injected by the test runner.
     * @throws RemoteException 
     * @throws InterruptedException 
     * @throws NotBoundException 
     * @throws MalformedURLException 
     */
    @Test
    void testCorrectErrorState(FxRobot robot) throws RemoteException, InterruptedException, MalformedURLException, NotBoundException {
    	
    	Mockito.when(elevatorMock.getClockTick()).thenThrow(new RemoteException());
    	Mockito.doThrow(RemoteException.class).when(elevatorMock).reconnectToRMI();
    	app.getController().setReconnectErrorText("ReconnectFailed");
    	Mockito.verify(buildingMock, Mockito.timeout(150).times(1)).reconnectToRMI();
        po.assertAfterJavaFxPlatformEventsAreDone(() -> {
        	assertEquals(robot.lookup(po.GetErrorListView()).queryAs(ListView.class).getItems().get(0), "ReconnectFailed");
       });

    }

}