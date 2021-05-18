package hu.bme.mit.train.controller;

import java.util.Timer;
import java.util.TimerTask;

import hu.bme.mit.train.interfaces.TrainController;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private boolean emergencyStopped = false;
	private Timer timer = new Timer(true);
	
	
	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			followSpeed();
		}
	};
	
	public TrainControllerImpl() {
		timer.schedule(task, 0L, 1000L);
	}
	

	@Override
	public void followSpeed() {
		if (emergencyStopped) {
			referenceSpeed = 0;
			return;
		}
		
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed+step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}
		
		enforceSpeedLimit();
	}
	
	
	
	public void emergencyBraking() {
		emergencyStopped = true;
	}
	
	public void releaseBrake() {
		emergencyStopped = false;
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}
	

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;	
	}
	
	

}
