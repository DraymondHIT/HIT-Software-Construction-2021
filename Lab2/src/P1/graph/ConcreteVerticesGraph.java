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
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Vertex的抽象数据类型到有向图的映射
    // Representation invariant:
    //   顶点不能重复
    // Safety from rep exposure:
    //   All fields are private final,
    //   The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
    
    // constructor
    public ConcreteVerticesGraph() {
    	
    }

    // checkRep
    private void checkRep() {
    	for(int i=0; i<vertices.size(); i++) {
    		for(int j=i+1; j<vertices.size(); j++) {
    			if(vertices.get(i).equals(vertices.get(j))) {
    				assert false;
    			}
    		}
    	}
    }
    
    protected boolean contains(L vertex) {
    	Iterator<Vertex<L> > iter = vertices.iterator();
        while(iter.hasNext()) {
        	Vertex<L> list = iter.next();
        	if(list.getName().equals(vertex)) {
        		return true;
        	}
        }
        return false;
    }
    
    @Override public boolean add(L vertex) {
        try {
        	boolean flag;
        	if(contains(vertex)) {
	        	flag = false;
	        }else{
	        	vertices.add(new Vertex<L>(vertex));
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
    		if(!contains(source)) {
    			if(weight > 0) {
    				add(source);
    				vertices.get(vertices.size()-1).set(target, weight);
    				if(!contains(target)) {
    					add(target);
    				}
    			}else if(weight < 0) {
    				System.out.println("不接受边的权值为负数！");
    			}
	    		checkRep();
	    		return 0;
	    	}
			Iterator<Vertex<L>> iter = vertices.iterator();
			while(iter.hasNext()) {
				Vertex<L> list = iter.next();
				if(list.getName().equals(source)) {
					if(!contains(target)) {
						if(weight > 0) {
							add(target);
							list.set(target, weight);
						}else if(weight < 0) {
							System.out.println("不接受边的权值为负数！");
						}
						checkRep();
						return 0;
					}else {
						int temp = list.getWeight(target);
						if(weight == 0) {
							list.remove(target);
						}else if(weight < 0){
							System.out.println("不接受边的权值为负数！");
						}else {
							list.set(target, weight);
						}
						checkRep();
						return temp;
					}
				}
			}
			return 0;
    	}catch(Exception e) {
    		throw new RuntimeException("not implemented");
    	}
    }
    
    @Override public boolean remove(L vertex) {
        try {
        	if(!contains(vertex)) {
	        	checkRep();
        		return false;
	        }
	        Iterator<Vertex<L> > iter = vertices.iterator();
	        while(iter.hasNext()) {
	        	Vertex<L> list = iter.next();
	        	if(list.getName().equals(vertex)) {
	        		iter.remove();
	        	}else {
	        		list.remove(vertex);
	        	}
	        }
	        checkRep();
	        return true;
        }catch(Exception e) {
    		throw new RuntimeException("not implemented");
    	}
    	
    }
    
    @Override public Set<L> vertices() {
        Set<L> s = new HashSet<>();
    	Iterator<Vertex<L> > iter = vertices.iterator();
        while(iter.hasNext()) {
        	Vertex<L> vertex = iter.next();
        	s.add(vertex.getName());
        }
        return Collections.unmodifiableSet(s);
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	Map<L, Integer> sources = new HashMap<>();
    	for(Vertex<L> list : vertices) {
    		if(!list.getName().equals(target)) {
    			int weight = list.getWeight(target);
    			if(weight > 0) {
    				sources.put(list.getName(), weight);
    			}
    		}
    	}
    	return Collections.unmodifiableMap(sources);
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	for(Vertex<L> list : vertices) {
    		if(list.getName().equals(source)) {
    			return Collections.unmodifiableMap(list.vertices());
    		}
    	}
    	return Collections.emptyMap();
    }
    
    // toString()
    @Override
    public String toString() {
    	String str = "[";
    	for(Vertex<L> vertex : vertices) {
    		if(vertex.toString() != "") str += vertex.toString() + ",";
    	}
    	if(str.endsWith(",")) str = str.substring(0, str.length()-1);
    	str += "]";
    	return str;
    }
    
}

/**
 * A mutable list of map of the target vertex and the weight of the edge.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * @param target the target vertex labels of the edge, must be immutable
 * @param weight the weight of the edge, not less than zero
 */
class Vertex<L> {
    
    // fields
	private final L name;
	private final Map<L, Integer> targets = new HashMap<>();
    
    
    // Abstraction function:
    //   构建当前节点所能到达所有点及对应边的权重的集合
    // Representation invariant:
    //   顶点各不相同
	//   边权重为非负整数
    // Safety from rep exposure:
    //   All fields are private final,
	//   The Set and Map objects in the rep are made immutable by unmodifiable wrappers
    
    // constructor
    public Vertex(L vertex) {
		this.name = vertex;
	}
    // checkRep
    public void checkRep() {
    	Set<L> used = new HashSet<>();
    	for(Map.Entry<L, Integer> kv : targets.entrySet()) {
    		if(used.contains(kv.getKey()) || kv.getValue() < 0) {
    			assert false;
    		}else {
    			used.add(kv.getKey());
    		}
    	}
    }
    
    // methods
    
    public L getName() {
    	return this.name;
    }
	
	public void add(L vertex, int weight) {
		targets.put(vertex, weight);
		checkRep();
	}
	
	public boolean remove(L target) {
		for(Map.Entry<L, Integer> kv : targets.entrySet()) {
			if(kv.getKey().equals(target)) {
				targets.remove(kv.getKey(), kv.getValue());
				checkRep();
				return true; 
			}
		}
		checkRep();
		return false;
	}
	
	public void set(L target, int weight) {
		for(Map.Entry<L, Integer> kv : targets.entrySet()) {
			if(kv.getKey().equals(target)) {
				if(weight == 0) {
					targets.remove(target);
				}else {
					targets.replace(target, weight);
				}
			}
		}
		checkRep();
		if(weight == 0) return;
		targets.put(target,weight);
		checkRep();
	}
	
	public Map<L, Integer> vertices(){
		return Collections.unmodifiableMap(targets);
	}
	
	public int getWeight(L target) {
		int weight = 0;
		for(Map.Entry<L, Integer> kv : targets.entrySet()) {
			if(kv.getKey().equals(target)) {
				weight = kv.getValue();
			}
		}
		return weight;
	}

    // toString()
    @Override
    public String toString() {
    	String str = "";
    	
    	for(Map.Entry<L, Integer> kv : targets.entrySet()) {
        	str += "[" + this.name + "," + kv.getKey() + "," + kv.getValue() + "],";
        }
    	if(targets.size() > 0) str = str.substring(0, str.length()-1);
    	
    	return str;
    }
    
    @Override
    public boolean equals(Object that) {
    	if(!(that instanceof Vertex)) {
    		return false;
    	}else {
    		Vertex<L> temp = (Vertex<L>)that;
    		return this.name.equals(temp.name)
    				&& this.vertices().equals(temp.vertices());
    	}
    }
    
    @Override
    public int hashCode() {
    	return this.name.hashCode() + this.vertices().hashCode();
    }
}
