package interval;

import static org.junit.Assert.*;

import org.junit.Test;

public class NoOverlapIntervalSetTest {

	@Test
	public void EmptyIntervalSetTest() throws Exception {
		NonOverlapIntervalSet<String> nois = new NonOverlapIntervalSetImpl<>();
	
		assertEquals("{}", nois.toString());
		
		nois.checkNoOverlap("ENABLE");
	}
	
	@Test
	public void NoOverlapTest1() throws Exception {
		NonOverlapIntervalSet<String> nois = new NonOverlapIntervalSetImpl<>();
		
		nois.insert(0, 10, "A", "ENABLE");
		nois.insert(11, 15, "A", "ENABLE");
		nois.insert(0, 20, "B", "ENABLE");
	}
	
	@Test(expected=Exception.class)
	public void NoOverlapTest2() throws Exception {
		IntervalSet<String> interval = IntervalSet.empty();
		interval.insert(0, 10, "A");
		interval.insert(11, 15, "B");
		
		NonOverlapIntervalSet<String> nois = new NonOverlapIntervalSetImpl<>(interval);
		
		nois.insert(8, 12, "A", "ENABLE");
	}

	@Test(expected=Exception.class)
	public void NoOverlapTest3() throws Exception {
		MultiIntervalSet<String> multiInterval = MultiIntervalSet.empty();
		multiInterval.insert(0, 10, "A");
		multiInterval.insert(11, 15, "B");
		multiInterval.insert(12, 16, "A");
		
		NonOverlapIntervalSet<String> nois = new NonOverlapIntervalSetImpl<>(multiInterval);
		
		nois.checkNoOverlap("ENABLE");
		nois.checkNoOverlap("DISABLE");
	}
}
