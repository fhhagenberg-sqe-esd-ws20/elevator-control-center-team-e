package at.fhhagenberg.sqe.pageobject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;

import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.util.Pair;

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
	
	public Object GetFirstError(FxRobot robot) {
		return robot.lookup(GetErrorListView()).queryAs(ListView.class).getItems().get(0);
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
	
	public void VerifyEquals(Object actual, Object expected) throws InterruptedException {
		assertAfterJavaFxPlatformEventsAreDone(() -> {
			assertEquals(expected, actual);
		});
	}
	
	public void VerifyLabel(String elementId, String value) throws InterruptedException {
		assertAfterJavaFxPlatformEventsAreDone(() -> {
			FxAssert.verifyThat(elementId, LabeledMatchers.hasText(value));
		});
	}
	
	public void VerifyLabels(Pair<String, String>... pairs) throws InterruptedException {
		assertAfterJavaFxPlatformEventsAreDone(() -> {
			for(var pair : pairs) {
				FxAssert.verifyThat(pair.getKey(), LabeledMatchers.hasText(pair.getValue()));
			}
		});
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
