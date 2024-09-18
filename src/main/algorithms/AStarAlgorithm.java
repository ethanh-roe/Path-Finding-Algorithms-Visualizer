package main.algorithms;

import main.Panel;
import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

import main.Node;
import main.NodeComparator;

public class AStarAlgorithm implements PathFindingAlgorithm, Runnable {

    private double dist;
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
        panel.setFrameTitle("A* Algorithm");
        dist = 0;
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
        double tempG;
        boolean newPath = false;
        while (openList.size() > 0) {
            Thread.sleep(10);
            currentNode = openList.poll();
            closedList.add(currentNode);

            if (currentNode.getX() == endNode.getX() && currentNode.getY() == endNode.getY()) {
                endNode.setParent(currentNode);
                foundEnd();
                return;
            }
            findNeighbors(currentNode);
            for (Node n : tempNeighborList) {
                if(!closedListHas(n) && !panel.isWall((int) n.getY(), (int) n.getX())){
                    tempG = currentNode.getG() + currentNode.getH();

                    newPath = false;
                    if(openListHas(n)){
                        if(tempG < n.getG()){
                            n.setG(tempG);
                            newPath = true;
                        }
                    }else{
                        n.setG(tempG);
                        newPath = true;
                        openList.add(n);
                    }

                    if(newPath){
                        n.setH(endNode);
                        n.setF();
                        n.setParent(currentNode);
                    }
                }
            }
            updateGrid();
        }

    }

    private void foundEnd() throws InterruptedException {
        Node current = endNode;
        while(current != startNode){
            dist++;
            Thread.sleep(10);
            panel.setGridState((int)current.getY(), (int)current.getX(), 4);
            current = current.getPreviousNode();
        }
        System.out.println("Distance = " + dist);
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
            panel.setGridState((int) n.getY(), (int) n.getX(), 2);
        }
        for (Node n : closedList) {
            panel.setGridState((int) n.getY(), (int) n.getX(), 3);
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


    @Override
    public void run() {
        findPath();
    }
}
