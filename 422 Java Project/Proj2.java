package AliciaEdits;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class Proj2 {

	public static void main(String[] args) {
		
		

		try {
			MyDataFrame al = MyPandas.readCSV("src/AL.csv");
			MyDataFrame ak = MyPandas.readCSV("src/AK.csv");
//			long totalTime = 0;
//			for (int i = 0; i < 10; i++) {
//				long startTime = System.nanoTime();
//				System.out.println(al.filter("Year", ">", 2000));
//				long endTime = System.nanoTime();
//				totalTime += (endTime - startTime);
//			}
//			System.out.println("Took " + totalTime / 100 + " ns");
//		    
			File f = new File("src");

		    FilenameFilter textFilter = new FilenameFilter() {
		        public boolean accept(File dir, String name) {
		            return name.toLowerCase().endsWith(".csv");
		        }
		    };

		    File[] files = f.listFiles(textFilter);
		    for (File file : files) {
		    	boolean test = !(new String("AL.csv").equals(file.getName())) & !(new String("dfNew.csv").equals(file.getName()));
		    	test = false;
		    	if (test) {
					MyDataFrame dfTemp = MyPandas.readCSV("src/" + file.getName());
					al = MyPandas.concat(al, dfTemp);

		    	}
		    }
		    MyDataFrame dfSorted = al.sort(3);
		    System.out.println(dfSorted.head(10));
		    System.out.println(al.filter("Births", "<", "55").head(5));
		    testEverything(al);
		    
		    testAllFiles();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static void testAllFiles() throws IOException {
		FilenameFilter textFilter = new FilenameFilter() {
	        public boolean accept(File dir, String name) {
	            return name.toLowerCase().endsWith(".csv") && !name.endsWith("AL.csv");
	        }
	    };
	    File dir = new File("src");
	    File[] files = dir.listFiles(textFilter);
	    MyDataFrame allStates = MyPandas.readCSV("src/AL.csv");
	    for (File file: files) {
	    	System.out.println(file.getParent() + "/" + file.getName());
	    	allStates = MyPandas.concat(allStates, MyPandas.readCSV(file.getParent() + "/" +
	    			file.getName()).filter("Year", ">=", 1975));
	    }
	    System.out.println(allStates);
	}
	
	private static void testEverything(MyDataFrame df) throws IOException {
	    testReadCSV("src/AK.csv");
	    testWriteCSV(df,"src/dfNew.csv");
		MyDataFrame df1 = MyPandas.readCSV("src/AK.csv");
		MyDataFrame df2 = MyPandas.readCSV("src/AL.csv");
	    testConcat(df1,df2);
		testHead(df,6);
		testTail(df,6);
		testdTypeIndex(df,0);
		testdTypeString(df,"Births");
		testSliceIndex(df,0);
		testSliceIndex(df,"Births");
		testSliceIndexArray(df,new int[] {0,1,2});
		testSliceIndexArray(df,new String[] {"Births","Year","State"});
		//test filter
		testLoc(df,100);
		testLoc(df,100,500);
		//test sort index
		//test sort name			
		testGetMin(df,2);
		testGetMin(df,"Births");
		testGetMax(df,2);
		testGetMax(df,"Births");
	}
	
	private static void testReadCSV(String path) {
		MyDataFrame df = null;
		try {
			df = MyPandas.readCSV(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Top 10 rows from data frame genderated by csv file located at specified path: ");
		System.out.println(df.head(10));
	}
	
	private static void testWriteCSV(MyDataFrame df, String path) {
		try {
			MyPandas.writeCSV(df,path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void testConcat(MyDataFrame df1, MyDataFrame df2) {
		MyDataFrame newDF = MyPandas.concat(df1, df2);
		System.out.println("First 10 rows of the merged data frame are: ");
		System.out.println(newDF.head(10));
		System.out.println("Last 10 rows of the merged data frame are: ");
		System.out.println(newDF.tail(10));
	}
	
	private static void testHead(MyDataFrame df, int n) {
		MyDataFrame testDf = df.head(n);
		System.out.println("First " + n + " rows of the data frame are: ");
		System.out.println(testDf);
	}
	
	private static void testTail(MyDataFrame df, int n) {
		MyDataFrame testDf = df.tail(n);
		System.out.println("Last " + n + " rows of the data frame are: ");
		System.out.println(testDf);
	}
	
	private static void testdTypeIndex(MyDataFrame df, int n) {
		String testType = df.dType(n);
		System.out.println("The data type of the column in position " + n + " is: " + testType);
	}
	
	private static void testdTypeString(MyDataFrame df, String name) {
		String testType = df.dType(name);
		System.out.println("The data type of the column with label " + name + " is: " + testType);
	}
	
	private static void testSliceIndex(MyDataFrame df, int n) {
		MyDataFrame testDf = df.slice(n).head(10);
		System.out.println("The first 10 entries of the column at position " + n + " are: ");
		System.out.println(testDf);
	}
	
	private static void testSliceIndex(MyDataFrame df, String name) {
		MyDataFrame testDf = df.slice(name).head(10);
		System.out.println("The first 10 entries of the column with label " + name + " are: ");
		System.out.println(testDf);
	}
	
	private static void testSliceIndexArray(MyDataFrame df, int[] indexArr) {
		MyDataFrame testDf = df.slice(indexArr).head(10);
		System.out.println("The first 10 entries of the columns at the positions specified are: ");
		System.out.println(testDf);
	}
	
	private static void testSliceIndexArray(MyDataFrame df, String[] nameArr) {
		MyDataFrame testDf = df.slice(nameArr).head(10);
		System.out.println("The first 10 entries of the columns at the names specified are: ");
		System.out.println(testDf);
	}
	
	private static void testLoc(MyDataFrame df, int index) {
		MyDataFrame testDf = df.loc(index);
		System.out.println("The first 10 entries of the dataframe starting at row " + index + " are: ");
		System.out.println(testDf.head(10));
	}
	
	private static void testLoc(MyDataFrame df, int to, int from) {
		MyDataFrame testDf = df.loc(to,from);
		System.out.println("The first 10 entries of the dataframe starting at row " + to + " are: ");
		System.out.println(testDf.head(10));
		System.out.println("The last 10 entries of the dataframe ending at row " + from + " are: ");
		System.out.println(testDf.tail(10));
	}
	
	private static void testGetMin(MyDataFrame df, int index) {
		Object o = df.getMin(index);
		System.out.println("The smallest value of the dataframe of the column at index " + index + " is: " + o);
	}
	
	
	private static void testGetMin(MyDataFrame df, String name) {
		Object o = df.getMin(name);
		System.out.println("The smallest value of the dataframe of the column with label " + name + " is: " + o);
	}
	
	
	private static void testGetMax(MyDataFrame df, int index) {
		Object o = df.getMax(index);
		System.out.println("The largest value of the dataframe of the column at index " + index + " is: " + o);
	}
	
	
	private static void testGetMax(MyDataFrame df, String name) {
		Object o = df.getMax(name);
		System.out.println("The largest value of the dataframe of the column with label " + name + " is: " + o);
	}
}
