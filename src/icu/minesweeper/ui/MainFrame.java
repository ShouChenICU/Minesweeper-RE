package icu.minesweeper.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author shouchen
 * DateTime: 2021-05-15 22:41
 */
public class MainFrame extends JFrame {
	private void initUi() {
		SwingUtilities.invokeLater(() -> {
			this.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
			this.setTitle("Minesweeper-RE");
			this.setMinimumSize(new Dimension(800, 600));
			this.setSize(800, 600);
			this.setLocationRelativeTo(null);

			this.setVisible(true);
		});
	}

	public MainFrame() {
		initUi();
	}
}
