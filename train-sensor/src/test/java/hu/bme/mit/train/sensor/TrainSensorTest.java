package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import static org.mockito.Mockito.*;

public class TrainSensorTest {
	
	TrainUser mockUser;
	TrainController mockController;
	TrainSensorImpl sensor;

    @Before
    public void before() {
        mockUser = mock(TrainUser.class);
        mockController = mock(TrainController.class);
        sensor = new TrainSensorImpl(mockController, mockUser);
    }

    @Test
    public void overrideSpeedLimitOver500() {
        sensor.overrideSpeedLimit(501);
        verify(mockUser, times(1)).setAlarmState(true);
    }
    
    @Test
    public void overrideSpeedLimitBelow0() {
    	sensor.overrideSpeedLimit(-1);
        verify(mockUser, times(1)).setAlarmState(true);
    }
    
    @Test
    public void overrideSpeedLimitBelowHalf() {
    	when(mockController.getReferenceSpeed()).thenReturn(150);
    	sensor.overrideSpeedLimit(50);       
        verify(mockUser, times(1)).setAlarmState(true);
    }
    
    @Test
    public void overrideSpeedLimitAboveHalf() {
    	when(mockController.getReferenceSpeed()).thenReturn(60);
    	sensor.overrideSpeedLimit(50);       
        verify(mockUser, times(0)).setAlarmState(true);       
    }
}
