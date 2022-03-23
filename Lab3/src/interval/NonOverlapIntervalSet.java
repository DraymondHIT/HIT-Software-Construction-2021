package interval;

import java.util.List;

public interface NonOverlapIntervalSet<L> {
	/**
	 * Check if there are overlaps in interval set. 
	 * 
	 * @throws Exception if there are overlaps in interval set. 
	 */
	public void checkNoOverlap(String opt) throws Exception;
	
	/**
	 * Insert an interval to this interval set.
	 * 
	 * @param start the start number of the interval(assume non-negative)
	 * @param end the end number of the interval(assume non-negative)
	 * @param label the label of the interval
	 * @param opt if overlaps between intervals accepted
	 * @throw if start or end is negative
	 */
	public void insert(long start, long end, L label, String opt) throws Exception;
	
	/**
	 * Remove an interval from this interval set.
	 * 
	 * @param label label of the interval to remove
	 * @return true if this interval set included an interval with the given label;
     *         otherwise false (and this interval set is not modified)
	 * @throws Exception 
	 */
	public boolean remove(L label) throws Exception;
	
	/**
	 * Get all intervals in this interval set, sorted by startTime.
	 * 
	 * @return the list of all intervals
	 */
	public List<Interval<L>> all();
}
