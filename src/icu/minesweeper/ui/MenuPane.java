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
public class MenuPane extends JLayeredPane implements AutoResizePane {
	private JLabel titleLabel;

	private void initUI() {
		this.titleLabel = new JLabel(EnvironmentVariable.NAME);
		this.titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		this.titleLabel.setSize(titleLabel.getPreferredSize());
		SwingUtilities.invokeLater(() -> {
			this.add(titleLabel, JLayeredPane.DEFAULT_LAYER);
		});
	}

	public MenuPane() {
		this.initUI();
	}

	@Override
	public void autoResize(int width, int height) {
		this.titleLabel.setLocation((width - titleLabel.getWidth()) / 2, 0);
	}
}
