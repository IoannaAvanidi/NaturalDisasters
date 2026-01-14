package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import dom2app.Disaster;
import dom2app.Request;

public class RequestTests {

	@Test
	public void testRequestgetRequestName() {
		boolean isPassed = true;
		
		Request check_request = new Request("Greece-Fire", "wildFire");
		
		if (!(check_request.getRequestName().equals("Greece-Fire"))) {
			isPassed = false;
		}
		
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testRequestgetRequestFilter() {
		boolean isPassed = true;
		
		Request check_request = new Request("Greece-Fire", "wildFire");
		
		if (!(check_request.getRequestFilter().equals("wildFire"))) {
			isPassed = false;
		}
		
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testRequestIsAnsweredFlag() {
		boolean isPassed = true;
		
		Request check_request = new Request("Greece-Fire", "wildFire");
		Disaster check_disaster = new Disaster("Greece", "wildFire", null);
		check_request.setAnswer(check_disaster);
		
		if (!(check_request.isAnsweredFlag())) {
			isPassed = false;
		}
		
		assertEquals(isPassed,true);
	}

}


