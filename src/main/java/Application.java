import data.DataLoader;
import graph.DeliveryTour;
import graph.Solution;

public class Application {

    public static void main(String[] args) {
        Solution g3205 = DataLoader.read("3205");
        System.out.println(g3205);

        DeliveryTour dtTest3205 = g3205.testTour();
        System.out.println("\n");
        System.out.println(dtTest3205.toString());
    }

}
