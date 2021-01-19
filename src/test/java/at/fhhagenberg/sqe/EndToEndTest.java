package at.fhhagenberg.sqe;

import java.rmi.RemoteException;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import at.fhhagenberg.sqe.model.BuildingWrapper;
import at.fhhagenberg.sqe.model.ElevatorWrapper;
import at.fhhagenberg.sqe.pageobject.AppPO;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Pair;
import sqelevator.IElevator;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
class EndToEndTest {
    private int target; 

    @Mock
	private IElevator elevator;
    
    AppPO po;
	
	
	private void setupMock() throws RemoteException {
		Mockito.when(elevator.getElevatorNum()).thenReturn(2);
		Mockito.when(elevator.getFloorNum()).thenReturn(3);
		Mockito.when(elevator.getTarget(0)).thenReturn(target);
		Mockito.when(elevator.getElevatorDoorStatus(0)).thenReturn(2);
		Mockito.when(elevator.getServicesFloors(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
	}
    
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     * @throws RemoteException 
     */
    @Start
    public void start(Stage stage) throws RemoteException {
    	try {
    		target = 99;
			setupMock();
		} catch (RemoteException e) {
			e.printStackTrace();
		}    	
        var app = new App(new BuildingWrapper(elevator), new ElevatorWrapper(elevator));
        app.start(stage);
        po = new AppPO();
    }

    /**
     * @param robot - Will be injected by the test runner.
     * @throws RemoteException 
     * @throws InterruptedException 
     */
    @Test
    void testEndToEndStaticInformation(FxRobot robot) throws RemoteException, InterruptedException {
    	Mockito.reset(elevator);
    	Mockito.when(elevator.getElevatorFloor(0)).thenReturn(3);
    	Mockito.when(elevator.getElevatorWeight(0)).thenReturn(100);
    	Mockito.when(elevator.getElevatorSpeed(0)).thenReturn(5);
    	Mockito.when(elevator.getElevatorDoorStatus(0)).thenReturn(1);
    	
    	Mockito.verify(elevator, Mockito.timeout(2000).atLeast(2)).getClockTick();
    	
//        po.assertAfterJavaFxPlatformEventsAreDone(() -> {
//        	FxAssert.verifyThat(po.GetCurrentFloorLabel(), LabeledMatchers.hasText(Integer.toString(3)));
//        	FxAssert.verifyThat(po.GetPayloadLabel(), LabeledMatchers.hasText(Integer.toString(100)));
//        	FxAssert.verifyThat(po.GetSpeedLabel(), LabeledMatchers.hasText(Integer.toString(5)));
//        	FxAssert.verifyThat(po.GetDoorsLabel(), LabeledMatchers.hasText("open"));
//        	FxAssert.verifyThat(po.GetFloorNumberLabel(), LabeledMatchers.hasText(Integer.toString(3)));
//       });
        
        po.VerifyLabels(
        		new Pair<String, String>(po.GetCurrentFloorLabel(), Integer.toString(3)),
        		new Pair<String, String>(po.GetPayloadLabel(), Integer.toString(100)),
        		new Pair<String, String>(po.GetCurrentFloorLabel(), Integer.toString(3)),
        		new Pair<String, String>(po.GetDoorsLabel(), "open"),
        		new Pair<String, String>(po.GetFloorNumberLabel(), Integer.toString(3))
        		);

    }
    
    /**
     * @param robot - Will be injected by the test runner.
     * @throws RemoteException 
     */
    @Test
    void testEndToEndScenarioSetTarget(FxRobot robot) throws Exception  {
    	Mockito.doAnswer(invocation -> {
			target = invocation.getArgument(1);
			Mockito.when(elevator.getTarget(0)).thenReturn(target);
			return null;
		}).when(elevator).setTarget(Mockito.anyInt(), Mockito.anyInt());
    	
    	
//        po.assertAfterJavaFxPlatformEventsAreDone(() -> {
//        	FxAssert.verifyThat(po.GetTargetLabel(), LabeledMatchers.hasText(Integer.toString(target)));
//       });
        
        po.VerifyLabel(po.GetTargetLabel(), Integer.toString(target));
    	
        
        po.ClickSetTarget(robot);
    	// robot.clickOn(".button");
    	
    	Mockito.verify(elevator, Mockito.timeout(1000)).setTarget(0, 0);
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     * @throws RemoteException 
     */
    @Test
    void testEndToEndScenarioReadTarget(FxRobot robot) throws Exception  {
    	Mockito.reset(elevator);
    	Mockito.when(elevator.getTarget(0)).thenReturn(5);
    	Mockito.verify(elevator, Mockito.timeout(2000).atLeast(2)).getClockTick();
//        po.assertAfterJavaFxPlatformEventsAreDone(() -> {
//            FxAssert.verifyThat(po.GetTargetLabel(), LabeledMatchers.hasText(Integer.toString(5)));
//       });
        
        po.VerifyLabel(po.GetTargetLabel(), Integer.toString(5));

    }

}