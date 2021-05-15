package icu.minesweeper.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created with IntelliJ IDEA.
 * Description:UI主类
 *
 * @author shouchen
 * DateTime: 2021-05-15 22:41
 */
public class MainFrame extends JFrame implements AutoResizePane {
	/**
	 * 菜单面板
	 */
	private MenuPane menuPane;
	/**
	 * 当前显示的容器
	 */
	private AutoResizePane displayPane;

	/**
	 * 初始化UI
	 */
	private void initUI() {
		this.menuPane = new MenuPane();
		SwingUtilities.invokeLater(() -> {
			this.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
			this.setTitle("Minesweeper-RE");
			this.setMinimumSize(new Dimension(800, 600));
			this.setSize(800, 600);
			this.setBackground(Color.lightGray);
			this.setLocationRelativeTo(null);
			this.setContentPane(this.menuPane);
			this.displayPane = this.menuPane;
			this.setVisible(true);
		});
	}

	public MainFrame() {
		initUI();
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				autoResize(getWidth(), getHeight());
			}
		});
	}

	@Override
	public void autoResize(int width, int height) {
		this.displayPane.autoResize(width, height);
	}
}
