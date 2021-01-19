package at.fhhagenberg.sqe.pageobject;

import java.util.concurrent.CountDownLatch;

import org.testfx.api.FxRobot;

import javafx.application.Platform;

public class AppPO {

	public AppPO() {
		
	}
	
	/* IDs of UI Elements */
	
	public String GetCurrentFloorLabel() {
		return "#lbFloor";
	}
	
	public String GetDownImage() {
		return "#imgElevDown";
	}
	
	public String GetUpImage() {
		return "#imgElevUp";
	}
	
	public String GetPayloadLabel() {
		return "#lbPayload";
	}
	
	public String GetSpeedLabel() {
		return "#lbSpeed";
	}
	
	public String GetDoorsLabel() {
		return "#lbDoors";
	}
	
	public String GetTargetLabel() {
		return "#lbTarget";
	}
	
	public String GetErrorListView() {
		return "#lvErrors"; 
	}
	
	public String GetFloorListView() {
		return "#lvFloors"; 
	}
	
	public String GetManualRB() {
		return "#rbManual";
	}
	
	public String GetAutomaticRB() {
		return "#rbAuto";
	}

	public String GetFloorNumberLabel() {
		return "#floorNumber";
	}
	
	public String GetElevatorCombobox() {
		return "#cmbElevators";
	}

	/* Actions */
	
	public void ClickSetTarget(FxRobot robot) {
		// just take first button
		robot.clickOn(".button");
	}
	
	/* Assertions */
	
	
	
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
