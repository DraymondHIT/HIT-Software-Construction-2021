/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {
    
    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }
    
    // test other vertex label types in Problem 3.2
    @Test
    public void addTest() {
    	Graph<Integer> graph = Graph.empty();
    	
    	assertEquals(true, graph.add(1));
    	assertEquals(Set.of(1), graph.vertices());
    	assertEquals(true, graph.add(2));
    	assertEquals(Set.of(1,2), graph.vertices());
    	assertEquals(false, graph.add(1));
    	assertEquals(Set.of(1,2), graph.vertices());
    }
    
    @Test
    public void setTest() {
    	Graph<Integer> graph = Graph.empty();
    	
    	graph.add(1);
    	graph.add(2);
    	graph.add(3);
    	
    	assertEquals(0, graph.set(2, 3, 40));  	
    	assertEquals("[[2,3,40]]", graph.toString());
    	assertEquals(0, graph.set(2, 1, 20));
    	assertEquals("[[2,3,40],[2,1,20]]", graph.toString());
    	assertEquals(20, graph.set(2, 1, 0));
    	assertEquals("[[2,3,40]]", graph.toString());
    	assertEquals(0, graph.set(2, 1, 20));
    	assertEquals("[[2,3,40],[2,1,20]]", graph.toString());
    	assertEquals(20, graph.set(2, 1, -1));
    	assertEquals("[[2,3,40],[2,1,20]]", graph.toString());
    	assertEquals(0, graph.set(2, 4, 30));
    	assertEquals("[[2,3,40],[2,1,20],[2,4,30]]", graph.toString());
    	assertEquals(40, graph.set(2, 3, 60));
    	assertEquals("[[2,3,60],[2,1,20],[2,4,30]]", graph.toString());
    }
    
    @Test
    public void removeTest() {
    	Graph<Integer> graph = Graph.empty();
    	
    	graph.add(1);
    	graph.add(2);
    	graph.add(3);
    	graph.add(4);
    	
    	graph.set(2, 3, 40);
    	graph.set(2, 1, 20);
    	graph.set(1, 4, 30);
    	
    	assertEquals(false, graph.remove(6));
    	assertEquals("[[2,3,40],[2,1,20],[1,4,30]]", graph.toString());
    	assertEquals(true, graph.remove(1));
    	assertEquals("[[2,3,40]]", graph.toString());
    }
    
    @Test
    public void verticesTest() {
    	Graph<Integer> graph = Graph.empty();
        
        assertEquals(Collections.emptySet(), graph.vertices());
        
        graph.add(1);
    	assertEquals(Set.of(1), graph.vertices());
    }
    
    @Test
    public void sourcesTest() {
    	Graph<Integer> graph = Graph.empty();
    	
    	graph.add(1);
    	graph.add(2);
    	graph.add(3);
    	graph.add(4);
    	
    	graph.set(2, 3, 0);
    	graph.set(2, 1, 20);
    	graph.set(1, 4, 30);
    	graph.set(3, 4, 50);
    	
    	assertEquals(Collections.emptyMap(), graph.sources(2));
    	assertEquals(Map.of(2,20), graph.sources(1));
    	assertEquals(Map.of(1,30,3,50), graph.sources(4));
    	assertEquals(Collections.emptyMap(), graph.sources(3));
    }
    
    @Test
    public void targetsTest() {
    	Graph<Integer> graph = Graph.empty();
    	
    	graph.add(1);
    	graph.add(2);
    	graph.add(3);
    	graph.add(4);
    	
    	graph.set(2, 3, 40);
    	graph.set(2, 1, 20);
    	graph.set(1, 4, 30);
    	graph.set(3, 4, 0);
    	
    	assertEquals(Collections.emptyMap(), graph.targets(4));
    	assertEquals(Map.of(4,30), graph.targets(1));
    	assertEquals(Map.of(1,20,3,40), graph.targets(2));
    	assertEquals(Collections.emptyMap(), graph.targets(3));
    }
}
