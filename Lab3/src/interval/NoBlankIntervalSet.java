package interval;

public interface NoBlankIntervalSet<L>{
	/**
	 * Check if there are blanks in interval set. 
	 * 
	 * @throws Exception if there are blanks in interval set
	 */
	public void checkNoBlank() throws Exception;
	
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
	 * Remove an interval from this interval set.
	 * 
	 * @param label label of the interval to remove
	 * @return true if this interval set included an interval with the given label;
     *         otherwise false (and this interval set is not modified)
	 * @throws Exception 
	 */
	public boolean remove(L label) throws Exception;
	
}
