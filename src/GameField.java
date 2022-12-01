import java.util.Random;

public class GameField {
    private int[][] theField;

    public GameField() {
        theField = new int[COUNT_CELLS_X][Constants.COUNT_CELLS_Y]; //maybe x must be Constants. ?

        for (int i = 0; i < theField.length; i++) {
            for (int j = 0; j < theField[i].length; j++) {
                theField[i][j] = 0;
            }
        }
    }

    public int getState(int x, int y) { return theField[x][y]; }
    public void setState(int x, int y, int state) {
        //some geniuses want to check input.

        //ok, I'll check input.
        if (x > theField.length || x < 0 || y > theField[x].length || y < 0) throw new IllegalArgumentException("указанная позиция оказалась вне массива.");

        theField[x][y] = state;
    }

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

	private static void createInitialCells() {
		for (int i = 0; i < COUNT_INITIAL_CELLS; i++) generateNewCell();
	}
	private static void generateNewCell() {
		int state = (new Random().nextInt(100) <= Constants.CHANCE_OF_LUCKY_SPAWN) ? LUCKY_INITIAL_CELL_STATE : INITIAL_CELL_STATE;
		int randomX, randomY;

		randomX = new Random().nextInt(Constants.COUNT_CELLS_X);
		int currentX = randomX;

		randomY = new Random().nextInt(Constants.COUNT_CELLS_Y);
		int currentY = randomY;

		boolean placed = false;

		//безумие, а не код... //пришлось его читаемость поправлять, но мало чем помогло...
		while (!placed){
			if (gameField.getState(currentX, currentY) == 0) {
				gameField.setState(currentX, currentY, state);
				placed = true;
			}
			else{
				if (currentX + 1 < Constants.COUNT_CELLS_X) currentX++;
				else {
					currentX = 0;
					if(currentY + 1 < Constants.COUNT_CELLS_Y) currentY++;
					else currentY = 0;
				}

				if ((currentX == randomX) && (currentY==randomY) ) ErrorCatcher.cellCreationFailure();
			}
		}
		score += state;
	}

	private static void input() {
		keyboardModule.update();
		direction = keyboardModule.lastDirectionKeyPressed();
		endOfGame = endOfGame || graphicsModule.isCloseRequseted() || keyboardModule.wasEscPressed();
	}
}