package interval;

import static org.junit.Assert.*;

import org.junit.Test;

public class NoBlankIntervalSetTest {

	@Test
	public void EmptyIntervalSetTest() throws Exception {
		NoBlankIntervalSet<String> nbis = new NoBlankIntervalSetImpl<>();
		
		assertEquals("{}", nbis.toString());
		
		nbis.checkNoBlank();
	}
	
	@Test
	public void NoBlankTest1() throws Exception {
		NoBlankIntervalSet<String> nbis1 = new NoBlankIntervalSetImpl<>();
		nbis1.insert(0, 10, "A");
		nbis1.insert(11, 15, "A");
		
		nbis1.checkNoBlank();
		
		IntervalSet<String> interval = IntervalSet.empty();
		interval.insert(0, 10, "A");
		interval.insert(11, 15, "B");
		NoBlankIntervalSet<String> nbis2 = new NoBlankIntervalSetImpl<>(interval);
		
		nbis2.checkNoBlank();
		
		MultiIntervalSet<String> multiInterval = MultiIntervalSet.empty();
		multiInterval.insert(0, 10, "A");
		multiInterval.insert(11, 15, "B");
		multiInterval.insert(12, 16, "A");
		NoBlankIntervalSet<String> nbis3 = new NoBlankIntervalSetImpl<>(multiInterval);
		
		nbis3.checkNoBlank();
	}
	
	@Test
	public void NoBlankTest2() throws Exception {
		NoBlankIntervalSet<String> nbis = new NoBlankIntervalSetImpl<>();
		nbis.insert(0, 10, "A");
		nbis.insert(13, 15, "A");
		nbis.insert(8, 12, "B");
		nbis.insert(15, 20, "D");
		
		nbis.checkNoBlank();
		
		nbis.remove("D");
		
		nbis.checkNoBlank();
		
	}
	
	@Test(expected = Exception.class)
	public void BlankTest1() throws Exception {
		NoBlankIntervalSet<String> nbis = new NoBlankIntervalSetImpl<>();
		nbis.insert(0, 10, "A");
		nbis.insert(13, 15, "A");
		
		nbis.checkNoBlank();
	}
	
	@Test(expected = Exception.class)
	public void BlankTest2() throws Exception {
		NoBlankIntervalSet<String> nbis = new NoBlankIntervalSetImpl<>();
		nbis.insert(0, 10, "A");
		nbis.insert(13, 15, "A");
		nbis.insert(8, 12, "B");
		nbis.insert(15, 20, "D");
		
		nbis.remove("B");
		
		nbis.checkNoBlank();
	}

}
