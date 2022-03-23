package interval;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class PeriodIntervalSetTest {

	@Test
	public void periodIntervalSetTest1() throws Exception {
				
		PeriodIntervalSet<String> periods = new PeriodIntervalSetImpl<>();
		
		periods.insert("A", new Interval<Integer>(1, 1, 1));
		periods.insert("A", new Interval<Integer>(2, 2, 1));
		periods.insert("A", new Interval<Integer>(3, 3, 3));
		periods.insert("A", new Interval<Integer>(5, 5, 4));
		
		assertEquals(true, Math.abs(periods.ratioOfFree()-(double)31/35) < 0.01);
		assertEquals(true, Math.abs(periods.ratioOfOverlap()-0.) < 0.01);
		
		assertEquals(List.of(Set.of("A"),Set.of("A"),Collections.emptySet(),Collections.emptySet(),Collections.emptySet()),periods.eventsOfDay(1));
	}
	
	@Test
	public void periodIntervalSetTest2() throws Exception {
		
		PeriodIntervalSet<String> periods = new PeriodIntervalSetImpl<>();
		
		periods.insert("A", new Interval<Integer>(1, 1, 1));
		periods.insert("A", new Interval<Integer>(2, 2, 1));
		periods.insert("A", new Interval<Integer>(3, 3, 3));
		periods.insert("A", new Interval<Integer>(5, 5, 4));
		
		periods.insert("B", new Interval<Integer>(2, 2, 1));
		periods.insert("B", new Interval<Integer>(1, 1, 3));
		periods.insert("B", new Interval<Integer>(5, 5, 7));
		
		assertEquals(true, Math.abs(periods.ratioOfFree()-(double)29/35) < 0.01);
		assertEquals(true, Math.abs(periods.ratioOfOverlap()-(double)1/35) < 0.01);
		
		assertEquals(List.of(Set.of("B"),Collections.emptySet(),Set.of("A"),Collections.emptySet(),Collections.emptySet()),periods.eventsOfDay(3));
	}

	@Test(expected = Exception.class)
	public void conflictTest() throws Exception {
		
		PeriodIntervalSet<String> periods = new PeriodIntervalSetImpl<>(7,5);
		
		periods.insert("A", new Interval<Integer>(1, 1, 1));
		periods.insert("A", new Interval<Integer>(2, 2, 1));
		periods.insert("A", new Interval<Integer>(3, 3, 3));
		periods.insert("A", new Interval<Integer>(5, 5, 4));
		
		periods.insert("B", new Interval<Integer>(2, 2, 2));
		periods.insert("B", new Interval<Integer>(3, 3, 3));
		periods.insert("B", new Interval<Integer>(5, 5, 7));
		
		periods.checkNoConflict();
	}
	
}
