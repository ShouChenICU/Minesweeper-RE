package icu.minesweeper.ui;

import icu.minesweeper.EnvironmentVariable;

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
public class MainFrame extends JFrame {
	private static volatile MainFrame mainFrame;
	/**
	 * 菜单面板
	 */
	private MenuPane menuPane;
	/**
	 * 当前显示的容器
	 */
	private AutoResizePane displayPane;

	/**
	 * 获取单例
	 *
	 * @return 对象
	 */
	public static MainFrame getInstance() {
		if (mainFrame == null) {
			synchronized (MainFrame.class) {
				if (mainFrame == null) {
					mainFrame = new MainFrame();
				}
			}
		}
		return mainFrame;
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		this.menuPane = new MenuPane();
		SwingUtilities.invokeLater(() -> {
			this.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
			this.setTitle(EnvironmentVariable.NAME + " V" + EnvironmentVariable.VERSION);
			this.setMinimumSize(new Dimension(800, 600));
			this.setSize(800, 600);
			this.setBackground(Color.lightGray);
			this.setLocationRelativeTo(null);
			this.setContentPane(this.menuPane);
			this.displayPane = this.menuPane;
			this.setVisible(true);
		});
	}

	private MainFrame() {
		initUI();
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				autoResize(getWidth(), getHeight());
			}
		});
	}

	public void autoResize(int width, int height) {
		this.displayPane.autoResize(width, height);
	}
}
