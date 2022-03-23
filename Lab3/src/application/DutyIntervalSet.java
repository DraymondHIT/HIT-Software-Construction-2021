package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interval.Interval;
import interval.NonOverlapIntervalSet;
import interval.NonOverlapIntervalSetImpl;

public class DutyIntervalSet {
	
	//fields
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Date startDay = new Date();
	private Date endDay = new Date();
	private final long period;
	private NonOverlapIntervalSet<Employee> TimeTable = new NonOverlapIntervalSetImpl<>();
	private Set<Employee> EmployeeTable = new HashSet<>();
	private Scanner s = new Scanner(System.in);
	
	// Abstraction function:
    //   sdf表示格式化输出日期
	//   startDay是开始日期
	//   endDay是结束日期
	//   period是时间间隔
	//   TimeTable表示排班表
	//   EmployeeTable是员工表
	//   s是输入流
    // Representation invariant:
    //   period应该等于startDay和endDay的日期差
	//   TimeTable中不允许overlap
    // Safety from rep exposure:
    //   All fields are private,
    //   The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
	
	public static void main(String[] args) throws Exception {
		
		DutyIntervalSet DutyRoster = new DutyIntervalSet("./txt/duty/test.txt");
		
		System.out.println(DutyRoster.toString());
		
		DutyRoster.checkFull();
	}
	
	public void checkRep() throws Exception {
		assert(period == (int) ((endDay.getTime() - startDay.getTime()) / (24*60*60*1000)));
		TimeTable.checkNoOverlap("DISABLE");
	}
	
	//constructor
	public DutyIntervalSet(String filename) throws Exception {
		String str = fileToString(filename);
		
		String pattern1 = "Period\\{.*\\}";
		Pattern r1 = Pattern.compile(pattern1);
		
		String pattern2 = "Employee\\{[\\s\\S]*\\}";
		Pattern r2 = Pattern.compile(pattern2);
		
		String pattern3 = "Roster\\{[\\s\\S]*\\}";
		Pattern r3 = Pattern.compile(pattern3);
		
	    Matcher m = r1.matcher(str);
	    
	    if(!m.find()) {
	    	throw new Exception("匹配失败！");
	    }else {
	    	String temp = m.group(0);
	    	String pattern_t = "\\d{4}-\\d{2}-\\d{2}";
	    	Pattern r_t = Pattern.compile(pattern_t);
	    	Matcher m_t = r_t.matcher(temp);
	    	m_t.find();
	    	setStartDay(m_t.group(0));
	    	m_t.find();
	    	setEndDay(m_t.group(0));
			period = calculatePeriod(startDay, endDay);
	    }
		
	    m = r2.matcher(str);
	    
	    if(!m.find()) {
	    	throw new Exception("匹配失败！");
	    }else {
	    	String temp = m.group(0);
	    	String pattern_t = "[a-zA-Z]+\\{[a-zA-Z]+[a-zA-Z ]*,\\d{3}-\\d{4}-\\d{4}\\}";
	    	Pattern r_t = Pattern.compile(pattern_t);
	    	Matcher m_t = r_t.matcher(temp);
	    	while(m_t.find()) {
	    		String temp_t = m_t.group(0);
	    		String name = temp_t.substring(0, temp_t.indexOf("{"));
	    		String position = temp_t.substring(temp_t.indexOf("{")+1, temp_t.indexOf(","));
	    		String tel = temp_t.substring(temp_t.indexOf(",")+1, temp_t.indexOf("}"));
	    		EmployeeTable.add(new Employee(name, position, tel));
	    	}
	    }
	    
	    m = r3.matcher(str);
	    
	    if(!m.find()) {
//	    	throw new Exception("匹配失败！");
	    }else {
	    	String temp = m.group(0);
	    	String pattern_t = "[a-zA-Z]+\\{\\d{4}-\\d{2}-\\d{2},\\d{4}-\\d{2}-\\d{2}\\}";
	    	Pattern r_t = Pattern.compile(pattern_t);
	    	Matcher m_t = r_t.matcher(temp);
	    	while(m_t.find()) {
	    		String temp_t = m_t.group(0);
	    		String name = temp_t.substring(0, temp_t.indexOf("{"));
	    		String start = temp_t.substring(temp_t.indexOf("{")+1, temp_t.indexOf(","));
	    		String end = temp_t.substring(temp_t.indexOf(",")+1, temp_t.indexOf("}"));
	    		set(name, start, end);
	    	}
	    }
	    checkRep();
	}
	
	/**
	 * 根据用户输入的选项进行选择（用户输入的数必须为0-4中的一个）
	 * 1：手动排班 2：随机排班 3：查看是否排满 4：查看当前排班情况 0：退出
	 * 
	 * @throws ParseException
	 * @throws Exception
	 */
	public void process() throws ParseException, Exception {
		int choice = meau();
		while(choice != 0) {
			switch(choice) {
			case 1:
				System.out.println("请输入员工姓名：");
				String name = s.next();
				while(!hasEmployee(name)) {
					System.out.println("请输入员工姓名：");
					name = s.next();
				}
				System.out.println("请输入值班开始时间：");
				String startTime = s.next();
				System.out.println("请输入值班结束时间：");
				String endTime = s.next();
				set(name, startTime, endTime);
				choice = meau();
				break;
			case 2:
				TimeTable = new NonOverlapIntervalSetImpl<>();
				List<Employee> temp = new ArrayList<>();
		    	Iterator<Employee> iter = EmployeeTable.iterator();
		    	while(iter.hasNext()) {
		    		Employee employee = iter.next();
		    		temp.add(employee);
		    	}
		    	long start = 0;
		    	while(start < period) {
		    		long end;
		    		if(temp.size() == 1) {
		    			end = period;
		    		}else {
		    			end = (long) (Math.random() * (period - start + 1) + start);
		    		}
		    		Employee employee_t = temp.get(0);
		    		temp.remove(0);
		    		Calendar c = Calendar.getInstance();
		    		c.setTime(startDay);
					c.add(Calendar.DATE, (int) start);
					String startDate = sdf.format(c.getTime());
					c.setTime(startDay);
					c.add(Calendar.DATE, (int) end);
					String endDate = sdf.format(c.getTime());
		    		set(employee_t.getName(), startDate, endDate);
		    		start = Math.min(end+1, period);
		    		if(end+1 == period) {
		    			c.setTime(startDay);
						c.add(Calendar.DATE, (int) period);
		    			set(temp.get(0).getName(), sdf.format(c.getTime()), sdf.format(c.getTime()));
		    		}
		    	}
		    	choice = meau();
				break;
			case 3:
				checkFull();
				choice = meau();
				break;
			case 4:
				System.out.println("当前排班情况：");
				System.out.println(toString());
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
	
	/**
	 * 设置开始日期
	 * 
	 * @param date 日期的格式化表示
	 * @throws ParseException
	 */
	private void setStartDay(String date) throws ParseException {
		startDay = sdf.parse(date);
	}
	
	/**
	 * 设置结束日期
	 * 
	 * @param date 日期的格式化表示
	 * @throws ParseException
	 */
	private void setEndDay(String date) throws ParseException {
		endDay = sdf.parse(date);
	}
	
	/**
	 * 计算period
	 * 
	 * @param startDay 开始日期
	 * @param endDay 结束日期
	 * @return 日期差
	 */
	private int calculatePeriod(Date startDay, Date endDay) {
		return (int) ((endDay.getTime() - startDay.getTime()) / (24*60*60*1000));
	}
	
	/**
	 * 进行手动排班
	 * 
	 * @param name 员工姓名
	 * @param start 排班开始日期
	 * @param end 排班结束日期
	 * @throws ParseException
	 * @throws Exception
	 */
	private void set(String name, String start, String end) throws ParseException, Exception {
		Iterator<Employee> iter = EmployeeTable.iterator();
		Employee emp = new Employee();
		while(iter.hasNext()) {
			emp = iter.next();
			if(emp.getName().equals(name)) {
				TimeTable.insert(calculatePeriod(startDay, sdf.parse(start)), calculatePeriod(startDay, sdf.parse(end)), emp, "DISABLE");
				checkRep();
				return;
			}
		}
		checkRep();
	}
	
	/**
	 * 将文件里的内容转化为字符串
	 * 
	 * @param filename 文件名
	 * @return 表示文件所有内容的字符串
	 * @throws IOException
	 */
	private static String fileToString(String filename) throws IOException {
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
	 * 查看当前排班表是否排满，
	 * 如果未排满，则输出未排班的时间段
	 * 
	 * @throws Exception
	 */
	private void checkFull() throws Exception {
		List<Interval<Employee>> list = new ArrayList<>();
		List<Interval<Integer>> freelist = new ArrayList<>();
 		
		list = TimeTable.all();
		int num = 0;
		
		if(list.get(0).getStart() > 0) {
			freelist.add(new Interval<Integer>(0, list.get(0).getStart()-1, num++));
		}
		for(int i=1; i<list.size(); i++) {
			if(list.get(i).getStart() - list.get(i-1).getEnd() > 1) {
				freelist.add(new Interval<Integer>(list.get(i-1).getEnd()+1, list.get(i).getStart()-1, num++));
			}
		}
		if(list.get(list.size()-1).getEnd() < period) {
			freelist.add(new Interval<Integer>(list.get(list.size()-1).getEnd()+1, period, num++));
		}
		
		if(freelist.isEmpty()){
			System.out.println("已排满！");
		}else {
			System.out.println("未排班时间如下：");
			Calendar c = Calendar.getInstance();
			for(int i=0; i<freelist.size(); i++) {
				String str = i + ":(";
				c.setTime(startDay);
				c.add(Calendar.DATE, (int)freelist.get(i).getStart());
				str += sdf.format(c.getTime());
				str += ",";
				c.setTime(startDay);
				c.add(Calendar.DATE, (int)freelist.get(i).getEnd());
				str += sdf.format(c.getTime());
				str += ")";
				System.out.println(str);
			}
		}
		checkRep();
	}
	
	/**
	 * 输出菜单
	 * 
	 * @return 用户的选项（0-4）
	 * @throws Exception
	 */
	private int meau() throws Exception {
		System.out.println("菜单：");
		System.out.println("1：手动排班 2：随机排班 3：查看是否排满 4：查看当前排班情况 0：退出");
		int choice = s.nextInt();
		checkRep();
		return choice;
	}
	
	/**
	 * 判断当前员工表中是否有该员工
	 * 
	 * @param name 所需查询的员工姓名
	 * @return true 员工表中有该员工
	 *         false 员工表中没有该员工
	 */
	private boolean hasEmployee(String name) {
		Iterator<Employee> iter = EmployeeTable.iterator();
		Employee emp = new Employee();
		while(iter.hasNext()) {
			emp = iter.next();
			if(emp.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 格式化输出当前排班情况
	 * 
	 */
	public String toString() {
		List<Interval<Employee>> list = new ArrayList<>();
	
		list = TimeTable.all();

		Calendar c = Calendar.getInstance();
		String str = "";
		
		for(int i=0; i<list.size(); i++) {
			str += "(";
			c.setTime(startDay);
			c.add(Calendar.DATE, (int)list.get(i).getStart());
			str += sdf.format(c.getTime());
			str += ",";
			c.setTime(startDay);
			c.add(Calendar.DATE, (int)list.get(i).getEnd());
			str += sdf.format(c.getTime());
			str += "): " + list.get(i).getLabel().toString() + "\n";
		}
		return str;
	}
	
}

class Employee {
	private final String name, position, tel;
	
	public Employee() {
		name = "";
		position = "";
		tel = "";
	}
	
	public Employee(String name, String position, String tel) {
		this.name = name;
		this.position = position;
		this.tel = tel;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return "{"+name+","+position+","+tel+"}";
	}
}
