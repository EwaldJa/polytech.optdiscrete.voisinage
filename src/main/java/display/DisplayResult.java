package display;

import graph.Node;
import graph.Solution;
import utils.ColorPicker;

import javax.swing.*;
import java.awt.*;

/**
 * This class displays the result
 *
 * @author Lucas Aupoil, Ewald Janin
 */
public class DisplayResult extends JFrame {

    public static final int CANVAS_WIDTH  = 1280;
    public static final int CANVAS_HEIGHT = 720;

    private DrawCanvas canvas;
    private Solution _graph;

    // Constructor to set up the GUI components and event handlers
    public DisplayResult(Solution graph) {

        _graph = graph;
        canvas = new DrawCanvas();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.add(canvas);
        // or "setContentPane(canvas);"

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("Result");  // "super" JFrame sets the title
        setVisible(true);    // "super" JFrame show
    }

    /**
     * Define inner class DrawCanvas, which is a JPanel used for custom drawing.
     */
    private class DrawCanvas extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint parent's background

            ColorPicker.initColors();
            // Your custom painting codes. For example,
            // Drawing primitive shapes

            _graph.get_deliveryTours().forEach(tour -> {
                g.setColor(ColorPicker.getRandomColor());
                        tour.getEdges().forEach(
                                edge -> {

                        g.drawLine(convertPosX(edge.getN1().getXPos()), convertPosY(edge.getN1().getYPos()), convertPosX(edge.getN2().getXPos()), convertPosY(edge.getN2().getYPos()) );

                        Node node = edge.getN1();
                        int width = node.getOrder() + 3;
                        g.drawOval(convertPosX(node.getXPos()) - width/2, convertPosY(node.getYPos()) - width/2, width, width);
                        g.fillOval(convertPosX(node.getXPos()) - width/2, convertPosY(node.getYPos()) - width/2, width, width);

                        node = edge.getN2();
                        width = node.getOrder() + 3;
                        g.drawOval(convertPosX(node.getXPos()) - width/2, convertPosY(node.getYPos()) - width/2, width, width);
                        g.fillOval(convertPosX(node.getXPos()) - width/2, convertPosY(node.getYPos()) - width/2, width, width);
                    });
                }
            );


        }

    }

    private int convertPosX(int posX){
        return (int) (((double) posX / 100.0) * (double) CANVAS_WIDTH);
    }

    private int convertPosY(int posY){
        return (int) (((double) posY / 100.0) * (double) CANVAS_HEIGHT);
    }
}
