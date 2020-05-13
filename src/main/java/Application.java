import data.DataLoader;
import display.DisplayResult;
import graph.Solution;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {
        Solution g3205 = DataLoader.read("3205");
        System.out.println(g3205);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayResult(g3205); // Let the constructor do the job
            }
        });

    }

}
