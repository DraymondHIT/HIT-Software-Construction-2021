package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interval.Interval;
import interval.NonOverlapIntervalSet;
import interval.NonOverlapIntervalSetImpl;

public class ProcessIntervalSet {
	
	//fields
	private List<Process> processes = new ArrayList<>();
	private NonOverlapIntervalSet<String> ProcessTable = new NonOverlapIntervalSetImpl<>();
	private Scanner s = new Scanner(System.in);
	
	// Abstraction function:
	//   ProcessTable表示进程安排
	//   processes是进程表
	//   s是输入流
    // Representation invariant:
	//   TimeTable中不允许overlap
    // Safety from rep exposure:
    //   All fields are private,
    //   The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
	
	public static void main(String[] args) throws Exception {
		
		ProcessIntervalSet ProcessSchedule = new ProcessIntervalSet("./txt/process/test.txt");
		
//		ProcessSchedule.randomSet();
		ProcessSchedule.bestSet();
		
		System.out.println(ProcessSchedule.toString());
		
		ProcessSchedule.show(100);
	}
	
	//checkRep
	public void checkRep() throws Exception {
		ProcessTable.checkNoOverlap("DISABLE");
	}
	
	/**
	 * 根据用户输入的选项进行选择（用户输入的数必须为0-3中的一个）
	 * 1：随机选择进程 2：最短进程优先 3：查看当前时刻之前的进程调度结果 0：退出
	 * 
	 * @throws Exception
	 */
	public void process() throws Exception {
		int choice = meau();
		while(choice != 0) {
			switch(choice) {
			case 1:
				ProcessTable = new NonOverlapIntervalSetImpl<>();
				randomSet();
				choice = meau();
				break;
			case 2:
				ProcessTable = new NonOverlapIntervalSetImpl<>();
				bestSet();
				choice = meau();
				break;
			case 3:
				System.out.println("请输入当前时刻数（自然数）：");
				int time = s.nextInt();
				show(time);
				choice = meau();
				break;
			default:
				choice = meau();
				break;
			}
		}
		System.out.println("已退出！");
		s.close();
		checkRep();
	}
	
	//constructor
	public ProcessIntervalSet(String filename) throws Exception {
		String str = fileToString(filename);
		
		String pattern1 = "Process\\{[\\s\\S]*\\}";
		Pattern r1 = Pattern.compile(pattern1);
		
		Matcher m = r1.matcher(str);
	    
	    if(!m.find()) {
	    	throw new Exception("匹配失败！");
	    }else {
	    	String temp = m.group(0);
	    	String pattern_t = "\\d{8}-[\\w]+\\{[\\d]+,[\\d]+\\}";
	    	Pattern r_t = Pattern.compile(pattern_t);
	    	Matcher m_t = r_t.matcher(temp);
	    	while(m_t.find()) {
	    		String temp_t = m_t.group(0);
	    		String id = temp_t.substring(0, temp_t.indexOf("-"));
	    		String name = temp_t.substring(temp_t.indexOf("-")+1, temp_t.indexOf("{"));
	    		String min = temp_t.substring(temp_t.indexOf("{")+1, temp_t.indexOf(","));
	    		String max = temp_t.substring(temp_t.indexOf(",")+1, temp_t.indexOf("}"));
	    		processes.add(new Process(id, name, Integer.parseInt(min), Integer.parseInt(max)));
	    	}
	    }
	    checkRep();
	}
	
	/**
	 * 随机安排进程
	 * 
	 * @throws Exception
	 */
	public void randomSet() throws Exception {
		List<Process> temp = new ArrayList<>();
		for(int i=0; i<processes.size(); i++) {
			temp.add(processes.get(i));
		}
		int size = temp.size();
		int start = 0, end;
		while(size > 0) {
			int index = (int) (Math.random() * (size + 1));
			int length;
			if(index == size) {
				length = (int) (Math.random() * (10 - 5 + 1) + 5);
				end = start + length;
			}else {
				Process process = temp.get(index);
				length = (int) (Math.random() * (process.getMaxTime() + 1));
				end = start + length;
				ProcessTable.insert(start, end, process.getId(), "DISABLE");
				if(length < process.getMinTime()) {
					String id = process.getId();
					String name = process.getName();
					int min = process.getMinTime() - length;
					int max = process.getMaxTime() - length;
					temp.remove(index);
					temp.add(new Process(id, name, min, max));
				}else {
					temp.remove(index);
					size--;
				}
			}
			start = end + 1;
		}
		checkRep();
	}
	
	/**
	 * 最短进程优先
	 * 
	 * @throws Exception
	 */
	public void bestSet() throws Exception {
		List<Process> temp = new ArrayList<>();
		for(int i=0; i<processes.size(); i++) {
			temp.add(processes.get(i));
		}
		int size = temp.size();
		int start = 0, end;
		while(size > 0) {
			int index=0, length, minTime=Integer.MAX_VALUE;
			for(int i=0; i<size; i++) {
				if(temp.get(i).getMaxTime() < minTime) {
					minTime = temp.get(i).getMaxTime();
					index = i;
				}
			}
			
			Process process = temp.get(index);
			length = (int) (Math.random() * (process.getMaxTime() + 1));
			end = start + length;
			ProcessTable.insert(start, end, process.getId(), "DISABLE");
			if(length < process.getMinTime()) {
				String id = process.getId();
				String name = process.getName();
				int min = process.getMinTime() - length;
				int max = process.getMaxTime() - length;
				temp.remove(index);
				temp.add(new Process(id, name, min, max));
			}else {
				temp.remove(index);
				size--;
			}
			start = end + 1;
		}
		checkRep();
	}
	
	/**
	 * 可视化当前时刻之前的进程调度结果
	 * 
	 * @param time 当前时刻（非负数）
	 */
	public void show(int time) {
		List<Interval<String>> list = new ArrayList<>();
		
		list = ProcessTable.all();

		String str = "";
		
		for(int i=0; i<list.size(); i++) {
			if(time < list.get(i).getStart()) {
				str += "当前进程：无";
				break;
			}
			str += "(";
			str += list.get(i).getStart();
			str += ",";
			if(time <= list.get(i).getEnd()) {
				str += time;
				str += ") id:" + list.get(i).getLabel() + "\n";
				str += "当前进程：" + list.get(i).getLabel();
				break;
			}else {
				str += list.get(i).getEnd();
				str += ") id:" + list.get(i).getLabel() + "\n";
			}
			
		}
		
		if(list.isEmpty()) {
			str += "当前进程：无";
		}else if(!list.isEmpty() && time > list.get(list.size()-1).getEnd()) {
			str += "当前进程：无";
		}
		
		System.out.println(str);
	}
	
	/**
	 * 输出菜单
	 * 
	 * @return 用户的选项（0-3）
	 * @throws Exception
	 */
	private int meau() {
		System.out.println("菜单：");
		System.out.println("1：随机选择进程 2：最短进程优先 3：查看当前时刻之前的进程调度结果 0：退出");
		int choice = s.nextInt();
		return choice;
	}
	
	/**
	 * 将文件里的内容转化为字符串
	 * 
	 * @param filename 文件名
	 * @return 表示文件所有内容的字符串
	 * @throws IOException
	 */
	public static String fileToString(String filename) throws IOException {
		File file = new File(filename);
	    if(!file.exists()){
	        return null;
	    }
	    FileInputStream inputStream = new FileInputStream(file);
	    int length = inputStream.available();
	    byte bytes[] = new byte[length];
	    inputStream.read(bytes);
	    inputStream.close();
	    String str = new String(bytes, StandardCharsets.UTF_8);
	    return str ;
	}
	
	/**
	 * 格式化输出进程调度情况
	 * 
	 */
	public String toString() {
		List<Interval<String>> list = new ArrayList<>();
	
		list = ProcessTable.all();

		String str = "";
		
		for(int i=0; i<list.size(); i++) {
			str += "(";
			str += list.get(i).getStart();
			str += ",";
			str += list.get(i).getEnd();
			str += ") id:" + list.get(i).getLabel() + "\n";
		}
		
		return str;
		
	}
}

class Process {
	private final String id, name;
	private final int minTime, maxTime;
	
	public Process(String id, String name, int min, int max) {
		this.id = id;
		this.name = name;
		this.minTime = min;
		this.maxTime = max;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMinTime() {
		return minTime;
	}
	
	public int getMaxTime() {
		return maxTime;
	}
	
	public String toString() {
		return "{"+id+","+name+","+minTime+","+maxTime+"}";
	}
}