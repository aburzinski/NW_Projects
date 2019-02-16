package AliciaEdits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class MyDataFrame {
	private List<String> columnNames;
	private List<Integer> index;
	private List<String> columnTypes;
	public List<List<Object>> data;
	private int length;
	private int width;
	
	public MyDataFrame(ArrayList<String> columnNames, ArrayList<String> columnTypes,
			List<List<Object>> columns) {
		this.columnNames = columnNames;
		this.columnTypes = columnTypes;
		this.data = columns;
		
		this.index = new ArrayList<Integer>();D
		for (int col = 0; col < columnNames.size(); col++) {
			this.index.add(col);
		}
		
		this.length = this.data.get(0).size();
		this.width = this.columnNames.size();
	}
	
	private String getRow(int rowIndex, List<Integer> columnIndex) {
		String row = "";
		int length = 0;
		for (int col: columnIndex) {
			row += this.data.get(col).get(rowIndex).toString();
			length += 1;
			if (length < columnIndex.size()) {
				row += ", ";
			}
		}
		return row;
	}
	
	private int getColumnIndex(String name) {
		int rtn = -1;
		for (int col = 0; col < this.width; col++) {
			if (this.columnNames.get(col).equals(name)) {
				rtn = col;
			}
		}
		return rtn;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.columnNames.get(0));
		for (int col = 1; col < this.width; col++) {
			sb.append(", " + this.columnNames.get(col));
		}
		sb.append("\n"); 
		for (int row = 0; row < this.length; row++) {
			sb.append(this.getRow(row, this.index) + "\n");
		}
		return sb.toString();
	}
	
	public List<String> getColumnNames() {
		return this.columnNames;
	}
	
	public List<String> getColumnTypes() {
		return this.columnTypes;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public Object getIndexedValue(int row, int col) {
		return this.data.get(col).get(row);
	}
	
	public MyDataFrame head(int n) {
		List<List<Object>> newData = new ArrayList<>();
		
		for (int col = 0; col < this.width; col++) {
			newData.add(new ArrayList<Object>());
		}
		
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < this.width; col++) {
				newData.get(col).add(this.data.get(col).get(row));
			}
		}
		
		return new MyDataFrame(new ArrayList<String>(this.columnNames), new ArrayList<String>(this.columnTypes), newData);
	}
	
	public MyDataFrame tail(int n) {
		List<List<Object>> newData = new ArrayList<>();
		
		for (int col = 0; col < this.width; col++) {
			newData.add(new ArrayList<Object>());
		}
		
		for (int row = this.length - n; row < this.length; row++) {
			for (int col = 0; col < this.width; col++) {
				newData.get(col).add(this.data.get(col).get(row));
			}
		}
		
		return new MyDataFrame(new ArrayList<String>(this.columnNames), new ArrayList<String>(this.columnTypes), newData);
	}
	
	public String dType(int col) {
		return this.columnTypes.get(col);
	}
	
	public String dType(String name) {
		return this.columnTypes.get(this.getColumnIndex(name));
	}
	
	public MyDataFrame slice(int columnIndex) {
		List<List<Object>> slicedData = new ArrayList<>();
		slicedData.add(new ArrayList<>());
		for (int row = 0; row < this.length; row++) {
			slicedData.get(0).add(this.data.get(columnIndex).get(row));
		}
		return new MyDataFrame(new ArrayList<String>(Arrays.asList(this.columnNames.get(columnIndex))),
				new ArrayList<String>(Arrays.asList(this.columnTypes.get(columnIndex))), slicedData);
	}
	
	public MyDataFrame slice(String columnName) {
		int columnIndex = this.getColumnIndex(columnName);
		return this.slice(columnIndex);
	}
	
	public MyDataFrame slice(int[] columnIndices) {
		List<List<Object>> slicedData = new ArrayList<>();
		for (int col = 0; col < columnIndices.length; col++) {
			slicedData.add(new ArrayList<>());
		}
		for (int row = 0; row < this.length; row++) {
			for (int col = 0; col < columnIndices.length; col++) {
				slicedData.get(col).add(this.data.get(columnIndices[col]).get(row));
			}
		}
		
		ArrayList<String> slicedNames = new ArrayList<>();
		ArrayList<String> slicedTypes = new ArrayList<>();
		for (int col: columnIndices) {
			slicedNames.add(this.columnNames.get(col));
			slicedTypes.add(this.columnTypes.get(col));
		}
		return new MyDataFrame(slicedNames, slicedTypes, slicedData);
	}
	
	public MyDataFrame slice(String[] columnNames) {
		int[] columnIndices = new int[columnNames.length];
		for (int col = 0; col < columnNames.length; col++) {
			columnIndices[col] = this.getColumnIndex(columnNames[col]);
		}
		return this.slice(columnIndices);
	}
	
	public MyDataFrame filter(String col, String op, Object o) {
		int colIndex = getColumnIndex(col);
		ArrayList<Integer> colList = new ArrayList<Integer>();
		colList.add(colIndex);
		ArrayList<Integer> rowList = new ArrayList<Integer>();
		
		switch (op) {
		case "<":
			String s = o.toString();
			int newInt = Integer.parseInt(s);
			for (int row = 0; row < this.length; row++) {
				Object obj = this.getRow(row, colList);
				String s1 = obj.toString();
				int newInt1 = Integer.parseInt(s1);
				if (newInt1 < newInt) {
					rowList.add(row);
				}
			}
			break;
		case "<=":
			String s2 = o.toString();
			int newInt2 = Integer.parseInt(s2);
			for (int row = 0; row < this.length; row++) {
				Object obj = this.getRow(row, colList);
				String s1 = obj.toString();
				int newInt1 = Integer.parseInt(s1);
				if (newInt1 <= newInt2) {
					rowList.add(row);
				}
			}
			break;
		case ">":
			String s3 = o.toString();
			int newInt3 = Integer.parseInt(s3);
			for (int row = 0; row < this.length; row++) {
				Object obj = this.getRow(row, colList);
				String s1 = obj.toString();
				int newInt1 = Integer.parseInt(s1);
				if (newInt1 > newInt3) {
					rowList.add(row);
				}
			}
			break;
		case ">=":
			String s4 = o.toString();
			int newInt4 = Integer.parseInt(s4);
			for (int row = 0; row < this.length; row++) {
				Object obj = this.getRow(row, colList);
				String s1 = obj.toString();
				int newInt1 = Integer.parseInt(s1);
				if (newInt1 >= newInt4) {
					rowList.add(row);
				}
			}
			break;
		case "=":
			for (int row = 0; row < this.length; row++) {
				Object obj = this.getRow(row, colList);
				if (obj.equals(o)) {
					rowList.add(row);
				}
			}
			break;
		default:           // Inform if the user enters a value other than the menu choices           
			System.out.println("This is not a valid choice. Please try again.");
			break;
	}
		
		//create new data frame
		ArrayList<String> newColumnNames = (ArrayList<String>) this.columnNames;
		ArrayList<String> newColumnTypes = (ArrayList<String>) this.columnTypes;
		List<List<Object>> columns = new ArrayList<List<Object>>();
		for (int column = 0; column < newColumnNames.size(); column++) {
			columns.add(new ArrayList<Object>());
		}
		for (int row = 0; row < rowList.size(); row++) {
			for (int column = 0; column < this.width; column++) {
				ArrayList<Integer> colList1 = new ArrayList<Integer>();
				colList1.add(column);
				columns.get(column).add(this.getRow(rowList.get(row), colList1));
			}
		}
		
		MyDataFrame sortedDf = new MyDataFrame(newColumnNames,
				newColumnTypes, columns);
		
		return sortedDf;
	}
	
	public MyDataFrame loc(int start, int end) {
		List<List<Object>> newData = new ArrayList<>();
		for (int col = 0; col < this.width; col++) {
			newData.add(new ArrayList<Object>());
		}
		for (int row = start; row < end; row++) {
			for (int col = 0; col < this.width; col++) {
				newData.get(col).add(this.data.get(col).get(row));
			}
		}
		
		return new MyDataFrame(new ArrayList<String>(this.columnNames), new ArrayList<String>(this.columnTypes), newData);
	}
	
	public MyDataFrame loc(int loc) {
		return this.loc(loc, this.length);
	}
	
	
	public MyDataFrame sort(int index) {
		String colType = dType(index);
		int[] sortedIndices;
		if (new String("Integer").equals(colType)) {
			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			ArrayList<Object> newList = (ArrayList<Object>) this.data.get(index);
			for (Object obj : newList) {
				String newStr = (String) obj;
				Integer newInt = Integer.parseInt(newStr);
				arrayList.add(newInt);
			}
			sortedIndices = IntStream.range(0, arrayList.size())
	                .boxed().sorted((i, j) -> (arrayList.get(i))-(arrayList.get(j)) )
	                .mapToInt(ele -> ele).toArray();
		}
		else {
			ArrayList<String> arrayList = new ArrayList<String>();
			ArrayList<Object> newList = (ArrayList<Object>) this.data.get(index);
			for (Object obj : newList) {
				String newStr = (String) obj;
				arrayList.add(newStr);
			}
			sortedIndices = IntStream.range(0, arrayList.size())
			                .boxed().sorted((i, j) -> (arrayList.get(i)).compareTo(arrayList.get(j)) )
			                .mapToInt(ele -> ele).toArray();
		}
		//create new sorted dataframe
		ArrayList<String> newColumnNames = (ArrayList<String>) this.columnNames;
		ArrayList<String> newColumnTypes = (ArrayList<String>) this.columnTypes;
		List<List<Object>> columns = new ArrayList<List<Object>>();
		for (int col = 0; col < newColumnNames.size(); col++) {
			columns.add(new ArrayList<Object>());
		}
		for (int row = 0; row < this.length; row++) {
			for (int col = 0; col < this.width; col++) {
				ArrayList<Integer> colList = new ArrayList<Integer>();
				colList.add(col);
				columns.get(col).add(this.getRow(sortedIndices[row], colList));
			}
		}
		
		MyDataFrame sortedDf = new MyDataFrame(newColumnNames,
				newColumnTypes, columns);
		
		return sortedDf;
	}
	
	public MyDataFrame sort(String name) {
		int indexToSortOn = getColumnIndex(name);
		return sort(indexToSortOn);
	}
	
	public Object getMin(int index) {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		ArrayList<Object> newList = (ArrayList<Object>) this.data.get(index);
		for (Object obj : newList) {
			String newStr = (String) obj;
			Integer newInt = Integer.parseInt(newStr);
			arrayList.add(newInt);
		}
		return Collections.min(arrayList);
		}
	
	public Object getMin(String name) {
		int indexToFilterOn = getColumnIndex(name);
		return getMin(indexToFilterOn);
	}
	
	public Object getMax(int index) {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		ArrayList<Object> newList = (ArrayList<Object>) this.data.get(index);
		for (Object obj : newList) {
			String newStr = (String) obj;
			Integer newInt = Integer.parseInt(newStr);
			arrayList.add(newInt);
		}
		return Collections.max(arrayList);
	}
	
	public Object getMax(String name) {
		int indexToFilterOn = getColumnIndex(name);
		return getMax(indexToFilterOn);
	}
	
}
