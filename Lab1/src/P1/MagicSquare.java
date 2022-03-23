package P1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MagicSquare {
	private static List<Character> LegalWords = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '\t', '\n');
	private final static int N = 200;
	private static boolean[] used = new boolean[N*N+5] ;
	private static int[][] nums = new int[N][N];

	
    public static void main(String[] args) {
    	try {
			System.out.println(isLegalMagicSquare("./src/P1/txt/1.txt"));
			System.out.println(isLegalMagicSquare("./src/P1/txt/2.txt"));
			System.out.println(isLegalMagicSquare("./src/P1/txt/3.txt"));
			System.out.println(isLegalMagicSquare("./src/P1/txt/4.txt"));
			System.out.println(isLegalMagicSquare("./src/P1/txt/5.txt"));
			System.out.println(isLegalMagicSquare("./src/P1/txt/6.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	generateMagicSquare(7);
    }
    
    public static boolean isLegalMagicSquare(String fileName) throws IOException {
    	
    	//加载文件
    	FileReader file = new FileReader(fileName);
    	BufferedReader br = new BufferedReader(file);
    	
    	String line;
    	String[] words;
    	Arrays.fill(used, false);
       	int k = 0; //遍历行的循环变量
    	int length = 0; //每行的长度
    	int sum = 0;
    	
    	//按行读取
    	while((line = br.readLine()) != null) {
    		//判断是否存在非法字符，主要针对'.',' '
    		for (int i=0; i<line.length(); i++) {
    			if(!LegalWords.contains(line.charAt(i))) {
    				System.out.println("存在非法字符");
    				file.close();
    				br.close();
    				return false;
    			}
    		}
    		
    		words = line.split("\t");
    		if(k == 0) {
    			length = words.length;
    		}else {
    			//是否存在两行元素个数不同
    			if(length != words.length) {
    				System.out.println("不是矩阵");
    				file.close();
    				br.close();
    				return false;
    			}
    		}
    		
    		//分别对每行求和
    		int temp = 0;
    		for (int i=0; i<words.length; i++) {
    			nums[k][i] = Integer.valueOf(words[i]);
    			if(used[nums[k][i]]) {
    				System.out.println("出现了重复元素");
    				file.close();
    				br.close();
    				return false;
    			}else if(nums[k][i]<=0){
    				System.out.println("出现了负数或零");
    				file.close();
    				br.close();
    				return false;
    			}else {
    				used[nums[k][i]] = true;
    			}
    			if(k == 0) {
    				sum += nums[k][i];
    			}else {
    				temp += nums[k][i];
    			}
    		}
    		
    		//是否存在两行和不同
    		if(sum != temp && k != 0) {
    			System.out.println("存在行或列或对角线上元素值之和不相等");
    			file.close();
				br.close();
    			return false;
    		}
    		
    		k++;
    	}
    	
    	//判断行数和列数是否相等
    	if(k != length) {
    		System.out.println("不是矩阵");
    		file.close();
			br.close();
    		return false;
    	}
    	
    	//对列求和
    	for(int i=0; i<length; i++) {
    		int temp = 0;
    		for(int j=0; j<length; j++) {
    			temp += nums[j][i];
    		}
    		//判断当前列所有数之和是否与之前每行的和相等
    		if(sum != temp) {
    			file.close();
				br.close();
    			return false;
    		}
    	}
    	
    	//判断对角线的和是否符合要求
    	int temp1 = 0;
		int temp2 = 0;
    	for(int i=0; i<length; i++) {
    		temp1 += nums[i][i];
    		temp2 += nums[i][length-1-i];
    	}
    	
    	if(sum != temp1 || sum != temp2) {
    		System.out.println("存在行或列或对角线上元素值之和不相等");
    		file.close();
			br.close();
    		return false;
    	}
    	
    	//满足条件
    	file.close();
		br.close();
    	return true;
    }
    
    public static boolean generateMagicSquare(int n) {
    	if(n < 0) {
    		System.out.println("输入不合法（n为负数）");
    		return false;
    	}else if(n % 2 == 0) {
    		System.out.println("输入不合法（n为偶数）");
    		return false;
    	}
    	
    	int magic[][] = new int[n][n];
    	int row = 0, col = n / 2, i, j, square = n * n;
    	for (i = 1; i <= square; i++) {
    		magic[row][col] = i;
    		if (i % n == 0)
    			row++;
    		else {
    			if (row == 0)
    				row = n - 1;
    			else
    				row--;
    			if (col == (n - 1))
    				col = 0;
    			else
    				col++;
    		}
    	}
    	for (i = 0; i < n; i++) {
    		for (j = 0; j < n; j++)
    			System.out.print(magic[i][j] + "\t");
    		System.out.println();
    	}
    	return true;
    }
}
