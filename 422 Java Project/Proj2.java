import java.io.IOException;

public class Proj2 {

	public static void main(String[] args) {

		try {
			MyDataFrame df = MyPandas.readCSV("src/Test.csv");
			MyDataFrame df1 = MyPandas.readCSV("src/Test2.csv");
			
//			System.out.println(df);
//			System.out.println(df.head(3));
//			System.out.println(df.tail(4));
//
//			System.out.println(df.dType(0));
//			System.out.println(df.dType(1));
//			System.out.println(df.dType(2));
//			System.out.println("\n");
//			
//			System.out.println(df.dType("Col1"));
//			System.out.println(df.dType("Col2"));
//			System.out.println(df.dType("Col3"));
//			System.out.println("\n");
//
//			System.out.println(df.slice(1));
//			System.out.println(df.slice("Col3"));
//			
//			System.out.println(df.slice(new String[] {"Col1", "Col3"}));
//			System.out.println(df.slice(new int[] {1, 2}));
//			
//			System.out.println(df.loc(3));
//			
//			System.out.println(df.getIndexedValue(3, 0));
			System.out.println(MyPandas.concat(df1, df));
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
