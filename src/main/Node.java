package main;

import java.awt.*;

public class Node {

    private int x;

    private int y;

    private int g;

    private int h;

    private int f;

    private Node previousNode;

    public Node(int x, int y, Node prevNode) {
        this.x = x;
        this.y = y;
        this.previousNode = prevNode;
    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setParent(Node parent) {
        this.previousNode = parent;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setG(int n) {
        this.g = n;
    }

    public int getG() {
        return g;
    }

    public void setH(Node end) {
        h = (int) Math.sqrt(Math.pow(end.x - this.x, 2) + Math.pow(end.y - this.y, 2));
    }

    public int getH() {
        return h;
    }


    public void setF() {
        f = this.h + this.g;
    }

    public int getF() {
        return f;
    }

    public Node getPreviousNode() {
        return this.previousNode;
    }

}
