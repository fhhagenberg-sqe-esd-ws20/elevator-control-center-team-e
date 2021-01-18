package at.fhhagenberg.sqe.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControllerDataTest {
	
	private ControllerData cd;

	@BeforeEach
	void setUp() throws Exception {
		cd = new ControllerData();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetElevatorNumbers() {
		var res = cd.getElevatorNumbers();
		assertEquals(0, res);
	}

	@Test
	void testGetFloorHeight() {
		var res = cd.getFloorHeight();
		assertEquals(0, res);
	}

	@Test
	void testGetFloorNumber() {
		var res = cd.getFloorNumber();
		assertEquals(0, res);
	}

	@Test
	void testGetCurrentElevator() {
		var res = cd.getCurrentElevator();
		assertEquals(0, res);
	}

	@Test
	void testGetButtons() {
		var res = cd.getButtons();
		assertEquals(true, res.isEmpty());
	}

	@Test
	void testGetIsManualMode() {
		var res = cd.getIsManualMode();
		assertEquals(false, res);
	}

	@Test
	void testGetCommittedDirection() {
		var res = cd.getCommittedDirection();
		assertEquals(0, res);
	}

	@Test
	void testGetElevatorAccel() {
		var res = cd.getElevatorAccel();
		assertEquals(0, res);
	}

	@Test
	void testGetElevatorDoorStatus() {
		var res = cd.getElevatorDoorStatus();
		assertEquals(0, res);
	}

	@Test
	void testGetElevatorFloor() {
		var res = cd.getElevatorFloor();
		assertEquals(0, res);
	}

	@Test
	void testGetElevatorPosition() {
		var res = cd.getElevatorPosition();
		assertEquals(0, res);
	}

	@Test
	void testGetElevatorSpeed() {
		var res = cd.getElevatorSpeed();
		assertEquals(0, res);
	}

	@Test
	void testGetElevatorWeight() {
		var res = cd.getElevatorWeight();
		assertEquals(0, res);
	}

	@Test
	void testGetElevatorCapacity() {
		var res = cd.getElevatorCapacity();
		assertEquals(0, res);
	}

	@Test
	void testGetElevatorTarget() {
		var res = cd.getElevatorTarget();
		assertEquals(0, res);
	}

}
