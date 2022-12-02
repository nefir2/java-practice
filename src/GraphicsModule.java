public interface GraphicsModule {
	void draw(GameField field);
	boolean isClosedRequested();
	void destroy();
}
