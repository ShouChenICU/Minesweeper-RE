package icu.minesweeper.ui;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author shouchen
 * DateTime: 2021-05-15 23:46
 */
public interface AutoResizePane {
	/**
	 * 自动适应大小
	 *
	 * @param width  父容器宽度
	 * @param height 父容器高度
	 */
	void autoResize(int width, int height);
}
