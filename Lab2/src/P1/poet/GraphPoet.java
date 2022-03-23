/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   利用一个有向加权图往输入字符串中添加新词
    // Representation invariant:
    //   只要满足Graph<String>的RI即可
    // Safety from rep exposure:
    //   All fields are private final.
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
    	FileReader file = new FileReader(corpus);
    	BufferedReader br = new BufferedReader(file);
    	
    	String line;
    	String[] words;
    	while((line = br.readLine()) != null) {
    		char endWord = line.charAt(line.length()-1);
    		if(endWord < 'a' || endWord > 'z') line = line.substring(0, line.length()-1);
    		words = line.split(" ");
    		for(int i=0; i<words.length-1; i++) {
    			int lastWeight = graph.set(words[i].toLowerCase(), words[i+1].toLowerCase(), 1);
    			if(lastWeight > 0) {
    				graph.set(words[i].toLowerCase(), words[i+1].toLowerCase(), lastWeight + 1);
    			}
    		}
    	}
    	
    	file.close();
    	br.close();
    }
    
    // TODO checkRep
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
    	if(input == "") return "";
    	char endWord = input.charAt(input.length()-1);
    	if(endWord < 'a' || endWord > 'z') input = input.substring(0, input.length()-1);
        String[] words = input.split(" ");
        String str = "";
        Map<String, Integer> targets = new HashMap<>();
        Map<String, Integer> sources = new HashMap<>();
        for(int i=0; i<words.length-1; i++) {
        	str += words[i] + " ";
        	targets = graph.targets(words[i].toLowerCase());
    		sources = graph.sources(words[i+1].toLowerCase());
    		Set<String> intersection = new HashSet<>();
    		intersection.addAll(sources.keySet());
    		intersection.retainAll(targets.keySet());
    		int maxBridge = Integer.MIN_VALUE;
			String bridge = "";
			for (String key : intersection) {
				if (sources.get(key) + targets.get(key) > maxBridge) {
					maxBridge = sources.get(key) + targets.get(key);
					bridge = key;
				}
			}
			if(!bridge.isEmpty()) str += bridge + " ";
        }
        str += words[words.length-1] + ".";
        return str;
    }
    
    // toString()
    public String toString() {
    	return graph.toString();
    }
    
}
