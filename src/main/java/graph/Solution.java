package graph;

import java.io.Serializable;
import java.util.List;

/**
 * This class models a graph corresponding to the loaded dataset.
 * It is used in the project to find optimum solutions to the Capacitated Vehicule Routing Problem.
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see Edge
 * @see Node
 * @see DeliveryTour
 */
public class Solution implements Serializable {

    private Node _deposit;
    private List<Node> _clients;
    private List<DeliveryTour> _deliveryTours;

    public Solution(Node deposit, List<Node> clients) {
        _deposit = deposit;
        _clients = clients;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(_deposit).append("\n");
        _clients.forEach(node -> sb.append(node).append("\n"));
        return sb.toString();
    }

    public DeliveryTour testTour() {
        DeliveryTour dt = new DeliveryTour(_deposit);
        int i = 0;
        while(_clients.get(i).getOrder() <= dt.remainingSpace()) {
            dt.append(_clients.get(i));
            i++;
        }
        dt.internalSwap(_clients.get(0), _clients.get(6));
        return dt;
    }

    //TODO : a method to fill randomly DeliveryTours
}
