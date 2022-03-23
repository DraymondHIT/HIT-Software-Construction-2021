/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L> > edges = new ArrayList<>();
    
    // Abstraction function:
    //   vertices,edges到有向图的映射
    // Representation invariant:
    //   每条edge的weight为非负整数
    //   edge的顶点必须在vertices中
    //   每两点之间最多只有一条边
    // Safety from rep exposure:
    //   All fields are private final,
    //   The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
    
    // constructor
    public ConcreteEdgesGraph() {
    	
    }
    
    // checkRep
    private void checkRep() {
    	for(int i=0; i<edges.size(); i++) {
    		if(edges.get(i).getWeight() < 0) {
    			assert false;
    		}
    		if(!vertices.contains(edges.get(i).getSource()) || !vertices.contains(edges.get(i).getTarget())) {
    			assert false;
    		}
    		for(int j=i+1; j<edges.size(); j++) {
    			if(edges.get(i).equals(edges.get(j))) {
    				assert false;
    			}
    		}
    	}
    }
    
    @Override public boolean add(L vertex) {
    	try {
    		boolean flag;
    		if(vertices.contains(vertex)) {
	    		flag = false;
	    	}else {
	    		vertices.add(vertex);
	    		flag = true;
	    	}
    		checkRep();
    		return flag;
    	}catch(Exception e) {
    		throw new RuntimeException("not implemented");
    	}
    }
    
    @Override public int set(L source, L target, int weight) {
    	try {
    		Iterator<Edge<L> > iter = edges.iterator();
	        while(iter.hasNext()) {
	        	Edge<L> edge = iter.next();
	        	if(edge.getSource().equals(source) && edge.getTarget().equals(target)) {
	        		if(weight == 0) {
	        			iter.remove();
	        			checkRep();
	        			return edge.getWeight();
	        		}else if(weight < 0){
	        			System.out.println("不接受边的权值为负数！");
	        			checkRep();
	        			return edge.getWeight();
	        		}else{
	        			int temp = edge.getWeight();
	        			edges.add(edges.indexOf(edge), new Edge<>(edge.getSource(),edge.getTarget(),weight));
	        			edges.remove(edges.indexOf(edge));
	        			checkRep();
	        			return temp;
	        		}
	        	}
	        }
	        if(weight == 0) return 0;        
	        Edge<L> edge = new Edge<>(source, target, weight);
	        edges.add(edge);
	        if(!vertices.contains(source)) vertices.add(source);
	        if(!vertices.contains(target)) vertices.add(target);
	        checkRep();
	        return 0;
    	}catch(Exception e) {
    		throw new RuntimeException("not implemented");
    	}
    }
    
    @Override public boolean remove(L vertex) {
        try{
        	if(!vertices.contains(vertex)) {
	        	checkRep();
	        	return false;
	        }
	        Iterator<Edge<L> > iter = edges.iterator();
	        while(iter.hasNext()) {
	        	Edge<L> edge = iter.next();
	        	if(edge.getSource().equals(vertex) || edge.getTarget().equals(vertex)) {
	        		iter.remove();
	        	}
	        }
	        checkRep();
	        return true;
        }catch(Exception e) {
    		throw new RuntimeException("not implemented");
    	}
    }
    
    @Override public Set<L> vertices() {
    	return Collections.unmodifiableSet(this.vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	Map<L, Integer> sources = new HashMap<>();
        for(Edge<L> edge:edges) {
        	if(edge.getTarget().equals(target) && edge.getWeight() > 0) {
        		sources.put(edge.getSource(), edge.getWeight());
        	}
        }
        return Collections.unmodifiableMap(sources);
    }
    
    @Override public Map<L, Integer> targets(L source) {
		Map<L, Integer> targets = new HashMap<>();
        for(Edge<L> edge:edges) {
        	if(edge.getSource().equals(source) && edge.getWeight() > 0) {
        		targets.put(edge.getTarget(), edge.getWeight());
        	}
        }
        return Collections.unmodifiableMap(targets);
    }
    
    // toString()
    @Override
    public String toString() {
		String str = "[";
    	for(Edge<L> edge:edges) {
    		str += edge.toString();
    		str += ",";
    	}
    	if(edges.size() >= 1) str = str.substring(0, str.length()-1);
    	str += "]";
    	return str;
    }
}

/**
 * An immutable directed edge.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: The rep of the edge can only be read.
 * 
 * @param source the source vertex labels of the edge, must be immutable
 * @param target the target vertex labels of the edge, must be immutable
 * @param weight the weight of the edge, not less than zero
 */
class Edge<L> {
    
    // fields
    private final L source, target;
    private final int weight;
    
    // Abstraction function:
    //   source,target,weight到一条有向边的映射
    // Representation invariant:
    //   weight必须是正整数
    // Safety from rep exposure:
    //   All fields are private final,
    //   both L and int are immutable
    
    // constructor
    public Edge(L source, L target, int weight) {
    	this.source = source;
    	this.target = target;
    	this.weight = weight;
    	checkRep();
    }
    
    // checkRep
    private void checkRep() {
    	if(this.weight < 0) {
    		assert false;
    	}
    }
    
    // methods
    public L getSource() {
    	return this.source;
    }
    
    public L getTarget() {
    	return this.target;
    }
    
    public int getWeight() {
    	return this.weight;
    }

    @Override
    public String toString(){
    	return "["+this.source+","+this.target+","+this.weight+"]";
    }
    
    @Override
    public boolean equals(Object that) {
    	if(!(that instanceof Edge)) {
    		return false;
    	}else {
    		Edge<L> temp = (Edge<L>)that;
	    	return this.source.equals(temp.source)
	    			&& this.target.equals(temp.target)
	    			&& this.weight == temp.weight;
    	}
    }
    
    @Override
    public int hashCode() {
    	return this.source.hashCode() + this.target.hashCode() + weight;
    }
}
