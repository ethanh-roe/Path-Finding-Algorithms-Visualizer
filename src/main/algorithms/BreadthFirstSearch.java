package main.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import main.Node;
import main.Panel;
public class BreadthFirstSearch implements PathFindingAlgorithm, Runnable {

    private ArrayList<Node> tempNeighborList = new ArrayList<>();

    private Queue<Node> queue = new LinkedList<>();

    private ArrayList<Node> visited = new ArrayList<>();

    private Node endNode;

    private Node currentNode;

    private Node startNode;

    private Panel panel;

    public BreadthFirstSearch(Panel p) {
        panel = p;
        endNode = new Node(panel.getWidth() / 10 - 1, panel.getHeight() / 10 - 5);
        startNode = new Node(0, 0);
        queue.add(startNode);
        visited.add(startNode);
        panel.setFrameTitle("Breadth First Search");
    }

    @Override
    public void findPath() {
        try {
            BFS();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGrid() {
        for (Node n : visited) {
            panel.setGridState((int) n.getY(), (int) n.getX(), 3);
        }
        for (Node n : queue) {
            panel.setGridState((int) n.getY(), (int) n.getX(), 2);
        }
        panel.repaint();
    }

    @Override
    public void run() {
        findPath();
    }

    private void BFS() throws InterruptedException {
        currentNode = startNode;
        while (queue.size() != 0) {
            Thread.sleep(2);
            currentNode = queue.poll();

            if (currentNode.getX() == endNode.getX() && currentNode.getY() == endNode.getY()) {
                endNode.setParent(currentNode);
                foundEnd();
                return;
            }

            findNeighbors(currentNode);
            for (Node n : tempNeighborList) {
                if (visitedListHas(n)) {

                } else {
                    n.setParent(currentNode);
                    queue.add(n);
                    visited.add(n);
                }
            }
            updateGrid();
        }
        foundEnd();
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

    private boolean visitedListHas(Node node) {
        for (Node n : visited) {
            if (n.getY() == node.getY() && n.getX() == node.getX()) {
                return true;
            }
        }
        return false;
    }

    private void findNeighbors(Node node) {
        tempNeighborList.clear();
        Node temp;
        double x = node.getX();
        double y = node.getY();
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (r == 0 && c == 0) {
                    continue;
                }
                if (Math.abs(r) + Math.abs(c) > 1) {
                    continue;
                }
                if (isOnMap((int) x + c, (int) y + r)) {
                    tempNeighborList.add(new Node(x + c, y + r, currentNode));
                }
            }
        }

    }

    private boolean isOnMap(int x, int y) {
        return x >= 0 && y >= 0 && x < panel.getScreenWidth() / 10 && y < panel.getScreenHeight() / 10 && !panel.isWall(y, x);
    }
}
