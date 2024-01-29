package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarAlgorithm implements PathFindingAlgorithm, Runnable {

    private int g = 0;

    private int h = 0;

    private int f = 0;

    private PriorityQueue<Node> openList = new PriorityQueue<>(1, new NodeComparator());

    private ArrayList<Node> closedList = new ArrayList<>();

    private ArrayList<Node> tempNeighborList = new ArrayList<>();

    private Node endNode;

    private Node currentNode;

    private Node startNode;

    private Panel panel;


    public AStarAlgorithm(Panel p) {
        panel = p;
        endNode = new Node(panel.getWidth() / 10 - 1, panel.getHeight() / 10 - 5);
        startNode = new Node(0, 0);
        openList.add(startNode);
    }

    @Override
    public void findPath() {
        try {
            aStar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void aStar() throws InterruptedException {
        while (openList.size() > 0) {
            Thread.sleep(10);
            currentNode = openList.poll();
            closedList.add(currentNode);
            System.out.println(currentNode.getX() + ", " + currentNode.getY());
            System.out.println(endNode.getX() + ", " + endNode.getY());

            if (currentNode.getX() == endNode.getX() && currentNode.getY() == endNode.getY()) {
                //TODO
                endNode.setParent(currentNode);
                System.out.println("found end");
                foundEnd();
                return;
            }
            findNeighbors(currentNode);
            for (Node n : tempNeighborList) {

                if (panel.isWall(n.getY(), n.getX()) || closedListHas(n)) {

                } else {
                    if (!openListHas(n) || openList.comparator().compare(currentNode, n) == 1) {
                        n.setParent(currentNode);
                        if (!openListHas(n)) {
                            openList.add(n);
                        }
                    }
                }
            }
            updateGrid();
        }

    }

    private void foundEnd() throws InterruptedException {
        Node current = endNode;
        while(current != startNode){
            Thread.sleep(10);
            panel.setGridState(current.getY(), current.getX(), 4);
            current = current.getPreviousNode();
        }
    }

    private boolean closedListHas(Node node){
        for(Node n: closedList){
            if(n.getY() == node.getY() && n.getX() == node.getX()){
                return true;
            }
        }
        return false;
    }

    private boolean openListHas(Node node){
        for(Node n: openList){
            if(n.getY() == node.getY() && n.getX() == node.getX()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateGrid() {
        for (Node n : openList) {
            panel.setGridState(n.getY(), n.getX(), 2);
        }
        for (Node n : closedList) {
            panel.setGridState(n.getY(), n.getX(), 3);
        }
        panel.repaint();
    }

    private void findNeighbors(Node node) {
        tempNeighborList.clear();
        Node temp;
        int x = node.getX();
        int y = node.getY();
        for (int xx = -1; xx <= 1; xx++) {
            for (int yy = -1; yy <= 1; yy++) {
                if (xx == 0 && yy == 0) {
                    continue; // You are not neighbor to yourself
                }
                if ( Math.abs(xx) + Math.abs(yy) > 1) {
                    continue;
                }
                if (isOnMap(x + xx, y + yy)) {
                    tempNeighborList.add(new Node(x + xx, y + yy, currentNode));
                }
            }
        }

    }
    private boolean isOnMap(int x, int y) {
        return x >= 0 && y >= 0 && x < panel.getScreenWidth() / 10 && y < panel.getScreenHeight() / 10;
    }

    private void initNode(Node n) {
        n.setG();
        n.setH(endNode);
        n.setF();
    }


    @Override
    public void run() {
        findPath();
    }
}
