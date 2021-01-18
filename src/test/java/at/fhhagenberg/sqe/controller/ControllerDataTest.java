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
		cd.elevatorNumbers.set(1);
		var res = cd.getElevatorNumbers();
		assertEquals(1, res);
	}

	@Test
	void testGetFloorHeight() {
		cd.floorHeight.set(1);
		var res = cd.getFloorHeight();
		assertEquals(1, res);
	}

	@Test
	void testGetFloorNumber() {
		cd.floorNumber.set(1);
		var res = cd.getFloorNumber();
		assertEquals(1, res);
	}

	@Test
	void testGetCurrentElevator() {
		cd.currentElevator.set(1);
		var res = cd.getCurrentElevator();
		assertEquals(1, res);
	}

	@Test
	void testGetButtons() {
		var res = cd.getButtons();
		assertEquals(true, res.isEmpty());
	}

	@Test
	void testGetIsManualMode() {
		cd.isManualMode.set(true);
		var res = cd.getIsManualMode();
		assertEquals(true, res);
	}

	@Test
	void testGetCommittedDirection() {
		cd.committedDirection.set(1);
		var res = cd.getCommittedDirection();
		assertEquals(1, res);
	}

	@Test
	void testGetElevatorAccel() {
		cd.elevatorAccel.set(1);
		var res = cd.getElevatorAccel();
		assertEquals(1, res);
	}

	@Test
	void testGetElevatorDoorStatus() {
		cd.elevatorDoorStatus.set(1);
		var res = cd.getElevatorDoorStatus();
		assertEquals(1, res);
	}

	@Test
	void testGetElevatorFloor() {
		cd.elevatorFloor.set(1);
		var res = cd.getElevatorFloor();
		assertEquals(1, res);
	}

	@Test
	void testGetElevatorPosition() {
		cd.elevatorPosition.set(1);
		var res = cd.getElevatorPosition();
		assertEquals(1, res);
	}

	@Test
	void testGetElevatorSpeed() {
		cd.elevatorSpeed.set(1);
		var res = cd.getElevatorSpeed();
		assertEquals(1, res);
	}

	@Test
	void testGetElevatorWeight() {
		cd.elevatorWeight.set(1);
		var res = cd.getElevatorWeight();
		assertEquals(1, res);
	}

	@Test
	void testGetElevatorCapacity() {
		cd.elevatorCapacity.set(1);
		var res = cd.getElevatorCapacity();
		assertEquals(1, res);
	}

	@Test
	void testGetElevatorTarget() {
		cd.elevatorTarget.set(1);
		var res = cd.getElevatorTarget();
		assertEquals(1, res);
	}

}
