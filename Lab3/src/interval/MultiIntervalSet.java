package interval;

import java.util.List;
import java.util.Set;

public interface MultiIntervalSet<L> {
	/**
     * Create an empty interval set.
     * 
     * @param <L> type of labels in this interval set, must be immutable
     * @return a new empty interval set
     */
	public static <L> MultiIntervalSet<L> empty(){
		return new CommonMultiIntervalSet<>();
	}
	
	/**
	 * Insert an interval to this interval set.
	 * 
	 * @param start the start number of the interval(assume non-negative)
	 * @param end the end number of the interval(assume non-negative)
	 * @param label the label of the interval
	 * @throw if start or end is negative
	 */
	public void insert(long start, long end, L label) throws Exception;
	
	/**
	 * Get all the intervals in this interval set.
	 * 
	 * @return the set of intervals in this interval set
	 */
	public Set<L> labels();
	
	/**
	 * Remove an interval from this interval set.
	 * 
	 * @param label label of the interval to remove
	 * @return true if this interval set included an interval with the given label;
     *         otherwise false (and this interval set is not modified)
	 * @throws Exception 
	 */
	public boolean remove(L label);
	
	/**
	 * Get the interval set with given label.
	 * 
	 * @param label label of an interval
	 * @return the interval set with the given label
	 */
	public IntervalSet<Integer> intervals(L label) throws Exception;
	
	/**
	 * Get all intervals in this interval set, sorted by startTime.
	 * 
	 * @return the list of all intervals
	 */
	public List<Interval<L>> all();
}
