package interval;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NonOverlapIntervalSetImpl<L> implements NonOverlapIntervalSet<L>{

	//fields
	private MultiIntervalSet<L> multiIntervals = MultiIntervalSet.empty();
	
	// Abstraction function:
	//   multiIntervals表示所有interval的集合
    // Representation invariant:
    //   intervals中间不能有overlaps
    // Safety from rep exposure:
    //   All fields are private,
    //   The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
	
	//constructor
	public NonOverlapIntervalSetImpl() {
		
	}
	
    public NonOverlapIntervalSetImpl(IntervalSet<L> initial) throws Exception {
		multiIntervals = new CommonMultiIntervalSet<>(initial);
	}
    
    public NonOverlapIntervalSetImpl(MultiIntervalSet<L> initial) throws Exception {
    	multiIntervals = initial;
	}
	
	@Override
	public void checkNoOverlap(String opt) throws Exception {
		if(!opt.equals("ENABLE") && !opt.equals("DISABLE")) {
			throw new Exception();
		}
		Iterator<L> iter = multiIntervals.labels().iterator();
		while(iter.hasNext()) {
			L label = iter.next();
			List<Interval<Integer>> tempIntervals = multiIntervals.intervals(label).all();
			for(int i=0; i<tempIntervals.size()-1; i++) {
				if(tempIntervals.get(i).getEnd() >= tempIntervals.get(i+1).getStart()) {
					throw new Exception("Overlaps in interval set!");
				}
			}
		}
		if(opt.equals("DISABLE")) {
			Set<Long> numbers = new HashSet<>();
			Iterator<L> iterator = multiIntervals.labels().iterator();
			while(iterator.hasNext()) {
				L label = iterator.next();
				List<Interval<Integer>> tempIntervals = multiIntervals.intervals(label).all();
				for(int i=0; i<tempIntervals.size(); i++) {
					for(long j=tempIntervals.get(i).getStart(); j<=tempIntervals.get(i).getEnd(); j++) {
						if(!numbers.contains(j)) {
							numbers.add(j);
						}else {
							throw new Exception("Overlaps in interval set!");
						}
					}
				}
			}
		}
	}

	@Override
	public void insert(long start, long end, L label, String opt) throws Exception {
		multiIntervals.insert(start, end, label);
		checkNoOverlap(opt);
	}

	@Override
	public boolean remove(L label) throws Exception {
		return multiIntervals.remove(label);
	}
	
	@Override
	public List<Interval<L>> all() {
		return multiIntervals.all();
	}
	
	@Override
	public String toString() {
		return multiIntervals.toString();
	}
	
}
