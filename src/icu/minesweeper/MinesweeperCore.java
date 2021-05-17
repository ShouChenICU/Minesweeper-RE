package icu.minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description:扫雷核心
 *
 * @author shouchen
 * DateTime: 2021-05-16 19:20
 */
public class MinesweeperCore {
	private static final int GAME_MODE_EASY = 0;
	private static final int GAME_MODE_MEDIUM = 1;
	private static final int GAME_MODE_HARD = 2;
	/**
	 * 雷区地图
	 * 0-9表示被翻开，9表示雷
	 * 10-19表示未翻开
	 * 20-29表示被确定标记
	 * 30-39表示被可能标记
	 */
	private byte[][] mineMap;
	/**
	 * 地图宽度
	 */
	private int mapWidth;
	/**
	 * 地图高度
	 */
	private int mapHeight;
	/**
	 * 总区块数
	 */
	private int blockCount;
	/**
	 * 翻开数量
	 */
	private int clickCount;
	/**
	 * 剩余雷数
	 */
	private int mineCount;
	/**
	 * 雷占比
	 */
	private double mineScale;
	/**
	 * 生命值
	 */
	private int lifeValue;
	/**
	 * 开始时间
	 */
	private long startTime;
	/**
	 * 结束时间
	 */
	private long endTime;
	/**
	 * 游戏是否结束
	 */
	private boolean isEnd;
	/**
	 * 是否第一次点击
	 */
	private boolean firstClick;
	/**
	 * 方块翻转推送
	 */
	private Runnable flipCallBack;

	/**
	 * 生成地图
	 *
	 * @param x 起始点坐标x
	 * @param y 起始点坐标y
	 */
	private void genMineMap(int x, int y) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < blockCount; i++) {
			list.add(i);
		}
		list.remove(Integer.valueOf(y * mapWidth + x));
		for (int i = 0; i < mapHeight; i++) {
			for (int j = 0; j < mapWidth; j++) {
				mineMap[i][j] = 10;
			}
		}
		Random random = new Random();
		int n, x0, y0, x1, y1, x2, y2, rand;
		for (int i = 0; i < mineCount; i++) {
			rand = random.nextInt(list.size());
			n = list.remove(rand);
			x0 = n % mapWidth;
			y0 = n / mapWidth;
			mineMap[y0][x0] = 19;
			x1 = x0 == 0 ? 0 : x0 - 1;
			x2 = x0 == mapWidth - 1 ? mapWidth - 1 : x0 + 1;
			y1 = y0 == 0 ? 0 : y0 - 1;
			y2 = y0 == mapHeight - 1 ? mapHeight - 1 : y0 + 1;
			for (int j = y1; j <= y2; j++) {
				for (int k = x1; k <= x2; k++) {
					if (mineMap[j][k] != 19) {
						mineMap[j][k]++;
					}
				}
			}
		}
	}

	/**
	 * 点击方块
	 *
	 * @param x x
	 * @param y y
	 */
	public void clickBlock(int x, int y) {
		if (x >= mapWidth || y >= mapHeight || isEnd) {
			return;
		}
		if (firstClick) {
			genMineMap(x, y);
			firstClick = false;
			startTime = System.currentTimeMillis();
		}
		if (mineMap[y][x] < 10) {
			return;
		}
		if (mineMap[y][x] < 20 || mineMap[y][x] >= 30) {
			if (mineMap[y][x] == 19 || mineMap[y][x] == 39) {
				lifeValue--;
				mineMap[y][x] = 9;
				if (lifeValue == 0) {
					isEnd = true;
					endTime = System.currentTimeMillis();
				}
				flipCallBack.run();
				return;
			}
			List<Point> pendingList = new ArrayList<>();
			int n, x0, y0, x1, y1;
			pendingList.add(new Point(x, y));
			while (pendingList.size() > 0) {
				n = pendingList.size();
				for (int i = 0; i < n; i++) {
					Point point = pendingList.remove(0);
					if (mineMap[point.getY()][point.getX()] == 10 || mineMap[point.getY()][point.getX()] == 30) {
						mineMap[point.getY()][point.getX()] = 0;
						x0 = point.getX() == 0 ? 0 : point.getX() - 1;
						y0 = point.getY() == 0 ? 0 : point.getY() - 1;
						x1 = point.getX() == mapWidth - 1 ? mapWidth - 1 : point.getX() + 1;
						y1 = point.getY() == mapHeight - 1 ? mapHeight - 1 : point.getY() + 1;
						for (int j = y0; j <= y1; j++) {
							for (int k = x0; k <= x1; k++) {
								if ((mineMap[j][k] >= 10 && mineMap[j][k] < 19) || (mineMap[j][k] >= 30 && mineMap[j][k] < 39)) {
									pendingList.add(new Point(k, j));
								}
							}
						}
					} else {
						mineMap[y][x] = (byte) (mineMap[y][x] - (mineMap[y][x] < 20 ? 10 : 30));
					}
				}
				flipCallBack.run();
			}
		}
	}

	/**
	 * 双击块
	 *
	 * @param x x
	 * @param y y
	 */
	public void doubleClickBlock(int x, int y) {
		if (mineMap[y][x] >= 10) {
			return;
		}
		int x0, y0, x1, y1, n = 0;
		x0 = x == 0 ? 0 : x - 1;
		y0 = y == 0 ? 0 : y - 1;
		x1 = x == mapWidth - 1 ? mapWidth - 1 : x + 1;
		y1 = y == mapHeight - 1 ? mapHeight - 1 : y + 1;
		for (int j = y0; j <= y1; j++) {
			for (int k = x0; k <= x1; k++) {
				if (mineMap[j][k] >= 20 && mineMap[j][k] < 30) {
					n++;
				}
			}
		}
		if (n == mineMap[y][x]) {
			for (int j = y0; j <= y1; j++) {
				for (int k = x0; k <= x1; k++) {
					if ((mineMap[j][k] >= 10 && mineMap[j][k] < 20) || mineMap[j][k] >= 30) {
						clickBlock(k, j);
					}
				}
			}
		}
	}

	/**
	 * 标记方块
	 *
	 * @param x x
	 * @param y y
	 */
	public void markBlock(int x, int y) {

	}

	/**
	 * 初始化游戏
	 *
	 * @param gameMode 游戏模式
	 */
	public void startGame(int gameMode) {
		this.clickCount = 0;
		this.isEnd = false;
		this.firstClick = true;
		if (gameMode == GAME_MODE_EASY) {
			mapWidth = 9;
			mapHeight = 9;
			mineCount = 10;
			lifeValue = 1;
		} else if (gameMode == GAME_MODE_MEDIUM) {
			mapWidth = 16;
			mapHeight = 16;
			mineCount = 40;
			lifeValue = 2;
		} else if (gameMode == GAME_MODE_HARD) {
			mapWidth = 30;
			mapHeight = 16;
			mineCount = 99;
			lifeValue = 3;
		}
		this.mineMap = new byte[mapHeight][mapWidth];
		this.blockCount = mapHeight * mapWidth;
		this.mineScale = (double) mineCount / blockCount;
	}

	public byte[][] getMineMap() {
		return mineMap;
	}

	public int getClickCount() {
		return clickCount;
	}

	public int getBlockCount() {
		return blockCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public int getLifeValue() {
		return lifeValue;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public MinesweeperCore() {
		this.isEnd = true;
		this.flipCallBack = () -> {
		};
	}

	public double getMineScale() {
		return mineScale;
	}

	public void setFlipCallBack(Runnable flipCallBack) {
		this.flipCallBack = flipCallBack;
	}

	private static class Point {
		private int y;
		private int x;

		public Point(int x, int y) {
			this.y = y;
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}
	}

	public static void main(String[] args) {
		MinesweeperCore core = new MinesweeperCore();
		core.startGame(GAME_MODE_HARD);
		core.clickBlock(2, 4);
		for (int y = 0; y < core.mapHeight; y++) {
			for (int x = 0; x < core.mapWidth; x++) {
				System.out.print(core.mineMap[y][x] - 10);
			}
			System.out.println();
		}
	}
}
