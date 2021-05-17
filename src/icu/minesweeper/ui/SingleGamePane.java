package icu.minesweeper.ui;

import icu.minesweeper.MinesweeperCore;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * Description:单人游戏主界面
 *
 * @author shouchen
 * DateTime: 2021-05-16 19:19
 */
public class SingleGamePane extends AutoResizePane {
	private MinesweeperCore minesweeperCore;
	private JLabel gameInfoLabel;
	private GameBroad gameBroad;

	private void initUI() {
		gameInfoLabel = new JLabel();
		gameInfoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		gameInfoLabel.setHorizontalAlignment(JLabel.CENTER);
		gameInfoLabel.setText("时间:123s 剩余雷数:99 生命:3");
		gameBroad = new GameBroad();
		SwingUtilities.invokeLater(() -> {
			this.add(gameInfoLabel, JLayeredPane.DEFAULT_LAYER);
			this.add(gameBroad, JLayeredPane.DEFAULT_LAYER);
		});
	}

	public void startGame(){

	}

	public SingleGamePane() {
		initUI();
	}

	@Override
	void autoResize(int width, int height) {
		gameInfoLabel.setBounds(0, 0, width, 50);
		gameBroad.setBounds(0, 50, width, height - 50);
	}

	private class GameBroad extends JLabel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
		}

		public GameBroad() {
		}
	}
}
