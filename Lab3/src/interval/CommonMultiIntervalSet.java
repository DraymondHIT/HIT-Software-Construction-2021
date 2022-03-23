package interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CommonMultiIntervalSet<L> implements MultiIntervalSet<L>{
	//fields
	private final Set<MultiInterval<L>> multiIntervals = new HashSet<>();
	private final Set<L> labels = new HashSet<>();
	private IntervalSet<Integer> intervals = IntervalSet.empty();
	
	// Abstraction function:
    //   labels表示所有intervals的标签名的集合
	//   multiIntervals表示所有multiInterval的集合
	//   intervals用以记录某个label对应的一系列interval的集合
    // Representation invariant:
	//   multiInterval中的start和end必须非负
    //   multiIntervals里所有的label必须出现在labels中
    // Safety from rep exposure:
    //   All fields are private final,
    //   The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
	
	//checkRep
	public void checkRep() {
		Set<L> tempLabels = new HashSet<>();
		Iterator<MultiInterval<L>> iter = multiIntervals.iterator();
		while(iter.hasNext()) {
			MultiInterval<L> multiInterval = iter.next();
			if(tempLabels.contains(multiInterval.getLabel())) {
				assert false;
			}else {
				tempLabels.add(multiInterval.getLabel());
			}
			for(int i=0; i<multiInterval.size(); i++) {
				assert multiInterval.getStart().get(i) >= 0 && multiInterval.getEnd().get(i) >= 0;
			}
		}	
		assert tempLabels.equals(labels);
	}
	
	//constructor
	public CommonMultiIntervalSet() {
    	
    }
	
	public CommonMultiIntervalSet(IntervalSet<L> initial) throws Exception {
		for(Interval<L> interval : initial.all()) {
			MultiInterval<L> multiInterval = new MultiInterval<>(interval.getLabel());
			multiInterval.add(interval.getStart(), interval.getEnd());
			multiIntervals.add(multiInterval);
			labels.add(interval.getLabel());
		}
		checkRep();
	}
	
	@Override
	public void insert(long start, long end, L label) throws Exception {
		if(!labels.contains(label)) {
			MultiInterval<L> multiInterval = new MultiInterval<>(label);
			multiInterval.add(start, end);
			multiIntervals.add(multiInterval);
			labels.add(label);
			checkRep();
			return;
		}else {
			for(MultiInterval<L> multiInterval : multiIntervals) {
				if(multiInterval.getLabel().equals(label)) {
					multiInterval.add(start, end);
					checkRep();
					return;
				}
			}
		}
	}

	@Override
	public Set<L> labels() {
		return Collections.unmodifiableSet(labels);
	}

	@Override
	public boolean remove(L label) {
		Iterator<MultiInterval<L>> iter = multiIntervals.iterator();
		while(iter.hasNext()) {
			MultiInterval<L> multiInterval = iter.next();
			if(multiInterval.getLabel().equals(label)) {
				iter.remove();
				labels.remove(label);
				checkRep();
				return true;
			}
		}
		checkRep();
		return false;
	}

	@Override
	public IntervalSet<Integer> intervals(L label) throws Exception {
		intervals = IntervalSet.empty();
		Iterator<MultiInterval<L>> iter = multiIntervals.iterator();
		while(iter.hasNext()) {
			MultiInterval<L> multiInterval = iter.next();
			if(multiInterval.getLabel().equals(label)) {
				List<Long> starts = multiInterval.getStart();
				List<Long> ends = multiInterval.getEnd();
				for(int i=0; i<multiInterval.size(); i++) {
					intervals.insert(starts.get(i), ends.get(i), i);
				}
				checkRep();
				return intervals;
			}
		}
		checkRep();
		return intervals;
	}
	
	@Override
	public List<Interval<L>> all()  {
		List<Interval<L>> temp = new ArrayList<>();
		Iterator<MultiInterval<L>> iter = multiIntervals.iterator();
		while(iter.hasNext()) {
			MultiInterval<L> multiInterval = iter.next();
			L label = multiInterval.getLabel();
			List<Long> start = multiInterval.getStart();
			List<Long> end = multiInterval.getEnd();
			for(int i=0; i<start.size(); i++) {
				try {
					temp.add(new Interval<L>(start.get(i), end.get(i), label));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		temp.sort(new Comparator<Interval<L>>() {
			@Override
			public int compare(Interval<L> o1, Interval<L> o2) {
				return (int) (o1.getStart()-o2.getStart());
			}
		});
		checkRep();
		return Collections.unmodifiableList(temp);
	}
	
	@Override
	public String toString() {
		String str = "{";
		Iterator<MultiInterval<L>> iter = multiIntervals.iterator();
		while(iter.hasNext()) {
			MultiInterval<L> multiInterval = iter.next();
			str += multiInterval.toString() + ",";
		}
		if(str.endsWith(",")) {
			str = str.substring(0, str.length()-1);
		}
		str += "}";
		checkRep();
		return str;
	}
}
