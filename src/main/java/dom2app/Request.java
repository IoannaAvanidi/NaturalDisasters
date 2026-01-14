package dom2app;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.util.Pair;

public class Request implements ISingleMeasureRequest {

	String requestName;
	String requestFilter;
	boolean answeredFlag;
	IMeasurementVector answer; //typou disaster
	String descriptiveStatsString;
	String regressionResultString;
	
	public Request(String requestName, String requestFilter) {
		this.requestName=requestName;
		this.requestFilter=requestFilter;
	}
	
	@Override
	public String getRequestName() {
		return requestName;
	}

	@Override
	public String getRequestFilter() {
		return requestFilter;
	}

	@Override
	public boolean isAnsweredFlag() {
		if (answer != null) {
			return true;
		}
		return false;
	}

	@Override
	public IMeasurementVector getAnswer() {
		return answer;
	}
	
	public String getLabel(double slope) {
		if (Double.isNaN(slope)) {
			return "Tendency Undefined";
		} else if (slope > 0.1) {
			return "Increased Tendency";
		}else if (slope < -0.1) {
			return "Decreased Tendency";
		}return "Tendency stable";
	}

	//Report
	@Override
	public String getDescriptiveStatsString() {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for(Pair<Integer, Integer> xyPair: answer.getMeasurements()) {
			int value = xyPair.getSecond();
			stats.addValue(value);
		}

		long count = stats.getN();
		double minD = stats.getMin();
		double gMean = stats.getGeometricMean();
		double mean = stats.getMean();
		double medianD = stats.getPercentile(50);
		double maxD = stats.getMax();
		double kurtosis = stats.getKurtosis();
		double stdev = stats.getStandardDeviation();
		double sumD = stats.getSum();
		
		descriptiveStatsString=("Descriptive stats Result"
				+ "\nCount:\t" + count + "\nMin:\t" + minD + "\ngMean:\t" + gMean + "\nMean:\t" + mean + 
				"\nMedian:\t" + medianD + "\nMax:\t" + maxD + "\nkurtosis:\t" + kurtosis + "\nstdev:\t" + stdev + "\nsum:\t" + sumD);
		
		return descriptiveStatsString;
	}
	
	//Report
	@Override
	public String getRegressionResultString() {
		SimpleRegression regression = new SimpleRegression();
		for(Pair<Integer, Integer> xyPair: answer.getMeasurements()) {
			int year = xyPair.getFirst();
			int value = xyPair.getSecond();
			regression.addData(year, value);
		}
		double intercept = regression.getIntercept();
		double slope = regression.getSlope();
		double slopeError = regression.getSlopeStdErr();
		
		regressionResultString= ("\n\nRegression\n------------------------" 
		+ "\nIntercept:\t" + intercept + "\nSlope:\t" + slope + "\nSlope Error:\t " + slopeError + "\nTrend:\t " + getLabel(slope));
		
		
		return regressionResultString;
	}
	
	public void setAnswer(IMeasurementVector answer) {
		this.answer=answer;
	}
}
