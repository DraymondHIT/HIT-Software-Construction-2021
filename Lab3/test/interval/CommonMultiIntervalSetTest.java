package interval;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

public class CommonMultiIntervalSetTest extends MultiIntervalSetInstanceTest{

	@Override
	public MultiIntervalSet<String> emptyInstance() {
		return new CommonMultiIntervalSet<>();
	}

	@Test
	public void constructorTest() throws Exception {
		IntervalSet<String> intervals = IntervalSet.empty();
		
		intervals.insert(0, 10, "A");
		intervals.insert(10, 20, "B");
		
		MultiIntervalSet<String> multiIntervals = new CommonMultiIntervalSet<>(intervals);
		
		assertEquals(true, multiIntervals.toString().equals("{A=[[0,10]],B=[[10,20]]}") || multiIntervals.toString().equals("{B=[[10,20]],A=[[0,10]]}"));
		assertEquals(Set.of("A","B"), multiIntervals.labels());
		assertEquals("{0=[0,10]}", multiIntervals.intervals("A").toString());
	}
	
	@Test
	public void toStringTest() throws Exception {
		MultiIntervalSet<String> multiIntervals = emptyInstance();
		
		multiIntervals.insert(0, 10, "A");
		assertEquals("{A=[[0,10]]}", multiIntervals.toString());
		
		multiIntervals.insert(15, 20, "A");
		assertEquals("{A=[[0,10],[15,20]]}", multiIntervals.toString());
		
		multiIntervals.insert(20, 30, "B");
		assertEquals(true, multiIntervals.toString().equals("{A=[[0,10],[15,20]],B=[[20,30]]}") || multiIntervals.toString().equals("{B=[[20,30]],A=[[0,10],[15,20]]}"));
	}
	
	@Test
	public void labelsTest() throws Exception {
		MultiIntervalSet<String> multiIntervals = emptyInstance();
		
		multiIntervals.insert(0, 10, "A");
		assertEquals(Set.of("A"), multiIntervals.labels());
		
		multiIntervals.insert(15, 20, "A");
		assertEquals(Set.of("A"), multiIntervals.labels());
		
		multiIntervals.insert(20, 30, "B");
		assertEquals(Set.of("A","B"), multiIntervals.labels());
	}
	
	@Test
	public void intervalsTest() throws Exception {
		MultiIntervalSet<String> multiIntervals = emptyInstance();
		
		assertEquals("{}", multiIntervals.intervals("A").toString());
		
		multiIntervals.insert(0, 10, "A");
		multiIntervals.insert(15, 20, "A");
		multiIntervals.insert(20, 30, "B");
		
		assertEquals("{0=[0,10],1=[15,20]}", multiIntervals.intervals("A").toString());
		assertEquals("{0=[20,30]}", multiIntervals.intervals("B").toString());
	}
	
	@Test(expected = Exception.class)
    public void insertExceptionTest() throws Exception {
    	MultiIntervalSet<String> multiIntervals = emptyInstance();
    	
    	multiIntervals.insert(-1, 10, "A");
    }
    
    @Test
    public void insertTest() throws Exception {
    	MultiIntervalSet<String> multiIntervals = emptyInstance();
    	
    	assertEquals("{}", multiIntervals.toString());
    	assertEquals(Collections.emptySet(), multiIntervals.labels());
    	
    	multiIntervals.insert(0, 10, "A");
    	assertEquals("{A=[[0,10]]}", multiIntervals.toString());
    	assertEquals(Set.of("A"), multiIntervals.labels());
    	
		multiIntervals.insert(15, 20, "A");
    	assertEquals("{A=[[0,10],[15,20]]}", multiIntervals.toString());
    	assertEquals(Set.of("A"), multiIntervals.labels());
   	
    	multiIntervals.insert(20, 30, "B");
    	assertEquals(true, multiIntervals.toString().equals("{A=[[0,10],[15,20]],B=[[20,30]]}") || multiIntervals.toString().equals("{B=[[20,30]],A=[[0,10],[15,20]]}"));
    	assertEquals(Set.of("A", "B"), multiIntervals.labels());
    }
    
    @Test
    public void removeTest() throws Exception {
    	MultiIntervalSet<String> multiIntervals = emptyInstance();
    	
    	assertEquals("{}", multiIntervals.toString());
    	assertEquals(Collections.emptySet(), multiIntervals.labels());
    	
    	assertEquals(false, multiIntervals.remove("A"));
    	assertEquals("{}", multiIntervals.toString());
    	assertEquals(Collections.emptySet(), multiIntervals.labels());
    	
    	multiIntervals.insert(0, 10, "A");
    	multiIntervals.insert(15, 20, "A");
    	multiIntervals.insert(20, 30, "B");
    	
    	assertEquals(true, multiIntervals.toString().equals("{A=[[0,10],[15,20]],B=[[20,30]]}") || multiIntervals.toString().equals("{B=[[20,30]],A=[[0,10],[15,20]]}"));
    	assertEquals(Set.of("A", "B"), multiIntervals.labels());
    	
    	assertEquals(true, multiIntervals.remove("A"));
    	assertEquals("{B=[[20,30]]}", multiIntervals.toString());
    	assertEquals(Set.of("B"), multiIntervals.labels());
    	
    	assertEquals(false, multiIntervals.remove("A"));
    	assertEquals("{B=[[20,30]]}", multiIntervals.toString());
    	assertEquals(Set.of("B"), multiIntervals.labels());
    	
    	assertEquals(true, multiIntervals.remove("B"));
    	assertEquals("{}", multiIntervals.toString());
    	assertEquals(Collections.emptySet(), multiIntervals.labels());
    }
    
    @Test
    public void multiIntervalTest() throws Exception {
    	MultiInterval<String> multiInterval = new MultiInterval<>("A");
    	
    	assertEquals(Collections.emptyList(), multiInterval.getStart());
    	assertEquals(Collections.emptyList(), multiInterval.getEnd());
    	assertEquals("A", multiInterval.getLabel());
    	assertEquals("A=[]", multiInterval.toString());
    	
    	multiInterval.add(0, 10);
    	assertEquals("[0]", multiInterval.getStart().toString());
    	assertEquals("[10]", multiInterval.getEnd().toString());
    	assertEquals("A", multiInterval.getLabel());
    	assertEquals("A=[[0,10]]", multiInterval.toString());
    	
    	multiInterval.add(10, 15);
//    	System.out.println(multiInterval.getStart().toString());
    	assertEquals("[0, 10]", multiInterval.getStart().toString());
    	assertEquals("[10, 15]", multiInterval.getEnd().toString());
    	assertEquals("A", multiInterval.getLabel());
    	assertEquals("A=[[0,10],[10,15]]", multiInterval.toString());
    	
    	multiInterval.add(10, 15);
    	assertEquals("[0, 10]", multiInterval.getStart().toString());
    	assertEquals("[10, 15]", multiInterval.getEnd().toString());
    	assertEquals("A", multiInterval.getLabel());
    	assertEquals("A=[[0,10],[10,15]]", multiInterval.toString());
    	
    	multiInterval.add(8, 12);
    	assertEquals("[0, 8, 10]", multiInterval.getStart().toString());
    	assertEquals("[10, 12, 15]", multiInterval.getEnd().toString());
    	assertEquals("A", multiInterval.getLabel());
    	assertEquals("A=[[0,10],[8,12],[10,15]]", multiInterval.toString());
    }
}
