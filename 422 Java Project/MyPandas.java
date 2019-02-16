import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyPandas {

	public static MyDataFrame readCSV(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String[] headers = br.readLine().split(",");
		String[] types = new String[headers.length];
		for (int col = 0; col < headers.length; col++) {
			types[col] = "String";
		}
		List<List<Object>> listOfLists = new ArrayList<>();
		
		for (int col = 0; col < headers.length; col++) {
			listOfLists.add(new ArrayList<Object>());
		}
		
		String nextLine;
		while ((nextLine = br.readLine()) != null) {
			String[] nextValues = nextLine.split(",");
			for (int col = 0; col < nextValues.length; col++) {
				listOfLists.get(col).add(nextValues[col]);
			}
		}
		
		for (int col = 0; col < listOfLists.size(); col++) {
			boolean isInt = true;
			for (Object o: listOfLists.get(col)) {
				if (MyUtil.tryParseInt(o.toString()) == false) {
					isInt = false;
				}
			}
			if (isInt) {
				types[col] = "Integer";
			}
		}
		br.close();
		
		MyDataFrame df = new MyDataFrame(new ArrayList<String>(Arrays.asList(headers)),
				new ArrayList<String>(Arrays.asList(types)), listOfLists);
		
		return df;
	}
	
	public static MyDataFrame concat(MyDataFrame df1, MyDataFrame df2) {
		ArrayList<String> concatColumns = new ArrayList<String>();
		for (String name: df1.getColumnNames()) {
			concatColumns.add(name);
		}
		for (String name: df2.getColumnNames()) {
			concatColumns.add(name);
		}
		
		ArrayList<String> concatTypes = new ArrayList<String>();
		for (String type: df1.getColumnTypes()) {
			concatTypes.add(type);
		}
		for (String type: df2.getColumnTypes()) {
			concatTypes.add(type);
		}
		
		int concatWidth = df1.getWidth() + df2.getWidth();
		List<List<Object>> concatData = new ArrayList<>();
		for (int col = 0; col < concatWidth; col++) {
			concatData.add(new ArrayList<>());
		}
		
		for (int row = 0; row < Math.min(df1.getLength(), df2.getLength()); row++) {
			for (int col = 0; col < df1.getWidth(); col++) {
				concatData.get(col).add(df1.getIndexedValue(row, col));
			}
			for (int col = df1.getWidth(); col < concatWidth; col++) {
				concatData.get(col).add(df2.getIndexedValue(row, col - df1.getWidth()));
			}
		}
		
		if (df1.getLength() > df2.getLength()) {
			for (int row = df2.getLength(); row < df1.getLength(); row++) {
				for (int col = 0; col < df1.getWidth(); col++) {
					concatData.get(col).add(df1.getIndexedValue(row, col));
				}
				for (int col = df1.getWidth(); col < concatWidth; col++) {
					concatData.get(col).add("NA");
				}
			}
		} else if (df1.getLength() < df2.getLength()) {
			for (int row = df1.getLength(); row < df2.getLength(); row++) {
				for (int col = 0; col < df1.getWidth(); col++) {
					concatData.get(col).add("NA");
				}
				for (int col = df1.getWidth(); col < concatWidth; col++) {
					concatData.get(col).add(df2.getIndexedValue(row, col - df1.getWidth()));
				}
			}
		}
		
		return new MyDataFrame(concatColumns, concatTypes, concatData);
	}
	
}
