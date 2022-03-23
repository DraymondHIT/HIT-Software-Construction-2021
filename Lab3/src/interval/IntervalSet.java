package interval;

import java.util.List;
import java.util.Set;

/**
 * A mutable interval set with labeled intervals.
 * Intervals have distinct labels of an immutable type.
 * Intervals must have a non-negative start and end.
 * 
 * @param <L> type of labels in this interval set, must be immutable
 */
public interface IntervalSet<L> {
	/**
     * Create an empty interval set.
     * 
     * @param <L> type of labels in this interval set, must be immutable
     * @return a new empty interval set
     */
	public static <L> IntervalSet<L> empty(){
		return new CommonIntervalSet<>();
	}
	
	/**
	 * Insert an interval to this interval set.
	 * 
	 * @param start the start number of the interval(assume non-negative)
	 * @param end the end number of the interval(assume non-negative)
	 * @param label the label of the interval
	 * @return true if this interval set has not included an interval with this label;
	 *         false otherwise
	 */
	public boolean insert(long start, long end, L label) throws Exception;
	
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
	 */
	public boolean remove(L label);
	
	/**
	 * Get the start number of the interval with given label.
	 * 
	 * @param label label of an interval
	 * @return the start number if this interval set included an interval with the given label
	 * @throws Exception otherwise
	 */
	public long getStart(L label) throws Exception;
	
	/**
	 * Get the end number of the interval with given label.
	 * 
	 * @param label label of an interval
	 * @return the end number if this interval set included an interval with the given label
	 * @throws Exception otherwise
	 */
	public long getEnd(L label) throws Exception;
	
	/**
	 * Get all intervals in this interval set, sorted by startTime.
	 * 
	 * @return the list of all intervals
	 */
	public List<Interval<L>> all();
}
