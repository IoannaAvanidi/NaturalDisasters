package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.math3.util.Pair;

import dom2app.Disaster;
import dom2app.IMeasurementVector;
import dom2app.ISingleMeasureRequest;
import dom2app.Request;
import reporter.Report;

public class Manager implements IMainController{

	List<IMeasurementVector> returnList = new ArrayList<IMeasurementVector>();
	List<Request> req = new ArrayList<Request>(); //requests 
	ISingleMeasureRequest regression;
	ISingleMeasureRequest descrStats;
	Set<String> allRequestNames;
	
	@Override
	public List<IMeasurementVector> load(String fileName, String delimiter) throws FileNotFoundException, IOException {
		
		//Line By Line Reading
		try {
			Scanner scanner = new Scanner(new File(fileName));
			
			
			//edw try catch 
			
			scanner.nextLine(); //skip first line
			
			while(scanner.hasNextLine()) {
				String Line = scanner.nextLine(); //get line from file
				String[] elemLine = Line.split(delimiter); //split line and put it in a list
				
				int year= 1980; //nitializing year
				List<Pair<Integer, Integer>> getM = new ArrayList<Pair<Integer, Integer>>(); //list of pairs <year, value>
				
				//Gia kathe hronia poses katastrofes exei
				for(int i=5; i<elemLine.length; i++) { //5 giati apo ti 5i sthlh toy arxeioy kai meta exoyme tis xronies
					if (elemLine[i].length()>0) { //an exei katastrofi
						getM.add(new Pair<Integer, Integer>(year, Integer.parseInt(elemLine[i]))); //add pair in list of pairs
						year++; //next year
					}else{
						getM.add(new Pair<Integer, Integer>(year, 0)); //add pair in list of pairs
						year++; //next year
					}
				}
				
				// in case we dont have any other element
				if (year<2023) {
					while (year<2023) {
						getM.add(new Pair<Integer, Integer>(year, 0));
						year++;
					}
				}
				
				//Vazoume tis katastrofes sto pinaka katastrofwn
				IMeasurementVector dis = new Disaster(elemLine[1], elemLine[4], getM);

				returnList.add(dis);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Delulu! Couldnt load file!");
			
		}
		//test
		/*for(int i=0; i<returnList.size(); i++){
			for (int j=0; j<returnList.get(i).getMeasurements().size(); j++) {
				System.out.println(returnList.get(i).getMeasurements().get(j));
			}
		}*/ 
		return returnList;
	}
	
	//se periptwsi pou den dvthei oloklhro to onoma tis xwras
	public boolean firstEquals(String str1, String str2) {
		if (str1.length()<str2.length()) {
			return str1.equalsIgnoreCase(str2.substring(0, str1.length()));
		}
		return false;
	}
	
	@Override
	public ISingleMeasureRequest findSingleCountryIndicator(String requestName, String countryName, String indicatorString) throws IllegalArgumentException {
		Request returnReq = new Request(requestName, indicatorString); 
		if(requestName.equals("") || countryName.equals("")) {
			System.out.println("Wrong request Type");
		}else {
			for(int i=0; i<returnList.size(); i++) { //diatrexoume ti lista apo zeugaria
				if(returnList.get(i).getCountryName().equalsIgnoreCase(countryName) || firstEquals(countryName, returnList.get(i).getCountryName()) ) { //elegxoyme to onoma tis xwras
					if(returnList.get(i).getIndicatorString().equals(indicatorString)) { //elegxoume ton tupo katastrofis
						IMeasurementVector answer = returnList.get(i); 
						returnReq.setAnswer(answer);
						
							
						req.add(returnReq);
						break; //otan vroume to zeygari
					}	
				}
			}
			ISingleMeasureRequest return_Measure = returnReq;
			return return_Measure;
		}
		return null;
	}

	@Override
	public ISingleMeasureRequest findSingleCountryIndicatorYearRange(String requestName, String countryName, String indicatorString, int startYear, int endYear) throws IllegalArgumentException {
		
		Request returnReq = new Request(requestName, indicatorString); 
		//omoiws me panw
		if(requestName.equals("") || countryName.equals("") || (startYear > endYear)) {
			System.out.println("Wrong request Type");
		}else {
			for(int i=0; i<returnList.size(); i++) {
				if(returnList.get(i).getCountryName().equalsIgnoreCase(countryName) || firstEquals(countryName, returnList.get(i).getCountryName())) {
					if(returnList.get(i).getIndicatorString().equals(indicatorString)) {
						List<Pair<Integer, Integer>> filteredList = new ArrayList<Pair<Integer, Integer>>();
							
						//make my filtered list 
						for(Pair<Integer, Integer> elemPair: returnList.get(i).getMeasurements()) {
							if(elemPair.getKey()>=startYear && elemPair.getKey()<=endYear) {
								filteredList.add(elemPair);
							}
						}
							
							//put this filtered list back
						Disaster disaster = new Disaster(returnList.get(i).getCountryName(), returnList.get(i).getIndicatorString(),  filteredList);
							
						IMeasurementVector answer = disaster;
						returnReq.setAnswer(answer);
							
						req.add(returnReq); //adding request to list of requests, use: analysis
						break;
					}
				}
			}
			ISingleMeasureRequest return_Measure = returnReq;
			return return_Measure;
		}
		return null;
	}

	@Override
	public Set<String> getAllRequestNames() {
		Set<String> returnSet = new HashSet<>();
		for(Request elem : req) {
			returnSet.add(elem.getRequestName());
		} 
		return returnSet;
	}

	@Override
	public ISingleMeasureRequest getRequestByName(String requestName) {
		ISingleMeasureRequest reqByName = null;
		for(Request elem: req) { //diatrexoume ti lista me ta aitimata
			if (elem.getRequestName().equalsIgnoreCase(requestName)) {
				reqByName=elem;
				break;
			}
		}
		return reqByName;
	}

	@Override
	public ISingleMeasureRequest getRegression(String requestName) { 
		regression = getRequestByName(requestName);
		return regression;
	}
	

	@Override
	public ISingleMeasureRequest getDescriptiveStats(String requestName) {
		descrStats = getRequestByName(requestName);
		return descrStats;
	}

	@Override
	public int reportToFile(String outputFilePath, String requestName, String reportType) throws IOException {
		Report rep = null;
		for (Request req : req) {
			 if (req.getRequestName().equalsIgnoreCase(requestName)) {
				 rep = new Report (outputFilePath, req);
				 break;
			 }
		}
		
		if (reportType.equals("text")) {
			return rep.reportTxt(outputFilePath, requestName);
		} else if (reportType.equals("html")){
			return rep.reportHtml(outputFilePath, requestName);
		} else if (reportType.equals("md")) {
			return rep.reportMd(outputFilePath, requestName);
		}
		
		return 0;
	}
	
}
