package dom2app;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.util.Pair;

public class Disaster implements IMeasurementVector {
	String countryName;
	String indicator;
	List<Pair<Integer, Integer>> Measurements;
	String DescriptiveStats;
	String RegressionResult;
	
	public Disaster(String countryName, String indicator, List<Pair<Integer, Integer>> Measurements) {
		this.countryName = countryName;
		this.indicator = indicator;
		this.Measurements = Measurements;
		
	} 
	
	@Override
	public String getCountryName() {
		return countryName;
	}

	@Override
	public String getIndicatorString() {
		return indicator;
	}

	@Override
	public List<Pair<Integer, Integer>> getMeasurements() {
		return Measurements;
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
	
	@Override
	public String getDescriptiveStatsAsString() {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for(Pair<Integer, Integer> xyPair: Measurements) {
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
		
		//change that like report 
		DescriptiveStats=("DescriptiveStatsResult "
				+ "[count=" + count + ", min=" + (int)minD + ", gMean=" + gMean + ", mean=" + mean + 
				", median=" + (int)medianD + ", max=" + (int)maxD + ", kurtosis=" + kurtosis + ", stdev=" + stdev + ", sum=" + (int)sumD);
		
		return DescriptiveStats;
	}

	@Override
	public String getRegressionResultAsString() {
		SimpleRegression regression = new SimpleRegression();
		for(Pair<Integer, Integer> xyPair: Measurements) {
			int year = xyPair.getFirst();
			int value = xyPair.getSecond();
			regression.addData(year, value);
		}
		double intercept = regression.getIntercept();
		double slope = regression.getSlope();
		double slopeError = regression.getSlopeStdErr();
		
		RegressionResult= ("\nRegressionResult " 
		+ "[intercept=" + intercept + ", slope=" + slope + ", slopeError=" + slopeError + ", " + getLabel(slope));
		
		return RegressionResult;
	}
	
}
