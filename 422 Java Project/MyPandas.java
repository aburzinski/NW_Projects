package AliciaEdits;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
			if (MyUtil.tryParseInt(listOfLists.get(col).get(0).toString()) == false) {
					isInt = false;
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
	
	public static void writeCSV(MyDataFrame data, String path) throws IOException {
		String header = "";
		for (String str : data.getColumnNames()) {
			header = header + str + ",";
		}
		header = header.substring(0, header.length() - 1);
		int lengthOfFile = data.getLength();
		String[] writeLines = new String[lengthOfFile];

		//loop over rows
		for (int i = 0; i < lengthOfFile; i++) {
			String newLine = "";
			//loop over columns
			for (int j = 0; j < data.getWidth(); j++) {
				newLine = newLine + data.getIndexedValue(i, j).toString() + ",";
			}
			newLine = newLine.substring(0, newLine.length() - 1);
			writeLines[i] = newLine;
		}
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
		bufferedWriter.write(header+"\n");
		for (String line : writeLines) {
			bufferedWriter.write(line+"\n");
		}
		bufferedWriter.close();

	}
	
	public static MyDataFrame concat(MyDataFrame df1, MyDataFrame df2) {
		ArrayList<String> concatColumns = (ArrayList<String>) df1.getColumnNames();
		ArrayList<String> concatTypes = (ArrayList<String>) df1.getColumnTypes();
		
		List<List<Object>> concatData = df1.data;
		

		for (int row = 0; row < df2.getLength(); row++) {
			for (int col = 0; col < df2.getWidth(); col++) {
				concatData.get(col).add(df2.getIndexedValue(row, col));
			}
		}
		
		
		return new MyDataFrame(concatColumns, concatTypes, concatData);
	}
	
}
