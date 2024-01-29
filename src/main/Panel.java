package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel {

	private JFrame frame;

	private StopWatch timer;

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
	
	public int getScreenWidth() {
		return SCREEN_WIDTH;
	}
	
	public int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public boolean isWall(int x, int y){
		return grid[y][x] == 1;
	}

	private void startAlgorithm(){
		AStarAlgorithm algo = new AStarAlgorithm(this);
		algo.findPath();
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
				}
			}
		}

		paintGoal(g);
		g.setColor(Color.blue);
		g.fillRect(grid[0][0], grid[0][0], 10, 10);

	}

	private void paintGoal(Graphics g) {

		g.setColor(Color.red);
		g.fillRect(grid.length * 10 - 10, grid[0].length * 10 - 10, 10, 10);
		System.out.print(grid.length * 10 - 10);
		System.out.println(" , " + (grid[0].length * 10 - 10));
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

	private class StopWatch {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

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
			if(e.getKeyCode() == KeyEvent.VK_S){

			}
		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}
}
