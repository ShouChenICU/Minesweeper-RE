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
	private JPanel contentPane;
	/**
	 * 菜单面板
	 */
	private MenuPane menuPane;
	/**
	 * 单人游戏
	 */
	private SingleGamePane singleGamePane;
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
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(null);
		contentPane.setOpaque(true);
		contentPane.setBackground(Color.white);
		menuPane = new MenuPane();
		singleGamePane = new SingleGamePane();
		SwingUtilities.invokeLater(() -> {
			this.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
			this.setTitle(EnvironmentVariable.NAME + " V" + EnvironmentVariable.VERSION);
			this.setMinimumSize(new Dimension(800, 600));
			this.setSize(800, 600);
			this.setBackground(Color.lightGray);
			this.setLocationRelativeTo(null);
			contentPane.add(menuPane);
			this.displayPane = this.menuPane;
			this.setVisible(true);
		});
	}

	/**
	 * 显示主菜单
	 */
	public void showMenu() {
		contentPane.removeAll();
		contentPane.add(menuPane);
		displayPane = menuPane;
		this.autoResize();
	}

	/**
	 * 显示单人游戏界面
	 */
	public void showSingleGame() {
		contentPane.removeAll();
		contentPane.add(singleGamePane);
		displayPane = singleGamePane;
		this.autoResize();
	}

	private MainFrame() {
		initUI();
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				autoResize();
			}
		});
	}

	public void autoResize() {
		int width = getWidth();
		int height = getHeight();
		this.displayPane.setBounds(0, 0, width, height);
		this.displayPane.autoResize(width, height);
	}
}
