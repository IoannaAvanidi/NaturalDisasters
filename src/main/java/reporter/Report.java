package reporter;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.math3.util.Pair;

import dom2app.Request;

public class Report {
	
	String outputFileName;
	Request req;
	
	public Report(String outputFileName, Request req) {
		this.outputFileName = outputFileName;
		this.req=req;
	}
	
	int counter=0;
	public int reportTxt (String outputFileName, String requestName) {
		try {
			FileWriter wr = new FileWriter(outputFileName);
			
			wr.write(req.getRequestName() + "\n");
			counter++;
			
			wr.write("Country ~ " + req.getAnswer().getCountryName() + " Indicator: " + req.getRequestFilter() + "\n");
			counter++;
			
			wr.write("Year" +"\t" + "Value" +"\t\n");
			counter++;
			
			for (Pair<Integer, Integer> elemPair : req.getAnswer().getMeasurements()) {
				wr.write(elemPair.getKey() + "\t" +elemPair.getValue() + "\n");
				counter++;
			}
			
			wr.write(req.getAnswer().getDescriptiveStatsAsString() + "]");
			counter++;
			
			wr.write(req.getAnswer().getRegressionResultAsString()+"]");
			counter++;
			
			wr.close();
			
		}catch(IOException e){
			return 0;
		}
		return counter;
	}
	
	int counter2=0;
	public int reportHtml (String outputFileName, String requestName) {
		try {
			FileWriter wr = new FileWriter(outputFileName);
			
			wr.write("<!doctype html>" + "\n");
			wr.write("<html>\n" );
			wr.write("<head>\n");
			wr.write("<meta http-equiv=\"Content-Type\" content\"text/html; charset=windows-1253\">\n");
			wr.write("<title>Natural Disaster Data</title>\n");
			wr.write("</head>\n");
			wr.write("<body>\n\n");
			counter2+=7;
			
			wr.write("<p><b>"+req.getRequestName() + "</b></p>" + "\n");
			
			wr.write("<p><i>Country ~ " + req.getAnswer().getCountryName() + " Indicator: " + req.getRequestFilter() + "</i></p>" + "\n");
			
			wr.write("<table>" + "\n");
			wr.write("<tr>" + "\n");
			
			wr.write("<td>Year</td>\t<td>Value</td>\t</tr>" +"\n\n");
			counter2+=5;
			
			for (Pair<Integer, Integer> elemPair : req.getAnswer().getMeasurements()) {
				wr.write("<tr>" + "\n");
				wr.write("<td>" + elemPair.getKey() + "</td>" + "\t" + "<td>" +elemPair.getValue() + "</td>" +"\n");
				wr.write("</tr>" + "\n");
				counter2+=3;
			}
			
			wr.write("</table><p>" + req.getAnswer().getDescriptiveStatsAsString()+ "<p>" + req.getAnswer().getRegressionResultAsString().substring(1,req.getAnswer().getRegressionResultAsString().length()) + "</body>\n");

			wr.write("</html>");
			
			counter2+=4;
			
			wr.close();
			
		}catch(IOException e){
			return 0;
		}
		return counter2;
	}
	
	int counter3=0;
	public int reportMd(String outputFileName, String requestName) {
		
		try {
			FileWriter wr = new FileWriter(outputFileName);
			
			wr.write("**"+req.getRequestName() + "**" +"\n");
			counter3++;
			
			wr.write("\n");
			
			wr.write("_Country ~ " + req.getAnswer().getCountryName() + " Indicator: " + req.getRequestFilter() + "_" +"\n\n");
			counter3++;
			
			wr.write("|*Year*|*Value*|" +"\n");
			wr.write("|----|----|" +"\n");
			counter3+=2;
			
			for (Pair<Integer, Integer> elemPair : req.getAnswer().getMeasurements()) {
				wr.write("|"+elemPair.getKey()  +" |" +elemPair.getValue() + "|" +"\n");
				counter3++;
			}
			
			wr.write("\n");
			counter3++;
			
			wr.write(req.getAnswer().getDescriptiveStatsAsString()+"]");
			counter3++;
			
			wr.write("\n");
			
			wr.write(req.getAnswer().getRegressionResultAsString()+"]");
			counter3++;
			
			wr.close();
			
		}catch(IOException e){
			return 0;
		}
		return counter3;
	}
}
