package hu.bme.mit.train.system;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	Tachograph tachograph;
	
	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();
		tachograph = system.getTachograph();

		sensor.overrideSpeedLimit(50);
	}
	
	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		sensor.overrideSpeedLimit(10);
	
		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToNegative_SetsReferenceSpeedToZero() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}
	
	@Test
	public void OverridingJoystickPosition_IncreasingSpeedAboveLimit() {
			sensor.overrideSpeedLimit(20);			
			user.overrideJoystickPosition(10);
			controller.followSpeed();
			Assert.assertEquals(10, controller.getReferenceSpeed());
			user.overrideJoystickPosition(15);
			controller.followSpeed();
			Assert.assertEquals(20, controller.getReferenceSpeed());			
	}
	
	@Test
	public void TachographCollectionNotEmpty() {
		tachograph.addDataToTable(10, 20);
		tachograph.addDataToTable(20, 45);
		tachograph.addDataToTable(12, 20);
		Assert.assertEquals(3, tachograph.tachographData.size());
	}

	
}
