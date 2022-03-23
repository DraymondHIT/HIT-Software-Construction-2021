package interval;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NoBlankIntervalSetImpl<L> implements NoBlankIntervalSet<L> {

	//fields
	private MultiIntervalSet<L> multiIntervals = MultiIntervalSet.empty();
	private final Set<Long> numbers = new HashSet<>();
	
	// Abstraction function:
	//   multiIntervals表示所有interval的集合
	//   numbers表示multiIntervals中出现时刻的集合
    // Representation invariant:
    //   intervals中间不能有blanks
    // Safety from rep exposure:
    //   All fields are private final,
    //   The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
	
	//constructor
	public NoBlankIntervalSetImpl() {
		
	}
	
    public NoBlankIntervalSetImpl(IntervalSet<L> initial) throws Exception {
		multiIntervals = new CommonMultiIntervalSet<>(initial);
		Iterator<L> iter = multiIntervals.labels().iterator();
		while(iter.hasNext()) {
			L label = iter.next();
			List<Interval<Integer>> tempIntervals = multiIntervals.intervals(label).all();
			for(int i=0; i<tempIntervals.size(); i++) {
				for(long j=tempIntervals.get(i).getStart(); j<=tempIntervals.get(i).getEnd(); j++) {
					numbers.add(j);
				}
			}
		}
	}
    
    public NoBlankIntervalSetImpl(MultiIntervalSet<L> initial) throws Exception {
    	multiIntervals = initial;
    	Iterator<L> iter = multiIntervals.labels().iterator();
		while(iter.hasNext()) {
			L label = iter.next();
			List<Interval<Integer>> tempIntervals = multiIntervals.intervals(label).all();
			for(int i=0; i<tempIntervals.size(); i++) {
				for(long j=tempIntervals.get(i).getStart(); j<=tempIntervals.get(i).getEnd(); j++) {
					numbers.add(j);
				}
			}
		}
	}
	
	
	public void insert(long start, long end, L label) throws Exception {
		multiIntervals.insert(start, end, label);
		for(Long i=start; i<=end; i++) {
			numbers.add(i);
		}
	}

	public boolean remove(L label) throws Exception {
		boolean flag = multiIntervals.remove(label);
		numbers.clear();
		Iterator<L> iter = multiIntervals.labels().iterator();
		while(iter.hasNext()) {
			L tempLabel = iter.next();
			List<Interval<Integer>> tempIntervals = multiIntervals.intervals(tempLabel).all();
			for(int i=0; i<tempIntervals.size(); i++) {
				for(long j=tempIntervals.get(i).getStart(); j<=tempIntervals.get(i).getEnd(); j++) {
					numbers.add(j);
				}
			}
		}
		return flag;
	}

	@Override
	public void checkNoBlank() throws Exception {
		if(numbers.isEmpty()) return;
		Long min = Long.MAX_VALUE;
		Long max = Long.MIN_VALUE;
		Iterator<Long> iter = numbers.iterator();
		while(iter.hasNext()) {
			Long temp = iter.next();
			if(temp > max) max = temp;
			if(temp < min) min = temp;
		}
		if(numbers.size() != max - min + 1) {
			throw new Exception("Blanks in interval Set!");
		}
	}
	
	@Override
	public String toString() {
		return multiIntervals.toString();
	}
}
