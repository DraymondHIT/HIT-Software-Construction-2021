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
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    // add方法测试：
	//     分别测试已加入图中的点和未加入图中的点
	// set方法测试：
	//     测试已加入图中的边和未加入图中的边，
	//     测试有新顶点的边和没有新节点的边,
	//     测试weight等于0和大于0的情况
	// remove方法测试：
	//     测试删除图中已有的边和实际没有的边
	// vertices方法测试：
	//     测试空图和非空图
	// sources方法测试：
	//     测试入度为0的点和入度非零的点
	// target方法测试：
	//     测试出度为0的点和出度非零的点
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // other tests for instance methods of Graph
    @Test
    public void addTest() {
        Graph<String> graph = emptyInstance();
    	
    	assertEquals(true, graph.add("Beijing"));
    	assertEquals(Set.of("Beijing"), graph.vertices());
    	assertEquals(true, graph.add("Nanjing"));
    	assertEquals(Set.of("Beijing","Nanjing"), graph.vertices());
    	assertEquals(false, graph.add("Beijing"));
    	assertEquals(Set.of("Beijing","Nanjing"), graph.vertices());
    }
    
    @Test
    public void setTest() {
        Graph<String> graph = emptyInstance();
    	
    	graph.add("Beijing");
    	graph.add("Nanjing");
    	graph.add("Shanghai");
    	
    	assertEquals(0, graph.set("Nanjing", "Shanghai", 40));
    	assertEquals("[[Nanjing,Shanghai,40]]", graph.toString());
    	assertEquals(0, graph.set("Nanjing", "Beijing", 20));
    	assertEquals("[[Nanjing,Shanghai,40],[Nanjing,Beijing,20]]", graph.toString());
    	assertEquals(20, graph.set("Nanjing", "Beijing", 0));
    	assertEquals("[[Nanjing,Shanghai,40]]", graph.toString());
    	assertEquals(0, graph.set("Nanjing", "Beijing", 20));
    	assertEquals("[[Nanjing,Shanghai,40],[Nanjing,Beijing,20]]", graph.toString());
    	assertEquals(20, graph.set("Nanjing", "Beijing", -1));
    	assertEquals("[[Nanjing,Shanghai,40],[Nanjing,Beijing,20]]", graph.toString());
    	assertEquals(0, graph.set("Nanjing", "Hangzhou", 30));
    	assertEquals("[[Nanjing,Shanghai,40],[Nanjing,Beijing,20],[Nanjing,Hangzhou,30]]", graph.toString());
    	assertEquals(40, graph.set("Nanjing", "Shanghai", 60));
    	assertEquals("[[Nanjing,Shanghai,60],[Nanjing,Beijing,20],[Nanjing,Hangzhou,30]]", graph.toString());
    }
    
    @Test
    public void removeTest() {
    	Graph<String> graph = emptyInstance();
     	
     	graph.add("Beijing");
     	graph.add("Nanjing");
     	graph.add("Shanghai");
     	graph.add("Hangzhou");
     	
     	graph.set("Nanjing", "Shanghai", 40);
     	graph.set("Nanjing", "Beijing", 20);
     	graph.set("Beijing", "Hangzhou", 30);
     	
     	assertEquals(false, graph.remove("Tianjin"));
     	assertEquals("[[Nanjing,Shanghai,40],[Nanjing,Beijing,20],[Beijing,Hangzhou,30]]",graph.toString());
     	assertEquals(true, graph.remove("Beijing"));
     	assertEquals("[[Nanjing,Shanghai,40]]",graph.toString());
    }
    
    @Test
    public void verticesTest() {
        Graph<String> graph = emptyInstance();
        
        assertEquals(Collections.emptySet(), graph.vertices());
        assertEquals("[]", graph.toString());
        
        graph.add("Beijing");
        assertEquals("[]", graph.toString());
    	assertEquals(Set.of("Beijing"), graph.vertices());
    }
    
    @Test
    public void sourcesTest() {
    	Graph<String> graph = emptyInstance();
    	
    	graph.add("Beijing");
    	graph.add("Nanjing");
    	graph.add("Shanghai");
    	graph.add("Hangzhou");
    	
    	graph.set("Nanjing", "Shanghai", 0);
    	graph.set("Nanjing", "Beijing", 20);
    	graph.set("Beijing", "Hangzhou", 30);
    	graph.set("Shanghai", "Hangzhou", 50);
    	
    	assertEquals(Collections.emptyMap(), graph.sources("Nanjing"));
    	assertEquals(Map.of("Nanjing",20), graph.sources("Beijing"));
    	assertEquals(Map.of("Beijing",30,"Shanghai",50), graph.sources("Hangzhou"));
    	assertEquals(Collections.emptyMap(), graph.sources("Shanghai"));
    }
    
    @Test
    public void targetsTest() {
    	Graph<String> graph = emptyInstance();
    	
    	graph.add("Beijing");
    	graph.add("Nanjing");
    	graph.add("Shanghai");
    	graph.add("Hangzhou");
    	
    	graph.set("Nanjing", "Shanghai", 40);
    	graph.set("Nanjing", "Beijing", 20);
    	graph.set("Beijing", "Hangzhou", 30);
    	graph.set("Shanghai", "Hangzhou", 0);
    	
    	assertEquals(Collections.emptyMap(), graph.targets("Hangzhou"));
    	assertEquals(Map.of("Hangzhou",30), graph.targets("Beijing"));
    	assertEquals(Map.of("Beijing",20,"Shanghai",40), graph.targets("Nanjing"));
    	assertEquals(Collections.emptyMap(), graph.targets("Shanghai"));
    }
}
