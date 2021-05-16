package icu.minesweeper.ui;

import icu.minesweeper.EnvironmentVariable;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author shouchen
 * DateTime: 2021-05-15 23:43
 */
public class MenuPane extends AutoResizePane {
	private JPanel titlePanel;
	private JLabel titleLabel;
	private JLabel versionLabel;
	private JButton startGameButton;
	private JButton aboutButton;
	private JPanel actionPanel;

	/**
	 * 初始化UI
	 */
	private void initUI() {
		titlePanel = new JPanel();
		titleLabel = new JLabel(EnvironmentVariable.NAME);
		versionLabel = new JLabel(" V" + EnvironmentVariable.VERSION);
		titlePanel.setOpaque(false);
		titlePanel.setLayout(null);
		titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		titleLabel.setSize(titleLabel.getPreferredSize());
		titleLabel.setLocation(0, 0);
		versionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		versionLabel.setSize(versionLabel.getPreferredSize());
		versionLabel.setLocation(titleLabel.getWidth(), titleLabel.getHeight() - versionLabel.getHeight());
		titlePanel.setSize(titleLabel.getWidth() + versionLabel.getWidth(), titleLabel.getHeight() + versionLabel.getHeight());
		actionPanel = new JPanel();
		actionPanel.setOpaque(false);
		actionPanel.setLayout(null);
		actionPanel.setSize(100, 90);
		startGameButton = new JButton("单人游戏");
		startGameButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		startGameButton.setBounds(0, 0, 100, 40);
		aboutButton = new JButton("关于");
		aboutButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		aboutButton.setBounds(0, 50, 100, 40);

		SwingUtilities.invokeLater(() -> {
			titlePanel.add(titleLabel);
			titlePanel.add(versionLabel);
			this.add(titlePanel, JLayeredPane.DEFAULT_LAYER);
			actionPanel.add(startGameButton);
			actionPanel.add(aboutButton);
			this.add(actionPanel, JLayeredPane.PALETTE_LAYER);
		});
	}

	public MenuPane() {
		this.initUI();
	}

	@Override
	public void autoResize(int width, int height) {
		titlePanel.setLocation((width - titlePanel.getWidth()) / 2, 0);
		actionPanel.setLocation((width - actionPanel.getWidth()) / 2, (height - actionPanel.getHeight() - titlePanel.getHeight() - titlePanel.getY()) / 2);
	}
}
