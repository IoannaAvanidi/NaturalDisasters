package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.math3.util.Pair;
import org.junit.Test;

import app.AppController;
import app.gui.jtableview.SimpleTableModel;
import dom2app.ISingleMeasureRequest;
import dom2app.Request;
import engine.Manager;
import reporter.Report;

public class ReportTests {
	
	Manager mtest = new Manager();
	Report rep ;
	
	@Test
	public void testReportFirstTxt() {
		boolean isPassed = true;

		// exeture our scenario 
		if (isPassed) {
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
			
			SimpleTableModel query2 = controller.filterSingleCountryIndicatorYearRange("FR-TOT-1990s", "France", "TOTAL", 1990, 1999); 
			
			String descrStatsResultString = controller.getDescriptiveStats("Total-of-Australia").toString();
			
			String regressionResultString = controller.getRegression("Total-of-Australia").toString();
			
			try {
				controller.createReportText("./src/test/resources/output/GeneratedFiles/test1.txt", "FR-TOT-1990s");
			}catch(Exception failToGenerateFile) {
				isPassed = false;
			}
		}
		
		
		//check if the wanted file is correct 
		
		if (isPassed) {
			
			try {
				File check_file_1 = new File("./src/test/resources/output/GeneratedFiles/test1.txt");
				File check_file_2 = new File("./src/test/resources/output/FR-TOT-1990s.txt");
			
				Scanner file1 = new Scanner(check_file_1);
				Scanner file2 = new Scanner(check_file_2);
				
				while (file1.hasNextLine()) {
					
					String s1 = file1.nextLine();
					String s2 = file2.nextLine();

					if (!(s1).equals(s2)) {
						isPassed = false;
						break;
					}
				}
				
				file1.close();
				file2.close();
				
			}catch(Exception failedToCheckFiles) {
				isPassed = false;
			}
		}
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testReportMD() {
		boolean isPassed = true;
		
		// exeture our scenario 
		if (isPassed) {
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
			
			SimpleTableModel query2 = controller.filterSingleCountryIndicatorYearRange("FR-TOT-1990s", "France", "TOTAL", 1990, 1999); 
			
			String descrStatsResultString = controller.getDescriptiveStats("Total-of-Australia").toString();
			
			String regressionResultString = controller.getRegression("Total-of-Australia").toString();
			

			try {
				controller.createReportMd("./src/test/resources/output/GeneratedFiles/test2.md", "FR-TOT-1990s");
			}catch(Exception failToGenerateFile) {
				isPassed = false;
			}
		}
		
		//check if the wanted file is correct 
		if (isPassed) {
			
			try {
				File check_file_1 = new File("./src/test/resources/output/GeneratedFiles/test2.md");
				File check_file_2 = new File("./src/test/resources/output/FR-TOT-1990s.md");
				
				Scanner file1 = new Scanner(check_file_1);
				Scanner file2 = new Scanner(check_file_2);
				
				while (file1.hasNextLine()) {
					
					String s1 = file1.nextLine();
					String s2 = file2.nextLine();
					
					if (!(s1).equals(s2)) {
						isPassed = false;
						break;
					}
				}
				file1.close();
				file2.close();
				
			}catch(Exception failedToCheckFiles) {
				isPassed = false;
			}
		}
		
		assertEquals(isPassed,true);
	}
	
	@Test
	public void testReportHTML() {
		boolean isPassed = true;
		
		
		// exeture our scenario 
		if (isPassed) {
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
					
			SimpleTableModel query2 = controller.filterSingleCountryIndicatorYearRange("FR-TOT-1990s", "France", "TOTAL", 1990, 1999); 
					
			String descrStatsResultString = controller.getDescriptiveStats("Total-of-Australia").toString();
					
			String regressionResultString = controller.getRegression("Total-of-Australia").toString();
					

			try {
				controller.createReportHtml("./src/test/resources/output/GeneratedFiles/test3.html", "FR-TOT-1990s");
			}catch(Exception failToGenerateFile) {
				isPassed = false;
			}
		}
		//check if the wanted file is correct 
		if (isPassed) {
			try {
				File check_file_1 = new File("./src/test/resources/output/GeneratedFiles/test3.html");
				File check_file_2 = new File("./src/test/resources/output/FR-TOT-1990s.html");
						
				Scanner file1 = new Scanner(check_file_1);
				Scanner file2 = new Scanner(check_file_2);
						
				while (file1.hasNextLine()) {
					String s1 = file1.nextLine();
					String s2 = file2.nextLine();
							
					if (!(s1).equals(s2)) {
						isPassed = false;
						break;
					}
							
				}
						file1.close();
						file2.close();
						
					}catch(Exception failedToCheckFiles) {
						isPassed = false;
					}
				}
		
		assertEquals(isPassed,true);
	}

}
