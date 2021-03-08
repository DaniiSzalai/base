package hu.bme.mit.train.system;

import java.util.Date;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class Tachograph {
	Table<Date, Integer, Integer> tachographData;
	
	public Tachograph() {
		tachographData = HashBasedTable.create();
	}
		
	public void addDataToTable(Integer joystick_position, Integer speed) {
		tachographData.put(new Date(System.currentTimeMillis()), joystick_position, speed);
	}
	
}
