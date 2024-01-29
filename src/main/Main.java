package main;

import javax.swing.*;

public class Main {

    static Panel mainPanel;

    static JFrame frame;

    public static void main(String[] args) {
        create();
    }

    private static void create() {

        frame = new JFrame("Path Finding Algorithms: Algorithm = []");
        mainPanel = new Panel(frame);
        frame.add(mainPanel);

        frame.setFocusable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}
