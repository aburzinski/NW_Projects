import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyDataFrame {
	private List<String> columnNames;
	private List<Integer> index;
	private List<String> columnTypes;
	private List<List<Object>> data;
	private int length;
	private int width;
	
	public MyDataFrame(ArrayList<String> columnNames, ArrayList<String> columnTypes,
			List<List<Object>> columns) {
		this.columnNames = columnNames;
		this.columnTypes = columnTypes;
		this.data = columns;
		
		this.index = new ArrayList<Integer>();
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
		String rtn = this.columnNames.get(0);
		for (int col = 1; col < this.width; col++) {
			rtn += ", " + this.columnNames.get(col);
		}
		rtn += "\n";
		for (int row = 0; row < this.length; row++) {
			rtn += this.getRow(row, this.index) + "\n";
		}
		return rtn;
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
	
//	public MyDataFrame filter(String col, String op, Object o) {
//		
//	}
	
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
	
//	public MyDataFrame sort(int index) {
//		
//	}
//	
//	public MyDataFrame sort(String name) {
//		
//	}
//	
//	public Object getMin(int index) {
//		
//	}
//	
//	public Object getMin(String name) {
//		
//	}
//	
//	public Object getMax(int index) {
//		
//	}
//	
//	public Object getMax(String name) {
//		
//	}
	
}
