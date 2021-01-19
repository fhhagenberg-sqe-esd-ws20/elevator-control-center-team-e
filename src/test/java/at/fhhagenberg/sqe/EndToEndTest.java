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

import at.fhhagenberg.sqe.model.BuildingWrapper;
import at.fhhagenberg.sqe.model.ElevatorWrapper;
import at.fhhagenberg.sqe.pageobject.AppPO;
import javafx.stage.Stage;
import javafx.util.Pair;
import sqelevator.IElevator;

@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
class EndToEndTest {
    private int target; 

    @Mock
	private IElevator elevator;
    
    AppPO po;
	
	
	private void setupMock() throws Exception {
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
        var app = new App(new BuildingWrapper(elevator), new ElevatorWrapper(elevator));
        app.start(stage);
        po = new AppPO();
    }

    /**
     * @param robot - Will be injected by the test runner.
     * @throws Exception 
     */
	@Test
    void testEndToEndStaticInformation(FxRobot robot) throws Exception {
    	Mockito.reset(elevator);
    	Mockito.when(elevator.getElevatorFloor(0)).thenReturn(3);
    	Mockito.when(elevator.getElevatorWeight(0)).thenReturn(100);
    	Mockito.when(elevator.getElevatorSpeed(0)).thenReturn(5);
    	Mockito.when(elevator.getElevatorDoorStatus(0)).thenReturn(1);
    	
    	Mockito.verify(elevator, Mockito.timeout(2000).atLeast(2)).getClockTick();
    	        
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
     * @throws Exception 
     */
    @Test
    void testEndToEndScenarioSetTarget(FxRobot robot) throws Exception  {
    	Mockito.doAnswer(invocation -> {
			target = invocation.getArgument(1);
			Mockito.when(elevator.getTarget(0)).thenReturn(target);
			return null;
		}).when(elevator).setTarget(Mockito.anyInt(), Mockito.anyInt());
    	        
        po.VerifyLabel(po.GetTargetLabel(), Integer.toString(target));
    	      
        po.ClickSetTarget(robot);
    	
    	Mockito.verify(elevator, Mockito.timeout(1000)).setTarget(0, 0);
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     * @throws Exception 
     */
    @Test
    void testEndToEndScenarioReadTarget(FxRobot robot) throws Exception  {
    	Mockito.reset(elevator);
    	Mockito.when(elevator.getTarget(0)).thenReturn(5);
    	Mockito.verify(elevator, Mockito.timeout(2000).atLeast(2)).getClockTick();

        po.VerifyLabel(po.GetTargetLabel(), Integer.toString(5));
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     * @throws Exception 
     */
    @Test
    void testSetAutomaticMode(FxRobot robot) throws Exception {
    	po.ClickSetAutomatic(robot);
    	
    	po.VerifyEquals(po.GetFloorsDisabled(robot), true);
    }
    
    /**
     * @param robot - Will be injected by the test runner.
     * @throws Exception 
     */
    @Test
    void testResetToManualMode(FxRobot robot) throws Exception {
    	po.ClickSetAutomatic(robot);
    	po.ClickSetManual(robot);
    	
    	po.VerifyEquals(po.GetFloorsDisabled(robot), false);
    }
    
}