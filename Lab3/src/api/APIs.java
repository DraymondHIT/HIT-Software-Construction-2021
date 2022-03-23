package api;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import interval.CommonMultiIntervalSet;
import interval.Interval;
import interval.IntervalSet;
import interval.MultiIntervalSet;

public class APIs<L> {
	
	public double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2) throws Exception {
		Set<Long> number = new HashSet<>();
		long size = Math.max(s1.all().get(s1.all().size()-1).getEnd(), s2.all().get(s2.all().size()-1).getEnd()) - Math.min(s1.all().get(0).getStart(), s2.all().get(0).getStart());
		long fit = 0;
		Iterator<L> iter = s1.labels().iterator();
		while(iter.hasNext()) {
			L temp = iter.next();
			List<Interval<Integer>> intervals = s1.intervals(temp).all();
			for(int i=0; i<intervals.size(); i++) {
				for(long j=intervals.get(i).getStart(); j<intervals.get(i).getEnd(); j++) {
					number.add(j);
				}
			}
			intervals = s2.intervals(temp).all();
			for(int i=0; i<intervals.size(); i++) {
				for(long j=intervals.get(i).getStart(); j<intervals.get(i).getEnd(); j++) {
					if(number.contains(j)) {
						fit++;
					}
				}
			}
			number.clear();
		}
		return (double)fit/size;
	}
	
	double calcConflictRatio(IntervalSet<L> set) throws Exception {
		MultiIntervalSet<L> multiset = new CommonMultiIntervalSet<>(set);
		return calcConflictRatio(multiset);
	}
	
	double calcConflictRatio(MultiIntervalSet<L> set) {
		long size = set.all().get(set.all().size()-1).getEnd() - set.all().get(0).getStart();
		long offset = set.all().get(0).getStart();
		long fit = 0;
		int[] times = new int[(int) size];
		List<Interval<L>> intervals = set.all();
		for(int i=0; i<intervals.size(); i++) {
			for(long j=intervals.get(i).getStart(); j<intervals.get(i).getEnd(); j++) {
				times[(int) (j-offset)]++;
			}
		}
		for(int i=0; i<times.length; i++) {
			if(times[i] > 1) {
				fit++;
			}
		}
		return (double)fit/size;
	}
	
	double calcFreeTimeRatio(IntervalSet<L> set) throws Exception {
		MultiIntervalSet<L> multiset = new CommonMultiIntervalSet<>(set);
		return calcFreeTimeRatio(multiset);
	}
	
	double calcFreeTimeRatio(MultiIntervalSet<L> set) {
		long size = set.all().get(set.all().size()-1).getEnd() - set.all().get(0).getStart();
		long offset = set.all().get(0).getStart();
		long fit = 0;
		int[] times = new int[(int) size];
		List<Interval<L>> intervals = set.all();
		for(int i=0; i<intervals.size(); i++) {
			for(long j=intervals.get(i).getStart(); j<intervals.get(i).getEnd(); j++) {
				times[(int) (j-offset)]++;
			}
		}
		for(int i=0; i<times.length; i++) {
			if(times[i] == 0) {
				fit++;
			}
		}
		return (double)fit/size;
	}
	
}
