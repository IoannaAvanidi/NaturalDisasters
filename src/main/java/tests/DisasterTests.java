package tests;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.junit.Test;

import app.AppController;
import app.gui.jtableview.SimpleTableModel;
import dom2app.Disaster;
import dom2app.IMeasurementVector;
import dom2app.ISingleMeasureRequest;
import dom2app.Request;
import engine.Manager;

public class DisasterTests {
	
	Manager mtest = new Manager();
	
	@Test
	public void testDistasterGetCountryName() throws FileNotFoundException, IOException {
		boolean isPassed = true;
		
		
		Disaster check_disaster = new Disaster("Greece", "wildFire", null);
		
		if (!(check_disaster.getCountryName().equals("Greece"))) {
			isPassed = false;
		}
		
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testDisasterGetIndicatorString() {
		
		boolean isPassed = true;
		
		Disaster check_disaster = new Disaster("Greece", "wildFire", null);
		
		if (!(check_disaster.getIndicatorString().equals("wildFire"))) {
			isPassed = false;
		}
		
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testDisasterGetMeasurements() {
		boolean isPassed = true;
		
		//prepare data 
		List<Pair<Integer, Integer>>  given_pairs = new ArrayList<Pair<Integer,Integer>>();
		
		given_pairs.add(new Pair<Integer,Integer>(1980,3));
		given_pairs.add(new Pair<Integer,Integer>(2000,5));
		given_pairs.add(new Pair<Integer,Integer>(2004,8));
		
		Disaster check_disaster = new Disaster("Greece", "wildFire", given_pairs);
		
		//check if get the same 
		if(!(check_disaster.getMeasurements().get(0) == given_pairs.get(0))) {
			isPassed = false;
		}
		
		if (isPassed) {
			if(!(check_disaster.getMeasurements().get(1) == given_pairs.get(1))) {
				isPassed = false;
			}
		}
		
		if (isPassed) {
			if(!(check_disaster.getMeasurements().get(2) == given_pairs.get(2))) {
				isPassed = false;
			}
		}
		
		assertEquals(isPassed,true);
	}

}
