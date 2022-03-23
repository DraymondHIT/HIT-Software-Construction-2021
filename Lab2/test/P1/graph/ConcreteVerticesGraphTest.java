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
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   首先测试toString方法的重写，然后针对ConcreteVerticesGraph类中的方法依次进行测试。
    
    // tests for ConcreteVerticesGraph.toString()
    @Test
    public void toStringTest() {
    	Graph<String> graph = emptyInstance();
    	
    	assertEquals("[]", graph.toString());
    	
    	graph.add("Beijing");
    	assertEquals("[]", graph.toString());
    	
    	graph.set("Beijing", "Nanjing", 20);
    	assertEquals("[[Beijing,Nanjing,20]]", graph.toString());
    }
    
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
    	assertEquals("[[Nanjing,Beijing,20],[Nanjing,Shanghai,40]]", graph.toString());
    	assertEquals(20, graph.set("Nanjing", "Beijing", 0));
    	assertEquals("[[Nanjing,Shanghai,40]]", graph.toString());
    	assertEquals(0, graph.set("Nanjing", "Beijing", 20));
    	assertEquals("[[Nanjing,Beijing,20],[Nanjing,Shanghai,40]]", graph.toString());
    	assertEquals(20, graph.set("Nanjing", "Beijing", -1));
    	assertEquals("[[Nanjing,Beijing,20],[Nanjing,Shanghai,40]]", graph.toString());
    	assertEquals(0, graph.set("Nanjing", "Hangzhou", 30));
    	assertEquals("[[Nanjing,Beijing,20],[Nanjing,Shanghai,40],[Nanjing,Hangzhou,30]]", graph.toString());
    	assertEquals(40, graph.set("Nanjing", "Shanghai", 60));
    	assertEquals("[[Nanjing,Beijing,20],[Nanjing,Shanghai,60],[Nanjing,Hangzhou,30]]", graph.toString());
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
    	assertEquals("[[Beijing,Hangzhou,30],[Nanjing,Beijing,20],[Nanjing,Shanghai,40]]",graph.toString());
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
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   创建一个Vertex类型的对象，然后对它进行各类操作，以测试Vertex类中各种方法的正确性。
    
    // tests for operations of Vertex
    @Test
    public void vertexTest() {
    	Vertex<String> v1 = new Vertex<>("Beijing");
    	Vertex<String> v2 = new Vertex<>("Beijing");
    	
    	assertEquals("", v1.toString());
    	assertEquals(Collections.emptyMap(), v1.vertices());
    	assertEquals(true, v1.equals(v2));
    	assertEquals(true, v1.hashCode() == v2.hashCode());
    	
    	v1.add("Nanjing", 20);
    	assertEquals("[Beijing,Nanjing,20]", v1.toString());
    	assertEquals(Map.of("Nanjing",20), v1.vertices());
    	assertEquals(false, v1.equals(v2));
    	assertEquals(false, v1.hashCode() == v2.hashCode());
    	
    	v1.set("Shanghai", 30);
    	assertEquals("[Beijing,Shanghai,30],[Beijing,Nanjing,20]", v1.toString());
    	assertEquals(Map.of("Nanjing",20,"Shanghai",30), v1.vertices());
    	v1.set("Nanjing", 0);
    	assertEquals("[Beijing,Shanghai,30]", v1.toString());
    	assertEquals(Map.of("Shanghai",30), v1.vertices());
    	v1.set("Shanghai", 10);
    	assertEquals("[Beijing,Shanghai,10]", v1.toString());
    	assertEquals(Map.of("Shanghai",10), v1.vertices());
    	
    	v1.remove("Shanghai");
    	assertEquals("", v1.toString());
    	assertEquals(Collections.emptyMap(), v1.vertices());
    	assertEquals(true, v1.equals(v2));
    	assertEquals(true, v1.hashCode() == v2.hashCode());
    }
}
