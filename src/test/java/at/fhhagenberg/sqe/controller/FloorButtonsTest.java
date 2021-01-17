package at.fhhagenberg.sqe.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.event.ActionEvent;

class FloorButtonsTest {
	
	private FloorButtons fb;

	@BeforeEach
	void setUp() throws Exception {
		fb = new FloorButtons(null, 10, true, false, true, true);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetFloorNr() {
		var ret = fb.getFloorNr();
		assertEquals(10, ret);
	}

	@Test
	void testGetFloorButtonDown() {
		var ret = fb.getFloorButtonDown();
		assertEquals(true, ret);
	}

	@Test
	void testGetFloorButtonUp() {
		var ret = fb.getFloorButtonUp();
		assertEquals(false, ret);
	}

	@Test
	void testGetElevatorButton() {
		var ret = fb.getElevatorButton();
		assertEquals(true, ret);
	}

	@Test
	void testGetElevatorServicesFloor() {
		var ret = fb.getElevatorServicesFloor();
		assertEquals(true, ret);
	}

	@Test
	void testGetIsCurrentFloor() {
		var ret = fb.getIsCurrentFloor();
		assertEquals(false, ret);
	}

	@Test
	void testDoSetTarget() {
		fb.doSetTarget(new ActionEvent());
		assertTrue(true);
	}

}
