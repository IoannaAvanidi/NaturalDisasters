package tests;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import app.AppController;
import app.gui.jtableview.SimpleTableModel;
import dom2app.IMeasurementVector;
import dom2app.ISingleMeasureRequest;
import engine.Manager;

public class ManagerTests {
	
	Manager mtest = new Manager();
	@Test
	public void testManagerLoadClimateSuccess() {
		
		String filename = "src/main/resources/InputData/ClimateRelatedDisasters.tsv";
		AppController controller = new AppController();
		
		boolean loadedOK = false;
		try {
			loadedOK = controller.load(filename, "\t");
		} catch (FileNotFoundException e) {
			System.err.println("File not found for: " + filename);
			return;
		} catch (IOException e) {
			System.err.println("Buffered reader readline() failed for: " + filename);
			return;
		}
	
		assertEquals(loadedOK,true);	
	}
	
	
	@Test
	public void testManagerLoadClimateFull() {
		
		boolean isPassed = true;
		boolean isNotEmpty = false; //check if empty our disasters 
		
		try {
			//get data from our file 
			List<IMeasurementVector> check_List = mtest.load("./src/main/java/tests/_ClimateRelatedDisastersFull.tsv","\t");
			
			//check if we don't get nothing from file 
			if (check_List.size() != 0) {
				isNotEmpty = false;
			}	
		}catch(Exception fail_load_mathod) {
			isPassed = false;
		}		
	
		assertEquals(isPassed,true);	
	}
	
	
	@Test
	public void testManagerFindSingleCountryIndicator() {
		
		boolean isPassed = true;
		
		
		try {
			List<IMeasurementVector> checkRequests = mtest.load("./src/main/java/tests/_ClimateRelatedDisastersFull.tsv", "\t");
			
			ISingleMeasureRequest check_request = mtest.findSingleCountryIndicator("REQUEST_TEST", "Greece", "Wildfire");
			
			
			
			//check if we have same name with given request name 
			if(!(check_request.getRequestName().equals("REQUEST_TEST"))) {
				isPassed = false;
			}

			if(isPassed) {
				
				if(!(check_request.getRequestFilter().equals("Wildfire"))) {
					isPassed = false;
				}
				
				if(isPassed) {
					if (!(check_request.getAnswer().getCountryName().equals("Greece")) || (check_request.getAnswer() == null)) {
						isPassed = false;
					}
				}
				
			}
			
			
			
		}catch(Exception cant_load_file) {
			
			isPassed = false;
		}
		
		//check if passed our test 
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testManagerFindSingleCountryIndicatoNoCountry() {
		
		boolean isPassed = true;
		
		
		try {
			List<IMeasurementVector> checkRequests = mtest.load("./src/main/java/tests/_ClimateRelatedDisastersFull.tsv", "\t");
			
			
			//fake country no input
			ISingleMeasureRequest check_request = mtest.findSingleCountryIndicator("REQUEST_TEST", "SalahAbdul", "Wildfire");
			
			
			//check if we have same name with given request name 
			if(!(check_request.getRequestName().equals("REQUEST_TEST"))) {
				isPassed = false;
			}
			
			if(isPassed) {
				
				if(!(check_request.getRequestFilter().equals("Wildfire"))) {
					isPassed = false;
				}
				
				if(isPassed) {
					if (check_request.getAnswer() != null) {
						isPassed = false;
					}
				}
				
			}	
		}catch(Exception cant_load_file) {
			
			isPassed = false;
		}
		
		//check if passed our test 
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testManagerFindSingleCountryIndicatorYearRange() {
	
		boolean isPassed = true;
		
		int set_first_year = 2000;
		int set_second_year = 2010;
		
		
		try {
			List<IMeasurementVector> checkRequests = mtest.load("./src/main/java/tests/_ClimateRelatedDisastersFull.tsv", "\t");
			
			ISingleMeasureRequest check_request = mtest.findSingleCountryIndicatorYearRange("REQUEST_TEST", "Greece", "Wildfire",set_first_year,set_second_year);
			
			
			
			//check if we have same name with given request name 
			if(!(check_request.getRequestName().equals("REQUEST_TEST"))) {
				isPassed = false;
			}

			//test if created 
			if(isPassed) {
				if(!(check_request.getRequestFilter().equals("Wildfire"))) {
					isPassed = false;
				}
			}
			
		
			if(isPassed) {
				if (!(check_request.getAnswer().getCountryName().equals("Greece")) || (check_request.getAnswer() == null)) {
					isPassed = false;
				}	
			}	
				
			if (isPassed) {
				if (check_request.getAnswer().getMeasurements().size() != 11) {
					isPassed = false;
				}
			}
						
			
		}catch(Exception cant_load_file) {
			cant_load_file.printStackTrace();
			isPassed = false;
		}
		
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testFindSingleCountryIndicatorYearRangeGreaterEndYear() {
	
		boolean isPassed = true;
		
		int set_first_year = 2010;
		int set_second_year = 2000;
		
		
		try {
			List<IMeasurementVector> checkRequests = mtest.load("./src/main/java/tests/_ClimateRelatedDisastersFull.tsv", "\t");
			
			ISingleMeasureRequest check_request = mtest.findSingleCountryIndicatorYearRange("REQUEST_TEST", "Greece", "Wildfire",set_first_year,set_second_year);
			
			// we must get null
			if (check_request != null) {
				isPassed = false;
			}
						
			
		}catch(Exception cant_load_file) {
			cant_load_file.printStackTrace();
			isPassed = false;
		}
		
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testManagergetAllRequestNames() {
		boolean isPassed = true;
		
		try {
			List<IMeasurementVector> checkRequests = mtest.load("./src/main/java/tests/_ClimateRelatedDisastersFull.tsv", "\t");
			
			List<String> check_req_names = new ArrayList<String>();
			
			//create first request 
			
			ISingleMeasureRequest req1 = mtest.findSingleCountryIndicator("REQUEST_TEST", "Greece", "Wildfire");
			
			check_req_names.add(req1.getRequestName());
			
			
			//create second request
			ISingleMeasureRequest req2 = mtest.findSingleCountryIndicatorYearRange("REQUEST_TEST_1", "Greece", "Wildfire",2000,2010);
			
			check_req_names.add(req2.getRequestName());
			
			
			
			//now we are checking the return set with the request we did 
			
			Set<String> checkRequestNames = mtest.getAllRequestNames();
			
			
			//check if same
			int index = 0;
			for (String ch: checkRequestNames) {
				if(!(ch.equalsIgnoreCase(check_req_names.get(index)))) {
					isPassed = false;
					break;
				}
				index++;
			}
			
		}catch(Exception fail_to_pass_the_test) {
			
			fail_to_pass_the_test.printStackTrace();
			
		}
		
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testManagerGetRequestByName() {
		boolean isPassed = true;
		
		try {
			List<IMeasurementVector> checkRequests = mtest.load("./src/main/java/tests/_ClimateRelatedDisastersFull.tsv", "\t");
			
			List<String> check_req_names = new ArrayList<String>();
			
			//create first request 
			
			ISingleMeasureRequest req1 = mtest.findSingleCountryIndicator("REQUEST_TEST", "Greece", "Wildfire");
			
			check_req_names.add(req1.getRequestName());
			
			
			//create second request
			ISingleMeasureRequest req2 = mtest.findSingleCountryIndicatorYearRange("REQUEST_TEST_1", "Greece", "Wildfire",2000,2010);
			
			check_req_names.add(req2.getRequestName());
			
			ISingleMeasureRequest check_req = mtest.getRequestByName("REQUEST_TEST");
			
			if(!(check_req.getRequestName().equals("REQUEST_TEST"))){
				isPassed = false;
			}
			
		}catch(Exception fail_to_pass_the_test) {
			
			fail_to_pass_the_test.printStackTrace();
		}
		
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testManagerGetRegression() {
		
		boolean isPassed = true;
		
		//prepare data 
		String originalRegression = "\n\nRegression\n------------------------" 
				+ "\nIntercept:\t" + "-113.5225007550589" + "\nSlope:\t" + "0.058894593778314706" + "\nSlope Error:\t " + "0.028753817286978643" + "\nTrend:\t " + "Tendency stable";
	
		//antikeimeno 
		String filename = "src/main/resources/InputData/ClimateRelatedDisasters.tsv";
		AppController controller = new AppController();
		
		boolean loadedOK = false;
		try {
			loadedOK = controller.load(filename, "\t");
		} catch (FileNotFoundException e) {
			System.err.println("File not found for: " + filename);
			return;
		} catch (IOException e) {
			System.err.println("Buffered reader readline() failed for: " + filename);
			return;
		}
		
		SimpleTableModel query1 = controller.filterSingleCountryIndicator("Total-of-Australia", "Austra", "TOTAL"); 
		
		String regressionResultString = controller.getRegression("Total-of-Australia").toString();
		
		if (!(originalRegression.equals(regressionResultString))) {
			isPassed = false;
		}
		
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testManagerGetDescriptiveStats() {
		boolean isPassed = true;
		//prepare data 
		String originalRegression = "Descriptive stats Result"
				+ "\nCount:\t" + "43" + "\nMin:\t" + "0.0" + "\ngMean:\t" + "0.0" + "\nMean:\t" + "4.325581395348837" + 
				"\nMedian:\t" + "4.0" + "\nMax:\t" + "10.0" + "\nkurtosis:\t" + "-0.16459866852625948" + "\nstdev:\t" + "2.427235643322884" + "\nsum:\t" + "186.0";
	
		
		//antikeimeno 
		String filename = "src/main/resources/InputData/ClimateRelatedDisasters.tsv";
		AppController controller = new AppController();
		
		boolean loadedOK = false;
		try {
			loadedOK = controller.load(filename, "\t");
		} catch (FileNotFoundException e) {
			System.err.println("File not found for: " + filename);
			return;
		} catch (IOException e) {
			System.err.println("Buffered reader readline() failed for: " + filename);
			return;
		}
		
		
		SimpleTableModel query1 = controller.filterSingleCountryIndicator("Total-of-Australia", "Austra", "TOTAL"); 
		
		String descrStatsResultString = controller.getDescriptiveStats("Total-of-Australia").toString();
		
		if (!(originalRegression.equals(descrStatsResultString))) {
			isPassed = false;
		}
		
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testManagerReportToFile() throws IOException {
		boolean isPassed = true;
		
		String filename = "src/main/resources/InputData/ClimateRelatedDisasters.tsv";
		AppController controller = new AppController();
		
		boolean loadedOK = false;
		try {
			loadedOK = controller.load(filename, "\t");
		} catch (FileNotFoundException e) {
			System.err.println("File not found for: " + filename);
			return;
		} catch (IOException e) {
			System.err.println("Buffered reader readline() failed for: " + filename);
			return;
		}
		System.out.println("Loading status: " + loadedOK);
		
		SimpleTableModel query1 = controller.filterSingleCountryIndicator("Total-of-Australia", "Austra", "TOTAL"); 
		
		SimpleTableModel query2 = controller.filterSingleCountryIndicatorYearRange("Total-of-Australia_10-18", "Austra", "TOTAL", 2010, 2018); 
		
		SimpleTableModel query3 = controller.filterSingleCountryIndicatorYearRange("Total-of-Greece_10-18", "GRE", "TOTAL", 2010, 2018); 
		
		SimpleTableModel query4 = controller.filterSingleCountryIndicatorYearRange("", "GRE", "TOTAL", 2010, 2018); 
		
		SimpleTableModel query5 = controller.filterSingleCountryIndicatorYearRange("GR-TOT_90s", "", "TOTAL", 0, 0); 
		
		String descrStatsResultString = controller.getDescriptiveStats("Total-of-Australia").toString();
		
		String regressionResultString = controller.getRegression("Total-of-Australia").toString();
		
		int check_lines = controller.createReportText("./src/test/resources/output/GeneratedFiles/test1.txt", "Total-of-Australia");

		if (check_lines == 0){
			isPassed = false;
		}
		
		
		assertEquals(isPassed,true);
	}

}
