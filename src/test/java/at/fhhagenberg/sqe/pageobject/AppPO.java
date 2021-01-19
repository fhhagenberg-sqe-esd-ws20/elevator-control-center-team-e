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
	
	/**
	 * @return CSS id of Current Floor
	 */
	public String GetCurrentFloorLabel() {
		return "#lbFloor";
	}
	
	/**
	 * @return CSS id of Down Image
	 */
	public String GetDownImage() {
		return "#imgElevDown";
	}
	
	/**
	 * @return CSS id of Up Image
	 */
	public String GetUpImage() {
		return "#imgElevUp";
	}
	
	/**
	 * @return CSS id of Payload
	 */
	public String GetPayloadLabel() {
		return "#lbPayload";
	}
	
	/**
	 * @return CSS id of Speed
	 */
	public String GetSpeedLabel() {
		return "#lbSpeed";
	}
	
	/**
	 * @return CSS id of Door Status
	 */
	public String GetDoorsLabel() {
		return "#lbDoors";
	}
	
	/**
	 * @return CSS id of next Target
	 */
	public String GetTargetLabel() {
		return "#lbTarget";
	}
	
	/**
	 * @return CSS id of Error ListView
	 */
	public String GetErrorListView() {
		return "#lvErrors"; 
	}
	
	/**
	 * @param robot - FxRobot of test
	 * @return First Object in Error LV
	 */
	public Object GetFirstError(FxRobot robot) {
		return robot.lookup(GetErrorListView()).queryAs(ListView.class).getItems().get(0);
	}
	
	/**
	 * @return CSS id of Floors ListView
	 */
	public String GetFloorListView() {
		return "#lvFloors"; 
	}
	
	/**
	 * @return CSS id of Manual Mode RB
	 */
	public String GetManualRB() {
		return "#rbManual";
	}
	
	/**
	 * @return CSS id of Automatic Mode RB
	 */
	public String GetAutomaticRB() {
		return "#rbAuto";
	}

	/**
	 * @return CSS id of Floor Number
	 */
	public String GetFloorNumberLabel() {
		return "#floorNumber";
	}
	
	/**
	 * @return CSS id of Elevator Combobox
	 */
	public String GetElevatorCombobox() {
		return "#cmbElevators";
	}
	
	

	/* Actions */
	
	/**
	 * Click on a "set target" button
	 * @param robot - FxRobot of test
	 */
	public void ClickSetTarget(FxRobot robot) {
		// just take first button
		robot.clickOn(".button");
	}
	
	
	
	/* Assertions */
	
	/**
	 * @param actual - Object to do assertion on
	 * @param expected - expected value
	 * @throws InterruptedException
	 */
	public void VerifyEquals(Object actual, Object expected) throws InterruptedException {
		assertAfterJavaFxPlatformEventsAreDone(() -> {
			assertEquals(expected, actual);
		});
	}
	
	/**
	 * @param elementId - CSS id of element
	 * @param value - expected value
	 * @throws InterruptedException
	 */
	public void VerifyLabel(String elementId, String value) throws InterruptedException {
		assertAfterJavaFxPlatformEventsAreDone(() -> {
			FxAssert.verifyThat(elementId, LabeledMatchers.hasText(value));
		});
	}
	
	/**
	 * @param pairs - Pairs of CSS id of element and expected value
	 * @throws InterruptedException
	 */
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
