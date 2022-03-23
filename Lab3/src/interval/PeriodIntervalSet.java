package interval;

import java.util.List;
import java.util.Set;

public interface PeriodIntervalSet<L> {
	/**
	 * Check if there are conflicts in interval set. 
	 * 
	 * @throws Exception if there are conflicts in interval set
	 */
	public void checkNoConflict() throws Exception;
	
	/**
	 * Insert an interval to this interval set.
	 * 
	 * @param label the label of the interval
	 * @param interval
	 * @throw if interval is illegal
	 */
	public void insert(L label, Interval<Integer> interval) throws Exception;
	
	/**
	 * Calculate the ratio of free of the interval set.
	 * 
	 * @return the ratio of free of the interval set
	 */
	public double ratioOfFree();
	
	/**
	 * Calculate the ratio of overlap of the interval set.
	 * 
	 * @return the ratio of overlap of the interval set
	 */
	public double ratioOfOverlap();
	
	/**
	 * Watch the list of events of a specific day.
	 * 
	 * @param day the specific date
	 * @return the list of events of a specific day
	 */
	public List<Set<L>> eventsOfDay(int day);
}
