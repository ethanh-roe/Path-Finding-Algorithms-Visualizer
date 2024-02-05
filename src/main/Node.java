package main;

import java.awt.*;

public class Node {

    private double x;

    private double y;

    private double g;

    private double h;

    private double f;

    private Node previousNode;

    public Node(double x, double y, Node prevNode) {
        this.x = x;
        this.y = y;
        this.previousNode = prevNode;
    }

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setParent(Node parent) {
        this.previousNode = parent;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setG(double n) {
        this.g = n;
    }

    public double getG() {
        return g;
    }

    public void setH(Node end) {
        h = Math.sqrt(Math.pow(end.x - this.x, 2) + Math.pow(end.y - this.y, 2));
    }

    public double getH() {
        return h;
    }


    public void setF() {
        f = this.h + this.g;
    }

    public double getF() {
        return f;
    }

    public Node getPreviousNode() {
        return this.previousNode;
    }

}
