import java.util.Random;

public class GameField {
	private int[][] theField;

	public GameField() {
		theField = new int[COUNT_CELLS_X][Constants.COUNT_CELLS_Y]; 

		for (int i = 0; i < theField.length; i++) {
			for (int j = 0; j < theField[i].length; j++) {
				theField[i][j] = 0;
			}
		}
	}

	public int getState(int x, int y) { return theField[x][y]; }
	public void setState(int x, int y, int state) { theField[x][y] = state; }

	public void setColumn(int i, int[] newColumn) { theField[i] = newColumn; }
	public int[] getColumn(int i) { return theField[i]; }

	public void setLine(int i, int[] newLine) {
		for (int j = 0; j < COUNT_CELLS_X; j++) {
			theField[j][i] = newLine[j];
		}
	}
	public int[] getLine(int i) {
		int[] ret = new int[COUNT_CELLS_X];
		for (int j = 0; j < COUNT_CELLS_X; j++) { ret[j] = theField[j][i]; }
		return ret;
	}


}
