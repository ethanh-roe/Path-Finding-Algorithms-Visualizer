package main;

import java.util.ArrayList;

public class AStarAlgorithm implements PathFindingAlgorithm {

    private int g = 0;

    private int h = 0;

    private int f = 0;

    private ArrayList<Node> openList = new ArrayList<>();

    private ArrayList<Node> closedList = new ArrayList<>();

    private Node endNode;

    private Node currentNode;

    private Node startNode;

    private Panel panel;


    public AStarAlgorithm(Panel p) {
        panel = p;
        openList.set(0, new Node(0, 0));
        endNode = new Node(panel.getWidth() / 10 - 1, panel.getHeight() / 10 - 1);
        startNode = new Node(0, 0);
        currentNode = startNode;
    }

    @Override
    public void findPath() {
        openList.add(startNode);
        startNode.setH(endNode);
        startNode.setG();
        startNode.setF();
        Node low = openList.get(0);
        for (int i = 1; i < openList.size(); i++) {
            if (openList.get(i).getF() < low.getF()) {
                low = openList.get(i);
            }
        }
        closedList.add(low);
        findNeighbors(low);
    }

    @Override
    public void updateGrid() {
        // TODO Auto-generated method stub

    }

    private void findNeighbors(Node node) {
        Node temp;
        int x = node.getX();
        int y = node.getY();
        boolean inClosedList = false;
        boolean inOpenList = false;
        if (x == 0 && y == 0) {
            temp = new Node(1, 0, node);
            initNode(temp);
            openList.add(temp);

            temp = new Node(0, 1, node);
            initNode(temp);
            openList.add(temp);

            temp = new Node(1, 1, node);
            initNode(temp);
            openList.add(temp);
        } else {


        }
    }

    private void initNode(Node n) {
        n.setG();
        n.setH(endNode);
        n.setF();
    }


}
