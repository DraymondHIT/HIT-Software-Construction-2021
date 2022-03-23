package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
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
import interval.PeriodIntervalSet;
import interval.PeriodIntervalSetImpl;

public class CourseIntervalSet {

	private Date startDay = new Date();
	private final int weeks;
	private Set<Course> courses = new HashSet<>();
	private PeriodIntervalSet<Course> CourseTable = new PeriodIntervalSetImpl<>(7, 5);
	private Scanner s = new Scanner(System.in);
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	// Abstraction function:
    //   sdf表示格式化输出日期
	//   startDay是学期开始日期
	//   weeks是周数
	//   CourseTable表示课程安排表
	//   courses是课程表
	//   s是输入流
    // Representation invariant:
    //   
    // Safety from rep exposure:
    //   All fields are private,
    //   The Set and Map objects in the rep are made immutable by unmodifiable wrappers.
	
	public static void main(String[] args) throws Exception {
		
		CourseIntervalSet CourseSchedule = new CourseIntervalSet("./txt/course/test.txt");
		
		CourseSchedule.process();
	}
	
	//constructor
	public CourseIntervalSet(String filename) throws Exception {
		String str = fileToString(filename);
		
		String pattern1 = "Period\\{\\d{4}-\\d{2}-\\d{2},[\\d]+\\}";
		Pattern r1 = Pattern.compile(pattern1);
		
		String pattern2 = "Course\\{[\\s\\S]*\\}";
		Pattern r2 = Pattern.compile(pattern2);
		
		Matcher m = r1.matcher(str);
		
		if(!m.find()) {
	    	throw new Exception("匹配失败！");
	    }else {
	    	String temp = m.group(0);
	    	String startDay = temp.substring(temp.indexOf("{")+1, temp.indexOf(","));
	    	String week = temp.substring(temp.indexOf(",")+1, temp.indexOf("}"));
	    	this.startDay = sdf.parse(startDay);
	    	this.weeks = Integer.parseInt(week);
	    }
		
		m = r2.matcher(str);
	    
	    if(!m.find()) {
	    	throw new Exception("匹配失败！");
	    }else {
	    	String temp = m.group(0);
	    	String pattern_t = "[\\d]{8}\\{[a-zA-Z]+[a-zA-Z ]*,[a-zA-Z]+[a-zA-Z ]*,[\\w ]+,[\\d]+\\}";
	    	Pattern r_t = Pattern.compile(pattern_t);
	    	Matcher m_t = r_t.matcher(temp);
	    	while(m_t.find()) {
	    		String temp_t = m_t.group(0);
	    		String id = temp_t.substring(0, temp_t.indexOf("{"));
	    		temp_t = temp_t.substring(temp_t.indexOf("{")+1);
	    		String name = temp_t.substring(0, temp_t.indexOf(","));
	    		temp_t = temp_t.substring(temp_t.indexOf(",")+1);
	    		String teacher = temp_t.substring(0, temp_t.indexOf(","));
	    		temp_t = temp_t.substring(temp_t.indexOf(",")+1);
	    		String location = temp_t.substring(0, temp_t.indexOf(","));
	    		temp_t = temp_t.substring(temp_t.indexOf(",")+1);
	    		String hours = temp_t.substring(0, temp_t.indexOf("}"));
	    		courses.add(new Course(id, name, teacher, location, Integer.parseInt(hours)));
	    	}
	    }
	}
	
	/**
	 * 根据用户输入的选项进行选择（用户输入的数必须为0-5中的一个）
	 * 1：安排课程 2：查看未安排课程 3：查看每周空闲时间比 4：查看每周重复时间比例 5：查看任意一天课表 0：退出
	 * 
	 * @throws Exception
	 */
	public void process() throws Exception {
		Set<Course> copy = new HashSet<>();
		Iterator<Course> iterator = courses.iterator();
		while(iterator.hasNext()) {
			copy.add(iterator.next());
		}
		int choice = meau();
		while(choice != 0) {
			switch(choice) {
			case 1:
				System.out.println("请输入安排课程的名称:");
				String name = s.next();
				while(!hasCourse(name)) {
					System.out.println("请输入安排课程的名称:");
					name = s.next();
				}
				System.out.println(name);
				
				int total = getCourse(name).getHours();
				while(total > 0) {
					System.out.println("剩余需要安排的学时数："+total);
					System.out.println("请输入星期数（例如：周三则输入3）：");
				    int day = s.nextInt();
				    System.out.println("请输入节数（例如：第三节则输入3）：");
				    int times = s.nextInt();
				    CourseTable.insert(getCourse(name), new Interval<Integer>(times,times,day));
				    total -= 2;
				}
				copy.remove(getCourse(name));
				choice = meau();
				break;
			case 2:
				System.out.println("当前未安排的课程有：");
				Iterator<Course> iter = copy.iterator();
				while(iter.hasNext()) {
					Course temp = iter.next();
					System.out.println(temp.toString());
				}
				choice = meau();
				break;
			case 3:
				System.out.println("当前每周的空闲时间比例："+CourseTable.ratioOfFree());
				choice = meau();
				break;
			case 4:
				System.out.println("当前每周的重复时间比例："+CourseTable.ratioOfOverlap());
				choice = meau();
				break;
			case 5:
				System.out.println("请输入需要查询课表的日期（例如：2021-01-01）：");
				String input = s.next();
				Date date = sdf.parse(input);
				while((date.getTime() - startDay.getTime()) / (24*60*60*1000) > weeks*7) {
					System.out.println("请输入需要查询课表的日期（例如：2021-01-01）：");
					input = s.next();
					date = sdf.parse(input);
				}
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				List<Set<Course>> result = CourseTable.eventsOfDay(cal.get(Calendar.DAY_OF_WEEK)-1);
//				}
				System.out.println("(08-10时):"+result.get(0).toString());
				System.out.println("(10-12时):"+result.get(1).toString());
				System.out.println("(13-15时):"+result.get(2).toString());
				System.out.println("(15-17时):"+result.get(3).toString());
				System.out.println("(19-21时):"+result.get(4).toString());
				choice = meau();
				break;
			default:
				choice = meau();
				break;
			}
		}
		System.out.println("已退出！");
		s.close();
	}
	
	/**
	 * 判断当前课程表中是否有该课程
	 * 
	 * @param name 所需查询的课程名
	 * @return true 课程表中有该课程
	 *         false 课程表中没有该课程
	 */
	private boolean hasCourse(String name) {
		Iterator<Course> iter = courses.iterator();
		while(iter.hasNext()) {
			Course temp = iter.next();
			if(temp.getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据课程名获取课程
	 * 
	 * @param name 课程名
	 * @return 查询到的课程
	 * @throws Exception
	 */
	private Course getCourse(String name) throws Exception {
		Iterator<Course> iter = courses.iterator();
		while(iter.hasNext()) {
			Course temp = iter.next();
			if(temp.getName().equals(name)){
				return temp;
			}
		}
		throw new Exception();
	}
	
	/**
	 * 将文件里的内容转化为字符串
	 * 
	 * @param filename 文件名
	 * @return 表示文件所有内容的字符串
	 * @throws IOException
	 */
	private String fileToString(String filename) throws IOException {
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
	 * 输出菜单
	 * 
	 * @return 用户的选项（0-5）
	 */
	private int meau() {
		System.out.println("菜单：");
		System.out.println("1：安排课程 2：查看未安排课程 3：查看每周空闲时间比 4：查看每周重复时间比例 5：查看任意一天课表 0：退出");
		int choice = s.nextInt();
		return choice;
	}
}

class Course {
	private final String id, name, teacher, location;
	private final int hours;
	
	public Course(String id, String name, String teacher, String location, int hours) {
		this.id = id;
		this.name = name;
		this.teacher = teacher;
		this.location = location;
		this.hours = hours;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTeacher() {
		return teacher;
	}
	
	public String getLocation() {
		return location;
	}
	
	public int getHours() {
		return hours;
	}
	
	public String toString() {
		return "{ID："+id+",课程名："+name+",授课教师："+teacher+",上课地点："+location+",周学时数："+hours+"}";
	}
}