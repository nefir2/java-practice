public class Main() {
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
}