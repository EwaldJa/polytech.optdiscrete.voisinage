import algo.SimulatedAnnealing;
import data.DataLoader;
import display.DisplayResult;
import graph.Solution;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {
        Solution g3205 = DataLoader.read("3205");
        System.out.println(g3205.toString());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(g3205);  // Let the constructor do the job
            }
        });

        Solution bestg3205 = new SimulatedAnnealing(500, 0.99, 1000, 0.01).processCurrent(g3205);


        System.out.println(bestg3205.toString());


        //System.out.println(g3205);

        /*
        DeliveryTour dt_0_3205 = g3205.getDeliveryTours().get(0);
        DeliveryTour dt_1_3205 = g3205.getDeliveryTours().get(1);


        System.out.println("------\nBefore internal swap");
        System.out.println("dt0");
        System.out.println(dt_0_3205.toString());
        dt_0_3205.internalSwapRandom();
        System.out.println("------\nAfter internal swap");
        System.out.println("dt0");
        System.out.println(dt_0_3205.toString());



        System.out.println("------\nBefore external swap");
        System.out.println("dt0");
        System.out.println(dt_0_3205.toString());
        System.out.println("    dt0 nodelist :");
        dt_0_3205.getNodes().forEach(node -> System.out.println("      " + node.toString()));
        System.out.println("dt1");
        System.out.println(dt_1_3205.toString());
        System.out.println("    dt1 nodelist :");
        dt_1_3205.getNodes().forEach(node -> System.out.println("      " + node.toString()));
        dt_0_3205.externalSwapRandom(dt_1_3205);
        System.out.println("------\nAfter external swap");
        System.out.println("dt0");
        System.out.println(dt_0_3205.toString());
        System.out.println("    dt0 nodelist :");
        dt_0_3205.getNodes().forEach(node -> System.out.println("      " + node.toString()));
        System.out.println("dt1");
        System.out.println(dt_1_3205.toString());
        System.out.println("    dt1 nodelist :");
        dt_1_3205.getNodes().forEach(node -> System.out.println("      " + node.toString()));
        */


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(bestg3205.finaliserSolution()); // Let the constructor do the job
            }
        });

    }

}
