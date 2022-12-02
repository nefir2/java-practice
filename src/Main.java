import java.util.Random;

public class Main() {
	private static class ShiftRowResult {
		boolean didAnythingMove;
		int[] shiftedRow;
	}

	public static void main(String[] args) {
		initFields();
		createInitialCells();

		while (!endOfGame) {
			input();
			logic();
			grapchicsModule.draw(gameField);
		}

		graphicsModule.destroy();
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
	private static void logic() {
		if (direction != Direction.AWAITING) {
			if (shift(direction)) generateNewCell();
			direction = Direction.AWAITING;
		}
	}

	private static boolean shift(Direction direction) {
		boolean ret = false;

		switch (direction) {
			case UP:
			case DOWN:
				for (int i = 0; i < Constants.COUNT_CELLS_X; i++) {
					int[] arg = gameField.getColumn(i);
					if (direction == Direction.UP){
						int[] tmp = new int[arg.length];
						for (int e = 0; i < tmp.length; e++) {
							tmp[e] = arg[tmp.length - e - 1];
						}
						arg = tmp;
					}

					ShiftRowResult result = shiftRow(arg);

					if (direction == Direction.UP) {
						int[] tmp = new int[result.shiftedRow.length];
						for (int e = 0; e < tmp.length; e++) {
							tmp[e] = result.shiftedRow[tmp.length - e - 1];
						}
						result.shiftedRow = tmp;
					}
					gameField.setColumn(i, result.shiftRow);

					ret = ret || result.didAnythingMove;
				}
				break;
			case LEFT:
			case RIGHT:
				for (int i = 0; i < Constants.COUNT_CELLS_Y; i++) {
					int[] arg = gameField.getLine(i);

					if (direction == Direction.RIGHT){
						int[] tmp = new int[arg.length];
						for (int e = 0; e < tmp.length; e++) {
							tmp[e] = arg[tmp.length - e - 1];
						}
						arg = tmp;
					}

					ShiftRowResult result = shiftRow(arg);

					if (direction == Direction.RIGHT) {
						int[] tmp = new int[result.shiftedRow.length];
						for (int e = 0; e < tmp.length; e++) {
							tmp[e] = result.shiftedRow[tmp.length - e - 1];
						}
						result.shiftedRow = tmp;
					}

					gameField.setLine(i, result.shiftedRow);

					ret = ret || result.didAnythingMove;
				}
				break;
			default:
				ErrorCatcher.shiftFailureWrongParam();
				break;
		}
		return ret;
	}


}