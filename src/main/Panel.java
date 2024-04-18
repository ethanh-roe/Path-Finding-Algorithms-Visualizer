package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Panel extends JPanel {

	private JFrame frame;

	private StopWatch timer;

	private int selectedAlgorithm = 1; // BFS by default

	private int[][] grid;

	private final int SCREEN_WIDTH = 900;

	private final int SCREEN_HEIGHT = 900;

	public Panel(JFrame f) {
		this.frame = f;

		timer = new StopWatch();

		Dimension d = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT + 40);
		this.setPreferredSize(d);

		frame.addKeyListener(new MyKeyListener());

		frame.setFocusable(true);

		initGrid();
	}

	public void setFrameTitle(String str) {
		this.frame.setTitle("Path Finding Algorithms: Algorithm = [" + str + " ]");
	}

	public int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	public int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public boolean isWall(int r, int c) {
		return grid[r][c] == 1;
	}

	private void BFS() {
		BreadthFirstSearch algo = new BreadthFirstSearch(this);
		Thread thread = new Thread(algo);
		thread.start();

	}

	private void AStar() {
		AStarAlgorithm algo = new AStarAlgorithm(this);
		Thread thread = new Thread(algo);
		thread.start();
	}

	private void initGrid() {
		grid = new int[SCREEN_HEIGHT / 10][SCREEN_WIDTH / 10];
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				grid[r][c] = 0;
			}
		}
		initObstacles();
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.drawString("Controls:", 20, 920);
		for (int r = 0; r <= SCREEN_HEIGHT / 10; r++) {
			g.drawLine(0, r * 10, SCREEN_WIDTH, r * 10);
		}

		for (int c = 0; c <= SCREEN_WIDTH / 10; c++) {
			g.drawLine(c * 10, 0, c * 10, SCREEN_HEIGHT);
		}

		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] == 1) {
					g.setColor(Color.black);
					g.fillRect(r * 10, c * 10, 10, 10);
				} else if (grid[r][c] == 2) {
					g.setColor(Color.green);
					g.fillRect(r * 10, c * 10, 10, 10);
				} else if (grid[r][c] == 3) {
					g.setColor(Color.cyan);
					g.fillRect(r * 10, c * 10, 10, 10);
				} else if (grid[r][c] == 4) {
					g.setColor(Color.yellow);
					g.fillRect(r * 10, c * 10, 10, 10);
				}
			}
		}
		paintGoal(g);
		g.setColor(Color.blue);
		g.fillRect(0, 0, 10, 10);

	}

	private void paintGoal(Graphics g) {

		g.setColor(Color.blue);
		g.fillRect(grid.length * 10 - 10, grid[0].length * 10 - 10, 10, 10);
	}

	private void initObstacles() {
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (Math.random() < .2) {
					grid[r][c] = 1;
				}

			}
		}
		grid[0][0] = 0;
		grid[0][1] = 0;
		grid[1][0] = 0;
		grid[1][1] = 0;
		grid[SCREEN_HEIGHT / 10 - 1][SCREEN_WIDTH / 10 - 1] = 0;
		grid[SCREEN_HEIGHT / 10 - 1][SCREEN_WIDTH / 10 - 2] = 0;
		grid[SCREEN_HEIGHT / 10 - 2][SCREEN_WIDTH / 10 - 1] = 0;
		grid[SCREEN_HEIGHT / 10 - 2][SCREEN_WIDTH / 10 - 2] = 0;
	}

	public void setGridState(int r, int c, int num) {
		grid[r][c] = num;
	}

	private class StopWatch {

		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				repaint();
			}
		};

		public StopWatch() {
			Timer timer = new Timer(50, taskPerformer);
			timer.setRepeats(true);
			timer.start();
		}
	}

	private class MyKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_S:

				System.out.println("Started selection: " + selectedAlgorithm);

				switch (selectedAlgorithm) {
				case 1:
					BFS();
					break;
				case 2:
					AStar();
				}

				break;

			case KeyEvent.VK_1:
				System.out.println("Selected = BFS");
				selectedAlgorithm = 1; // BFS
				break;

			case KeyEvent.VK_2:
				System.out.println("Selected = A*");
				selectedAlgorithm = 2; // A*
				break;
				
			case KeyEvent.VK_C:
				System.out.println("Cleared grid");
				initGrid();
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}
}
