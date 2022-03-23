package P3;

public class Person {
	private String name;
	private int index = -1;
	
	public Person(String ... name) {
		if(name.length>1) {
			System.out.println("每个人不能有多个姓名！");
		} else {
			this.name = name[0];
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
}
