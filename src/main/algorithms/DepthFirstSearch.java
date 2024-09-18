package main.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import main.Node;
import main.Panel;

public class DepthFirstSearch implements PathFindingAlgorithm, Runnable {

	private ArrayList<Node> tempNeighborList = new ArrayList<>();

	private Queue<Node> queue = new LinkedList<>();

	private ArrayList<Node> visited = new ArrayList<>();

	private Node endNode;

	private Node currentNode;

	private Node startNode;

	private Panel panel;

	public DepthFirstSearch(Panel p) {
		panel = p;
		endNode = new Node(panel.getWidth() / 10 - 1, panel.getHeight() / 10 - 5);
		startNode = new Node(0, 0);
		panel.setFrameTitle("Breadth First Search");
	}
	
	private void DFS(Node currentNode) throws InterruptedException {
		if(currentNode.getX() == endNode.getX() && currentNode.getY() == endNode.getY()) {
			foundEnd();
		}
		visited.add(currentNode);
		findNeighbors(currentNode);
		
		for(Node n : tempNeighborList) {
			Thread.sleep(2);
			if(!visited.contains(n)) {
				DFS(n);
			}
		}
	}
	
	private void foundEnd() throws InterruptedException {
    	int dist = 0;
        Node current = endNode;
        while (current != startNode) {
        	dist++;
            Thread.sleep(10);
            panel.setGridState((int) current.getY(), (int) current.getX(), 4);
            current = current.getPreviousNode();
        }
        System.out.println("Distance = " + dist);
    }

	@Override
	public void run() {
		try {
			DFS(startNode);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void findPath() {
		

	}

	public void updateGrid() {
        for (Node n : visited) {
            panel.setGridState((int) n.getY(), (int) n.getX(), 3);
        }
        for (Node n : queue) {
            panel.setGridState((int) n.getY(), (int) n.getX(), 2);
        }
        panel.repaint();
    }

	
	
	private void findNeighbors(Node node) {
        tempNeighborList.clear();
        Node temp;
        double x = node.getX();
        double y = node.getY();
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (c == 0 && r == 0) {
                    continue;
                }
                if ( Math.abs(c) + Math.abs(r) > 1) {
                    continue;
                }
                if (isValidSquare((int) x + r, (int) y + c)) {
                    temp = new Node(x + r, y + c);
                    tempNeighborList.add(temp);

                }
            }
        }

    }
    private boolean isValidSquare(int x, int y) {
        return x >= 0 && y >= 0 && x < panel.getScreenWidth() / 10 && y < panel.getScreenHeight() / 10;
    }
	
	

}
